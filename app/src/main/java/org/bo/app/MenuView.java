package org.bo.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.util.List;

public class MenuView extends GridPane {

    private List<ItemDTO> dishes;
    private VBox orderDetail;

    public MenuView(List<ItemDTO> dishes, VBox orderDetail) {
        this.orderDetail = orderDetail;
        this.dishes = dishes;

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));

        this.setHgap(5);
        this.setVgap(5);

        fillDishImage();

    }

    private void fillDishImage() {
        int column = 0;
        int row = 0;
        for (ItemDTO dish : dishes) {
            this.add(new DishImage(dish, orderDetail), column, row);
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
