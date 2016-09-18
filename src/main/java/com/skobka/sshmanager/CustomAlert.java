package com.skobka.sshmanager;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

public class CustomAlert {
    public static void show(
            Alert.AlertType alertType,
            String title,
            String message
    ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }
}
