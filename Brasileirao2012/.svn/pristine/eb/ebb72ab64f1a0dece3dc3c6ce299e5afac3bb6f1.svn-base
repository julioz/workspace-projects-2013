package br.com.zynger.brasileirao2012.adapters;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Move;
import br.com.zynger.brasileirao2012.model.Move.Tipo;
import br.com.zynger.brasileirao2012.util.MoveByTimeComparator;

public class MoveToMoveArrayAdapter extends ArrayAdapter<Move> {
	private final static int LAYOUT_RESOURCE_ID = R.layout.movetomoverowlayout;

	private LayoutInflater layoutInflater;
	protected ArrayList<Move> moves;

	public MoveToMoveArrayAdapter(Context context, ArrayList<Move> moves) {
		super(context, 0, moves);
		layoutInflater = LayoutInflater.from(context);
		this.moves = moves;
		Collections.sort(this.moves, new MoveByTimeComparator());
		Collections.reverse(this.moves);
	}

	@Override
	public int getCount() {
		return moves.size();
	}

	public ArrayList<Move> getMovesWithVideos() {
		ArrayList<Move> videoMoves = new ArrayList<Move>();
		for (Move move : moves) {
			if (move.getVideoId() != null) {
				videoMoves.add(move);
			}
		}
		return videoMoves;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}

	@Override
	public Move getItem(int position) {
		return moves.get(position);
	}

	public Move getMoveById(int id) {
		for (Move move : moves) {
			if (move.getId().intValue() == id)
				return move;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final MoveToMoveHolder holder;

		if (row == null) {
			row = layoutInflater.inflate(LAYOUT_RESOURCE_ID, parent, false);

			holder = new MoveToMoveHolder();
			holder.llContent = (LinearLayout) row
					.findViewById(R.movetomoverow.ll_content);
			holder.tvText = (TextView) row
					.findViewById(R.movetomoverow.tv_text);
			holder.tvMoment = (TextView) row
					.findViewById(R.movetomoverow.tv_moment);
			holder.tvTime = (TextView) row
					.findViewById(R.movetomoverow.tv_time);
			holder.ivSign = (ImageView) row
					.findViewById(R.movetomoverow.iv_sign);
			holder.ivVideoThumb = (ImageButton) row
					.findViewById(R.movetomoverow.iv_videothumb);
			holder.rlVideoThumb = (RelativeLayout) row
					.findViewById(R.movetomoverow.rl_videothumb);

			row.setTag(holder);
		} else {
			holder = (MoveToMoveHolder) row.getTag();
			holder.ivSign.setVisibility(View.INVISIBLE);
			holder.ivSign.setImageDrawable(null);
		}

		final Move move = (Move) getItem(position);

		final Spanned moveText = Html.fromHtml(move.getTexto());
		holder.tvText.setText(moveText);

		Integer momento = move.getMomento();
		if (momento != null) {
			holder.tvMoment.setText(momento + "'");
			holder.tvMoment.setVisibility(View.VISIBLE);
		} else {
			holder.tvMoment.setVisibility(View.GONE);
		}

		holder.tvTime.setText(move.getPeriodo());

		int color = getColorFromType(move);
		holder.tvMoment.setTextColor(color);
		holder.tvTime.setTextColor(color);
		if (move.getVideoId() != null) {
			holder.ivSign.setImageResource(R.drawable.img_video_small);
			holder.ivSign.setVisibility(View.VISIBLE);
		} else {
			setSignByType(holder.ivSign, move);
		}

		return row;
	}

	private int getColorFromType(Move move) {
		Move.Tipo tipo = move.getTipo();
		Move.Card card = move.getCard();

		switch (tipo) {
		case LANCE_IMPORTANTE:
			return Color.GRAY;
		case LANCE_GOL:
			return Color.GREEN;
		case LANCE_CARTAO:
			switch (card.getTipo()) {
			case A:
				return Color.YELLOW;
			case V:
				return Color.RED;
			default:
				break;
			}
			break;
		default:
			return Color.WHITE;
		}
		return Color.WHITE;
	}

	private void setSignByType(ImageView ivSign, Move move) {
		Move.Tipo tipo = move.getTipo();
		Move.Card card = move.getCard();

		switch (tipo) {
		case LANCE:
			ivSign.setVisibility(View.INVISIBLE);
			break;
		case LANCE_CARTAO:
			switch (card.getTipo()) {
			case A:
			case V:
				ivSign.setImageResource(card.getTipo().getImageResource());
				ivSign.setVisibility(View.VISIBLE);
				break;
			default:
				ivSign.setVisibility(View.INVISIBLE);
				break;
			}
			break;
		default:
			ivSign.setImageResource(tipo.getImageResource());
			ivSign.setVisibility(View.VISIBLE);
			break;
		}
	}

	public Integer[] getScore(Club home, Club away) {
		return getScore(home, away, null);
	}

	public Integer[] getScore(Club home, Club away, ArrayList<Move> moves) {
		Integer[] score = new Integer[2];
		Integer scoreHome = 0;
		Integer scoreAway = 0;

		if (moves == null) {
			moves = this.moves;
		}

		for (Move move : moves) {
			if (move.getTipo() == Tipo.LANCE_GOL) {
				if (move.getNomeTime().equals(home.getName())) {
					scoreHome++;
				} else if (move.getNomeTime().equals(away.getName())) {
					scoreAway++;
				}
			}
		}

		score[0] = scoreHome;
		score[1] = scoreAway;
		return score;
	}

	public class MoveToMoveHolder {
		public LinearLayout llContent;
		public TextView tvText;
		public TextView tvMoment;
		public TextView tvTime;
		public ImageButton ivVideoThumb;
		public ImageView ivSign;
		public RelativeLayout rlVideoThumb;
	}
}
