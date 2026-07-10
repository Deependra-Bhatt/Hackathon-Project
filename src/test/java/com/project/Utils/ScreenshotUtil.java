package com.project.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static void capture(WebDriver driver,String fileName)throws IOException {
    	Date date = new Date();

        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File destination = new File("Screenshots/"+ fileName+ date.getDate()+ date.getTime() + ".png");
        
        destination.getParentFile().mkdirs();
        Files.copy(source.toPath(), destination.toPath());
    }
}