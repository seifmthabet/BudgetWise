package com.budgetwise.budgetwise.utils;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class NavigationUtil {

    public  void goToPage(AnchorPane currentRoot, String fxmlPath) {
        try {
            Parent newPage = FXMLLoader.load(
                    NavigationUtil.class.getResource(fxmlPath)
            );

            Scene scene = currentRoot.getScene();

            newPage.translateXProperty().set(scene.getWidth());

            currentRoot.getChildren().add(newPage);

            TranslateTransition tt = new TranslateTransition(
                    Duration.seconds(0.5),
                    newPage
            );

            tt.setToX(0);
            tt.setOnFinished(e -> currentRoot.getChildren().remove(0));
            tt.play();

        } catch (Exception e) {
            throw new RuntimeException("Error to Navigate Page");
        }
    }
}