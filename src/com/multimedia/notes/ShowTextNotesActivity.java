package com.multimedia.notes;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowTextNotesActivity extends ListActivity {

	private TextView welcomeText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_notes);
		
		welcomeText = (TextView) findViewById(R.id.welcomeText);
		Intent recieved = getIntent();
		
		ArrayList<ParcelableTextNote> parcelableNoteList = recieved.getParcelableArrayListExtra(NotesConstants.SAVED_NOTES);
		ArrayList<TextNote> notes = getListFromParcelledNotes(parcelableNoteList);

		if (notes == null || notes.size() == 0) {
			welcomeText.setText("No Text Notes found.");
		}
		
		if(notes.size() >0){
			setTitle("Texts saved on " +notes.get(0).getTextNoteTimeStamp().substring(0, "yyyy MM dd".length()));
		}

		setListAdapter(new NotesListAdapter(this, R.layout.notes_display_list, notes));
	}

	private ArrayList<TextNote> getListFromParcelledNotes(ArrayList<ParcelableTextNote> parcelableNoteList) {
		ArrayList<TextNote> notes = new ArrayList<TextNote>();
		for (ParcelableTextNote parcelNote : parcelableNoteList) {
			notes.add(parcelNote.getNote());
		}
		return notes;
	}
}
