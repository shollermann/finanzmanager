package finanzmanager.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggingHandler {

    enum LoggingType{
        ERROR, WARNING, INFO
    }

    private String getLoggintTypeAsString(LoggingType loggingType){
        switch (loggingType){
            case ERROR:
                return "ERR ";
            case INFO:
                return "INFO ";
            case WARNING:
                return "WARN ";
        }
        return null;
    }

    public LoggingHandler(File logFile, Class aClass, LoggingType loggingType, String message) throws IOException {
        if(logFile.exists() && !logFile.isDirectory()){
            FileWriter fw = new FileWriter(logFile);
            fw.write(doLog(aClass,loggingType,message));
        } else if(!logFile.isDirectory()) {
            logFile.createNewFile();
            FileWriter fw = new FileWriter(logFile);
            fw.write(doLog(aClass,loggingType,message));
        } else {
            throw new IOException("File is a Directory, please provide a filename");
        }
    }

    private String doLog(Class aClass, LoggingType loggingType, String message){
        return getLoggintTypeAsString(loggingType)+" - " + aClass.getName() + " - " + message;
    }

}
