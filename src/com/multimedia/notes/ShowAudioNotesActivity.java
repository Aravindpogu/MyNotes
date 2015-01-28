package com.multimedia.notes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Class to display audio files
 * 
 * @author aravind
 * 
 */
public class ShowAudioNotesActivity extends ListActivity {

	private String selectedDate;
	private TextView welcomeText;
	private String[] audioList;
	private String[] audioFileNames;
	private String[] audioPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_audio_notes);

		Intent recieved = getIntent();
		selectedDate = recieved.getStringExtra(NotesConstants.DATE_SELECTED);

		setTitle("Audio files saved on " + selectedDate);
		welcomeText = (TextView) findViewById(R.id.welcomeText);

		audioList = getAudioList();
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, audioList));
		ListView lv = getListView();
		final Context context = this;
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int index = position;
				try {
					Intent audioPlayIntent = new Intent(context, PlayAudioActivity.class);
					audioPlayIntent.putExtra(NotesConstants.FILE_PATH, audioPath[index]);
					startActivity(audioPlayIntent);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} 

			}
		});

	}

	private String[] getAudioList() {

		List<String> audioList = new ArrayList<String>();
		List<String> audioPathList = new ArrayList<String>();
		File returnList[] = Util.getMediaFiles(NotesConstants.NOTES_ON_GO_AUDIO_DIR, NotesConstants.AUDIO_FILE_FORMAT,
				selectedDate);

		if (null != returnList) {
			for (File file : returnList) {
				if (null != file) {
					audioList.add(file.getName());
					audioPathList.add(file.getPath());
				}
			}
		}

		if (audioList.size() == 0) {
			welcomeText.setText(NotesConstants.NO_RECORDS_FOUND);
		}
		audioFileNames = new String[audioList.size()];
		audioPath = new String[audioList.size()];

		audioPath = audioPathList.toArray(audioPath);
		audioFileNames = audioList.toArray(audioFileNames);

		return audioFileNames;
	}

}
