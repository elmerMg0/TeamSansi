package org.bo.app.view.waiter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class ViewWaiter extends VBox {
    protected Label labelName, labelCi, labelPhone, labelBirthDate, labelInitDate;
    protected TextField txtFieldName, txtFieldCi, txtFieldPhone;
    protected DatePicker birthDate, initDate;

    public ViewWaiter() {
        this.labelName = new Label("Nombre: ");
        this.labelCi = new Label("CI: ");
        this.labelPhone = new Label("Telefono");
        this.labelBirthDate = new Label("Cumpleaños: ");
        this.labelInitDate = new Label("Fecha de Inicio de trabajo: ");

        this.txtFieldCi = new TextField();
        this.txtFieldName = new TextField();
        this.txtFieldPhone = new TextField();

        this.birthDate = new DatePicker();
        birthDate.setEditable(false);
        birthDate.setConverter(new LocalDateStringConverter(DateTimeFormatter.ofPattern("dd-MM-yyyy"), null));

        this.initDate = new DatePicker();
        initDate.setEditable(false);
        initDate.setConverter(new LocalDateStringConverter(DateTimeFormatter.ofPattern("dd-MM-yyyy"), null));

        txtFieldCi.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtFieldCi.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtFieldPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtFieldPhone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        this.getChildren().addAll(labelName, txtFieldName, labelCi, txtFieldCi, labelPhone, txtFieldPhone,
                labelBirthDate, birthDate, labelInitDate, initDate);

    }
}
