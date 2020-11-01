package br.com.zynger.vamosmarcar.view;

import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.model.Event;

import com.fima.cardsui.objects.Card;

public class EventCard extends Card {

	private Event event;

	public EventCard(Event event) {
		this.event = event;
	}

	@Override
	public View getCardContent(Context context) {
		View card = LayoutInflater.from(context).inflate(R.layout.card_event, null);
		
		FacebookImageView fivAuthor = (FacebookImageView) card.findViewById(R.card_event.fiv_author);
		TextView tvTitle = (TextView) card.findViewById(R.card_event.tv_title);
		ImageView ivAudience = (ImageView) card.findViewById(R.card_event.iv_audience);
		TextView tvDescription = (TextView) card.findViewById(R.card_event.tv_description);
		ImageView ivPicture = (ImageView) card.findViewById(R.card_event.iv_picture);
		TextView tvParticipants = (TextView) card.findViewById(R.card_event.tv_participants);
		
		tvTitle.setText(event.getTitle());
		ivAudience.setImageResource(event.getAudience().getImageRes());
		tvDescription.setText(event.getDescription());
		fivAuthor.loadParseUser(event.getHost());
		
		try {
			if (!event.getParticipants().isEmpty()) {
				tvParticipants.setText(context.getString(R.string.feedactivity_participants, event.getParticipants().size()));
			}
		} catch(JSONException je) {
			je.printStackTrace();
			tvParticipants.setText("");
		} catch(NullPointerException npe) {
			npe.printStackTrace();
			tvParticipants.setText("");
		}
		
		// TODO: remover
		ivPicture.setImageResource(R.drawable.ic_launcher);
		
		return card;
	}

}
