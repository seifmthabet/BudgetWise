package com.budgetwise.budgetwise.utils;
import com.budgetwise.budgetwise.Main;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.util.Objects;

/**
 * NavigationUtil component.
 */
public class NavigationUtil {

    /**
     * goToPage operation.
     * @param currentRoot parameter value
     * @param fxmlPath parameter value
     * @return result value
     */
    public static void goToPage(AnchorPane currentRoot, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(Main.class.getResource(fxmlPath))
            );

            Parent view = loader.load();

            currentRoot.applyCss();
            currentRoot.layout();

            double width = currentRoot.getWidth();

            if (width == 0) {
                width = currentRoot.getPrefWidth(); // fallback
            }

            view.setTranslateX(width);

            currentRoot.getChildren().add(view);

            Node oldView = currentRoot.getChildren().get(0);

            TranslateTransition in = new TranslateTransition(Duration.seconds(0.7), view);
            in.setFromX(width);
            in.setToX(0);

            TranslateTransition out = new TranslateTransition(Duration.seconds(0.7), oldView);
            out.setFromX(0);
            out.setToX(-width);

            ParallelTransition pt = new ParallelTransition(in, out);

            pt.setOnFinished(e -> currentRoot.getChildren().remove(oldView));

            pt.play();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * goToPage operation.
     * @param root parameter value
     * @param fxmlPath parameter value
     * @return result value
     */
    public static void goToPage(BorderPane root, String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(
                    Objects.requireNonNull(Main.class.getResource(fxmlPath))
            );

            root.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
