package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import net.sf.json.JSONSerializer;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;

public class Controller implements Initializable {

    Stage dialogStage = new Stage();
    private Stage stage;
    Scene scene;
    public final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea textArea;

    @FXML
    private ListView<String> dataListView;

    Path appPath = FileSystems.getDefault().getPath(".").toAbsolutePath();
    String directoryName = appPath.toString().concat("Bitcomm");
    File configFile = new File(directoryName + "/config.json");
    File configDirectory = new File(directoryName);
    File dataDirectory = new File(appPath.toString().concat("Bitcomm/data"));
    File[] dataFiles = dataDirectory.listFiles();

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


    public JSONObject getConfig(){
        JSONObject configObject = new JSONObject();
        System.out.println("+==> Directory Path: " + directoryName);

        if (!dataDirectory.exists()){
            dataDirectory.mkdir();
        }

        if (! configDirectory.exists()){
            System.out.print("## CREATE EVERYTHING!! ");
            configObject.put("password", "2");
            configObject.put("app_name", "Bitcomm");
            configDirectory.mkdir();
            try{
                FileWriter fw = new FileWriter(configFile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(configObject.toJSONString());
                bw.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.exit(-1);
            }
            return configObject;
        }

        try {
            File reader = new File(configFile.getAbsolutePath());

            configObject = readJsonFile(reader);

            System.out.print("## FILE EXIST!! " + configObject.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }

    return configObject;
    }

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

    public void updateConfig(JSONObject configObj){
        try{
            FileWriter fw = new FileWriter(configFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(configObj.toJSONString());
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
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
    private void viewFileMenu(ActionEvent event) {
        ListView listView = new ListView();

        for (int i = 0; i < dataFiles.length; i++) {
            if (dataFiles[i].isFile()) {
                System.out.println("File " + dataFiles[i].getName());
                listView.getItems().add(dataFiles[i].getName());
            }
        }
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());

                File configFile = new File(dataDirectory + "/" + listView.getSelectionModel().getSelectedItem());
                JSONObject dataObj = readJsonFile(configFile);

                System.out.println("==>> File data: " + dataObj);
                BitcomForm bdataform = new BitcomForm();
                try{
                    URL url2 = Paths.get("src/main/java/sample/app.fxml").toUri().toURL();
                    Parent root = FXMLLoader.load(url2);
                    scene = new Scene(root);
                    dialogStage.setScene(scene);
                    dialogStage.show();
                    bdataform.loadBitcomData(dataObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        HBox hbox = new HBox(listView);

        Scene scene = new Scene(hbox, 300, 120);
        dialogStage.setScene(scene);
        dialogStage.show();

    }

    private JSONObject readJsonFile(File file_path_reader){

        try {
            InputStream is = new FileInputStream(file_path_reader.getAbsolutePath());
            String jsonTxt = IOUtils.toString(is, "UTF-8");

            JSONParser parser = new JSONParser();
            return  (JSONObject) parser.parse(jsonTxt);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
}
