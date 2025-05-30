package model;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {
	
	/** The user id. */
	private int user_id;
	
	/** The username. */
	private String username;
	
	/** The email. */
	private String email;
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param user_id the user id
	 * @param username the username
	 * @param email the email
	 * @param password the password
	 */
	public User(int user_id, String username, String email, String password) {
		this.user_id = user_id;
		this.username = username;
		this.email = email;
		this.password = password;
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
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
