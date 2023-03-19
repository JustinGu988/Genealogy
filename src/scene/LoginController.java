package scene;

import dataLayer.Common;
import dataLayer.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/***
 * @author Justin
 */
public class LoginController implements Initializable {

    public TextField userAccount;
    public Button loginButton;
    public Button registerButton;
    public PasswordField userPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Common.userList = Main.loadUserInfo();
    }

    //Login check
    public void login(ActionEvent actionEvent) {
        if (User.ifInfoOK(userAccount.getText(),userPassword.getText())){
            System.out.println("valid account and password");
            Common.currentStage.close();
            System.out.println("login scene closed");
            Main.startGenealogyScene();
        }else {
            Common.sendAlert(false,"Wrong username or password");
        }
    }

    public void register(ActionEvent actionEvent) {

        //check username
        if (User.ifNameExist(userAccount.getText())){
            Common.sendAlert(false,"Username exists. Please try again.");
            return;
        }
        //check pw
        if(userPassword.getText().length()<6){
            Common.sendAlert(false,"Password should be at least 6 char");
            return;
        }
        Common.userList.add(new User(userAccount.getText(),userPassword.getText()));
        Common.sendAlert(true,"Common account create successful");
    }


}
