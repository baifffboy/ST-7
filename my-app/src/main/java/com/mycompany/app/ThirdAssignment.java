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

public class ThirdAssignment {
    private static final String WEB_DRIVER_LOCATION = "D:/Dwlnds/chromedriver-win64/chromedriver-win64/chromedriver.exe";
    private static final String BROWSER_EXE_PATH = "D:/Dwlnds/chrome-win64/chrome-win64/chrome.exe";

    public static void execute() {
        System.setProperty("webdriver.chrome.driver", WEB_DRIVER_LOCATION);

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setBinary(BROWSER_EXE_PATH);

        WebDriver browserInstance = new ChromeDriver(browserOptions);

        try {
            String weatherApiUrl = "https://api.open-meteo.com/v1/forecast" +
                    "?latitude=56&longitude=44" +
                    "&hourly=temperature_2m,rain" +
                    "&current=cloud_cover" +
                    "&timezone=Europe%2FMoscow" +
                    "&forecast_days=1" +
                    "&wind_speed_unit=ms";

            browserInstance.get(weatherApiUrl);
            Thread.sleep(1000);

            WebElement rawContent = browserInstance.findElement(By.tagName("pre"));
            String jsonData = rawContent.getText();

            JSONParser jsonParser = new JSONParser();
            JSONObject parsedObject = (JSONObject) jsonParser.parse(jsonData);
            JSONObject hourlyData = (JSONObject) parsedObject.get("hourly");

            JSONArray timeStamps = (JSONArray) hourlyData.get("time");
            JSONArray temperatureValues = (JSONArray) hourlyData.get("temperature_2m");
            JSONArray rainfallAmounts = (JSONArray) hourlyData.get("rain");

            StringBuilder outputTable = new StringBuilder();
            outputTable.append("№\tДата/время\tТемпература\tОсадки (мм)\n");
            outputTable.append("=".repeat(55)).append("\n");

            for (int idx = 0; idx < timeStamps.size(); idx++) {
                String currentTime = (String) timeStamps.get(idx);
                String currentTemp = String.valueOf(temperatureValues.get(idx));
                String currentRain = String.valueOf(rainfallAmounts.get(idx));
                outputTable.append(String.format("%2d\t%s\t%s°C\t%s\n", idx + 1, currentTime, currentTemp, currentRain));
            }

            String projectDirectory = System.getProperty("user.dir").replace("my-app", "");
            String outputFilePath = Paths.get(projectDirectory, "result", "forecast.txt").toString();

            try (PrintWriter fileWriter = new PrintWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8))) {
                fileWriter.print(outputTable.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            browserInstance.quit();
        }
    }
}
