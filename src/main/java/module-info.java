module org.KTKT {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.KTKT.Data.Matrix to javafx.fxml;
    exports org.KTKT.Data.Matrix;
    opens org.KTKT.Data.CosetSyndromWeightTable to javafx.fxml;
    exports org.KTKT.Data.CosetSyndromWeightTable;
    opens org.KTKT.Data to javafx.fxml;
    exports org.KTKT.Data;

    opens org.KTKT.SettingsPage to javafx.fxml;
    exports org.KTKT.SettingsPage;

    opens org.KTKT.Utils.WindowManager to javafx.fxml;
    exports org.KTKT.Utils.WindowManager;



    opens org.KTKT to javafx.fxml;
    exports org.KTKT;
}