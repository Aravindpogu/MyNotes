package com.multimedia.notes;

/**
 * 
 * @author aravind
 *
 */
public class TextNote {

	private int id;
	private String value;
	private String textNoteTimeStamp;

	public TextNote(){
	}
	
	public TextNote(String value,String textNoteTimeStamp){
		this.value = value;
		this.textNoteTimeStamp = textNoteTimeStamp;
	}
	
	public TextNote(int id, String value,String textNoteTimeStamp){
		this.id = id;
		this.value = value;
		this.textNoteTimeStamp = textNoteTimeStamp;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTextNoteTimeStamp() {
		return textNoteTimeStamp;
	}

	public void setTextNoteTimeStamp(String textNoteTimeStamp) {
		this.textNoteTimeStamp = textNoteTimeStamp;
	}

}
