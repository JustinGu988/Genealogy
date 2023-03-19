package dataLayer;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ArrayList;

/***
 * @author Justin
 */
public class Common {
    public static ArrayList<User> userList;
    public static String userInfoPath = "src/data/user.data";//user data storage location
    public static String treePath = "src/data/tree.data";//tree data storage location
    public static Stage currentStage;
    public static Parent currentRoot;//mark current node, for iteration/searching
    public static Tree currentTree;
    public static Member currentMember;
    public static User currentUser;


    //Sending alerts
    public static void sendAlert(boolean OK, String info){
        Alert alert;
        if(OK) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success Notice");
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failure Notice");
        }
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }
}
