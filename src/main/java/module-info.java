module com.ds.todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;


    opens com.ds.todo to javafx.fxml;
    exports com.ds.todo;
}