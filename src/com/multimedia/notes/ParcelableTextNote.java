package com.multimedia.notes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author Sowjanya
 *
 */
public class ParcelableTextNote implements Parcelable {

	private TextNote textNote;

	public ParcelableTextNote(TextNote textNote) {
		this.textNote = textNote;
	}

	public TextNote getNote() {
		return textNote;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(textNote.getId());
		out.writeString(textNote.getValue());
		out.writeString(textNote.getTextNoteTimeStamp());
	}

	public static final Parcelable.Creator<ParcelableTextNote> CREATOR = new Parcelable.Creator<ParcelableTextNote>() {
		@Override
		public ParcelableTextNote createFromParcel(Parcel in) {
			return new ParcelableTextNote(in);
		}

		@Override
		public ParcelableTextNote[] newArray(int size) {
			return new ParcelableTextNote[size];
		}
	};
	
	private ParcelableTextNote(Parcel in) {
		Integer id = in.readInt();
		String value = in.readString();
		String noteTime = in.readString();
		textNote = new TextNote(id, value, noteTime);
	}
}
