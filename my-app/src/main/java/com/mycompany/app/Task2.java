package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Task2 {
    private static final String CHROME_DRIVER_PATH = "D:/Dwlnds/chromedriver-win64/chromedriver-win64/chromedriver.exe";
    private static final String CHROME_BINARY_PATH = "D:/Dwlnds/chrome-win64/chrome-win64/chrome.exe";

    public static void run() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(CHROME_BINARY_PATH);

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://api.ipify.org/?format=json");
            Thread.sleep(1000);

            WebElement elem = driver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            String ip = (String) obj.get("ip");

            System.out.println("\n=== Задание 2 ===");
            System.out.println("Ваш IPv4 адрес: " + ip);

        } catch (Exception e) {
            System.out.println("Error in Task 2: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}