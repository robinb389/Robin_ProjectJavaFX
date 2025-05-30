package model;

public class Rating {
	private int rating_id;
	private int rating;
	private String review_text;
	private int user_id;
	private int song_id;
	
	public Rating(int rating_id, int rating, String review_text, int user_id, int song_id) {
		this.rating_id = rating_id;
		this.rating = rating;
		this.review_text = review_text;
		this.user_id = user_id;
		this.song_id = song_id;
	}

	public int getRating_id() {
		return rating_id;
	}

	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview_text() {
		return review_text;
	}

	public void setReview_text(String review_text) {
		this.review_text = review_text;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
	
	
}
