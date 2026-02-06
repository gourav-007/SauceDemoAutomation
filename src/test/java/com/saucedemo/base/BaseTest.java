package com.saucedemo.base;

import com.saucedemo.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected ConfigReader config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Setting up test environment");
        config = ConfigReader.getInstance();

        driver = initializeDriver();
        configureDriver();

        logger.info("Navigating to application URL: {}", config.getAppUrl());
        driver.get(config.getAppUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
            if (config.isScreenshotOnFailure()) {
                takeScreenshot(result.getName());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test passed: {}", result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test skipped: {}", result.getName());
        }

        if (driver != null) {
            logger.info("Closing browser");
            driver.quit();
        }
    }

    private WebDriver initializeDriver() {
        String browser = config.getBrowser().toLowerCase();
        boolean headless = config.isHeadless() || isCIEnvironment();

        logger.info("Initializing {} driver (headless: {})", browser, headless);

        WebDriver driver;
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                prefs.put("autofill.profile_enabled", false);
                chromeOptions.setExperimentalOption("prefs", prefs);

                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation", "enable-logging"});
                chromeOptions.addArguments("--disable-features=PasswordCheck,PasswordLeakDetection,PasswordManagerOnboarding");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--disable-save-password-bubble");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");

                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--window-size=1920,1080");

                driver = new ChromeDriver(chromeOptions);
                break;
        }

        logger.info("WebDriver initialized successfully");
        return driver;
    }

    private void configureDriver() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();
        logger.info("WebDriver configured with timeouts and maximized window");
    }

    protected void takeScreenshot(String screenshotName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshot), "png");
            logger.info("Screenshot captured: {}", screenshotName);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private boolean isCIEnvironment() {
        String ciEnv = System.getenv("CI");
        return "true".equalsIgnoreCase(ciEnv) || 
               System.getenv("GITHUB_ACTIONS") != null;
    }
}
