package MobileCloudComputing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterSuite;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppTest 
{		

   private final String PickPhotoButtonId = "btnPickPhoto";

   private final String TextViewPersonsId = "txtNumPeople";

   private final String TextViewBarcodeId = "txtBarcode";

   AndroidDriver driver;

   TouchAction t;

    @BeforeClass
    public void InstallAPK() throws IOException {
    	File f = new File("/");
		File fs = new File(f, "/submission/user/application.apk");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("deviceName", "Android");
		cap.setCapability("newCommandTimeout", 6001);
		cap.setCapability("appWaitDuration", 6000002 );
		cap.setCapability("deviceReadyTimeout", 6003);
		cap.setCapability("androidDeviceReadyTimeout", 6004);
		cap.setCapability("androidInstallTimeout", 4000005);
		cap.setCapability("avdLaunchTimeout", 4000006);
      cap.setCapability("autoWebviewTimeout", 600007);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

      t = new TouchAction(driver);   
    }


    @Test(description="Selfie photo")
    public void SelfieTest() throws IOException, InterruptedException {
      runTest("//*[@resource-id='fi.aalto.mcc.customgallery:id/button2']", "1", "No", 7000L);
    }

    @Test(description="QR Code photo")
    public void BarcodeTest() throws IOException, InterruptedException {
      runTest("//*[@resource-id='fi.aalto.mcc.customgallery:id/button1']", "0", "Yes", 7000L);
    }

    @Test(description="Landscape photo")
    public void LandscapeTest() throws IOException, InterruptedException {
      runTest("//*[@resource-id='fi.aalto.mcc.customgallery:id/button3']", "0", "No", 7000L);
    }

    @Test(description="Giant landscape photo. Are you handling well the memory?")
    public void LargeLandscapeTest() throws IOException, InterruptedException {  
      runTest("//*[@resource-id='fi.aalto.mcc.customgallery:id/button4']", "0", "No", 7000L);
    }

   public void runTest(String testCaseButton, String persons, String barcode, long processing) {
      driver.resetApp();

      String currId = null;
      
      try {
         // press button
         currId = PickPhotoButtonId;
         driver.findElement(By.id(PickPhotoButtonId)).click();
   
         // allow time for android gallery to open
		   Thread.sleep(1000L);

         // open test case app
         try {
            t.tap(driver.findElementByXPath("//*[@text='Test Cases']")).perform();

            // allow time for test case app to open
   		   Thread.sleep(1000L);

   		   driver.findElementByXPath(testCaseButton).click();         
         } catch (NoSuchElementException e) {
            throw new Exception("Internal Error. Could not load test case.");
         }

         // allow time for photo processing
		   Thread.sleep(processing);

         // check output
         currId = TextViewPersonsId;
  		   Assert.assertEquals(persons, driver.findElement(By.id(TextViewPersonsId)).getText());
         currId = TextViewBarcodeId;
		   Assert.assertEquals(barcode, driver.findElement(By.id(TextViewBarcodeId)).getText());

      }  catch (NoSuchElementException e) {
         Assert.fail("Could not find element with id '"+currId+"'. Please check the assignment instruction and use the provided View ids.");
      } catch (Exception e) {
         Assert.fail(e.toString());
      }
   }  
}
