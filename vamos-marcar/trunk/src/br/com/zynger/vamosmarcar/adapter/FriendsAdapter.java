package br.com.zynger.vamosmarcar.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.model.FacebookFriend;
import br.com.zynger.vamosmarcar.util.Util;
import br.com.zynger.vamosmarcar.view.FacebookImageView;
import br.com.zynger.vamosmarcar.view.RobotoTextView;

public class FriendsAdapter extends ArrayAdapter<FacebookFriend> implements
		SectionIndexer {

	private static final int LAYOUT_RESOURCE = R.layout.row_friends;
	private final String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private List<FacebookFriend> friends;
	private LayoutInflater mInflater;

	public FriendsAdapter(Context context, List<FacebookFriend> friends) {
		super(context, 0);
		this.friends = friends;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public FacebookFriend getItem(int position) {
		return friends.get(position);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return friends.size();
	}

	public int getPositionFromId(String id) {
		for (int i = 0; i < friends.size(); i++) {
			final FacebookFriend friend = getItem(i);
			if (friend.getId().equals(id)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CommentHolder holder = null;

		if (row == null) {
			row = mInflater.inflate(LAYOUT_RESOURCE, parent, false);

			holder = new CommentHolder();
			holder.ivUserPicture = (FacebookImageView) row
					.findViewById(R.friendsadapter.iv_user_picture);
			holder.tvName = (RobotoTextView) row
					.findViewById(R.friendsadapter.tv_name);

			row.setTag(holder);
		} else {
			holder = (CommentHolder) row.getTag();
		}

		final FacebookFriend friend = getItem(position);
		holder.ivUserPicture.loadFacebookId(friend.getId());
		holder.tvName.setText(friend.getName());

		return row;
	}

	@Override
	public int getPositionForSection(int section) {
		for (int sectionChar = section; sectionChar >= 0; sectionChar--) {
			for (int friendIndex = 0; friendIndex < getCount(); friendIndex++) {
				char friendFirstChar = getItem(friendIndex).getName().charAt(0);
				if (Util.matchString(String.valueOf(friendFirstChar),
						String.valueOf(mSections.charAt(sectionChar)))) {
					return friendIndex;
				}
			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}

	class CommentHolder {
		FacebookImageView ivUserPicture;
		RobotoTextView tvName;
	}
}
