package com.multimedia.notes;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAudioNoteActivity extends Activity implements OnClickListener {
	private static final String STOP = "stop";
	private static final String START = "start";
	private Button startButton;
	private Button stopButton;
	private MediaRecorder mediaRecorder;
	private File audioFile;
	private TextView suggestion;
	private EditText audioNoteName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_audio_notes);

		startButton = (Button) findViewById(R.id.start);
		startButton.setOnClickListener(this);
		startButton.setText(START);

		stopButton = (Button) findViewById(R.id.stop);
		stopButton.setOnClickListener(this);
		stopButton.setEnabled(false);
		stopButton.setText(STOP);

		suggestion = (TextView) findViewById(R.id.suggestion);
		suggestion.setText(NotesConstants.GIVE_AUDIO_FILE_NAME);
		audioNoteName = (EditText) findViewById(R.id.audioNoteName);

	}

	private void resetRecorder() {

		File audioDirectory = new File(Environment.getExternalStorageDirectory() + NotesConstants.NOTES_ON_GO_AUDIO_DIR);
		if (!audioDirectory.exists()) {
			audioDirectory.mkdirs();
		}

		audioFile = new File(audioDirectory.getPath(), audioNoteName.getText().toString() + NotesConstants.AUDIO_FILE_FORMAT);
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mediaRecorder.setAudioEncodingBitRate(16);
		mediaRecorder.setAudioSamplingRate(44100);
		mediaRecorder.setOutputFile(audioFile.getAbsolutePath());

		try {
			mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			mediaRecorder = new MediaRecorder();
			resetRecorder();
			mediaRecorder.start();

			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			break;
		case R.id.stop:
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;

			startButton.setEnabled(false);
			stopButton.setEnabled(false);
			suggestion.setText("");
			audioNoteName.setEnabled(false);
			
			Toast.makeText(getApplicationContext(), NotesConstants.AUDIO_NOTE_SAVED, Toast.LENGTH_LONG).show();
			Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(500);
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mediaRecorder != null) {
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
		}
	}
}
