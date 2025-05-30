package model;

// TODO: Auto-generated Javadoc
/**
 * The Class Rating.
 */
public class Rating {
	
	/** The rating id. */
	private int rating_id;
	
	/** The rating. */
	private int rating;
	
	/** The review text. */
	private String review_text;
	
	/** The user id. */
	private int user_id;
	
	/** The song id. */
	private int song_id;
	
	/**
	 * Instantiates a new rating.
	 *
	 * @param rating_id the rating id
	 * @param rating the rating
	 * @param review_text the review text
	 * @param user_id the user id
	 * @param song_id the song id
	 */
	public Rating(int rating_id, int rating, String review_text, int user_id, int song_id) {
		this.rating_id = rating_id;
		this.rating = rating;
		this.review_text = review_text;
		this.user_id = user_id;
		this.song_id = song_id;
	}

	/**
	 * Gets the rating id.
	 *
	 * @return the rating id
	 */
	public int getRating_id() {
		return rating_id;
	}

	/**
	 * Sets the rating id.
	 *
	 * @param rating_id the new rating id
	 */
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating the new rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Gets the review text.
	 *
	 * @return the review text
	 */
	public String getReview_text() {
		return review_text;
	}

	/**
	 * Sets the review text.
	 *
	 * @param review_text the new review text
	 */
	public void setReview_text(String review_text) {
		this.review_text = review_text;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * Sets the user id.
	 *
	 * @param user_id the new user id
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
	
	
}
