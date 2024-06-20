module app.test.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.swing;
    requires java.desktop;


    opens app.test.app to javafx.fxml;
    exports app.test.app;
}