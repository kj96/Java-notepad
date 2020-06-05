package sample;

import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONObject;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static javax.swing.UIManager.put;
import static junit.framework.Assert.assertEquals;

public class BitcomForm implements Initializable {
    Stage dialogStage = new Stage();
    Scene scene;
    private Stage stage;

    Path appPath = FileSystems.getDefault().getPath(".").toAbsolutePath();
    String directoryName = appPath.toString().concat("Bitcomm/data");
    File dataDirectory = new File(directoryName);

    @FXML
    private TextField trainnumber;
    @FXML
    private TextField trainnumberd;
    @FXML
    private TextField numberofuphalts;
    @FXML
    private TextField numberofuphaltsd;
    @FXML
    private TextField trainnamehindi;
    @FXML
    private TextField trainnamehindid;
    @FXML
    private TextField trainname;
    @FXML
    private TextField trainnamed;
    @FXML
    private TextField number;
    @FXML
    private TextField numberd;
    @FXML
    private TextField language;
    @FXML
    private TextField languaged;
    @FXML
    private TextField stncode;
    @FXML
    private TextField stncoded;
    @FXML
    private TextField stnreg;
    @FXML
    private TextField stnregd;
    @FXML
    private TextField latitude;
    @FXML
    private TextField latituded;
    @FXML
    private TextField longitude;
    @FXML
    private TextField longituded;
    @FXML
    private TextField arrivaltime;
    @FXML
    private TextField arrivaltimed;
    @FXML
    private TextField departure;
    @FXML
    private TextField departured;

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd.HH.mm.ss");
    // private File dataFile;
    private BufferedWriter cur_buff_w;
    private int upHaltsNO;
    private int downHaltsNO;
    private String upText;
    private String downText = "                                                NO OF DOWN HALTS        *\n";

    public BufferedWriter createDataFile() {

        File dataFile = new File(dataDirectory + sdf.format(Calendar.getInstance().getTime()) + ".csv");
        try {
            FileWriter fw = new FileWriter(dataFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("        TRAIN NO        TRAIN NAME      RUNS FROM SOURCE                        NO OF UP HALTS  *\n");
            bw.write("SNO     LANGUAGE        STN CODE        STN NAME        STN NAME HINDI  STN NAME REGINOL        N       E       ARRIVAL TIME    DEP. TIME       DISTANCE      *\n");
            return bw;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cur_buff_w = createDataFile();
        System.out.print("@@BITCOMM FORMM@@ " + location.getPath());
    }

    @FXML
    public void loadBitcomData(JSONObject bitcomData){
        trainnumber.setText("qwqwqwqwqw");
    }


    private BufferedWriter addRow2fs(BufferedWriter bw, boolean isUpDown) throws IOException {
        try {
            if (isUpDown){
                upText = upText + String.valueOf(upHaltsNO) + "\t" + trainnumber.getText().toString() + "\t" + trainnumberd.getText().toString() + "\t" + numberofuphalts.getText().toString() + "\t" + numberofuphaltsd.getText().toString() + "\t" + trainname.getText().toString() + "\t" + trainnamed.getText().toString() + "\t" + number.getText().toString() + "\t" + numberd.getText().toString() + "\t" + language.getText().toString() + "\t" + languaged.getText().toString() + "\t" + stncode.getText().toString() + "\t" + stncoded.getText().toString() + "\n";
                upHaltsNO ++;
            }
            else {
                downText = downText + String.valueOf(downHaltsNO) + "\t"  + latitude.getText().toString() + "\t" + latituded.getText().toString() + "\t" + longitude.getText().toString() + "\t" + longituded.getText().toString() + "\t" + stnreg.getText().toString() + "\t" + stnregd.getText().toString() + "\t" + trainnamehindid.getText().toString() + "\t" + trainnamehindi.getText().toString() + "\t" + arrivaltime.getText().toString() + "\t" + arrivaltimed.getText().toString() + "\t" + departure.getText().toString() + "\t" + departured.getText().toString() + "\n";
                downHaltsNO++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bw;
    }

    @FXML
    private void addInfoUp(ActionEvent event){
        System.out.println("++@ addInfoUp");
        try {

            cur_buff_w = addRow2fs(cur_buff_w, true);
        }
        catch (Exception e){

        }
    }

    @FXML
    private void addInfoDown(ActionEvent event){
        System.out.println("++@ addInfoDown");
        try {

            cur_buff_w = addRow2fs(cur_buff_w, false);
        }
        catch (Exception e){

        }
    }

    @FXML
    private void saveInfo(ActionEvent event){
        try {
            cur_buff_w.write(upText);
            cur_buff_w.write(downText);
            cur_buff_w.close();
        }
        catch (Exception e){

        }
    }

    @FXML
    private void saveinfo(ActionEvent event) {
        JSONObject data_obj = new JSONObject();

        data_obj.put("trainnumber", trainnumber.getText().toString());
        data_obj.put("trainnumberd", trainnumberd.getText().toString());
        data_obj.put("numberofuphalts", numberofuphalts.getText().toString());
        data_obj.put("numberofuphaltsd", numberofuphaltsd.getText().toString());
        data_obj.put("trainname", trainname.getText().toString());
        data_obj.put("trainnamed", trainnamed.getText().toString());
        data_obj.put("number", number.getText().toString());
        data_obj.put("numberd", numberd.getText().toString());
        data_obj.put("language", language.getText().toString());
        data_obj.put("languaged", languaged.getText().toString());
        data_obj.put("stncode", stncode.getText().toString());
        data_obj.put("stncoded", stncoded.getText().toString());
        data_obj.put("latitude", latitude.getText().toString());
        data_obj.put("latituded", latituded.getText().toString());
        data_obj.put("longitude", longitude.getText().toString());
        data_obj.put("longituded", longituded.getText().toString());
        data_obj.put("stnreg", stnreg.getText().toString());
        data_obj.put("stnregd", stnregd.getText().toString());
        data_obj.put("trainnamehindid", trainnamehindid.getText().toString());
        data_obj.put("trainnamehindi", trainnamehindi.getText().toString());
        data_obj.put("arrivaltime", arrivaltime.getText().toString());
        data_obj.put("arrivaltimed", arrivaltimed.getText().toString());
        data_obj.put("departure", departure.getText().toString());
        data_obj.put("departured", departured.getText().toString());


        try {

            File dataFile = new File(dataDirectory + sdf.format(Calendar.getInstance().getTime()) + ".csv");

//            System.out.println("=> My data: " + data_obj.toJSONString() + dataFile.toString());
//            FileWriter fw = new FileWriter(dataFile.getAbsolutePath());
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(data_obj.toJSONString());
//            bw.close();

            CSVWriter csvWriter = new CSVWriter(new FileWriter(dataFile.getAbsolutePath()), '\t');
            csvWriter.writeNext(new String[]{"trainnumber","trainnumberd","numberofuphalts","numberofuphaltsd","trainname","trainnamed","number","numberd","language","languaged","stncode","stncoded","latitude","latituded","longitude","longituded","stnreg","stnregd","trainnamehindid","trainnamehindi","arrivaltime","arrivaltimed","departure","departured"});
            csvWriter.writeNext(new String[]{trainnumber.getText().toString(), trainnumberd.getText().toString(), numberofuphalts.getText().toString(), numberofuphaltsd.getText().toString(), trainname.getText().toString(), trainnamed.getText().toString(), number.getText().toString(), numberd.getText().toString(), language.getText().toString(), languaged.getText().toString(), stncode.getText().toString(), stncoded.getText().toString(), latitude.getText().toString(), latituded.getText().toString(), longitude.getText().toString(), longituded.getText().toString(), stnreg.getText().toString(), stnregd.getText().toString(), trainnamehindid.getText().toString(), trainnamehindi.getText().toString(), arrivaltime.getText().toString(), arrivaltimed.getText().toString(), departure.getText().toString(), departured.getText().toString() });

            csvWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
