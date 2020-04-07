package MobileCloudComputing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterSuite;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.util.Random;
import java.lang.System;
import java.lang.Exception;

public class AppTest 
{		

   AndroidDriver driver;

   private final String EditTextId = "txtInput";

   private final String ButtonId = "btnSubmit";

   private final String TextViewId = "txtResult";

   @BeforeClass
   public void InstallAPK() throws IOException {
    	File f = new File("/");
		File fs = new File(f, "/submission/user/application.apk");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("newCommandTimeout", 6001);
		cap.setCapability("appWaitDuration", 6000002 );
		cap.setCapability("deviceReadyTimeout", 6003);
		cap.setCapability("androidDeviceReadyTimeout", 6004);
		cap.setCapability("androidInstallTimeout", 4000005);
		cap.setCapability("avdLaunchTimeout", 4000006);
      cap.setCapability("autoWebviewTimeout", 600007);
		cap.setCapability("deviceName", "Android");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
		driver = new AndroidDriver(new URL(System.getenv("APPIUM_SERVER")+"/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   }

    @Test(description="Enter an input text and check that it is appended to the TextView.")
    public void HelloUserTest() throws IOException, InterruptedException {
      String currId = null;
      try { 
         // input value into EditText
         Random rand = new Random();
         int n = rand.nextInt(25000) + 1; 
         currId = EditTextId;
         driver.findElement(By.id(EditTextId)).sendKeys(""+n);

         // press button
		   TouchAction t = new TouchAction(driver);
         currId = ButtonId;
		   t.tap(driver.findElement(By.id(ButtonId))).perform();

         // check input text is appended to result
         currId = TextViewId;
		   Assert.assertEquals("Hello "+n, driver.findElement(By.id(TextViewId)).getText(), "The TextView contains the wrong text.");

      } catch (NoSuchElementException e) {
         Assert.fail("Could not find element with id '"+currId+"'. Please check the assignment instruction and use the provided View ids.");
      } catch (Exception e) {
         Assert.fail(e.getMessage());
      }
    }

   @AfterSuite
	public void closeDriver() {      
      driver.quit();
   }

}

