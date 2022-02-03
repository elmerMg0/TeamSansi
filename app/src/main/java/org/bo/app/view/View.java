package org.bo.app.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.app.MenuView;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.File;
import java.sql.SQLException;

public class View extends VBox {

    protected final static String PATH = "src/main/java/org/bo/app/img/dish/";
    protected ItemDTO item;

    protected Stage stage;
    protected Label lblName, lblPrice, lblDescription, lblChooseItem;
    protected TextField textName, textPrice;
    protected TextArea textDescription;
    protected Button btnSearchPath, btnCancel, btnAccept;
    protected ComboBox<String> cbx;
    protected GridPane buttons;
    protected String pathImage;
    protected HBox searchPathChooseItemHBox, searchPathHBox, chooseItemHBox;
    protected ImageView imageView;

    protected Menu menu;
    protected GridPane menuView;

    public View(Stage stage, ItemDTO item) {
        this(stage);
        this.item = item;
    }

    public View(Stage stage) {
        this.stage = stage;
        this.pathImage = "";

        String path = new File("src/main/java/org/bo/app/img/confirmation.png").toURI().toString();
        Image image = new Image(path);
        imageView = new ImageView(image);
        imageView.setVisible(false);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        lblName = new Label("Nombre");
        lblPrice = new Label("Precio");
        lblDescription = new Label("Descripcion");
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
        items.addAll("Bebida", "Comida");
        cbx = new ComboBox<>(items);

        searchPathChooseItemHBox = generateHBox();

        buttons = new GridPane();
        insertElements(buttons);

        this.getChildren().addAll(lblName, textName, lblPrice, textPrice, lblDescription, textDescription, searchPathChooseItemHBox, buttons);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 5, 10, 5));
        VBox.setMargin(textDescription, new Insets(0, 10, 10, 10));
        VBox.setMargin(textName, new Insets(0, 10, 10, 10));
        VBox.setMargin(textPrice, new Insets(0, 10, 10, 10));

    }

    private HBox generateHBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        HBox.setMargin(hBox, new Insets(5, 0, 5, 0));

        searchPathHBox = new HBox();
        searchPathHBox.setAlignment(Pos.CENTER);
        searchPathHBox.getChildren().addAll(btnSearchPath, imageView);

        chooseItemHBox = new HBox();
        chooseItemHBox.setSpacing(5);
        chooseItemHBox.setAlignment(Pos.CENTER);
        chooseItemHBox.getChildren().addAll(lblChooseItem, cbx);
        hBox.getChildren().addAll(searchPathHBox, chooseItemHBox);
        return hBox;
    }

    private void insertElements(GridPane grilla) {
        grilla.add(btnCancel, 0, 0);
        grilla.add(btnAccept, 1, 0);
        grilla.setAlignment(Pos.CENTER);
        grilla.setPadding(new Insets(10, 10, 10, 10));
        grilla.setHgap(40);
        grilla.setVgap(15);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
