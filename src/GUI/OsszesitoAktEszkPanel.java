/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Forma;
import Controls.Penznem;
import Controls.Szemely;
import Controls.Tranzakcio;
import Controls.Vagyon;
import Model.DB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author HorvathAttila
 */
public class OsszesitoAktEszkPanel extends JPanel implements ActionListener {

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
    JButton keres = new JButton("Lekérdezés");
    JComboBox currencyComboField = new JComboBox();
    JComboBox vagyonEszkCombo = new JComboBox();
    JComboBox szemelyCombo = new JComboBox();

    DefaultTableModel ModelTable = (DefaultTableModel) AktEszkTable.getModel();
    DefaultTableModel ModelTable2 = (DefaultTableModel) BefektetTable.getModel();
    DefaultTableModel ModelTable3 = (DefaultTableModel) HitelekTable.getModel();
    DefaultTableModel ModelTable4 = (DefaultTableModel) VagyonTable.getModel();
    JFreeChart chartPie = ChartFactory.createPieChart("Összesítés", createDatasetPie(), true, true, true);
    ChartPanel chartPanel3 = new ChartPanel(chartPie);
    DefaultCategoryDataset dataset;
    DefaultCategoryDataset dataset2;
    DefaultPieDataset dataset3;

    //<editor-fold defaultstate="collapsed" desc="Komponens adatfeltöltés metódusok"> 
    public void loadVagyonEszkCombo(JComboBox VagyonEszkCombo) {

        Forma Osszes = new Forma();
        Osszes.setNev("Mind");
        ArrayList<Forma> formaLista = null;
        formaLista = database.getForma();
        formaLista.add(Osszes);
        for (int i = 0; i < formaLista.size(); i++) {
            VagyonEszkCombo.addItem(formaLista.get(i).getNev());
        }
    }

    public void loadUserCombo(JComboBox UserCombo) {

        Szemely Osszes = new Szemely();
        Osszes.setName("Mind");
        ArrayList<Szemely> szemelyLista = null;
        szemelyLista = database.getSzemely();
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
        float total;
        total = 0;
        String penznemString;
        String szemelyString;
        String vagyonString;
        chooseDate = model.getValue().toLocalDate();
        if (currencyComboField.getItemCount() == 0) {
            penznemString = "PÉNZNEM";
        } else {
            penznemString = currencyComboField.getSelectedItem().toString();
        }
        if (szemelyCombo.getItemCount() == 0) {
            szemelyString = "Mind";
        } else {
            szemelyString = szemelyCombo.getSelectedItem().toString();
        }
        if (vagyonEszkCombo.getItemCount() == 0) {
            vagyonString = "Mind";
        } else {
            vagyonString = vagyonEszkCombo.getSelectedItem().toString();
        }
        chooseDate = model.getValue().toLocalDate();
        if (vagyonString.equals("Mind") || vagyonString.equals("Aktív eszközök")) {
            if (szemelyString.equals("Mind")) {
                Hitelek = database.getVagyonParam3("1", chooseDate, "%", "%");
            } else {
                Hitelek = database.getVagyonParam3("1", chooseDate, szemelyString, "%");
            }
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
                    total = total + (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad);
                } else {
                    float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    float RatioAmit = database.getPenznemID((String) AktivEszkozok.get(i).getPenznemNev()).get(0).getHUFRatio();
                    row[1] = (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad) * (RatioAmit / RatioAmire);
                    total = total + (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad) * (RatioAmit / RatioAmire);
                }
                if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                    row[2] = AktivEszkozok.get(i).getPenznemNev();
                } else {
                    row[2] = currencyComboField.getSelectedItem();
                }
                row[3] = AktivEszkozok.get(i).getSzemelyNev();
                ModelTable.addRow(row);
            }
            row[0] = "Összesen";
            row[1] = total;
            row[2] = "";
            row[3] = "";
            ModelTable.addRow(row);
        }
    }

    public void createTableAktEszk() {

        x = model.getMonth() + 1;
        y = model.getYear();
        float total;
        total = 0;
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
                total = total + (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad);
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) AktivEszkozok.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad) * (RatioAmit / RatioAmire);
                total = total + (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = AktivEszkozok.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem();
            }
            row[3] = AktivEszkozok.get(i).getSzemelyNev();
            ModelTable.addRow(row);
        }
        row[0] = "Összesen";
        row[1] = total;
        row[2] = "";
        row[3] = "";
        ModelTable.addRow(row);
    }

    public void refreshTableBefektetes() {
        float total;
        x = model.getMonth() + 1;
        y = model.getYear();
        total = 0;
        String penznemString;
        String szemelyString;
        String vagyonString;
        chooseDate = model.getValue().toLocalDate();
        if (currencyComboField.getItemCount() == 0) {
            penznemString = "PÉNZNEM";
        } else {
            penznemString = currencyComboField.getSelectedItem().toString();
        }
        if (szemelyCombo.getItemCount() == 0) {
            szemelyString = "Mind";
        } else {
            szemelyString = szemelyCombo.getSelectedItem().toString();
        }
        if (vagyonEszkCombo.getItemCount() == 0) {
            vagyonString = "Mind";
        } else {
            vagyonString = vagyonEszkCombo.getSelectedItem().toString();
        }

        chooseDate = model.getValue().toLocalDate();
        if (vagyonString.equals("Mind") || vagyonString.equals("Befektetések")) {
            if (szemelyString.equals("Mind")) {
                Hitelek = database.getVagyonParam3("2", chooseDate, "%", "%");
            } else {
                Hitelek = database.getVagyonParam3("2", chooseDate, szemelyString, "%");
            }
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
                    total = total + Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad;
                } else {
                    float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                    row[1] = (Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad) * (RatioAmit / RatioAmire);
                    total = total + (Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad) * (RatioAmit / RatioAmire);
                }
                if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                    row[2] = Befektetesek.get(i).getPenznemNev();
                } else {
                    row[2] = currencyComboField.getSelectedItem();
                }
                row[3] = Befektetesek.get(i).getSzemelyNev();
                ModelTable2.addRow(row);
            }
            row[0] = "Összesen";
            row[1] = total;
            row[2] = "";
            row[3] = "";
            ModelTable2.addRow(row);
        }
    }

    public void createTableBefektetes() {
        float total;
        x = model.getMonth() + 1;
        y = model.getYear();
        total = 0;
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
                total = total + Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad;
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad) * (RatioAmit / RatioAmire);
                total = total + (Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = Befektetesek.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem();
            }
            row[3] = Befektetesek.get(i).getSzemelyNev();
            ModelTable2.addRow(row);
        }
        row[0] = "Összesen";
        row[1] = total;
        row[2] = "";
        row[3] = "";
        ModelTable2.addRow(row);
    }

    public void refreshTableHitel() {

        String penznemString;
        String szemelyString;
        String vagyonString;
        x = model.getMonth() + 1;
        y = model.getYear();
        float total;
        total = 0;
        if (currencyComboField.getItemCount() == 0) {
            penznemString = "PÉNZNEM";
        } else {
            penznemString = currencyComboField.getSelectedItem().toString();
        }
        if (szemelyCombo.getItemCount() == 0) {
            szemelyString = "Mind";
        } else {
            szemelyString = szemelyCombo.getSelectedItem().toString();
        }
        if (vagyonEszkCombo.getItemCount() == 0) {
            vagyonString = "Mind";
        } else {
            vagyonString = vagyonEszkCombo.getSelectedItem().toString();
        }
        chooseDate = model.getValue().toLocalDate();
        if (vagyonString.equals("Mind") || vagyonString.equals("Hitelek")) {
            if (szemelyString.equals("Mind")) {
                Hitelek = database.getVagyonParam3("3", chooseDate, "%", "%");
            } else {
                Hitelek = database.getVagyonParam3("3", chooseDate, szemelyString, "%");
            }
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
                    total = total + Hitelek.get(i).getNyitoEgyenleg() - Kiad;
                } else {
                    float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    float RatioAmit = database.getPenznemID((String) Hitelek.get(i).getPenznemNev()).get(0).getHUFRatio();
                    row[1] = (Hitelek.get(i).getNyitoEgyenleg() - Kiad) * (RatioAmit / RatioAmire);
                    total = total + (Hitelek.get(i).getNyitoEgyenleg() - Kiad) * (RatioAmit / RatioAmire);
                }
                if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                    row[2] = Hitelek.get(i).getPenznemNev();
                } else {
                    row[2] = currencyComboField.getSelectedItem();
                }
                row[3] = Hitelek.get(i).getSzemelyNev();
                ModelTable3.addRow(row);
            }
            row[0] = "Összesen";
            row[1] = total;
            row[2] = "";
            row[3] = "";
            ModelTable3.addRow(row);
        }
    }

    public void createTableHitel() {
        x = model.getMonth() + 1;
        y = model.getYear();
        float total;
        total = 0;
        chooseDate = model.getValue().toLocalDate();
        Hitelek = database.getVagyonParam3("3", chooseDate, "%", "%");
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
                total = total + Hitelek.get(i).getNyitoEgyenleg() - Kiad;
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Hitelek.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (Hitelek.get(i).getNyitoEgyenleg() - Kiad) * (RatioAmit / RatioAmire);
                total = total + (Hitelek.get(i).getNyitoEgyenleg() - Kiad) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem() == "PÉNZNEM") {
                row[2] = Hitelek.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem();
            }
            row[3] = Hitelek.get(i).getSzemelyNev();
            ModelTable3.addRow(row);
        }
        row[0] = "Összesen";
        row[1] = total;
        row[2] = "";
        row[3] = "";
        ModelTable3.addRow(row);

    }

    public void refreshTableVagyon() {
        String penznemString;
        String szemelyString;
        String vagyonString;
        x = model.getMonth() + 1;
        y = model.getYear();
        float total;
        total = 0;
        if (currencyComboField.getItemCount() == 0) {
            penznemString = "PÉNZNEM";
        } else {
            penznemString = currencyComboField.getSelectedItem().toString();
        }
        if (szemelyCombo.getItemCount() == 0) {
            szemelyString = "Mind";
        } else {
            szemelyString = szemelyCombo.getSelectedItem().toString();
        }
        if (vagyonEszkCombo.getItemCount() == 0) {
            vagyonString = "Mind";
        } else {
            vagyonString = vagyonEszkCombo.getSelectedItem().toString();
        }
        chooseDate = model.getValue().toLocalDate();
        if (vagyonString.equals("Mind") || vagyonString.equals("Vagyontárgyak")) {
            if (szemelyString.equals("Mind")) {
                Vagyonok = database.getVagyonParam3("4", chooseDate, "%", "%");
            } else {
                Vagyonok = database.getVagyonParam3("4", chooseDate, szemelyString, "%");
            }
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
                if (penznemString == "PÉNZNEM") {
                    row[1] = Vagyonok.get(i).getNyitoEgyenleg() + Bev;
                    total = total + Vagyonok.get(i).getNyitoEgyenleg() + Bev;
                } else {
                    float RatioAmire = database.getPenznemID((String) penznemString).get(0).getHUFRatio();
                    float RatioAmit = database.getPenznemID((String) Vagyonok.get(i).getPenznemNev()).get(0).getHUFRatio();
                    row[1] = (Vagyonok.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                    total = total + (Vagyonok.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                }
                if (penznemString == "PÉNZNEM") {
                    row[2] = Vagyonok.get(i).getPenznemNev();
                } else {
                    row[2] = penznemString;
                }
                row[3] = Vagyonok.get(i).getSzemelyNev();
                ModelTable4.addRow(row);
            }
            row[0] = "Összesen";
            row[1] = total;
            row[2] = "";
            row[3] = "";
            ModelTable4.addRow(row);
        }
    }

    public void createTableVagyon() {

        String penznemString;
        String szemelyString;
        String vagyonString;
        x = model.getMonth() + 1;
        y = model.getYear();
        float total;
        total = 0;
        chooseDate = model.getValue().toLocalDate();
        Vagyonok = database.getVagyonParam3("4", chooseDate, "%", "%");
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
            if (currencyComboField.getSelectedItem().toString() == "PÉNZNEM") {
                row[1] = Vagyonok.get(i).getNyitoEgyenleg() + Bev;
                total = total + Vagyonok.get(i).getNyitoEgyenleg() + Bev;
            } else {
                float RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem().toString()).get(0).getHUFRatio();
                float RatioAmit = database.getPenznemID((String) Vagyonok.get(i).getPenznemNev()).get(0).getHUFRatio();
                row[1] = (Vagyonok.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                total = total + (Vagyonok.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
            }
            if (currencyComboField.getSelectedItem().toString() == "PÉNZNEM") {
                row[2] = Vagyonok.get(i).getPenznemNev();
            } else {
                row[2] = currencyComboField.getSelectedItem().toString();
            }
            row[3] = Vagyonok.get(i).getSzemelyNev();
            ModelTable4.addRow(row);
        }
        row[0] = "Összesen";
        row[1] = total;
        row[2] = "";
        row[3] = "";
        ModelTable4.addRow(row);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Diagram adatsor metódus"> 
    private PieDataset createDatasetPie() {
        float pieNumber = 0;
        float RatioAmire = 0;
        float RatioAmit = 0;
        String penznemString;
        String szemelyString;
        String vagyonString;
        String pieName;
        dataset3 = new DefaultPieDataset();
        if (currencyComboField.getItemCount() == 0) {
            penznemString = "PÉNZNEM";
        } else {
            penznemString = currencyComboField.getSelectedItem().toString();
        }
        if (szemelyCombo.getItemCount() == 0) {
            szemelyString = "Mind";
        } else {
            szemelyString = szemelyCombo.getSelectedItem().toString();
        }
        if (vagyonEszkCombo.getItemCount() == 0) {
            vagyonString = "Mind";
        } else {
            vagyonString = vagyonEszkCombo.getSelectedItem().toString();
        }
        model.setSelected(true);
        chooseDate = model.getValue().toLocalDate();
        if (vagyonString.equals("Mind") || vagyonString.equals("Aktív eszközök")) {
            if (szemelyString.equals("Mind")) {
                AktivEszkozok = database.getVagyonParam3("1", chooseDate, "%", "%");
            } else {
                AktivEszkozok = database.getVagyonParam3("1", chooseDate, szemelyString, "%");
            }
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
                pieName = AktivEszkozok.get(i).getNev();
                if (penznemString == "PÉNZNEM") {
                    pieNumber = AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad;
                } else {
                    RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    RatioAmit = database.getPenznemID((String) AktivEszkozok.get(i).getPenznemNev()).get(0).getHUFRatio();
                    pieNumber = (AktivEszkozok.get(i).getNyitoEgyenleg() + AktBev - AktKiad + AktAtvez - AktAtvezLevon + VarhatoVagytargyBev - VarhatoVagytargyKiad + VarhatoBefektetesBev - VarhatoBefektetesKiad - VarhatoHitelKiad + VarhatoEgyebBev - VarhatoEgyebKiad) * (RatioAmit / RatioAmire);
                }
                dataset3.setValue(pieName, pieNumber);
            }
        }
        if (vagyonString.equals("Mind") || vagyonString.equals("Befektetések")) {
            if (szemelyString.equals("Mind")) {
                Befektetesek = database.getVagyonParam3("2", chooseDate, "%", "%");
            } else {
                Befektetesek = database.getVagyonParam3("2", chooseDate, szemelyString, "%");
            }
            for (int i = 0; i < Befektetesek.size(); i++) {
                LocalDate ld = model.getValue().toLocalDate();
                Bev = 0;
                Kiad = 0;
                pieName = Befektetesek.get(i).getNev();
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
                if (penznemString == "PÉNZNEM") {
                    pieNumber = Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad;
                } else {
                    RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    RatioAmit = database.getPenznemID((String) Befektetesek.get(i).getPenznemNev()).get(0).getHUFRatio();
                    pieNumber = (Befektetesek.get(i).getNyitoEgyenleg() + AktAtvez - AktAtvezLevon + Bev - Kiad) * (RatioAmit / RatioAmire);
                }
                dataset3.setValue(pieName, pieNumber);
            }
        }
        if (vagyonString.equals("Mind") || vagyonString.equals("Hitelek")) {

            if (szemelyString.equals("Mind")) {
                Hitelek = database.getVagyonParam3("3", chooseDate, "%", "%");
            } else {
                Hitelek = database.getVagyonParam3("3", chooseDate, szemelyString, "%");
            }
            for (int i = 0; i < Hitelek.size(); i++) {
                Kiad = 0;
                pieName = Hitelek.get(i).getNev();
                TranzakcioBev = dbBevetel.getTranzakcioFutureValue2(9, Hitelek.get(i).getVID());
                for (int k = 0; k < TranzakcioBev.size(); k++) {
                    Kiad = TranzakcioBev.get(k).getPresentValue(Hitelek.get(i).getNyitoDatum(), model.getValue().toLocalDate());
                }

                if (penznemString == "PÉNZNEM") {
                    pieNumber = Hitelek.get(i).getNyitoEgyenleg() - Kiad;
                } else {
                    RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    RatioAmit = database.getPenznemID((String) Hitelek.get(i).getPenznemNev()).get(0).getHUFRatio();
                    pieNumber = (Hitelek.get(i).getNyitoEgyenleg() - Kiad) * (RatioAmit / RatioAmire);
                }
                dataset3.setValue(pieName, pieNumber);
            }
        }
        if (vagyonString.equals("Mind") || vagyonString.equals("Vagyontárgyak")) {
            if (szemelyString.equals("Mind")) {
                Vagyonok = database.getVagyonParam3("4", chooseDate, "%", "%");
            } else {
                Vagyonok = database.getVagyonParam3("4", chooseDate, szemelyString, "%");
            }

            for (int i = 0; i < Vagyonok.size(); i++) {
                Bev = 0;
                Kiad = 0;
                pieName = Vagyonok.get(i).getNev();
                TranzakcioBev = dbBevetel.getTranzakcioFutureValue2(4, Vagyonok.get(i).getVID());
                for (int k = 0; k < TranzakcioBev.size(); k++) {
                    Bev = TranzakcioBev.get(k).getPresentValue(Vagyonok.get(i).getNyitoDatum(), model.getValue().toLocalDate());
                }

                if (penznemString == "PÉNZNEM") {
                    pieNumber = Vagyonok.get(i).getNyitoEgyenleg() + Bev;
                } else {
                    RatioAmire = database.getPenznemID((String) currencyComboField.getSelectedItem()).get(0).getHUFRatio();
                    RatioAmit = database.getPenznemID((String) Vagyonok.get(i).getPenznemNev()).get(0).getHUFRatio();
                    pieNumber = (Vagyonok.get(i).getNyitoEgyenleg() + Bev) * (RatioAmit / RatioAmire);
                }
                dataset3.setValue(pieName, pieNumber);
            }
        }
        return dataset3;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adatsor, táblázat frissítés metódus"> 
    public void diagramRefresh() {
        ModelTable.getDataVector().removeAllElements();
        ModelTable.fireTableDataChanged();
        ModelTable2.getDataVector().removeAllElements();
        ModelTable2.fireTableDataChanged();
        ModelTable3.getDataVector().removeAllElements();
        ModelTable3.fireTableDataChanged();
        ModelTable4.getDataVector().removeAllElements();
        ModelTable4.fireTableDataChanged();
        dataset3.clear();
        createDatasetPie();
        PiePlot plot3 = (PiePlot) chartPie.getPlot();
        plot3.setDataset(dataset3);
        refreshTableAktEszk();
        refreshTableBefektetes();
        refreshTableVagyon();
        refreshTableHitel();

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">   
    public OsszesitoAktEszkPanel() {

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
        datePicker = new JDatePickerImpl(panle, CustomFormat);
        datePicker.getJFormattedTextField().setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
        datePicker.setAutoscrolls(true);
        loadCurrencyCombo(currencyComboField);
        loadVagyonEszkCombo(vagyonEszkCombo);
        loadUserCombo(szemelyCombo);
        currencyComboField.setSelectedIndex(currencyComboField.getItemCount() - 1);
        currencyComboField.addActionListener(this);
        vagyonEszkCombo.addActionListener(this);
        szemelyCombo.addActionListener(this);
        keres.addActionListener(this);
        datePanel.add(szemelyCombo);
        datePanel.add(vagyonEszkCombo);
        datePanel.add(currencyComboField);
        datePanel.add(datePicker);
        datePanel.add(keres);
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
        diagramPanel.add(chartPanel3, gbc_algo);
        diagramPanel.add(chartPanel3, gbc_algo);
        createTableAktEszk();
        createTableVagyon();
        createTableHitel();
        createTableBefektetes();
        tablesPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY));
        diagramPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY));
        this.add(tablesPanel, BorderLayout.WEST);
        this.add(diagramPanel, BorderLayout.CENTER);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés">    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == keres) {
            diagramRefresh();
        }

    }
    //</editor-fold>
}
