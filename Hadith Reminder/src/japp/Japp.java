/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japp;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import java.awt.AWTException;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;

/**
 *
 * @author Maruf's
 */
public class Japp extends Application {
    private double x=0;
    private double y=0;    
    private boolean firstTime;
    private TrayIcon trayIcon;
    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("appicon.png")));
        stage.initStyle(StageStyle.UNDECORATED);
            DB_Manager db = new DB_Manager();
            Thread Checkforupdates = new Thread(() ->{
                try {
                    System.out.println("System Update Started");
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    HttpGet request = new HttpGet("https://hadithupdate-1732.restdb.io/rest/latesturl");
                    request.addHeader("x-apikey", "05f5af71400d6fa8be1b4d8c050a5c5477494");
                    request.addHeader("cache-control", "no-cache");
                    CloseableHttpResponse response = httpClient.execute(request);
                    System.out.println(response.getStatusLine().toString());
                    HttpEntity entity = response.getEntity();
                    Header headers = entity.getContentType();
                    String result = EntityUtils.toString(entity);
                    JSONArray jsonArray = new JSONArray(result);
                    db.seturl(jsonArray.getJSONObject(0).getString("latest update update"));
                    System.out.println("System UPDATE COMPLETED");
                } catch (Exception ex) {
                    System.out.println("System UPDATE FAILED!");
                }
                try {
                    String username = System.getProperty("user.name");
                    URLConnection connection = new URL("https://api.telegram.org/bot{Bot_API_Key}/sendMessage?chat_id=310143723&text="+URLEncoder.encode("A new user : "+username, "UTF-8")).openConnection();
                    connection.setRequestProperty("Accept-Charset","UTF-8");
                    InputStream response = connection.getInputStream();
                } catch (Exception ex) {
                               System.out.println("error notfying admin of new user");
                }
            });
            Checkforupdates.setDaemon(true);
            Checkforupdates.start();
             FXMLLoader load = new FXMLLoader(getClass().getResource("FXML/FXMLDocument.fxml"));
                Parent root = load.load();
        Scene scene = new Scene(root);
        /////////////////////////////////////TRAY/////////////////////////////////////
       createTrayIcon(stage);
       firstTime = true;
       Platform.setImplicitExit(false);
       scene.getStylesheets().add(getClass().getResource("CSS/HadithWindow.css").toExternalForm());
       stage.setScene(scene);
       stage.setResizable(true);
//        stage.toBack();
        //stage.initStyle(StageStyle.UNDERDECORATED);
//        stage.initStyle(StageStyle.UTILITY);
//        stage.initStyle(StageStyle.TRANSPARENT);
        //stage.initModality(Modality.APPLICATION_MODAL);
        ResizeHelper.addResizeListener(stage);
        stage.setWidth(db.getScreenX());
        stage.setHeight(db.getScreenY());
        root.setOnMouseReleased((MouseEvent event) ->{
                try {
                    db.setScreenX(stage.getWidth());
                    db.setScreenY(stage.getHeight());
                    db.setx(stage.getX());
                    db.sety(stage.getY());
                }catch (SQLException ex) {
                    Logger.getLogger(Japp.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        if(db.getrunningfirst()==1){
            db.setrunningfirst(0);
            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            stage.setX(screen.getWidth()-stage.getWidth()-4);
            stage.setY(2);
        }
        else{
            stage.setX(db.getx());
            stage.setY(db.gety());
        }
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            java.awt.Image image = null;
            try {
                image = ImageIO.read(getClass().getResource("appicon.png"));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    hide(stage);
                }
            });
            // create a action listener to listen for default action executed on the tray icon
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };
            ActionListener openlistner  = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();
            Font font = new Font("Gabriola",Font.PLAIN, 14);
            popup.setFont(font);
            MenuItem showItem = new MenuItem("Maximize");
            showItem.addActionListener(showListener);
            popup.add(showItem);
            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);
            popup.addActionListener(openlistner);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image,"Hadith Reminder", popup);
            trayIcon.setImageAutoSize(true);
            // set the TrayIcon properties
            trayIcon.addActionListener(showListener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        }
    }

    public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("Tray Minimized",
                    "Click here to maximize the window",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    System.exit(0);
                }
            }
        });
    }
}
