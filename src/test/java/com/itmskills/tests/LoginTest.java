package com.itmskills.tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;



public class LoginTest {
    @Test
    public void testLogin() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.saucedemo.com");

            driver.findElement(By.id("user-name"))
                  .sendKeys("standard_user");

            driver.findElement(By.id("password"))
                  .sendKeys("secret_sauce");

            driver.findElement(By.id("login-button"))
                  .click();

            WebDriverWait wait =
                new WebDriverWait(driver,
                    Duration.ofSeconds(10));
            WebElement titleElement =
                wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            By.className("title")));
            String title = titleElement.getText();
            Assert.assertEquals(
                title,
                "Products",
                "Login failed!");
            System.out.println("Login Successful");

            wait.until(
                ExpectedConditions.urlContains(
                    "inventory.html"));
                Assert.assertTrue(
                    driver.getCurrentUrl()
                        .contains("inventory.html"),
                        "Inventory page not loaded");
                        
                        String pageTitle =
                            driver.findElement(By.className("title"))
                                .getText();
                        Assert.assertEquals(
                            pageTitle,
                            "Products");
                            //you can be sure that correct page is loaded
                            Assert.assertTrue(
                                driver.getCurrentUrl()
                                .contains("inventory.html"));
            
        } finally {
            driver.quit();
        }
    }
}
