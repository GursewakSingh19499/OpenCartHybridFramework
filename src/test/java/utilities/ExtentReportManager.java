package utilities;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;


public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkReporter;	//UI of the report
	public ExtentReports extent;	//populate common info of the report
	public ExtentTest test;	//creating test case entries in report and update status of test methods, pass/fail test cases, screen shots
	
	String repName;
	
	public void onStart(ITestContext context) {
		
		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); 
		 * Date dt = new Date();
		 * String currentDateTimeStamp = df.format(dt);
		 */
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + " .html";	//dynamic report name so that every time when test runs, new file is created and old file is present
	    
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+ repName);
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("OpenCart Funcitonal testing");
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent = new ExtentReports();
		
		extent.attachReporter(sparkReporter);	//important
		
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub-Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = context.getCurrentXmlTest().getParameter("os");		//form XML file get os parameter value
		extent.setSystemInfo("Operating System", os);
		
		String browser = context.getCurrentXmlTest().getParameter("browser");		//form XML file get browser parameter value
		extent.setSystemInfo("Browser", browser);
		
		List<String> groups = context.getCurrentXmlTest().getIncludedGroups();	//check all available groups
		if(!groups.isEmpty()) {
			extent.setSystemInfo("Groups", groups.toString());
		}
		
	  }
	
	public void onTestSuccess(ITestResult result) {
	    
		test = extent.createTest(result.getTestClass().getName());		//create entry in the report, with class name
		test.assignCategory(result.getMethod().getGroups());	//get @test method belongs to which groups
		test.log(Status.PASS, result.getName()+" got successfully Executed");	//update status of test case
	  }
	
	public void onTestFailure(ITestResult result) {
	    
		test = extent.createTest(result.getTestClass().getName());		//create entry in the report, with class name
		test.assignCategory(result.getMethod().getGroups());	//get @test method belongs to which groups
		
		test.log(Status.FAIL, result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());	//get error in report
		
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	  }
	
	public void onTestSkipped(ITestResult result) {
	    
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	  }
	
	public void onFinish(ITestContext context) {
	    
		extent.flush();
		
		//code to automatically open report file
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}catch(IOException e) {
			e.printStackTrace();
		}
	  }

}
