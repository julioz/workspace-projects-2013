package br.com.zynger.brasileirao2012.asynctasks;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.enums.NewsDomain;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class ReadNewsTask extends AsyncTask<Void, Void, String> {
	private String newsurl;
	private NewsDomain domain;
	private AsyncTaskListener<Object> listener;
	
	public ReadNewsTask(String newsurl, NewsDomain domain, AsyncTaskListener<Object> listener){
		this.newsurl = newsurl;
		this.domain = domain;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Void... arg0) {
		return HTMLManager.getNewsContent(newsurl, domain);
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(result != null) {
			listener.onComplete(result);
		} else {
			listener.onFail(listener.getContext().getString(R.string.readnewsfromdomain_errorreadingtrywebsite));
		}
		super.onPostExecute(result);
	}
}