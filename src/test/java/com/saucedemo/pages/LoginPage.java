package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = ".error-button")
    private WebElement errorButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step
    public LoginPage enterUsername(String username) {
        sendKeys(usernameField, username);
        return this;
    }

    @Step
    public LoginPage enterPassword(String password) {
        sendKeys(passwordField, password);
        return this;
    }

    @Step
    public ProductsPage clickLoginButton() {
        click(loginButton);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            if (alert != null) {
                alert.dismiss();
            }
        } catch (Exception e) {
            // No alert present
        }

        return new ProductsPage(driver);
    }

    @Step
    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }

    @Step
    public LoginPage attemptLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(loginButton);
        return this;
    }

    @Step
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    @Step
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public boolean isUsernameFieldDisplayed() {
        return isElementDisplayed(usernameField);
    }

    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordField);
    }

    @Step
    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("saucedemo.com") && isLoginButtonDisplayed();
    }
}
