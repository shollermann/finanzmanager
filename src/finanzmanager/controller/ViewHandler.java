package finanzmanager.controller;


import finanzmanager.model.MoneyTransfer;
import finanzmanager.model.Property;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewHandler extends Application {

    public TextField txtFixAusgaben;
    public TextField txtFixEinkommen;
    public MenuItem miTransaction;
    public TableColumn colAdditive;
    public TableColumn colValue;
    public TableColumn colDatum;
    public TableColumn colDescription;
    public TableColumn colSource;
    public TableColumn colId;
    /**
     * Controls
     */
    @FXML private Button btnExit;
    //@FXML private TableColumn colAdditive;
    @FXML private TableView<MoneyTransfer> tableTransactions;
    @FXML public ComboBox cbQuellen;
    @FXML public Button btnDelete;
    @FXML public Button btnEdit;
    @FXML public MenuItem miExit;
    @FXML public ListView lvSources;
    @FXML public Button btnSource;
    @FXML private Button btnTransaction;
    @FXML private Button btnAbort;

    /**
     * Properties
     */
    private Property mainWindow;
    private Property settingsWindow;
    private Property sourceWindow;
    private Property transactionWindow;
    private Scene scene;

    private StorageHandler storageHandler;


    public ViewHandler() {
        mainWindow = initProperty(mainWindow,"../view/desktop/Startup.fxml", 400, 800,  "Finanzmanager");
    }

    private Property initProperty(Property property, String viewPath, int height,int width, String title){
        property = new Property(viewPath);
        property.setHeight(height);
        property.setWidth(width);
        property.setTitle(title);
        return property;
    }

    @Override
    public void start(Stage stage) throws IOException {
        storageHandler = new StorageHandler(mainWindow);
        storageHandler.getAllSources();
        createView(stage, mainWindow);
        addDataToTable(storageHandler.getAllMoneyTransfers());
        calculateOverview(storageHandler.getAllMoneyTransfers());
    }

    private void calculateOverview(ObservableList<MoneyTransfer> allMoneyTransfers) {
        Double sumSingleIncome = 0.0;
        Double sumSingleOutcome = 0.0;
        Double sumDailyIncome = 0.0;
        Double sumWeeklyIncome = 0.0;
        Double sumMonthlyIncome = 0.0;
        Double sumYearlyIncome = 0.0;
        Double sumDailyOutcome = 0.0;
        Double sumWeeklyOutcome = 0.0;
        Double sumMonthlyOutcome = 0.0;
        Double sumYearlyOutcome = 0.0;


        for (MoneyTransfer moneyTransfer: allMoneyTransfers)
            if (moneyTransfer.isAdditive()=="+") {
                switch (moneyTransfer.getTransactionType()) {
                    case SINGLE:
                        sumSingleIncome+=moneyTransfer.getValue();
                        break;
                    case DAILY:
                        sumDailyIncome+=moneyTransfer.getValue();
                        break;
                    case WEEKLY:
                        sumWeeklyIncome+=moneyTransfer.getValue();
                        break;
                    case MONTHLY:
                        sumMonthlyIncome+=moneyTransfer.getValue();
                        break;
                    case YEARLY:
                        sumYearlyIncome+=moneyTransfer.getValue();
                        break;
                    }
                }else {
                switch (moneyTransfer.getTransactionType()){
                    case SINGLE:
                        sumSingleOutcome+=moneyTransfer.getValue();
                        break;
                    case DAILY:
                        sumDailyOutcome+=moneyTransfer.getValue();
                        break;
                    case WEEKLY:
                        sumWeeklyOutcome+=moneyTransfer.getValue();
                        break;
                    case MONTHLY:
                        sumMonthlyOutcome+=moneyTransfer.getValue();
                        break;
                    case YEARLY:
                        sumYearlyOutcome+=moneyTransfer.getValue();
                        break;
                }
            }
        txtFixEinkommen.setText(sumSingleIncome+mainWindow.getCurrencySymbol());
        txtFixAusgaben.setText(sumSingleOutcome+mainWindow.getCurrencySymbol());
    }

    private void addDataToTable(ObservableList<MoneyTransfer> allMoneyTransfer){

        List<PropertyValueFactory> propertyLists = new ArrayList<>();
        propertyLists.add(new PropertyValueFactory<>("id"));
        propertyLists.add(new PropertyValueFactory<>("additive"));
        propertyLists.add(new PropertyValueFactory<>("value"));
        propertyLists.add(new PropertyValueFactory<>("date"));
        propertyLists.add(new PropertyValueFactory<>("description"));

        for (int i =0; i < propertyLists.size(); i++){
            tableTransactions.getColumns().get(i).setCellValueFactory(propertyLists.get(i));
        }
        tableTransactions.setItems(allMoneyTransfer);

        for (MoneyTransfer moneyTransfer: allMoneyTransfer){
            System.out.println(moneyTransfer.toString());
        }
    }

    public void exit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    private Scene createView(Stage stage, Property property) throws IOException {
        stage.setTitle(property.getTitle());
        Parent root = FXMLLoader.load(getClass().getResource(property.getViewPath()));
        scene = new Scene(root, property.getWidth(),property.getHeight());
        tableTransactions = (TableView) scene.lookup("#tableTransactions");
        txtFixAusgaben = (TextField) scene.lookup("#txtFixAusgaben");
        txtFixEinkommen = (TextField) scene.lookup("#txtFixEinkommen");

        stage.setScene(scene);
        stage.show();
        return scene;
    }


    public void newTransaction() throws IOException {
        Stage stage = (Stage) btnTransaction.getScene().getWindow();
        transactionWindow = initProperty(transactionWindow, "../view/desktop/MoneyTransfer.fxml",400,800,"Neue Transaktion");
        createView(stage,transactionWindow);
    }

    public void returnToMain() throws IOException {
        Stage stage = (Stage) btnAbort.getScene().getWindow();
        createView(stage, mainWindow);
    }

    public void editSources() throws IOException {
        Stage stage = (Stage) btnSource.getScene().getWindow();
        sourceWindow = initProperty(sourceWindow, "../view/desktop/Sources.fxml", 400,800, "Quellen bearbeiten");
        createView(stage, sourceWindow);

    }

    public void btnEdit() throws IOException {
        Stage stage = (Stage) btnEdit.getScene().getWindow();
        transactionWindow = initProperty(transactionWindow, "../view/desktop/MoneyTransfer.fxml",400,800,"Neue Transaktion");
        createView(stage, transactionWindow);
    }

    public void btnDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sicherheitsbestätigung");
        alert.setHeaderText("Soll dieser Datensatz tatsächlich gelöscht werden?");
        alert.setContentText("Ja oder nein?");
        ButtonType buttonTypeYes = new ButtonType("Ja");
        ButtonType buttonTypeNo = new ButtonType("Nein");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()== buttonTypeYes){
            System.out.println("Datensatz wird gelöscht");
        } else {
            System.out.println("Nix");
        }
    }
}
