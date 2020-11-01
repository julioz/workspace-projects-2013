package edu.mst.kmnrradio.asyncs;

import android.os.AsyncTask;
import edu.mst.kmnrradio.web.MediaInfoParser;

public class GetRadioInfoTask extends AsyncTask<Void, Void, String> {

	private String radioURL;
	private AsyncTaskListener listener;
	
	public GetRadioInfoTask(String radioURL, AsyncTaskListener listener) {
		this.radioURL = radioURL;
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		return MediaInfoParser.parseInfo(radioURL);
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(result != null) listener.onComplete(result);
		super.onPostExecute(result);
	}

}
