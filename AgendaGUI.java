import javafx.application.Application;
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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;


public class AgendaGUI extends Application {

    private Agenda agenda;
    private TableView<Persona> tablaPersonas;
    private TableView<Telefono> tablaTelefonos;

    @Override
    public void start(Stage stage) throws SQLException {

        agenda = new Agenda();
        BorderPane root = new BorderPane();

        Label labelBienv = new Label("Bienvenido a la agenda");
        labelBienv.setFont(Font.font("Arial", FontWeight.BOLD, 39));
        labelBienv.setAlignment(Pos.CENTER);
        Button botonAgregar = new Button("Agregar");
        Button botonEliminar = new Button("Eliminar");
        Button botonEditar = new Button("Editar");

        botonEditar.setAlignment(Pos.CENTER);
        botonEditar.setOnAction(e -> botonEditar());

        botonAgregar.setAlignment(Pos.CENTER);
        botonAgregar.setOnAction(e -> botonAgregar());

        botonEliminar.setAlignment(Pos.CENTER);
        botonEliminar.setOnAction(e -> botonEliminar());

        BorderPane primerPane = new BorderPane();
        primerPane.setPadding(new Insets(20));

        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(labelBienv, botonAgregar, botonEliminar, botonEditar);

        BorderPane tablaPane = new BorderPane();

        tablaPersonas = new TableView<>();
        TableColumn<Persona, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<Persona, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombre()));

        TableColumn<Persona, String> colDireccion = new TableColumn<>("Direccion");
        colDireccion.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(agenda.arrayToString(cellData.getValue().getDirecciones())));

        tablaPersonas.getColumns().addAll(colID, colNombre, colDireccion);
        tablaPersonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Persona> personasToTabla = FXCollections.observableArrayList(agenda.getPeople());
        tablaPersonas.setItems(personasToTabla);

        VBox vBoxPersonas = new VBox(5, new Label("Personas"), tablaPersonas);
        vBoxPersonas.setPadding(new Insets(10));
        vBoxPersonas.setPrefWidth(600);

        tablaTelefonos = new TableView<>();
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
        vBoxTelefonos.setPrefWidth(600);

        HBox tablasBox = new HBox(10, vBoxPersonas, vBoxTelefonos);
        tablasBox.setPadding(new Insets(10));
        tablasBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(tablasBox);
        scrollPane.setFitToWidth(true);

        tablaPane.setCenter(scrollPane);
        primerPane.setTop(topBox);
        root.setTop(primerPane);
        root.setCenter(tablaPane);
        Scene scene = new Scene(root, 1200,700);
        stage.setTitle("Agenda");
        stage.setScene(scene);
        stage.show();
    }

    private void botonAgregar() {
        Stage stage = new Stage();
        stage.setTitle("Agregar");
        BorderPane newRoot = new BorderPane();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Persona", "Telefono");

        comboBox.setOnAction(e -> {
            switch (comboBox.getValue()) {
                case "Persona":
                    Label labelNombre = new Label("Nombre");
                    Label labelDireccion = new Label("Direccion");
                    TextField TFNombre = new TextField();
                    TextField TFDireccion = new TextField();
                    Button botonGuardar = new Button("Guardar");

                    botonGuardar.setOnAction(a -> {
                        String nombre = TFNombre.getText();
                        String direccion = TFDireccion.getText();
                        try {
                            ArrayList<Direccion> direccionesPersona = agenda.stringToArray(direccion, agenda.getPeople().getLast().getId()+1);
                            Persona persona = new Persona(nombre,agenda.getPeople().getLast().getId()+1, direccionesPersona);

                            agenda.addPersonData(persona);
                            ObservableList<Persona> personasToTabla = FXCollections.observableArrayList(agenda.getPeople());
                            tablaPersonas.setItems(personasToTabla);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    VBox personaInfo = new VBox(10, labelNombre, TFNombre, labelDireccion, TFDireccion, botonGuardar);
                    newRoot.setCenter(personaInfo);
                    break;
                case "Telefono":
                    Label labelPersonaID = new Label("PersonaID");
                    Label labelTelefono = new Label("Telefono");
                    TextField TFPersonaID = new TextField();
                    TextField TFTelefono = new TextField();
                    Button botonGuardar1 = new Button("Guardar");

                    botonGuardar1.setOnAction(a1 -> {
                        int personaID = Integer.parseInt(TFPersonaID.getText());
                        String telefono = TFTelefono.getText();
                        Telefono telefono1 = new Telefono(telefono,-1,personaID);

                        try {
                            agenda.addTelephoneData(telefono1);
                            ObservableList<Telefono> telefonosToTabla = FXCollections.observableArrayList(agenda.getTelephones());
                            tablaTelefonos.setItems(telefonosToTabla);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    VBox vTelefonosInfo = new VBox(10, labelPersonaID, TFPersonaID, labelTelefono, TFTelefono, botonGuardar1);
                    newRoot.setCenter(vTelefonosInfo);
                    break;
            }
        });

        VBox vMainBox = new VBox(20, comboBox);
        vMainBox.setAlignment(Pos.CENTER);

        newRoot.setTop(vMainBox);
        stage.setScene(new Scene(newRoot, 400, 400));
        stage.show();

    }

    private void botonEliminar() {
        Stage stage = new Stage();
        stage.setTitle("Eliminar");
        BorderPane newRoot = new BorderPane();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Personas", "Telefono");

        comboBox.setOnAction(e -> {
            Label labelID = new Label("Ingresa el ID a eliminar");
            TextField TFID = new TextField();
            Button botonEliminar = new Button("Eliminar");

            botonEliminar.setOnAction(action -> {
                if (comboBox.getValue() == "Personas") {
                    try {
                        int ID = Integer.parseInt(TFID.getText());
                        Persona persona = buscarPersona(ID);

                        agenda.deletePersonData(persona);
                        ObservableList<Persona> personasToTablas = FXCollections.observableArrayList(agenda.getPeople());
                        ObservableList<Telefono> telefonosToTablas = FXCollections.observableArrayList(agenda.getTelephones());

                        tablaPersonas.setItems(personasToTablas);
                        tablaTelefonos.setItems(telefonosToTablas);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        int ID = Integer.parseInt(TFID.getText());
                        Telefono telefono = buscarTelefono(ID);

                        agenda.deleteTelephoneData(telefono);
                        ObservableList<Telefono> telefonosToTabla = FXCollections.observableArrayList(agenda.getTelephones());
                        tablaTelefonos.setItems(telefonosToTabla);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            VBox vPersonasID = new VBox(10, labelID, TFID, botonEliminar);
            newRoot.setCenter(vPersonasID);
        });

        VBox vMainBox = new VBox(20, comboBox);
        vMainBox.setAlignment(Pos.CENTER);

        newRoot.setTop(vMainBox);
        stage.setScene(new Scene(newRoot, 400, 400));
        stage.show();
    }

    private void botonEditar() {
        Stage stage = new Stage();
        stage.setTitle("Ediiar dato");
        BorderPane newRoot = new BorderPane();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Persona", "Telefono");

        comboBox.setOnAction(action -> {
            Label labelID = new Label("Ingresa el ID");
            TextField TFD = new TextField();
            Button botonBuscar = new Button("Buscar");

            botonBuscar.setOnAction(e -> {
                int ID = Integer.parseInt(TFD.getText());
                switch (comboBox.getValue()) {
                    case "Persona":
                        try {
                            newRoot.setCenter(null);
                            Persona persona = buscarPersona(ID);

                            Label labelNombre = new Label("Nombre");
                            TextField TFNombre = new TextField(persona.getNombre());

                            Label labelDireccion = new Label("Direccion");
                            TextField TFDireccion = new TextField(agenda.arrayToString(persona.getDirecciones()));

                            Button botonGuardar1 = new Button("Guardar cambios");

                            botonGuardar1.setOnAction(newAction -> {
                                persona.setNombre(TFNombre.getText());
                                try {
                                    persona.setDirecciones(agenda.stringToArray(TFDireccion.getText(), persona.getId()));


                                    agenda.editPersonData(persona);
                                    ObservableList<Persona> personasToTablas = FXCollections.observableArrayList(agenda.getPeople());
                                    tablaPersonas.setItems(personasToTablas);

                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }


                            });

                            VBox newBoxPerson = new VBox(10, labelNombre, TFNombre, labelDireccion, TFDireccion, botonGuardar1);
                            newRoot.setCenter(newBoxPerson);


                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } finally {

                        }
                        break;
                    case "Telefono":
                        try {
                            newRoot.setCenter(null);
                            Telefono telefono = buscarTelefono(ID);

                            Label labelNumero = new Label("Numero");
                            TextField TFNumero = new TextField(telefono.getTelefono());
                            Button botonGuardar = new Button("Guardar cambios");

                            botonGuardar.setOnAction(e1 -> {
                                telefono.setTelefono(TFNumero.getText());

                                try {
                                    agenda.editTelephoneData(telefono);
                                    ObservableList<Telefono> telefonosToTablas = FXCollections.observableArrayList(agenda.getTelephones());
                                    tablaTelefonos.setItems(telefonosToTablas);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            });

                            VBox newBoxTelephone = new VBox(10, labelNumero, TFNumero, botonGuardar);
                            newRoot.setCenter(newBoxTelephone);

                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        break;
                }
            });

            VBox vID = new VBox(10, labelID, TFD, botonBuscar);
            newRoot.setCenter(vID);
        });

        VBox vMainBox = new VBox(10, comboBox);
        vMainBox.setAlignment(Pos.CENTER);

        newRoot.setTop(vMainBox);
        stage.setScene(new Scene(newRoot, 400, 400));
        stage.show();

    }

    private Persona buscarPersona(int iD) throws SQLException {

        int size = agenda.getPeople().size();
        for (int i = 0; i < size; i++) {
            Persona persona = agenda.getPeople().get(i);
            if (iD == persona.getId()) {
                return persona;
            }
        }
        return null;
    }

    private Telefono buscarTelefono(int iD) throws SQLException {
        int size = agenda.getTelephones().size();
        for (int i = 0; i < size; ++i) {
            Telefono telefono = agenda.getTelephones().get(i);
            if (iD == telefono.getId()) {
                return telefono;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        launch(args);

    }
}
