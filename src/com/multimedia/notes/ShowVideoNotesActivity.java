package com.multimedia.notes;

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.multimedia.notes.Util;

public class ShowVideoNotesActivity extends ListActivity {

	private MediaPlayer mediaPlayer;
	private String[] videoList;
	private String[] videos = new String[1];
	private String[] videoPath = new String[1];
	private String selectedDate;
	private TextView welcomeText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_audio_notes);

		Intent recieved = getIntent();
		selectedDate = recieved.getStringExtra(NotesConstants.DATE_SELECTED);
		setTitle("Video files saved on " + selectedDate);

		welcomeText = (TextView)findViewById(R.id.welcomeText);
		mediaPlayer = new MediaPlayer();
		videoList = getVideoList();
		
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, videoList));
		ListView listView = getListView();
		final Context context = this;
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int index = position;
				try {
					Intent newIntent = new Intent(context, PlayVideoActivity.class);
					newIntent.putExtra(NotesConstants.FILE_PATH,videoPath[index]);
					startActivity(newIntent);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} 
			}
		});

	}

	private String[] getVideoList() {
		ArrayList<String> videosList = new ArrayList<String>();
		ArrayList<String> videoPathList = new ArrayList<String>();
		File returnList[] = Util.getVideoFiles(selectedDate);

		if(null != returnList){
			for(File file : returnList) {
				if(null != file) {
					videosList.add(file.getName());
					videoPathList.add(file.getPath());
				}
			}
		}

		if(videosList.size() == 0){
			welcomeText.setText(NotesConstants.NO_RECORDS_FOUND);
		}
		videos = new String[videosList.size()];
		videoPath = new String[videosList.size()];

		videoPath = videoPathList.toArray(videoPath);
		videos = videosList.toArray(videos);

		return videos;
	}

	@Override
	public void onBackPressed() {
		mediaPlayer.reset();
		super.onBackPressed();
	}
}

