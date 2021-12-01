module com.me.asteroids {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.me.asteroids to javafx.fxml;
    exports com.me.asteroids;
}