package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import org.apache.commons.lang3.RandomStringUtils;

public class Main extends Application {
	// Main Stage
	private VBox mainContainer = new VBox();
	private VBox starter = new VBox();
	private VBox titleScreen = new VBox();
	private VBox resultScreen = new VBox();
	private Image iconImg = new Image("/assets/reel.jpg");
	
	// Sub-Stage
	private HBox frontPageTitleController = new HBox();
	private HBox mainContentController = new HBox();
	private HBox againController = new HBox();
	
	// Elements
	private Text frontPageTitle = new Text();
	private Text frontPageSubtitle1 = new Text();
	private Text frontPageSubtitle2 = new Text();
	private Text credits = new Text();
	private Text title = new Text();
	private Text releasedYear = new Text();
	private Text notableActorsTitle = new Text("Notable Actors: ");
	private Text notableActors = new Text();
	private Text showTypeTitle = new Text("Type: ");
	private Text showType = new Text();
	private Text ratingTitle = new Text("Content Rating: ");
	private Text rating = new Text();
	private Text showSynopsisTitle = new Text("Show Synopsis: ");
	private Text showSynopsis = new Text();
	private Image mainMenuImage = new Image("/assets/Main Menu Image.png");
	private ImageView mainMenuImageView = new ImageView();
	private ImageView resultImageView = new ImageView();
	private Button randomer = new Button("Click Me");
	private Button backer = new Button("Back To Main Menu");
	private Button again = new Button("Again?");
	
	// JSON Objects
	private JSONObject movie;
	private JSONObject movieDetail;
	
	// Movie Object
	private Movie theMovie;
	
	// Value dari Key Movie
	private String id;
	private String movieTitle;
	private String releaseYear;
	private String mainActors;
	private String posterUrl;
	private String type;
	private String synopsis;
	private String contentRating;
	
	// Scene
	private Scene mainScene;
	private Scene resScene;
	private Integer mainCount=0;
	private Integer resCount=0;
	
 	private String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	 }
	
	private JSONObject getRandomMovie(String keyword, Boolean isDetail) {
		JSONObject json = null;
		
		// Fetching Dari URL
		try {
			if(!isDetail) {				
				json = readJsonFromUrl(("https://search.imdbot.workers.dev/?q=" + keyword));
			}
			else {
				json = readJsonFromUrl(("https://search.imdbot.workers.dev/?tt=" + keyword));
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		// Pecah isi description jadi array of json objects
		ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();
		
		JSONArray jArray = null;
		
		try {
			if(!isDetail) {				
				jArray = (JSONArray)json.get("description");
			}
			else {
				json = (JSONObject) json.get("short");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
		if (jArray != null && !isDetail) { 
			int randomChoosing = (int) Math.floor(Math.random() * (jArray.length() - 1));
			
			try {
				listdata.add(jArray.getJSONObject(randomChoosing));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return listdata.size() > 0 ? listdata.get(0) : null;
		}
		else {
			return json;
		}
	}
	
	public void mainMenu(Stage primaryStage) {
		mainCount++;
		
		/* Ada frontPageTitleController -> HBox
		 * Ada frontPageTitle -> Text
		 * Ada randomer -> Button
		 */
		mainMenuImageView.setImage(mainMenuImage);
		
		titleScreen.setId("titleScreen");
		
		starter.setBackground(Background.fill(Color.web("#333185")));
		
		// Title
		frontPageTitle.setText("The Movie Recommender");
		frontPageTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 36));
		frontPageTitle.setFill(Color.web("#F4EB78"));
		frontPageTitle.setWrappingWidth(350);
		VBox.setMargin(frontPageTitle, new Insets(0, 0, 15, 0));
		
		// Subtitle
		frontPageSubtitle1.setText("Don’t know what to watch?");
		frontPageSubtitle1.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
		frontPageSubtitle1.setFill(Color.web("#F4EB78"));
		frontPageSubtitle1.setWrappingWidth(350);
		VBox.setMargin(frontPageSubtitle1, new Insets(0, 0, 10, 0));
		
		frontPageSubtitle2.setText("Click the button below and we will give you something fresh to watch!");
		frontPageSubtitle2.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
		frontPageSubtitle2.setFill(Color.web("#F4EB78"));
		frontPageSubtitle2.setWrappingWidth(350);
		
		// Button
		randomer.setText("Recommend Me \nA Movie!");
		randomer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		randomer.setId("recommendButton");
		VBox.setMargin(randomer, new Insets(30, 0, 30, 0));
		randomer.setOnAction(f -> {
			resultScene(primaryStage);
		});
		
		// Credits
		credits.setText("Created By:\r\n"
				+ "Jeremy Saputra Tatuil\r\n"
				+ "Joshua Evans Setiyawan\r\n"
				+ "Lie Reubensto\r\n"
				+ "Roger Julianto Angryawan");
		credits.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
		credits.setFill(Color.web("#F4EB78"));
		
		if(mainCount==1) {
			mainScene = new Scene(starter,800,600);
			mainScene.getStylesheets().add(this.getClass().getResource("/application/application.css").toExternalForm());
		}
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("The Movie Recommender");
		primaryStage.getIcons().add(iconImg);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void resultScene(Stage primaryStage) {
		resCount++;
		try {
			// Fetch Random Movie
			do {				
				String randomWord = RandomStringUtils.randomAlphabetic(3);
				movie = getRandomMovie(randomWord, false);
			} while (movie == null);
			
			// Store movie variable from main api url
			id = movie.get("#IMDB_ID").toString();
			movieTitle = movie.get("#TITLE").toString();
			
			try {
				releaseYear = movie.get("#YEAR").toString();
			} catch(Exception e) {
				releaseYear = "Year Not Available";
			}
			
			try {
				mainActors = movie.get("#ACTORS").toString();
			} catch(Exception e) {
				mainActors = "Actors Not Available";
			}
			
			try {
				posterUrl = movie.get("#IMG_POSTER").toString();
			} catch(Exception e) {
				posterUrl = "https://img.freepik.com/premium-vector/white-exclamation-mark-sign-red-circle-isolated-white-background_120819-332.jpg?w=2000";
			}
			
			// Get Movie Details
			movieDetail = getRandomMovie(id, true);
			
			try {
				type = movieDetail.getString("@type");
			} catch (Exception e) {
				type = "Type Not Available";
			}
			
			try {
				synopsis = movieDetail.getString("description");
				synopsis = synopsis.replace("&quot;", "'");
				synopsis = synopsis.replace("&quot", "'");
				synopsis = synopsis.replace("&apos;", "'");
				synopsis = synopsis.replace("&apos", "'");
			} catch (Exception e) {
				synopsis = "Description Not Available";
			}
			
			try {
				contentRating = movieDetail.getString("contentRating");
			} catch (Exception e) {
				contentRating = "Content Rating Not Available";
			}
			
			// Buat Object
			theMovie = new Movie(id, movieTitle, releaseYear, mainActors, posterUrl, type, synopsis, contentRating);
			
			resultScreen.setId("resultScreen");
			mainContainer.setBackground(Background.fill(Color.web("#333185")));
			
			resultImageView.setImage(new Image(theMovie.getPosterUrl()));
			resultImageView.setFitWidth(400);
			resultImageView.setFitHeight(600);
			
			// releasedYear
			releasedYear.setText(theMovie.getReleaseYear());
			releasedYear.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
			releasedYear.setFill(Color.web("#F4EB78"));
			
			// Title
			title.setText(theMovie.getTitle());
			title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
			title.setFill(Color.web("#F4EB78"));
			title.setWrappingWidth(350);
			VBox.setMargin(title, new Insets(5, 0, 25, 0));
			
			// Actors
			notableActorsTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			notableActorsTitle.setFill(Color.web("#F4EB78"));
			notableActors.setText(theMovie.getMainActors());
			notableActors.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
			notableActors.setFill(Color.web("#F4EB78"));
			notableActors.setWrappingWidth(300);
			VBox.setMargin(notableActors, new Insets(8, 0, 20, 0));
			
			// Type
			showTypeTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			showTypeTitle.setFill(Color.web("#F4EB78"));
			showType.setText(theMovie.getType());
			showType.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
			showType.setFill(Color.web("#F4EB78"));
			VBox.setMargin(showType, new Insets(8, 0, 20, 0));
			
			// Rating
			ratingTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			ratingTitle.setFill(Color.web("#F4EB78"));
			rating.setText(theMovie.getContentRating());
			rating.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
			rating.setFill(Color.web("#F4EB78"));
			VBox.setMargin(rating, new Insets(8, 0, 20, 0));
			
			// Synopsis
			showSynopsisTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			showSynopsisTitle.setFill(Color.web("#F4EB78"));
			showSynopsis.setText(theMovie.getSynopsis());
			showSynopsis.setFont(Font.font("verdana",FontWeight.NORMAL, FontPosture.REGULAR, 15));
			showSynopsis.setFill(Color.web("#F4EB78"));
			showSynopsis.setWrappingWidth(300);
			VBox.setMargin(showSynopsis, new Insets(8, 0, 20, 0));
			
			// Button	
			// Again
			again.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
			again.setId("againButton");
			VBox.setMargin(again, new Insets(0, 0, 10, 0));
			again.setOnAction(h -> {
				resultScene(primaryStage);
			});
			againController.setAlignment(Pos.CENTER);
			VBox.setMargin(againController, new Insets(10, 0, 0, 0));
			
			// Backer
			backer.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
			backer.setId("backButton");
			backer.setOnAction(h -> {
				mainMenu(primaryStage);
			});
			
			if(resCount==1) resScene = new Scene(mainContainer,800,600);
			resScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(resScene);
			primaryStage.getIcons().add(iconImg);
			primaryStage.setTitle("The Movie Recommender");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		titleScreen.getChildren().addAll(frontPageTitle, frontPageSubtitle1, frontPageSubtitle2, randomer, credits);
		frontPageTitleController.getChildren().addAll(mainMenuImageView, titleScreen);
		starter.getChildren().add(frontPageTitleController);

		resultScreen.getChildren().addAll(releasedYear, title, notableActorsTitle, notableActors, showTypeTitle, showType, ratingTitle, rating, showSynopsisTitle, showSynopsis, again, backer);
		mainContentController.getChildren().addAll(resultImageView, resultScreen);
		mainContainer.getChildren().add(mainContentController);
		mainMenu(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
