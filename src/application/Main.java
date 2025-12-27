package application;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		@SuppressWarnings("unused")
		AppController controller = new AppController(primaryStage);

		String reelImage = CommonFunction.getAssets("reel");
		String title = CommonFunction.getText("title");

		primaryStage.setTitle(title);
		primaryStage.getIcons().add(new Image(reelImage));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
