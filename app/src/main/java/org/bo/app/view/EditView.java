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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.bo.app.MenuView;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

public class EditView extends View {

    private String oldPath;

    public EditView(Stage stage, ItemDTO dish) {
        super(stage, dish);

        this.oldPath = "";

        boolean isVisible = !dish.getPathImage().equals("");
        imageView.setVisible(isVisible);

        textName.setText(dish.getName());
        textPrice.setText(String.valueOf(dish.getPrice()));
        textDescription.setWrapText(true);
        textDescription.setText(dish.getDescription());

        cbx.setValue(dish.isDish() ? "Comida" : "Bebida");

        btnAccept.setText("Guardar Cambios");
        btnAccept.setOnMouseClicked(event -> {
            try {
                updateDates();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnCancel.setOnMouseClicked(event -> stage.close());

        btnSearchPath.setOnMouseClicked(event -> searchPath());

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

    private void searchPath() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg")
        );
        File pathImg = fileChooser.showOpenDialog(stage);
        if (pathImg != null) {
            pathImage = pathImg.toPath().toString();
            if (pathImage.contains("jpg") || pathImage.contains("png") || pathImage.contains("jpeg")) {
                imageView.setVisible(true);
            }
        }
        System.out.println(pathImage);
    }

    private void updateDates() throws Exception {
        if (textName.getText().length() == 0 || textPrice.getText().length() == 0
                || textDescription.getText().length() == 0 || cbx.getValue() == null) {
            generateAlert("Advertencia!!!", "Debe llenar los campos");
            return;
        }
        String name = textName.getText();
        Double price = Double.parseDouble(textPrice.getText());
        String description = textDescription.getText();
        String dish = cbx.getValue();
        boolean isDish = dish.equals("Comida");
        oldPath = item.getPathImage();
        String newPath = generateNewPath();
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        item.setDish(isDish);

        if (newPath.length() == 0) {
            item.setPathImage(oldPath);
        } else {
            item.setPathImage(newPath);
        }

        menu.update(item);
        stage.close();
    }

    private String generateNewPath() throws Exception {
        if (!pathImage.equals("")) {
            byte[] bytes = loadImage64(pathImage);
            File oldImage = new File(oldPath);
            FileUtils.writeByteArrayToFile(oldImage, bytes);
            return item.getPathImage();
        } else {
//            generateAlert("Advertencia!!!", "Debe seleccionar una imagen");
            return "";
        }
    }

    private void generateAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
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


