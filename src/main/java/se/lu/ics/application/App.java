package se.lu.ics.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.lu.ics.controllers.MainController;
import se.lu.ics.models.AppModel;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the MainView
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        //single shared AppModel instance makes it possible to share data between controllers without it diseappering when switching subviews
        AppModel model = new AppModel();
        mainController.setModel(model);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("VikingExpress Fleet Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}