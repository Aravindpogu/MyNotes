package com.multimedia.notes;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateVideoNotesActivity extends Activity implements
		SurfaceHolder.Callback {

	private Button videoRecordButton;
	private MediaRecorder mediaRecorder;
	private SurfaceHolder surfaceHolder;
	private boolean isRecording = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_video_notes);

		mediaRecorder = new MediaRecorder();
		initMediaRecorder();

		SurfaceView myVideoView = (SurfaceView) findViewById(R.id.videoView);
		surfaceHolder = myVideoView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		videoRecordButton = (Button) findViewById(R.id.videoRecordButton);
		videoRecordButton.setOnClickListener(videoButtonClickListener);
	}

	private Button.OnClickListener videoButtonClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View view) {

			if (isRecording) {
				mediaRecorder.stop();
				mediaRecorder.release();
				finish();
				Toast.makeText(getApplicationContext(), NotesConstants.VIDEO_NOTE_SAVED, Toast.LENGTH_LONG).show();
				Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(500);
			} else {
				mediaRecorder.start();
				isRecording = true;
				videoRecordButton.setText(NotesConstants.STOP);
			}
		}
	};

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		return;
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		prepareMediaRecorder();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {

	}

	private void initMediaRecorder() {
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
		mediaRecorder.setProfile(camcorderProfile_HQ);

		File notesOnGoDir = new File(Environment.getExternalStorageDirectory() + NotesConstants.NOTES_ON_GO_VIDEO_DIR);
		if (!notesOnGoDir.exists()) {
			notesOnGoDir.mkdirs();
		}

		mediaRecorder.setOutputFile(notesOnGoDir.getPath() + Util.getVideoFileName());
		mediaRecorder.setMaxDuration(NotesConstants.VIDEO_LENGTH);
		mediaRecorder.setMaxFileSize(NotesConstants.VIDEO_SIZE);
	}

	private void prepareMediaRecorder() {
		mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
		try {
			mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
