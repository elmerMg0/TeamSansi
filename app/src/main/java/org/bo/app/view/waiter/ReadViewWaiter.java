package org.bo.app.view.waiter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.bo.list.waiter.WaiterDTO;
import org.bo.list.waiter.WaiterManagement;

import java.time.LocalDate;

public class ReadViewWaiter extends ViewWaiter {
    private WaiterDTO waiterDTO;
    private Stage stage;
    private WaiterManagement waiterManagement;

    public ReadViewWaiter(WaiterDTO waiterDTO, WaiterManagement waiterManagement, Stage stage) {
        super();
        this.waiterDTO = waiterDTO;
        this.stage = stage;
        this.waiterManagement = waiterManagement;

        txtFieldCi.setText(waiterDTO.getCi() + "");
        txtFieldPhone.setText(waiterDTO.getPhone() + "");
        txtFieldName.setText(waiterDTO.getName());

        birthDate.setValue(LocalDate.parse(waiterDTO.getBirthDate()));
        initDate.setValue(LocalDate.parse(waiterDTO.getInitDate()));

        txtFieldCi.setEditable(false);
        txtFieldName.setEditable(false);
        txtFieldPhone.setEditable(false);

        Button accept = new Button("Aceptar");
        Button edit = new Button("Editar");
        
        accept.setOnAction(event -> stage.close());
        edit.setOnAction(event -> createWindowEdit());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(accept, edit);
        
        this.getChildren().addAll(hBox);
    }

    private void createWindowEdit() {
        this.stage.close();
        Stage stage = new Stage();
        ViewWaiter view = new EditViewWaiter(waiterDTO, waiterManagement, stage);
        Scene scene = new Scene(view, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Añadir nuevo item");
        stage.show();
    }

}
