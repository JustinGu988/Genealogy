package scene;

import dataLayer.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/***
 * @author Justin
 */
public class EditController implements Initializable {

    public Button editButton;
    public TextField name;
    public ChoiceBox gender;
    public ChoiceBox isAlive;
    public TextField birthYear;
    public TextField birthMonth;
    public TextField birthDay;
    public TextField deathYear;
    public TextField deathMonth;
    public TextField deathDay;
    public ChoiceBox marriage;
    public TextField address;
    public TextField extra;

    public TextField addParent;
    public TextField removeName;
    public Button addButton;
    public Button removeButton;
    public TextField movedName;
    public TextField moveUnderParent;
    public Button moveButton;
    public TextField nameToSearch;
    public Button searchButton;
    public TextField generation;
    public TextField father;
    public TextField mother;
    public TextField descendents;

    private Member currentMember;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gender.getItems().addAll(GenderType.values());//load gender type enum into choice box
        marriage.getItems().addAll(MarriageState.values());
        isAlive.getItems().addAll(LiveState.values());
    }

    public void addInTree(ActionEvent actionEvent) {
        String addparentString = addParent.getText();
        String nameString = name.getText();

        if (!addparentString.equals("") && !nameString.equals("") && !gender.getValue().equals("")&& Common.currentTree.find(nameString)==null){
            if (Common.currentTree.insert(addParent.getText(),
                    new Member(
                            name.getText(), (GenderType) gender.getValue(),(LiveState) isAlive.getValue(),
                            transInt(birthYear.getText()),transInt(birthMonth.getText()),transInt(birthDay.getText()),
                            transInt(deathYear.getText()),transInt(deathMonth.getText()),transInt(deathDay.getText()),
                            (MarriageState) marriage.getValue(),address.getText(),extra.getText()
                    )
            ))
                Common.sendAlert(true,"Adding Success.");
            else
                Common.sendAlert(false,"Adding Failed. Parents not found.");
        }else{
            Common.sendAlert(false,"Input Error. Requires name (unique) & gender.");
        }
    }

    public void removeFromTree(ActionEvent actionEvent) {
        Common.currentTree.remove(removeName.getText());
        Common.sendAlert(true,"Remove Success.");

    }

    public void moveInsideTree(ActionEvent actionEvent) {
        if(Common.currentTree.move2des(movedName.getText(),moveUnderParent.getText())){
            Common.sendAlert(true,"Move Success.");
        }else{
            Common.sendAlert(false,"Move Failed.");
        }
    }


    public void editPerson(ActionEvent actionEvent) {
        boolean flag = false;
        if (Common.currentTree.find(name.getText())==null || Common.currentMember.getName().equals(name.getText()))//name not found || no change
            if (gender.getValue()!=null)
                flag = true;
        if(flag) {
            Common.currentMember.setName(name.getText());
            Common.currentMember.setGender((GenderType) gender.getValue());
            if (isAlive.getValue()!=null)Common.currentMember.setIsAlive((LiveState) isAlive.getValue());
            if (transInt(birthYear.getText()) != 0 &&
                    transInt(birthMonth.getText()) != 0 &&
                    transInt(birthDay.getText()) != 0)
                Common.currentMember.setBirthday(transInt(birthYear.getText()),
                        transInt(birthMonth.getText()), transInt(birthDay.getText()));
            if (transInt(deathYear.getText()) != 0 &&
                    transInt(deathMonth.getText()) != 0 &&
                    transInt(deathDay.getText()) != 0)
                Common.currentMember.setDeathday(transInt(deathYear.getText()),
                        transInt(deathMonth.getText()), transInt(deathDay.getText()));
            if (marriage.getValue() != null) Common.currentMember.setIfMarried((MarriageState) marriage.getValue());
            if (address.getText() != "") Common.currentMember.setAddress(address.getText());
            if (extra.getText() != "") Common.currentMember.setExtraMessage(extra.getText());
            Common.sendAlert(true,"Modification Success.");
        }else{
            Common.sendAlert(false,"Please input name (unique) & gender.");
        }
    }

    private int transInt(String getText){
        try{
            return Integer.valueOf(getText);
        }catch (Exception e){
            return 0;
        }
    }


    public void search(ActionEvent actionEvent) {
        if (!nameToSearch.getText().equals("")) {
            Common.currentMember = Common.currentTree.find(nameToSearch.getText());
            freshView();
        }
    }

    private void freshView() {
        name.setText(Common.currentMember.getName());
        gender.setValue(Common.currentMember.getGender());
        isAlive.setValue(Common.currentMember.getIsAlive());
        birthYear.setText(Common.currentMember.getBirthday()[0]+"");
        birthMonth.setText(Common.currentMember.getBirthday()[1]+"");
        birthDay.setText(Common.currentMember.getBirthday()[2]+"");
        deathYear.setText(Common.currentMember.getDeathday()[0]+"");
        deathMonth.setText(Common.currentMember.getDeathday()[1]+"");
        deathDay.setText(Common.currentMember.getDeathday()[2]+"");
        marriage.setValue(Common.currentMember.getIfMarried());
        address.setText(Common.currentMember.getAddress());
        generation.setText(Common.currentMember.getGeneration()+"");
        if (Common.currentMember.getFather() != null)
            father.setText(Common.currentMember.getFather().getName());
        if (Common.currentMember.getMother() != null)
            mother.setText(Common.currentMember.getMother().getName());
        descendents.setText(Common.currentMember.getDescendentsString());
        extra.setText(Common.currentMember.getExtraMessage());
        addParent.setText("");
        removeName.setText("");
        movedName.setText("");
        moveUnderParent.setText("");
    }
}
