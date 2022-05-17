/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author HorvathAttila Vagyon osztály létrehozása, konstruktor, getter, setter
 * metódusok
 */
public class Vagyon {

    private int VID;
    private int Szemely;
    private int Penznem;
    private int Tipus;
    private int Forma;
    private int NyitoEgyenleg;
    private String Nev;
    private String Megj;
    private LocalDate NyitoDatum;
    private String PenznemNev;
    private String TipusNev;
    private String FormaNev;
    private String SzemelyNev;
    private String VagyonTipus;
    private int AktFel;
    int Vagyondb;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">  
    public Vagyon(String VagyonTipus, int VID) {
        this.VagyonTipus = VagyonTipus;
        this.VID = VID;
    }

    public Vagyon(int VID, String Nev, String TipusNev, int NyitoEgyenleg, String PenznemNev, LocalDate NyitoDatum, String SzemelyNev, String Megj) {
        this.VID = VID;
        this.NyitoEgyenleg = NyitoEgyenleg;
        this.Nev = Nev;
        this.Megj = Megj;
        this.NyitoDatum = NyitoDatum;
        this.PenznemNev = PenznemNev;
        this.TipusNev = TipusNev;
        this.SzemelyNev = SzemelyNev;
    }

    public Vagyon(int VID, int Szemely, int Penznem, int Tipus, int Forma, int NyitoEgyenleg, String Nev, String Megj, LocalDate NyitoDatum) {
        this.VID = VID;
        this.Szemely = Szemely;
        this.Penznem = Penznem;
        this.Tipus = Tipus;
        this.Forma = Forma;
        this.NyitoEgyenleg = NyitoEgyenleg;
        this.Nev = Nev;
        this.Megj = Megj;
        this.NyitoDatum = NyitoDatum;
    }

    public Vagyon(String VagyonTipus) {
        this.VagyonTipus = VagyonTipus;
    }

    public Vagyon(int VID) {
        this.VID = VID;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">  

    public int getAktFel() {
        return AktFel;
    }

    public void setAktFel(int AktFel) {
        this.AktFel = AktFel;
    }

    public String getPenznemNev() {
        return PenznemNev;
    }

    public void setPenznemNev(String PenznemNev) {
        this.PenznemNev = PenznemNev;
    }

    public String getTipusNev() {
        return TipusNev;
    }

    public void setTipusNev(String TipusNev) {
        this.TipusNev = TipusNev;
    }

    public String getFormaNev() {
        return FormaNev;
    }

    public void setFormaNev(String FormaNev) {
        this.FormaNev = FormaNev;
    }

    public String getSzemelyNev() {
        return SzemelyNev;
    }

    public void setSzemelyNev(String SzemelyNev) {
        this.SzemelyNev = SzemelyNev;
    }

    public Vagyon() {
    }

    public Vagyon(int VID, int Szemely, int Penznem, int Tipus, int Forma, String Nev, LocalDate NyitoEgyenleg) {
        this.VID = VID;
        this.Szemely = Szemely;
        this.Penznem = Penznem;
        this.Tipus = Tipus;
        this.Forma = Forma;
        this.Nev = Nev;
        this.NyitoDatum = NyitoEgyenleg;
    }

    public int getVID() {
        return VID;
    }

    public int getNyitoEgyenleg() {
        return NyitoEgyenleg;
    }

    public void setNyitoEgyenleg(int NyitoEgyenleg) {
        this.NyitoEgyenleg = NyitoEgyenleg;
    }

    public void setVID(int VID) {
        this.VID = VID;
    }

    public int getSzemely() {
        return Szemely;
    }

    public void setSzemely(int Szemely) {
        this.Szemely = Szemely;
    }

    public int getPenznem() {
        return Penznem;
    }

    public void setPenznem(int Penznem) {
        this.Penznem = Penznem;
    }

    public int getTipus() {
        return Tipus;
    }

    public void setTipus(int Tipus) {
        this.Tipus = Tipus;
    }

    public int getForma() {
        return Forma;
    }

    public void setForma(int Forma) {
        this.Forma = Forma;
    }

    public String getNev() {
        return Nev;
    }

    public void setNev(String Nev) {
        this.Nev = Nev;
    }

    public String getMegj() {
        return Megj;
    }

    public void setMegj(String Megj) {
        this.Megj = Megj;
    }

    public LocalDate getNyitoDatum() {
        return NyitoDatum;
    }

    public void setNyitoDatum(LocalDate NyitoDatum) {
        this.NyitoDatum = NyitoDatum;
    }

    public String getVagyonTipus() {
        return VagyonTipus;
    }

    public void setVagyonTipus(String VagyonTipus) {
        this.VagyonTipus = VagyonTipus;
    }

    public int getVagyondb() {
        return Vagyondb;
    }

    public void setVagyondb(int Vagyondb) {
        this.Vagyondb = Vagyondb;
    }
    //</editor-fold>
}
