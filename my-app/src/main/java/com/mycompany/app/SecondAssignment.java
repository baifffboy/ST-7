package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SecondAssignment {
    private static final String WEB_DRIVER_LOCATION = "D:/Dwlnds/chromedriver-win64/chromedriver-win64/chromedriver.exe";
    private static final String BROWSER_EXE_PATH = "D:/Dwlnds/chrome-win64/chrome-win64/chrome.exe";

    public static void execute() {
        System.setProperty("webdriver.chrome.driver", WEB_DRIVER_LOCATION);

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setBinary(BROWSER_EXE_PATH);

        WebDriver browserInstance = new ChromeDriver(browserOptions);

        try {
            browserInstance.get("https://api.ipify.org/?format=json");
            Thread.sleep(1000);

            WebElement rawContent = browserInstance.findElement(By.tagName("pre"));
            String jsonData = rawContent.getText();

            JSONParser jsonParser = new JSONParser();
            JSONObject parsedObject = (JSONObject) jsonParser.parse(jsonData);
            String userAddress = (String) parsedObject.get("ip");

        } catch (Exception e) {
            System.out.println("Error in Task 2: " + e.getMessage());
            e.printStackTrace();
        } finally {
            browserInstance.quit();
        }
    }
}
