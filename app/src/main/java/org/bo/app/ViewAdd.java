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
import org.apache.commons.io.FileUtils;
import org.bo.list.Item.Dish;
import org.bo.list.Item.Drink;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

public class ViewAdd extends VBox {

    private final static String PATH = "src/main/java/org/bo/app/img/";

    private Stage stage;
    private Label lblName, lblPrice, lblDescription, lblChooseItem, lblAddPhoto;
    private TextField textName, textPrice;
    private TextArea textDescription;
    private Button btnSearchPath, btnCancel, btnAccept;
    private ComboBox<String> cbx;
    private GridPane buttons;
    private String pathImage;

    private Menu menu;
    private GridPane menuView;

    public ViewAdd(Stage stage, Menu menu, GridPane menuView) {
        this.menu = menu;
        this.menuView = menuView;

        this.stage = stage;
        this.pathImage = "";

        lblName = new Label("Nombre");
        lblPrice = new Label("Precio");
        lblDescription = new Label("Descripcion");
        lblAddPhoto = new Label("Agregar foto");
        lblChooseItem = new Label("Elegit item");

        btnSearchPath = new Button("Buscar Foto");
        btnAccept = new Button("ACEPTAR");
        btnCancel = new Button("CANCELAR");

        btnAccept.setOnMouseClicked(event -> {
            try {
                insertDates();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnCancel.setOnMouseClicked(event -> stage.close());

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

    private void insertElements(GridPane grilla) {
        grilla.add(lblAddPhoto, 0, 0);
        grilla.add(lblChooseItem, 1, 0);
        grilla.add(btnSearchPath, 0, 1);
        grilla.add(cbx, 1, 1);
        grilla.add(btnCancel, 0, 2);
        grilla.add(btnAccept, 1, 2);
        grilla.setAlignment(Pos.CENTER);
        grilla.setPadding(new Insets(10, 10, 10, 10));
        grilla.setHgap(40);
        grilla.setVgap(15);
    }

    private void searchPath() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg")
        );
        File pathImg = fileChooser.showOpenDialog(stage);
        pathImage = pathImg.toPath().toString();
        System.out.println(pathImage);
    }

    private void insertDates() throws Exception {
        String name = textName.getText();
        Double price = Double.parseDouble(textPrice.getText());
        String description = textDescription.getText();
        String dish = cbx.getValue();
        boolean isDish = dish.equals("Comida");

        String newPath = generateNewPath();


        ItemDTO itemDTO = isDish ? new Dish(name, description, price, newPath) : new Drink(name, description, price, newPath);
        menu.addItem(itemDTO);
        ((MenuView) menuView).refresh();
        stage.close();
    }

    private String generateNewPath() throws Exception {
        byte[] bytes = loadImage64(pathImage);
        String[] pathImages = pathImage.split("/");
        String newPath = PATH + pathImages[pathImages.length - 1];
        File newImage = new File(newPath);
        FileUtils.writeByteArrayToFile(newImage, bytes);
        return newPath;
    }

    private byte[] loadImage64(String url) throws Exception {
        File file = new File(url.toString());
        if (file.exists()) {
            int lenght = (int) file.length();
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[lenght];
            reader.read(bytes, 0, lenght);
            reader.close();
            return bytes;
        } else {
            return null;
        }
    }

}

