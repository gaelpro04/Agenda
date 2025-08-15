import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Scanner;

public class AgendaGUI extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        Label labelBienv = new Label("Bienvenido a la agenda");
        labelBienv.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 39));
        labelBienv.setAlignment(Pos.CENTER);
        Button botonAgregar = new Button("Agregar");
        botonAgregar.setAlignment(Pos.CENTER);
        botonAgregar.setOnAction(e -> botonAgregar());

        BorderPane primerPane = new BorderPane();
        primerPane.setPadding(new Insets(20));

        VBox topBox = new VBox(30);
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(labelBienv, botonAgregar);

        BorderPane tablaPane = new BorderPane();

        TableView<Persona> tablaPersonas = new TableView<>();
        TableColumn<Persona, Integer> colID = new TableColumn<>("ID");
        TableColumn<Persona, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Persona, String> colDireccion = new TableColumn<>("Direccion");
        tablaPersonas.getColumns().addAll(colID, colNombre, colDireccion);
        tablaPersonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBoxPersonas = new VBox(5, new Label("Personas"), tablaPersonas);
        vBoxPersonas.setPadding(new Insets(10));
        vBoxPersonas.setPrefWidth(350);

        TableView<Telefono> tablaTelefonos = new TableView<>();
        TableColumn<Telefono, Integer> telColID = new TableColumn<>("ID");
        TableColumn<Telefono, Integer> telColpersonaID = new TableColumn<>("personaID");
        TableColumn<Telefono, String> telColNumero = new TableColumn<>("Numero");
        tablaTelefonos.getColumns().addAll(telColID, telColpersonaID, telColNumero);
        tablaTelefonos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBoxTelefonos = new VBox(5, new Label("Telefonos"), tablaTelefonos);
        vBoxTelefonos.setPadding(new Insets(10));
        vBoxTelefonos.setPrefWidth(350);



        HBox tablasBox = new HBox(10, vBoxPersonas, vBoxTelefonos);
        tablasBox.setPadding(new Insets(10));
        tablasBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(tablasBox);
        scrollPane.setFitToWidth(true);

        tablaPane.setCenter(scrollPane);
        primerPane.setTop(topBox);
        root.setTop(primerPane);
        root.setCenter(tablaPane);
        Scene scene = new Scene(root, 1400,750);
        stage.setTitle("Agenda");
        stage.setScene(scene);
        stage.show();
    }

    private void botonAgregar() {

    }

    public static void main(String[] args) {
        launch(args); // inicia la app
    }
}
