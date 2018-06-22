package finanzmanager.controller;


import finanzmanager.model.Property;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class Startup extends Application {

    /**
     * Controls
     */
    @FXML private Button btnExit;
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


    public Startup() {
        mainWindow = initProperty(mainWindow,"../view/javafx/Startup.fxml", 400, 800,  "Finanzmanager");
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
        transactionWindow = initProperty(transactionWindow, "../view/javafx/Transaction.fxml",400,800,"Neue Transaktion");
        createView(stage,transactionWindow);
    }

    public void returnToMain() throws IOException {
        Stage stage = (Stage) btnAbort.getScene().getWindow();
        createView(stage, mainWindow);
    }

    public void editSources() throws IOException {
        Stage stage = (Stage) btnSource.getScene().getWindow();
        sourceWindow = initProperty(sourceWindow, "../view/javafx/Sources.fxml", 400,800, "Quellen bearbeiten");
        createView(stage, sourceWindow);

    }
}
