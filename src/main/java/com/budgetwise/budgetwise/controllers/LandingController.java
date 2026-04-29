package com.budgetwise.budgetwise.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LandingController {

    @FXML private AnchorPane root;

    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private Label logoLabel;

    @FXML private Button loginBtn;
    @FXML private Button registerBtn;
    @FXML private Button startBtn;

    @FXML private VBox teamBox;

    @FXML
    public void initialize() {

        FadeTransition pageFade = new FadeTransition(Duration.seconds(1.5), root);
        pageFade.setFromValue(0);
        pageFade.setToValue(1);
        pageFade.play();

        TranslateTransition titleAnim = new TranslateTransition(Duration.seconds(1), titleLabel);
        titleAnim.setFromY(-80);
        titleAnim.setToY(0);

        FadeTransition titleFade = new FadeTransition(Duration.seconds(1), titleLabel);
        titleFade.setFromValue(0);
        titleFade.setToValue(1);

        ParallelTransition titleAll = new ParallelTransition(titleAnim, titleFade);
        titleAll.play();

        FadeTransition descFade = new FadeTransition(Duration.seconds(1.2), descLabel);
        descFade.setDelay(Duration.seconds(0.5));
        descFade.setFromValue(0);
        descFade.setToValue(1);
        descFade.play();

        ScaleTransition btnScale = new ScaleTransition(Duration.seconds(1), startBtn);
        btnScale.setDelay(Duration.seconds(1));
        btnScale.setFromX(0);
        btnScale.setFromY(0);
        btnScale.setToX(1);
        btnScale.setToY(1);
        btnScale.play();

        TranslateTransition teamAnim = new TranslateTransition(Duration.seconds(1.2), teamBox);
        teamAnim.setFromX(250);
        teamAnim.setToX(0);

        FadeTransition teamFade = new FadeTransition(Duration.seconds(1.2), teamBox);
        teamFade.setFromValue(0);
        teamFade.setToValue(1);

        ParallelTransition teamAll = new ParallelTransition(teamAnim, teamFade);
        teamAll.setDelay(Duration.seconds(0.8));
        teamAll.play();

        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1.2), logoLabel);
        pulse.setFromX(1);
        pulse.setFromY(1);
        pulse.setToX(1.08);
        pulse.setToY(1.08);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
    }
    private final DropShadow shadow = new DropShadow(15, Color.rgb(0,0,0,0.4));

    public void loginHoverIn() {
        loginBtn.setEffect(shadow);
        loginBtn.setTranslateY(-2);
    }

    public void loginHoverOut() {
        loginBtn.setEffect(null);
        loginBtn.setTranslateY(0);
    }

    public void registerHoverIn() {
        registerBtn.setEffect(shadow);
        registerBtn.setTranslateY(-2);
    }

    public void registerHoverOut() {
        registerBtn.setEffect(null);
        registerBtn.setTranslateY(0);
    }

    public void goToLogin() {
        try {
            Parent loginView = FXMLLoader.load(
                    getClass().getResource("/fxml/LoginView.fxml")
            );

            AnchorPane parent = root;

            loginView.setTranslateX(parent.getWidth());

            parent.getChildren().add(loginView);

            TranslateTransition in = new TranslateTransition(Duration.seconds(0.7), loginView);
            in.setToX(0);

            TranslateTransition out = new TranslateTransition(Duration.seconds(0.7), parent.getChildren().get(0));
            out.setToX(-parent.getWidth());

            ParallelTransition pt = new ParallelTransition(in, out);

            pt.setOnFinished(e -> {
                parent.getChildren().remove(0); // remove old page
            });

            pt.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToRegister() {
        try {
            Parent registerView = FXMLLoader.load(
                    getClass().getResource("/fxml/Register.fxml")
            );

            AnchorPane parent = root;

            registerView.setTranslateX(parent.getWidth());

            parent.getChildren().add(registerView);

            TranslateTransition in = new TranslateTransition(Duration.seconds(0.7), registerView);
            in.setToX(0);

            TranslateTransition out = new TranslateTransition(Duration.seconds(0.7), parent.getChildren().get(0));
            out.setToX(-parent.getWidth());

            ParallelTransition pt = new ParallelTransition(in, out);

            pt.setOnFinished(e -> {
                parent.getChildren().remove(0);
            });

            pt.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}