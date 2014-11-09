package com.codeepy.adbeacon.app.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import com.codeepy.adbeacon.app.factory.JsonFactory;
import com.codeepy.adbeacon.app.factory.WebServiceFactory;
import com.codeepy.adbeacon.app.factory.WebServiceURLFactory;
import com.codeepy.adbeacon.app.model.Advertisement;
import com.codeepy.adbeacon.app.webservice.AdWebService;
import com.codeepy.adbeacon.app.webservice.WebService;

public class Utils {

	private Context _context;
    private WebServiceFactory webServiceFactory;

    public static String MACAddress;

	// constructor
	public Utils(Context context) {
		this._context = context;
	}

	/*
	 * Reading file paths from SDCard
	 */
	public ArrayList<String> getFilePaths() {
		ArrayList<String> filePaths = new ArrayList<String>();

		File directory = new File(
				android.os.Environment.getExternalStorageDirectory()
						+ File.separator + AppConstant.PHOTO_ALBUM);

        try {
            // check for directory
            if (directory.isDirectory()) {
                // getting list of file paths
                File[] listFiles = directory.listFiles();

                // Check for count
                if (listFiles.length > 0) {

                    // loop through all files
                    for (int i = 0; i < listFiles.length; i++) {

                        // get file path
                        String filePath = listFiles[i].getAbsolutePath();

                        // check for supported file extension
                        if (IsSupportedFile(filePath)) {
                            // Add image path to array list
                            filePaths.add(filePath);
                        }
                    }
                } else {
                    // image directory is empty
                    Toast.makeText(
                            _context,
                            AppConstant.PHOTO_ALBUM
                                    + " is empty. Please load some images in it !",
                            Toast.LENGTH_LONG).show();
                }

            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("Error!");
                alert.setMessage(AppConstant.PHOTO_ALBUM
                        + " directory path is not valid! Please set the image directory name AppConstant.java class");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
        }
        catch (Exception ex) {
            Log.e(Codeepy.TAG.toString(), ex.getMessage());
        }

		return filePaths;
	}

    public ArrayList<String> getFileUrls(String UUID) {
        String rootUrl = "http://adbeacon.herokuapp.com/media/";
        ArrayList<String> fileUrls = new ArrayList<String>();

        webServiceFactory = new WebServiceFactory();

        AdWebService adws = new AdWebService();
        adws.setUUID(UUID);
        adws.setFormat(WebService.FORMAT_JSON);
        String url = WebServiceURLFactory.getInstance().buildUri(adws);
        webServiceFactory.execute(url);
        try {
            String json = webServiceFactory.get();
            List<Advertisement> ads = JsonFactory.getInstance().handleAdvertisement(json);

            for (Advertisement ad : ads) {
                fileUrls.add(rootUrl + ad.getPicurl());
            }
        } catch (InterruptedException e) {
            Log.e(Codeepy.TAG.toString(), e.getMessage());
        } catch (ExecutionException e) {
            Log.e(Codeepy.TAG.toString(), e.getMessage());
        }

        return fileUrls;
    }
	/*
	 * Check supported file extensions
	 * 
	 * @returns boolean
	 */
	private boolean IsSupportedFile(String filePath) {
		String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
				filePath.length());

		if (AppConstant.FILE_EXTN
				.contains(ext.toLowerCase(Locale.getDefault())))
			return true;
		else
			return false;

	}

	/*
	 * getting screen width
	 */
	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) _context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;
	}
}
