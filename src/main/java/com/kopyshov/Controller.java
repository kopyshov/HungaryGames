package com.kopyshov;

import com.kopyshov.model.ActionMapModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Controller {
    public StackPane platform;
    public Button generateButton;
    public Button playSimButton;
    public Button pauseSimButton;
    public Button stopSimButton;
    public Spinner<Integer> widthValue;
    public Spinner<Integer> heightValue;
    public BorderPane mainView;
    public TextArea terminal;
    public Simulation simulation;

    public void initialize() {

    }
    public void playSimulation(ActionEvent event) {
        simulation.playSimulation();

        generateButton.setDisable(true);
        pauseSimButton.setDisable(false);
        stopSimButton.setDisable(false);
    }

    public void pauseSimulation(ActionEvent event) {
        simulation.pauseSimulation();
    }

    public void stopSimulation(ActionEvent event) {
        simulation.stopSimulation();
        terminal.clear();
        playSimButton.setDisable(true);
        generateButton.setDisable(false);
        platform.getChildren().clear();
    }

    public void generateActionMap(ActionEvent event) {
        simulation.generateActionMap();
        playSimButton.setDisable(false);
    }
}
