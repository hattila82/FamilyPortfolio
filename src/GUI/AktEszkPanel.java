/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Szemely;
import Controls.Tranzakcio;
import Controls.Vagyon;
import Model.DB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

/**
 *
 * @author HorvathAttila AktEszkPanel osztály létrehozása, konstruktor metódus
 */
public class AktEszkPanel extends JPanel implements ActionListener {

    JTable AktEszkTable = new JTable(0, 6);
    DefaultTableModel ModelTable = (DefaultTableModel) AktEszkTable.getModel();
    DB VagyonDB = new DB();
    Vagyon VagyonInstance = new Vagyon();
    ArrayList<Vagyon> AktivEszkozok = null;
    JPanel keresFeltetel = new JPanel();
    JPanel keresPanel = new JPanel();
    JPanel feltetelPanel = new JPanel();
    JPanel aktivEszkozPanel = new JPanel();
    JDatePickerImpl datePickerFrom;
    JDatePickerImpl datePickerTo;
    SqlDateModel model = new SqlDateModel();
    SqlDateModel model2 = new SqlDateModel();
    Properties Properties = new Properties();
    JLabel idoszak = new JLabel("Időszak: ");
    JLabel akteszk = new JLabel("Aktív vagyoni eszközök: ");
    JLabel tulajdonos = new JLabel("Személy: ");
    JLabel akteszklabel = new JLabel("Vagyoni eszközök: ");
    JComboBox szemelyCombo = new JComboBox();
    JComboBox akteszkCombo = new JComboBox();
    JButton keres = new JButton();
    LocalDate chooseDate;
    LocalDate chooseDate2;
    ArrayList<Tranzakcio> VagyonTranzakcio;
    ArrayList<Tranzakcio> VagyonTranzakcio2;
    ArrayList<Tranzakcio> VagyonTranzakcio3;
    ArrayList<Tranzakcio> VagyonTranzakcio4;

    //<editor-fold defaultstate="collapsed" desc="Komponens adatfeltöltés metódusok">      
    public void loadUserCombo(JComboBox UserCombo) {

        Szemely Osszes = new Szemely();
        Osszes.setName("Mind");
        ArrayList<Szemely> szemelyLista = null;
        szemelyLista = VagyonDB.getSzemely();
        szemelyLista.add(Osszes);
        for (int i = 0; i < szemelyLista.size(); i++) {
            UserCombo.addItem(szemelyLista.get(i).getName());
        }
    }

    public void loadGoodsTypeCombo(int Szemely, JComboBox typeCombo) {
        Vagyon OsszesVagyon = new Vagyon();
        OsszesVagyon.setVagyonTipus("Mind");
        ArrayList<Vagyon> vagyonLista = null;

        if (Szemely != 0) {

            vagyonLista = VagyonDB.getVagyonAndTipusParam2(Szemely);
            vagyonLista.add(OsszesVagyon);
            for (int i = 0; i < vagyonLista.size(); i++) {
                typeCombo.addItem(vagyonLista.get(i).getVagyonTipus());
            }
        }
        if (Szemely == 0) {
            vagyonLista = VagyonDB.getVagyonAndTipusParam3();
            vagyonLista.add(OsszesVagyon);
            for (int i = 0; i < vagyonLista.size(); i++) {
                typeCombo.addItem(vagyonLista.get(i).getVagyonTipus());
            }

        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Táblázat készítése">      
    public void aktEszkTableLoad() {
        chooseDate = model.getValue().toLocalDate();
        chooseDate2 = model2.getValue().toLocalDate();
        if (akteszkCombo.getSelectedItem().equals("Mind") && szemelyCombo.getSelectedItem().equals("Mind")) {
            VagyonTranzakcio = VagyonDB.getTranzakcioAktEszk("%", chooseDate, chooseDate2, 1, "%");
            VagyonTranzakcio2 = VagyonDB.getTranzakcioAktEszk("%", chooseDate, chooseDate2, 2, "%");
            VagyonTranzakcio3 = VagyonDB.getTranzakcioAktEszk("%", chooseDate, chooseDate2, 3, "%");
            VagyonTranzakcio4 = VagyonDB.getTranzakcioAktEszk2("%", chooseDate, chooseDate2, 3, "%");

        }
        if (!akteszkCombo.getSelectedItem().equals("Mind") && szemelyCombo.getSelectedItem().equals("Mind")) {
            VagyonTranzakcio = VagyonDB.getTranzakcioAktEszk((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 1, "%");
            VagyonTranzakcio2 = VagyonDB.getTranzakcioAktEszk((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 2, "%");
            VagyonTranzakcio3 = VagyonDB.getTranzakcioAktEszk((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 3, "%");
            VagyonTranzakcio4 = VagyonDB.getTranzakcioAktEszk2((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 3, "%");
        }

        if (akteszkCombo.getSelectedItem().equals("Mind") && !szemelyCombo.getSelectedItem().equals("Mind")) {
            VagyonTranzakcio = VagyonDB.getTranzakcioAktEszk("%", chooseDate, chooseDate2, 1, szemelyCombo.getSelectedItem().toString());
            VagyonTranzakcio2 = VagyonDB.getTranzakcioAktEszk("%", chooseDate, chooseDate2, 2, szemelyCombo.getSelectedItem().toString());
            VagyonTranzakcio3 = VagyonDB.getTranzakcioAktEszk("%", chooseDate, chooseDate2, 3, szemelyCombo.getSelectedItem().toString());
            VagyonTranzakcio4 = VagyonDB.getTranzakcioAktEszk2("%", chooseDate, chooseDate2, 3, szemelyCombo.getSelectedItem().toString());
        }

        if (!akteszkCombo.getSelectedItem().equals("Mind") && !szemelyCombo.getSelectedItem().equals("Mind")) {
            VagyonTranzakcio = VagyonDB.getTranzakcioAktEszk((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 1, szemelyCombo.getSelectedItem().toString());
            VagyonTranzakcio2 = VagyonDB.getTranzakcioAktEszk((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 2, szemelyCombo.getSelectedItem().toString());
            VagyonTranzakcio3 = VagyonDB.getTranzakcioAktEszk((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 3, szemelyCombo.getSelectedItem().toString());
            VagyonTranzakcio4 = VagyonDB.getTranzakcioAktEszk2((String) akteszkCombo.getSelectedItem(), chooseDate, chooseDate2, 3, szemelyCombo.getSelectedItem().toString());
        }

        Object[] row = new Object[6];
        ModelTable.setRowCount(0);
        try {
            for (int i = 0; i < VagyonTranzakcio.size(); i++) {
                row[0] = VagyonTranzakcio.get(i).getNev();
                row[1] = VagyonTranzakcio.get(i).getOsszeg();
                row[2] = VagyonTranzakcio.get(i).getPenzTipus();
                row[3] = VagyonTranzakcio.get(i).getDatum();
                row[4] = VagyonTranzakcio.get(i).getSzemelyNev1();
                row[5] = VagyonTranzakcio.get(i).getVagyonNev1();
                ModelTable.addRow(row);
            }
        } catch (Exception e) {
        }
        try {
            for (int i = 0; i < VagyonTranzakcio2.size(); i++) {

                row[0] = VagyonTranzakcio2.get(i).getNev();
                row[1] = VagyonTranzakcio2.get(i).getOsszeg() * -1;
                row[2] = VagyonTranzakcio2.get(i).getPenzTipus();
                row[3] = VagyonTranzakcio2.get(i).getDatum();
                row[4] = VagyonTranzakcio2.get(i).getSzemelyNev1();
                row[5] = VagyonTranzakcio2.get(i).getVagyonNev1();
                ModelTable.addRow(row);
            }
        } catch (Exception e) {
        }
        try {
            for (int i = 0; i < VagyonTranzakcio3.size(); i++) {

                row[0] = VagyonTranzakcio3.get(i).getNev();
                row[1] = VagyonTranzakcio3.get(i).getOsszeg();
                row[2] = VagyonTranzakcio3.get(i).getPenzTipus();
                row[3] = VagyonTranzakcio3.get(i).getDatum();
                row[4] = VagyonTranzakcio3.get(i).getSzemelyNev1();
                row[5] = VagyonTranzakcio3.get(i).getVagyonNev1();
                ModelTable.addRow(row);
            }
        } catch (Exception e) {
        }
        try {
            for (int i = 0; i < VagyonTranzakcio4.size(); i++) {

                row[0] = VagyonTranzakcio4.get(i).getNev();
                row[1] = VagyonTranzakcio4.get(i).getOsszeg() * -1;
                row[2] = VagyonTranzakcio4.get(i).getPenzTipus();
                row[3] = VagyonTranzakcio4.get(i).getDatum();
                row[4] = VagyonTranzakcio4.get(i).getSzemelyNev1();
                row[5] = VagyonTranzakcio4.get(i).getVagyonNev1();
                ModelTable.addRow(row);
            }
        } catch (Exception e) {
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">  
    public AktEszkPanel() {
        this.setLayout((new BorderLayout()));
        CustomFormat CustomFormat = new CustomFormat();
        JDatePanelImpl panle = new JDatePanelImpl(model, Properties);
        JDatePanelImpl panle2 = new JDatePanelImpl(model2, Properties);
        model.setSelected(true);
        model2.setSelected(true);
        szemelyCombo.addActionListener(this);
        keres.addActionListener(this);
        keres.setText("Lekérdezés");
        loadUserCombo(szemelyCombo);
        datePickerFrom = new JDatePickerImpl(panle, CustomFormat);
        datePickerTo = new JDatePickerImpl(panle2, CustomFormat);
        GridBagLayout gbl = new GridBagLayout();
        keresFeltetel.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        keresFeltetel.add(idoszak, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        keresFeltetel.add(datePickerFrom, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        keresFeltetel.add(datePickerTo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        keresFeltetel.add(tulajdonos, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        keresFeltetel.add(szemelyCombo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        keresFeltetel.add(akteszklabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        keresFeltetel.add(akteszkCombo, gbc);
        keresPanel.add(keres);
        feltetelPanel.add(keresFeltetel);
        feltetelPanel.add(keresPanel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        feltetelPanel.setLayout(new GridLayout(2, 1));
        AktEszkTable.setFocusable(false);
        AktEszkTable.setSelectionBackground(Color.lightGray);
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(0).setHeaderValue("Vagyon");
        AktEszkTable.getColumnModel().getColumn(0).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(0).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(0).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(0).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
        AktEszkTable.getColumnModel().getColumn(1).setHeaderValue("Összeg");
        AktEszkTable.getColumnModel().getColumn(1).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(1).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(1).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(1).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(2).setHeaderValue("Pénznem");
        AktEszkTable.getColumnModel().getColumn(2).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(2).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(2).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(2).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(3).setHeaderValue("Dátum");
        AktEszkTable.getColumnModel().getColumn(3).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(3).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(3).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(3).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(4).setHeaderValue("Személy");
        AktEszkTable.getColumnModel().getColumn(4).setMinWidth(30);
        AktEszkTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(4).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(4).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(4).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(5).setHeaderValue("Vagyoni eszköz");
        AktEszkTable.getColumnModel().getColumn(5).setMinWidth(30);
        AktEszkTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(5).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(5).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(5).sizeWidthToFit();
        AktEszkTable.getTableHeader().resizeAndRepaint();
        AktEszkTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        AktEszkTable.setShowVerticalLines(false);
        JScrollPane scrollPaneAktEszk = new JScrollPane(AktEszkTable);
        this.add(feltetelPanel, BorderLayout.NORTH);
        scrollPaneAktEszk.setBorder(BorderFactory.createEmptyBorder(10, 150, 150, 150));
        this.add(scrollPaneAktEszk, BorderLayout.CENTER);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés">  
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == szemelyCombo) {
            akteszkCombo.removeAllItems();
            if (szemelyCombo.getSelectedItem().equals("Mind")) {
                loadGoodsTypeCombo(0, akteszkCombo);
            } else if (!szemelyCombo.getSelectedItem().equals("Mind")) {
                loadGoodsTypeCombo(VagyonDB.getSzemelyNev((String) szemelyCombo.getSelectedItem()).get(0).getId(), akteszkCombo);
            }
        }
        if (e.getSource() == keres) {
            aktEszkTableLoad();
        }
    }
    //</editor-fold>
}
