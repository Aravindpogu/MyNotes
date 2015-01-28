package com.multimedia.notes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.multimedia.notes.R;
import com.multimedia.notes.DBHandler;
import com.multimedia.notes.TextNote;
import com.multimedia.notes.Util;

public class CreateTextNotesActivity extends Activity {

	private TextView welcomeText;
	private Button saveNoteButton;
	private EditText noteText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_text_notes);

		welcomeText = (TextView) findViewById(R.id.welcomeText);
		welcomeText.setText(NotesConstants.WELCOME_TEXT);
		
		saveNoteButton = (Button) findViewById(R.id.saveNote);
		noteText = (EditText) findViewById(R.id.noteText);
		final Context context = this;

		saveNoteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String currentDateTime = Util.getDateTimeString();
				String textNote = noteText.getText().toString();

				if (Util.isEmpty(textNote)) {
					welcomeText.setText("Please enter any note.");
				} else {
					TextNote note = new TextNote(textNote, currentDateTime);
					DBHandler dbHandler = new DBHandler(context);
					dbHandler.addNote(note);

					Toast.makeText(getApplicationContext(), NotesConstants.TEXT_SAVED_MESSAGE, Toast.LENGTH_LONG).show();
					noteText.setEnabled(false);
					welcomeText.setText("");
					saveNoteButton.setEnabled(false);
					Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
					vibrator.vibrate(500);
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

}
