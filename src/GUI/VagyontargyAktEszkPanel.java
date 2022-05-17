/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Penznem;
import Controls.Szemely;
import Controls.Tranzakcio;
import Controls.Vagyon;
import Model.DB;
import java.awt.BorderLayout;
import java.awt.Color;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author HorvathAttila VagyontargyAktEszkPanel osztály létrehozása, panel
 * felépítése, eseménykezelés
 */
public class VagyontargyAktEszkPanel extends JPanel implements ActionListener {

    JTable AktEszkTable = new JTable(0, 7);
    DefaultTableModel ModelTable = (DefaultTableModel) AktEszkTable.getModel();
    DB VagyonDB = new DB();
    Vagyon VagyonInstance = new Vagyon();
    ArrayList<Vagyon> AktivEszkozok = null;
    JPanel keresFeltetel = new JPanel();
    JPanel keresPanel = new JPanel();
    JPanel feltetelPanel = new JPanel();
    JPanel aktivEszkozPanel = new JPanel();
    JPanel lekerdezPanel = new JPanel();
    JDatePickerImpl datePickerFrom;
    JDatePickerImpl datePickerTo;
    SqlDateModel model = new SqlDateModel();
    SqlDateModel model2 = new SqlDateModel();
    Properties Properties = new Properties();
    JLabel idoszak = new JLabel("Időszak: ");
    JLabel akteszk = new JLabel("Aktív vagyoni eszközök: ");
    JLabel tulajdonos = new JLabel("Személy: ");
    JLabel akteszklabel = new JLabel("Vagyontárgyak: ");
    JLabel penznem = new JLabel("Pénznem: ");
    JComboBox szemelyCombo = new JComboBox();
    JComboBox akteszkCombo = new JComboBox();
    JComboBox goodsComboFieldAdd = new JComboBox();
    JButton keres = new JButton();
    LocalDate chooseDate;
    LocalDate chooseDate2;
    ArrayList<Tranzakcio> VagyonTranzakcio;
    ArrayList<Tranzakcio> VagyonTranzakcio2;
    ArrayList<Tranzakcio> VagyonTranzakcio3;
    ArrayList<Tranzakcio> VagyonTranzakcio4;
    ArrayList<Tranzakcio> TranzakcioBev = null;
    ArrayList<Tranzakcio> TranzakcioBev2 = null;
    ArrayList<Tranzakcio> TranzakcioKiad = null;
    ArrayList<Vagyon> Befektetesek = null;
    ArrayList<Vagyon> Befektetesek2 = null;
    JComboBox currencyComboField = new JComboBox();
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    int Bev;
    float AktBev;
    int AktKiad;
    int AktAtvez;
    int AktAtvezLevon;
    int VarhatoVagytargyBev;
    int VarhatoVagytargyKiad;
    int VarhatoBefektetesBev;
    int VarhatoBefektetesKiad;
    int VarhatoHitelKiad;
    int VarhatoEgyebBev;
    int VarhatoEgyebKiad;
    int Kiad;
    int Bev2;
    int Kiad2;
    float FirstPrice;
    float SecondPrice;

    JFreeChart lineChart = ChartFactory.createLineChart(
            "Vagyontárgyak változása",
            "Bevétel",
            "Kiadás",
            createDataset(),
            PlotOrientation.VERTICAL,
            true, true, false);
    ChartPanel chartPanel = new ChartPanel(lineChart);

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

    public void loadCurrencyCombo(JComboBox CurrencyCombo) {

        ArrayList<Penznem> penznemLista = null;
        Penznem Cur = new Penznem();
        Cur.setName("PÉNZNEM");
        Cur.setHUFRatio((float) 1.0);
        penznemLista = VagyonDB.getPenznem();
        penznemLista.add(Cur);
        for (int i = 0; i < penznemLista.size(); i++) {
            CurrencyCombo.addItem(penznemLista.get(i).getName());
        }
    }

    public void loadGoodsCombo(String Vagyon, int Szemely, JComboBox typeCombo) {
        Vagyon OsszesVagyon = new Vagyon();
        OsszesVagyon.setNev("Mind");
        ArrayList<Vagyon> vagyonLista = null;
        if (Szemely != 0) {
            vagyonLista = VagyonDB.getVagyonParamNev(Vagyon, Szemely);
            vagyonLista.add(OsszesVagyon);
            for (int i = 0; i < vagyonLista.size(); i++) {
                typeCombo.addItem(vagyonLista.get(i).getNev());
            }
        }
        if (Szemely == 0) {
            vagyonLista = VagyonDB.getVagyonParam(Vagyon);
            vagyonLista.add(OsszesVagyon);
            for (int i = 0; i < vagyonLista.size(); i++) {
                typeCombo.addItem(vagyonLista.get(i).getNev());
            }
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

    //<editor-fold defaultstate="collapsed" desc="Diagram adatkészelet és frissítés metódus"> 
    private CategoryDataset createDataset() {
        model.setSelected(true);
        model2.setSelected(true);
        chooseDate = model.getValue().toLocalDate();
        chooseDate2 = model2.getValue().toLocalDate();

        try {
            if (goodsComboFieldAdd.getSelectedItem().equals("Mind") && szemelyCombo.getSelectedItem().equals("Mind")) {

                Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, "%", "%");

            }
        } catch (Exception e) {
        }
        try {
            if (!goodsComboFieldAdd.getSelectedItem().equals("Mind") && szemelyCombo.getSelectedItem().equals("Mind")) {

                Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, "%", goodsComboFieldAdd.getSelectedItem().toString());
            }
        } catch (Exception e) {
        }
        try {
            if (goodsComboFieldAdd.getSelectedItem().equals("Mind") && !szemelyCombo.getSelectedItem().equals("Mind")) {

                Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, szemelyCombo.getSelectedItem().toString(), "%");
            }
        } catch (Exception e) {
        }
        try {
            if (!goodsComboFieldAdd.getSelectedItem().equals("Mind") && !szemelyCombo.getSelectedItem().equals("Mind")) {

                Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, szemelyCombo.getSelectedItem().toString(), goodsComboFieldAdd.getSelectedItem().toString());
            }
        } catch (Exception e) {
        }
        try {
            for (int i = 0; i < Befektetesek.size(); i++) {
                Bev = 0;
                TranzakcioBev = VagyonDB.getTranzakcioFutureValue2(4, Befektetesek.get(i).getVID());
                for (int k = 0; k < TranzakcioBev.size(); k++) {
                    Bev = TranzakcioBev.get(k).getPresentValue(Befektetesek.get(i).getNyitoDatum(), chooseDate);
                }

                if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                    FirstPrice = Befektetesek.get(i).getNyitoEgyenleg() + Bev;
                } else {
                    float RatioAmire = VagyonDB.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    float RatioAmit = VagyonDB.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                    FirstPrice = (Befektetesek.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                }
                Bev = 0;
                TranzakcioBev = VagyonDB.getTranzakcioFutureValue2(4, Befektetesek.get(i).getVID());
                for (int k = 0; k < TranzakcioBev.size(); k++) {
                    Bev = TranzakcioBev.get(k).getPresentValue(Befektetesek.get(i).getNyitoDatum(), chooseDate2);
                }

                if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                    SecondPrice = Befektetesek.get(i).getNyitoEgyenleg() + Bev;
                } else {
                    float RatioAmire = VagyonDB.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    float RatioAmit = VagyonDB.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                    SecondPrice = (Befektetesek.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                }
                dataset.addValue(FirstPrice, Befektetesek.get(i).getNev(), model.getValue().toString());
                dataset.addValue(SecondPrice, Befektetesek.get(i).getNev(), model2.getValue().toString());
            }
        } catch (Exception e) {
        }
        return dataset;
    }

    public void diagramRefresh() {
        dataset.clear();
        createDataset();
        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        plot.setDataset(dataset);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Táblázat alkotás metódus">  
    public void aktEszkTableLoad() {
        model.setSelected(true);
        model2.setSelected(true);
        chooseDate = model.getValue().toLocalDate();
        chooseDate2 = model2.getValue().toLocalDate();
        if (goodsComboFieldAdd.getSelectedItem().equals("Mind") && szemelyCombo.getSelectedItem().equals("Mind")) {
            Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, "%", "%");
        }
        if (!goodsComboFieldAdd.getSelectedItem().equals("Mind") && szemelyCombo.getSelectedItem().equals("Mind")) {
            Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, "%", goodsComboFieldAdd.getSelectedItem().toString());
        }
        if (goodsComboFieldAdd.getSelectedItem().equals("Mind") && !szemelyCombo.getSelectedItem().equals("Mind")) {
            Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, szemelyCombo.getSelectedItem().toString(), "%");
        }
        if (!goodsComboFieldAdd.getSelectedItem().equals("Mind") && !szemelyCombo.getSelectedItem().equals("Mind")) {
            Befektetesek = VagyonDB.getVagyonParam3("4", chooseDate, szemelyCombo.getSelectedItem().toString(), goodsComboFieldAdd.getSelectedItem().toString());
        }
        Object[] row = new Object[7];
        ModelTable.setRowCount(0);
        for (int i = 0; i < Befektetesek.size(); i++) {
            Bev = 0;
            Kiad = 0;
            row[0] = Befektetesek.get(i).getNev();
            row[1] = model.getValue();
            Bev = 0;
            AktBev = 0;
            TranzakcioBev = VagyonDB.getTranzakcioFutureValue2(4, Befektetesek.get(i).getVID());
            for (int k = 0; k < TranzakcioBev.size(); k++) {
                Bev = TranzakcioBev.get(k).getPresentValue(Befektetesek.get(i).getNyitoDatum(), chooseDate);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = Befektetesek.get(i).getNyitoEgyenleg() + Bev;
            } else {
                float RatioAmire = VagyonDB.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = VagyonDB.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                AktBev = (Befektetesek.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                row[2] = (int) AktBev;
            }
            row[3] = model2.getValue();
            Bev = 0;
            AktBev = 0;
            TranzakcioBev = VagyonDB.getTranzakcioFutureValue2(4, Befektetesek.get(i).getVID());
            for (int k = 0; k < TranzakcioBev.size(); k++) {
                Bev = TranzakcioBev.get(k).getPresentValue(Befektetesek.get(i).getNyitoDatum(), chooseDate2);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[4] = Befektetesek.get(i).getNyitoEgyenleg() + Bev;
            } else {
                float RatioAmire = VagyonDB.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = VagyonDB.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                AktBev = (Befektetesek.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                row[4] = (int) AktBev;
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[5] = Befektetesek.get(i).getPenznemNev();
            } else {
                row[5] = currencyComboField.getSelectedItem();
            }
            row[6] = Befektetesek.get(i).getSzemelyNev();
            ModelTable.addRow(row);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus"> 
    public VagyontargyAktEszkPanel() {
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
        loadCurrencyCombo(currencyComboField);
        currencyComboField.setSelectedIndex(currencyComboField.getItemCount() - 1);
        datePickerFrom = new JDatePickerImpl(panle, CustomFormat);
        datePickerTo = new JDatePickerImpl(panle2, CustomFormat);
        GridBagLayout gbl = new GridBagLayout();
        keresFeltetel.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;//VERTICAL, HORIZONTAL
        gbc.gridx = 0;
        gbc.gridy = 0;
        keresFeltetel.add(idoszak, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        keresFeltetel.add(datePickerFrom, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        keresFeltetel.add(datePickerTo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        keresFeltetel.add(tulajdonos, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        keresFeltetel.add(szemelyCombo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        keresFeltetel.add(akteszklabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        keresFeltetel.add(goodsComboFieldAdd, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        keresFeltetel.add(penznem, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        keresFeltetel.add(currencyComboField, gbc);
        keresPanel.add(keres);
        feltetelPanel.add(keresFeltetel);
        feltetelPanel.add(keresPanel);
        feltetelPanel.setLayout(new GridLayout(2, 1));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        feltetelPanel.setLayout(new GridLayout(2, 1));
        AktEszkTable.setFocusable(false);
        AktEszkTable.setSelectionBackground(Color.lightGray);
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(0).setHeaderValue("Vagyontárgy");
        AktEszkTable.getColumnModel().getColumn(0).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(0).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(0).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(0).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
        AktEszkTable.getColumnModel().getColumn(1).setHeaderValue("Kezdő időpont");
        AktEszkTable.getColumnModel().getColumn(1).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(1).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(1).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(1).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(2).setHeaderValue("Összeg");
        AktEszkTable.getColumnModel().getColumn(2).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(2).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(2).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(2).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(3).setHeaderValue("Záró időpont");
        AktEszkTable.getColumnModel().getColumn(3).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(3).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(3).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(3).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(4).setHeaderValue("Összeg");
        AktEszkTable.getColumnModel().getColumn(4).setMinWidth(30);
        AktEszkTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(4).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(4).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(4).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(5).setHeaderValue("Pénznem");
        AktEszkTable.getColumnModel().getColumn(5).setMinWidth(30);
        AktEszkTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(5).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(5).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(5).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        AktEszkTable.getColumnModel().getColumn(6).setHeaderValue("Személy");
        AktEszkTable.getColumnModel().getColumn(6).setMinWidth(30);
        AktEszkTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        AktEszkTable.getColumnModel().getColumn(6).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(6).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(6).sizeWidthToFit();
        AktEszkTable.getTableHeader().resizeAndRepaint();
        AktEszkTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        AktEszkTable.setShowVerticalLines(false);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        AktEszkTable.setFocusable(false);
        AktEszkTable.setSelectionBackground(Color.lightGray);
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPaneAktEszk = new JScrollPane(AktEszkTable);
        lekerdezPanel.add(chartPanel);
        lekerdezPanel.add(scrollPaneAktEszk);
        lekerdezPanel.setLayout(new GridLayout(2, 1));
        lekerdezPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPaneAktEszk.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(lekerdezPanel, BorderLayout.CENTER);
        this.add(feltetelPanel, BorderLayout.WEST);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés">  
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == szemelyCombo) {
            goodsComboFieldAdd.removeAllItems();
            if (szemelyCombo.getSelectedItem().equals("Mind")) {
                //              loadGoodsTypeCombo(0, akteszkCombo);
                loadGoodsCombo("4", 0, goodsComboFieldAdd);
            } else if (!szemelyCombo.getSelectedItem().equals("Mind")) {
                //              loadGoodsTypeCombo(VagyonDB.getSzemelyNev((String) szemelyCombo.getSelectedItem()).get(0).getId(), akteszkCombo);
                loadGoodsCombo("4", VagyonDB.getSzemelyNev((String) szemelyCombo.getSelectedItem()).get(0).getId(), goodsComboFieldAdd);
            }
        }
        if (e.getSource() == keres) {
            aktEszkTableLoad();
            diagramRefresh();
        }
    }
//</editor-fold>
}
