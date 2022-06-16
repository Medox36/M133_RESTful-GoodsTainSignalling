package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.UserData;
import ch.giuntini.goodstrainsignalling.model.User;
import ch.giuntini.goodstrainsignalling.util.AESEncrypt;

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

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
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
                    .entity(String.valueOf(wordNo))
                    .cookie(userRoleCookie(user.getRole(), 60),
                            wordCookie(String.valueOf(wordNo), 60))
                    .build();
        }
    }

    @POST
    @Path("loginForm")
    @Produces(MediaType.TEXT_PLAIN)
    public Response twoFactor(
            @CookieParam("twoFacWord") String word,
            @CookieParam("userRole") String role,
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("word") String clientWord
    ) {
        int status = 200;

        word = AESEncrypt.decrypt(word);
        role = AESEncrypt.decrypt(role);

        User user = UserData.findUser(username, password);

        if (role == null || role.equals("guest") || user == null || word == null || word.equals("word")) {
            status = 403;

            return Response
                    .status(status)
                    .entity("")
                    .cookie(userRoleCookie("guest", 1),
                            wordCookie("word", 1))
                    .build();
        }

        if (user.getWordAt(Integer.parseInt(word)).equals(clientWord)) {
            return Response
                    .status(status)
                    .entity("")
                    .cookie(userRoleCookie(role, 600),
                            wordCookie("word", 1),
                            authCookie("true", 600))
                    .build();
        } else {
            status = 404;

            return Response
                    .status(status)
                    .entity("")
                    .cookie(userRoleCookie("guest", 1),
                            wordCookie("word", 1))
                    .build();
        }

    }

    @DELETE
    @Path("logoff")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoff(){

        return Response
                .status(200)
                .entity("")
                .cookie(userRoleCookie("guest", 1),
                        authCookie("false", 1))
                .build();
    }


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

    private NewCookie wordCookie(String word, int maxAge) {
        return new NewCookie(
                "twoFacWord",
                AESEncrypt.encrypt(word),
                "/",
                "", //z.B. abc.ch
                "Fancy-2Factor-Cookie",
                maxAge,
                false
        );
    }

    public static boolean isUserAuthorized(String role, String auth) {
        role = AESEncrypt.decrypt(role);
        auth = AESEncrypt.decrypt(auth);
        if (role == null || auth == null) {
            return false;
        }
        return (role.equals("admin") || role.equals("user")) && (auth.equals("true"));
    }

    public static boolean isUserAuthorizedAdmin(String role, String auth) {
        role = AESEncrypt.decrypt(role);
        auth = AESEncrypt.decrypt(auth);
        if (role == null || auth == null) {
            return false;
        }
        return (role.equals("admin") && auth.equals("true"));
    }
}