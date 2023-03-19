package scene;

/***
 * @author Justin
 */
import dataDealer.IODealer;
import dataLayer.Common;
import dataLayer.Tree;
import dataLayer.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Common.currentStage = primaryStage;
        Common.currentRoot = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));//loaf fxml scene
        primaryStage.setTitle("Family Tree App");
        primaryStage.setScene(new Scene(Common.currentRoot));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Login scene closed");
            }
        });

    }

    static ArrayList<User> loadUserInfo() {
        return (ArrayList<User>)IODealer.readObjectFromFile(Common.userInfoPath);
    }
    static Tree loadFamilyTree(){
        return (Tree) IODealer.readObjectFromFile(Common.treePath);
    }


    public static void main(String[] args) {
        launch(args);
    }

    //Open scene
    public static void startGenealogyScene(){
        Stage secondStage = new Stage();
        try {
            Common.currentRoot = FXMLLoader.load(Main.class.getResource("GenealogyScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondStage.setTitle("Family Tree App");
        secondStage.setScene(new Scene(Common.currentRoot));
        secondStage.show();

        //save when close
        secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Scene closed");
                IODealer.writeObjectToFile(Common.userList,Common.userInfoPath);
                IODealer.writeObjectToFile(Common.currentTree,Common.treePath);
            }
        });
    }

    //Open scene
    public static void startEditScene() {
        Stage thirdStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("EditScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        thirdStage.setTitle("Family Tree Edit");
        thirdStage.setScene(new Scene(root));
        thirdStage.show();
    }

    //Open scene
    public static void startEditUserScene() {
        Stage fourthStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("EditUserScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fourthStage.setTitle("User Edit");
        fourthStage.setScene(new Scene(root));
        fourthStage.show();
        fourthStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("User edit scene closed");
                IODealer.writeObjectToFile(Common.userList,Common.userInfoPath);
            }
        });
    }
}
