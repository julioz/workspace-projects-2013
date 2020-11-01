package br.com.zynger.influ.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.util.Log;

public abstract class FileHandler {

	public static void saveBackup(Context c, String fileName, Object obj){
		FileOutputStream out;
		try {
			out = c.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveVersionData(Context c, String value){
		saveBackup(c, "versiondata", value);
	}
	
	public static String openVersionData(Context c){
		String ret = (String) openBackup(c, "versiondata");
		if(ret == null) return "";
		else return ret;
	}
	
	public static Object openBackup(Context c, String fileName){
		FileInputStream fis;
		try {
			fis = c.openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);
			Object obj = is.readObject();
			is.close();
			return obj;
		} catch (FileNotFoundException e) {
			Log.i("inFLU", e.toString());
			return null;
		} catch (StreamCorruptedException e) {
			Log.i("inFLU", e.toString());
			return null;
		} catch (IOException e) {
			Log.i("inFLU", e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			Log.i("inFLU", e.toString());
			return null;
		}
	}
	
}
