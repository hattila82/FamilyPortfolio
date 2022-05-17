/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.AktFelhasznalo;
import Controls.Felhasznalo;
import Model.DB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
public class AutentikacioPanel extends JFrame implements ActionListener {

    JLabel mainLabel = new JLabel("Családi portfólió:");
    JLabel nameLabel = new JLabel("Felhasználó:");
    JLabel passwordLabel = new JLabel("Jelszó:");
    JButton entryButton = new JButton("Belépés");
    JButton regButton = new JButton("Regisztráció");
    JButton cancelButton = new JButton("Mégsem");
    JTextField nameTextField = new JTextField(20);
    JPasswordField passwordTextField = new JPasswordField(25);
    JPanel mainPanelButtons = new JPanel();
    JPanel mainPanelText = new JPanel();
    JPanel mainPanel = new JPanel();
    DB database = new DB();
    AutentikacioMuvelet autMuv = new AutentikacioMuvelet();
    String felhasznalo;
    String password;
    int UID;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">  
    public AutentikacioPanel() {

        nameTextField.setText(null);
        passwordTextField.setText(null);
        this.setTitle("Belépés");
        this.setVisible(true);
        mainPanelButtons.setLayout(new FlowLayout());
        GridBagLayout gbl = new GridBagLayout();
        mainPanel.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;//VERTICAL, HORIZONTAL
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(nameLabel, gbc);
        gbc.gridy = 1;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        mainPanel.add(nameTextField, gbc);
        gbc.gridy = 1;
        mainPanel.add(passwordTextField, gbc);
        mainPanelButtons.add(entryButton, gbc);
        mainPanelButtons.add(regButton);
        mainPanelButtons.add(cancelButton);
        mainLabel.setFont((new Font("Arial", Font.BOLD, 40)));
        mainPanelText.add(mainLabel);
        entryButton.addActionListener(this);
        regButton.addActionListener(this);
        cancelButton.addActionListener(this);
        this.getContentPane().setPreferredSize(new Dimension(500, 300));
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(mainPanelButtons, BorderLayout.SOUTH);
        this.add(mainPanelText, BorderLayout.NORTH);
        this.pack();
        this.setLocationRelativeTo(null);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés">  
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == entryButton) {
            ArrayList<Felhasznalo> CheckFelhasznalo = null;
            CheckFelhasznalo = database.getFelhasznaloNev(nameTextField.getText());
            for (int i = 0; i < CheckFelhasznalo.size(); i++) {
                felhasznalo = CheckFelhasznalo.get(i).getNev();
                password = CheckFelhasznalo.get(i).getJelszo();
                UID = CheckFelhasznalo.get(i).getId();
            }
            String passText = new String(passwordTextField.getPassword());
            if (nameTextField.getText().equals(felhasznalo)) {
                if (BCrypt.checkpw(passText, password) == true) {
                    this.dispose();
                    database.deleteAktFelhasznalo();
                    AktFelhasznalo aktfel = new AktFelhasznalo();
                    aktfel.setAktFelID(UID);
                    database.addAktFelhasznalo(aktfel);
                    GuiMain G = new GuiMain();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ilyen felhasználónév/jelszó párosítás nem létezik!!");
                nameTextField.setText("");
                passwordTextField.setText("");
            }
        }
        if (e.getSource() == regButton) {
            autMuv.setupAdd();
        }
        if (e.getSource() == cancelButton) {
            this.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
    }
//</editor-fold>
}
