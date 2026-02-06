package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    private static final String REMOVE_BUTTON_BY_NAME = "//div[text()='%s']/ancestor::div[@class='cart_item']//button[contains(text(),'Remove')]";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step
    public boolean isOnCartPage() {
        return getCurrentUrl().contains("cart.html") && getPageTitle().equals("Your Cart");
    }

    @Step
    public int getCartItemCount() {
        return cartItems.size();
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    @Step
    public CartPage removeItem(String productName) {
        By removeButton = By.xpath(String.format(REMOVE_BUTTON_BY_NAME, productName));
        click(removeButton);
        return this;
    }

    @Step
    public ProductsPage continueShopping() {
        click(continueShoppingButton);
        return new ProductsPage(driver);
    }

    @Step
    public CheckoutInfoPage checkout() {
        click(checkoutButton);
        return new CheckoutInfoPage(driver);
    }

    public boolean isProductInCart(String productName) {
        By productLocator = By.xpath("//div[text()='" + productName + "']");
        return isElementDisplayed(productLocator);
    }

    public List<String> getProductNames() {
        return itemNames.stream()
                .map(this::getText)
                .toList();
    }

    public String getProductPrice(String productName) {
        By priceLocator = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']//div[@class='inventory_item_price']");
        return getText(priceLocator);
    }

    public boolean isContinueShoppingButtonDisplayed() {
        return isElementDisplayed(continueShoppingButton);
    }

    public boolean isCheckoutButtonDisplayed() {
        return isElementDisplayed(checkoutButton);
    }
}
