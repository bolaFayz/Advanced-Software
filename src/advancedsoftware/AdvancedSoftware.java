package advancedsoftware;

import java.sql.*;
public class AdvancedSoftware {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
<<<<<<< HEAD
        MainFrame IF = new MainFrame();
        IF.setLocation(100, 100);
        IF.setVisible(true);
        IF.setResizable(false);
=======
            Connection conn = DatabaseManager.getConnection(); 
            javax.swing.JFrame frame = new javax.swing.JFrame("Projects Management");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.add(new Projects());
        frame.setVisible(true);
>>>>>>> b822f8440cfea6ce6209fa1a0c0d7922afa91b2e
    }
    
   
}
