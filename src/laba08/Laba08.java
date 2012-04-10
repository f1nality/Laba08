package laba08;

import java.awt.BorderLayout;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author 3D-GRAF
 */
public class Laba08 {
    public static DB db = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow.setSystemLookAndFeel();
        
        try {
            db = new DB();
            
            MainWindow mainwindow = new MainWindow();
            mainwindow.refreshAccounts();
            mainwindow.setVisible(true);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            //System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ошибка запроса");
        }
    }
}
