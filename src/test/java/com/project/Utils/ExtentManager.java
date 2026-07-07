package com.project.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports createInstance() {
        if (extent == null) {
            // Set report storage path
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("Reports/ExtentReport.html");
            
            // Dashboard configurations
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Urban Ladder Automation Report");
            sparkReporter.config().setReportName("Regression Suite Results");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // System Environment Metadatas
            extent.setSystemInfo("Project Name", "Urban Ladder");
            extent.setSystemInfo("Environment", "QA Production-Mirror");
            extent.setSystemInfo("User", "Automation Engineer");
        }
        return extent;
    }
}