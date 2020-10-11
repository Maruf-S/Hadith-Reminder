package japp;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
/**
 *
 * @author Maruf's
 *
 */
//<Manage the SQLLite database>
public class DB_Manager {
    private Connection conn;
    private Connection conn1;
    private Connection conn2;
    public Integer random;
    private Connection conn3;
    public DB_Manager() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn1 = DriverManager.getConnection("jdbc:sqlite::resource:hadith_en_ar.db");
            conn = DriverManager.getConnection("jdbc:sqlite::resource:preferences.db");
            conn2 = DriverManager.getConnection("jdbc:sqlite::resource:hadith_am.db");
            conn3 = DriverManager.getConnection("jdbc:sqlite::resource:sticky_notes.db");
            random = (int )(Math.random() * 15000+ 1);
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS properties (color text ,font Intger,background text,screenX Intger,screenY intger,firsttime boolean,currentlang text,x double,y double)");
            statement.close();
            Statement statement1 = conn3.createStatement();
            statement1.execute("CREATE TABLE IF NOT EXISTS data (dat text)");
            statement1.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        public void setstyle(int x) throws SQLException{
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET style='"+x+"'");
        statement.close();
        }
    public int getstyle() throws SQLException{
        Statement statement  = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        int c = result.getInt("style");
        statement.close();
        return c;
    }
    public String getnotes(){
        String c = "";
        try {
            Statement statement  = conn3.createStatement();
                statement.executeUpdate("VACUUM");
            statement.execute("SELECT * FROM data");
            ResultSet result = statement.getResultSet();
            c = result.getString("dat");
            statement.close();
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
                    return c;
    }
    public void updatenotes(String notes) throws SQLException{
        notes = notes.replace("'","''");
    Statement statement  = conn3.createStatement();   
    statement.execute("UPDATE data SET dat='"+notes+"'");
    statement.close();
    }
    
    private String getrandomenglish() throws SQLException{
        Statement statement  = conn1.createStatement();
        System.out.println(random);
        statement.execute("SELECT narrator_en || '\n' || hadith_en ||'\n'|| hadith_reference_en AS en FROM hadithdb WHERE ROWID ="+random);
        ResultSet result = statement.getResultSet();
        String c = result.getString("en");
        statement.close();
        return c;
    }
        public void setloaderthread(int x) throws SQLException{
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET loaderthread='"+x+"'");
        statement.close();
        }
    public long getloaderthread() throws SQLException{
        Statement statement  = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        long c = result.getLong("loaderthread");
        statement.close();
        return c;
    }
    
  public void seturl(String x) throws SQLException{
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET latesturl='"+x+"'");
        statement.close();
        }
    public String geturl() throws SQLException{
        Statement statement  = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        String c = result.getString("latesturl");
        statement.close();
        return c;
    }
    
    
    public void langset(String x) throws SQLException{
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET currentlang='"+x+"'");
        statement.close();
        }
    public String getlang() throws SQLException{
        Statement statement  = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        String c = result.getString("currentlang");
        statement.close();
        return c;
    } 
    public void setlastopend(String x) throws SQLException{
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET lastopened='"+x+"'");
        statement.close();
        }
    public String getlastopened() throws SQLException{
        Statement statement  = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        String c = result.getString("lastopened");
        statement.close();
        return c;
    }    
    public String returnhadith() throws SQLException{
        Statement statement  = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        String c = result.getString("currentlang");
        statement.close();
        if(c.equals("English")){
            return getrandomenglish();
        }
        else if (c.equals("عربى")){
            return getrandomarabic();
        }
        else{
            return getrandomamharic();
        }
    }
    private String getrandomarabic() throws SQLException{
        Statement statement  = conn1.createStatement();
        statement.execute("SELECT narrator_ar ||'\n'|| c10text_ar ||'\n'|| c11narrator_arend AS ar FROM hadithdb WHERE ROWID="+random);
        //AS hadith_en
        ResultSet result = statement.getResultSet();
        String c = result.getString("ar");
        statement.close();
        return c;
    }
    public String arabictoenglish(int ss) throws SQLException{
        Statement statement  = conn1.createStatement();
        System.out.println(random);
        statement.execute("SELECT narrator_en || '\n' || hadith_en ||'\n'|| hadith_reference_en AS en FROM hadithdb WHERE ROWID ="+ss);
        //AS hadith_en
        ResultSet result = statement.getResultSet();
        String c = result.getString("en");
        statement.close();
        return c;
    }
    public String englishtoarabic(int ss) throws SQLException{
        Statement statement  = conn1.createStatement();
        statement.execute("SELECT narrator_ar ||'\n'|| c10text_ar ||'\n'|| c11narrator_arend AS ar FROM hadithdb WHERE ROWID="+ss);
        ResultSet result = statement.getResultSet();
        String c = result.getString("ar");
        statement.close();
        return c;
    }
    public String getrandomamharic() throws SQLException{
        Statement statement  = conn2.createStatement();
        int r = (int)(Math.random()*407+1);
        System.out.println(r);
        statement.execute("SELECT field2 FROM hadith_am WHERE ROWID="+r);
        //AS hadith_en
        ResultSet result = statement.getResultSet();
        String c = result.getString("field2");
        statement.close();
        return c;
    }
    public int getrunningfirst() throws SQLException{
        Statement statement  = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        int c = result.getInt("firsttime");
        statement.close();
        return c;
    }
    public void setrunningfirst(int x) throws SQLException{
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET firsttime='"+x+"'");
        statement.close();
    }
    
    
    public double getx() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        double c = result.getDouble("x");
        statement.close();
        return c;
    }
    
    public void setx(double x) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET x='"+x+"'");
        statement.close();
    }
    public double gety() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        double c = result.getDouble("y");
        statement.close();
        return c;
    }
    
    public void sety(double y) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET y='"+y+"'");
        statement.close();
    }
    public double getScreenX() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        double c = result.getDouble("screenX");
        statement.close();
        return c;
    }
    
    public void setScreenX(double ScreenX) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET screenX='"+ScreenX+"'");
        statement.close();
    }

    public double getScreenY() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        double c = result.getDouble("screenY");
        statement.close();
        return c;
    }

    public void setScreenY(double ScreenY) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET screenY='"+ScreenY+"'");
        statement.close();
    }

    public ObservableList getBgList(){
        ObservableList<String> bg = FXCollections.observableArrayList("Background (0).jpg","Background (1).jpg","Background (2).jpg","Background (3).jpg","Background (4).jpg",
                "Background (5).jpg","Background (6).jpg","Background (7).jpg","Background (8).jpg","Background (9).jpg","Background (10).jpg");
        return bg;
    }

    public String getBg() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        String c = result.getString("background");
        statement.close();
        return c;
    }
     public void setBg(String bg) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET background='"+bg+"'");
        statement.close();
    }
    public Color getColor() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        Color c = Color.web(result.getString("color"),1);
        result.close();
        statement.close();
        return c;
    }
    public String getColorDec() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        String c = result.getString("color");
        statement.close();
        return c;
    }
    public void setColor(String color) throws SQLException {
        Statement statement = conn.createStatement();
        if(color.equals("#ff")){
        statement.execute("UPDATE properties SET color ='"+"BLACK"+"'");
        }
        else{statement.execute("UPDATE properties SET color ='"+color+"'");
        }
        statement.close();
    }
    public int getFont_size() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM properties");
        ResultSet result = statement.getResultSet();
        int c = result.getInt("font");
        statement.close();
        return c;
    }

    public void setFont_size(double font_size) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("UPDATE properties SET font='"+font_size+"'");
        statement.close();
    }}
