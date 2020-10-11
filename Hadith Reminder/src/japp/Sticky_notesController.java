/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author Maruf's
 */
public class Sticky_notesController implements Initializable {
    
    double x =0;
    double y=0;
    public boolean was_open= false;
    public Label label;
    public Button refresh;
    public FontAwesomeIcon refreshf;
    public Button B0;
    public Button settings;
    public FontAwesomeIcon settingsf;
     public FontAwesomeIcon minf;
     public FontAwesomeIcon stick;
    public Button close;
    public FontAwesomeIcon closef;
    public GridPane top_c;
    public Button min;
    public AnchorPane anchorpane;
    public Timeline timeline  = new Timeline();
    public Timeline timeline1  = new Timeline();
    public Timeline timeline2  = new Timeline();
     public  RotateTransition rtf = new RotateTransition();
    public  RotateTransition rt = new RotateTransition();
    public  RotateTransition mrt = new RotateTransition();
    public RotateTransition ttrs = new RotateTransition();
    public  ScaleTransition rs = new ScaleTransition();
    public  RotateTransition crt = new RotateTransition();
    public RotateTransition srt = new RotateTransition();
    public  ScaleTransition cst = new ScaleTransition();
    public  ScaleTransition mmt = new ScaleTransition();
    public  FadeTransition fades = new FadeTransition();
    public  FadeTransition ref = new FadeTransition();
    Tooltip lt = new Tooltip();
    public Button stickl;
    Stage primaryStage = new Stage();
    private SettingsController childController;
    public Color Acolor;
    public String Acstring;
    public String Background;
    public Button lang;
    public FontAwesomeIcon langf;
    //public BorderPane borderpane;
    public GridPane border;
    public TextArea body;
    int index;
    public GridPane suptop;
    public FontAwesomeIcon stylecf;
    DB_Manager db;
    @FXML    
    public void openmainwindow(){
                try {
            db.updatenotes(body.getText());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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
                    DB_Manager db = new DB_Manager();
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
        } catch (IOException ex1) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex1);
        }    
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
                opensettings();
            } catch (IOException ex) {
                Logger.getLogger(HadithWindowController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Sticky_notesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    public void makefadein(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(2000));
        fade.setNode(anchorpane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    public void settinganimss(){
        KeyValue yValue = new KeyValue(settingsf.fillProperty(),Acolor, Interpolator.EASE_IN);
        KeyFrame keyFrame  = new KeyFrame(Duration.seconds(1.6), yValue);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();
        rt.stop();
        rs.stop();
        rs.setDuration(Duration.millis(1200));
        rs.setNode(settingsf);
        rs.setToY(1.5);
        rs.setToX(1.5);
        rs.play();
        rt.setDuration(Duration.millis(10000));
        rt.setNode(settingsf);
        rt.setByAngle(4320);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.play();
    }
    public void stopsettinganimss(){
        rt.stop();
        rs.stop();
        timeline.stop();
        KeyValue yValue = new KeyValue(settingsf.fillProperty(), Color.BLACK, Interpolator.EASE_IN);
        KeyFrame keyFrame  = new KeyFrame(Duration.seconds(1), yValue);
        timeline1.getKeyFrames().addAll(keyFrame);
        timeline1.play();
        rt.setDuration(Duration.millis(1000));
        rt.setNode(settingsf);
        rt.setByAngle(180);
        rt.setCycleCount(1);
        rt.play();
        rs.setDuration(Duration.millis(1000));
        rt.setCycleCount(1);
        rs.setNode(settingsf);
        rs.setToY(1);
        rs.setToX(1);
        rs.play();
}
        public void startcloseanim() {
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
        
    public void start() throws SQLException{
        DB_Manager db = new DB_Manager();
        Acolor=db.getColor();
        Acstring = db.getColorDec();
        body.setText(db.getnotes());
        body.setFont(Font.font("Segoe Print", FontWeight.SEMI_BOLD, db.getFont_size()));
        Tooltip tt = new Tooltip("Go back to the Main window");tt.setStyle("-fx-font: normal 19 'gabriola';"+ "-fx-text-fill:"+Acstring+";");
            stickl.setTooltip(tt);
       stickl.setOnMouseEntered(e -> stickymouse());
       stickl.setOnMouseExited(e -> stickymousee());
       border.prefWidthProperty().bind(anchorpane.widthProperty());
       border.prefHeightProperty().bind(anchorpane.heightProperty());
       body.prefHeightProperty().bind(border.heightProperty());
       body.prefWidthProperty().bind(border.widthProperty());
       top_c.prefWidthProperty().bind(anchorpane.widthProperty());
    }
        private void stickymouse(){
        srt.stop();
        srt.setNode(stick);
        stick.setScaleX(1.1);
        stick.setScaleY(1.1);
        srt.setCycleCount(10);
        srt.setDuration(Duration.seconds(0.5));
        srt.setAutoReverse(true);
        srt.setByAngle(45);
        srt.play();
    }
    private void stickymousee(){
        stick.setRotate(0);
        stick.setScaleX(1);
        stick.setScaleY(1);
        srt.stop();
    }
    
    private void startminanim() {
        rs.stop();
        rs.setDuration(Duration.millis(500));
        rt.setCycleCount(1);
        rs.setNode(minf);
        rs.setToY(1.1);
        rs.setToX(1.4);
        rs.play();
    }
        private void stopminanim() {
        rs.stop();
        rs.setToY(1);
        rs.setToX(1);
    }
            private void stopcloseanim() { 
                timeline2.stop();
                closef.setFill(Color.BLACK);
    }
        public void closebuttohandler(){
        try {
            db.updatenotes(body.getText());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
        }
    public void minimize(){
            try {
            db.updatenotes(body.getText());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    Stage stage = (Stage) close.getScene().getWindow();
    stage.setIconified(true);
    //stage.close();
    }
    public void opensettings() throws IOException, SQLException {
                try {
            db.updatenotes(body.getText());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                db.setlastopend("stickynotes");
        Stage ex = (Stage) anchorpane.getScene().getWindow();
        ResizeHelper.addResizeListener(ex);
        FXMLLoader load = new FXMLLoader(getClass().getResource("FXML/Settings.fxml"));
    Parent roots = load.load();
    Scene scene = new Scene(roots);
    scene.getStylesheets().add(getClass().getResource("CSS/settings.css").toExternalForm());
    roots.setOnMousePressed((MouseEvent event) -> {
        x = event.getSceneX();
        y = event.getSceneY();
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
    roots.setOnMouseDragged((MouseEvent event) -> {
    ex.setX(event.getScreenX() - x);
    ex.setY(event.getScreenY() - y);
    });
    ex.setScene(scene);
    ex.show();
}
    public void openstickynotes() throws IOException{
        System.out.println("oppening THE APP");
    }
    public void changestyle() throws SQLException{
            if(db.getstyle()==1){
    anchorpane.getScene().getStylesheets().remove((getClass().getResource("CSS/sticky_notes.css").toExternalForm()));
    anchorpane.getScene().getStylesheets().add(getClass().getResource("CSS/backup.css").toExternalForm());  
    db.setstyle(0);
        }else{
     anchorpane.getScene().getStylesheets().remove((getClass().getResource("CSS/backup.css").toExternalForm()));
    anchorpane.getScene().getStylesheets().add(getClass().getResource("CSS/sticky_notes.css").toExternalForm());
    db.setstyle(1);
            }
    }
    public void initialize(URL url, ResourceBundle rb) {
        Runtime.getRuntime().gc();
        db = new DB_Manager(); 
        anchorpane.setOpacity(0);
        try {
            start();
        } catch (SQLException ex) {
            Logger.getLogger(Sticky_notesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stick.setFill(Acolor);
        stylecf.setFill(Acolor);
       anchorpane.setStyle("-fx-background-color:"+"WHITE"+";"+"-fx-border-color:"+Acstring+";");
      border.setStyle("-fx-border-color:"+Acstring+";");
       settings.setOnMouseEntered(e -> settinganimss());
       settings.setOnMouseExited(e -> stopsettinganimss());
       close.setOnMouseEntered(e -> startcloseanim());
       close.setOnMouseExited(e -> stopcloseanim());
       min.setOnMouseEntered(e -> startminanim());
       min.setOnMouseExited(e -> stopminanim());
       makefadein();
    }     
 }