package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotal;

    @FindBy(className = "summary_tax_label")
    private WebElement tax;

    @FindBy(className = "summary_total_label")
    private WebElement total;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(className = "summary_info")
    private WebElement summaryInfo;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    @Step
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step
    public boolean isOnCheckoutOverviewPage() {
        return getCurrentUrl().contains("checkout-step-two.html") && getPageTitle().equals("Checkout: Overview");
    }

    @Step
    public String getItemTotal() {
        return getText(itemTotal);
    }

    @Step
    public String getTax() {
        return getText(tax);
    }

    @Step
    public String getTotal() {
        return getText(total);
    }

    public double extractPrice(String priceText) {
        String numericValue = priceText.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numericValue);
    }

    @Step
    public boolean verifyTotalCalculation() {
        double itemTotalValue = extractPrice(getItemTotal());
        double taxValue = extractPrice(getTax());
        double totalValue = extractPrice(getTotal());
        double expectedTotal = itemTotalValue + taxValue;
        return Math.abs(expectedTotal - totalValue) < 0.01;
    }

    @Step
    public CheckoutCompletePage clickFinish() {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }

    @Step
    public ProductsPage clickCancel() {
        click(cancelButton);
        return new ProductsPage(driver);
    }

    public boolean isFinishButtonDisplayed() {
        return isElementDisplayed(finishButton);
    }

    public boolean isSummaryInfoDisplayed() {
        return isElementDisplayed(summaryInfo);
    }
}
