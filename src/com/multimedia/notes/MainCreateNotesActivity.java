package com.multimedia.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.multimedia.notes.R;
import com.multimedia.notes.CreateVideoNotesActivity;
import com.multimedia.notes.CreateAudioNoteActivity;
import com.multimedia.notes.CreateTextNotesActivity;

public class MainCreateNotesActivity extends Activity {

	private ImageButton createTextNoteButton;
	private ImageButton createAudioNoteButton;
	private ImageButton createVideoNoteButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_create_notes);

		createTextNoteButton = (ImageButton) findViewById(R.id.createTextNote);
		final Context context = this;

		createTextNoteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(context, CreateTextNotesActivity.class);
				startActivity(newIntent);

			}
		});

		createAudioNoteButton = (ImageButton) findViewById(R.id.createAudioNote);
		createAudioNoteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(context, CreateAudioNoteActivity.class);
				startActivity(newIntent);

			}
		});

		createVideoNoteButton = (ImageButton) findViewById(R.id.createVideoNote);
		createVideoNoteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(context, CreateVideoNotesActivity.class);
				startActivity(newIntent);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

}
