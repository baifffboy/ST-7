package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class App {
    private static final String CHROME_DRIVER_PATH = "D:/Dwlnds/chromedriver-win64/chromedriver-win64/chromedriver.exe";
    private static final String CHROME_BINARY_PATH = "D:/Dwlnds/chrome-win64/chrome-win64/chrome.exe";

    public static void main(String[] args) {
        parsePassword();
        Task2.run();
        Task3.run();
    }

    public static void parsePassword() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(CHROME_BINARY_PATH);
        options.addArguments("--log-level=3");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.calculator.net/password-generator.html");

            Thread.sleep(2000);

            WebElement passwordElement = driver.findElement(By.cssSelector("div.verybigtext"));
            String password = passwordElement.getText();

            password = password.replaceAll("<[^>]*>", "").trim();

            System.out.println("=== Задание 1 ===");
            System.out.println("Сгенерированный пароль: " + password);

        } catch (Exception e) {
            System.out.println("Error in Task 1: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}