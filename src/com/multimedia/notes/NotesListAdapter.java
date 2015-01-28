package com.multimedia.notes;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.multimedia.notes.R;
import com.multimedia.notes.TextNote;

public class NotesListAdapter extends ArrayAdapter<TextNote> {

	private List<TextNote> notes;

	public NotesListAdapter(Context context, int resource, List<TextNote> notes) {
		super(context, resource, notes);
		this.notes = notes;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null) {
			LayoutInflater inflator;
			inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(R.layout.notes_display_list, null);
		}

		TextNote textNote = notes.get(position);

		if (textNote != null) {
			TextView idView = (TextView) view.findViewById(R.id.id);
			TextView valueView = (TextView) view.findViewById(R.id.value);

			if (idView != null) {
				idView.setText(textNote.getTextNoteTimeStamp().substring("yyyy MM dd".length()));
			}
			
			if (valueView != null) {
				valueView.setText(textNote.getValue());
			}
		}
		return view;
	}
}

