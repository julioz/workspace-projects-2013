package br.com.zynger.brasileirao2012.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import br.com.zynger.brasileirao2012.enums.Division;

public abstract class FileHandler {	
	public static final String TORCIDOMETER_SENT_CLUB = "bras2013_torcidometer_sent_club";

	public static final String JSON_APROV = "bras2013_json_aprov";
	public static final String JSON_RANKING = "bras2013_json_ranking";
	public static final String JSON_MATCHES = "bras2013_json_matches_";
	public static final String JSON_STRIKERS = "bras2013_json_strikers";
	public static final String JSON_FANS = "bras2013_json_fans";
	public static final String JSON_THIRDDIVISIONRANKING = "bras2013_json_thirddivisionranking";

	public static void saveBackup(Context c, String fileName, Object obj){
		try {
			FileOutputStream out = c.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (StackOverflowError e) {
			e.printStackTrace();
		}
	}
	
	public static boolean deleteBackup(Context context, String fileName){
		File dir = context.getFilesDir();
		File file = new File(dir, fileName);
		return file.delete();
	}
	
	public static void saveBackup(Context c, String fileName, Object obj, Division division){
		saveBackup(c, fileName + division.getNotation().toLowerCase(Locale.getDefault()), obj);
	}
	
	public static Object openBackup(Context c, String fileName, Division division){
		return openBackup(c, fileName + division.getNotation().toLowerCase(Locale.getDefault()));
	}
	
	public static Object openBackup(Context context, String fileName){
		if(fileName.equals(JSON_MATCHES)){
			throw new RuntimeException("You cannot try to open matches json without specifying a Division");
		}
		
		try {
			FileInputStream fis = context.openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);
			Object obj = is.readObject();
			is.close();
			return obj;
		} catch (FileNotFoundException e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch (StreamCorruptedException e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch (IOException e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch(OutOfMemoryError e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch(ClassCastException e) {
			/* deserializacao de uma classe serializada numa versao antiga e que foi modificada causa esta exception - inconsistencia das estruturas */
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch(StackOverflowError e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch(NullPointerException e){
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch(NegativeArraySizeException e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch(ArrayIndexOutOfBoundsException e) {
			/* se ocorrer falta de memoria, o indice do array a ser desserializado pode ver como lixo de memoria e causar esta exception */
			Log.i(Constants.TAG, e.toString());
			return null;
		} catch(Exception e) {
			Log.i(Constants.TAG, e.toString());
			return null;
		}
	}
	
}