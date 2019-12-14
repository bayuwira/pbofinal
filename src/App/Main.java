package App;

import Model.Teller;
import View.LoginView;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Main {
    static Teller teller;
    
    public static void main(String[] args){
        teller = new Teller();
        
        LoginView loginView = new LoginView();
        loginView.setTeller(teller);
        loginView.run(loginView);
        loginView.getBtnLogin().addActionListener((ActionEvent e) -> {
            String username = loginView.getTxtUsername().getText();
            String password = new String(loginView.getTxtPassword().getPassword());
        
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "SELECT id, nama, username FROM teller WHERE username = ? AND password = md5(?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, username);
                p.setString(2, password);

                ResultSet r = p.executeQuery();
                if(r.first()== false){
                    JOptionPane.showMessageDialog(null, "Username/Password salah");
                    throw new SQLException("Error");
                }
                teller.setId(r.getString("id"));
                teller.setUsername(r.getString("username"));
                teller.setNama(r.getString("nama"));

                JOptionPane.showMessageDialog(null, "Berhasil");

            }catch(SQLException err){
                System.out.println(err);
            }        
        });
    }
}
