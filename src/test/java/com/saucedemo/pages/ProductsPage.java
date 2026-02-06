package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement hamburgerMenu;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    private static final String ADD_TO_CART_BUTTON = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[contains(text(),'Add to cart')]";
    private static final String REMOVE_BUTTON = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[contains(text(),'Remove')]";

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Step
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step
    public boolean isOnProductsPage() {
        return getCurrentUrl().contains("inventory.html") && getPageTitle().equals("Products");
    }

    @Step
    public ProductsPage addItemToCart(String productName) {
        By addToCartButton = By.xpath(String.format(ADD_TO_CART_BUTTON, productName));
        WebElement element = waitForElementVisible(addToCartButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

        By removeButton = By.xpath(String.format(REMOVE_BUTTON, productName));
        waitForElementVisible(removeButton);
        return this;
    }

    @Step
    public ProductsPage removeItemFromCart(String productName) {
        By removeButton = By.xpath(String.format(REMOVE_BUTTON, productName));
        WebElement element = waitForElementVisible(removeButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

        By addToCartButton = By.xpath(String.format(ADD_TO_CART_BUTTON, productName));
        waitForElementVisible(addToCartButton);
        return this;
    }

    @Step
    public String getCartBadgeCount() {
        try {
            return getText(cartBadge);
        } catch (Exception e) {
            return "0";
        }
    }

    public boolean isCartBadgeDisplayed() {
        return isElementDisplayed(cartBadge);
    }

    @Step
    public CartPage clickCartIcon() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", cartIcon);
        return new CartPage(driver);
    }

    @Step
    public ProductsPage openMenu() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", hamburgerMenu);
        waitForElementVisible(logoutLink);
        return this;
    }

    @Step
    public LoginPage logout() {
        if (!isElementDisplayed(logoutLink)) {
            openMenu();
        }
        click(logoutLink);
        return new LoginPage(driver);
    }

    public int getProductCount() {
        return inventoryItems.size();
    }

    public boolean isProductDisplayed(String productName) {
        By productLocator = By.xpath("//div[text()='" + productName + "']");
        return isElementDisplayed(productLocator);
    }

    public boolean isAddToCartButtonDisplayed(String productName) {
        By addToCartButton = By.xpath(String.format(ADD_TO_CART_BUTTON, productName));
        return isElementDisplayed(addToCartButton);
    }

    public boolean isRemoveButtonDisplayed(String productName) {
        By removeButton = By.xpath(String.format(REMOVE_BUTTON, productName));
        return isElementDisplayed(removeButton);
    }
}
