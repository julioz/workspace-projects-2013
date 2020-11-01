package br.com.site.view;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.site.MainActivity;
import br.com.site.R;

import com.google.android.maps.OverlayItem;


public class BalloonOverlayView extends FrameLayout {

	private LinearLayout layout;
	private TextView title;
	private TextView snippet;
	private Context c;
	private OverlayItem overlayitem;
	private ImageView edit, position;

	/**
	 * Create a new BalloonOverlayView.
	 * 
	 * @param context - The activity context.
	 * @param balloonBottomOffset - The bottom padding (in pixels) to be applied
	 * when rendering this view.
	 */
	public BalloonOverlayView(Context context, int balloonBottomOffset) {

		super(context);
		
		this.c = context;

		setPadding(10, 0, 10, balloonBottomOffset);
		layout = new LinearLayout(context);
		layout.setVisibility(VISIBLE);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.balloon_overlay, layout);
		title = (TextView) v.findViewById(R.balao.balloon_item_title);
		snippet = (TextView) v.findViewById(R.balao.balloon_item_snippet);

		ImageView close = (ImageView) v.findViewById(R.balao.close_img_button);
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				layout.setVisibility(GONE);
			}
		});
		
		edit = (ImageView) v.findViewById(R.balao.edit_img_button);
		position = (ImageView) v.findViewById(R.balao.position_img_button);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.NO_GRAVITY;

		addView(layout, params);

	}
	
	/**
	 * Sets the view data from a given overlay item.
	 * 
	 * @param item - The overlay item containing the relevant view data 
	 * (title and snippet). 
	 */
	public void setData(OverlayItem item) {
		String[] bancoNegrito = {"Veículo:", "Estrutura:", "Equipamento:",
				"Equipe:", "Perigo:", "Vítima:", "Instituição:"};
		
		layout.setVisibility(VISIBLE);
		if (item.getTitle() != null) {
			title.setVisibility(VISIBLE);
			title.setText(item.getTitle());
		} else {
			title.setVisibility(GONE);
		}		
		if (item.getSnippet() != null) {
			snippet.setVisibility(VISIBLE);
			
			String text = item.getSnippet();
			
			text = text.replaceAll("\n", "<br />");
			
			for (String s : bancoNegrito) {
				if(text.contains(s)){
					text = text.replace(s, "<font color='000000'>"+s+"</font>");
				}
			}
			
			
			snippet.setText(Html.fromHtml(text));
		
		} else {
			snippet.setVisibility(GONE);
		}
		
		this.overlayitem = item;
		
		edit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainActivity.editarPDI(c, overlayitem);
			}
		});
		
		position.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.moverPDI(c, overlayitem);
			}
		});
		
	}
}