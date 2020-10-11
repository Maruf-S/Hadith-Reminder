/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.animation.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Maruf's
 */

public class HadithWindowController implements Initializable {
    boolean splay = false;
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
    public GridPane anchorpane;
    public Timeline timeline  = new Timeline();
    public Timeline timeline1  = new Timeline();
    public Timeline timeline2  = new Timeline();
    public Timeline bodytimeline = new Timeline();
    public Timeline baodytimeline = new Timeline();
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
    Tooltip ral = new Tooltip();
    public Button stickl;
    public Color Acolor;
    public String Acstring;
    public String CBackground;
    public Button lang;
    public FontAwesomeIcon langf;
    //public BorderPane borderpane;
    public GridPane border;
    public TextArea body;
    Thread ss;
    public Button sound;
    public FontAwesomeIcon soundf;
    int index;
    Image image1;
    
    Iterator<MediaPlayer> google$$$;
    final MediaView view = new MediaView();
    MediaPlayer linef = null;
    BackgroundSize bSize;
    @FXML    
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
                Logger.getLogger(HadithWindowController.class.getName()).log(Level.SEVERE, null, ex);
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
    public void start(){
            try {
            DB_Manager db = new DB_Manager();
            stick.setFill(db.getColor());
            refreshf.setFill(db.getColor());
           Tooltip tt = new Tooltip();
           Tooltip tr = new Tooltip();
           ral.setText("Read aloud this hadith");
           ral.setStyle("-fx-font: normal 19 'gabriola';"
    + "-fx-text-fill:"+db.getColorDec()+";");
           sound.setTooltip(ral);
           tr.setText("Load a new hadith");
           tr.setStyle("-fx-font: normal 19 'gabriola';"
    + "-fx-text-fill:"+db.getColorDec()+";");
           refresh.setTooltip(tr);
        tt.setText("Open the Stickynotes window");
        if (db.getlang().equals("English")){
        lt.setText("Translate the hadith to "+"Arabic");
        }
        else{
        lt.setText("Translate the hadith to "+"English");
        }
        lt.setStyle("-fx-font: normal 19 'gabriola';"
    + "-fx-text-fill:"+db.getColorDec()+";");
        lang.setTooltip(lt);
            index = db.random;
            if(db.getlang().equals("Amharic")){
                lang.setDisable(true);
                sound.setDisable(true);
            }
            else{
                langf.setFill(db.getColor());
            }
            tt.setStyle("-fx-font: normal 19 'gabriola';" +"-fx-text-fill:"+db.getColorDec()+";");
            stickl.setTooltip(tt);
            if(db.getlang().equals("Amharic")){
                try{
                body.setFont(Font.loadFont(getClass().getResource("Dire_Dawa.ttf").toExternalForm(),db.getFont_size()+6));}
                catch(Exception e){
                }
        }
            else{
        body.setFont(Font.font("Segoe Print", FontWeight.NORMAL, db.getFont_size()));}
            body.setText(db.returnhadith());
            Acolor=db.getColor();
            Acstring = db.getColorDec();
            CBackground = db.getBg();}
        catch (SQLException ex) {
        }
       border.prefWidthProperty().bind(anchorpane.widthProperty());
       border.prefHeightProperty().bind(anchorpane.heightProperty());
       body.prefHeightProperty().bind(border.heightProperty());
       body.prefWidthProperty().bind(border.widthProperty());
       top_c.prefWidthProperty().bind(anchorpane.widthProperty());
       //////////////////////////////////////////////WORKSHOP////////////////////////////////////////////////////////////
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
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
        }
    public void minimize(){
    
    Stage stage = (Stage) close.getScene().getWindow();
    stage.setIconified(true);
//    stage.close();
    }
    public void setanchorpanestyle(String x){
        anchorpane.setStyle(x);
    }
    public void opensettings() throws IOException, SQLException {
                splay = false;//audio
        if(linef!=null)linef.stop();
        ss.stop();
        DB_Manager dbs = new DB_Manager();
        dbs.setlastopend("main");
        Stage ex = (Stage) anchorpane.getScene().getWindow();
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
                DB_Manager db = new DB_Manager();
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
    public void openstickynotes() throws IOException, SQLException{
        splay=false;//audio
        if(linef!=null)linef.stop();
        ss.stop();
        DB_Manager dbs = new DB_Manager();
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
                DB_Manager db = new DB_Manager();
                /*                db.setScreenX(ex.getWidth());
                db.setScreenY(ex.getHeight());*/
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
    public void lanentered(){
    ttrs.setNode(langf);
    ttrs.setByAngle(180);
    ttrs.setAxis(Rotate.Y_AXIS);
    ttrs.setDuration(Duration.seconds(1));
    ttrs.play();
    langf.setScaleX(1.4);
    langf.setScaleY(1.4);
    }
    public void lanexited(){
    langf.setRotate(0);
    langf.setScaleX(1);
    langf.setScaleY(1);
    }
    public void langhandler() throws SQLException{
      fades.stop();
      fades.setDuration(Duration.millis(1000));
        fades.setAutoReverse(false);
        fades.setCycleCount(1);
        fades.setNode(body);
        fades.setFromValue(1);
        fades.setToValue(0);
        fades.play();
        fades.stop();
      DB_Manager dbl = new DB_Manager();
        if (dbl.getlang().equals("English")){
                    body.setText(dbl.englishtoarabic(index));
                    dbl.langset("عربى");
                }
        else{
                    body.setText(dbl.arabictoenglish(index));
                    dbl.langset("English");
                }
       if (dbl.getlang().equals("English")){
        lt.setText("Translate the hadith to "+"Arabic");
        }
        else{
        lt.setText("Translate the hadith to "+"English");
        }
        fades.setDuration(Duration.millis(1000));
        fades.setAutoReverse(false);
        fades.setCycleCount(1);
        fades.setNode(body);
        fades.setFromValue(0);
        fades.setToValue(1);
        fades.play();
    }
    public void newhadith(){
        splay = false;//audio
        if(linef!=null)linef.stop();
        ref.stop();
        ref.setDuration(Duration.millis(1000));
        ref.setAutoReverse(false);
        ref.setCycleCount(1);
        ref.setNode(body);
        ref.setFromValue(1);
        ref.setToValue(0);
        ref.play();
        ref.stop();
    start();
        ref.setDuration(Duration.millis(1000));
       ref.setAutoReverse(false);
        ref.setCycleCount(1);
        ref.setNode(body);
        ref.setFromValue(0);
        ref.setToValue(1);
        ref.play();
        
    }
    public void refreshme(){
    refreshf.setScaleX(1.4);
    refreshf.setScaleY(1.4);
    rtf.stop();
    rtf.setNode(refreshf);
    rtf.setCycleCount(1);
    rtf.setDuration(Duration.seconds(0.5));
    rtf.setAutoReverse(true);
    rtf.setByAngle(360);
    rtf.play();
    }
    public void refreshmex(){
        refreshf.setScaleX(1);
       refreshf.setScaleY(1);
    }
   void readaloud(){
        splay = !splay;
        ArrayList formattedstrings = new ArrayList();
        String readable = body.getText();
        readable = readable.replace("`", "");
        String[] chunks = readable.split(" ");
        String temp = "";
        for(int i = 0;i<chunks.length;i++){
            temp+=(chunks[i]+" ");
            if(temp.length()>150){
                formattedstrings.add(temp);
                System.out.println(temp);
                temp="";
            }
        }
        formattedstrings.add(temp);
        Iterator it = formattedstrings.iterator();
        MediaPlayer mediaPlayer = null;
        ArrayList playlist = new ArrayList();
        int count = 0;
        /*
        Uses googles free Read aloud feauture on thair website to read aloud text, but since there is 100 character limit
        the text will be translated 100 char at a time, then saved to an array list which then plays it one at a time from an array list
        */
        DB_Manager db = new DB_Manager();
        do{
            try {
                String lan;
                String encoded = URLEncoder.encode(it.next().toString(), "UTF-8");
                if(db.getlang().equals("English")){
                    lan = "=en";
                }
                else{
                    lan = "=ar";
                }
                URL url = new URL((db.geturl().replace("=en",lan))+encoded);
                System.out.println(url);
                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                InputStream is = conn.getInputStream();
                File f = File.createTempFile("Hadithload",".tmp");
                OutputStream outstream = new FileOutputStream(f);
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    outstream.write(buffer, 0, len);
                }
                outstream.close();
                System.out.println(f.toURI().toURL().toString());
                Media media = new Media(f.toURI().toURL().toString());
                playlist.add(new MediaPlayer(media));
                count++;
                is.close();
                System.out.println(f.getAbsoluteFile().delete());
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Network Error");
                alert.setHeaderText("Error Connecting to the internet:");
                alert.setContentText("You'll need an internet connection to use this feauture please.");
                alert.showAndWait();
                return;
            }
        }while(it.hasNext());
        google$$$ = playlist.iterator();
        Gplay(google$$$.next());
        return;
    }
    void Gplay(MediaPlayer line){
               linef = line;
               view.setMediaPlayer(line);
               linef.play();
                linef.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                linef.stop();
                if (google$$$.hasNext() && splay) {
                    //Plays the subsequent files
                    Gplay(google$$$.next());
                }
                System.out.println("finished playing");
                return;
            }
        });
    }
    public void bodyanimation(){
       Background bg = new Background(new BackgroundImage(image1,
       BackgroundRepeat.NO_REPEAT,
       BackgroundRepeat.NO_REPEAT,
       BackgroundPosition.CENTER,
       bSize));
        baodytimeline.stop();
        bodytimeline.stop();
        KeyValue yValue = new KeyValue(anchorpane.backgroundProperty(),bg, Interpolator.LINEAR);
        KeyFrame keyFrame  = new KeyFrame(Duration.seconds(4), yValue);
        baodytimeline.getKeyFrames().addAll(keyFrame);
        baodytimeline.play();
        baodytimeline.setOnFinished(e ->{
        border.setBackground(Background.EMPTY);
        });
    }
        public void borderanimation(){
               bodytimeline.stop();
               baodytimeline.stop();
       Background bg = new Background(new BackgroundImage(image1,
       BackgroundRepeat.NO_REPEAT,
       BackgroundRepeat.NO_REPEAT,
       BackgroundPosition.CENTER,
       bSize));
//       border.setBackground(bg);
        KeyValue yValue = new KeyValue(border.backgroundProperty(),bg, Interpolator.LINEAR);
        KeyFrame keyFrame  = new KeyFrame(Duration.seconds(0.2), yValue);
        bodytimeline.getKeyFrames().addAll(keyFrame);
        bodytimeline.setOnFinished(e ->{
            anchorpane.setBackground(Background.EMPTY);
        });
        bodytimeline.play();
    }
    public void initialize(URL url, ResourceBundle rb) {
        Runtime.getRuntime().gc();
        anchorpane.setOpacity(0);
        stickl.setOnMouseEntered(e -> stickymouse());
        stickl.setOnMouseExited(e -> stickymousee());
        lang.setOnMouseEntered(e ->lanentered());
        lang.setOnMouseExited(e ->lanexited());
        start();
       image1 = new Image(getClass().getResource("Overlays/"+CBackground).toExternalForm());
    bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
       lang.setOnAction(e -> {
           splay=false;//audio
           if(linef!=null)linef.stop();
            try {
                langhandler();
            } catch (SQLException ex) {
                Logger.getLogger(HadithWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       stickl.setOnAction(e ->{try {openstickynotes();} catch (IOException ex) {Logger.getLogger(HadithWindowController.class.getName()).log(Level.SEVERE, null, ex);} catch (SQLException ex) {
                Logger.getLogger(HadithWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       refresh.setOnMouseEntered(e -> refreshme());
       refresh.setOnMouseExited(e ->refreshmex());
       refresh.setOnAction(e -> newhadith());
       //anchorpane.setStyle("-fx-border-color:"+Acstring+";");
       settings.setOnMouseEntered(e -> settinganimss());
       settings.setOnMouseExited(e -> stopsettinganimss());
       close.setOnMouseExited(e -> stopcloseanim());
       min.setOnMouseEntered(e -> startminanim());
       close.setOnMouseEntered(e -> startcloseanim());
       min.setOnMouseExited(e -> stopminanim());
       sound.setOnAction(e ->readaloud());
      // anchorpane.setStyle("-fx-border-color:"+Acstring+";");
       top_c.setStyle("-fx-border-color:"+Acstring+";"
               + "-fx-border-width: 1px 1px 0px 1px;");
       anchorpane.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                       borderanimation();
                       System.out.println(newValue);
            } else {
                bodyanimation();
                System.out.println(newValue);
            }
        });
       anchorpane.setBackground(new Background(new BackgroundImage(image1,
       BackgroundRepeat.NO_REPEAT,
       BackgroundRepeat.NO_REPEAT,
       BackgroundPosition.CENTER,
       bSize)));
       makefadein();
       soundf.setFill(Acolor);
       //out(bg);
       //Load a new text on the background on a specfied interval
               ss = new Thread(() ->{
                   DB_Manager db = new DB_Manager();
                   int count = 0;
                   while(true){
                       try {TimeUnit.MINUTES.sleep(db.getloaderthread());} catch (Exception ex) {Logger.getLogger(HadithWindowController.class.getName()).log(Level.SEVERE, null, ex);}
                       System.out.println("THREAD RUNNIN"+(++count));
                       refreshme();
                       refreshmex();
                       newhadith();
                   }
        });
        ss.setDaemon(true);
        ss.start();
    }  
}