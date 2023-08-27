module cz.daiton {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;

    opens cz.daiton to javafx.graphics;
    exports cz.daiton;
}
