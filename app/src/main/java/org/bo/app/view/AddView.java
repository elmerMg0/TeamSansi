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
import org.bo.list.Item.Dish;
import org.bo.list.Item.Drink;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

public class AddView extends View {

    public AddView(Stage stage) {
        super(stage);
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

        btnSearchPath.setOnMouseClicked(event -> searchPath());

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

    private void insertDates() throws Exception {
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

        String newPath = generateNewPath();

        if (newPath.length() == 0) {
            generateAlert("Advertencia!!!", "Debe agregar una imagen");
        } else {
            ItemDTO itemDTO = isDish ? new Dish(name, description, price, newPath) : new Drink(name, description, price, newPath);
            menu.addItem(itemDTO);
            stage.close();
        }
    }

    private String generateNewPath() throws Exception {
        if (!pathImage.equals("")) {
            byte[] bytes = loadImage64(pathImage);
            String [] pathImages = pathImage.split("\\\\");
            String newPath = PATH + pathImages[pathImages.length - 1];
            File newImage = new File(newPath);
            FileUtils.writeByteArrayToFile(newImage, bytes);
            return newPath;
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

