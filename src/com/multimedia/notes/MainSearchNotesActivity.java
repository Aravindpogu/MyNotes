package com.multimedia.notes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainSearchNotesActivity extends Activity {

	private Button searchButton;
	private RadioGroup radioGroup;
	private RadioButton radioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_search_notes);
		
		radioGroup = (RadioGroup) findViewById(R.id.typeOfNote);
		searchButton = (Button)findViewById(R.id.searchNotes);
		final Context context = this;
		
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String dateString = getDateString();
				int selectedId = radioGroup.getCheckedRadioButtonId();
				radioButton = (RadioButton) findViewById(selectedId);
				
				String text = (String) radioButton.getText();
				Intent searchIntent= null;

				if(text.equals(NotesConstants.TEXT)) {
					DBHandler dbHandler = new DBHandler(context);
					ArrayList<TextNote> notesList = dbHandler.getAllNotesFromCurrentDate(dateString); 
					ArrayList<ParcelableTextNote> parcelableNoteList = getParcelableList(notesList);
					searchIntent = new Intent(context, ShowTextNotesActivity.class);
					searchIntent.putExtra(NotesConstants.SAVED_NOTES, parcelableNoteList);
				}else if(text.equals(NotesConstants.AUDIO)){
					searchIntent = new Intent(context, ShowAudioNotesActivity.class);
					searchIntent.putExtra(NotesConstants.DATE_SELECTED,dateString);
				}else if(text.equals(NotesConstants.VIDEO)){
					searchIntent = new Intent(context, ShowVideoNotesActivity.class);
					searchIntent.putExtra(NotesConstants.DATE_SELECTED,dateString);
				}
				startActivity(searchIntent);
				
			}

			private String getDateString() {
				DatePicker datePicker = (DatePicker) findViewById(R.id.selectDate);
				int   day  = datePicker.getDayOfMonth();
				int   month= datePicker.getMonth() + 1;
				int   year = datePicker.getYear();
				return ""+year+"-"+month+"-"+day;
			}
			
			private ArrayList<ParcelableTextNote> getParcelableList(ArrayList<TextNote> notesList) {
				ArrayList<ParcelableTextNote> parcelableNoteList = new ArrayList<ParcelableTextNote>();
				for(TextNote note : notesList) {
					parcelableNoteList.add(new ParcelableTextNote(note));
				}
				return parcelableNoteList;
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
