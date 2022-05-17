/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.AktFelhasznalo;
import Controls.Szemely;
import Model.DB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author HorvathAttila SzemelyMuvelet tábla műveletek és felhasználói
 * felületek
 */
public class SzemelyMuvelet extends JFrame implements ActionListener {

    JFrame mainFrameAdd = new JFrame();
    JFrame mainFrameMod = new JFrame();
    JPanel mainPanelAdd = new JPanel();
    JPanel mainPanelAddButtons = new JPanel();
    JPanel mainPanelModButtons = new JPanel();
    JPanel mainPanelMod = new JPanel();
    JLabel nameLabelAdd = new JLabel("Név:");
    JLabel nameLabelMod = new JLabel("Név:");
    JLabel commentLabelAdd = new JLabel("Megjegyzés:");
    JLabel commentLabelMod = new JLabel("Megjegyzés:");
    JButton saveButtonAdd = new JButton("ment");
    JButton saveButtonMod = new JButton("ment");
    JButton saveCancelAdd = new JButton("mégsem");
    JButton saveCancelMod = new JButton("mégsem");
    JTextField nameTextFieldAdd = new JTextField(20);
    JTextField nameTextFieldMod = new JTextField(25);
    JTextField commentTextFieldAdd = new JTextField(20);
    JTextField commentTextFieldMod = new JTextField(25);
    int deleteOption;
    int deleteEvent;
    int felh;
    Szemely newUser = new Szemely();
    DB database = new DB();
    int UserID;

    //<editor-fold defaultstate="collapsed" desc="Panelek felépítése, setup metódusok"> 
    public void setupAdd() {
        nameTextFieldAdd.setText(null);
        commentTextFieldAdd.setText(null);
        mainFrameAdd.setTitle("AddUser");
        mainFrameAdd.setVisible(true);
        mainPanelAddButtons.setLayout(new FlowLayout());
        GridBagLayout gbl = new GridBagLayout();
        mainPanelAdd.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;//VERTICAL, HORIZONTAL
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanelAdd.add(nameLabelAdd, gbc);
        gbc.gridy = 1;
        mainPanelAdd.add(commentLabelAdd, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        mainPanelAdd.add(nameTextFieldAdd, gbc);
        gbc.gridy = 1;
        mainPanelAdd.add(commentTextFieldAdd, gbc);
        mainPanelAddButtons.add(saveButtonAdd);
        mainPanelAddButtons.add(saveCancelAdd);
        saveButtonAdd.addActionListener(this);
        saveCancelAdd.addActionListener(this);
        mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Személy Felvitele"));
        mainFrameAdd.getContentPane().setPreferredSize(new Dimension(400, 150));
        mainFrameAdd.add(mainPanelAdd, BorderLayout.CENTER);
        mainFrameAdd.add(mainPanelAddButtons, BorderLayout.SOUTH);
        mainFrameAdd.pack();
        mainFrameAdd.setLocationRelativeTo(null);
    }

    public void setupMod(Szemely Valaki) {
        mainFrameMod.setTitle("ModUser");
        mainFrameMod.setVisible(true);
        mainPanelModButtons.setLayout(new FlowLayout());
        GridBagLayout gbl = new GridBagLayout();
        mainPanelMod.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;//VERTICAL, HORIZONTAL
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanelMod.add(nameLabelMod, gbc);
        gbc.gridy = 1;
        mainPanelMod.add(commentLabelMod, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        nameTextFieldMod.setText(Valaki.getName());
        mainPanelMod.add(nameTextFieldMod, gbc);
        gbc.gridy = 1;
        commentTextFieldMod.setText(Valaki.getMegjegyzes());
        mainPanelMod.add(commentTextFieldMod, gbc);
        mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Személy módosítása"));
        mainPanelModButtons.add(saveButtonMod);
        mainPanelModButtons.add(saveCancelMod);
        saveButtonMod.addActionListener(this);
        saveCancelMod.addActionListener(this);
        mainFrameMod.add(mainPanelMod, BorderLayout.CENTER);
        mainFrameMod.add(mainPanelModButtons, BorderLayout.SOUTH);
        mainFrameMod.getContentPane().setPreferredSize(new Dimension(400, 150));
        mainFrameMod.pack();
        mainFrameMod.setLocationRelativeTo(null);
        UserID = Valaki.getId();
    }

    public void setupDel(Szemely Valaki) {
        deleteEvent = 0;
        deleteOption = JOptionPane.showConfirmDialog(rootPane, "Biztosan törli a kijelölt Személyt?", "Option", JOptionPane.YES_NO_OPTION);
        if (deleteOption == 0) {
            newUser.setId(Valaki.getId());
            database.deleteSzemely(newUser);
            this.dispose();
            deleteEvent = 1;
        } else {
            this.dispose();
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés"> 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButtonAdd) {
            ArrayList<AktFelhasznalo> felhasznalo = null;
            felhasznalo = database.getAktFelhasznalo();
            felh = felhasznalo.get(0).getAktFelID();
            newUser.setName(nameTextFieldAdd.getText());
            newUser.setMegjegyzes(commentTextFieldAdd.getText());
            newUser.setAktFel(felh);
            database.addSzemely(newUser);
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
        if (e.getSource() == saveCancelAdd) {
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
        if (e.getSource() == saveButtonMod) {
            newUser.setName(nameTextFieldMod.getText());
            newUser.setMegjegyzes(commentTextFieldMod.getText());
            newUser.setId(UserID);
            database.updateSzemely(newUser);
            mainFrameMod.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
        if (e.getSource() == saveCancelMod) {
            mainFrameMod.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
    }
    //</editor-fold>
}
