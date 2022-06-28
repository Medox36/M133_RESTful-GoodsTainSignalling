package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.UserData;
import ch.giuntini.goodstrainsignalling.model.User;
import ch.giuntini.goodstrainsignalling.util.AESEncrypt;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Random;

/**
 * services for authentication and authorization of users
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.15
 * @version 1.1
 */
@Path("user")
public class UserService {

    /**
     * service for logging in
     *
     * @param username of the user that wants to log in
     * @param password of the user that wants to log in
     * @return the position of the two-factor-authentication word in the users words
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @NotNull @FormParam("username") String username,
            @NotNull @FormParam("password") String password
    ) {
        int status = 200;

        User user = UserData.findUser(username, password);
        if (user == null || user.getRole() == null || user.getRole().equals("guest")) {
            status = 404;

            return Response
                    .status(status)
                    .entity("")
                    .build();
        } else {
            int wordNo = new Random().nextInt(5);

            return Response
                    .status(status)
                    .entity(String.valueOf(wordNo + 1))
                    .cookie(userRoleCookie(user.getRole(), 60),
                            plainUserRoleCookie(user.getRole(), 60),
                            wordCookie(String.valueOf(wordNo), 60))
                    .build();
        }
    }

    /**
     * service for checking two-factor-authentication
     *
     * @param word position of the two-factor-authentication word in the users words
     * @param role of the user
     * @param username of the user
     * @param password of the user
     * @param clientWord typed in by the client
     * @return Response
     */
    @POST
    @Path("2factor")
    @Produces(MediaType.TEXT_PLAIN)
    public Response twoFactor(
            @CookieParam("twoFacWord") String word,
            @CookieParam("userRole") String role,
            @NotNull @FormParam("username") String username,
            @NotNull @FormParam("password") String password,
            @NotNull @FormParam("word") String clientWord
    ) {
        int status = 200;

        word = AESEncrypt.decrypt(word);
        role = AESEncrypt.decrypt(role);

        User user = UserData.findUser(username, password);

        if (role == null || role.equals("guest") || user == null || word == null || word.equals("word")) {
            status = 401;

            return Response
                    .status(status)
                    .entity("")
                    .cookie(userRoleCookie("guest", 1),
                            plainUserRoleCookie("guest", 1),
                            wordCookie("word", 1))
                    .build();
        }

        if (user.getWordAt(Integer.parseInt(word)).equals(clientWord)) {
            return Response
                    .status(status)
                    .entity("")
                    .cookie(userRoleCookie(role, 600),
                            plainUserRoleCookie(role, 600),
                            wordCookie("word", 1),
                            authCookie("true", 600))
                    .build();
        } else {
            status = 400;

            return Response
                    .status(status)
                    .entity("")
                    .build();
        }

    }

    /**
     * service for logging out
     *
     * @return Response
     */
    @DELETE
    @Path("logoff")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoff(){

        return Response
                .status(200)
                .entity("")
                .cookie(userRoleCookie("guest", 1),
                        authCookie("false", 1),
                        wordCookie("word", 1))
                .build();
    }

    /**
     * creates a encrypted cookie for authorisation
     *
     * @param userRole value to store in the cookie
     * @param maxAge og the cookie
     * @return a NewCookie
     */
    private NewCookie userRoleCookie(String userRole, int maxAge) {
        return new NewCookie(
                "userRole",
                AESEncrypt.encrypt(userRole),
                "/",
                "", //z.B. abc.ch
                "Fancy-Cookie",
                maxAge,
                false
        );
    }

    /**
     * creates a cookie for authorisation
     *
     * @param userRole value to store in the cookie
     * @param maxAge og the cookie
     * @return a NewCookie
     */
    private NewCookie plainUserRoleCookie(String userRole, int maxAge) {
        return new NewCookie(
                "plainUserRole",
                userRole,
                "/",
                "", //z.B. abc.ch
                "Fancy-Cookie",
                maxAge,
                false
        );
    }

    /**
     * creates a encrypted cookie for authorisation
     *
     * @param value to store in the cookie
     * @param maxAge og the cookie
     * @return a NewCookie
     */
    private NewCookie authCookie(String value, int maxAge) {
        return new NewCookie(
                "auth",
                AESEncrypt.encrypt(value),
                "/",
                "", //z.B. abc.ch
                "Fancy-2Factor-Cookie",
                maxAge,
                false
        );
    }

    /**
     * creates a encrypted cookie for two-factor-authentication
     *
     * @param word value to store in the cookie
     * @param maxAge og the cookie
     * @return a NewCookie
     */
    private NewCookie wordCookie(String word, int maxAge) {
        return new NewCookie(
                "twoFacWord",
                AESEncrypt.encrypt(word),
                "/",
                "", //z.B. abc.ch
                "Fancy-2Factor-Word-Cookie",
                maxAge,
                false
        );
    }

    /**
     * checks if a logged in user is an authorized
     *
     * @param role of the user
     * @param auth of the user
     * @return true if the user is an authorized
     */
    public static boolean isUserAuthorized(String role, String auth) {
        role = AESEncrypt.decrypt(role);
        auth = AESEncrypt.decrypt(auth);
        if (role == null || auth == null) {
            return false;
        }
        return (role.equals("admin") || role.equals("user")) && (auth.equals("true"));
    }

    /**
     * checks if a logged in user is an authorized admin
     *
     * @param role of the user
     * @param auth of the user
     * @return true if the user is an authorized admin
     */
    public static boolean isUserAuthorizedAdmin(String role, String auth) {
        role = AESEncrypt.decrypt(role);
        auth = AESEncrypt.decrypt(auth);
        if (role == null || auth == null) {
            return false;
        }
        return (role.equals("admin") && auth.equals("true"));
    }
}