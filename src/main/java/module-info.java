module com.ds.todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;


    opens com.ds.todo to javafx.fxml;
    exports com.ds.todo;
    exports com.ds.todo.task;
    opens com.ds.todo.task to javafx.fxml;
}