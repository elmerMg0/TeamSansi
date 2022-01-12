package org.bo.app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bo.list.Item.ItemDTO;

import java.io.File;

public class ViewAdd extends VBox {
    private Label lblName;
    private Label lblPrice;
    private Label lblDescription;
    private Label lblChooseItem;
    private Label lblAddPhoto;
    private TextField textName;
    private TextField textPrice;
    private TextArea textDescription;
    private Button btnSearchPath;
    private Button btnCancel;
    private Button btnAccept;
    private ComboBox<String> cbx;
    private GridPane buttons;

    public ViewAdd() {
        lblName = new Label("Nombre");
        lblPrice = new Label("Precio");
        lblDescription = new Label("Descripcion");
        lblAddPhoto = new Label("Agregar foto");
        lblChooseItem = new Label("Elegit item");

        btnSearchPath = new Button("Buscar Foto");
        btnAccept = new Button("ACEPTAR");
        btnCancel = new Button("CANCELAR");

        textName = new TextField();
        textPrice = new TextField();
        textPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textDescription = new TextArea();

        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Bebida","Comida");
        cbx = new ComboBox<>(items);
        buttons = new GridPane();
        insertElements(buttons);


        this.getChildren().addAll(lblName, textName, lblPrice, textPrice, lblDescription, textDescription, buttons);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
        VBox.setMargin(textDescription, new Insets(0, 10, 10, 10));
        VBox.setMargin(textName, new Insets(0, 10, 10, 10));
        VBox.setMargin(textPrice, new Insets(0, 10, 10, 10));

        btnSearchPath.setOnMouseClicked(event -> searchPath());

    }
    private void insertElements(GridPane grilla){
       grilla.add(lblAddPhoto,0,0);
       grilla.add(lblChooseItem,1,0);
       grilla.add(btnSearchPath,0,1);
       grilla.add(cbx,1,1);
       grilla.add(btnCancel,0,2);
       grilla.add(btnAccept,1,2);
       grilla.setAlignment(Pos.CENTER);
       grilla.setPadding(new Insets(10, 10, 10, 10));
       grilla.setHgap(40);
       grilla.setVgap(15);
    }
    private void searchPath(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File pathImg = fileChooser.showOpenDialog(stage);
    }
}

