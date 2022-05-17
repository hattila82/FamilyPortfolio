/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.AktFelhasznalo;
import Controls.Kategoria;
import Controls.Penznem;
import Controls.Szemely;
import Controls.Tipus;
import Controls.Tranzakcio;
import Controls.TranzakcioRendszeresseg;
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
 * @author HorvathAttila TranzakcioMuvelet tábla műveletek és felhasználói felületek
 */
public class TranzakcioMuvelet extends JFrame implements ActionListener {

    JFrame mainFrameAdd = new JFrame();
    JFrame mainFrameMod = new JFrame();
    JPanel mainPanelAdd = new JPanel();
    JPanel mainPanelAddButtons = new JPanel();
    JPanel mainPanelModButtons = new JPanel();
    JPanel mainPanelMod = new JPanel();
    JLabel reguLabelAdd = new JLabel("Rendszeresség:");
    JLabel reguLabelMod = new JLabel("Rendszeresség:");
    JLabel nameLabelAdd = new JLabel("Tranzakció:");
    JLabel nameLabelMod = new JLabel("Tranzakció:");
    JLabel typeLabelAdd = new JLabel("Kategória:");
    JLabel typeLabelMod = new JLabel("Kategória:");
    JLabel valueLabelAdd = new JLabel("Összeg:");
    JLabel valueLabelMod = new JLabel("Összeg:");
    JLabel currencyLabelAdd = new JLabel("Pénznem:");
    JLabel currencyLabelMod = new JLabel("Pénznem:");
    JLabel dateLabelAdd = new JLabel("Dátum:");
    JLabel dateLabelMod = new JLabel("Dátum:");
    JLabel userLabelAdd = new JLabel("Személy:");
    JLabel userLabelAdd2 = new JLabel("Személy:");
    JLabel userLabelMod = new JLabel("Személy:");
    JLabel userLabelMod2 = new JLabel("Személy:");
    JLabel goodsLabelAdd = new JLabel("Vagyoni Eszköz:");
    JLabel goodsLabelMod = new JLabel("Vagyoni Eszköz:");
    JLabel goodsLabelAdd2 = new JLabel("Vagyoni Eszköz:");
    JLabel goodsLabelMod2 = new JLabel("Vagyoni Eszköz:");
    JLabel goodsLabelAdd3 = new JLabel("Vagyontárgy:");
    JLabel goodsLabelMod3 = new JLabel("Vagyontárgy:");
    JButton saveButtonAdd = new JButton("ment");
    JButton saveButtonMod = new JButton("ment");
    JButton saveCancelAdd = new JButton("mégsem");
    JButton saveCancelMod = new JButton("mégsem");
    JTextField nameTextFieldAdd = new JTextField(20);
    JTextField nameTextFieldMod = new JTextField(25);
    JComboBox categoryComboFieldAdd = new JComboBox();
    JComboBox categoryComboFieldMod = new JComboBox();
    JTextField valueTextFieldAdd = new JTextField(20);
    JTextField valueTextFieldMod = new JTextField(25);
    JComboBox currencyComboFieldAdd = new JComboBox();
    JComboBox currencyComboFieldMod = new JComboBox();
    JTextField dateTextFieldAdd = new JTextField(20);
    JTextField dateTextFieldMod = new JTextField(25);
    JComboBox userComboFieldAdd = new JComboBox();
    JComboBox userComboFieldMod = new JComboBox();
    JComboBox userComboFieldAdd2 = new JComboBox();
    JComboBox userComboFieldMod2 = new JComboBox();
    JComboBox goodsTypeComboFieldAdd = new JComboBox();
    JComboBox goodsTypeComboFieldMod = new JComboBox();
    JComboBox goodsTypeComboFieldAdd2 = new JComboBox();
    JComboBox goodsTypeComboFieldMod2 = new JComboBox();
    JComboBox goodsComboFieldAdd = new JComboBox();
    JComboBox goodsComboFieldMod = new JComboBox();
    JComboBox regularityComboFieldAdd = new JComboBox();
    JComboBox regularityComboFieldMod = new JComboBox();
    JTextField commentTextFieldAdd = new JTextField(20);
    JTextField commentTextFieldMod = new JTextField(25);
    int deleteOption;
    int deleteEvent;
    Tipus typeInstance = new Tipus();
    Vagyon newVagyon = new Vagyon();
    Tranzakcio newTranzakcio = new Tranzakcio();
    DB database = new DB();
    int VagyonID;
    int TranzakcioID;
    JDatePickerImpl datePicker;
    SqlDateModel model = new SqlDateModel();
    Properties Properties = new Properties();
    CustomFormat CustomFormat = new CustomFormat();
    int TranzakcioParameter = 0;
    int felh;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus"> 
    public TranzakcioMuvelet(int TranzakcioParameter) {
        this.TranzakcioParameter = TranzakcioParameter;
    }

    public TranzakcioMuvelet() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Komponens feltöltése metódusok"> 
    public void loadGoodsCombo(String Vagyon, JComboBox typeCombo) {
        ArrayList<Vagyon> vagyonLista = null;
        vagyonLista = database.getVagyonParam(Vagyon);
        for (int i = 0; i < vagyonLista.size(); i++) {
            typeCombo.addItem(vagyonLista.get(i).getNev());
        }
    }

    public void loadCategoryCombo(String Kategoria, JComboBox typeCombo) {
        ArrayList<Kategoria> kategoriaLista = null;
        kategoriaLista = database.getKategoriaParam(Kategoria);
        for (int i = 0; i < kategoriaLista.size(); i++) {
            typeCombo.addItem(kategoriaLista.get(i).getNev());
        }
    }

    public void loadGoodsTypeCombo(int Szemely, JComboBox typeCombo) {
        ArrayList<Vagyon> vagyonLista = null;
        vagyonLista = database.getVagyonAndTipusParam(Szemely);
        for (int i = 0; i < vagyonLista.size(); i++) {
            typeCombo.addItem(vagyonLista.get(i).getVagyonTipus());
        }
    }

    public void loadTypeCombo(String Forma, JComboBox typeCombo) {
        ArrayList<Tipus> tipusLista = null;
        tipusLista = database.getTipusParam(Forma);
        for (int i = 0; i < tipusLista.size(); i++) {
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

    public void loadRegularityCombo(JComboBox UserCombo) {
        ArrayList<TranzakcioRendszeresseg> tranzakcioLista = null;
        tranzakcioLista = database.getTranzakcioRendszeresseg();
        for (int i = 0; i < tranzakcioLista.size(); i++) {
            UserCombo.addItem(tranzakcioLista.get(i).getNev());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panelek felépítése, setup metódusok"> 
    public void setupAdd() {

        nameTextFieldAdd.setText(null);
        categoryComboFieldAdd.removeAllItems();
        valueTextFieldAdd.setText(null);
        currencyComboFieldAdd.removeAllItems();
        dateTextFieldAdd.setText(null);
        userComboFieldAdd.removeAllItems();
        userComboFieldAdd2.removeAllItems();
        commentTextFieldAdd.setText(null);
        goodsComboFieldAdd.removeAllItems();
        regularityComboFieldAdd.removeAllItems();
        mainFrameAdd.setTitle("AddTransaction");
        mainFrameAdd.setVisible(true);
        mainPanelAddButtons.setLayout(new FlowLayout());
        GridBagLayout gbl = new GridBagLayout();
        mainPanelAdd.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;//VERTICAL, HORIZONTAL
        valueTextFieldAdd.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c)
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_MINUS)
                        || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        loadCurrencyCombo(currencyComboFieldAdd);
        JDatePanelImpl panle = new JDatePanelImpl(model, Properties);
        model.setSelected(true);
        datePicker = new JDatePickerImpl(panle, CustomFormat);
        loadUserCombo(userComboFieldAdd);
        loadUserCombo(userComboFieldAdd2);
        userComboFieldAdd.addActionListener(this);
        userComboFieldAdd2.addActionListener(this);
        loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId(), goodsTypeComboFieldAdd);
        loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldAdd2.getSelectedItem()).get(0).getId(), goodsTypeComboFieldAdd2);
        switch (TranzakcioParameter) {
            case 1:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(typeLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Bevétel Tranzakcio Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelAdd.add(categoryComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 2:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(typeLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Kiadás Tranzakcio Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelAdd.add(categoryComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("2", categoryComboFieldAdd);
                break;
            case 3:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                userLabelAdd.setText("Személy (átvevő):");
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                goodsLabelAdd.setText("Pénzügyi eszközre:");
                mainPanelAdd.add(goodsLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                userLabelAdd2.setText("Személy (átadó):");
                mainPanelAdd.add(userLabelAdd2, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                goodsLabelAdd2.setText("Pénzügyi eszközről:");
                mainPanelAdd.add(goodsLabelAdd2, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Átvezetés Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd2, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd2, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 4:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(goodsLabelAdd3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Értékváltozás Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("4", goodsComboFieldAdd);
                mainPanelAdd.add(goodsComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 5:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(goodsLabelAdd3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Vagyontárgy Bevétel Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("4", goodsComboFieldAdd);
                mainPanelAdd.add(goodsComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 6:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(goodsLabelAdd3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Vagyontárgy Költség Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("4", goodsComboFieldAdd);
                mainPanelAdd.add(goodsComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 7:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(goodsLabelAdd3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Hozam Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("2", goodsComboFieldAdd);
                mainPanelAdd.add(goodsComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 8:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(goodsLabelAdd3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Veszteség Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("2", goodsComboFieldAdd);
                mainPanelAdd.add(goodsComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;

            case 9:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(goodsLabelAdd3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Hiteltőke változás Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("3", goodsComboFieldAdd);
                mainPanelAdd.add(goodsComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 10:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(goodsLabelAdd3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Hitel törlesztőrészlet Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("3", goodsComboFieldAdd);
                mainPanelAdd.add(goodsComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 11:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(typeLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Egyéb Bevétel Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelAdd.add(categoryComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("1", categoryComboFieldAdd);
                break;
            case 12:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelAdd.add(nameLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelAdd.add(typeLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelAdd.add(reguLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelAdd.add(valueLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelAdd.add(dateLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelAdd.add(userLabelAdd, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsLabelAdd, gbc);
                mainPanelAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Egyéb Kiadás Felvitele"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelAdd.add(nameTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelAdd.add(categoryComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                loadRegularityCombo(regularityComboFieldAdd);
                mainPanelAdd.add(regularityComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelAdd.add(valueTextFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelAdd.add(currencyComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelAdd.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelAdd.add(userComboFieldAdd, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelAdd.add(goodsTypeComboFieldAdd, gbc);
                loadCategoryCombo("2", categoryComboFieldAdd);
                break;
        }
        mainPanelAddButtons.add(saveButtonAdd);
        mainPanelAddButtons.add(saveCancelAdd);
        saveButtonAdd.addActionListener(this);
        saveCancelAdd.addActionListener(this);
        mainFrameAdd.getContentPane().setPreferredSize(new Dimension(400, 400));
        mainFrameAdd.add(mainPanelAdd, BorderLayout.CENTER);
        mainFrameAdd.add(mainPanelAddButtons, BorderLayout.SOUTH);
        mainFrameAdd.pack();
        mainFrameAdd.setLocationRelativeTo(null);

    }

    public void setupMod(Tranzakcio TranzakcioElem) {
        mainFrameMod.repaint();
        mainFrameMod.setTitle("ModTranzaction");
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
        gbc.gridy = 0;
        gbc.gridx = 1;
        nameTextFieldMod.setText(TranzakcioElem.getNev());
        mainPanelMod.add(nameTextFieldMod, gbc);
        categoryComboFieldMod.setSelectedItem(TranzakcioElem.getKategoriaNev());
        valueTextFieldMod.setText(Integer.toString(TranzakcioElem.getOsszeg()));
        valueTextFieldAdd.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c)
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_MINUS)
                        || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        loadCurrencyCombo(currencyComboFieldMod); //TODO feltöltés és kiválasztás
        currencyComboFieldMod.setSelectedItem(database.getPenznemIDkeres(TranzakcioElem.getPenznem()).get(0).getName());
        model.setValue(java.sql.Date.valueOf(TranzakcioElem.getDatum()));
        JDatePanelImpl panle = new JDatePanelImpl(model, Properties);
        model.setSelected(true);
        datePicker = new JDatePickerImpl(panle, CustomFormat);
        userComboFieldMod.addActionListener(this);
        userComboFieldMod2.addActionListener(this);
        loadUserCombo(userComboFieldMod);
        loadUserCombo(userComboFieldMod2);
        userComboFieldMod.setSelectedItem(TranzakcioElem.getSzemelyNev1());
        userComboFieldMod2.setSelectedItem(TranzakcioElem.getSzemelyNev2());
        loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId(), goodsTypeComboFieldMod);
        loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldMod2.getSelectedItem()).get(0).getId(), goodsTypeComboFieldMod2);
        String Szemelynev = database.getSzemelyNev(TranzakcioElem.getSzemelyNev1()).get(0).getName(); // szemely neve
        String Szemelynev2 = database.getSzemelyNev(TranzakcioElem.getSzemelyNev2()).get(0).getName(); // szemely neve
        int SzemelyID = database.getSzemelyNev(Szemelynev).get(0).getId(); // szemely ID
        int SzemelyID2 = database.getSzemelyNev(Szemelynev2).get(0).getId(); // szemely ID
        goodsTypeComboFieldMod.setSelectedItem(database.getVagyonAndTipusParamSelected(SzemelyID, TranzakcioElem.getVagyonID1()).get(0).getVagyonTipus());
        goodsTypeComboFieldMod2.setSelectedItem(database.getVagyonAndTipusParamSelected(SzemelyID2, TranzakcioElem.getVagyonID2()).get(0).getVagyonTipus());
        loadRegularityCombo(regularityComboFieldMod);
        goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
        String c = database.getTranzakcioRendszeressegIDidbol(TranzakcioElem.getTranzakcioRendszeresseg()).get(0).getNev();
        regularityComboFieldMod.setSelectedItem(database.getTranzakcioRendszeressegIDidbol(TranzakcioElem.getTranzakcioRendszeresseg()).get(0).getNev()); //TODO
        switch (TranzakcioParameter) {
            case 1:
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(typeLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Bevétel Tranzakcio Módosítása"));
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(categoryComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                categoryComboFieldMod.setSelectedItem(TranzakcioElem.getKategoriaNev());
                break;
            case 2:
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(typeLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Kiadás Tranzakcio Módosítása"));
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(categoryComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("2", categoryComboFieldMod);
                categoryComboFieldMod.setSelectedItem(TranzakcioElem.getKategoriaNev());
                break;
            case 3:
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                userLabelMod.setText("Személy (átvevő):");
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                goodsLabelMod.setText("Pénzügyi eszközre:");
                mainPanelMod.add(goodsLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                userLabelMod2.setText("Személy (átadó):");
                mainPanelMod.add(userLabelMod2, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                goodsLabelMod2.setText("Pénzügyi eszközről:");
                mainPanelMod.add(goodsLabelMod2, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Új Átvezetés Módosítása"));
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod2, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                mainPanelMod.add(goodsTypeComboFieldMod2, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;
            case 4:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(goodsLabelMod3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Értékváltozás Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                loadGoodsCombo("4", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;
            case 5:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(goodsLabelMod3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Vagyontárgy bevétel Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(goodsComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod2, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("4", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod2, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;
            case 6:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(goodsLabelMod3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Vagyontárgy költség Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(goodsComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("4", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod2, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;
            case 7:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(goodsLabelMod3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Hozam Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(goodsComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("2", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;

            case 8:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(goodsLabelMod3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Veszteség Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(goodsComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("2", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;
            case 9:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(goodsLabelMod3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Hiteltőke Vátltozás Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(goodsComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("3", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;
            case 10:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(goodsLabelMod3, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Hitel Törlesztőrészlet Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(goodsComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("3", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                break;
            case 11:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(typeLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Egyéb Bevétel Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(categoryComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("3", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("1", categoryComboFieldMod);
                categoryComboFieldMod.setSelectedItem(TranzakcioElem.getKategoriaNev());
                break;
            case 12:
                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanelMod.add(nameLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanelMod.add(typeLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanelMod.add(reguLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanelMod.add(valueLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 4;
                mainPanelMod.add(currencyLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 5;
                mainPanelMod.add(dateLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 6;
                mainPanelMod.add(userLabelMod, gbc);
                gbc.gridx = 0;
                gbc.gridy = 7;
                mainPanelMod.add(goodsLabelMod, gbc);
                mainPanelMod.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Egyéb Kiadás Módosítása"));
                gbc.gridy = 0;
                gbc.gridx = 1;
                mainPanelMod.add(nameTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                mainPanelMod.add(categoryComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                mainPanelMod.add(regularityComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                mainPanelMod.add(valueTextFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                mainPanelMod.add(currencyComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 5;
                mainPanelMod.add(datePicker, gbc);
                gbc.gridx = 1;
                gbc.gridy = 6;
                mainPanelMod.add(userComboFieldMod, gbc);
                gbc.gridx = 1;
                gbc.gridy = 7;
                loadGoodsCombo("3", goodsComboFieldMod);
                goodsComboFieldMod.setSelectedItem(database.getVagyonIDidbol(TranzakcioElem.getVagyonID1()).get(0).getNev());
                mainPanelMod.add(goodsTypeComboFieldMod, gbc);
                loadCategoryCombo("2", categoryComboFieldMod);
                categoryComboFieldMod.setSelectedItem(TranzakcioElem.getKategoriaNev());
                break;
        }
        mainPanelModButtons.add(saveButtonMod);
        mainPanelModButtons.add(saveCancelMod);
        saveButtonMod.addActionListener(this);
        saveCancelMod.addActionListener(this);
        mainFrameMod.add(mainPanelMod, BorderLayout.CENTER);
        mainFrameMod.add(mainPanelModButtons, BorderLayout.SOUTH);
        mainFrameMod.getContentPane().setPreferredSize(new Dimension(400, 400));
        mainFrameMod.pack();
        mainFrameMod.setLocationRelativeTo(null);
        TranzakcioID = TranzakcioElem.getTRID();
    }

    public void setupDel(Tranzakcio TranzakcioPeldany) {
        deleteEvent = 0;
        deleteOption = JOptionPane.showConfirmDialog(rootPane, "Biztosan törli a kijelölt Tranzakcioelemet?", "Option", JOptionPane.YES_NO_OPTION);
        if (deleteOption == 0) {
            newTranzakcio.setTRID(TranzakcioPeldany.getTRID());
            database.deleteTranzakcio(newTranzakcio);
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

        if (e.getSource() == userComboFieldAdd) {
            goodsTypeComboFieldAdd.removeAllItems();
            loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId(), goodsTypeComboFieldAdd);
        }
        if (e.getSource() == userComboFieldAdd2) {
            goodsTypeComboFieldAdd2.removeAllItems();
            loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldAdd2.getSelectedItem()).get(0).getId(), goodsTypeComboFieldAdd2);
        }

        if (e.getSource() == userComboFieldMod) {
            goodsTypeComboFieldMod.removeAllItems();
            loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId(), goodsTypeComboFieldMod);
        }

        if (e.getSource() == userComboFieldMod2) {
            goodsTypeComboFieldMod2.removeAllItems();
            loadGoodsTypeCombo(database.getSzemelyNev((String) userComboFieldMod2.getSelectedItem()).get(0).getId(), goodsTypeComboFieldMod2);
        }
        if (e.getSource() == saveButtonAdd) {
            newTranzakcio.setNev(nameTextFieldAdd.getText());
            newTranzakcio.setDatum(model.getValue().toLocalDate());
            newTranzakcio.setOsszeg((Integer.parseInt(valueTextFieldAdd.getText())));
            newTranzakcio.setPenznem(database.getPenznemID((String) currencyComboFieldAdd.getSelectedItem()).get(0).getId());
            ArrayList<AktFelhasznalo> felhasznalo = null;
            felhasznalo = database.getAktFelhasznalo();
            felh = felhasznalo.get(0).getAktFelID();
            switch (TranzakcioParameter) {
                case 1:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(1);
                    newTranzakcio.setTranzakcioRendszeresseg(1);
                    newTranzakcio.setAktFel(felh);
                    break;
                case 2:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 2).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(2);
                    newTranzakcio.setTranzakcioRendszeresseg(1);
                    newTranzakcio.setAktFel(felh);
                    break;
                case 3:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd2.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(3);
                    newTranzakcio.setTranzakcioRendszeresseg(1);
                    newTranzakcio.setAktFel(felh);
                    break;
                case 4:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(4);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 5:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd2.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(5);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 6:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd2.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(6);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 7:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(7);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 8:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(8);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 9:

                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(9);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 10:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldAdd.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(10);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 11:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(11);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;
                case 12:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldAdd.getSelectedItem(), 2).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldAdd.getSelectedItem(), database.getSzemelyNev((String) userComboFieldAdd.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(12);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldAdd.getSelectedItem()).get(0).getTRRID());
                    newTranzakcio.setAktFel(felh);
                    break;

            }
            database.addTranzakcio(newTranzakcio);
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
            newTranzakcio.setTRID(TranzakcioID);
            newTranzakcio.setNev(nameTextFieldMod.getText());
            newTranzakcio.setDatum(model.getValue().toLocalDate());
            newTranzakcio.setOsszeg((Integer.parseInt(valueTextFieldMod.getText())));
            newTranzakcio.setPenznem(database.getPenznemID((String) currencyComboFieldMod.getSelectedItem()).get(0).getId());
            switch (TranzakcioParameter) {
                case 1:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(1);
                    newTranzakcio.setTranzakcioRendszeresseg(1);
                    break;
                case 2:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 2).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(2);
                    newTranzakcio.setTranzakcioRendszeresseg(1);
                    break;
                case 3:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod2.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(3);
                    newTranzakcio.setTranzakcioRendszeresseg(1);
                    break;
                case 4:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(4);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 5:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod2.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(5);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 6:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod2.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(6);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 7:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(7);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 8:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(8);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 9:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(9);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 10:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonAndTipusParamID((String) goodsTypeComboFieldMod.getSelectedItem(), database.getSzemelyNev((String) userComboFieldMod2.getSelectedItem()).get(0).getId()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(10);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 11:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 1).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(11);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;
                case 12:
                    newTranzakcio.setKategoria(database.getKategoriaID((String) categoryComboFieldMod.getSelectedItem(), 2).get(0).getKID());
                    newTranzakcio.setVagyonID1(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setVagyonID2(database.getVagyonIDnevbol((String) goodsComboFieldMod.getSelectedItem()).get(0).getVID());
                    newTranzakcio.setTranzakcioTipus(12);
                    newTranzakcio.setTranzakcioRendszeresseg(database.getTranzakcioRendszeressegID((String) regularityComboFieldMod.getSelectedItem()).get(0).getTRRID());
                    break;

            }
            database.updateTranzakcio(newTranzakcio);
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
