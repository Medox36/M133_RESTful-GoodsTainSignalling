package ch.giuntini.goodstrainsignalling.model;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.15
 * @version 1.1
 */
public class User {
    private String userUUID;
    private String username;
    private String password;
    private String role;
    private String[] words;

    /**
     * gets userUUID
     *
     * @return value of userUUID
     */

    public String getUserUUID() {
        return userUUID;
    }

    /**
     * sets userUUID
     *
     * @param userUUID the value to set
     */

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    /**
     * gets username
     *
     * @return value of username
     */

    public String getUsername() {
        return username;
    }

    /**
     * sets username
     *
     * @param username the value to set
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets password
     *
     * @return value of password
     */

    public String getPassword() {
        return password;
    }

    /**
     * sets password
     *
     * @param password the value to set
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets role
     *
     * @return value of role
     */

    public String getRole() {
        return role;
    }

    /**
     * sets role
     *
     * @param role the value to set
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * gets the 2 factor authentication words
     *
     * @return authentication words
     */
    public String[] getWords() {
        return words;
    }

    /**
     * sets the 2 factor authentication words
     *
     * @param words for 2 factor authentication
     */
    public void setWords(String[] words) {
        this.words = words;
    }

    /**
     * gets the 2 factor authentication word at a given index
     *
     * @param index of the word
     * @return authentication word
     */
    public String getWordAt(int index) {
        return words[index];
    }
}
