package com.kopyshov.model.entities.creatures;

import com.kopyshov.actions.AStarSearch;
import com.kopyshov.model.ActionMapModel;
import com.kopyshov.model.GraphNode;

public class Herbivore extends Creature {

    public Herbivore() {
        this.setImagePath("com/kopyshov/images/pig.png");
    }

    @Override
    public GraphNode findPrey(ActionMapModel actionMapModel) {
        GraphNode pos = this.getPosition();

        GraphNode nextTarget = actionMapModel.getGrassList()
                .keySet()
                .stream()
                .min(AStarSearch.LengthComparator(pos))
                .orElse(null);
        return nextTarget;
    }

    @Override
    public void eat(ActionMapModel actionMapModel, GraphNode food) {
        actionMapModel.getGrassList().remove(food);
        this.setHungry(false);
        this.reduceMovePoints();
    }

    @Override
    public void makeMove(ActionMapModel actionMapModel, GraphNode to) {
        actionMapModel.getHerbivores().put(to, this);
        actionMapModel.getHerbivores().remove(this.getPosition());
        this.setPosition(to);
        this.reduceMovePoints();
    }
}
