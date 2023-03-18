package com.kopyshov.actions;

import com.kopyshov.model.entities.creatures.Creature;
import com.kopyshov.model.ActionMapModel;
import com.kopyshov.model.GraphNode;
import com.kopyshov.gui.CreatureLayer;
import com.kopyshov.gui.StaticLayer;
import javafx.animation.Timeline;
import javafx.application.Platform;

import java.util.LinkedList;

public class CreatureTurn {
    Creature creature;
    private final CreatureLayer creatureLayer;
    private final StaticLayer staticLayer;
    private final ActionMapModel actionMapModel;

    public CreatureTurn(CreatureLayer creatureLayer, StaticLayer staticLayer, ActionMapModel actionMapModel) {
        this.creatureLayer = creatureLayer;
        this.staticLayer = staticLayer;
        this.actionMapModel = actionMapModel;
    }

    public final void executeTurn(ActionMapModel actionMapModel, Timeline lifeCycle) {
        creature.setMovePoints(2);
        if(creature.isHungry()) {
            GraphNode prey = creature.findPrey(actionMapModel);
            PathFinder pathFinder = new AStarSearch(actionMapModel, creature.getPosition(), prey);
            LinkedList<GraphNode> foundedPath = pathFinder.findPath();
            foundedPath.removeFirst();
            if (creature.getMovePoints() != 0) {
                lifeCycle.pause();
                try  {
                    if ((foundedPath.getFirst()).equals(prey)) {
                        creature.eat(actionMapModel, prey);
                        creature.makeMove(actionMapModel, prey);
                        Platform.runLater(this::updateView);
                    } else {
                        creature.makeMove(actionMapModel, foundedPath.getFirst());
                        Platform.runLater(this::updateView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lifeCycle.play();
                }
            }
        } else {
            System.out.println("Move anywhere");
        }
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }
    private void updateView() {
        creatureLayer.updateCreatureLayer(actionMapModel);
        staticLayer.updateStaticLayer(actionMapModel);
    }
}
