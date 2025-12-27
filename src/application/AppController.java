package application;

import javafx.application.Platform;
import javafx.stage.Stage;

public class AppController {

    private Stage stage;
    private MovieService movieService;
    private MainMenuView mainMenuView;
    private ResultView resultView;

    public AppController(Stage stage) {
        this.stage = stage;
        this.movieService = new MovieService();
        this.mainMenuView = new MainMenuView();
        this.resultView = new ResultView();

        setupActions();
        showMainMenu();
    }

    private void setupActions() {
        mainMenuView.setOnRecommendAction(() -> {
            mainMenuView.showLoading();
            movieService.fetchRandomMovie(movie -> {
                Platform.runLater(() -> {
                    resultView.updateMovie(movie);
                    resultView.hideLoading();
                    mainMenuView.hideLoading();
                    stage.setScene(resultView.getScene());
                });
            });
        });

        resultView.setOnAgainAction(() -> {
            resultView.showLoading();
            movieService.fetchRandomMovie(movie -> {
                Platform.runLater(() -> {
                    resultView.updateMovie(movie);
                    resultView.hideLoading();
                    stage.setScene(resultView.getScene());
                });
            });
        });

        resultView.setOnBackAction(this::showMainMenu);
    }

    private void showMainMenu() {
        stage.setScene(mainMenuView.getScene());
    }
}
