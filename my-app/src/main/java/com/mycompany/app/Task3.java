package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Task3 {
    
    public static String getWeatherForecast(WebDriver webDriver) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
            webDriver.get(url);
            
            WebElement preElement = webDriver.findElement(By.tagName("pre"));
            String jsonString = preElement.getText();
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            
            JSONObject hourly = (JSONObject) jsonObject.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            System.out.println("\n" + "=".repeat(80));
            System.out.println("Прогноз погоды для Нижнего Новгорода (56, 44)");
            System.out.println("=".repeat(80));
            
            System.out.printf("%-4s | %-20s | %-12s | %-10s%n", "№", "Дата/время", "Температура", "Осадки (мм)");
            System.out.println("-".repeat(60));
            
            StringBuilder table = new StringBuilder();
            table.append(String.format("%-4s | %-20s | %-12s | %-10s%n", "№", "Дата/время", "Температура", "Осадки (мм)"));
            table.append("-".repeat(60)).append("\n");
            
            for (int i = 0; i < times.size(); i++) {
                String time = (String) times.get(i);
                Number temp = (Number) temperatures.get(i);
                Number rain = (Number) rains.get(i);
                
                String line = String.format("%-4d | %-20s | %-12s | %-10s%n", 
                    (i + 1), time, temp + "°C", rain + " mm");
                System.out.print(line);
                table.append(line);
            }
            
            System.out.println("=".repeat(80));
            
            return table.toString();
            
        } catch (Exception e) {
            System.out.println("Ошибка в Task3: " + e.getMessage());
            return "Ошибка получения прогноза погоды";
        }
    }
}