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

public class AppTest 
{		

    AndroidDriver driver;

    private final String EditTextId = "txtUrl";

    private final String ButtonId = "btnLoadImg";

    private final String JsonUrl = "https://raw.githubusercontent.com/djbb7/stupidjson/master/photos.json";

    @BeforeClass
    public void InstallAPK() throws IOException {
      File f = new File("/");
		File fs = new File(f, "/submission/user/application.apk");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("deviceName", "Android");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test(description="Passes a JSON with a list of images and verifies that they are rendered in a ListView (Image and Author).")
    public void ImageExerciseTest() throws IOException, InterruptedException {
      String currId = null;
      
      try { 
         // pass JSON URL
         currId = EditTextId;
		   driver.findElement(By.id(EditTextId)).sendKeys(JsonUrl);

         // press 'load' button
         currId = ButtonId;
		   TouchAction t = new TouchAction(driver);
		   t.tap(driver.findElement(By.id(ButtonId))).perform();

         // allow some time to download images
		   Thread.sleep(3000L);
		   Assert.assertEquals("Toby Keller", driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Toby Keller\"));").getText());
		      Assert.assertEquals("Luigi Alesi", driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Luigi Alesi\"));").getText());	
       }  catch (NoSuchElementException e) {
            Assert.fail("Could not find element with id '"+currId+"'. Please check the assignment instruction and use the provided View ids.");
       } catch (Exception e) {
         Assert.fail(e.toString());
       }
   }

   @AfterSuite
	public void closeDriver() {      
      driver.quit();
   }
}
