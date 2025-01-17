package MobileCloudComputing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class BarcodeTextExtract {
	//https://stackoverflow.com/questions/36210537/find-qr-code-in-image-and-decode-it-using-zxing/36286748#36286748
	public static String readQRCode(String filePath, String charset, Map hintMap)
		      throws FileNotFoundException, IOException, NotFoundException {
		    BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
		        new BufferedImageLuminanceSource(
		            ImageIO.read(new FileInputStream(filePath)))));
		    Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
		    return qrCodeResult.getText();
		  }

}
