package org.bo.app;

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
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.File;

public class ReadView extends VBox {

    private Stage stage;
    private ItemDTO dish;
    private Label lblName, lblPrice, lblDescription, lblChooseItem;
    private TextField textName, textPrice;
    private TextArea textDescription;
    private Button btnSearchPath, btnEdit, btnAccept;
    private ComboBox<String> cbx;
    private GridPane buttons;
    private HBox searchPathChooseItemHBox, searchPathHBox, chooseItemHBox;
    private ImageView imageView;

    private Menu menu;
    private GridPane menuView;

    public ReadView(Stage stage, ItemDTO dish) {
        this.stage = stage;
        this.dish = dish;

        String path = new File("src/main/java/org/bo/app/img/confirmation.png").toURI().toString();
        Image image = new Image(path);
        imageView = new ImageView(image);
        boolean isVisible = !dish.getPathImage().equals("");
        imageView.setVisible(isVisible);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        lblName = new Label("Nombre");
        lblPrice = new Label("Precio");
        lblDescription = new Label("Descripcion");
        lblChooseItem = new Label("Elegit item");

        btnSearchPath = new Button("Buscar Foto");
        btnAccept = new Button("ACEPTAR");
        btnEdit = new Button("EDITAR");

        textName = new TextField();
        textName.setEditable(false);
        textPrice = new TextField();
        textPrice.setEditable(false);
        textPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textName.setText(dish.getName());
        textPrice.setText(String.valueOf(dish.getPrice()));

        textDescription = new TextArea();
        textDescription.setEditable(false);
        textDescription.setWrapText(true);
        textDescription.setText(dish.getDescription());

        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Bebida", "Comida");
        cbx = new ComboBox<>(items);
        cbx.setEditable(false);
        cbx.setValue(dish.isDish() ? "Comida" : "Bebida");

        searchPathChooseItemHBox = generateHBox();

        buttons = new GridPane();
        insertElements(buttons);

        btnAccept.setOnMouseClicked(event -> stage.close());

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
        grilla.add(btnEdit, 0, 0);
        grilla.add(btnAccept, 1, 0);
        grilla.setAlignment(Pos.CENTER);
        grilla.setPadding(new Insets(10, 10, 10, 10));
        grilla.setHgap(40);
        grilla.setVgap(15);
    }

}
