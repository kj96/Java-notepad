package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;


public class Main extends Application {

    String user = "1";
    String pw = "2";

    Scene scene;
    Stage dialogStage = new Stage();
    Controller BcomCtrl = new Controller();
    JSONObject configObject = BcomCtrl.getConfig();

    @FXML
    private TextField textEmail;
    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textPasswordOld;
    @FXML
    private PasswordField textPasswordnew;

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("+==> Config[Password]: " + pw);

        URL url = Paths.get("src/main/java/sample/logins.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Bitcomm Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        pw = (String) configObject.get("password");
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        Scene scene;

        try {

            if(email.equals(user) && password.equals(pw)){
                infoBox("Login Successful", "Success", null);

                Node source = (Node) event.getSource();
                dialogStage = (Stage) source.getScene().getWindow();
                dialogStage.close();

                URL url2 = Paths.get("src/main/java/sample/menu.fxml").toUri().toURL();
                Parent root = FXMLLoader.load(url2);
                scene = new Scene(root);
                dialogStage.setScene(scene);
                dialogStage.show();

            }
            else{
                infoBox("Enter Correct Email and Password", "Failed", null);
            }
            textEmail.setText("");
            textPassword.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handlePasswordAction(ActionEvent event) {
        pw = (String) configObject.get("password");
        String passOld = textPasswordOld.getText().toString();
        String passNew = textPasswordnew.getText().toString();

        try{

            // System.out.println("@==> pw: " + pw);
            // System.out.println("@==> passOld: " + passOld);
            // System.out.println("@==> passNew: " + passNew);

            if(!passNew.isEmpty() && passOld.equals(pw)){
                // System.out.println("  ADMIN BIYATCH  \\m/: ");
                configObject.put("password", passNew);
                BcomCtrl.updateConfig(configObject);
            }

            Node source = (Node) event.getSource();
            dialogStage = (Stage) source.getScene().getWindow();
            dialogStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
