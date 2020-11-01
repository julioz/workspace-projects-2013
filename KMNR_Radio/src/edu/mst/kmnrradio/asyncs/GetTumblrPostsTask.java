package edu.mst.kmnrradio.asyncs;

import java.util.ArrayList;

import android.os.AsyncTask;
import edu.mst.kmnrradio.model.Post;
import edu.mst.kmnrradio.web.PostsParser;

public class GetTumblrPostsTask extends AsyncTask<Void, Void, ArrayList<Post>> {

	private AsyncTaskListener listener;
	
	public GetTumblrPostsTask(AsyncTaskListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected ArrayList<Post> doInBackground(Void... params) {
		return PostsParser.getPosts();
	}
	
	@Override
	protected void onPostExecute(ArrayList<Post> result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail();
		super.onPostExecute(result);
	}

}