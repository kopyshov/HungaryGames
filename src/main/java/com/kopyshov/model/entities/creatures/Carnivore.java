package com.kopyshov.model.entities.creatures;

import com.kopyshov.actions.AStarSearch;
import com.kopyshov.model.ActionMapModel;
import com.kopyshov.model.GraphNode;

public class Carnivore extends Creature implements Attacker{
    public Carnivore() {
        this.setImagePath("com/kopyshov/images/catview.png");
    }
    public void makeMove(ActionMapModel actionMapModel, GraphNode to){
        actionMapModel.getCarnivores().put(to, this);
        actionMapModel.getCarnivores().remove(this.getPosition());
        this.setPosition(to);
        this.reduceMovePoints();
    }

    @Override
    public GraphNode findPrey(ActionMapModel actionMapModel) {
        GraphNode pos = this.getPosition();

        GraphNode nextTarget = actionMapModel.getHerbivores()
                .keySet()
                .stream()
                .min(AStarSearch.LengthComparator(pos))
                .orElse(null);
        return nextTarget;
    }

    @Override
    public void eat(ActionMapModel actionMapModel, GraphNode food) {
        actionMapModel.getCreatureQueue().remove(actionMapModel.getHerbivores().remove(food));
        this.setHungry(false);
        this.reduceMovePoints();
    }

    @Override
    public void attack(ActionMapModel actionMapModel, GraphNode prey) {
        Creature creature = actionMapModel.getHerbivores().get(prey);
        creature.reduceHitPoints();
        this.reduceMovePoints();
    }
}
