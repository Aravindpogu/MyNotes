package com.multimedia.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class SplashScreen extends Activity {
	private boolean isActive = true;
	private int splashTime = 1500;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);

		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (isActive && (waited < splashTime)) {
						sleep(100);
						if (isActive) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					isActive = false;
					e.printStackTrace();
				} finally {
					if (isActive) {
						startActivity(new Intent(SplashScreen.this, MainActivity.class));
					}
					finish();
				}
			}
		};
		splashThread.start();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isActive = false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
