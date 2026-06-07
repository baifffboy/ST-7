package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Task2 {
    
    public static String getIpAddress(WebDriver webDriver) {
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            
            WebElement preElement = webDriver.findElement(By.tagName("pre"));
            String jsonString = preElement.getText();
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            
            return (String) jsonObject.get("ip");
            
        } catch (Exception e) {
            System.out.println("Ошибка в Task2: " + e.getMessage());
            return null;
        }
    }
}