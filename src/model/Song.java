package model;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Song.
 */
public class Song {
	
	/** The song id. */
	private int song_id;
	
	/** The title. */
	private String title;
	
	/** The artist. */
	private String artist;
	
	/** The album. */
	private String album;
	
	/** The duration seconds. */
	private int durationSeconds;
	
	/** The realese date. */
	private Date realeseDate;
	
	/**
	 * Instantiates a new song.
	 *
	 * @param song_id the song id
	 * @param title the title
	 * @param artist the artist
	 * @param album the album
	 * @param durationSeconds the duration seconds
	 * @param realeseDate the realese date
	 */
	public Song(int song_id, String title, String artist, String album, int durationSeconds, Date realeseDate) {
		this.song_id = song_id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.durationSeconds = durationSeconds;
		this.realeseDate = realeseDate;
	}

	/**
	 * Gets the song id.
	 *
	 * @return the song id
	 */
	public int getSong_id() {
		return song_id;
	}

	/**
	 * Sets the song id.
	 *
	 * @param song_id the new song id
	 */
	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the artist.
	 *
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Sets the artist.
	 *
	 * @param artist the new artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Gets the album.
	 *
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Sets the album.
	 *
	 * @param album the new album
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Gets the duration seconds.
	 *
	 * @return the duration seconds
	 */
	public int getDurationSeconds() {
		return durationSeconds;
	}

	/**
	 * Sets the duration seconds.
	 *
	 * @param durationSeconds the new duration seconds
	 */
	public void setDurationSeconds(int durationSeconds) {
		this.durationSeconds = durationSeconds;
	}

	/**
	 * Gets the realese date.
	 *
	 * @return the realese date
	 */
	public Date getRealeseDate() {
		return realeseDate;
	}

	/**
	 * Sets the realese date.
	 *
	 * @param realeseDate the new realese date
	 */
	public void setRealeseDate(Date realeseDate) {
		this.realeseDate = realeseDate;
	}
	     
}
