package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.data.TestData;
import com.saucedemo.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod
    public void loginAndAddItemBeforeTest() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.login(config.getStandardUsername(), config.getStandardPassword());
        productsPage.addItemToCart(TestData.SAUCE_LABS_BACKPACK);
    }

    @Test(priority = 1)
    public void testCompleteCheckoutFlow() {
        CartPage cartPage = productsPage.clickCartIcon();
        Assert.assertTrue(cartPage.isOnCartPage());
        
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        Assert.assertTrue(checkoutInfoPage.isOnCheckoutInfoPage());
        
        CheckoutOverviewPage checkoutOverviewPage = checkoutInfoPage
            .fillCheckoutInfo(TestData.CHECKOUT_FIRST_NAME, 
                            TestData.CHECKOUT_LAST_NAME, 
                            TestData.CHECKOUT_POSTAL_CODE)
            .clickContinue();
        
        Assert.assertTrue(checkoutOverviewPage.isOnCheckoutOverviewPage());
        Assert.assertTrue(checkoutOverviewPage.verifyTotalCalculation());
        
        CheckoutCompletePage checkoutCompletePage = checkoutOverviewPage.clickFinish();
        
        Assert.assertTrue(checkoutCompletePage.isOnCheckoutCompletePage());
        Assert.assertTrue(checkoutCompletePage.isOrderComplete());
        Assert.assertTrue(checkoutCompletePage.getCompleteHeader().contains(TestData.SUCCESS_ORDER_COMPLETE));
    }

    @Test(priority = 2)
    public void testCheckoutWithEmptyFields() {
        CartPage cartPage = productsPage.clickCartIcon();
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        
        checkoutInfoPage.attemptContinue();
        
        Assert.assertTrue(checkoutInfoPage.isErrorMessageDisplayed());
        Assert.assertTrue(checkoutInfoPage.getErrorMessage().contains(TestData.ERROR_FIRST_NAME_REQUIRED));
    }

    @Test(priority = 3)
    public void testCheckoutWithMissingFirstName() {
        CartPage cartPage = productsPage.clickCartIcon();
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        
        checkoutInfoPage.enterLastName(TestData.CHECKOUT_LAST_NAME);
        checkoutInfoPage.enterPostalCode(TestData.CHECKOUT_POSTAL_CODE);
        checkoutInfoPage.attemptContinue();
        
        Assert.assertTrue(checkoutInfoPage.isErrorMessageDisplayed());
        Assert.assertTrue(checkoutInfoPage.getErrorMessage().contains(TestData.ERROR_FIRST_NAME_REQUIRED));
    }

    @Test(priority = 4)
    public void testCheckoutWithMissingLastName() {
        CartPage cartPage = productsPage.clickCartIcon();
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        
        checkoutInfoPage.enterFirstName(TestData.CHECKOUT_FIRST_NAME);
        checkoutInfoPage.enterPostalCode(TestData.CHECKOUT_POSTAL_CODE);
        checkoutInfoPage.attemptContinue();
        
        Assert.assertTrue(checkoutInfoPage.isErrorMessageDisplayed());
        Assert.assertTrue(checkoutInfoPage.getErrorMessage().contains(TestData.ERROR_LAST_NAME_REQUIRED));
    }

    @Test(priority = 5)
    public void testCheckoutWithMissingZipCode() {
        CartPage cartPage = productsPage.clickCartIcon();
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        
        checkoutInfoPage.enterFirstName(TestData.CHECKOUT_FIRST_NAME);
        checkoutInfoPage.enterLastName(TestData.CHECKOUT_LAST_NAME);
        checkoutInfoPage.attemptContinue();
        
        Assert.assertTrue(checkoutInfoPage.isErrorMessageDisplayed());
        Assert.assertTrue(checkoutInfoPage.getErrorMessage().contains(TestData.ERROR_POSTAL_CODE_REQUIRED));
    }

    @Test(priority = 6)
    public void testCancelCheckout() {
        CartPage cartPage = productsPage.clickCartIcon();
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        
        CartPage returnedCartPage = checkoutInfoPage.clickCancel();
        
        Assert.assertTrue(returnedCartPage.isOnCartPage());
        Assert.assertEquals(returnedCartPage.getPageTitle(), TestData.TITLE_CART);
    }

    @Test(priority = 7)
    public void testOrderTotalCalculation() {
        productsPage.addItemToCart(TestData.SAUCE_LABS_BIKE_LIGHT);
        
        CartPage cartPage = productsPage.clickCartIcon();
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        CheckoutOverviewPage checkoutOverviewPage = checkoutInfoPage
            .fillCheckoutInfo(TestData.CHECKOUT_FIRST_NAME, 
                            TestData.CHECKOUT_LAST_NAME, 
                            TestData.CHECKOUT_POSTAL_CODE)
            .clickContinue();
        
        Assert.assertTrue(checkoutOverviewPage.verifyTotalCalculation());
        Assert.assertFalse(checkoutOverviewPage.getItemTotal().isEmpty());
        Assert.assertFalse(checkoutOverviewPage.getTax().isEmpty());
        Assert.assertFalse(checkoutOverviewPage.getTotal().isEmpty());
    }

    @Test(priority = 8)
    public void testBackHomeAfterCheckout() {
        CartPage cartPage = productsPage.clickCartIcon();
        CheckoutInfoPage checkoutInfoPage = cartPage.checkout();
        CheckoutOverviewPage checkoutOverviewPage = checkoutInfoPage
            .fillCheckoutInfo(TestData.CHECKOUT_FIRST_NAME, 
                            TestData.CHECKOUT_LAST_NAME, 
                            TestData.CHECKOUT_POSTAL_CODE)
            .clickContinue();
        CheckoutCompletePage checkoutCompletePage = checkoutOverviewPage.clickFinish();
        
        ProductsPage returnedProductsPage = checkoutCompletePage.clickBackHome();
        
        Assert.assertTrue(returnedProductsPage.isOnProductsPage());
        Assert.assertEquals(returnedProductsPage.getPageTitle(), TestData.TITLE_PRODUCTS);
    }
}
