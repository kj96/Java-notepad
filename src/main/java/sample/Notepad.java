package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Optional;

public class Notepad implements Initializable {
    Stage dialogStage = new Stage();
    Scene scene;
    private Stage stage;
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea textArea;

    @FXML
    private void createNewFileMenu(ActionEvent event) {

        try {
            URL url2 = Paths.get("src/main/java/sample/app.fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url2);
            scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changePasswordMenu(ActionEvent event) {

        try {
            URL url2 = Paths.get("src/main/java/sample/passwordchange.fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url2);
            scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createNewFile(ActionEvent event) {

        try {
            // infoBox("NEW SOMETHING", "Success", null);
            Node source = (Node) event.getSource();
            dialogStage = (Stage) source.getScene().getWindow();
            dialogStage.close();

            URL url2 = Paths.get("src/main/java/sample/app.fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url2);
            scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.print("@@USER HOME: " + (System.getProperty("user.home")));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser
                .getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("Text", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
    }

    public void init(Stage myStage) {
        this.stage = myStage;
    }

    @FXML
    private void newFile(ActionEvent event) {
        try {
//            infoBox("NEW FILE", "Success", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openFile(ActionEvent event) {
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            textArea.clear();
            readText(file);
        }
    }
    @FXML
    private void openFileMenu(ActionEvent event) {
        fileChooser.setTitle("Open File Menu");
        File file = fileChooser.showOpenDialog(stage);

        try{
            URL url2 = Paths.get("src/main/java/sample/teditor.fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url2);
            scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (file != null) {
            textArea.clear();
            readText(file);
        }
    }
    @FXML
    private void save(ActionEvent event) {
        try {
            fileChooser.setTitle("Save As");
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PrintWriter savedText = new PrintWriter(file);
                BufferedWriter out = new BufferedWriter(savedText);
                out.write(textArea.getText());
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void exit(ActionEvent event) {
        if (textArea.getText().isEmpty()) {
            Platform.exit();
            return;
        }

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Exit without saving?",
                ButtonType.YES,
                ButtonType.NO,
                ButtonType.CANCEL
        );

        alert.setTitle("Confirm");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
        if (alert.getResult() == ButtonType.NO) {
            save(event);
            Platform.exit();
        }
    }
    // sets the textArea to the text of the opened file
    private void readText(File file) {
        String text;

        try (BufferedReader buffReader = new BufferedReader(new FileReader(file))) {
            while ((text = buffReader.readLine()) != null) {
                textArea.appendText(text + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void fontSize(ActionEvent event) {
        try {
            infoBox("NEW FILE", "Success", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("About");
        alert.setHeaderText("A project written by Raghv.");
        alert.setContentText("github.com/Raghav_satyarthi");
        alert.showAndWait();
    }
    void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
