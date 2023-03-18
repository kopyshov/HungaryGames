package com.kopyshov.model;

import com.kopyshov.model.entities.EntityModel;
import com.kopyshov.model.entities.Grass;
import com.kopyshov.model.entities.creatures.Carnivore;
import com.kopyshov.model.entities.creatures.Creature;
import com.kopyshov.model.entities.creatures.Herbivore;
import com.kopyshov.model.entities.obstacles.Rock;
import com.kopyshov.model.entities.obstacles.Tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class ActionMapModel {
    private final int width;
    private final int height;

    public ActionMapModel(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    private final HashMap<GraphNode, EntityModel> obstacles = new HashMap<>();

    public void addPermanentObstacles() {
        for (int i = 0; i < height * width; i++) {
            int chance = ThreadLocalRandom.current().nextInt(0, 100);
            if(chance < 10) {
                createRock();
            }
            if(chance >= 10 && chance < 20) {
                createTree();
            }
        }
    }
    public void createRock() {
        Rock rock = new Rock();
        GraphNode pos = takePosition();
        rock.setPosition(pos);
        obstacles.put(pos, rock);
    }
    public void createTree(){
        Tree tree = new Tree();
        GraphNode pos = takePosition();
        obstacles.put(pos, tree);
    }

    private final HashMap<GraphNode, Grass> grassList = new HashMap<>();
    public void createGrass() {
        Grass grass = new Grass();
        GraphNode pos = takePosition();
        grass.setPosition(pos);
        grassList.put(pos, grass);
    }

    public void addSomeGrass() {
        for (int i = 0; i < width; i++) {
            this.createGrass();
        }
    }

    private final LinkedList<Creature> creatureQueue = new LinkedList<>();
    private final HashMap<GraphNode, Creature> herbivores = new HashMap<>();
    public void createHerbivore() {
        Herbivore herbivore = new Herbivore();
        herbivore.setName("Herbivore Anatol");
        GraphNode pos = takePosition();
        herbivore.setPosition(pos);
        creatureQueue.add(herbivore);
        herbivores.put(pos, herbivore);
    }

    private final HashMap<GraphNode, Creature> carnivores = new HashMap<>();
    public void createCarnivore() {
        Carnivore carnivore = new Carnivore();
        carnivore.setName("Carnivore Pantera");
        GraphNode pos = takePosition();
        carnivore.setPosition(pos);
        creatureQueue.add(carnivore);
        carnivores.put(pos, carnivore);
    }

    public void addCreatures() {
        for(int i = 0; i < width/4; i++){
            this.createHerbivore();
            this.createCarnivore();
        }
    }

    private GraphNode takePosition() {
        int x = ThreadLocalRandom.current().nextInt(0, this.width - 1);
        int y = ThreadLocalRandom.current().nextInt(0, this.height - 1);
        GraphNode pos = new GraphNode(x, y);
        if (obstacles.containsKey(pos)
            || grassList.containsKey(pos)
            || carnivores.containsKey(pos)
            || herbivores.containsKey(pos))
        {
            return this.takePosition(); //кажется сомнительный способ
        }
        return pos;
        // да и вообще ощущение, что реализация не особо красива
        // ведь сколько раз должна сработать рекурсия когда существ станет много на карте?
    }

    public Integer getWidth() {
        return width;
    }
    public Integer getHeight() {
        return height;
    }

    public HashMap<GraphNode, EntityModel> getObstacles() {
        return obstacles;
    }

    public HashMap<GraphNode, Grass> getGrassList() {
        return grassList;
    }

    public LinkedList<Creature> getCreatureQueue() {
        return creatureQueue;
    }


    public HashMap<GraphNode, Creature> getHerbivores() {
        return herbivores;
    }

    public HashMap<GraphNode, Creature> getCarnivores() {
        return carnivores;
    }
}
