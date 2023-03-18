package com.kopyshov.gui;

import com.kopyshov.model.ActionMapModel;

public class BasicLayer extends ActionMapView {
    public BasicLayer(int width, int height) {
        super(width, height);
        this.setStyle("-fx-background-color: lightgreen");
    }

    public void showObstacles(ActionMapModel actionMapModel) {
        actionMapModel.getObstacles().forEach((k, v) -> {
            int column = k.x();
            int row = k.y();
            String path = v.getImagePath();
            createImagePane(column, row, path);
        });
    }
}
