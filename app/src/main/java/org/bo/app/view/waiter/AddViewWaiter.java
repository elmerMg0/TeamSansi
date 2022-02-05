package org.bo.app.view.waiter;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.bo.list.waiter.WaiterDTO;
import org.bo.list.waiter.WaiterManagement;

import java.sql.SQLException;

public class AddViewWaiter extends ViewWaiter {

    private  WaiterManagement waiterManagement;
    private Stage stage;
    private MenuWaiterView menuWaiterView;

    public AddViewWaiter(MenuWaiterView menuWaiterView, WaiterManagement waiterManagement, Stage stage) {
        super();
        this.menuWaiterView = menuWaiterView;
        this.waiterManagement = waiterManagement;
        this.stage = stage;

        Button accept = new Button("Aceptar");
        Button cancel = new Button("Cancelar");
        accept.setOnAction(event -> {
            try {
                addWaiter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        cancel.setOnAction(event -> stage.close());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(accept, cancel);

        this.getChildren().addAll(hBox);

    }

    private void addWaiter() throws SQLException {
        int ci = Integer.parseInt(txtFieldCi.getText());
        String name = txtFieldName.getText();
        int phone = Integer.parseInt(txtFieldPhone.getText());
        String birthDate = super.birthDate.getValue().toString();
        String initDate = super.initDate.getValue().toString();

        WaiterDTO waiterDTO = new WaiterDTO(ci, name, birthDate, phone, initDate, "photo");
        waiterManagement.addWaiter(waiterDTO);
        menuWaiterView.refresh();
        stage.close();
    }

}
