/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.AktFelhasznalo;
import Controls.Penznem;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author HorvathAttila PenznemMuvelet tábla műveletek és felhasználói
 * felületek
 */
public class PenznemMuvelet extends JFrame implements ActionListener {

    JFrame mainFrameAdd = new JFrame();
    JFrame mainFrameMod = new JFrame();
    JPanel mainPanelAdd = new JPanel();
    JPanel mainPanelAddButtons = new JPanel();
    JPanel mainPanelModButtons = new JPanel();
    JPanel mainPanelMod = new JPanel();
    JLabel nameLabelAdd = new JLabel("Név:");
    JLabel nameLabelMod = new JLabel("Név:");
    JLabel ratioLabelAdd = new JLabel("Pénznem:");
    JLabel ratiotLabelMod = new JLabel("Pénznem:");
    JButton saveButtonAdd = new JButton("ment");
    JButton saveButtonMod = new JButton("ment");
    JButton saveCancelAdd = new JButton("mégsem");
    JButton saveCancelMod = new JButton("mégsem");
    JTextField nameTextFieldAdd = new JTextField(20);
    JTextField nameTextFieldMod = new JTextField(25);
    JTextField ratioTextFieldAdd = new JTextField(20);
    JFormattedTextField ratioTextFieldAddForm = new JFormattedTextField();
    JFormattedTextField ratioTextFieldModForm = new JFormattedTextField();
    JTextField ratioTextFieldMod = new JTextField(25);
    int deleteOption;
    int deleteEvent;
    Penznem newCurrency = new Penznem();
    DB database = new DB();
    int CurrencyID;
    DefaultFormatterFactory dff = new DefaultFormatterFactory();
    int felh;

    //<editor-fold defaultstate="collapsed" desc="Panelek felépítése, setup metódusok"> 
    public void setupAdd() throws ParseException {
        nameTextFieldAdd.setText(null);
        ratioTextFieldAdd.setText(null);
        mainFrameAdd.setTitle("AddCurrency");
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
        mainPanelAdd.add(ratioLabelAdd, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        mainPanelAdd.add(nameTextFieldAdd, gbc);
        gbc.gridy = 1;
        mainPanelAdd.add(ratioTextFieldAdd, gbc);
        mainPanelAddButtons.add(saveButtonAdd);
        mainPanelAddButtons.add(saveCancelAdd);
        saveButtonAdd.addActionListener(this);
        saveCancelAdd.addActionListener(this);
        mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Pénznem Felvitele"));
        mainFrameAdd.getContentPane().setPreferredSize(new Dimension(400, 150));
        mainFrameAdd.add(mainPanelAdd, BorderLayout.CENTER);
        mainFrameAdd.add(mainPanelAddButtons, BorderLayout.SOUTH);
        mainFrameAdd.pack();
        mainFrameAdd.setLocationRelativeTo(null);

    }

    public void setupMod(Penznem Valuta) throws ParseException {
        mainFrameMod.setTitle("ModCurrency");
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
        mainPanelMod.add(ratiotLabelMod, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        nameTextFieldMod.setText(Valuta.getName());
        mainPanelMod.add(nameTextFieldMod, gbc);
        gbc.gridy = 1;
        ratioTextFieldMod.setText(Float.toString(Valuta.getHUFRatio()));
        mainPanelMod.add(ratioTextFieldMod, gbc);
        mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pénznem módosítása"));
        mainPanelModButtons.add(saveButtonMod);
        mainPanelModButtons.add(saveCancelMod);
        saveButtonMod.addActionListener(this);
        saveCancelMod.addActionListener(this);
        mainFrameMod.add(mainPanelMod, BorderLayout.CENTER);
        mainFrameMod.add(mainPanelModButtons, BorderLayout.SOUTH);
        mainFrameMod.getContentPane().setPreferredSize(new Dimension(400, 150));
        mainFrameMod.pack();
        mainFrameMod.setLocationRelativeTo(null);
        CurrencyID = Valuta.getId();
    }

    public void setupDel(Penznem Valuta) {
        deleteEvent = 0;
        deleteOption = JOptionPane.showConfirmDialog(rootPane, "Biztosan törli a kijelölt Pénznemet?", "Option", JOptionPane.YES_NO_OPTION);
        if (deleteOption == 0) {
            newCurrency.setId(Valuta.getId());
            database.deletePenznem(newCurrency);
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
            newCurrency.setName(nameTextFieldAdd.getText());
            ArrayList<AktFelhasznalo> felhasznalo = null;
            felhasznalo = database.getAktFelhasznalo();
            felh = felhasznalo.get(0).getAktFelID();
            newCurrency.setHUFRatio(Float.parseFloat(ratioTextFieldAdd.getText()));
            newCurrency.setAktFel(felh);
            database.addPenznem(newCurrency);
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
        if (e.getSource() == saveCancelAdd) {
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
        if (e.getSource() == saveButtonMod) {
            newCurrency.setName(nameTextFieldMod.getText());
            newCurrency.setHUFRatio(Float.parseFloat(ratioTextFieldMod.getText()));
            newCurrency.setId(CurrencyID);
            database.updatePenznem(newCurrency);
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
