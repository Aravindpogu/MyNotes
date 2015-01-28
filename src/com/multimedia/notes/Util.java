package com.multimedia.notes;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Environment;

/**
 * This is a utility class, which has all the utility methods for this app.
 * @author aravind
 *
 */
@SuppressLint("SimpleDateFormat") 
public class Util {
	
	public static String getDateString(Date date) {
		DateFormat formatter = new SimpleDateFormat(NotesConstants.DATE_FORMAT);
	    String dateTime = formatter.format(date);
	    return dateTime;
	}
	
	public static String getDateTimeString() {
		DateFormat formatter = new SimpleDateFormat(NotesConstants.DATE_TIME_FORMAT);
	    String dateTime = formatter.format(new Date());
	    return dateTime;
	}

	public static boolean isEmpty(String noteString) {
			return noteString == null || noteString.trim().equals("");
	}
	
	public static String getVideoFileName() {
			String filename = getDateTimeString().substring(NotesConstants.DATE_FORMAT.length() + 1);
			filename = filename.replace(":", "_");
			return "/video_"+ filename + ".mp4";
	}

	public static File[] getMediaFiles(String path, String fileFormat, String selectedDate) {
		File directory = Environment.getExternalStorageDirectory();
	    File file = new File( directory + path );
	    File list[] = file.listFiles();
	    File returnList[] = null;
	    if(null != list){
	    	returnList = new File[list.length];
		    String lastModified = null;
		    for( int i=0; i< list.length; i++) {
		    	lastModified = getDateString(new Date(list[i].lastModified()));
		    	if(list[i].getName().contains(fileFormat) && lastModified.equals(selectedDate)){
		    		returnList[i] = list[i];
				}
		    }
	    }
		return returnList;
	}
	
	public static File[] getVideoFiles(String selectedDate) {
		File directory = Environment.getExternalStorageDirectory();
	    File file = new File( directory + NotesConstants.NOTES_ON_GO_VIDEO_DIR );
	    File list[] = file.listFiles();
	    File returnList[] = null;
	    if(null != list){
	    	String lastModified = null;
		    returnList = new File[list.length];
		    for( int i=0; i< list.length; i++) {
		    	lastModified = getDateString(new Date(list[i].lastModified()));
		    	if(list[i].getName().contains("video_") && lastModified.equals(selectedDate)){
		    		returnList[i] = list[i];
				}
		    }
	    }
		return returnList;
	}
}

