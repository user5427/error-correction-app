module org.KTKT {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.fxmisc.richtext;


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

    opens org.KTKT.CodingPages to javafx.fxml;
    exports org.KTKT.CodingPages;


    opens org.KTKT.Coding.Errors to javafx.fxml;
    exports org.KTKT.Coding.Errors;

    opens org.KTKT to javafx.fxml;
    exports org.KTKT;
}