package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.data.TestData;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.login(config.getStandardUsername(), config.getStandardPassword());
    }

    @Test(priority = 1)
    public void testAddSingleItemToCart() {
        productsPage.addItemToCart(TestData.SAUCE_LABS_BACKPACK);
        
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1");
        Assert.assertTrue(productsPage.isRemoveButtonDisplayed(TestData.SAUCE_LABS_BACKPACK));
        
        CartPage cartPage = productsPage.clickCartIcon();
        Assert.assertTrue(cartPage.isProductInCart(TestData.SAUCE_LABS_BACKPACK));
        Assert.assertEquals(cartPage.getCartItemCount(), 1);
    }

    @Test(priority = 2)
    public void testAddMultipleItemsToCart() {
        productsPage.addItemToCart(TestData.SAUCE_LABS_BACKPACK);
        productsPage.addItemToCart(TestData.SAUCE_LABS_BIKE_LIGHT);
        productsPage.addItemToCart(TestData.SAUCE_LABS_BOLT_TSHIRT);
        
        Assert.assertEquals(productsPage.getCartBadgeCount(), "3");
        
        CartPage cartPage = productsPage.clickCartIcon();
        Assert.assertEquals(cartPage.getCartItemCount(), 3);
        Assert.assertTrue(cartPage.isProductInCart(TestData.SAUCE_LABS_BACKPACK));
        Assert.assertTrue(cartPage.isProductInCart(TestData.SAUCE_LABS_BIKE_LIGHT));
        Assert.assertTrue(cartPage.isProductInCart(TestData.SAUCE_LABS_BOLT_TSHIRT));
    }

    @Test(priority = 3)
    public void testRemoveItemFromProductsPage() {
        productsPage.addItemToCart(TestData.SAUCE_LABS_BACKPACK);
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1");
        
        productsPage.removeItemFromCart(TestData.SAUCE_LABS_BACKPACK);
        
        Assert.assertFalse(productsPage.isCartBadgeDisplayed());
        Assert.assertTrue(productsPage.isAddToCartButtonDisplayed(TestData.SAUCE_LABS_BACKPACK));
    }

    @Test(priority = 4)
    public void testRemoveItemFromCartPage() {
        productsPage.addItemToCart(TestData.SAUCE_LABS_BACKPACK);
        
        CartPage cartPage = productsPage.clickCartIcon();
        Assert.assertEquals(cartPage.getCartItemCount(), 1);
        
        cartPage.removeItem(TestData.SAUCE_LABS_BACKPACK);
        
        Assert.assertTrue(cartPage.isCartEmpty());
    }

    @Test(priority = 5)
    public void testCartBadgeCount() {
        Assert.assertFalse(productsPage.isCartBadgeDisplayed());
        
        productsPage.addItemToCart(TestData.SAUCE_LABS_BACKPACK);
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1");
        
        productsPage.addItemToCart(TestData.SAUCE_LABS_BIKE_LIGHT);
        Assert.assertEquals(productsPage.getCartBadgeCount(), "2");
        
        productsPage.removeItemFromCart(TestData.SAUCE_LABS_BACKPACK);
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1");
        
        productsPage.removeItemFromCart(TestData.SAUCE_LABS_BIKE_LIGHT);
        Assert.assertFalse(productsPage.isCartBadgeDisplayed());
    }

    @Test(priority = 6)
    public void testContinueShopping() {
        productsPage.addItemToCart(TestData.SAUCE_LABS_BACKPACK);
        CartPage cartPage = productsPage.clickCartIcon();
        
        ProductsPage returnedProductsPage = cartPage.continueShopping();
        
        Assert.assertTrue(returnedProductsPage.isOnProductsPage());
        Assert.assertEquals(returnedProductsPage.getPageTitle(), TestData.TITLE_PRODUCTS);
    }
}
