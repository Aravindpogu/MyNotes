package com.multimedia.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button createNotesButton;
	private Button searchNotesButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createNotesButton = (Button) findViewById(R.id.create);
		searchNotesButton = (Button) findViewById(R.id.search);
		final Context context = this;

		createNotesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(context, MainCreateNotesActivity.class);
				startActivity(newIntent);
			}
		});

		searchNotesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(context, MainSearchNotesActivity.class);
				startActivity(newIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

}
