package org.rupesh.demo.extentReports;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import java.awt.image.RenderedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
//import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import javax.print.attribute.standard.DateTimeAtCreation;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.bcel.generic.NEWARRAY;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.KlovReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsPlainRunner {
	public static void main(String[] args) throws IOException, AddressException, MessagingException {

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Simple Extent Reports");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Demo on Simple Extent Reports");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		String chromeEXEPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeEXEPath);

		String testReporterDirectoryPath = System.getProperty("user.dir") + "\\TestReporter";
		File testDirectory = new File(testReporterDirectoryPath);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + testReporterDirectoryPath + " is created!");
			} else {
				System.out.println("Failed to create directory: " + testReporterDirectoryPath);
			}
		} else {
			System.out.println("Directory already exists: " + testReporterDirectoryPath);
		}

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

		System.out.println("Current Date: " + ft.format(dNow));
		String testReporterInsideDirectoryPath = System.getProperty("user.dir") + "\\TestReporter\\"
				+ ft.format(dNow).toString();
		String x = testReporterInsideDirectoryPath;
		File testDirectoryInside = new File(testReporterInsideDirectoryPath);
		if (!testDirectoryInside.exists()) {
			if (testDirectoryInside.mkdir()) {
				System.out.println("Directory: " + testReporterInsideDirectoryPath + " is created!");
			} else {
				System.out.println("Failed to create directory: " + testReporterInsideDirectoryPath);
			}
		} else {
			System.out.println("Directory already exists: " + testReporterInsideDirectoryPath);
		}

		ExtentTest test = extent.createTest("Sample Test 1",
				"This demo will gives you the advanced usage of extent reports with gif besides png or jpg or jpeg!");
		test.assignAuthor("Rupesh Kumar Somala");
		test.assignCategory("Demo");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		test.pass("Browser Instantiated Successfully.", MediaEntityBuilder
				.createScreenCaptureFromPath(takeScreenshot(driver, testReporterInsideDirectoryPath, "1")).build());

		test.info("The credentails that we will provide are as follows:");
		String[][] operationalTableDataForLogin = { { "username", "password" }, { "rupesh", "rupesh" } };
		test.info(MarkupHelper.createTable(operationalTableDataForLogin));

		driver.navigate().to("http://executeautomation.com/demosite/Login.html");

		test.pass("Browser Navigated Successfully.", MediaEntityBuilder
				.createScreenCaptureFromPath(takeScreenshot(driver, testReporterInsideDirectoryPath, "2")).build());

		driver.findElement(By.name("UserName")).sendKeys("rupesh");

		test.pass("UserName Provided Successfully.", MediaEntityBuilder
				.createScreenCaptureFromPath(takeScreenshot(driver, testReporterInsideDirectoryPath, "3")).build());

		driver.findElement(By.name("Password")).sendKeys("rupesh");

		test.pass("Password Provided Successfully.", MediaEntityBuilder
				.createScreenCaptureFromPath(takeScreenshot(driver, testReporterInsideDirectoryPath, "4")).build());

		driver.findElement(By.name("Login")).submit();

		test.info("The data that we will provide are as follows:");
		String[][] operationalTableDataForSaving = { { "Initial", "FirstName", "MiddleName" },
				{ "S", "rupesh", "kumar" } };
		test.info(MarkupHelper.createTable(operationalTableDataForSaving));

		driver.findElement(By.name("Initial")).sendKeys("S");

		test.pass("Initial Provided Successfully.", MediaEntityBuilder
				.createScreenCaptureFromPath(takeScreenshot(driver, testReporterInsideDirectoryPath, "5")).build());

		driver.findElement(By.name("FirstName")).sendKeys("rupesh");

		test.pass("FirstName Provided Successfully.", MediaEntityBuilder
				.createScreenCaptureFromPath(takeScreenshot(driver, testReporterInsideDirectoryPath, "6")).build());

		driver.findElement(By.name("MiddleName")).sendKeys("kumar");
		test.pass("MiddleName Provided Successfully.", MediaEntityBuilder
				.createScreenCaptureFromPath(takeScreenshot(driver, testReporterInsideDirectoryPath, "7")).build());

		driver.quit();

		test.info("Entire Test passed",
				MediaEntityBuilder.createScreenCaptureFromPath(createGIF(testReporterInsideDirectoryPath)).build());
		extent.flush();

		new EmailTransmitter().startTransmission();
	}

	public static String takeScreenshot(WebDriver driver, String testReporterInsideDirectoryPath, String fileName)
			throws IOException {
		String intialFileName = testReporterInsideDirectoryPath + "_" + fileName + ".png";
		String temp = testReporterInsideDirectoryPath.replace(System.getProperty("user.dir") + "\\TestReporter\\", "");

		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceFile, new File(intialFileName));
		FileUtils.moveFileToDirectory(new File(intialFileName), new File(testReporterInsideDirectoryPath), false);
		System.out.println("screenshot generated outside but moved into the corresponding folder");

		String fileNamePREChange = testReporterInsideDirectoryPath + "\\"
				+ testReporterInsideDirectoryPath.replace(System.getProperty("user.dir") + "\\TestReporter\\", "") + "_"
				+ fileName + ".png";

		String fileNamePOSTChange = testReporterInsideDirectoryPath
				+ "\\" + testReporterInsideDirectoryPath
						.replace(System.getProperty("user.dir") + "\\TestReporter\\", "").replace(temp, "")
				+ fileName + ".png";
		;

		System.out.println("intialFileName :" + intialFileName);
		System.out.println("fileNamePREChange :" + fileNamePREChange);
		System.out.println("fileNamePOSTChange :" + fileNamePOSTChange);

		File filePREChange = new File(fileNamePREChange);
		File filePOSTChange = new File(fileNamePOSTChange);
		filePREChange.renameTo(filePOSTChange);
		System.out.println(fileNamePREChange + " renamed to " + fileNamePOSTChange);

		// byte[] fileContent = FileUtils.readFileToByteArray(new
		// File(fileNamePOSTChange));
		// String encodedString = Base64.getEncoder().encodeToString(fileContent);
		// return encodedString;
		// return pngTogif(fileNamePOSTChange);
		// return "data:image/png;base64," + encodedString;

		// Conversion of image to base64
		// String encodedBase64 = null;
		// FileInputStream fileInputStreamReader = null;
		/*
		 * try { fileInputStreamReader = new FileInputStream(filePOSTChange); byte[]
		 * bytes = new byte[(int)filePOSTChange.length()];
		 * fileInputStreamReader.read(bytes); encodedBase64 = new
		 * String(Base64.encodeBase64(bytes)); } catch (FileNotFoundException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } return
		 * "data:image/png;base64,"+encodedBase64;
		 */
		return fileNamePOSTChange;

	}

	public static String createGIF(String folderPath) throws IOException {
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".png")) {
					return true;
				} else {
					return false;
				}
			}
		};
		File[] filesList = new File(folderPath).listFiles(textFilter);
		BufferedImage first = ImageIO.read(filesList[0]);
		String gifName = folderPath + "\\Timelapse.gif";
		ImageOutputStream output = new FileImageOutputStream(new File(gifName));
		GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 250, true);
		writer.writeToSequence(first);
		for (File f : filesList) {
			BufferedImage next = ImageIO.read(f);
			writer.writeToSequence(next);
		}
		writer.close();
		output.close();

		return gifName;
	}

	public static String pngTogif(String pngFilePath) throws IOException {
		File pngFile = new File(pngFilePath);
		BufferedImage first = ImageIO.read(pngFile);
		System.out.print(pngFilePath + " is converted to ");
		String gifName = pngFilePath.replace(".png", ".gif");
		ImageOutputStream output = new FileImageOutputStream(new File(gifName));
		GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 250, true);
		writer.writeToSequence(first);
		writer.close();
		output.close();
		System.out.println(gifName);
		return gifName;
	}

}
