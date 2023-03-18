package com.kopyshov;

import com.kopyshov.actions.CreatureTurn;
import com.kopyshov.model.entities.creatures.Creature;
import com.kopyshov.model.ActionMapModel;
import com.kopyshov.gui.BasicLayer;
import com.kopyshov.gui.CreatureLayer;
import com.kopyshov.gui.StaticLayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

    public Simulation(Stage stage) throws IOException {
        //Создание начальной страницы fxml (JavaFX)
        FXMLLoader mainLoader = generateStartPage(stage);
        //Передача Контроллера (функционал кнопок)
        controller = mainLoader.getController();
        controller.simulation = this; //Is this correct?
    }

    private final Controller controller;
    public void generateActionMap() {
        int width = controller.widthValue.getValue(); //пользовательские настройки
        int height = controller.heightValue.getValue();

        generateActionMapModel(width, height);
        generateActionMapView(width, height);
        initializeTimeline();
    }

    private ActionMapModel actionMapModel;
    private void generateActionMapModel(int width, int height) {
        actionMapModel = new ActionMapModel(width, height);
        actionMapModel.addPermanentObstacles();
        actionMapModel.addCreatures();
        actionMapModel.addSomeGrass();
    }

    private CreatureLayer creatureLayer;
    private StaticLayer staticLayer;
    private CreatureTurn creatureTurn;
    private void generateActionMapView(int width, int height) {
        //Создаем "слои"
        BasicLayer basicLayer = new BasicLayer(width, height);
        creatureLayer = new CreatureLayer(width, height);
        staticLayer = new StaticLayer(width, height);
        controller.platform.getChildren().addAll(basicLayer, staticLayer, creatureLayer);

        //Обновляем View
        basicLayer.showObstacles(actionMapModel);
        staticLayer.updateStaticLayer(actionMapModel);
        creatureLayer.updateCreatureLayer(actionMapModel);


        creatureTurn = new CreatureTurn(creatureLayer, staticLayer, actionMapModel);
    }

    private Timeline lifeCycle;
    public static final int MAX_AGE = 10; //возраст определен количеством сделанных ходов
    private static final int SIM_SPEED = 300; //в миллисекунах
    private void initializeTimeline() {
        lifeCycle = new Timeline();
        EventHandler<ActionEvent> eventHandler = event -> {
            try {
                Creature nextCreature = actionMapModel.getCreatureQueue().removeFirst();
                checkSomeStates(nextCreature);
                nextCreature.reduceAge();
                if (nextCreature.getAge() != 0) {
                    actionMapModel.getCreatureQueue().add(nextCreature);
                    creatureTurn.setCreature(nextCreature);
                    creatureTurn.executeTurn(actionMapModel, lifeCycle);
                } else {
                    actionMapModel.getHerbivores().remove(nextCreature.getPosition());
                    actionMapModel.getCarnivores().remove(nextCreature.getPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lifeCycle.play();
            }
        };
        KeyFrame k1 = new KeyFrame(Duration.millis(SIM_SPEED), eventHandler);
        lifeCycle.getKeyFrames().addAll(k1);
        lifeCycle.setCycleCount(Animation.INDEFINITE);
    }


    private void checkSomeStates(Creature nextCreature) {
        if ((MAX_AGE - nextCreature.getAge()) % 2 == 0) {
            nextCreature.setHungry(true);
        }
        int pregnantChance = ThreadLocalRandom.current().nextInt(0, 100);
        if (pregnantChance < 30) {
            actionMapModel.createGrass();
        }
        if (pregnantChance > 30 & pregnantChance < 45) {
            actionMapModel.createHerbivore();
        }
        if (pregnantChance > 45 & pregnantChance < 50) {
            actionMapModel.createCarnivore();
        }
    }

    public void playSimulation() {
        lifeCycle.play();
    }

    public void pauseSimulation() {
        lifeCycle.pause();
    }

    public void stopSimulation(){
        lifeCycle.stop();
        actionMapModel.getHerbivores().clear();
        actionMapModel.getCarnivores().clear();
        actionMapModel.getCreatureQueue().clear();
        actionMapModel.getGrassList().clear();
        actionMapModel.getObstacles().clear();
    }


    private FXMLLoader generateStartPage(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Parent mainView = mainLoader.load();
        Scene scene = new Scene(mainView, 640, 480);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        return mainLoader;
    }
}
