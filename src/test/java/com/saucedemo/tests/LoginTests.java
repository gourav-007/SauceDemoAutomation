package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.data.TestData;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(priority = 1)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isOnLoginPage());
        
        ProductsPage productsPage = loginPage.login(
            config.getStandardUsername(),
            config.getStandardPassword()
        );
        
        Assert.assertTrue(productsPage.isOnProductsPage());
        Assert.assertEquals(productsPage.getPageTitle(), TestData.TITLE_PRODUCTS);
    }

    @Test(priority = 2)
    public void testInvalidUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin(TestData.INVALID_USERNAME, config.getStandardPassword());
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.ERROR_INVALID_CREDENTIALS));
    }

    @Test(priority = 3)
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin(config.getStandardUsername(), TestData.INVALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.ERROR_INVALID_CREDENTIALS));
    }

    @Test(priority = 4)
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin("", "");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.ERROR_USERNAME_REQUIRED));
    }

    @Test(priority = 5)
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin(config.getLockedUsername(), config.getLockedPassword());
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.ERROR_LOCKED_OUT_USER));
    }

    @Test(priority = 6)
    public void testEmptyPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.attemptLogin(config.getStandardUsername(), "");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.ERROR_PASSWORD_REQUIRED));
    }
}
