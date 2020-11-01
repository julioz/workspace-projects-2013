package br.com.zynger.brasileirao2012.asynctasks;

import java.io.IOException;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;
import br.com.zynger.brasileirao2012.model.Article;
import br.com.zynger.brasileirao2012.model.NewsSource;
import br.com.zynger.brasileirao2012.util.Constants;

public class GetNewsTask extends AsyncTask<Void, Void, ArrayList<Article>> {
	private NewsSource source;
	private final AsyncTaskListener<ArrayList<Article>> listener;

	public GetNewsTask(AsyncTaskListener<ArrayList<Article>> listener,
			NewsSource source) {
		this.listener = listener;
		this.source = source;
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Article> doInBackground(Void... arg) {
		try {
			return source.getDomain().getParser()
					.parse(source.getUrl(), source.getEncoding());
		} catch (IOException e) {
			Log.e(Constants.TAG, "IOEXC=" + e.toString());
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException e) {
			Log.e(Constants.TAG, "SIOBEXC=" + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(ArrayList<Article> articles) {
		if (articles != null) {
			listener.onComplete(articles);
		} else {
			listener.onFail(null);
		}
	}
}