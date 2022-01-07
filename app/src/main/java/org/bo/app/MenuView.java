package org.bo.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class MenuView extends GridPane {

    public MenuView() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));

        this.setHgap(5);
        this.setVgap(5);

        this.add(new DishImage(), 0, 0);
        this.add(new DishImage(), 1, 0);
        this.add(new DishImage(), 2, 0);
        this.add(new DishImage(), 0, 1);

    }

}
