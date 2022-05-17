/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.AktFelhasznalo;
import Controls.Penznem;
import Controls.Szemely;
import Controls.Tipus;
import Controls.Vagyon;
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
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

/**
 *
 * @author HorvathAttila VagyonMuvelet tábla műveletek és felhasználói felületek
 */
public class VagyonMuvelet extends JFrame implements ActionListener {

    JFrame mainFrameAdd = new JFrame();
    JFrame mainFrameMod = new JFrame();
    JPanel mainPanelAdd = new JPanel();
    JPanel mainPanelAddButtons = new JPanel();
    JPanel mainPanelModButtons = new JPanel();
    JPanel mainPanelMod = new JPanel();
    JLabel nameLabelAdd = new JLabel("Vagyoni eszköz:");
    JLabel nameLabelMod = new JLabel("Vagyoni eszköz:");
    JLabel typeLabelAdd = new JLabel("Típus:");
    JLabel typeLabelMod = new JLabel("Típus:");
    JLabel valueLabelAdd = new JLabel("Nyitó egyenleg:");
    JLabel valueLabelMod = new JLabel("Nyitó egyenleg:");
    JLabel currencyLabelAdd = new JLabel("Pénznem:");
    JLabel currencyLabelMod = new JLabel("Pénznem:");
    JLabel dateLabelAdd = new JLabel("Nyitás dátuma:");
    JLabel dateLabelMod = new JLabel("Nyitás dátuma:");
    JLabel userLabelAdd = new JLabel("Személy:");
    JLabel userLabelMod = new JLabel("Személy:");
    JLabel commentLabelAdd = new JLabel("Megjegyzés:");
    JLabel commentLabelMod = new JLabel("Megjegyzés:");
    JButton saveButtonAdd = new JButton("ment");
    JButton saveButtonMod = new JButton("ment");
    JButton saveCancelAdd = new JButton("mégsem");
    JButton saveCancelMod = new JButton("mégsem");
    JTextField nameTextFieldAdd = new JTextField(20);
    JTextField nameTextFieldMod = new JTextField(25);
    JComboBox typeComboFieldAdd = new JComboBox();
    JComboBox typeComboFieldMod = new JComboBox();
    JTextField valueTextFieldAdd = new JTextField(20);
    JTextField valueTextFieldMod = new JTextField(25);
    JComboBox currencyComboFieldAdd = new JComboBox();
    JComboBox currencyComboFieldMod = new JComboBox();
    JTextField dateTextFieldAdd = new JTextField(20);
    JTextField dateTextFieldMod = new JTextField(25);
    JComboBox userComboFieldAdd = new JComboBox();
    JComboBox userComboFieldMod = new JComboBox();
    JTextField commentTextFieldAdd = new JTextField(20);
    JTextField commentTextFieldMod = new JTextField(25);
    int deleteOption;
    int deleteEvent;
    Tipus typeInstance = new Tipus();
    Vagyon newVagyon = new Vagyon();
    DB database = new DB();
    int VagyonID;
    JDatePickerImpl datePicker;
    SqlDateModel model = new SqlDateModel();
    Properties Properties = new Properties();
    CustomFormat CustomFormat = new CustomFormat();
    int VagyonParameter;
    int felh;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus"> 
    public VagyonMuvelet(int VagyonParameter) {
        this.VagyonParameter = VagyonParameter;
    }

    public VagyonMuvelet() {
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Komponens feltöltése metódusok"> 
    public void loadTypeCombo(String Forma, JComboBox typeCombo) {
        ArrayList<Tipus> tipusLista = null;
        tipusLista = database.getTipusParam(Forma);
        for (int i = 0; i < tipusLista.size(); i++) {
            System.out.println(tipusLista.get(i).getNev());
            typeCombo.addItem(tipusLista.get(i).getNev());
        }
    }

    public void loadCurrencyCombo(JComboBox CurrencyCombo) {
        ArrayList<Penznem> penznemLista = null;
        penznemLista = database.getPenznem();
        for (int i = 0; i < penznemLista.size(); i++) {

            CurrencyCombo.addItem(penznemLista.get(i).getName());
        }
    }

    public void loadUserCombo(JComboBox UserCombo) {
        ArrayList<Szemely> szemelyLista = null;
        szemelyLista = database.getSzemely();
        for (int i = 0; i < szemelyLista.size(); i++) {
            UserCombo.addItem(szemelyLista.get(i).getName());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panelek felépítése, setup metódusok"> 
    public void setupAdd() {

        nameTextFieldAdd.setText(null);
        typeComboFieldAdd.removeAllItems();
        valueTextFieldAdd.setText(null);
        currencyComboFieldAdd.removeAllItems();
        dateTextFieldAdd.setText(null);
        userComboFieldAdd.removeAllItems();
        commentTextFieldAdd.setText(null);
        mainFrameAdd.setTitle("AddGoods");
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
        mainPanelAdd.add(typeLabelAdd, gbc);
        gbc.gridy = 2;
        mainPanelAdd.add(valueLabelAdd, gbc);
        gbc.gridy = 3;
        mainPanelAdd.add(currencyLabelAdd, gbc);
        gbc.gridy = 4;
        mainPanelAdd.add(dateLabelAdd, gbc);
        gbc.gridy = 5;
        mainPanelAdd.add(userLabelAdd, gbc);
        gbc.gridy = 6;
        mainPanelAdd.add(commentLabelAdd, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        mainPanelAdd.add(nameTextFieldAdd, gbc);
        loadTypeCombo(Integer.toString(VagyonParameter), typeComboFieldAdd);
        gbc.gridy = 1;
        mainPanelAdd.add(typeComboFieldAdd, gbc);
        gbc.gridy = 2;
        mainPanelAdd.add(valueTextFieldAdd, gbc);
        valueTextFieldAdd.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c)
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        loadCurrencyCombo(currencyComboFieldAdd);
        gbc.gridy = 3;
        mainPanelAdd.add(currencyComboFieldAdd, gbc);
        gbc.gridy = 4;
        JDatePanelImpl panle = new JDatePanelImpl(model, Properties);
        model.setSelected(true);
        datePicker = new JDatePickerImpl(panle, CustomFormat);
        mainPanelAdd.add(datePicker, gbc);
        loadUserCombo(userComboFieldAdd);
        gbc.gridy = 5;
        mainPanelAdd.add(userComboFieldAdd, gbc);
        gbc.gridy = 6;
        mainPanelAdd.add(commentTextFieldAdd, gbc);
        mainPanelAddButtons.add(saveButtonAdd);
        mainPanelAddButtons.add(saveCancelAdd);
        saveButtonAdd.addActionListener(this);
        saveCancelAdd.addActionListener(this);
        mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Vagyonelem Felvitele"));
        mainFrameAdd.getContentPane().setPreferredSize(new Dimension(400, 400));
        mainFrameAdd.add(mainPanelAdd, BorderLayout.CENTER);
        mainFrameAdd.add(mainPanelAddButtons, BorderLayout.SOUTH);
        mainFrameAdd.pack();
        mainFrameAdd.setLocationRelativeTo(null);
    }

    public void setupMod(Vagyon VagyonElem) {

        mainFrameMod.setTitle("ModGoods");
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
        mainPanelMod.add(typeLabelMod, gbc);
        gbc.gridy = 2;
        mainPanelMod.add(valueLabelMod, gbc);
        gbc.gridy = 3;
        mainPanelMod.add(currencyLabelMod, gbc);
        gbc.gridy = 4;
        mainPanelMod.add(dateLabelMod, gbc);
        gbc.gridy = 5;
        mainPanelMod.add(userLabelMod, gbc);
        gbc.gridy = 6;
        mainPanelMod.add(commentLabelMod, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        nameTextFieldMod.setText(VagyonElem.getNev());
        mainPanelMod.add(nameTextFieldMod, gbc);
        loadTypeCombo(Integer.toString(VagyonParameter), typeComboFieldMod);
        typeComboFieldMod.setSelectedItem(VagyonElem.getTipusNev());
        gbc.gridy = 1;
        mainPanelMod.add(typeComboFieldMod, gbc);
        gbc.gridy = 2;
        valueTextFieldMod.setText(Integer.toString(VagyonElem.getNyitoEgyenleg()));
        mainPanelMod.add(valueTextFieldMod, gbc);
        valueTextFieldMod.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c)
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        loadCurrencyCombo(currencyComboFieldMod);
        gbc.gridy = 3;
        currencyComboFieldMod.setSelectedItem(VagyonElem.getPenznemNev());
        mainPanelMod.add(currencyComboFieldMod, gbc);
        gbc.gridy = 4;
        model.setValue(java.sql.Date.valueOf(VagyonElem.getNyitoDatum()));

        JDatePanelImpl panle = new JDatePanelImpl(model, Properties);
        model.setSelected(true);
        datePicker = new JDatePickerImpl(panle, CustomFormat);
        mainPanelMod.add(datePicker, gbc);
        loadUserCombo(userComboFieldMod);
        userComboFieldMod.setSelectedItem(VagyonElem.getSzemelyNev());
        gbc.gridy = 5;
        mainPanelMod.add(userComboFieldMod, gbc);
        gbc.gridy = 6;
        commentTextFieldMod.setText(VagyonElem.getMegj());
        mainPanelMod.add(commentTextFieldMod, gbc);
        mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Vagyonelem módosítása"));
        mainPanelModButtons.add(saveButtonMod);
        mainPanelModButtons.add(saveCancelMod);
        saveButtonMod.addActionListener(this);
        saveCancelMod.addActionListener(this);
        mainFrameMod.add(mainPanelMod, BorderLayout.CENTER);
        mainFrameMod.add(mainPanelModButtons, BorderLayout.SOUTH);
        mainFrameMod.getContentPane().setPreferredSize(new Dimension(400, 400));
        mainFrameMod.pack();
        mainFrameMod.setLocationRelativeTo(null);
        VagyonID = VagyonElem.getVID();
    }

    public void setupDel(Vagyon VagyonPeldany) {
        deleteEvent = 0;
        deleteOption = JOptionPane.showConfirmDialog(rootPane, "Biztosan törli a kijelölt Vagyonelemet?", "Option", JOptionPane.YES_NO_OPTION);
        if (deleteOption == 0) {
            newVagyon.setVID(VagyonPeldany.getVID());
            database.deleteVagyon(newVagyon);
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
            newVagyon.setNev(nameTextFieldAdd.getText());
            newVagyon.setMegj(commentTextFieldAdd.getText());
            newVagyon.setNyitoEgyenleg((Integer.parseInt(valueTextFieldAdd.getText())));
            newVagyon.setNyitoDatum(model.getValue().toLocalDate());
            newVagyon.setTipus(database.getTipusID((String) typeComboFieldAdd.getSelectedItem()).get(0).getTID());
            newVagyon.setForma(VagyonParameter);
            newVagyon.setSzemely(database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId());
            newVagyon.setPenznem(database.getPenznemID((String) currencyComboFieldAdd.getSelectedItem()).get(0).getId());
            newVagyon.setAktFel(felh);
            database.addVagyon(newVagyon);
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
            mainPanelAdd.remove(datePicker);
        }
        if (e.getSource() == saveCancelAdd) {
            mainFrameAdd.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
            mainPanelAdd.remove(datePicker);
        }
        if (e.getSource() == saveButtonMod) {
            newVagyon.setVID(VagyonID);
            newVagyon.setNev(nameTextFieldMod.getText());
            newVagyon.setMegj(commentTextFieldMod.getText());
            newVagyon.setNyitoEgyenleg((Integer.parseInt(valueTextFieldMod.getText())));
            newVagyon.setNyitoDatum(model.getValue().toLocalDate());
            newVagyon.setTipus(database.getTipusID((String) typeComboFieldMod.getSelectedItem()).get(0).getTID());
            newVagyon.setForma(VagyonParameter);
            newVagyon.setSzemely(database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId());
            newVagyon.setPenznem(database.getPenznemID((String) currencyComboFieldMod.getSelectedItem()).get(0).getId());
            database.updateVagyon(newVagyon);
            mainFrameMod.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
            mainPanelMod.remove(datePicker);
        }
        if (e.getSource() == saveCancelMod) {
            mainFrameMod.dispose();
            ((JButton) e.getSource()).removeActionListener(this);
            mainPanelMod.remove(datePicker);
        }
    }
//</editor-fold>
}
