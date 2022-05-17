/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.plot.CategoryPlot;

/**
 *
 * @author HorvathAttila GuiMain osztály létrehozása, eseménykezelés
 */
public class GuiMain extends JFrame {

    JTabbedPane Base = new JTabbedPane();

    JTabbedPane Alapadatok = new JTabbedPane();
    JTabbedPane Tranzakciok = new JTabbedPane();
    JTabbedPane Kimutatasok = new JTabbedPane();
    JTabbedPane Szemelyek = new JTabbedPane();
    JTabbedPane VagyoniEszkozok = new JTabbedPane();
    JTabbedPane Kategoriak = new JTabbedPane();
    JTabbedPane AktivTranzakcio = new JTabbedPane();
    JTabbedPane VarhatoTranzakcio = new JTabbedPane();
    JTabbedPane VagyonTargy = new JTabbedPane();
    JTabbedPane Befektetes = new JTabbedPane();
    JTabbedPane Hitel = new JTabbedPane();
    OsszegzesPanel Osszegzes = new OsszegzesPanel();
    SzemelyPanel SzemelyPanel = new SzemelyPanel();
    TipusPanel Tipusok = new TipusPanel();
    VagyonPanel AktivEszkozPanel = new VagyonPanel("1");
    VagyonPanel BefektetesekPanel = new VagyonPanel("2");
    VagyonPanel Hitelek = new VagyonPanel("3");
    VagyonPanel VagyonTargyak = new VagyonPanel("4");
    PenznemPanel Penznemek = new PenznemPanel();
    KategoriaPanel Bevetel = new KategoriaPanel("1");
    KategoriaPanel Kiadas = new KategoriaPanel("2");
    TranzakcioPanel Bevetelek = new TranzakcioPanel("1");
    TranzakcioPanel Kiadasok = new TranzakcioPanel("2");
    TranzakcioPanel Atvezetes = new TranzakcioPanel("3");
    TranzakcioPanel VagyonTargyErtekvaltozas = new TranzakcioPanel("4");
    TranzakcioPanel VagyonTargyBevetel = new TranzakcioPanel("5");
    TranzakcioPanel VagyonTargyKiadas = new TranzakcioPanel("6");
    TranzakcioPanel Hozam = new TranzakcioPanel("7");
    TranzakcioPanel Veszteseg = new TranzakcioPanel("8");
    TranzakcioPanel Toke = new TranzakcioPanel("9");
    TranzakcioPanel Torlesztes = new TranzakcioPanel("10");
    TranzakcioPanel EgyebBevetel = new TranzakcioPanel("11");
    TranzakcioPanel EgyebKiadas = new TranzakcioPanel("12");
    AktEszkPanel AktivEszkozPanelLekerd = new AktEszkPanel();
    BefektesAktEszkPanel BefektesAktEszkPanelLekered = new BefektesAktEszkPanel();
    VagyontargyAktEszkPanel VagyontargyAktEszkPanelLekered = new VagyontargyAktEszkPanel();
    HitelAktEszkPanel HitelAktEszkPanelLekered = new HitelAktEszkPanel();
    OsszesitoAktEszkPanel OsszesitoAktEszkPanelLekered = new OsszesitoAktEszkPanel();

//<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">      
    public GuiMain() {
        this.setTitle("Családi Portfólió");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Base.addTab("Összegzés", Osszegzes);
        Base.addTab("Alapadatok", Alapadatok);
        Base.addTab("Tranzakciók", Tranzakciok);
        Base.addTab("Kimutatások", Kimutatasok);
        Alapadatok.addTab("Személyek", SzemelyPanel);
        Alapadatok.addTab("VagyoniEszközök", VagyoniEszkozok);
        Alapadatok.addTab("Típusok", Tipusok);
        Alapadatok.addTab("Kategóriák", Kategoriak);
        Alapadatok.addTab("Pénznemek", Penznemek);
        VagyoniEszkozok.addTab("Aktív Eszközök", AktivEszkozPanel);
        VagyoniEszkozok.addTab("Befektetések", BefektetesekPanel);
        VagyoniEszkozok.addTab("Hitelek", Hitelek);
        VagyoniEszkozok.addTab("Vagyontárgyak", VagyonTargyak);
        Kategoriak.addTab("Bevétel", Bevetel);
        Kategoriak.addTab("Kiadás", Kiadas);
        Tranzakciok.addTab("Aktív Tranzakciók", AktivTranzakcio);
        Tranzakciok.addTab("Várható Tranzakciók", VarhatoTranzakcio);
        VarhatoTranzakcio.addTab("Vagyontárgy", VagyonTargy);
        VarhatoTranzakcio.addTab("Befektetés", Befektetes);
        VarhatoTranzakcio.addTab("Hitel", Hitel);
        VarhatoTranzakcio.addTab("Egyéb Bevétel", EgyebBevetel);
        VarhatoTranzakcio.addTab("Egyéb Kiadás", EgyebKiadas);
        AktivTranzakcio.addTab("Bevételek", Bevetelek);
        AktivTranzakcio.addTab("Kiadások", Kiadasok);
        AktivTranzakcio.addTab("Átvezetés", Atvezetes);
        VagyonTargy.addTab("Értékváltozás", VagyonTargyErtekvaltozas);
        VagyonTargy.addTab("Bevétel", VagyonTargyBevetel);
        VagyonTargy.addTab("Kiadás", VagyonTargyKiadas);
        Befektetes.addTab("Hozam", Hozam);
        Befektetes.addTab("Veszteség", Veszteseg);
        Hitel.addTab("Hiteltőke", Toke);
        Hitel.addTab("Törlesztés", Torlesztes);
        Kimutatasok.addTab("Aktív eszköz kivonat", AktivEszkozPanelLekerd);
        Kimutatasok.addTab("Befektetések változása kivonat", BefektesAktEszkPanelLekered);
        Kimutatasok.addTab("Vagyontárgyak változása kivonat", VagyontargyAktEszkPanelLekered);
        Kimutatasok.addTab("Hitelek változása kivonat", HitelAktEszkPanelLekered);
        Kimutatasok.addTab("Összesítő kivonat", OsszesitoAktEszkPanelLekered);
        Base.setBounds(10, 110, 20, 20);
        Base.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 0) {
                        Osszegzes.diagramRefresh();
                    }
                }
            }
        });
        Font myFont1 = new Font("Arial", Font.BOLD, 22);
        Base.setFont(myFont1);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.add(Base);
        this.pack();
        this.setLocationRelativeTo(null);
    }
//</editor-fold>
    
}
