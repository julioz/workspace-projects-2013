package br.com.zynger.vamosmarcar.adapter;

import java.util.GregorianCalendar;
import java.util.List;

import com.parse.ParseUser;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.interfaces.EllipsizableRespondable;
import br.com.zynger.vamosmarcar.model.Comment;
import br.com.zynger.vamosmarcar.model.User;
import br.com.zynger.vamosmarcar.view.FacebookImageView;
import br.com.zynger.vamosmarcar.view.RobotoTextView;

public class CommentsAdapter extends ArrayAdapter<Comment> {

	private static final int LAYOUT_RESOURCE = R.layout.row_comments;
	
	private List<Comment> comments;
	private LayoutInflater mInflater;
	private EllipsizableRespondable ellipsizableRespondable;
	
	private ParseUser currentUser;

	public CommentsAdapter(Context context, List<Comment> comments, EllipsizableRespondable ellipsizableRespondable) {
		super(context, 0);
		this.comments = comments;
		this.ellipsizableRespondable = ellipsizableRespondable;
		this.mInflater = LayoutInflater.from(context);
		
		this.currentUser = ParseUser.getCurrentUser();
	}
	
	@Override
	public Comment getItem(int position) {
		return comments.get(position);
	}
	
	@Override
	public int getCount() {
		return comments.size();
	}
	
	@Override
	public void add(Comment comment) {
		comments.add(comment);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CommentHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUT_RESOURCE, parent, false);
            
            holder = new CommentHolder();
            holder.ivUserPicture = (FacebookImageView) row.findViewById(R.commentsadapter.iv_user_picture);
            holder.llCommentLayout = (LinearLayout) row.findViewById(R.commentsadapter.ll_commentwrapper);
            holder.tvCommentText = (RobotoTextView) row.findViewById(R.commentsadapter.tv_comment_text);
            holder.tvTimeStamp = (RobotoTextView) row.findViewById(R.commentsadapter.tv_timestamp);

            row.setTag(holder);
        }else{
            holder = (CommentHolder) row.getTag();
        }
        
        final Comment comment = getItem(position);
        ParseUser author = comment.getAuthor();
		holder.ivUserPicture.loadParseUser(author);
        holder.tvCommentText.setText(comment.getText());
        
        if (author == currentUser) {
        	holder.llCommentLayout.setBackgroundResource(R.drawable.balloon_outgoing);
        } else {
        	holder.llCommentLayout.setBackgroundResource(R.drawable.balloon_incoming);
        }
        
        //TODO remover try-catch, manter só try
        try {
        	holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(comment.getCreatedAt().getTime(), GregorianCalendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
        } catch(Exception e) {
        	holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(1379263100000l, GregorianCalendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
        }
        
        holder.ivUserPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View imageView) {
				Toast.makeText(imageView.getContext(), User.getName(comment.getAuthor()), Toast.LENGTH_SHORT).show();
			}
		});
        
        if (holder.tvCommentText.isEllipsized()) {
        	holder.tvCommentText.setOnClickListener(new OnClickListener() {
        		@Override
        		public void onClick(View tv) {
        			ellipsizableRespondable.respondToEllipsis(comment.getText());
        		}
        	});
        }
        
        return row;
	}
	
	class CommentHolder {
		FacebookImageView ivUserPicture;
		LinearLayout llCommentLayout;
		RobotoTextView tvCommentText;
		RobotoTextView tvTimeStamp;
	}
}
