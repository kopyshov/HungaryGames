package com.kopyshov.gui;

import com.kopyshov.model.ActionMapModel;

public class StaticLayer extends ActionMapView {
    public StaticLayer(int width, int height) {
        super(width, height);
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1)");
    }

    public void updateStaticLayer(ActionMapModel actionMapModel) {
        this.getChildren().clear();
        actionMapModel.getGrassList().forEach((k, v) -> {
            int column = k.x();
            int row = k.y();
            String path = v.getImagePath();
            createImagePane(column, row, path);
        });
    }
}
