package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Botones extends HBox {
    public Botones(){
        Button imprimir = new Button("IMPRIMIR");
        Button aniadir = new Button("ANIADIR");
        Button editar = new Button("EDITAR");

        imprimir.setFont(new Font("Arial",20));
        imprimir.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        editar.setFont(new Font("Arial",20));
        editar.setStyle("-fx-background-color: Red ; -fx-text-fill:White");
        aniadir.setFont(new Font("Arial",20));

        setSpacing(290);
        setMargin(imprimir,new Insets(30));
        setMargin(aniadir,new Insets(30));
        setMargin(editar,new Insets(30));

        getChildren().addAll(imprimir,editar,aniadir);

    }
}
