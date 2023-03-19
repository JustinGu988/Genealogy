package scene;

import dataLayer.Common;
import dataLayer.LiveState;
import dataLayer.Member;
import dataLayer.Power;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ResourceBundle;

/***
 * @author Justin
 */
public class GenealogyController implements Initializable {

    public Button editButton;
    public Button searchButton;
    public TextField nameToSearch;
    public TextField name;
    public TextField gender;
    public TextField isAlive;
    public TextField birthday;
    public TextField deathday;
    public TextField marriage;
    public TextField address;
    public TextField generation;
    public TextField father;
    public TextField mother;
    public TextField descendents;
    public TextField extra;
    public TextArea chronicleList;
    public Button checkButton1;
    public TextField checkRelative2;
    public TextField checkRelative1;
    public TextField checkResult;
    public Canvas canvas;
    public Button editUserButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load tree
        Common.currentTree = Main.loadFamilyTree();
    }

    private void setChronicleList() {
        Common.currentTree.resort();
        LinkedList<Member> temp = Common.currentTree.getSortedlist();
        StringBuilder tempString = new StringBuilder();
        tempString.append("Sorted by DOB:\n");
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        int date = now.get(Calendar.DATE);
        Member tempMem;
        for (int i = 0; i < temp.size(); i++) {
            tempMem = temp.get(i);
            tempString.append(tempMem.getBirthday()[0]+"  "+tempMem.getName()+"\n");
        }
        chronicleList.setText(tempString.toString());
    }

    public void search(ActionEvent actionEvent) {
        if (nameToSearch.getText().length()<1){
            setChronicleList();
            TreeDrawing.draw(canvas,Common.currentTree);
        }else {
            Common.currentMember = Common.currentTree.find(nameToSearch.getText());
            if (Common.currentMember == null) {
                setChronicleList();
                TreeDrawing.draw(canvas,Common.currentTree);
                Common.sendAlert(false, "Name does not exist");
            } else {
                name.setText(Common.currentMember.getName());
                gender.setText(Common.currentMember.getGenderString());
                isAlive.setText(Common.currentMember.getIsAliveString());
                birthday.setText(Common.currentMember.getBirthdayString());
                deathday.setText(Common.currentMember.getDeathdayString());
                marriage.setText(Common.currentMember.getIfMarriedString());
                address.setText(Common.currentMember.getAddress());
                generation.setText(Common.currentMember.getGeneration() + "");
                if (Common.currentMember.getFather() != null)
                    father.setText(Common.currentMember.getFather().getName());
                if (Common.currentMember.getMother() != null)
                    mother.setText(Common.currentMember.getMother().getName());
                descendents.setText(Common.currentMember.getDescendentsString());
                extra.setText(Common.currentMember.getExtraMessage());
            }
        }

    }

    public void toEditScene(ActionEvent actionEvent) {
        //Admin onnly
        if (Common.currentUser.getPower()== Power.admin)
            Main.startEditScene();
        else
            Common.sendAlert(false,"You don't have the authority");
    }


    public void check(ActionEvent actionEvent) {
        if (checkRelative1.getText()!=""&&checkRelative2.getText()!=""){
            if (Common.currentTree.isDirectRelated(checkRelative1.getText(),checkRelative2.getText())){
                checkResult.setText("Immediate family");
            }else{
                checkResult.setText("Non-immediate family");
            }
        }
    }


    public void toEditUserScene(ActionEvent actionEvent) {
        if (Common.currentUser.getPower()== Power.admin)
            Main.startEditUserScene();
        else
            Common.sendAlert(false,"You don't have the authority");
    }
}
