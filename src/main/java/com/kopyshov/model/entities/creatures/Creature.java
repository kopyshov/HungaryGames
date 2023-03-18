package com.kopyshov.model.entities.creatures;

import com.kopyshov.model.entities.EntityModel;
import com.kopyshov.model.ActionMapModel;
import com.kopyshov.model.GraphNode;

import static com.kopyshov.Simulation.MAX_AGE;

public abstract class Creature extends EntityModel {

    private int age;
    private String name;
    private boolean isHungry;
    private int hitPoints;
    public int movePoints;


    public Creature() {
        age = MAX_AGE;
        hitPoints = 3;
        movePoints = 2;
        isHungry = true;
    }

    public abstract GraphNode findPrey(ActionMapModel actionMapModel);
    public abstract void eat(ActionMapModel actionMapModel, GraphNode food);
    public abstract void makeMove(ActionMapModel actionMapModel, GraphNode to);

    public void increaseHitPoints(){
        if(this.hitPoints < 3) {
            this.hitPoints++;
        } else {
            this.hitPoints = 3;
        }
    }

    public void reduceHitPoints(){
        this.hitPoints--;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    public int getMovePoints() {
        return movePoints;
    }

    public void setMovePoints(int movePoints) {
        this.movePoints = movePoints;
    }
    public void reduceMovePoints() {
        this.movePoints--;
    }
    public void reduceAge() {
        this.age--;
    }
    public int getAge() {
        return age;
    }

}
