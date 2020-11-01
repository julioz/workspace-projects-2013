package br.com.zynger.vamosmarcar.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.adapter.FeedDrawerAdapter;
import br.com.zynger.vamosmarcar.adapter.FeedDrawerAdapter.FeedDrawerItem;
import br.com.zynger.vamosmarcar.adapter.FeedTitleAdapter;
import br.com.zynger.vamosmarcar.model.Event;
import br.com.zynger.vamosmarcar.model.Event.EventAudience;
import br.com.zynger.vamosmarcar.model.Event.EventType;
import br.com.zynger.vamosmarcar.model.User;
import br.com.zynger.vamosmarcar.util.ParseQueryCreator;
import br.com.zynger.vamosmarcar.view.EventCard;
import br.com.zynger.vamosmarcar.view.FacebookImageView;
import br.com.zynger.vamosmarcar.view.RobotoTextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.MenuItem;
import com.fima.cardsui.views.CardUI;
import com.fima.cardsui.views.QuickReturnListView;
import com.google.android.gms.maps.model.LatLng;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class FeedActivity extends BaseActivity implements OnItemClickListener,
		OnNavigationListener {
	
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	private ArrayList<Event> eventList;

	private Comparator<Event> titleComparator;
	private Comparator<Event> typeComparator;

	private DrawerLayout drawerLayout;
	private ListView lvDrawer;
	private Button btDrawer;
	private ActionBarDrawerToggle drawerToggle;
	private FeedTitleAdapter actionBarSpinnerAdapter;
	private CardUI mCardView;

	private RelativeLayout rlDrawer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedactivity);

		setActionBarSpinner();
		loadViews();
		setDrawerAttributes();

		setCardComparators();
		setEventList();
		setCards();
	}

	private void setCardComparators() {
		titleComparator = new Comparator<Event>() {
			@Override
			public int compare(Event lhs, Event rhs) {
				// TODO: trocar de getTitle() para getCreatedAt()
				return lhs.getTitle().compareTo(rhs.getTitle());
			}
		};
		
		typeComparator = new Comparator<Event>() {
			@Override
			public int compare(Event lhs, Event rhs) {
				int comparison = lhs.getType().compareTo(rhs.getType());
				if (comparison == 0) {
					comparison = lhs.getTitle().compareTo(rhs.getTitle());
				}
				return comparison;
			}
		};
	}

	private void setCards() {
		mCardView.clearCards();
		for (final Event event : eventList) {
			EventCard card = new EventCard(event);
			card.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View card) {
					startActivityWithParseObject(EventActivity.class, event);
				}
			});
			mCardView.addCard(card);
			mCardView.refresh();
		}
		
		QuickReturnListView cardsListView = mCardView.getScrollView();
		SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter((BaseAdapter) cardsListView.getAdapter());
		animationAdapter.setAbsListView(cardsListView);
		cardsListView.setAdapter(animationAdapter);
	}

	private void setEventList() {
		eventList = new ArrayList<Event>();
		for (int i = 1; i <= 80; i++) {
			final Event event = new Event();
			event.setHost(ParseUser.getCurrentUser());
			event.setTitle("Evento " + (i < 10 ? "0" : "") + i);
			event.setAudience(EventAudience.values()[i % EventAudience.values().length]);
			event.setDescription("Description "
					+ i
					+ "\nLorem ipsum dolor sit amet, consectetur adipiscing elit. In interdum ipsum leo, id fermentum metus tincidunt non. In hac habitasse platea dictumst. Maecenas volutpat augue vel magna fermentum, ac aliquet tortor sollicitudin. Maecenas est velit, fermentum in laoreet dapibus, tristique interdum eros. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed ullamcorper, magna at pharetra condimentum, quam erat ornare elit, sit amet consequat ligula quam sed nisi. Cras pharetra nec libero ut porta. Suspendisse potenti.");
			event.setType(EventType.values()[i % EventType.values().length]);
			event.setLatLng(new LatLng(-22.964349, -43.21779));
			ArrayList<String> participants = new ArrayList<String>();
			for (int j = 0; j < new Random().nextInt(50); j++) {
				participants.add(String.valueOf(j));
			}
			event.setParticipants(participants);

			eventList.add(event);
		}
	}

	private void setActionBarSpinner() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);

		actionBarSpinnerAdapter = new FeedTitleAdapter(this);
		actionBar.setListNavigationCallbacks(actionBarSpinnerAdapter, this);
	}

	private void setDrawerAttributes() {
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);

		// Set the drawer toggle as the DrawerListener
		drawerLayout.setDrawerListener(drawerToggle);

		addDrawerHeader();
		lvDrawer.setAdapter(new FeedDrawerAdapter(this));
		lvDrawer.setOnItemClickListener(this);
		
		Drawable drawable = getResources().getDrawable(R.drawable.ic_action_logout);
		drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth() * 0.8), 
		                         (int)(drawable.getIntrinsicHeight() * 0.8));
		btDrawer.setCompoundDrawables(drawable, null, null, null);
		
		btDrawer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View button) {
				showLogoutDialog();
			}
		});

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	private void addDrawerHeader() {
		View header = LayoutInflater.from(this).inflate(
				R.layout.feedactivity_drawer_header, null);

		FacebookImageView facebookIv = (FacebookImageView) header
				.findViewById(R.feeddrawerheader.iv_user_picture);
		RobotoTextView tvName = (RobotoTextView) header
				.findViewById(R.feeddrawerheader.tv_name);

		ParseUser currentUser = ParseUser.getCurrentUser();
		facebookIv.loadParseUser(currentUser);
		tvName.setText(User.getName(currentUser));

		lvDrawer.addHeaderView(header);
	}

	private void loadViews() {
		drawerLayout = (DrawerLayout) findViewById(R.feedactivity.drawer_layout);
		rlDrawer = (RelativeLayout) findViewById(R.feedactivity.rl_drawer);
		lvDrawer = (ListView) findViewById(R.feedactivity.lv_drawer);
		btDrawer = (Button) findViewById(R.feedactivity.bt_drawer);
		mCardView = (CardUI) findViewById(R.feedactivity.cardsview);

		mCardView.setSwipeable(false);
	}

	private void showNewEventActivity() {
		startActivity(new Intent(this, NewEventActivity.class));
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getSupportActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getSupportActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// ActionbarSherlock still does not support DrawerLayout
		// with this workaround we can still get the icon click
		switch (item.getItemId()) {
		case android.R.id.home:
			if (drawerLayout.isDrawerOpen(rlDrawer)) {
				drawerLayout.closeDrawer(rlDrawer);
			} else {
				drawerLayout.openDrawer(rlDrawer);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> listview, View view, int position,
			long id) {
		// TODO Completar listener
		if (position == 0) {
			// TODO fazer activity com as informacoes do user
			Toast.makeText(view.getContext(), "Você clicou no header!",
					Toast.LENGTH_SHORT).show();
		} else {
			FeedDrawerItem item = (FeedDrawerItem) listview.getAdapter().getItem(position);
			if (item.getTitleRes() == R.string.feed_drawer_newevent) {
				showNewEventActivity();
			} else if (item.getTitleRes() == R.string.feed_drawer_checkins) {
				// TODO: placeholder code. Remove when functionality is done.
				ParseQueryCreator.getInstance().queryEvents().findInBackground(new FindCallback<Event>() {
					@Override
					public void done(List<Event> fetchedEvents, ParseException e) {
						if (e == null) {
				            Log.d(Constants.TAG, "Retrieved " + eventList.size() + " events");
				            eventList = new ArrayList<Event>();
				            eventList.addAll(fetchedEvents);
				            for (ParseObject parseObject : fetchedEvents) {
				            	Event event = (Event) parseObject;
								Log.d(Constants.TAG, event.toString() + " -> " + event.getTitle());
							}
				            setCards();
				        } else {
				            Log.d(Constants.TAG, "Error: " + e.getMessage());
				        }						
					}
				});
			} else {
				Toast.makeText(view.getContext(), item.getTitleRes(), Toast.LENGTH_SHORT)
						.show();
			}
		}
		
		drawerLayout.closeDrawer(rlDrawer);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Comparator<? super Event> eventComparator = null;

		String item = actionBarSpinnerAdapter.getItem(itemPosition);
		if (item.equals(getString(R.string.feedactivity_sort_newest))) {
			eventComparator = titleComparator;
		} else if (item.equals(getString(R.string.feedactivity_sort_type))) {
			eventComparator = typeComparator;
		}
		
		if (eventComparator != null) {
			Collections.sort(eventList, eventComparator);
			setCards();
			return true;
		}
		
		return false;
	}
}
