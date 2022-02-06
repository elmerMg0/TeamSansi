package org.bo.app.view.waiter;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.bo.list.waiter.WaiterDTO;
import org.bo.list.waiter.WaiterManagement;

import java.sql.SQLException;
import java.time.LocalDate;

public class EditViewWaiter extends ViewWaiter{

    private WaiterDTO waiterDTO;
    private WaiterManagement waiterManagement;
    private Stage stage;

    public EditViewWaiter(WaiterDTO waiterDTO, WaiterManagement waiterManagement, Stage stage) {
        super();
        this.waiterDTO = waiterDTO;
        this.waiterManagement =  waiterManagement;
        this.stage = stage;

        txtFieldCi.setText(waiterDTO.getCi() + "");
        txtFieldPhone.setText(waiterDTO.getPhone() + "");
        txtFieldName.setText(waiterDTO.getName());

        birthDate.setValue(LocalDate.parse(waiterDTO.getBirthDate()));
        initDate.setValue(LocalDate.parse(waiterDTO.getInitDate()));

        Button accept = new Button("Aceptar");
        Button cancel = new Button("Cancelar");
        accept.setOnAction(event -> {
            try {
                saveWaiterDTO();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        cancel.setOnAction(event -> stage.close());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(accept, cancel);

        txtFieldCi.setEditable(false);

        this.getChildren().addAll(hBox);
    }

    private void saveWaiterDTO() throws SQLException {
        waiterDTO.setName(txtFieldName.getText());
        waiterDTO.setPhone(Integer.parseInt(txtFieldPhone.getText()));
        waiterDTO.setBirthDate(birthDate.getValue().toString());
        waiterDTO.setInitDate(initDate.getValue().toString());
        waiterManagement.updateWaiter(waiterDTO);
        stage.close();
    }

}
