module pl.polsl.lab.justyna.ksiazek.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens pl.polsl.lab.justyna.ksiazek.controller to javafx.fxml;
    opens pl.polsl.lab.justyna.ksiazek.model to javafx.base;
    exports pl.polsl.lab.justyna.ksiazek.controller;
}
