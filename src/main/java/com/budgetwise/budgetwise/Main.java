package com.budgetwise.budgetwise;

import com.budgetwise.budgetwise.utils.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseManager.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterView.fxml"));
        Scene scene = new Scene(loader.load(), 1080, 1920);
        //scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());

        stage.setTitle("BudgetWise");
        stage.setMinWidth(1080);
        stage.setMinHeight(1920);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        DatabaseManager.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
