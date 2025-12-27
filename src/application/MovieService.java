package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovieService {

    private static String placeHolderImage = CommonFunction.getAssets("placeholder");
    private static String keyWordAPI = CommonFunction.getAPI("keyword");
    private static String idAPI = CommonFunction.getAPI("id");

    private static final String NA = "Not Available!";

    private static String readAll(InputStream is) {
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            int cp;

            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String fetchUrl(String url) {
        try (InputStream is = new URL(url).openStream()) {
            return readAll(is);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void fetchRandomMovie(Consumer<MovieModel> callback) {
        new Thread(() -> {
            try {
                String id = null;
                String title = null;
                String actors = null;
                String poster = null;
                short year = (short) 0;

                do {
                    String keyword = CommonFunction.RandomStringGenerator(3);
                    String searchUrl = keyWordAPI + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
                    String json = fetchUrl(searchUrl);

                    // Collect all matching movies
                    List<MovieEntryModel> movies = new ArrayList<>();

                    Matcher m = Pattern.compile(
                            "\"#IMDB_ID\"\\s*:\\s*\"(tt\\d+)\".*?\"#TITLE\"\\s*:\\s*\"([^\"]+)\".*?\"#YEAR\"\\s*:\\s*(\\d+).*?\"#ACTORS\"\\s*:\\s*\"([^\"]*?)\".*?\"#IMG_POSTER\"\\s*:\\s*\"([^\"]*)\"")
                            .matcher(json);

                    while (m.find()) {
                        String mid = m.group(1).trim().isEmpty() ? NA : m.group(1);
                        String mtitle = m.group(2).trim().isEmpty() ? NA : m.group(2);
                        short myear = Short.parseShort(m.group(3));
                        String mactors = m.group(4).trim().isEmpty() ? NA : m.group(4);
                        String mposter = m.group(5).trim().isEmpty() ? placeHolderImage : m.group(5);

                        movies.add(new MovieEntryModel(mid, mtitle, mactors, mposter, myear));
                    }

                    if (!movies.isEmpty()) {
                        MovieEntryModel selected = movies.get(new Random().nextInt(movies.size()));
                        id = selected.getId();
                        title = selected.getTitle();
                        year = selected.getYear();
                        actors = selected.getActors();
                        poster = selected.getPoster();
                    } else {
                        continue;
                    }
                } while (id == null);

                // Fetch details
                String dtl = fetchUrl(idAPI + id);

                String type = extractString(dtl, "type");
                String synopsis = extractString(dtl, "description");
                String rating = extractString(dtl, "rating");

                type = type.trim().isEmpty() ? NA : type;
                synopsis = synopsis.trim().isEmpty() ? NA : synopsis;
                rating = rating.trim().isEmpty() ? NA : rating;

                MovieModel m = new MovieModel(id, title, year, actors, poster, type, synopsis, rating);
                callback.accept(m);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }).start();
    }

    private static String extractString(String json, String type) {
        try {
            String pattern = null;
            switch (CommonFunction.getCleanKey(type)) {
                case "type": {
                    pattern = "\"@type\"\\s*:\\s*\"([^\"]*)\"";
                    break;
                }
                case "description": {
                    pattern = "(?:.*?\"description\"\\s*:\\s*\"[^\"]*\".*?)\"description\"\\s*:\\s*\"([^\"]*)\"";
                    break;
                }
                case "rating": {
                    pattern = "\"contentRating\"\\s*:\\s*\"([^\"]*)\"";
                    break;
                }
                default: {
                    pattern = "";
                    break;
                }
            }

            Matcher m = Pattern.compile(pattern).matcher(json);
            if (m.find()) {
                String value = m.group(1).trim();
                value = value.replace("\\\"", "\"").replace("&quot;", "'").replace("&apos;", "'");
                return value.isEmpty() ? NA : value;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
