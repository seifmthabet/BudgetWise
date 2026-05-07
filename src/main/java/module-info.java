module com.budgetwise.budgetwise {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires jbcrypt;
    requires com.github.librepdf.openpdf;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.budgetwise.budgetwise.controllers to javafx.fxml;
    opens com.budgetwise.budgetwise.models to javafx.base;
    exports com.budgetwise.budgetwise;
}