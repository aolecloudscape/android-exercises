package MobileCloudComputing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterSuite;
import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.lang.System;
import org.openqa.selenium.NoSuchElementException;

public class AppTest 
{		
	
   AndroidDriver driver;

   private final String EditTextId = "txtInput";

   private final String ButtonId = "btnCreateBarcode";

    @BeforeClass
    public void InstallAPK() throws IOException {
    	File f = new File("/");
		File fs = new File(f, "/submission/user/application.apk");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("deviceName", "Android");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
		cap.setCapability("resetKeyboard", true);
		cap.setCapability("unicodeKeyboard", true);
		driver = new AndroidDriver(new URL(System.getenv("APPIUM_SERVER")+"/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   }
 
    @Test(description="Enter a text into the form and verify that the QR contains exactly that information.")
    public void QRCodeTest() throws IOException, InterruptedException, NotFoundException {

      String currId = null;
      
      try {
         // generate value to encode in QR code
         Random rand = new Random();
         int n = rand.nextInt(25000) + 1;
         currId = EditTextId;
         driver.findElement(By.id(EditTextId)).sendKeys(""+n);

         // press button
		   TouchAction t = new TouchAction(driver);
         currId = ButtonId;
         t.tap(driver.findElement(By.id(ButtonId))).perform();

         // give time to generate QR code
		   Thread.sleep(5000L);

         // take screenshot
	      String filePath = "/barcode.png";
		   Process p = Runtime.getRuntime().exec("scrot "+filePath);
         p.waitFor();

         // decode QR code contained in screenshot
	      String charset = "UTF-8";
	      Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
         String foundText = BarcodeTextExtract.readQRCode(filePath, charset, hintMap);
         Assert.assertEquals(foundText,""+n, "The produced QR code does not match the entered text. Expected '"+n+"', Found '"+foundText+"'.");

      } catch (NoSuchElementException e) {
         Assert.fail("Could not find element with id '"+currId+"'. Please check the assignment instruction and use the provided View ids.");
      } catch (com.google.zxing.NotFoundException e) {
         Assert.fail("Could not find a QR code in the screen. Please make sure it is rendering in less than 5 seconds and is completely visible in the screen without scrolling.");
      } catch (Exception e) {
         Assert.fail(e.toString());
      }

    }

   @AfterSuite
	public void closeDriver() {      
      driver.quit();
   }

}
