package com.multimedia.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.multimedia.notes.TextNote;

public class DBHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "notesdatabase";
	private static final String TEST_NOTE = "note";
	private static final String ID = "id";
	private static final String VALUE = "value";
	private static final String TIME = "time";
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TEST_NOTE + " (" + ID
			+ " INTEGER PRIMARY KEY, " + TIME + " TEXT, " + VALUE + " TEXT);";

	public DBHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TEST_NOTE);
		onCreate(db);
	}

	// Adding new note
	public void addNote(TextNote note) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(VALUE, note.getValue());
		values.put(TIME, note.getTextNoteTimeStamp());
		db.insert(TEST_NOTE, null, values);
		db.close(); 
	}

	/**
	 * Method to filter Notes on a given date
	 * 
	 * @param currentDate
	 * @return
	 */
	public ArrayList<TextNote> getAllNotesFromCurrentDate(String currentDate) {
		ArrayList<TextNote> notesList = new ArrayList<TextNote>();
		String selectQuery = "SELECT  * FROM " + TEST_NOTE + " where time like '" + currentDate + "%' order by time desc;";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				TextNote note = new TextNote();
				note.setId(Integer.parseInt(cursor.getString(0)));
				note.setTextNoteTimeStamp(cursor.getString(1));
				note.setValue(cursor.getString(2));
				notesList.add(note);
			} while (cursor.moveToNext());
		}
		return notesList;
	}

}
