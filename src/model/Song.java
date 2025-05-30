package model;
import java.util.*;

public class Song {
	private int song_id;
	private String title;
	private String artist;
	private String album;
	private int durationSeconds;
	private Date realeseDate;
	
	public Song(int song_id, String title, String artist, String album, int durationSeconds, Date realeseDate) {
		this.song_id = song_id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.durationSeconds = durationSeconds;
		this.realeseDate = realeseDate;
	}

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getDurationSeconds() {
		return durationSeconds;
	}

	public void setDurationSeconds(int durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	public Date getRealeseDate() {
		return realeseDate;
	}

	public void setRealeseDate(Date realeseDate) {
		this.realeseDate = realeseDate;
	}
	     
}
