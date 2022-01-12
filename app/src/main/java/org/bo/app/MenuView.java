package org.bo.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.util.List;

public class MenuView extends GridPane {

    private Menu menu;

    public MenuView(Menu menu) {
        this.menu = menu;
        List<ItemDTO> dishes = menu.select();

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));

        this.setHgap(5);
        this.setVgap(5);

        int column = 0;
        int row = 0;
        for (ItemDTO dish : dishes) {
            this.add(new DishImage(dish.getName(), dish.getPathImage()), column, row);
            column++;
            if (column % 3 == 0) {
                row++;
            }
            if (column == 3) {
                column = 0;
            }
        }

    }

}
