package br.com.site.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.site.Home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

public class DownloadApk extends AsyncTask<String, Integer, String>{

	ProgressDialog progressDialog;
	String apkurl;
	String versionName;
	Activity activity;
	
	public DownloadApk(Activity act, ProgressDialog p, String url, String versionName){
		this.progressDialog = p;
		this.apkurl = url;
		this.versionName = versionName;
		this.activity = act;
	}
	
	@Override
	protected void onPreExecute() {
		this.progressDialog.show();
		super.onPreExecute();
	}
	
	@Override
	protected String doInBackground(String... params) {
		try{
			URL url = new URL(this.apkurl);
			HttpURLConnection c = (HttpURLConnection) url.openConnection();
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.connect();
			
			int tamanho = c.getContentLength();
			
			String PATH = Environment.getExternalStorageDirectory() + "/.sistemadetratamentodeemergencias/"; //TODO da pra colocar no cache?
			File file = new File(PATH);
			file.mkdirs();
			File outputFile = new File(file, "SiTE "+this.versionName+".apk");
			FileOutputStream fos = new FileOutputStream(outputFile);
			
			InputStream is = c.getInputStream();
			
			byte[] buffer = new byte[1024];
			long baixado = 0;
			int len1 = 0;
			while ((len1 = is.read(buffer)) != -1) {
				baixado += len1;
				publishProgress((int)(baixado*100/tamanho));
				fos.write(buffer, 0, len1);
			}
			fos.close();
			is.close();
		}catch (IOException e) {
			return "ioexception";
		}

        return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		progressDialog.setProgress(values[0]);
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(String result) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File (Environment.getExternalStorageDirectory() + "/.sistemadetratamentodeemergencias/" + "SiTE "+this.versionName+".apk")), "application/vnd.android.package-archive");
		((Home) activity).instalar(intent);
		this.progressDialog.dismiss();
		super.onPostExecute(result);
	}
	
	/*private boolean deleteNon_EmptyDir(File dir) {
		if(dir.isDirectory()){
			String[] children = dir.list();
			for(int i=0; i<children.length;i++){
				boolean success = deleteNon_EmptyDir(new File(dir, children[i]));
				if(!success) return false;
			}
		}
		return dir.delete();
	}*/

}
