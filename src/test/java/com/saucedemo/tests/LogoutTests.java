package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTests extends BaseTest {

    @Test(priority = 1)
    public void testSuccessfulLogout() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
            config.getStandardUsername(),
            config.getStandardPassword()
        );
        
        Assert.assertTrue(productsPage.isOnProductsPage());
        
        LoginPage logoutPage = productsPage.logout();
        
        Assert.assertTrue(logoutPage.isOnLoginPage());
        Assert.assertTrue(logoutPage.isLoginButtonDisplayed());
    }

    @Test(priority = 2)
    public void testLogoutRedirectsToLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
            config.getStandardUsername(),
            config.getStandardPassword()
        );
        
        LoginPage logoutPage = productsPage.logout();
        
        Assert.assertTrue(logoutPage.isUsernameFieldDisplayed());
        Assert.assertTrue(logoutPage.isPasswordFieldDisplayed());
        Assert.assertTrue(logoutPage.isLoginButtonDisplayed());
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
    }

    @Test(priority = 3)
    public void testCannotAccessPagesAfterLogout() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
            config.getStandardUsername(),
            config.getStandardPassword()
        );
        
        productsPage.logout();
        
        driver.get(config.getAppUrl() + "inventory.html");
        
        LoginPage currentPage = new LoginPage(driver);
        boolean isOnLoginPage = currentPage.isOnLoginPage() || currentPage.isErrorMessageDisplayed();
        Assert.assertTrue(isOnLoginPage);
    }
}
