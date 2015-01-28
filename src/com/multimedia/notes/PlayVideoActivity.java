package com.multimedia.notes;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayVideoActivity extends Activity implements OnClickListener, SurfaceHolder.Callback {

	private String videoPath = null;
	private VideoPlayer player;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Button playButon ;
	private Button pauseButton ;
	private Button stopButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playing_video);

		Intent recieved = getIntent();
		videoPath = recieved.getStringExtra(NotesConstants.FILE_PATH);
		playButon = (Button) findViewById(R.id.playButton);
		pauseButton = (Button) findViewById(R.id.pauseButton);
		stopButton = (Button) findViewById(R.id.stopButton);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

		playButon.setOnClickListener(this);
		pauseButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);

		surfaceHolder = surfaceView.getHolder();
		
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		try {
			player = new VideoPlayer(this);
			player.setUpVideoFrom(videoPath);
			player.setDisplay(surfaceView, surfaceHolder);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		player.release();
		player = null;
	}

	@Override
	public void onPause() {
		super.onPause();
		player.pause();
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.playButton:
			try {
				player.play();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case R.id.pauseButton:
			player.pause();
			break;
		case R.id.stopButton:
			player.stop();
			break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		synchronized (this) {
			this.notifyAll();
			player.setDisplay(surfaceView, holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		synchronized (this) {
			synchronized(this)          {
				this.notifyAll(); 
			}
		} 
	}

}

