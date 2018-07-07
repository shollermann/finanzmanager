package finanzmanager.controller;

import finanzmanager.model.MoneyTransfer;
import finanzmanager.model.Property;
import finanzmanager.model.Source;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class StorageHandler {

    public static LoggingHandler loggingHandler;
    Connection connection = null;
    Property property;
    private String sqlQuery;
    private Object listClass;


    public StorageHandler(Property property) throws IOException {


        //loggingHandler = new LoggingHandler(getClass())
        this.property = property;

        String url = "jdbc:sqlite:./transaction.db";

        try {
            connection = DriverManager.getConnection(url);
            if (connection != null){
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("Driver Name: "+meta.getDriverName());
                if(createStructure()){
                    System.out.println("Data Structure created");
                }

                getAllMoneyTransfers();
                getAllSources();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createStructure() throws SQLException {
        boolean correct = false;
        String createTransactionStructure = "CREATE TABLE IF NOT EXISTS 'moneytransfer' (" +
                "additive integer NOT NULL," +
                "value real NOT NULL, " +
                "description text," +
                "date text," +
                "type blob" +
                ");";

        String createSourceStructure = "CREATE TABLE IF NOT EXISTS 'source' (" +
                "name text NOT NULL" +
                ")";

        String createSourceTransactionLink = "CREATE TABLE IF NOT EXISTS 'moneytransfer_source' (" +
                "date text," +
                "idSource integer," +
                "idTransaction integer," +
                "FOREIGN KEY(idSource) REFERENCES source(rowid)," +
                "FOREIGN KEY(idTransaction) REFERENCES moneytransfer(rowid)"+
                ")";

        try {
            try (Statement statement = connection.createStatement()) {
                System.out.println("Trying: " + createTransactionStructure);
                statement.execute(createTransactionStructure);

                System.out.println("Trying: " + createSourceStructure);
                statement.execute(createSourceStructure);

                System.out.println("Trying: " + createSourceTransactionLink);
                statement.execute(createSourceTransactionLink);

            }
            correct = true;
            System.out.println("Datastructure successfully validated");
        } catch (SQLException e) {
            System.out.println(e.getMessage()+"\n");
        }

        return correct;
    }

    public List<MoneyTransfer> getAllMoneyTransfers(){
        List<MoneyTransfer> moneyTransferList = new ArrayList();
        String sqlQuery = "SELECT rowid,* FROM moneytransfer";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()){
                Map<String, Object> row = getRowData(resultSet);
                moneyTransferList.add(generateMoneytransfer(row));
            }
            System.out.println(moneyTransferList.get(0).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return moneyTransferList;
    }

    public List<Source> getAllSources() {
        List<Source> sourceList = new ArrayList<>();
        String sql = "SELECT rowid,* FROM source";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Map<String, Object> row = getRowData(resultSet);
                sourceList.add(generateSourceList(row));
                sourceList.get(0).toString();

            }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sourceList;
        }

    private Source generateSourceList(Map<String,Object> dataSource) {
        System.out.println(dataSource.toString());
        int id = (int) dataSource.get("rowid");
        String name = (String) dataSource.get("name");
        Source source = new Source(id, name);
        return source;
    }


    public Source searchSource(String name){
        Source source = new Source(name);
        return source;
    }

    public MoneyTransfer generateMoneytransfer(Map <String, Object> dataSource) throws ParseException {
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        moneyTransfer.setAdditive((int)dataSource.get("additive") == 1);
        DateFormat dateFormat =  new SimpleDateFormat(property.getDateFormat(), Locale.GERMANY);
        Date date = dateFormat.parse((String) dataSource.get("date"));
        moneyTransfer.setDate(date);
        moneyTransfer.setDescription((String) dataSource.get("description"));
        moneyTransfer.setValue((Double) dataSource.get("value"));
        return moneyTransfer;
    }

    public MoneyTransfer searchMoneyTransfer(String fieldName, Object vielValue){
        MoneyTransfer moneyTransfer = new MoneyTransfer();

        return moneyTransfer;
    }

    public Map<String, Object> getRowData(ResultSet resultSet){
        Map<String, Object> rowData = new HashMap<>();
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for(int i = 1; i<resultSetMetaData.getColumnCount()+1; i++){
                String columnName = resultSetMetaData.getColumnName(i);

                Object columnValue = resultSet.getObject(columnName);
                rowData.put(columnName,columnValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowData;
    }

}
