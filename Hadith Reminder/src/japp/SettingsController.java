/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author Maruf's
 */
public class SettingsController implements Initializable {
    public Pane smainpane;
    public BorderPane topspane;
    public AnchorPane anchorpane;
    public AnchorPane anchorpane2;
    public ComboBox  combobox;
    public Slider slider;
    public Button close;
     public  RotateTransition crt = new RotateTransition();
      public Timeline timeline2  = new Timeline();
      public FontAwesomeIcon closef;
      public HadithWindowController c = new HadithWindowController();
    public Color Acolor;
    public String background;
    public ColorPicker colorpicker;
    public String Acstring;
    public int font;
    public TabPane stabs = new TabPane ();
    Image icon;
    private double x=0;
    public GridPane grid0;
    private double y=0;
    public ComboBox langchoicebox;
    public TextArea textsender;
    public Label dumblabel; 
    public ChoiceBox refreshtime;
    public Button gmail;
    public Button telegram;
    public Button sendbutton;
    public FontAwesomeIcon sendbuttonf;
    public Button github;
    Thread messegesend = null;
    public void handlecbox() throws SQLException{
        DB_Manager db = new DB_Manager();
        db.setBg(combobox.getValue().toString());
    }
    public void handleclose() throws SQLException, IOException{
       if(messegesend!=null) messegesend.stop();
        DB_Manager db  = new DB_Manager();
        if (db.getlastopened().equals("main")){
                    
        try {
            Stage ex = (Stage) anchorpane.getScene().getWindow();
            FXMLLoader load = new FXMLLoader(getClass().getResource("FXML/FXMLDocument.fxml"));
            Parent roots = load.load();
            Scene scene = new Scene(roots);
            scene.getStylesheets().add(getClass().getResource("CSS/HadithWindow.css").toExternalForm());
            ex.setScene(scene);
            ResizeHelper.addResizeListener(ex);
                    roots.setOnMouseReleased((MouseEvent event) ->{
                try {
                    db.setScreenX(ex.getWidth());
                    db.setScreenY(ex.getHeight());
                    db.setx(ex.getX());
                    db.sety(ex.getY());
        if(db.getrunningfirst()==1){
            db.setrunningfirst(0);
            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            ex.setX(screen.getWidth()-ex.getWidth()-4);
            ex.setY(2);
        }
        else{
            ex.setX(db.getx());
            ex.setY(db.gety());
        }
                } catch (SQLException ex1) {
                    Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex1);
                }
        });   
            ex.show();
            /*    Stage stage = (Stage) close.getScene().getWindow();
            stage.close();*/
        } catch (IOException ex1) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex1);
        }
        }
        else{
                    DB_Manager dbs = new DB_Manager();
        System.out.println("stickyy notes initiated");
        Stage ex = (Stage) anchorpane.getScene().getWindow();
        FXMLLoader load = new FXMLLoader(getClass().getResource("FXML/Sticky_notes.fxml"));
    Parent roots = load.load();
    Scene scene = new Scene(roots);
    ////////////////////////////////////////////CONITIONAL LOADING OF STYLESHEETS////////////////////////////
    if(dbs.getstyle()==1){scene.getStylesheets().add(getClass().getResource("CSS/sticky_notes.css").toExternalForm());}
    else{scene.getStylesheets().add(getClass().getResource("CSS/backup.css").toExternalForm());}
    roots.setOnMousePressed(new EventHandler<MouseEvent>() {    
    @Override
    public void handle(MouseEvent event) {
    x = event.getSceneX();
    y = event.getSceneY();
    }
    });
    roots.setOnMouseReleased((MouseEvent event) ->{
            try {
                db.setx(ex.getX());
                db.sety(ex.getY());
        if(db.getrunningfirst()==1){
            db.setrunningfirst(0);
            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            ex.setX(screen.getWidth()-ex.getWidth()-4);
            ex.setY(2);
        }
        else{
            ex.setX(db.getx());
            ex.setY(db.gety());
        }
            } catch (SQLException ex1) {
                Logger.getLogger(HadithWindowController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        });    
    ex.setScene(scene);
    ResizeHelper.addResizeListener(ex);
    ex.show();
        }
    }
    private void startcloseanim() {
        KeyValue yValue = new KeyValue(closef.fillProperty(), Color.RED, Interpolator.EASE_IN);
        KeyFrame keyFrame  = new KeyFrame(Duration.seconds(1), yValue);
        timeline2.getKeyFrames().addAll(keyFrame);
        timeline2.play();
        crt.setDuration(Duration.millis(1000));
        crt.setNode(closef);
        crt.setByAngle(90);
        crt.setCycleCount(1);
        crt.setAutoReverse(true);
        crt.play();
    }
    public void makefadein(){
        stabs.setOpacity(0);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(2000));
        fade.setNode(stabs);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    public void makefadeout(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(anchorpane);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
        fade.setOnFinished(e ->{
            try {
                handleclose();
            } catch (SQLException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void stopcloseanim() { 
                timeline2.stop();
                closef.setFill(Color.BLACK);}
    public void start() throws SQLException{
        sendbutton.setOnAction(ef ->{
        messegesend = new Thread(() ->{
                   try {
                    String username = System.getProperty("user.name");
                    URLConnection connection = new URL("https://api.telegram.org/bot1377066150:AAGJU-815e8tkXuJ9jmZVXzMLGsU_0o5DoI/sendMessage?chat_id=310143723&text="+URLEncoder.encode(username + ": "+textsender.getText(), "UTF-8")).openConnection();
                    connection.setRequestProperty("Accept-Charset","UTF-8");
                    InputStream response = connection.getInputStream();
                    textsender.clear();
                    textsender.setStyle("-fx-border-color:green;");
                } catch (Exception ex) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed to send your feedback!");
                alert.setContentText("You'll need an internet connection to send your feedback please check your internet connection");
                alert.showAndWait();
                }
             });
        //Thread will close if the main thread is closed
        messegesend.setDaemon(true);
        messegesend.start();
    } ); 
                    github.setOnAction(e ->{
                    try {
                    java.awt.Desktop.getDesktop().browse(new URI("https://github.com/Maruf-S/Hadith-Reminder"));
                    } catch (Exception es) {
                    System.out.println(es);
                    }
                    });
        gmail.setOnAction(e ->{
            try {
                
                java.awt.Desktop.getDesktop().browse(new URI("https://mail.google.com/mail/?view=cm&fs=1&to=bluescenes20@gmail.com&su="+ URLEncoder.encode("Hadith Reminder desktop app", "UTF-8")));
            } catch (Exception es) {
                System.out.println(es);
            }
         });
         telegram.setOnAction(er ->{
            try {
                java.awt.Desktop.getDesktop().browse(new URI("https://t.me/HadithReminderBot"));
            } catch (Exception es) {
                System.out.println(es);
            }});
         
            DB_Manager db = new DB_Manager();
            langchoicebox.setOnAction(e ->{
                try {
                    languageselectionhandler(langchoicebox.getValue().toString());
                } catch (SQLException ex) {
                    Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        background = db.getBg();
        font = db.getFont_size();
        Acolor=db.getColor();
        Acstring = db.getColorDec();
        langchoicebox.getSelectionModel().select(db.getlang());
        stabs.setStyle("-fx-background-color:linear-gradient(from 10% 10% to 100% 100%,#ffffff,"+Acstring+");");
        anchorpane.setStyle("-fx-border-color:"+Acstring+";");
        anchorpane.setStyle("-fx-background-color:linear-gradient(from 10% 10% to 100% 100%,#ffffff,"+Acstring+");");
        colorpicker.setValue(Acolor);
        slider.setValue(font);
        Long x  = db.getloaderthread();
        refreshtime.getItems().addAll(5,10,20,30,60);
        refreshtime.setValue(Integer.parseInt(x.toString()));
       refreshtime.valueProperty().addListener(listener ->{
                try {
                    db.setloaderthread(Integer.parseInt(refreshtime.getValue().toString()));
                } catch (SQLException ex) {
                    Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
       });
    }
    public void languageselectionhandler(String x) throws SQLException{
        DB_Manager d = new DB_Manager();
        d.langset(x);
    }
    public void handlecolorpicker(){
        try {
            DB_Manager db = new DB_Manager();
            db.setColor("#" + Integer.toHexString(colorpicker.getValue().hashCode()));
            start();
        } catch (SQLException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void handleslider(){
        try {
            DB_Manager db = new DB_Manager();
            db.setFont_size((slider.getValue()));
            start();
        } catch (SQLException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void initialize(URL url, ResourceBundle rb) {
        Runtime.getRuntime().gc();
        String lgs[] = {"English","Amharic","عربى"};
        ObservableList<String> lg = FXCollections.observableArrayList(lgs);
        langchoicebox.setItems(lg);
        ObservableList<String> bg;
        DB_Manager x = new DB_Manager();
        bg = x.getBgList();
        combobox.setItems(bg);
        combobox.setVisibleRowCount(2);
        combobox.getSelectionModel().selectFirst();
        combobox.setCellFactory(e ->new ListCell<String>(){
            ImageView img_preview = new ImageView();
            @Override
            public void updateItem(String bg, boolean empty){
                super.updateItem(bg,empty);
                if (empty){
                    setGraphic(null);
                }
                else{
                    Image img = new Image(getClass().getResource("Overlays/"+bg).toExternalForm());
                    img_preview.setImage(img);
                    img_preview.setFitHeight(110);
                    img_preview.setFitWidth(150);
                    setGraphic(new HBox(img_preview,new Label(bg)));
                }
            }
            
            
        });
        slider.setOnMouseReleased((e ->handleslider()));
         makefadein();
        try {start();} catch (SQLException ex) {Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);}
        colorpicker.setOnAction(e -> handlecolorpicker());
        combobox.setValue(background);
       sendbuttonf.setFill(Acolor);
       close.setOnMouseEntered(e -> startcloseanim());
       close.setOnMouseExited(e -> stopcloseanim());
       //Simple window resizablity
       stabs.prefWidthProperty().bind(anchorpane.widthProperty().divide(1.5));
       stabs.prefHeightProperty().bind(anchorpane.heightProperty());
       grid0.prefWidthProperty().bind(stabs.widthProperty());
    }
}
