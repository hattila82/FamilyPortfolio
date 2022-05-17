/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Felhasznalo;
import Model.DB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HorvathAttila AutentikacioMuvelet osztály létrehozása, panel
 * felépítése, eseménykezelés
 */
public class AutentikacioMuvelet extends JFrame implements ActionListener {

    JFrame mainFrameAdd = new JFrame();
    JPanel panelButtons = new JPanel();
    JPanel panelAdd = new JPanel();
    JLabel userNameLabel = new JLabel("Felhasználó név:");
    JLabel passwordLabel = new JLabel("Jelszó:");
    JLabel passwordLabel2 = new JLabel("Jelszó mégegyszer:");
    JButton saveButton = new JButton("ment");
    JButton saveCancel = new JButton("mégsem");
    JTextField userTextField = new JTextField(20);
    JPasswordField passwordTextField = new JPasswordField(25);
    JPasswordField passwordTextField2 = new JPasswordField(25);
    Felhasznalo Felhasznalo = new Felhasznalo();
    DB dababase = new DB();
    String felhasznalo;

    //<editor-fold defaultstate="collapsed" desc="Panel felépítése">      
    public void setupAdd() {
        userTextField.setText(null);
        passwordTextField.setText(null);
        passwordTextField2.setText(null);
        mainFrameAdd.setTitle("Regisztráció");
        mainFrameAdd.setVisible(true);
        panelButtons.setLayout(new FlowLayout());
        GridBagLayout gbl = new GridBagLayout();
        panelAdd.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;//VERTICAL, HORIZONTAL
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelAdd.add(userNameLabel, gbc);
        gbc.gridy = 1;
        panelAdd.add(passwordLabel, gbc);
        gbc.gridy = 2;
        panelAdd.add(passwordLabel2, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        panelAdd.add(userTextField, gbc);
        gbc.gridy = 1;
        panelAdd.add(passwordTextField, gbc);
        gbc.gridy = 2;
        panelAdd.add(passwordTextField2, gbc);
        panelButtons.add(saveButton);
        panelButtons.add(saveCancel);
        saveButton.addActionListener(this);
        saveCancel.addActionListener(this);
        panelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új regisztráció"));
        mainFrameAdd.getContentPane().setPreferredSize(new Dimension(400, 150));
        mainFrameAdd.add(panelAdd, BorderLayout.CENTER);
        mainFrameAdd.add(panelButtons, BorderLayout.SOUTH);
        mainFrameAdd.pack();
        mainFrameAdd.setLocationRelativeTo(null);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés">  
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            Felhasznalo.setNev(userTextField.getText());
            String salt = BCrypt.gensalt();
            String hash = BCrypt.hashpw(String.valueOf(passwordTextField.getPassword()), salt);
            Felhasznalo.setJelszo(hash);
            ArrayList<Felhasznalo> CheckFelhasznalo = null;
            CheckFelhasznalo = dababase.getFelhasznaloNev(userTextField.getText());
            for (int i = 0; i < CheckFelhasznalo.size(); i++) {
                felhasznalo = CheckFelhasznalo.get(i).getNev();
            }
            if (userTextField.getText().equals(felhasznalo)) {
                JOptionPane.showMessageDialog(mainFrameAdd, "Ilyen névvel már létezik felhasználó!!");
            } else if (passwordTextField.getPassword().length <= 5 || userTextField.getText().length() <= 5) {
                JOptionPane.showMessageDialog(mainFrameAdd, "A jelszónak és a felhasználói névnek legalább 6 karakternek kell lenni!");
            } else {
                if (Arrays.equals(passwordTextField.getPassword(), passwordTextField2.getPassword()) == false) {
                    JOptionPane.showMessageDialog(mainFrameAdd, "A jelszavaknak meg kell egyezniük!");
                } else {
                    dababase.addFelhasznalo(Felhasznalo);
                    mainFrameAdd.dispose();
                }
            }
        }
        if (e.getSource() == saveCancel) {
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
    }
    //</editor-fold>
}
