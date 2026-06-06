package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.io.FileWriter;
import java.io.PrintWriter;

public class App {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ilya_\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();
        String generatedPassword = "";
        String ipAddress = "";
        String forecastTable = "";

        try {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Задание №1: Генерация пароля");
            System.out.println("=".repeat(60));
            
            webDriver.get("https://www.calculator.net/password-generator.html");

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

            WebElement passwordBlock = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.verybigtext b"))
            );
            generatedPassword = passwordBlock.getText();
            System.out.println("Сгенерированный пароль: " + generatedPassword);
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Задание №2: Получение IP-адреса");
            System.out.println("=".repeat(60));
            
            ipAddress = Task2.getIpAddress(webDriver);
            System.out.println("Ваш IP-адрес: " + ipAddress);
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Задание №3: Прогноз погоды");
            System.out.println("=".repeat(60));
            
            forecastTable = Task3.getWeatherForecast(webDriver);

            saveAllResults(generatedPassword, ipAddress, forecastTable);

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
    
    private static void saveAllResults(String password, String ip, String forecastTable) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("../result/README.md"))) {
            writer.println("# Результаты выполнения задания ST-7\n");
            writer.println("## Задание №1: Генерация пароля\n");
            writer.println("Сгенерированный пароль: **" + password + "**\n");
            writer.println("Настройки: длина 16, заглавные, строчные, цифры, спецсимволы\n");
            writer.println("## Задание №2: Получение IP-адреса\n");
            writer.println("Ваш IP-адрес: **" + ip + "**\n");
            writer.println("## Задание №3: Прогноз погоды для Нижнего Новгорода (56, 44)\n");
            writer.println(forecastTable);
            System.out.println("\nРезультаты сохранены в файл: ../result/README.md");
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении результатов: " + e.getMessage());
        }
    }
}
