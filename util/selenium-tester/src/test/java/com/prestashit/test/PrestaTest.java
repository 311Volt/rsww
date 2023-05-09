package com.prestashit.test;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.NoSuchElementException;
import java.util.Random;

public class PrestaTest {
    private final Random r;
    private final WebDriver webDriver;

    private static final String HOST_ADDRESS = "https://localhost/";
    private static final int MAX_PRODUCT_QUANTITY = 10;
    private static final int MAX_RETRIES = 10;

    public PrestaTest() {
        this.r = new Random();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--allow-insecure-localhost");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver(options);
        webDriver.get(HOST_ADDRESS);
    }

    @Test
    public void shopTest() throws InterruptedException {
        //System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        addProductsToCart();
        createUser();
        checkout();
        checkShipmentStatus();
    }

    private WebElement safeFind(By by, SearchContext ctx) throws InterruptedException {
        WebElement ret = null;
        for(int i=0; i<MAX_RETRIES; i++) {
            try {
                ret = ctx.findElement(by);
                break;
            } catch(Exception e) {
                if(i==MAX_RETRIES-1) {
                    throw e;
                }
            }
            Thread.sleep(((i==1)?700:100) + i*200);
        }
        if(ret == null) {
            throw new org.openqa.selenium.NoSuchElementException("safeFind by" + by + " failed");
        }
        return ret;
    }

    private WebElement safeFind(By by) throws InterruptedException {
        return safeFind(by, webDriver);
    }

    private void safeClick(By by, SearchContext ctx) throws InterruptedException {
        for(int i=0; i<MAX_RETRIES; i++) {
            try {
                ctx.findElement(by).click();
                break;
            } catch(Exception e) {
                if(i==MAX_RETRIES-1) {
                    throw e;
                }
            }
            Thread.sleep(((i==1)?700:100) + i*200);
        }
        //Thread.sleep(100);
    }

    private void safeClick(By by) throws InterruptedException {
        safeClick(by, webDriver);
    }

    private void addProductsToCart() throws InterruptedException {
        //selecting category
        safeClick(By.id("category-48"));
        //selecting subcategory
        safeClick(By.xpath("//div[@id=\"subcategories\"]/ul/li[1]/div/a"));
        //selecting items to cart from category 1
        for (int i = 0; i < 8; i++) {
            addProductToCart(i);
        }

        safeClick(By.id("category-52"));
        safeClick(By.xpath("//div[@id=\"subcategories\"]/ul/li[2]/div/a"));
        for (int i = 0; i < 2; i++) {
            addProductToCart(i);
        }
    }

    private void addProductToCart(int ind) throws InterruptedException {
        String url = webDriver.getCurrentUrl();
        System.out.println(ind);
        WebElement productList = safeFind(By.xpath("//div[@id=\"js-product-list\"]/div[@class=\"products row\"]"));
        WebElement product = productList.findElements(By.xpath("//div[@class=\"product\"]")).get(ind);

        safeClick(By.tagName("a"), product);
        Select select = new Select(safeFind(By.id("group_5")));
        select.selectByValue("27");
        safeFind(By.id("quantity_wanted")).sendKeys(Keys.chord(Keys.CONTROL, "a"), r.nextInt(MAX_PRODUCT_QUANTITY) + "");
        safeClick(By.xpath("//div[@class=\"add\"]/button"));
        WebDriverWait wait = new WebDriverWait(webDriver, 5 * 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("myModalLabel")));

        webDriver.navigate().to(url);
    }

    private void createUser() throws InterruptedException {
        //accessing the login page
        safeClick(By.xpath("//div[@id=\"_desktop_user_info\"]/div/a"));

        //accessing the register page
        safeClick(By.xpath("//div[@class=\"no-account\"]/a"));

        //filling the form
        safeFind(By.id("field-id_gender-1")).click();
        safeFind(By.id("field-firstname")).sendKeys("Janusz");
        safeFind(By.id("field-lastname")).sendKeys("Pawlacz");
        safeFind(By.id("field-email")).sendKeys("sample_email" + r.nextInt() + "@example.com");
        safeFind(By.id("field-password")).sendKeys("qwerty1234");
        safeFind(By.id("field-birthday")).sendKeys("2005-04-02");
        safeClick(By.name("customer_privacy"));
        safeClick(By.name("psgdpr"));
        safeClick(By.xpath("//section[@class=\"register-form\"]/form/footer/button"));
    }

    private void checkout() throws InterruptedException {
        safeClick(By.xpath("//div[@id=\"_desktop_cart\"]/div/div/a"));
        safeClick(By.xpath("//div[@class=\"text-sm-center\"]/a"));
        //personal data
        safeFind(By.id("field-address1")).sendKeys("sample address 1/23/4");
        safeFind(By.id("field-postcode")).sendKeys("69-420");
        safeFind(By.id("field-city")).sendKeys("SampleText");
        safeClick(By.name("confirm-addresses"));
        //delivery
        safeClick(By.id("delivery_option_5"));
        safeFind(By.id("delivery_message")).sendKeys("nie ma tu easter eggów, spokojnie :)");
        safeClick(By.name("confirmDeliveryOption"));
        //payment
        safeClick(By.id("payment-option-2"));
        safeClick(By.id("conditions_to_approve[terms-and-conditions]"));
        safeClick(By.xpath("//div[@id=\"payment-confirmation\"]/div[@class=\"ps-shown-by-js\"]/button"));

    }

    private void checkShipmentStatus() throws InterruptedException {
        safeClick(By.xpath("//div[@id=\"_desktop_user_info\"]/div/a[@class=\"account\"]"));
        safeClick(By.xpath("//a[@id=\"history-link\"]"));
        safeClick(By.xpath("//a[@data-link-action=\"view-order-details\"]"));
    }

}
