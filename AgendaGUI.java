import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Scanner;

public class AgendaGUI extends Application {

    private Agenda agenda;

    @Override
    public void start(Stage stage) throws SQLException {

        agenda = new Agenda();
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
        colID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<Persona, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombre()));

        TableColumn<Persona, String> colDireccion = new TableColumn<>("Direccion");
        colDireccion.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDireccion()));

        tablaPersonas.getColumns().addAll(colID, colNombre, colDireccion);
        tablaPersonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Persona> personasToTabla = FXCollections.observableArrayList(agenda.getPeople());
        tablaPersonas.setItems(personasToTabla);

        VBox vBoxPersonas = new VBox(5, new Label("Personas"), tablaPersonas);
        vBoxPersonas.setPadding(new Insets(10));
        vBoxPersonas.setPrefWidth(350);

        TableView<Telefono> tablaTelefonos = new TableView<>();
        TableColumn<Telefono, Integer> telColID = new TableColumn<>("ID");
        telColID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<Telefono, Integer> telColpersonaID = new TableColumn<>("personaID");
        telColpersonaID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPersonaId()));

        TableColumn<Telefono, String> telColNumero = new TableColumn<>("Numero");
        telColNumero.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTelefono()));

        tablaTelefonos.getColumns().addAll(telColID, telColpersonaID, telColNumero);
        tablaTelefonos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Telefono> telefonosToTabla = FXCollections.observableArrayList(agenda.getTelephones());
        tablaTelefonos.setItems(telefonosToTabla);

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
