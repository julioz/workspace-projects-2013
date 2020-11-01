package br.com.zynger.brasileirao2012.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import br.com.zynger.brasileirao2012.R;

public class Raw {
	public static String openRaw(Context context, String xmlName) {
		int raw = -1;
		
		try {
			raw = R.raw.class.getDeclaredField(xmlName).getInt(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		if(raw == -1) throw new RuntimeException("Não foi possível encontrar o xml");
		
		InputStream stream = context.getResources().openRawResource(raw);
		try {
			byte[] buffer = new byte[stream.available()];
			stream.read(buffer);
			stream.close();
			return new String(buffer);
		} catch (IOException e) {
			return null;
		}
	}
}
