module cz.daiton {
    requires javafx.controls;
    requires javafx.fxml;

    opens cz.daiton to javafx.fxml;
    exports cz.daiton;
}
