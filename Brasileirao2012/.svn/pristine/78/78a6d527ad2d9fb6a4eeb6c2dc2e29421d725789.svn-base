package br.com.zynger.brasileirao2012.asynctasks;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.enums.NewsDomain;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class ReadNewsFromDomain extends AsyncTask<Void, Void, String[]> {
	private String newsurl, serverId;
	private NewsDomain domain;
	private AsyncTaskListener<Object> listener;
	
	public ReadNewsFromDomain(AsyncTaskListener<Object> listener, 
			String newsurl, NewsDomain domain){
		this.newsurl = newsurl;
		this.domain = domain;
		this.listener = listener;
		
		if(this.domain == NewsDomain.UOL) serverId = "uol";
		else if(this.domain == NewsDomain.NETFLU) serverId = "netflu";
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected String[] doInBackground(Void... arg0) {
		try{
			return HTMLManager.getNewsDetailsContent(serverId, newsurl);
		}catch(Exception e){
			String[] result = new String[2];
			result[0] = null;
			result[1] = listener.getContext().getString(R.string.readnewsfromdomain_errorreading);
			return result;
		}
	}
	
	@Override
	protected void onPostExecute(String[] result) {
		listener.onComplete(result);
		super.onPostExecute(result);
	}
}