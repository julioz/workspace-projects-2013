package br.com.zynger.brasileirao2012.fragment;

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.util.AppHelper;
import br.com.zynger.brasileirao2012.util.Constants;

import com.actionbarsherlock.app.SherlockFragment;

public class AboutFragment extends SherlockFragment {

	private int mPageNumber;

	public static AboutFragment create(int pageNumber) {
		AboutFragment fragment = new AboutFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.PAGER_POSITION, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public AboutFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(Constants.PAGER_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Context context = getSherlockActivity();
		if(mPageNumber == 0){
			return createTheAppView(inflater, context);
		}else if(mPageNumber == 1){
			return createMailAndRightsView(context);
		}else if(mPageNumber == 2){				
			return createThanksView(context);
		}
		return container;
	}

	private View createThanksView(Context context) {
		String text = new String();
		for (String item : getResources().getStringArray(R.array.aboutactivity_thanks)) {
			text += "&#8226; " + item + "<br/>";
		}
		return formatTextView(context, Html.fromHtml(text));
	}

	private View createMailAndRightsView(Context context) {
		return formatTextView(context, getString(R.string.aboutactivity_mailandrights));
	}
	
	private TextView formatTextView(Context context, CharSequence text){
		TextView tv = new TextView(context);
		tv.setText(text);
		tv.setTextColor(getResources().getColor(android.R.color.white));
		tv.setTextSize(20);
		return tv;
	}

	private View createTheAppView(LayoutInflater inflater, Context context) {
		View view = inflater.inflate(R.layout.aboutactivity_theapp_layout, null);
		
		TextView tvAppName = (TextView) view.findViewById(R.aboutactivity.tv_appname);
		TextView tvVersion = (TextView) view.findViewById(R.aboutactivity.tv_version);

		SpannableString appname = new SpannableString(getString(R.string.app_name).toUpperCase(Locale.getDefault()));
		appname.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green_apptitle)), 0, 6, 0);
		appname.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow_apptitle)), 6, appname.length(), 0);
		tvAppName.setText(appname, BufferType.SPANNABLE);
		
		tvVersion.setText(getString(R.string.aboutactivity_version)
				+ " " + AppHelper.getAppVersion(context));
		return view;
	}

}
