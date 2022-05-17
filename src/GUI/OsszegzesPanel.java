/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Penznem;
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
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilCalendarModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author HorvathAttila OsszegzesPanel osztály létrehozása, panel felépítése,
 * eseménykezelés
 */
public class OsszegzesPanel extends JPanel implements ActionListener {

    JFrame teszt = new JFrame();
    JDatePickerImpl datePicker;
    SqlDateModel model = new SqlDateModel();
    Properties Properties = new Properties();
    CustomFormat CustomFormat = new CustomFormat();
    DB database = new DB();
    DB dbBevetel = new DB();
    JPanel tablesPanel = new JPanel();
    JPanel aktivEszkozPanel = new JPanel();
    JPanel befektetesPanel = new JPanel();
    JPanel hitelPanel = new JPanel();
    JPanel vagyotargyPanel = new JPanel();
    JPanel diagramPanel = new JPanel();
    JPanel datePanel = new JPanel();
    String col[] = {"a", "b", "c", "d"};
    JTable AktEszkTable = new JTable(0, 4);
    JTable BefektetTable = new JTable(0, 4);
    JTable HitelekTable = new JTable(0, 4);
    JTable VagyonTable = new JTable(0, 4);
    Vagyon VagyonInstance = new Vagyon();
    ArrayList<Vagyon> AktivEszkozok = null;
    ArrayList<Vagyon> Befektetesek = null;
    ArrayList<Vagyon> Hitelek = null;
    ArrayList<Vagyon> Vagyonok = null;
    ArrayList<Tranzakcio> TranzakcioBev = null;
    ArrayList<Tranzakcio> TranzakcioBev2 = null;
    ArrayList<Tranzakcio> TranzakcioKiad = null;
    JDatePanelImpl panle = new JDatePanelImpl(model, Properties);
    LocalDate chooseDate;

    String VagyonParam;
    int Bev;
    int AktBev;
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
    int x;
    int y;
    JComboBox currencyComboField = new JComboBox();
    DefaultTableModel ModelTable = (DefaultTableModel) AktEszkTable.getModel();
    DefaultTableModel ModelTable2 = (DefaultTableModel) BefektetTable.getModel();
    DefaultTableModel ModelTable3 = (DefaultTableModel) HitelekTable.getModel();
    DefaultTableModel ModelTable4 = (DefaultTableModel) VagyonTable.getModel();

    JFreeChart barChart = ChartFactory.createBarChart(
            "Havi egyenleg",
            "Bevétel",
            "Kiadás",
            createDataset(),
            PlotOrientation.HORIZONTAL,
            true, true, false);
    ChartPanel chartPanel = new ChartPanel(barChart);

    JFreeChart barChartTervTeny = ChartFactory.createBarChart(
            "Havi terv/tény egyenleg",
            "Bevétel",
            "Tervezett bevétel",
            createDatasetTervTeny(),
            PlotOrientation.HORIZONTAL,
            true, true, false);
    ChartPanel chartPanel2 = new ChartPanel(barChartTervTeny);
    DefaultCategoryDataset dataset;
    DefaultCategoryDataset dataset2;

    //<editor-fold defaultstate="collapsed" desc="Komponens adatfeltöltés metódus"> 
    public void loadCurrencyCombo(JComboBox CurrencyCombo) {
        ArrayList<Penznem> penznemLista = null;
        Penznem Cur = new Penznem();
        Cur.setName("PÉNZNEM");
        Cur.setHUFRatio((float) 1.0);
        penznemLista = database.getPenznem();
        penznemLista.add(Cur);
        for (int i = 0; i < penznemLista.size(); i++) {
            CurrencyCombo.addItem(penznemLista.get(i).getName());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Táblázat készítés metódusok"> 
    public void refreshTableAktEszk() {
        x = model.getMonth() + 1;
        y = model.getYear();
        chooseDate = model.getValue().toLocalDate();
        AktivEszkozok = database.getVagyonParam2("1", chooseDate);
        Object[] row = new Object[4];
        ModelTable.setRowCount(0);
        for (int i = 0; i < AktivEszkozok.size(); i++) {
            LocalDate ld = model.getValue().toLocalDate();
            AktBev = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioBalancedatum(1, AktivEszkozok.get(i).getVID(), ld);
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                AktBev = TranzakcioBev2.get(k).getSumma();
            }
            AktKiad = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioBalancedatum(2, AktivEszkozok.get(i).getVID(), ld);
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                AktKiad = TranzakcioBev2.get(k).getSumma();
            }
            AktAtvez = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioBalancedatum(3, AktivEszkozok.get(i).getVID(), ld);
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                AktAtvez = TranzakcioBev2.get(k).getSumma();
            }
            AktAtvezLevon = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioBalancedatum2(3, AktivEszkozok.get(i).getVID(), ld);
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                AktAtvezLevon = TranzakcioBev2.get(k).getSumma();
            }
            TranzakcioBev2 = dbBevetel.getTranzakcioFutureValue3(5, AktivEszkozok.get(i).getVID());
            VarhatoVagytargyBev = 0;
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                VarhatoVagytargyBev = VarhatoVagytargyBev + TranzakcioBev2.get(k).getPresentValue(AktivEszkozok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            VarhatoVagytargyKiad = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioFutureValue3(6, AktivEszkozok.get(i).getVID());
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                VarhatoVagytargyKiad = VarhatoVagytargyKiad + TranzakcioBev2.get(k).getPresentValue(AktivEszkozok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            VarhatoBefektetesBev = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioFutureValue3(7, AktivEszkozok.get(i).getVID());
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                VarhatoBefektetesBev = VarhatoBefektetesBev + TranzakcioBev2.get(k).getPresentValue(AktivEszkozok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            VarhatoBefektetesKiad = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioFutureValue3(8, AktivEszkozok.get(i).getVID());
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                VarhatoBefektetesKiad = VarhatoBefektetesKiad + TranzakcioBev2.get(k).getPresentValue(AktivEszkozok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            VarhatoHitelKiad = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioFutureValue3(10, AktivEszkozok.get(i).getVID());
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                VarhatoHitelKiad = VarhatoHitelKiad + TranzakcioBev2.get(k).getPresentValue(AktivEszkozok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            VarhatoEgyebBev = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioFutureValue2(11, AktivEszkozok.get(i).getVID());
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                VarhatoEgyebBev = VarhatoEgyebBev + TranzakcioBev2.get(k).getPresentValue(AktivEszkozok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            VarhatoEgyebKiad = 0;
            TranzakcioBev2 = dbBevetel.getTranzakcioFutureValue2(12, AktivEszkozok.get(i).getVID());
            for (int k = 0; k < TranzakcioBev2.size(); k++) {
                VarhatoEgyebKiad = VarhatoEgyebKiad + TranzakcioBev2.get(k).getPresentValue(AktivEszkozok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            row[0] = AktivEszkozok.get(i).getNev();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[1] = AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad;
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) AktivEszkozok.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = AktivEszkozok.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem();
            }
            row[3] = AktivEszkozok.get(i).getSzemelyNev();
            ModelTable.addRow(row);
        }
    }

    public void refreshTableBefektetes() {
        x = model.getMonth() + 1;
        y = model.getYear();
        chooseDate = model.getValue().toLocalDate();
        Befektetesek = database.getVagyonParam2("2", chooseDate);
        Object[] row = new Object[4];
        ModelTable2.setRowCount(0);
        for (int i = 0; i < Befektetesek.size(); i++) {
            LocalDate ld = model.getValue().toLocalDate();
            Bev = 0;
            Kiad = 0;
            row[0] = Befektetesek.get(i).getNev();
            AktAtvez = 0;
            TranzakcioBev = dbBevetel.getTranzakcioBalancedatum(3, Befektetesek.get(i).getVID(), ld);
            for (int k = 0; k < TranzakcioBev.size(); k++) {
                AktAtvez = TranzakcioBev.get(k).getSumma();
            }
            AktAtvezLevon = 0;
            TranzakcioBev = dbBevetel.getTranzakcioBalancedatum2(3, Befektetesek.get(i).getVID(), ld);
            for (int k = 0; k < TranzakcioBev.size(); k++) {

                AktAtvezLevon = TranzakcioBev.get(k).getSumma();
            }
            TranzakcioBev = dbBevetel.getTranzakcioFutureValue3(7, Befektetesek.get(i).getVID());
            for (int k = 0; k < TranzakcioBev.size(); k++) {
                Bev = Bev + TranzakcioBev.get(k).getPresentValue(Befektetesek.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            TranzakcioKiad = dbBevetel.getTranzakcioFutureValue3(8, Befektetesek.get(i).getVID());

            for (int k = 0; k < TranzakcioKiad.size(); k++) {
                Kiad = Kiad + TranzakcioKiad.get(k).getPresentValue(Befektetesek.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[1] = Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad;
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = Befektetesek.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem();
            }
            row[3] = Befektetesek.get(i).getSzemelyNev();
            ModelTable2.addRow(row);

        }
    }

    public void refreshTableHitel() {
        x = model.getMonth() + 1;
        y = model.getYear();
        chooseDate = model.getValue().toLocalDate();
        Hitelek = database.getVagyonParam2("3", chooseDate);
        Object[] row = new Object[4];
        ModelTable3.setRowCount(0);
        for (int i = 0; i < Hitelek.size(); i++) {
            Kiad = 0;
            row[0] = Hitelek.get(i).getNev();
            TranzakcioBev = dbBevetel.getTranzakcioFutureValue2(9, Hitelek.get(i).getVID());
            for (int k = 0; k < TranzakcioBev.size(); k++) {
                Kiad = TranzakcioBev.get(k).getPresentValue(Hitelek.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[1] = Hitelek.get(i).getNyitoEgyenleg() - Kiad;
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Hitelek.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (Hitelek.get(i).getNyitoEgyenleg() - Kiad) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = Hitelek.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem();
            }
            row[3] = Hitelek.get(i).getSzemelyNev();
            ModelTable3.addRow(row);
        }
    }

    public void refreshTableVagyon() {
        x = model.getMonth() + 1;
        y = model.getYear();
        chooseDate = model.getValue().toLocalDate();
        Vagyonok = database.getVagyonParam2("4", chooseDate);
        Object[] row = new Object[4];
        ModelTable4.setRowCount(0);
        for (int i = 0; i < Vagyonok.size(); i++) {
            Bev = 0;
            Kiad = 0;
            row[0] = Vagyonok.get(i).getNev();
            TranzakcioBev = dbBevetel.getTranzakcioFutureValue2(4, Vagyonok.get(i).getVID());
            for (int k = 0; k < TranzakcioBev.size(); k++) {
                Bev = TranzakcioBev.get(k).getPresentValue(Vagyonok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[1] = Vagyonok.get(i).getNyitoEgyenleg() + Bev;
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Vagyonok.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (Vagyonok.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = Vagyonok.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem();
            }
            row[3] = Vagyonok.get(i).getSzemelyNev();
            ModelTable4.addRow(row);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Diagram adatsor metódusok"> 
    private CategoryDataset createDataset() {

        final String bevetel = "Bevétel";
        final String kiadas = "Kiadás";
        final String haviegyenleg = "";
        dataset = new DefaultCategoryDataset();
        x = model.getMonth() + 1;
        y = model.getYear();
        ArrayList<Tranzakcio> trlekerd = null;
        trlekerd = database.getTranzakcioSum(1, x, y);
        for (int i = 0; i < trlekerd.size(); i++) {
            dataset.addValue(trlekerd.get(i).getSumma(), bevetel, haviegyenleg);
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                dataset.addValue(trlekerd.get(i).getSumma(), bevetel, haviegyenleg);
            } else if (currencyComboField.getItemCount() > 0) {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                dataset.addValue(trlekerd.get(i).getSumma() * (RatioAmit / RatioAmire), bevetel, haviegyenleg);
            }
        }
        trlekerd = database.getTranzakcioSum(2, x, y);
        for (int i = 0; i < trlekerd.size(); i++) {
            dataset.addValue(trlekerd.get(i).getSumma(), kiadas, haviegyenleg);
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                dataset.addValue(trlekerd.get(i).getSumma(), kiadas, haviegyenleg);
            } else if (currencyComboField.getItemCount() > 0) {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                dataset.addValue(trlekerd.get(i).getSumma() * (RatioAmit / RatioAmire), kiadas, haviegyenleg);
            }
        }
        return dataset;
    }

    private CategoryDataset createDatasetTervTeny() {
        final String bevetel = "Bevétel";
        final String beveteltervezett = "Tervezett Bevétel";
        final String kiadas = "Kiadás";
        final String kiadastervezett = "Tervezett Kiadás";
        final String haviegyenlegtervteny = "";
        dataset2 = new DefaultCategoryDataset();
        x = model.getMonth() + 1;
        y = model.getYear();
        ArrayList<Tranzakcio> trlekerd = null;
        ArrayList<Tranzakcio> trlekerdresz1 = null;
        ArrayList<Tranzakcio> trlekerdresz2 = null;
        ArrayList<Tranzakcio> trlekerdresz3 = null;
        ArrayList<Tranzakcio> trlekerdresz4 = null;
        int summa = 0;
        int summa1 = 0;
        int summa2 = 0;
        int summakiad = 0;
        int summakiad1 = 0;
        int summakiad2 = 0;
        int summakiad3 = 0;
        trlekerd = database.getTranzakcioSum(1, x, y);
        for (int i = 0; i < trlekerd.size(); i++) {
            dataset2.addValue(trlekerd.get(i).getSumma(), bevetel, haviegyenlegtervteny);
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                dataset2.addValue(trlekerd.get(i).getSumma(), bevetel, haviegyenlegtervteny);
            } else if (currencyComboField.getItemCount() > 0) {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                dataset2.addValue(trlekerd.get(i).getSumma() * (RatioAmit / RatioAmire), bevetel, haviegyenlegtervteny);
            }
        }
        trlekerdresz1 = database.getTranzakcioSumExpectedIn(5, x, y);
        for (int i = 0; i < trlekerdresz1.size(); i++) {
            summa = summa + trlekerdresz1.get(i).getSumma();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                summa = 0;
                summa = summa + trlekerdresz1.get(i).getSumma();
            } else if (currencyComboField.getItemCount() > 0) {
                summa = 0;
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                summa = (int) (summa + trlekerdresz1.get(i).getSumma() * (RatioAmit / RatioAmire));
            }
        }
        trlekerdresz2 = database.getTranzakcioSumExpectedIn(7, x, y);
        for (int i = 0; i < trlekerdresz2.size(); i++) {
            summa1 = summa1 + trlekerdresz2.get(i).getSumma();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                summa1 = 0;
                summa1 = summa1 + trlekerdresz2.get(i).getSumma();
            } else if (currencyComboField.getItemCount() > 0) {
                summa1 = 0;
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                summa1 = (int) (summa1 + trlekerdresz2.get(i).getSumma() * (RatioAmit / RatioAmire));
            }
        }
        trlekerdresz3 = database.getTranzakcioSumExpectedIn(11, x, y);
        for (int i = 0; i < trlekerdresz3.size(); i++) {
            summa2 = summa2 + trlekerdresz3.get(i).getSumma();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                summa2 = 0;
                summa2 = summa2 + trlekerdresz3.get(i).getSumma();
            } else if (currencyComboField.getItemCount() > 0) {
                summa2 = 0;
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                summa2 = (int) (summa2 + trlekerdresz3.get(i).getSumma() * (RatioAmit / RatioAmire));
            }
        }
        summa = summa + summa1 + summa2;
        dataset2.addValue(summa, beveteltervezett, haviegyenlegtervteny);
        trlekerd = database.getTranzakcioSum(2, x, y);
        for (int i = 0; i < trlekerd.size(); i++) {
            dataset2.addValue(trlekerd.get(i).getSumma(), kiadas, haviegyenlegtervteny);
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                dataset2.addValue(trlekerd.get(i).getSumma(), kiadas, haviegyenlegtervteny);
            } else if (currencyComboField.getItemCount() > 0) {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                dataset2.addValue(trlekerd.get(i).getSumma() * (RatioAmit / RatioAmire), kiadas, haviegyenlegtervteny);
            }
        }
        trlekerdresz1 = database.getTranzakcioSumExpectedIn(6, x, y);
        for (int i = 0; i < trlekerdresz1.size(); i++) {
            summakiad = summakiad + trlekerdresz1.get(i).getSumma();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                summakiad = 0;
                summakiad = summakiad + trlekerdresz1.get(i).getSumma();
            } else if (currencyComboField.getItemCount() > 0) {
                summakiad = 0;
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                summakiad = (int) (summakiad + trlekerdresz1.get(i).getSumma() * (RatioAmit / RatioAmire));
            }
        }
        trlekerdresz2 = database.getTranzakcioSumExpectedIn(8, x, y);
        for (int i = 0; i < trlekerdresz2.size(); i++) {
            summakiad1 = summakiad1 + trlekerdresz2.get(i).getSumma();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                summakiad1 = 0;
                summakiad1 = summakiad1 + trlekerdresz2.get(i).getSumma();
            } else if (currencyComboField.getItemCount() > 0) {
                summakiad1 = 0;
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                summakiad1 = (int) (summakiad1 + trlekerdresz2.get(i).getSumma() * (RatioAmit / RatioAmire));
            }
        }
        trlekerdresz3 = database.getTranzakcioSumExpectedIn(10, x, y);
        for (int i = 0; i < trlekerdresz3.size(); i++) {
            summakiad2 = summakiad2 + trlekerdresz3.get(i).getSumma();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                summakiad2 = 0;
                summakiad2 = summakiad2 + trlekerdresz3.get(i).getSumma();
            } else if (currencyComboField.getItemCount() > 0) {
                summakiad2 = 0;
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                summakiad2 = (int) (summakiad2 + trlekerdresz3.get(i).getSumma() * (RatioAmit / RatioAmire));
            }
        }
        trlekerdresz4 = database.getTranzakcioSumExpectedIn(12, x, y);
        for (int i = 0; i < trlekerdresz4.size(); i++) {
            summakiad3 = summakiad3 + trlekerdresz4.get(i).getSumma();
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                summakiad3 = 0;
                summakiad3 = summakiad3 + trlekerdresz4.get(i).getSumma();
            } else if (currencyComboField.getItemCount() > 0) {
                summakiad3 = 0;
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                summakiad3 = (int) (summakiad3 + trlekerdresz4.get(i).getSumma() * (RatioAmit / RatioAmire));
            }
        }
        summakiad = summakiad + summakiad1 + summakiad2 + summakiad3;
        dataset2.addValue(summakiad, kiadastervezett, haviegyenlegtervteny);
        return dataset2;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adatsor, táblázat frissítés metódus"> 
    public void diagramRefresh() {
        createDatasetTervTeny();
        createDataset();
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        plot.setDataset(dataset);
        CategoryPlot plot2 = (CategoryPlot) barChartTervTeny.getPlot();
        plot2.setDataset(dataset2);
        refreshTableAktEszk();
        refreshTableBefektetes();
        refreshTableVagyon();
        refreshTableHitel();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">   
    public OsszegzesPanel() {
        this.setLayout((new BorderLayout()));
        JLabel akteszk = new JLabel("Aktív eszközök");
        akteszk.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
        aktivEszkozPanel.setLayout(new BorderLayout());
        AktEszkTable.setFocusable(false);
        AktEszkTable.setSelectionBackground(Color.lightGray);
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 11));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        AktEszkTable.getColumnModel().getColumn(0).setHeaderValue("Vagyon");
        AktEszkTable.getColumnModel().getColumn(0).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(0).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(0).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(0).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 11));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        AktEszkTable.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
        AktEszkTable.getColumnModel().getColumn(1).setHeaderValue("Összeg");
        AktEszkTable.getColumnModel().getColumn(1).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(1).setMaxWidth(AktEszkTable.getColumnModel().getColumn(1).getWidth());
        AktEszkTable.getColumnModel().getColumn(1).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(1).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(1).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 11));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        AktEszkTable.getColumnModel().getColumn(2).setHeaderValue("Pénz");
        AktEszkTable.getColumnModel().getColumn(2).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(2).setMaxWidth(AktEszkTable.getColumnModel().getColumn(2).getWidth());
        AktEszkTable.getColumnModel().getColumn(2).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(2).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(2).sizeWidthToFit();
        AktEszkTable.setFont(new Font("Arial", Font.BOLD, 11));
        AktEszkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        AktEszkTable.getColumnModel().getColumn(3).setHeaderValue("Tulajdonos");
        AktEszkTable.getColumnModel().getColumn(3).setMinWidth(5);
        AktEszkTable.getColumnModel().getColumn(3).setPreferredWidth(AktEszkTable.getColumnModel().getColumn(3).getWidth() + 1);
        AktEszkTable.getColumnModel().getColumn(3).sizeWidthToFit();
        AktEszkTable.getTableHeader().resizeAndRepaint();
        AktEszkTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        AktEszkTable.setShowVerticalLines(false);
        JScrollPane scrollPaneAktEszk = new JScrollPane(AktEszkTable);
        aktivEszkozPanel.add(akteszk, BorderLayout.PAGE_START);
        aktivEszkozPanel.add(scrollPaneAktEszk, BorderLayout.CENTER);
        JLabel befekt = new JLabel("Befektetések");
        befekt.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
        befektetesPanel.setLayout(new BorderLayout());
        BefektetTable.setFocusable(false);
        BefektetTable.setSelectionBackground(Color.lightGray);
        BefektetTable.setFont(new Font("Arial", Font.BOLD, 11));
        BefektetTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        BefektetTable.getColumnModel().getColumn(0).setHeaderValue("Vagyon");
        BefektetTable.getColumnModel().getColumn(0).setMinWidth(5);
        BefektetTable.getColumnModel().getColumn(0).setPreferredWidth(BefektetTable.getColumnModel().getColumn(0).getWidth() + 1);
        BefektetTable.getColumnModel().getColumn(0).sizeWidthToFit();
        BefektetTable.setFont(new Font("Arial", Font.BOLD, 11));
        BefektetTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        BefektetTable.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
        BefektetTable.getColumnModel().getColumn(1).setHeaderValue("Összeg");
        BefektetTable.getColumnModel().getColumn(1).setMinWidth(5);
        BefektetTable.getColumnModel().getColumn(1).setMaxWidth(BefektetTable.getColumnModel().getColumn(1).getWidth());
        BefektetTable.getColumnModel().getColumn(1).setPreferredWidth(BefektetTable.getColumnModel().getColumn(1).getWidth() + 1);
        BefektetTable.getColumnModel().getColumn(1).sizeWidthToFit();
        BefektetTable.setFont(new Font("Arial", Font.BOLD, 11));
        BefektetTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        BefektetTable.getColumnModel().getColumn(2).setHeaderValue("Pénz");
        BefektetTable.getColumnModel().getColumn(2).setMinWidth(5);
        BefektetTable.getColumnModel().getColumn(2).setMaxWidth(BefektetTable.getColumnModel().getColumn(2).getWidth());
        BefektetTable.getColumnModel().getColumn(2).setPreferredWidth(BefektetTable.getColumnModel().getColumn(2).getWidth() + 1);
        BefektetTable.getColumnModel().getColumn(2).sizeWidthToFit();
        BefektetTable.setFont(new Font("Arial", Font.BOLD, 11));
        BefektetTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        BefektetTable.getColumnModel().getColumn(3).setHeaderValue("Tulajdonos");
        BefektetTable.getColumnModel().getColumn(3).setMinWidth(5);
        BefektetTable.getColumnModel().getColumn(3).setPreferredWidth(BefektetTable.getColumnModel().getColumn(3).getWidth() + 1);
        BefektetTable.getColumnModel().getColumn(3).sizeWidthToFit();
        BefektetTable.getTableHeader().resizeAndRepaint();
        BefektetTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        BefektetTable.setShowVerticalLines(false);
        JScrollPane scrollPaneBefektet = new JScrollPane(BefektetTable);
        befektetesPanel.add(befekt, BorderLayout.PAGE_START);
        befektetesPanel.add(scrollPaneBefektet, BorderLayout.CENTER);
        JLabel hitel = new JLabel("Hitelek");
        hitel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
        hitelPanel.setLayout(new BorderLayout());
        HitelekTable.setFocusable(false);
        HitelekTable.setSelectionBackground(Color.lightGray);
        HitelekTable.setFont(new Font("Arial", Font.BOLD, 11));
        HitelekTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        HitelekTable.getColumnModel().getColumn(0).setHeaderValue("Vagyon");
        HitelekTable.getColumnModel().getColumn(0).setMinWidth(5);
        HitelekTable.getColumnModel().getColumn(0).setPreferredWidth(HitelekTable.getColumnModel().getColumn(0).getWidth() + 1);
        HitelekTable.getColumnModel().getColumn(0).sizeWidthToFit();
        HitelekTable.setFont(new Font("Arial", Font.BOLD, 11));
        HitelekTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        HitelekTable.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
        HitelekTable.getColumnModel().getColumn(1).setHeaderValue("Összeg");
        HitelekTable.getColumnModel().getColumn(1).setMinWidth(5);
        HitelekTable.getColumnModel().getColumn(1).setMaxWidth(HitelekTable.getColumnModel().getColumn(1).getWidth());
        HitelekTable.getColumnModel().getColumn(1).setPreferredWidth(HitelekTable.getColumnModel().getColumn(1).getWidth() + 1);
        HitelekTable.getColumnModel().getColumn(1).sizeWidthToFit();
        HitelekTable.setFont(new Font("Arial", Font.BOLD, 11));
        HitelekTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        HitelekTable.getColumnModel().getColumn(2).setHeaderValue("Pénz");
        HitelekTable.getColumnModel().getColumn(2).setMinWidth(5);
        HitelekTable.getColumnModel().getColumn(2).setMaxWidth(HitelekTable.getColumnModel().getColumn(2).getWidth());
        HitelekTable.getColumnModel().getColumn(2).setPreferredWidth(HitelekTable.getColumnModel().getColumn(2).getWidth() + 1);
        HitelekTable.getColumnModel().getColumn(2).sizeWidthToFit();
        HitelekTable.setFont(new Font("Arial", Font.BOLD, 11));
        HitelekTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        HitelekTable.getColumnModel().getColumn(3).setHeaderValue("Tulajdonos");
        HitelekTable.getColumnModel().getColumn(3).setMinWidth(5);
        HitelekTable.getColumnModel().getColumn(3).setPreferredWidth(HitelekTable.getColumnModel().getColumn(3).getWidth() + 1);
        HitelekTable.getColumnModel().getColumn(3).sizeWidthToFit();
        HitelekTable.getTableHeader().resizeAndRepaint();
        HitelekTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        HitelekTable.setShowVerticalLines(false);
        JScrollPane scrollPaneHitel = new JScrollPane(HitelekTable);
        HitelekTable.setLayout(new BorderLayout());
        hitelPanel.add(hitel, BorderLayout.PAGE_START);
        hitelPanel.add(scrollPaneHitel, BorderLayout.CENTER);
        JLabel vagyon = new JLabel("Vagyontárgyak");
        vagyon.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
        VagyonTable.setFocusable(false);
        VagyonTable.setSelectionBackground(Color.lightGray);
        VagyonTable.setFont(new Font("Arial", Font.BOLD, 11));
        VagyonTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        VagyonTable.getColumnModel().getColumn(0).setHeaderValue("Vagyon");
        VagyonTable.getColumnModel().getColumn(0).setMinWidth(5);
        VagyonTable.getColumnModel().getColumn(0).setMaxWidth(VagyonTable.getColumnModel().getColumn(0).getWidth());
        VagyonTable.getColumnModel().getColumn(0).setPreferredWidth(VagyonTable.getColumnModel().getColumn(0).getWidth() + 1);
        VagyonTable.getColumnModel().getColumn(0).sizeWidthToFit();
        VagyonTable.setFont(new Font("Arial", Font.BOLD, 11));
        VagyonTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        VagyonTable.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
        VagyonTable.getColumnModel().getColumn(1).setHeaderValue("Összeg");
        VagyonTable.getColumnModel().getColumn(1).setMinWidth(5);
        VagyonTable.getColumnModel().getColumn(1).setMaxWidth(VagyonTable.getColumnModel().getColumn(1).getWidth());
        VagyonTable.getColumnModel().getColumn(1).setPreferredWidth(VagyonTable.getColumnModel().getColumn(1).getWidth() + 1);
        VagyonTable.getColumnModel().getColumn(1).sizeWidthToFit();
        VagyonTable.setFont(new Font("Arial", Font.BOLD, 11));
        VagyonTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        VagyonTable.getColumnModel().getColumn(2).setHeaderValue("Pénz");
        VagyonTable.getColumnModel().getColumn(2).setMinWidth(5);
        VagyonTable.getColumnModel().getColumn(2).setMaxWidth(VagyonTable.getColumnModel().getColumn(2).getWidth());
        VagyonTable.getColumnModel().getColumn(2).setPreferredWidth(VagyonTable.getColumnModel().getColumn(2).getWidth() + 1);
        VagyonTable.getColumnModel().getColumn(2).sizeWidthToFit();
        VagyonTable.setFont(new Font("Arial", Font.BOLD, 11));
        VagyonTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        VagyonTable.getColumnModel().getColumn(3).setHeaderValue("Tulajdonos");
        VagyonTable.getColumnModel().getColumn(3).setMinWidth(5);
        VagyonTable.getColumnModel().getColumn(3).setPreferredWidth(VagyonTable.getColumnModel().getColumn(3).getWidth() + 1);
        VagyonTable.getColumnModel().getColumn(3).sizeWidthToFit();
        VagyonTable.getTableHeader().resizeAndRepaint();
        VagyonTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        VagyonTable.setShowVerticalLines(false);
        JScrollPane scrollPaneVagyon = new JScrollPane(VagyonTable);
        vagyotargyPanel.setLayout(new BorderLayout());
        vagyotargyPanel.add(vagyon, BorderLayout.PAGE_START);
        vagyotargyPanel.add(scrollPaneVagyon, BorderLayout.CENTER);
        tablesPanel.add(aktivEszkozPanel);
        tablesPanel.add(befektetesPanel);
        tablesPanel.add(hitelPanel);
        tablesPanel.add(vagyotargyPanel);
        tablesPanel.setLayout(new GridLayout(4, 1));
        model.setSelected(true);
        datePicker = new JDatePickerImpl(panle, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
        datePicker.setAutoscrolls(true);
        loadCurrencyCombo(currencyComboField);
        currencyComboField.setSelectedIndex(currencyComboField.getItemCount() - 1);
        currencyComboField.addActionListener(this);
        datePanel.add(currencyComboField);
        datePanel.add(datePicker);
        panle.addActionListener(this);
        datePicker.addActionListener(this);
        datePicker.getJFormattedTextField().addActionListener(this);
        GridBagLayout gbl_rightPanel = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        diagramPanel.setLayout(gbl_rightPanel);
        GridBagConstraints gbc_algo = new GridBagConstraints();
        gbc_algo.fill = GridBagConstraints.BOTH;
        gbc_algo.insets = new Insets(0, 0, 5, 0);
        gbc_algo.gridx = 0;
        gbc_algo.gridy = 0;
        gbc_algo.weightx = 0;
        gbc_algo.weighty = 0;
        diagramPanel.add(datePanel, gbc_algo);
        gbc_algo.gridy = 1;
        gbc_algo.weightx = 1;
        gbc_algo.weighty = 1;
        diagramPanel.add(chartPanel, gbc_algo);
        gbc_algo.gridy = 2;
        gbc_algo.weightx = 1;
        gbc_algo.weighty = 1;
        diagramPanel.add(chartPanel2, gbc_algo);
        refreshTableAktEszk();
        refreshTableVagyon();
        refreshTableHitel();
        refreshTableBefektetes();
        tablesPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY));
        diagramPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY));
        this.add(tablesPanel, BorderLayout.WEST);
        this.add(diagramPanel, BorderLayout.CENTER);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés"> 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panle) {
            diagramRefresh();
        }
        if (e.getSource() == currencyComboField) {
            diagramRefresh();
        }
    }
    //</editor-fold>
}
