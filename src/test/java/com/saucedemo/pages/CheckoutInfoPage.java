package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutInfoPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public CheckoutInfoPage(WebDriver driver) {
        super(driver);
    }

    @Step
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step
    public boolean isOnCheckoutInfoPage() {
        return getCurrentUrl().contains("checkout-step-one.html") && getPageTitle().equals("Checkout: Your Information");
    }

    @Step
    public CheckoutInfoPage enterFirstName(String firstName) {
        sendKeys(firstNameField, firstName);
        return this;
    }

    @Step
    public CheckoutInfoPage enterLastName(String lastName) {
        sendKeys(lastNameField, lastName);
        return this;
    }

    @Step
    public CheckoutInfoPage enterPostalCode(String postalCode) {
        sendKeys(postalCodeField, postalCode);
        return this;
    }

    @Step
    public CheckoutInfoPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    @Step
    public CheckoutOverviewPage clickContinue() {
        click(continueButton);
        return new CheckoutOverviewPage(driver);
    }

    @Step
    public CartPage clickCancel() {
        click(cancelButton);
        return new CartPage(driver);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    @Step
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    @Step
    public CheckoutInfoPage attemptContinue() {
        click(continueButton);
        return this;
    }
}
