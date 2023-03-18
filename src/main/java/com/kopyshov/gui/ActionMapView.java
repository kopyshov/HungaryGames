package com.kopyshov.gui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public abstract class ActionMapView extends GridPane {
    public ActionMapView(int width, int height) {
        this.setPadding(new Insets(2));
        for (int col = 0; col < width; col++) {
            ColumnConstraints column = new ColumnConstraints(20, 20, Double.MAX_VALUE);
            column.setHgrow(Priority.ALWAYS);
            this.getColumnConstraints().add(column);
        }
        for (int rw = 0; rw < height; rw++) {
            RowConstraints row = new RowConstraints(20, 20, Double.MAX_VALUE);
            row.setVgrow(Priority.ALWAYS);
            this.getRowConstraints().add(row);
        }
    }
    void createImagePane(int column, int row, String path) {
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        Pane pane = new Pane();

        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.getChildren().add(imageView);
        this.add(pane, column, row);
    }
}
