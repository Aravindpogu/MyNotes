package com.multimedia.notes;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayAudioActivity extends Activity implements OnClickListener {
	private SeekBar seekBar;
	private TextView shownText;
	private Button playButton;
	private Button pauseButton;
	private MediaPlayer player;
	private Handler seekHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_audio);
		Intent intent = getIntent();
		String audioPath = intent.getStringExtra(NotesConstants.FILE_PATH);
		init(audioPath);
		updateSeekBar();
		int index = Environment.getExternalStorageDirectory().getAbsolutePath().length()
				+ NotesConstants.NOTES_ON_GO_AUDIO_DIR.length() + 1;
		setTitle(audioPath.substring(index));
	}

	public void init(String audioPath ) {
		
		seekBar = (SeekBar) findViewById(R.id.seek_bar);
		playButton = (Button) findViewById(R.id.play_button);
		pauseButton = (Button) findViewById(R.id.pause_button);
		shownText = (TextView) findViewById(R.id.text_shown);
		playButton.setOnClickListener(this);
		pauseButton.setOnClickListener(this);
		player = player.create(getApplicationContext(), Uri.fromFile(new File(audioPath)));
		int duration = player.getDuration();
		seekBar.setMax(duration);
	}

	Runnable run = new Runnable() {
		@Override
		public void run() {
			updateSeekBar();
		}
	};

	public void updateSeekBar() {
		seekBar.setProgress(player.getCurrentPosition());
		seekHandler.postDelayed(run, 1000);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.play_button:
			shownText.setText("Playing...");
			player.start();
			break;
		case R.id.pause_button:
			player.pause();
			shownText.setText("Paused...");
			break;
		}
	}

}
