package com.project.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static void capture(WebDriver driver, String fileName) throws IOException {
        // 1. A readable timestamp format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(formatter);

        // 2. Capture the screenshot
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        // 3. Constructing the destination path with the formatted timestamp
        File destination = new File("Screenshots/" + fileName + "_" + timestamp + ".png");
        
        // 4. Create directories if they don't exist and copy the file
        if (destination.getParentFile() != null) {
            destination.getParentFile().mkdirs();
        }
        Files.copy(source.toPath(), destination.toPath());
    }
}