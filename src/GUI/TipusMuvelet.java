/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.AktFelhasznalo;
import Controls.Forma;
import Controls.Penznem;
import Controls.Szemely;
import Controls.Tipus;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author HorvathAttila TipusMuvelet tábla műveletek és felhasználói felületek
 */
public class TipusMuvelet extends JFrame implements ActionListener {

    JFrame mainFrameAdd = new JFrame();
    JFrame mainFrameMod = new JFrame();
    JPanel mainPanelAdd = new JPanel();
    JPanel mainPanelAddButtons = new JPanel();
    JPanel mainPanelModButtons = new JPanel();
    JPanel mainPanelMod = new JPanel();
    JLabel nameLabelAdd = new JLabel("Tipus:");
    JLabel nameLabelMod = new JLabel("Tipus:");
    JLabel commentLabelAdd = new JLabel("Forma:");
    JLabel commentLabelMod = new JLabel("Forma:");
    JButton saveButtonAdd = new JButton("ment");
    JButton saveButtonMod = new JButton("ment");
    JButton saveCancelAdd = new JButton("mégsem");
    JButton saveCancelMod = new JButton("mégsem");
    JTextField nameTextFieldAdd = new JTextField(20);
    JTextField nameTextFieldMod = new JTextField(25);
    JComboBox typeComboFieldAdd = new JComboBox();
    JComboBox typeComboFieldMod = new JComboBox();
    int deleteOption;
    int deleteEvent;
    int felh;
    Szemely newUser = new Szemely();
    DB database = new DB();
    int UserID;
    int TipusID;
    Tipus newTipus = new Tipus();

    
    //<editor-fold defaultstate="collapsed" desc="Komponens feltöltése metódus"> 
    public void loadTypeCombo(JComboBox CurrencyCombo) {
        ArrayList<Forma> FormaLista = null;
        FormaLista = database.getForma();
        for (int i = 0; i < FormaLista.size(); i++) {
            CurrencyCombo.addItem(FormaLista.get(i).getNev());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panelek felépítése, setup metódusok"> 
    public void setupAdd() {
        typeComboFieldAdd.removeAllItems();
        nameTextFieldAdd.setText(null);
        mainFrameAdd.setTitle("AddTipus");
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
        loadTypeCombo(typeComboFieldAdd);
        gbc.gridy = 1;
        mainPanelAdd.add(typeComboFieldAdd, gbc);
        mainPanelAddButtons.add(saveButtonAdd);
        mainPanelAddButtons.add(saveCancelAdd);
        saveButtonAdd.addActionListener(this);
        saveCancelAdd.addActionListener(this);
        mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Tipus Felvitele"));
        mainFrameAdd.getContentPane().setPreferredSize(new Dimension(400, 150));
        mainFrameAdd.add(mainPanelAdd, BorderLayout.CENTER);
        mainFrameAdd.add(mainPanelAddButtons, BorderLayout.SOUTH);
        mainFrameAdd.pack();
        mainFrameAdd.setLocationRelativeTo(null);
    }

    public void setupMod(Tipus Tipus) {

        mainFrameMod.setTitle("ModType");
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
        nameTextFieldMod.setText(Tipus.getNev());
        mainPanelMod.add(nameTextFieldMod, gbc);
        gbc.gridy = 1;
        loadTypeCombo(typeComboFieldMod);
        typeComboFieldMod.setSelectedItem(Tipus.getFormaNev());
        mainPanelMod.add(typeComboFieldMod, gbc);
        mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tipus módosítása"));
        mainPanelModButtons.add(saveButtonMod);
        mainPanelModButtons.add(saveCancelMod);
        saveButtonMod.addActionListener(this);
        saveCancelMod.addActionListener(this);
        mainFrameMod.add(mainPanelMod, BorderLayout.CENTER);
        mainFrameMod.add(mainPanelModButtons, BorderLayout.SOUTH);
        mainFrameMod.getContentPane().setPreferredSize(new Dimension(400, 150));
        mainFrameMod.pack();
        mainFrameMod.setLocationRelativeTo(null);
        TipusID = Tipus.getTID();
    }

    public void setupDel(Tipus Tipus) {
        deleteEvent = 0;
        deleteOption = JOptionPane.showConfirmDialog(rootPane, "Biztosan törli a kijelölt Típust?", "Option", JOptionPane.YES_NO_OPTION);
        if (deleteOption == 0) {
            newTipus.setTID(Tipus.getTID());
            database.deleteTipus(newTipus);
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
            newTipus.setNev(nameTextFieldAdd.getText());
            newTipus.setForma(database.getFormaID((String) typeComboFieldAdd.getSelectedItem()).get(0).getFID());
            newTipus.setAktFel(felh);
            database.addTipus(newTipus);
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
        if (e.getSource() == saveCancelAdd) {
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
        }
        if (e.getSource() == saveButtonMod) {
            newTipus.setTID(TipusID);
            newTipus.setNev(nameTextFieldMod.getText());
            newTipus.setForma(database.getFormaID((String) typeComboFieldMod.getSelectedItem()).get(0).getFID());
            database.updateTipus(newTipus);
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
