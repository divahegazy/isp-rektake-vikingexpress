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
        // Create one single AppModel for the entire application
        AppModel sharedModel = new AppModel();

        // Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = loader.load();

        // Inject the single model into the MainController
        MainController mainController = loader.getController();
        mainController.setAppModel(sharedModel);

        // Show the scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("VikingExpress Fleet Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}