package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResultView {

    private Scene scene = null;
    private ImageView posterView = new ImageView();
    private Text titleText = new Text();
    private Text yearText = new Text();
    private Text actorsText = new Text();
    private Text typeText = new Text();
    private Text ratingText = new Text();
    private Text synopsisText = new Text();

    private StackPane root = new StackPane();
    private ProgressIndicator loader = new ProgressIndicator();
    private VBox content = new VBox();

    private Runnable onAgain = null;
    private Runnable onBack = null;

    public ResultView() {
        Color yellow = CommonFunction.getColor("yellow");
        Color blue = CommonFunction.getColor("blue");

        Font heading = CommonFunction.getFont("heading");
        Font subHeading = CommonFunction.getFont("sub-heading");
        Font subHeadingItalic = CommonFunction.getFont("sub-heading-italic");
        Font subHeadingBold = CommonFunction.getFont("sub-heading-bold");
        Font button = CommonFunction.getFont("button");

        String cssStyle = CommonFunction.getAssets("css");

        String actors = CommonFunction.getText("actors");
        String type = CommonFunction.getText("type");
        String contentRating = CommonFunction.getText("content-rating");
        String synopsis = CommonFunction.getText("synopsis");
        String agnBtn = CommonFunction.getText("agnbtn");
        String bckBtn = CommonFunction.getText("backbtn");

        posterView.setFitWidth(400);
        posterView.setFitHeight(600);

        titleText.setFont(heading);
        titleText.setFill(yellow);
        titleText.setWrappingWidth(350);

        yearText.setFont(subHeadingItalic);
        yearText.setFill(yellow);

        Text actorsLabel = new Text(actors);
        actorsLabel.setFont(subHeadingBold);
        actorsLabel.setFill(yellow);
        actorsText.setFont(subHeading);
        actorsText.setFill(yellow);
        actorsText.setWrappingWidth(350);

        Text typeLabel = new Text(type);
        typeLabel.setFont(subHeadingBold);
        typeLabel.setFill(yellow);
        typeText.setFont(subHeading);
        typeText.setFill(yellow);
        typeText.setWrappingWidth(350);

        Text ratingLabel = new Text(contentRating);
        ratingLabel.setFont(subHeadingBold);
        ratingLabel.setFill(yellow);
        ratingText.setFont(subHeading);
        ratingText.setFill(yellow);
        ratingText.setWrappingWidth(350);

        Text synopsisLabel = new Text(synopsis);
        synopsisLabel.setFont(subHeadingBold);
        synopsisLabel.setFill(yellow);
        synopsisText.setFont(subHeading);
        synopsisText.setFill(yellow);
        synopsisText.setWrappingWidth(300);

        Button againBtn = new Button(agnBtn);
        againBtn.setId("againButton");
        againBtn.setFont(button);
        againBtn.setOnAction(e -> {
            if (onAgain != null)
                onAgain.run();
        });

        Button backBtn = new Button(bckBtn);
        backBtn.setId("backButton");
        backBtn.setFont(button);
        backBtn.setOnAction(e -> {
            if (onBack != null)
                onBack.run();
        });

        VBox infoBox = new VBox(10, yearText, titleText,
                new VBox(new HBox(actorsLabel), actorsText),
                new VBox(new HBox(typeLabel), typeText),
                new VBox(new HBox(ratingLabel), ratingText),
                new VBox(new HBox(synopsisLabel), synopsisText),
                againBtn, backBtn);
        infoBox.setId("resultScreen");
        infoBox.setAlignment(Pos.CENTER_LEFT);

        HBox mainBox = new HBox(posterView, infoBox);
        mainBox.setAlignment(Pos.CENTER);
        root.setBackground(Background.fill(blue));

        loader.setVisible(false);
        loader.setManaged(false);

        content.getChildren().add(mainBox);
        root.getChildren().addAll(content, loader);

        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(this.getClass().getResource(cssStyle).toExternalForm());
    }

    public void updateMovie(MovieModel movie) {
        posterView.setImage(new Image(movie.getPosterUrl()));
        titleText.setText(movie.getTitle());
        yearText.setText(String.valueOf(movie.getReleaseYear()));
        actorsText.setText(movie.getMainActors());
        typeText.setText(movie.getType());
        ratingText.setText(movie.getContentRating());
        synopsisText.setText(movie.getSynopsis());
    }

    public void showLoading() {
        loader.setVisible(true);
        loader.setManaged(true);
    }

    public void hideLoading() {
        loader.setVisible(false);
        loader.setManaged(false);
    }

    public Scene getScene() {
        return scene;
    }

    public void setOnAgainAction(Runnable action) {
        this.onAgain = action;
    }

    public void setOnBackAction(Runnable action) {
        this.onBack = action;
    }
}
