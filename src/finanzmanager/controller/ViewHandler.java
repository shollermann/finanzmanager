package finanzmanager.controller;


import finanzmanager.model.Property;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ViewHandler extends Application {

    /**
     * Controls
     */
    @FXML private Button btnExit;
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
        StorageHandler storageHandler = new StorageHandler(mainWindow);
        createView(stage, mainWindow);
    }

    public void exit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    private Scene createView(Stage stage, Property property) throws IOException {
        stage.setTitle(property.getTitle());
        Parent root = FXMLLoader.load(getClass().getResource(property.getViewPath()));
        Scene scene = new Scene(root, property.getWidth(),property.getHeight());

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
