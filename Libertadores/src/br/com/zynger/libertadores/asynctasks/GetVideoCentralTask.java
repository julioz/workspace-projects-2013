package br.com.zynger.libertadores.asynctasks;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Video;
import br.com.zynger.libertadores.web.VideoCentralParser;

public class GetVideoCentralTask extends AsyncTask<Void, Void, ArrayList<Video>> {

	private Context context;
	private AsyncTaskListener listener;
	private VideoCentralParser parser;
	private String searchKey;
	
	public GetVideoCentralTask(VideoCentralParser parser, AsyncTaskListener listener){
		this.parser = parser;
		this.listener = listener;
		this.context = parser.getContext();
	}
	
	public GetVideoCentralTask(VideoCentralParser parser, String searchKey, AsyncTaskListener listener){
		this.parser = parser;
		this.listener = listener;
		this.context = parser.getContext();
		this.searchKey = searchKey;
		
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Video> doInBackground(Void... params) {
		return parser.getVideos(searchKey);
	}

	@Override
	protected void onPostExecute(ArrayList<Video> result) {
		if(null != result) listener.onComplete(result);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(result);
	}
	
	public Context getContext() {
		return context;
	}
}
