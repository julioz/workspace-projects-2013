package br.com.zynger.brasileirao2012.movetomove;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.adapters.MoveToMoveArrayAdapter;
import br.com.zynger.brasileirao2012.asynctasks.DownloadImageTask;
import br.com.zynger.brasileirao2012.asynctasks.DownloadImageTask.ImageDownloadable;
import br.com.zynger.brasileirao2012.model.Move;

public class MoveToMoveVideoCentralAdapter extends MoveToMoveArrayAdapter
		implements ImageDownloadable {

	private HashMap<String, Bitmap> cache;
	private Drawable videoPlaceHolder;
	private final OnMoveThumbClickListener thumbClickListener;

	public MoveToMoveVideoCentralAdapter(
			OnMoveThumbClickListener thumbClickListener, ArrayList<Move> moves) {
		super(thumbClickListener.getContext(), moves);
		this.thumbClickListener = thumbClickListener;
		this.cache = new HashMap<String, Bitmap>();
		this.videoPlaceHolder = thumbClickListener.getContext().getResources()
				.getDrawable(R.drawable.img_video_placeholder);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = super.getView(position, convertView, parent);
		MoveToMoveHolder holder = (MoveToMoveHolder) row.getTag();
		final Move move = getItem(position);

		new DownloadImageTask(this, move.getUrlThumbPlaylist(),
				holder.rlVideoThumb, holder.ivVideoThumb, videoPlaceHolder,
				cache).execute();

		if (move.getVideoUrl() == null) {
			holder.ivVideoThumb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View thumb) {
					thumbClickListener.onThumbClick(move);
				}
			});
		} else {
			holder.ivVideoThumb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View thumb) {
					thumbClickListener.executeUrlAction(move.getVideoUrl());
				}
			});
		}

		return row;
	}

	public void setVideoUrl(Integer videoId, String videoUrl) {
		for (Move move : moves) {
			if (move.getId().intValue() == videoId.intValue()) {
				move.setVideoUrl(videoUrl);
				notifyDataSetChanged();
				return;
			}
		}
	}

	// After showing the image, there is nothing else to be done
	@Override
	public void imageDownloaded(Bitmap bitmap) {
	}
}
