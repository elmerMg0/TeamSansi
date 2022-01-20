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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

public class ViewEdit extends VBox {

    private final static String PATH = "src/main/java/org/bo/app/img/";

    private Stage stage;
    private Label lblName, lblPrice, lblDescription, lblChooseItem;
    private TextField textName, textPrice;
    private TextArea textDescription;
    private Button btnSearchPath, btnCancel, btnSaveChanges;
    private ComboBox<String> cbx;
    private GridPane buttons;
    private String pathImage;
    private HBox searchPathChooseItemHBox, searchPathHBox, chooseItemHBox;
    private ImageView imageView;
    private MenuView menuView;
    private Menu menu;
    private ItemDTO item;
    private VBox orderDetail;

    public ViewEdit(Stage stage,ItemDTO item,MenuView menuView,Menu menu) {
        this.menu = menu;
        this.stage = stage;
        this.pathImage = "";
        this.item = item;
        this.menuView = menuView;
        //this.orderDetail = orderDetail;

        String path = new File("src/main/java/org/bo/app/img/confirmation.png").toURI().toString();
        Image image = new Image(path);
        imageView = new ImageView(image);
        imageView.setVisible(true);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        lblName = new Label("Nombre");
        lblPrice = new Label("Precio");
        lblDescription = new Label("Descripcion");
        lblChooseItem = new Label("Elegit item");

        btnSearchPath = new Button("Buscar Foto");
        btnSaveChanges = new Button("GUARDAR CAMBIOS");
        btnCancel = new Button("CANCELAR");

        btnSaveChanges.setOnMouseClicked(event -> {
            try {
                updateDates();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnCancel.setOnMouseClicked(event -> stage.close());

        textName = new TextField();
        textName.setText(item.getName());
        textPrice = new TextField();
        textPrice.setText(String.valueOf((Double)item.getPrice()));
        textPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textDescription = new TextArea();
        textDescription.setText(item.getDescription());

        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Bebida", "Comida");
        cbx = new ComboBox<>(items);
        if (item.isDish() ) {
            cbx.setValue("Comida");
        }else{
            cbx.setValue("Bebida");
        }


        searchPathChooseItemHBox = generateHBox();
        buttons = new GridPane();
        insertElements(buttons);

        this.getChildren().addAll(lblName, textName, lblPrice, textPrice, lblDescription, textDescription, searchPathChooseItemHBox, buttons);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 5, 10, 5));
        VBox.setMargin(textDescription, new Insets(0, 10, 10, 10));
        VBox.setMargin(textName, new Insets(0, 10, 10, 10));
        VBox.setMargin(textPrice, new Insets(0, 10, 10, 10));

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
        grilla.add(btnSaveChanges, 1, 0);
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
        String newPath = generateNewPath();
        String oldPath = item.getPathImage();
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        item.setDish(isDish);

        if (newPath.length() == 0) {
            item.setPathImage(oldPath);
        }else{
            item.setPathImage(newPath);
        }

        menu.update(item);
        menuView.refresh();
        stage.close();
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


