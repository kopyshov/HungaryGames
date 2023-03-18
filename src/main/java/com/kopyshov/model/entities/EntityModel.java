package com.kopyshov.model.entities;

import com.kopyshov.model.GraphNode;

public abstract class EntityModel {
    private GraphNode position;
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public GraphNode getPosition() {
        return position;
    }

    public void setPosition(GraphNode position) {
        this.position = position;
    }
}
