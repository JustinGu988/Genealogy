package scene;

import dataLayer.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/***
 * @author Justin
 */
public class EditUserController implements Initializable {


    public TextArea UserListShower;
    public Button freshButton;
    public Button addButton;
    public Button deleteButton;
    public Button editButton;
    public TextField addUserName;
    public TextField addUserPassword;
    public TextField deleteUserName;
    public TextField editedUserName;
    public TextField editUserNameTo;
    public TextField editUserPasswordTo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void freshList(ActionEvent actionEvent) {
        StringBuilder tempString = new StringBuilder();
        tempString.append("User list:\n");
        for (int i = 0; i < Common.userList.size(); i++) {
            tempString.append(Common.userList.get(i).getAccount()+"\n");
        }
        UserListShower.setText(tempString.toString());
    }

    public void add(ActionEvent actionEvent) {
        if (User.ifNameExist(addUserName.getText())){
            Common.sendAlert(false,"Add failed. Username exists");
            return;
        }
        if(addUserPassword.getText().length()<6){
            Common.sendAlert(false,"Password must not be less than 6 char");
            return;
        }
        Common.userList.add(new User(addUserName.getText(),addUserPassword.getText()));
        Common.sendAlert(true,"Common user create successful");
    }

    public void delete(ActionEvent actionEvent) {
        if (deleteUserName.getText().equals(Common.currentUser.getAccount())){
            Common.sendAlert(false,"Unable to delete current acct");
        }else if (User.ifNameExist(deleteUserName.getText())){
            for (int i = 0; i < Common.userList.size(); i++) {
                if (Common.userList.get(i).getAccount().equals(deleteUserName.getText())){
                    Common.userList.remove(i);
                    break;
                }
            }
            Common.sendAlert(true,"Delete successful");
        }else {
            Common.sendAlert(true,"Delete failed. Username not found");
        }
    }

    public void edit(ActionEvent actionEvent) {
        if (!User.ifNameExist(editedUserName.getText())){
            Common.sendAlert(true,"Edit failed. Username not found");
            return;
        }
        if (User.ifNameExist(editUserNameTo.getText())){
            Common.sendAlert(false,"Edit failed. Username exist");
            return;
        }
        if(editUserPasswordTo.getText().length()<6){
            Common.sendAlert(false,"Password must not be less than 6 char");
            return;
        }
        int i;
        for (i = 0; i < Common.userList.size(); i++) {
            if (Common.userList.get(i).getAccount().equals(editedUserName.getText())){
                break;
            }
        }
        User temp = Common.userList.get(i);
        temp.setAccount(editUserNameTo.getText());
        temp.setPassword(editUserPasswordTo.getText());
        Common.sendAlert(true,"Edit successful");
    }
}
