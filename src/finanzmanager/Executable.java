package finanzmanager;

import finanzmanager.controller.Startup;

public class Executable {
    public static void main(String[] args) {

        new Startup().launch(Startup.class, args);
    }
}
