package com.project.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static void capture(WebDriver driver,String fileName)throws IOException {

        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File destination = new File("Screenshots/"+ fileName+ ".png");
        
        destination.getParentFile().mkdirs();
        Files.copy(source.toPath(), destination.toPath());
    }
}