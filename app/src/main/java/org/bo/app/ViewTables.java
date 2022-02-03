package org.bo.app;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.bo.list.Item.ItemDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewTables extends VBox {
    private final int quantityTables = 21;
    private Button btnInitialSesion;
    private Button btnEditItems;
    private Button btnEditWaiters;
    private GridPane tables;
    private HBox buttons;

    private List<ItemDTO> dishes;

    public ViewTables(List<ItemDTO> dishes) throws SQLException, IOException {
        this.dishes = dishes;

        tables = new GridPane();
        buttons = new HBox();
        putButtons();
        fillTables();
        this.getChildren().addAll(buttons,tables);
    }

    public void fillTables( ) throws SQLException, IOException {
        tables.setAlignment(Pos.CENTER);
        int column = 0;
        int row = 0;
        int i = 1;
        while (i <= quantityTables) {
            tables.add(new TableImage(i,dishes), column, row);
            i++;
            column++;
            if (column == 7) {
                row++;
                column = 0;
            }
        }
    }
    public void putButtons(){
        btnInitialSesion = new Button("Iniciar Sesion");
        btnInitialSesion.setFont(new Font("Arial",20));

        btnEditItems = new Button("Editar Items");
        btnEditItems.setFont(new Font("Arial",20));
        btnEditItems.setVisible(false);

        btnEditWaiters = new Button("Editar meseros");
        btnEditWaiters.setFont(new Font("Arial",20));
        btnEditWaiters.setVisible(false);

        buttons.setPadding(new Insets(25,10,30,50));
        buttons.getChildren().addAll(btnEditItems,btnInitialSesion,btnEditWaiters);
        buttons.setSpacing(290);
    }
}
