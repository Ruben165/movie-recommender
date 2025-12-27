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

public class MainMenuView {

    private StackPane root = new StackPane();
    private Scene scene;
    private Runnable onRecommend = null;
    private ProgressIndicator loader = new ProgressIndicator();

    public MainMenuView() {
        Font heading = CommonFunction.getFont("heading");
        Font subHeading = CommonFunction.getFont("sub-heading");
        Font button = CommonFunction.getFont("button");
        Font credits = CommonFunction.getFont("credits");

        Color blue = CommonFunction.getColor("blue");
        Color yellow = CommonFunction.getColor("yellow");

        String mainImage = CommonFunction.getAssets("main");
        String cssStyle = CommonFunction.getAssets("css");

        String titleText = CommonFunction.getText("title");
        String slogan1 = CommonFunction.getText("slogan1");
        String slogan2 = CommonFunction.getText("slogan2");
        String recBtn = CommonFunction.getText("recbtn");
        String credit = CommonFunction.getText("credit");

        Image bgImage = new Image(mainImage);
        ImageView bgView = new ImageView(bgImage);

        Text title = new Text(titleText);
        title.setFont(heading);
        title.setFill(yellow);

        Text sub1 = new Text(slogan1);
        sub1.setFont(subHeading);
        sub1.setFill(yellow);

        Text sub2 = new Text(slogan2);
        sub2.setFont(subHeading);
        sub2.setFill(yellow);
        sub2.setWrappingWidth(350);

        Button recommendBtn = new Button(recBtn);
        recommendBtn.setId("recommendButton");
        recommendBtn.setFont(button);
        recommendBtn.setOnAction(e -> {
            if (onRecommend != null)
                onRecommend.run();
        });

        Text creditsText = new Text(credit);
        creditsText.setFont(credits);
        creditsText.setFill(yellow);

        VBox textBox = new VBox(10, title, sub1, sub2, recommendBtn, creditsText);
        textBox.setId("titleScreen");
        textBox.setAlignment(Pos.CENTER_LEFT);

        loader.setVisible(false);
        loader.setManaged(false);
        StackPane.setAlignment(loader, Pos.CENTER);

        HBox mainBox = new HBox(bgView, textBox);
        root.getChildren().addAll(mainBox, loader);
        root.setBackground(Background.fill(blue));

        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(this.getClass().getResource(cssStyle).toExternalForm());
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

    public void setOnRecommendAction(Runnable action) {
        this.onRecommend = action;
    }
}
