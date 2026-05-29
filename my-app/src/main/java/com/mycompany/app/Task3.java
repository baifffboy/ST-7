package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class Task3 {
    private static final String CHROME_DRIVER_PATH = "D:/Dwlnds/chromedriver-win64/chromedriver-win64/chromedriver.exe";
    private static final String CHROME_BINARY_PATH = "D:/Dwlnds/chrome-win64/chrome-win64/chrome.exe";

    public static void run() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(CHROME_BINARY_PATH);

        WebDriver driver = new ChromeDriver(options);

        try {
            String url = "https://api.open-meteo.com/v1/forecast" +
                    "?latitude=56&longitude=44" +
                    "&hourly=temperature_2m,rain" +
                    "&current=cloud_cover" +
                    "&timezone=Europe%2FMoscow" +
                    "&forecast_days=1" +
                    "&wind_speed_unit=ms";

            driver.get(url);
            Thread.sleep(1000);

            WebElement elem = driver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject hourly = (JSONObject) obj.get("hourly");

            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            StringBuilder table = new StringBuilder();
            table.append("№\tДата/время\tТемпература\tОсадки (мм)\n");
            table.append("=".repeat(55)).append("\n");

            for (int i = 0; i < times.size(); i++) {
                String time = (String) times.get(i);
                String temp = String.valueOf(temps.get(i));
                String rain = String.valueOf(rains.get(i));
                table.append(String.format("%2d\t%s\t%s°C\t%s\n", i+1, time, temp, rain));
            }

            System.out.println("\n=== Задание 3: Прогноз для Н.Новгорода ===");
            System.out.println(table.toString());

            String projectRoot = System.getProperty("user.dir").replace("my-app", "");
            String filePath = Paths.get(projectRoot, "result", "forecast.txt").toString();

            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
                writer.print(table.toString());
            }
            System.out.println("Результат сохранён в: " + filePath);

        } catch (Exception e) {
            System.out.println("Error in Task 3: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}