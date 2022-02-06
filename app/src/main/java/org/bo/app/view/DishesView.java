package org.bo.app.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bo.app.Buttons;
import org.bo.app.MenuView;
import org.bo.app.OrderDetail;
import org.bo.list.Item.ItemDTO;

import java.io.IOException;
import java.util.List;

public class DishesView extends VBox {

    private List<ItemDTO> dishes;

    public DishesView(List<ItemDTO> dishes) throws IOException {
        this.dishes = dishes;

        HBox frame = new HBox();

        VBox orderDetail = new OrderDetail();
        GridPane menuView = new MenuView(dishes, orderDetail);
        ScrollPane scrollPane = new ScrollPane(menuView);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        frame.getChildren().addAll(orderDetail, scrollPane);

        HBox buttons = new Buttons((OrderDetail) orderDetail);

        this.getChildren().addAll(frame, buttons);
        this.setStyle("-fx-background-color:Gris");
    }

}
