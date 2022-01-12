package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Buttons extends HBox {
    private Button btnPrint;
    private Button btnAdd;
    private Button btnEdit;
    public Buttons(){
        btnPrint = new Button("IMPRIMIR");
        btnAdd = new Button("AÑADIR");
         btnEdit = new Button("EDITAR");

        btnPrint.setFont(new Font("Arial",20));
        btnPrint.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        btnEdit.setFont(new Font("Arial",20));
        btnEdit.setStyle("-fx-background-color: Red ; -fx-text-fill:White");
        btnAdd.setFont(new Font("Arial",20));

        this.setSpacing(290);
        setMargin(btnPrint,new Insets(30));
        setMargin(btnAdd,new Insets(30));
        setMargin(btnEdit,new Insets(30));

        getChildren().addAll(btnPrint,btnEdit,btnAdd);

    }
}
