package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.PersonaException;
import logic.entities.AggressionType;
import logic.entities.Persona;
import logic.entities.Side;
import logic.services.impl.IPersonaServices;
import logic.services.impl.PersonaServices;

import javax.swing.text.LabelView;
import javax.swing.text.html.ImageView;
import javax.xml.crypto.NodeSetData;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

public class MainScene extends Application {


    //Vbox
    private VBox layout2;
    private VBox layout3;
    private VBox infoLayout;
    private VBox crudVBox;
    private VBox summaryLayout = new VBox();

    //Hbox
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;
    private HBox hBox4;


    //Buttons
    private Button selectPersona;
    private Button addPersona;
    private Button deletePersona;
    private Button editPersona;

    //TextFields
    private TextField nameInput;
    private TextField lastNameInput;
    private TextField ageInput;

    //ChoiceBoxes
    private ChoiceBox isVictim;
    private ChoiceBox side;
    private ChoiceBox aggression;

    //Info Labels
    private Label nameTitle;
    private Label ageInfo;
    private Label victimInfo;
    private Label aggressionInfo;
    private Label sideInfo;
    private Label isVictimChoice;
    private Label sideChoice;
    private Label aggressionChoice;

    //Summary Labels
    private Label totalVictimsNumber;
    private Label totalVictim;
    private Label totalPoliceVictims;
    private Label totalPoliceVictimsNumber;
    private Label totalCiviliansVictims;
    private Label totalCiviliansVictimsNumber;
    private Label pViolenciaHomicida;
    private Label pViolenciaHomicidaNumber;
    private Label pViolenciaConArmas;
    private Label pViolenciaConArmasNumber;
    private Label pViolenciaSexual;
    private Label pViolenciaSexualNumber;
    private Label mViolenciaHomicida;
    private Label mViolenciaHomicidaNumber;
    private Label mViolenciaConArmas;
    private Label mViolenciaConArmasNumber;
    private Label mViolenciaSexual;
    private Label mViolenciaSexualNumber;
    private Label edadAdolecentes;
    private Label edadAdolecentesNumber;
    private Label edadAdultosJovenes;
    private Label edadAdultosJovenesNumber;
    private Label edadAdultos;
    private Label edadAdultosNumber;
    private Label edadAdultosMayores;
    private Label edadAdultosMayoresNumber;

    //VisualProperties
    private Scene scene;
    private Scene scene2;
    private TableView<Persona> personasTable;

    //Menu
    private MenuBar menuBar;
    private Map<String, MenuItem> fileMenuItems;

    //Image
    private ImageView imv;
    private Image image;

    //Logic Properties
    private IPersonaServices personaServices;
    //Persona
    private Persona mia;
    private Persona sebas;


    {
        try {
            mia = new Persona("Mia","Lacouture",18,true, AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS, Side.CIVILIAN);
            sebas = new Persona("Sebastian","Guevara",19,false, AggressionType.VIOLENCIA_SEXUAL, Side.POLICE);
        } catch (PersonaException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {

        setUp();
        behaviour(primaryStage);
        primaryStage.setTitle("CRUD");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void behaviour(Stage stage) throws IOException, ClassNotFoundException {

        this.personaServices = new PersonaServices();

        personasTable.setItems((ObservableList<Persona>) this.personaServices.getAll());

        addPersona.setOnAction(e ->
        {
            boolean victim = false;
            Enum aggressionType = AggressionType.NO_APLICA;
            Enum sideType = null;

            if (isVictim.getSelectionModel().getSelectedItem().equals("SI"))
                victim=true;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA SEXUAL"))
                aggressionType = AggressionType.VIOLENCIA_SEXUAL;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA HOMICIDA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_CON_ARMAS;
            if (side.getSelectionModel().getSelectedItem().equals("POLICIA"))
                sideType = Side.POLICE;
            if (side.getSelectionModel().getSelectedItem().equals("MANIFESTANTE"))
                sideType = Side.CIVILIAN;



            try {
                int age = Integer.parseInt(ageInput.getText());
                if (age < 0) throw new PersonaException(PersonaException.BAD_AGE_LOWER);
                if (age > 120) throw new PersonaException(PersonaException.BAD_AGE_UPPER);
                Persona p = new Persona(nameInput.getText(),lastNameInput.getText(),age,victim,aggressionType,sideType);
                this.personaServices.insert(p);
                nameInput.clear();
                lastNameInput.clear();
                ageInput.clear();
                isVictim.getSelectionModel().clearSelection();
                side.getSelectionModel().clearSelection();
                aggression.getSelectionModel().clearSelection();

            } catch (PersonaException personaException) {
                personaException.printStackTrace();

            }
            catch (NumberFormatException er)
            {
                try {
                    throw new PersonaException(PersonaException.BAD_AGE + " : " + er.getMessage());
                } catch (PersonaException personaException) {
                    personaException.printStackTrace();
                }
            }
            totalVictimsNumber.setText(String.valueOf(personaServices.getVictims().size()));
            totalPoliceVictimsNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()+personaServices.getpViolenciaHomicida().size()+personaServices.getpViolenciaConArmas().size()));
            totalCiviliansVictimsNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()+personaServices.getmViolenciaHomicida().size()+personaServices.getmViolenciaConArmas().size()));
            pViolenciaConArmasNumber.setText(String.valueOf(personaServices.getpViolenciaConArmas().size()));
            pViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getpViolenciaHomicida().size()));
            pViolenciaSexualNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()));
            mViolenciaConArmasNumber.setText(String.valueOf(personaServices.getmViolenciaConArmas().size()));
            mViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getmViolenciaHomicida().size()));
            mViolenciaSexualNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()));
        });

        selectPersona.setOnAction(e ->
        {
            String isVictim1 = "NO";

            nameTitle.setText( personasTable.getSelectionModel().getSelectedItem().getFull());
            ageInfo.setText("Edad: "+personasTable.getSelectionModel().getSelectedItem().getAge());
            sideInfo.setText("Bando: "+personasTable.getSelectionModel().getSelectedItem().getSide());
            if (personasTable.getSelectionModel().getSelectedItem().isVictim())
                isVictim1="SI";
            victimInfo.setText("Es victima?: "+isVictim1);
            aggressionInfo.setText("Tipo de agresion: "+ personasTable.getSelectionModel().getSelectedItem().getAggressionType());

        });

        deletePersona.setOnAction(e ->
        {
            this.personaServices.delete(personasTable.getSelectionModel().getSelectedItem());
            totalVictimsNumber.setText(String.valueOf(personaServices.getVictims().size()));
            totalPoliceVictimsNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()+personaServices.getpViolenciaHomicida().size()+personaServices.getpViolenciaConArmas().size()));
            totalCiviliansVictimsNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()+personaServices.getmViolenciaHomicida().size()+personaServices.getmViolenciaConArmas().size()));
            pViolenciaConArmasNumber.setText(String.valueOf(personaServices.getpViolenciaConArmas().size()));
            pViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getpViolenciaHomicida().size()));
            pViolenciaSexualNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()));
            mViolenciaConArmasNumber.setText(String.valueOf(personaServices.getmViolenciaConArmas().size()));
            mViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getmViolenciaHomicida().size()));
            mViolenciaSexualNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()));


        });
        fileMenuItems.get("Export").setOnAction(e ->
        {
            try {
                this.personaServices.export();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        editPersona.setOnAction(e ->
        {
            boolean victim = false;
            Enum aggressionType = AggressionType.NO_APLICA;
            Enum sideType = null;

            if(isVictim.getSelectionModel().getSelectedItem().equals("SI"))
                victim=true;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA SEXUAL"))
                aggressionType = AggressionType.VIOLENCIA_SEXUAL;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA HOMICIDA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_CON_ARMAS;
            if (side.getSelectionModel().getSelectedItem().equals("POLICIA"))
                sideType = Side.POLICE;
            if (side.getSelectionModel().getSelectedItem().equals("MANIFESTANTE"))
                sideType = Side.CIVILIAN;

            this.personaServices.edit(nameInput.getText(),lastNameInput.getText(),Integer.parseInt(ageInput.getText()),victim,aggressionType,sideType,personasTable.getSelectionModel().getSelectedItem());
            nameInput.clear();
            lastNameInput.clear();
            ageInput.clear();
            isVictim.getSelectionModel().clearSelection();
            side.getSelectionModel().clearSelection();
            aggression.getSelectionModel().clearSelection();

            totalVictimsNumber.setText(String.valueOf(personaServices.getVictims().size()));
            totalPoliceVictimsNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()+personaServices.getpViolenciaHomicida().size()+personaServices.getpViolenciaConArmas().size()));
            totalCiviliansVictimsNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()+personaServices.getmViolenciaHomicida().size()+personaServices.getmViolenciaConArmas().size()));
            pViolenciaConArmasNumber.setText(String.valueOf(personaServices.getpViolenciaConArmas().size()));
            pViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getpViolenciaHomicida().size()));
            pViolenciaSexualNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()));
            mViolenciaConArmasNumber.setText(String.valueOf(personaServices.getmViolenciaConArmas().size()));
            mViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getmViolenciaHomicida().size()));
            mViolenciaSexualNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()));
        });

        fileMenuItems.get("Export").setOnAction(e ->
        {
            try {
                this.personaServices.export();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        fileMenuItems.get("Import").setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select personas file");
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) {
                System.out.println("No file");
            } else {
                try {
                    this.personaServices.importPersonas(file);
                    this.personaServices.getAll().stream().forEach(p -> System.out.println(p));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

    }
    private void setUp()
    {
        setUpCrud();
        setUpInputs();
        setupTable();
        setUpMenu();
        setUpLabels();
        setUplayout();


        BorderPane layout = new BorderPane();
        layout.setLeft(layout3);
        layout.setRight(layout2);
        layout.setTop(menuBar);


        scene = new Scene(layout,1100,800);
    }
    private void setUpCrud()
    {
        selectPersona = new Button("Select");
        selectPersona.setMinWidth(30);
        selectPersona.setPadding(new Insets(20));

        addPersona = new Button("Add");
        addPersona.setMinWidth(150);
        addPersona.setPadding(new Insets(20,20,20,20));

        deletePersona = new Button("Delete");
        deletePersona.setMinWidth(150);
        deletePersona.setPadding(new Insets(20));

        editPersona = new Button("Editar");
        editPersona.setMinWidth(150);
        editPersona.setPadding(new Insets(20));


    }
    private void setupTable()
    {


        TableColumn<Persona, String> actoresColumn= new TableColumn<>("Actores");

        actoresColumn.setCellValueFactory(new PropertyValueFactory<>("full"));
        actoresColumn.setMinWidth(400);

        personasTable = new TableView<>();
        personasTable.setMinWidth(400);
        personasTable.setMinHeight(780);
        personasTable.getColumns().addAll(actoresColumn);
        personasTable.setBorder(new Border(new BorderStroke(Color.valueOf("#4498C4"), BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(5),new Insets(20,20,20,20))));

    }
    private void setUpInputs()
    {
        nameInput = new TextField();
        nameInput.setPromptText("Nombre");
        nameInput.setMinWidth(177);

        lastNameInput = new TextField();
        lastNameInput.setPromptText("Apellido");
        lastNameInput.setMinWidth(177);

        ageInput = new TextField();
        ageInput.setPromptText("Edad");
        ageInput.setMinWidth(177);

        isVictim = new ChoiceBox(FXCollections.observableArrayList("SI","NO"));
        isVictim.setMinWidth(150);

        side = new ChoiceBox(FXCollections.observableArrayList("POLICIA","MANIFESTANTE"));
        side.setMinWidth(150);

        aggression = new ChoiceBox(FXCollections.observableArrayList("VIOLENCIA HOMICIDA CON ARMAS","VIOLENCIA CON ARMAS","VIOLENCIA SEXUAL", "NO APLICA"));



    }
    private void setUpMenu()
    {
        //Directory Menu
        Menu directoryMenu = new Menu("Directory");

        fileMenuItems = new HashMap<>();
        fileMenuItems.put("Import", new MenuItem("Import"));
        fileMenuItems.put("Export", new MenuItem("Export"));

        directoryMenu.getItems().add(fileMenuItems.get("Import"));
        directoryMenu.getItems().add(fileMenuItems.get("Export"));

        Menu summaryMenu = new Menu("Summary");
        Stage summary = new Stage();

        this.scene2 = new Scene(summaryLayout,1325,500);


        fileMenuItems.put("Show summary",new MenuItem("Show summary"));

        summaryMenu.getItems().add(fileMenuItems.get("Show summary"));
        summaryMenu.setOnAction(e->
        {
            summary.show();
            summary.setScene(scene2);
            totalVictimsNumber.setText(String.valueOf(personaServices.getVictims().size()));
            totalPoliceVictimsNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()+personaServices.getpViolenciaHomicida().size()+personaServices.getpViolenciaConArmas().size()));
            totalCiviliansVictimsNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()+personaServices.getmViolenciaHomicida().size()+personaServices.getmViolenciaConArmas().size()));
            pViolenciaConArmasNumber.setText(String.valueOf(personaServices.getpViolenciaConArmas().size()));
            pViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getpViolenciaHomicida().size()));
            pViolenciaSexualNumber.setText(String.valueOf(personaServices.getpViolenciaSexual().size()));
            mViolenciaConArmasNumber.setText(String.valueOf(personaServices.getmViolenciaConArmas().size()));
            mViolenciaHomicidaNumber.setText(String.valueOf(personaServices.getmViolenciaHomicida().size()));
            mViolenciaSexualNumber.setText(String.valueOf(personaServices.getmViolenciaSexual().size()));
        });

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(directoryMenu,summaryMenu);

    }
    private void setUpLabels()
    {
        //NameTitle
        this.nameTitle = new Label("Principales Actores del Paro");
        this.nameTitle.setMinWidth(600);
        this.nameTitle.setAlignment(Pos.CENTER);
        this.nameTitle.setPadding(new Insets(20,20,20,20));
        this.nameTitle.setBorder(new Border(new BorderStroke(Color.valueOf("#4498C4"), BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(5),new Insets(20,50,20,10))));
        this.nameTitle.setFont(Font.font("Arial", FontWeight.BOLD,35));

        //InfoLabels

        this.ageInfo = new Label("Edad: ");
        this.ageInfo.setMinWidth(600);
        this.ageInfo.setAlignment(Pos.TOP_LEFT);
        this.ageInfo.setPadding(new Insets(5,20,5,20));
        this.ageInfo.setFont(new Font(20));

        this.sideInfo = new Label("Bando: ");
        this.sideInfo.setMinWidth(600);
        this.sideInfo.setAlignment(Pos.TOP_LEFT);
        this.sideInfo.setPadding(new Insets(5,20,5,20));
        this.sideInfo.setFont(new Font(20));

        this.victimInfo = new Label("Es victima?: ");
        this.victimInfo.setMinWidth(600);
        this.victimInfo.setAlignment(Pos.TOP_LEFT);
        this.victimInfo.setPadding(new Insets(5,20,5,20));
        this.victimInfo.setFont(new Font(20));

        this.aggressionInfo = new Label("Tipo de agresion: ");
        this.aggressionInfo.setMinWidth(600);
        this.aggressionInfo.setAlignment(Pos.TOP_LEFT);
        this.aggressionInfo.setPadding(new Insets(5,20,30,20));
        this.aggressionInfo.setFont(new Font(20));





        this.isVictimChoice = new Label("Es victima?");
        this.sideChoice = new Label("Bando");
        this.aggressionChoice = new Label("Tipo de agresion");

        this.totalVictim = new Label("Victimas de agresion fisica grave o letal");
        this.totalVictim.setMinWidth(650);
        this.totalVictim.setPadding(new Insets(11,0,0,0));
        this.totalVictim.setAlignment(Pos.CENTER_RIGHT);
        this.totalVictim.setFont(new Font(35));



        this.totalVictimsNumber = new Label();
        this.totalVictimsNumber.setAlignment(Pos.CENTER_LEFT);
        this.totalVictimsNumber.setFont(new Font(50));

        this.totalPoliceVictims = new Label("Victimas Policiales\nTotales");
        this.totalPoliceVictims.setMinWidth(350);
        this.totalPoliceVictims.setPadding(new Insets(0,0,0,20));
        this.totalPoliceVictims.setAlignment(Pos.CENTER_LEFT);
        this.totalPoliceVictims.setFont(new Font(25));

        this.totalPoliceVictimsNumber = new Label("12");
        this.totalPoliceVictimsNumber.setAlignment(Pos.CENTER_LEFT);
        this.totalPoliceVictimsNumber.setFont(new Font(40));

        this.totalCiviliansVictims = new Label("Victimas Civiles\nTotales");
        this.totalCiviliansVictims.setMinWidth(350);
        this.totalCiviliansVictims.setPadding(new Insets(0,0,0,20));
        this.totalCiviliansVictims.setAlignment(Pos.CENTER_LEFT);
        this.totalCiviliansVictims.setFont(new Font(25));

        this.totalCiviliansVictimsNumber = new Label();
        this.totalCiviliansVictimsNumber.setAlignment(Pos.CENTER_LEFT);
        this.totalCiviliansVictimsNumber.setFont(new Font(40));

        this.pViolenciaConArmas = new Label("Victimas Policiales\nde Violencia con Armas");
        this.pViolenciaConArmas.setPadding(new Insets(5,0,0,20));
        this.pViolenciaConArmas.setAlignment(Pos.CENTER_LEFT);
        this.pViolenciaConArmas.setFont(new Font(20));

        this.pViolenciaConArmasNumber = new Label();
        this.pViolenciaConArmasNumber.setAlignment(Pos.CENTER_LEFT);
        this.pViolenciaConArmasNumber.setFont(new Font(35));



        this.edadAdolecentes = new Label("Edad victimas\n0 a 15");
        this.edadAdolecentes.setPadding(new Insets(5,0,0,20));
        this.edadAdolecentes.setAlignment(Pos.CENTER_LEFT);
        this.edadAdolecentes.setFont(new Font(20));

        this.edadAdolecentesNumber = new Label("0");
        this.edadAdolecentesNumber.setAlignment(Pos.CENTER_LEFT);
        this.edadAdolecentesNumber.setFont(new Font(35));


        this.edadAdultosJovenes = new Label("Edad victimas\n16 a 30");
        this.edadAdultosJovenes.setPadding(new Insets(5,0,0,20));
        this.edadAdultosJovenes.setAlignment(Pos.CENTER_LEFT);
        this.edadAdultosJovenes.setFont(new Font(20));

        this.edadAdultosJovenesNumber = new Label("0");
        this.edadAdultosJovenesNumber.setAlignment(Pos.CENTER_LEFT);
        this.edadAdultosJovenesNumber.setPadding(new Insets(5,0,0,80));
        this.edadAdultosJovenesNumber.setFont(new Font(35));


        this.edadAdultos = new Label("Edad victimas\n31 a 60");
        this.edadAdultos.setPadding(new Insets(5,0,0,20));
        this.edadAdultos.setAlignment(Pos.CENTER_LEFT);
        this.edadAdultos.setFont(new Font(20));

        this.edadAdultosNumber = new Label("0");
        this.edadAdultosNumber.setAlignment(Pos.CENTER_LEFT);
        this.edadAdultosNumber.setPadding(new Insets(5,0,0,80));
        this.edadAdultosNumber.setFont(new Font(35));

        this.edadAdultosMayores = new Label("Edad victimas\n61 a 100");
        this.edadAdultosMayores.setPadding(new Insets(5,0,0,20));
        this.edadAdultosMayores.setAlignment(Pos.CENTER_LEFT);
        this.edadAdultosMayores.setFont(new Font(20));

        this.edadAdultosMayoresNumber = new Label("0");
        this.edadAdultosMayoresNumber.setAlignment(Pos.CENTER_LEFT);
        this.edadAdultosMayoresNumber.setPadding(new Insets(5,0,0,80));
        this.edadAdultosMayoresNumber.setFont(new Font(35));




        this.pViolenciaHomicida = new Label("Victimas Policiales\nde Violencia Homicida");
        this.pViolenciaHomicida.setPadding(new Insets(5,0,0,20));
        this.pViolenciaHomicida.setAlignment(Pos.CENTER_LEFT);
        this.pViolenciaHomicida.setFont(new Font(20));

        this.pViolenciaHomicidaNumber = new Label();
        this.pViolenciaHomicidaNumber.setAlignment(Pos.CENTER_LEFT);
        this.pViolenciaHomicidaNumber.setPadding(new Insets(0,0,0,70));
        this.pViolenciaHomicidaNumber.setFont(new Font(35));

        this.pViolenciaSexual = new Label("Victimas Policiales\nde Violencia Sexual");
        this.pViolenciaSexual.setPadding(new Insets(5,0,0,20));
        this.pViolenciaSexual.setAlignment(Pos.CENTER_LEFT);
        this.pViolenciaSexual.setFont(new Font(20));

        this.pViolenciaSexualNumber = new Label();
        this.pViolenciaSexualNumber.setAlignment(Pos.CENTER_LEFT);
        this.pViolenciaSexualNumber.setPadding(new Insets(0,0,0,70));
        this.pViolenciaSexualNumber.setFont(new Font(35));

        this.mViolenciaConArmas = new Label("Victimas Civiles\nde Violencia con Armas");
        this.mViolenciaConArmas.setPadding(new Insets(5,0,0,20));
        this.mViolenciaConArmas.setAlignment(Pos.CENTER_LEFT);
        this.mViolenciaConArmas.setFont(new Font(20));

        this.mViolenciaConArmasNumber = new Label();
        this.mViolenciaConArmasNumber.setAlignment(Pos.CENTER_LEFT);
        this.mViolenciaConArmasNumber.setFont(new Font(35));

        this.mViolenciaHomicida = new Label("Victimas Civiles\nde Violencia Homicida");
        this.mViolenciaHomicida.setAlignment(Pos.CENTER_LEFT);
        this.mViolenciaHomicida.setPadding(new Insets(5,0,0,20));
        this.mViolenciaHomicida.setFont(new Font(20));

        this.mViolenciaHomicidaNumber = new Label();
        this.mViolenciaHomicidaNumber.setAlignment(Pos.CENTER_LEFT);
        this.mViolenciaHomicidaNumber.setPadding(new Insets(0,0,0,70));
        this.mViolenciaHomicidaNumber.setFont(new Font(35));

        this.mViolenciaSexual = new Label("Victimas Civiles\nde Violencia Sexual");
        this.mViolenciaSexual.setAlignment(Pos.CENTER_LEFT);
        this.mViolenciaSexual.setPadding(new Insets(5,0,0,20));
        this.mViolenciaSexual.setFont(new Font(20));

        this.mViolenciaSexualNumber = new Label();
        this.mViolenciaSexualNumber.setAlignment(Pos.CENTER_LEFT);
        this.mViolenciaSexualNumber.setPadding(new Insets(0,0,0,70));
        this.mViolenciaSexualNumber.setFont(new Font(35));

    }
    private void setUplayout()
    {
        layout2 = new VBox();
        infoLayout = new VBox();
        layout3 = new VBox();
        layout3.getChildren().addAll(personasTable);

        selectPersona.setMinWidth(550);
        selectPersona.setAlignment(Pos.BOTTOM_CENTER);

        hBox1 = new HBox();
        hBox1.getChildren().addAll(addPersona,deletePersona,editPersona);
        hBox1.setPadding(new Insets(20,0,20,0));
        hBox1.setSpacing(50);
        hBox2 = new HBox();
        hBox2.getChildren().addAll(nameInput,lastNameInput,ageInput);
        hBox2.setPadding(new Insets(20,0,20,0));
        hBox2.setSpacing(10);

        VBox h1 = new VBox();
        h1.getChildren().addAll(isVictimChoice,isVictim);
        VBox h2 = new VBox();
        h2.getChildren().addAll(sideChoice,side);
        VBox h3 = new VBox();
        h3.getChildren().addAll(aggressionChoice,aggression);

        hBox3 = new HBox();
        hBox3.getChildren().addAll(h1,h2,h3);
        hBox3.setSpacing(12);

        crudVBox= new VBox();
        crudVBox.getChildren().addAll(hBox2,hBox3,hBox1);

        infoLayout.getChildren().addAll(ageInfo,sideInfo,victimInfo,aggressionInfo,selectPersona);

        hBox4 = new HBox();

        layout2.getChildren().addAll(nameTitle,infoLayout,crudVBox, hBox4);

        //Summary menu layout

        HBox f = new HBox();
        f.setPadding(new Insets(20,0,0,80));
        f.getChildren().addAll(totalVictimsNumber,totalVictim);



        HBox f1 = new HBox();
        f1.setPadding(new Insets(80,0,0,80));
        f1.getChildren().addAll(totalPoliceVictimsNumber,totalPoliceVictims,pViolenciaConArmasNumber,pViolenciaConArmas,pViolenciaHomicidaNumber,pViolenciaHomicida,pViolenciaSexualNumber,pViolenciaSexual);

        HBox f2 = new HBox();
        f2.setPadding(new Insets(80,0,0,80));
        f2.getChildren().addAll(totalCiviliansVictimsNumber,totalCiviliansVictims,mViolenciaConArmasNumber,mViolenciaConArmas,mViolenciaHomicidaNumber,mViolenciaHomicida,mViolenciaSexualNumber,mViolenciaSexual);

        HBox f3 = new HBox();
        f3.setPadding(new Insets(80,0,0,80));
        f3.getChildren().addAll(edadAdolecentesNumber, edadAdolecentes
                , edadAdultosJovenesNumber, edadAdultosJovenes,
                edadAdultosNumber, edadAdultos,
                edadAdultosMayoresNumber, edadAdultosMayores);




        summaryLayout.getChildren().addAll(f,f1,f2, f3);

        Stop[] stops = new Stop[] {new Stop(0.4, Color.rgb(247,233,121)), new Stop(0.65, Color.rgb(121,176,247)), new Stop(1, Color.rgb(247,121,121))};
        LinearGradient lg = new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE, stops);

        summaryLayout.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}