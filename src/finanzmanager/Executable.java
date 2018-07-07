package finanzmanager;

import finanzmanager.controller.ViewHandler;

public class Executable {
    public static void main(String[] args) {

        new ViewHandler().launch(ViewHandler.class, args);
    }
}
