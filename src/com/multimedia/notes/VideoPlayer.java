package com.multimedia.notes;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VideoPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

	private int STATUS = 0;
	private final int STATUS_STOPED = 1;
	private final int STATUS_PLAYING = 2;
	private final int STATUS_PAUSED = 3;

	private Context ctx;
	private WakeLock wakeLock;
	private MediaPlayer mPlayer;
	private SurfaceView surfaceView;

	public VideoPlayer(Context ctx) {
		this.ctx = ctx;

		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(this);
		mPlayer.setOnPreparedListener(this);

		PowerManager powerManager = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyMediaPlayer");
	}

	/**
	 * Sets up the surface dimensions to display the video on it.
	 */
	private void setUpVideoDimensions() {
		// Get the dimensions of the video
		int videoWidth = mPlayer.getVideoWidth();
		int videoHeight = mPlayer.getVideoHeight();
		float videoProportion = (float) videoWidth / (float) videoHeight;

		// Get the width of the screen
		int screenWidth = ((Activity) ctx).getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = ((Activity) ctx).getWindowManager().getDefaultDisplay().getHeight();
		float screenProportion = (float) screenWidth / (float) screenHeight;

		// Get the SurfaceView layout parameters
		android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();

		if (videoProportion > screenProportion) {
			lp.width = screenWidth;
			lp.height = (int) ((float) screenWidth / videoProportion);
		} else {
			lp.width = (int) (videoProportion * (float) screenHeight);
			lp.height = screenHeight;
		}

		// Commit the layout parameters
		surfaceView.setLayoutParams(lp);
	}

	/**
	 * Pause the video playback.
	 */
	public void pause() {
		if (mPlayer.isPlaying()) {
			mPlayer.pause();
			STATUS = STATUS_PAUSED;
			wakeLockRelease();
		}
	}

	public void play() throws IllegalStateException, IOException {

		if (STATUS != STATUS_PLAYING) {
			wakeLockAcquire();

			if (STATUS == STATUS_PAUSED)
				mPlayer.start();
			else {
				mPlayer.prepare();
				mPlayer.start();
			}

			STATUS = STATUS_PLAYING;
		}
	}

	/**
	 * Sets up the video source.
	 * 
	 * @param source
	 *            - The video address
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void setUpVideoFrom(String source) throws IllegalArgumentException, IllegalStateException, IOException {
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mPlayer.setDataSource(source);
	}

	/**
	 * Release the video object. This will stops the playback and release the memory used.
	 */
	public void release() {
		reset();

		mPlayer.release();
		mPlayer = null;
	}

	private void reset() {
	}

	/**
	 * Set up the surface to display the video on it.
	 * 
	 * @param holder
	 *            - The surface to display the video.
	 */
	public void setDisplay(SurfaceView surfaceView, SurfaceHolder holder) {
		this.surfaceView = surfaceView;
		mPlayer.setDisplay(holder);
	}

	public void setOnPrepared(MediaPlayer.OnPreparedListener listener) {
		mPlayer.setOnPreparedListener(listener);
	}

	public void stop() {
		if (STATUS != STATUS_STOPED) {
			mPlayer.stop();
			STATUS = STATUS_STOPED;
			reset();
			wakeLockRelease();
		}
	}

	private void wakeLockAcquire() {
		wakeLock.acquire();
	}

	private void wakeLockRelease() {
		wakeLock.release();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		stop();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		setUpVideoDimensions();
	}
}
