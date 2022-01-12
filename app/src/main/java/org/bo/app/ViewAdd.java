package org.bo.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.bo.list.Item.ItemDTO;

public class ViewAdd extends VBox {
    Label lblName ;
    Label lblPrice;
    Label lblDescription;
    Label lblAddPhoto;
    TextField textName;
    TextField textPrice;
    TextField textDescription;
    Button btnSave;
    public ViewAdd(){
        lblName = new Label("Nombre");
        lblPrice = new Label("Precio");
        lblDescription = new Label("Descripcion");
        lblAddPhoto = new Label("Agregar foto");
        btnSave = new Button("Buscar");

        textName = new TextField();
        textPrice = new TextField();
        textDescription = new TextField();



        this.getChildren().addAll(lblName,textName,lblPrice,textPrice,lblDescription,textDescription,lblAddPhoto,btnSave);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,10,10,10));
        VBox.setMargin(textDescription,new Insets(0,10,10,10));
        VBox.setMargin(textName,new Insets(0,10,10,10));
        VBox.setMargin(textPrice,new Insets(0,10,10,10));

    }
    public Button getBtnSave(){
        return btnSave;
    }

}

