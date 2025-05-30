package model;

// TODO: Auto-generated Javadoc
/**
 * The Class Genre.
 */
public class Genre {
	
	/** The genre id. */
	private int genre_id;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new genre.
	 *
	 * @param genre_id the genre id
	 * @param name the name
	 */
	public Genre(int genre_id, String name) {
		this.genre_id = genre_id;
		this.name = name;
	}

	/**
	 * Gets the genre id.
	 *
	 * @return the genre id
	 */
	public int getGenre_id() {
		return genre_id;
	}

	/**
	 * Sets the genre id.
	 *
	 * @param genre_id the new genre id
	 */
	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
