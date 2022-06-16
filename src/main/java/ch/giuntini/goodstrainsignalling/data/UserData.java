package ch.giuntini.goodstrainsignalling.data;

import ch.giuntini.goodstrainsignalling.model.User;
import ch.giuntini.goodstrainsignalling.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserData {

    private static List<User> userList;

    public static User findUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static void readUserJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("locoUserJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            User[] users = objectMapper.readValue(jsonData, User[].class);
            userList.addAll(Arrays.asList(users));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
            readUserJSON();
        }
        return userList;
    }
}
