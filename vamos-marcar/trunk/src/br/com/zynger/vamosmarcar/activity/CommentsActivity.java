package br.com.zynger.vamosmarcar.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.adapter.CommentsAdapter;
import br.com.zynger.vamosmarcar.interfaces.EllipsizableRespondable;
import br.com.zynger.vamosmarcar.model.Comment;

import com.parse.ParseUser;

public class CommentsActivity extends BaseActivity implements EllipsizableRespondable {

	protected static final String INTENT_TITLE = "INTENT_TITLE";
	
	private ListView lvComments;
	private EditText etComment;
	private ImageButton btCommentSend;

	private CommentsAdapter commentsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_background));
		setContentView(R.layout.activity_comments);
		
		setTitle(getIntent().getExtras().getString(INTENT_TITLE));

		loadViews();

		//TODO carregar comentarios do servidor
		commentsAdapter = new CommentsAdapter(this, getFakeComments(), this);
		lvComments.setAdapter(commentsAdapter);
	}

	private void loadViews() {
		lvComments = (ListView) findViewById(R.comments_activity.lv_comments);

		etComment = (EditText) findViewById(R.comments_activity.et_comment);
		// TODO alterar icone do botao
		btCommentSend = (ImageButton) findViewById(R.comments_activity.bt_commentsend);

		btCommentSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View bt) {
				String etText = etComment.getText().toString();
				if (etText.trim().length() < 1) {
					//TODO hardcoded string (e alterar texto)
					Toast.makeText(bt.getContext(), "Escreva alguma coisa!",
							Toast.LENGTH_SHORT).show();
				} else {
					//TODO enviar para o servidor
					etComment.setText("");
					Comment comment = new Comment();
					comment.setAuthor(ParseUser.getCurrentUser());
					comment.setText(etText);
					commentsAdapter.add(comment);
					commentsAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	private ArrayList<Comment> getFakeComments() {
		ArrayList<Comment> comments = new ArrayList<Comment>();

		Comment comment1 = new Comment();
		comment1.setAuthor(ParseUser.getCurrentUser());
		comment1.setText("Poxa, esse horário está péssimo, não vou poder participar do evento. Por favor, troquem a data!");
		comments.add(comment1);

		Comment comment2 = new Comment();
		comment2.setAuthor(ParseUser.getCurrentUser());
		comment2.setText("Pra mim está ótimo. Espero vocês lá!");
		comments.add(comment2);

		Comment comment3 = new Comment();
		comment3.setAuthor(ParseUser.getCurrentUser());
		comment3.setText("Vamos lá, galera, vai ser muito legal! Estou muito animado para reencontrar vocês!");
		comments.add(comment3);

		Comment comment4 = new Comment();
		comment4.setAuthor(ParseUser.getCurrentUser());
		comment4.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ullamcorper urna at felis bibendum, in adipiscing mauris dictum. Curabitur faucibus urna a blandit scelerisque. Proin consectetur, ligula blandit placerat gravida, nisl metus sagittis tellus, vitae interdum metus ante vel enim. Donec fringilla urna tellus, non volutpat augue ultricies vel. Mauris nisi risus, suscipit vel porta eget.");
		comments.add(comment4);
		
		return comments;
	}

	@Override
	public void respondToEllipsis(String ellipsizedText) {
		showTextDialog(ellipsizedText);
	}
}
