/**
 * 
 */
package com.ringmd.weatherapp.Util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ringmd.weatherapp.Fragment.IndeterminateFragment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * @author Hemanth
 *
 */
public class MUtil {

	public static final String KEY_TAG = "KEY_TAG";
	public static final String KEY_CANCELABLE = "KEY_CANCELABLE";
	public static final String KEY_TITLE = "KEY_TITLE";
	public static final String KEY_ID = "KEY_ID";
	public static final String KEY_USER_ID = "KEY_USER_ID";
	public static final String KEY_NAME = "KEY_NAME";
	public static final String KEY_AVATAR = "KEY_AVATAR";
	public static final String KEY_MESSAGE = "KEY_MESSAGE";
	public static final String KEY_DIALOG_TAG = "KEY_DIALOG_TAG";
	public static final String KEY_CAN_CANCEL_OUTSIDE = "KEY_CAN_CANCEL_OUTSIDE";
	public static final String KEY_TEXT_POSITIVE_BUTTON = "KEY_TEXT_POSITIVE_BUTTON";
	public static final String KEY_TEXT_NEGATIVE_BUTTON = "KEY_TEXT_NEGATIVE_BUTTON";
	public static final String KEY_SERIALIZABLE = "KEY_SERIALIZABLE";
	public static final String KEY_JSON = "KEY_JSON";
	public static final String KEY_SERIALIZABLE_ARRAY_LIST = "KEY_SERIALIZABLE_ARRAY_LIST";
	public static final String KEY_FILE_PATH = "KEY_FILE_PATH";
	public static final String KEY_SUCCESS = "KEY_SUCCESS";
	public static final String KEY_PATH = "KEY_PATH";
	public static final String KEY_EXIT = "KEY_EXIT";
	public static final String KEY_USER_STARTED = "KEY_USER_STARTED";
	public static final String KEY_LOC_LATI = "KEY_LOC_LATI";
	public static final String KEY_LOC_LNG = "KEY_LOC_LNG";

	public static final String TAG_DIALOG_LOGOUT = "DIALOG_LOGOUT";

	private final static String ACTION_QUESTION_REPLY = ".question_reply";


	/**
	 * Checks if a network connection is available.
	 * 
	 */
	public static boolean isNetworkAvailable(final Context context) {
		final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	public static String convertDate(Date date) {
		SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd, HH:mm", Locale.getDefault());
		return simpleformat.format(date);
	}

	public static String convertDateForConservation(Date date) {
		SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yy, hh:mm aa", Locale.getDefault());
		return simpleformat.format(date);
	}

	public static byte[] readFileRaw(String path) {
		String filepath=compressImage(path);
		File file = new File(filepath);
		int size = (int) file.length();
		byte[] bytes = new byte[size];
		try {
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
			buf.read(bytes, 0, bytes.length);
			buf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return bytes;
	}
	public static String getFilename() {
	    File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
	    if (!file.exists()) {
	        file.mkdirs();
	    }
	    String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
	    return uriSting;
	 
	}
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	 
	    if (height > reqHeight || width > reqWidth) {
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }       final float totalPixels = width * height;       final float totalReqPixelsCap = reqWidth * reqHeight * 2;       while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
	        inSampleSize++;
	    }
	 
	    return inSampleSize;
	}
	
	 public static String compressImage(String imageUri) {
		 
	        String filePath = imageUri;
	        Bitmap scaledBitmap = null;
	 
	        BitmapFactory.Options options = new BitmapFactory.Options();
	 
//	      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//	      you try the use the bitmap here, you will get null.
	        options.inJustDecodeBounds = true;
	        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
	 
	        int actualHeight = options.outHeight;
	        int actualWidth = options.outWidth;
	 
//	      max Height and width values of the compressed image is taken as 816x612
	 
	        float maxHeight = 816.0f;
	        float maxWidth = 612.0f;
	        float imgRatio = actualWidth / actualHeight;
	        float maxRatio = maxWidth / maxHeight;
	 
//	      width and height values are set maintaining the aspect ratio of the image
	 
	        if (actualHeight > maxHeight || actualWidth > maxWidth) {
	            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
	                imgRatio = maxWidth / actualWidth;
	                actualHeight = (int) (imgRatio * actualHeight);
	                actualWidth = (int) maxWidth;
	            } else {
	                actualHeight = (int) maxHeight;
	                actualWidth = (int) maxWidth;
	 
	            }
	        }
	 
//	      setting inSampleSize value allows to load a scaled down version of the original image
	 
	        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
	 
//	      inJustDecodeBounds set to false to load the actual bitmap
	        options.inJustDecodeBounds = false;
	 
//	      this options allow android to claim the bitmap memory if it runs low on memory
	        options.inPurgeable = true;
	        options.inInputShareable = true;
	        options.inTempStorage = new byte[16 * 1024];
	 
	        try {
//	          load the bitmap from its path
	            bmp = BitmapFactory.decodeFile(filePath, options);
	        } catch (OutOfMemoryError exception) {
	            exception.printStackTrace();
	 
	        }
	        try {
	            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
	        } catch (OutOfMemoryError exception) {
	            exception.printStackTrace();
	        }
	 
	        float ratioX = actualWidth / (float) options.outWidth;
	        float ratioY = actualHeight / (float) options.outHeight;
	        float middleX = actualWidth / 2.0f;
	        float middleY = actualHeight / 2.0f;
	 
	        Matrix scaleMatrix = new Matrix();
	        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
	 
	        Canvas canvas = new Canvas(scaledBitmap);
	        canvas.setMatrix(scaleMatrix);
	        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
	 
//	      check the rotation of the image and display it properly
	        ExifInterface exif;
	        try {
	            exif = new ExifInterface(filePath);
	 
	            int orientation = exif.getAttributeInt(
	                    ExifInterface.TAG_ORIENTATION, 0);
	            Log.d("EXIF", "Exif: " + orientation);
	            Matrix matrix = new Matrix();
	            if (orientation == 6) {
	                matrix.postRotate(90);
	                Log.d("EXIF", "Exif: " + orientation);
	            } else if (orientation == 3) {
	                matrix.postRotate(180);
	                Log.d("EXIF", "Exif: " + orientation);
	            } else if (orientation == 8) {
	                matrix.postRotate(270);
	                Log.d("EXIF", "Exif: " + orientation);
	            }
	            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
						scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
						true);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        FileOutputStream out = null;
	        String filename = getFilename();
	        try {
	            out = new FileOutputStream(filename);
	 
//	          write the compressed bitmap at the destination specified by filename.
	            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
	 
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	 
	        return filename;
	 
	    }	

	public static String getFileName(String path) {
		File file = new File(path);
		return file.getName();
	}


	public static String getQuestionReplyAction(Context context) {
		return context.getPackageName() + ACTION_QUESTION_REPLY;
	}

	public static void hideKeyboard(Context context, EditText myEditText) {
		InputMethodManager imm = (InputMethodManager)context. getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
	}

	public static boolean isValidPhoneNumber(String no) {
		if(no.length() < 6 || no.length() > 15) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void dismissProgressBar(FragmentActivity fragmentActivity, String TAG) {
		IndeterminateFragment.dismiss(fragmentActivity, TAG);
	}

	public static void showProgressBar(FragmentActivity fragmentActivity, String TAG) {
		IndeterminateFragment.show(fragmentActivity, TAG, true);
	}

	public static void showProgressBar(FragmentActivity fragmentActivity, String TAG, boolean isCancelable) {
		IndeterminateFragment.show(fragmentActivity, TAG, isCancelable);
	}



	public static void clearCache() {
		try {
			System.gc();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String getLocationUpdateAction(Context context) {
		return context.getPackageName() + ".loc_update";
	}

	public static String getTimeTag() {
		return ""+ System.currentTimeMillis();
	}

	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public static String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try { 
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static boolean isHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/** used for PopupMenu.setOnDismissListener */
	public static boolean isIceCreamSandwich() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}


	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	public static Bitmap fixOrientation(Bitmap mBitmap, int degree) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		//create new rotated bitmap
		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
		return mBitmap;
	}

	public static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognise a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}

			}
		}

		return degree;
	}

}
