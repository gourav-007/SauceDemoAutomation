package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "complete-text")
    private WebElement completeText;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    @FindBy(className = "pony_express")
    private WebElement ponyExpressImage;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    @Step
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step
    public boolean isOnCheckoutCompletePage() {
        return getCurrentUrl().contains("checkout-complete.html") && getPageTitle().equals("Checkout: Complete!");
    }

    @Step
    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    @Step
    public String getCompleteText() {
        return getText(completeText);
    }

    @Step
    public boolean isOrderComplete() {
        return isElementDisplayed(completeHeader) && getCompleteHeader().contains("Thank you for your order");
    }

    @Step
    public ProductsPage clickBackHome() {
        click(backHomeButton);
        return new ProductsPage(driver);
    }

    public boolean isBackHomeButtonDisplayed() {
        return isElementDisplayed(backHomeButton);
    }

    public boolean isPonyExpressImageDisplayed() {
        return isElementDisplayed(ponyExpressImage);
    }

    @Step
    public String getFullConfirmationMessage() {
        return getCompleteHeader() + " " + getCompleteText();
    }
}
