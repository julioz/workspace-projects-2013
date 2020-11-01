package br.com.zynger.libertadores.asynctasks;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Article;
import br.com.zynger.libertadores.web.NewsParser;

public class GetNewsTask extends AsyncTask<Void, Void, ArrayList<Article>> {

	private Context context;
	private AsyncTaskListener listener;
	private NewsParser parser;
	
	public GetNewsTask(NewsParser parser, AsyncTaskListener listener){
		this.parser = parser;
		this.listener = listener;
		this.context = parser.getContext();
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Article> doInBackground(Void... params) {
		return parser.getNews();
	}
	
	@Override
	protected void onPostExecute(ArrayList<Article> result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(result);
	}
	
	public Context getContext() {
		return context;
	}
}
