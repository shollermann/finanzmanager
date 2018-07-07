package finanzmanager.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StorageHandler {

    public static LoggingHandler loggingHandler;

    public StorageHandler() throws IOException {

         loggingHandler = new LoggingHandler(getClass())

        Connection connection = null;
        String url = "jdbc:sqlite:./transaction2.db";

        try {
            connection = DriverManager.getConnection(url);
            if (connection != null){
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("Driver Name: "+meta.getDriverName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
