package application;

import java.util.Random;
import java.util.stream.Collectors;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class CommonFunction {
    public static String RandomStringGenerator(int length) {
        try {
            return new Random().ints(length, 'A', 'Z' + 1)
                    .mapToObj(i -> String.valueOf((char) i))
                    .collect(Collectors.joining());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Color getColor(String key) {
        switch (getCleanKey(key)) {
            case "blue": {
                return Color.web("#333185");
            }
            case "yellow": {
                return Color.web("#F4EB78");
            }
            default: {
                return null;
            }
        }
    }

    public static Font getFont(String key) {
        switch (getCleanKey(key)) {
            case "heading": {
                return Font.font("Verdana", FontWeight.BOLD, 24);
            }
            case "sub-heading": {
                return Font.font("Verdana", 15);
            }
            case "sub-heading-bold": {
                return Font.font("Verdana", FontWeight.BOLD, 15);
            }
            case "sub-heading-italic": {
                return Font.font("Verdana", FontPosture.ITALIC, 15);
            }
            case "button": {
                return Font.font("Verdana", FontWeight.BOLD, 20);
            }
            case "credits": {
                return Font.font("Verdana", FontPosture.ITALIC, 10);
            }
            default: {
                return null;
            }
        }
    }

    public static String getAssets(String key) {
        switch (getCleanKey(key)) {
            case "main": {
                return "/assets/main.jpg";
            }
            case "reel": {
                return "/assets/reel.jpg";
            }
            case "placeholder": {
                return "https://img.freepik.com/premium-vector/white-exclamation-mark-sign-red-circle-isolated-white-background_120819-332.jpg?w=2000";
            }
            case "css": {
                return "/application/application.css";
            }
            default: {
                return "";
            }
        }
    }

    public static String getText(String key) {
        switch (getCleanKey(key)) {
            case "title": {
                return "The Movie Recommender";
            }
            case "slogan1": {
                return "Don’t know what to watch?";
            }
            case "slogan2": {
                return "Click the button below!";
            }
            case "recbtn": {
                return "Let's Go!";
            }
            case "credit": {
                return "Created By:\n1. Jeremy Saputra Tatuil (https://github.com/6ixB)\n2. Joshua Evans Setiyawan (https://github.com/Evanstanislas)\n3. Lie Reubensto (https://github.com/Ruben165)\n4. Roger Julianto Angryawan (https://github.com/AngryCloudEver)";
            }
            case "actors": {
                return "Notable Actors: ";
            }
            case "type": {
                return "Type: ";
            }
            case "content-rating": {
                return "Content Rating: ";
            }
            case "synopsis": {
                return "Synopsis: ";
            }
            case "agnbtn": {
                return "Again?";
            }
            case "backbtn": {
                return "Back To Main Menu";
            }
            default: {
                return "";
            }
        }
    }

    public static String getAPI(String key) {
        switch (getCleanKey(key)) {
            case "keyword": {
                return "https://imdb.iamidiotareyoutoo.com/search?q=";
            }
            case "id": {
                return "https://imdb.iamidiotareyoutoo.com/search?tt=";
            }
            default: {
                return "";
            }
        }
    }

    public static String getCleanKey(String key) {
        return key.trim().toLowerCase();
    }
}
