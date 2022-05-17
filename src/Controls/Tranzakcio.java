/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import java.time.LocalDate;

/**
 *
 * @author HorvathAttila Tranzakcio osztály létrehozása, konstruktor, getter,
 * setter metódusok és tranzakció jövőérték számítás metódusok
 */
public class Tranzakcio {

    private int TRID;
    private String Nev;
    private int Kategoria;
    private LocalDate Datum;
    private int Osszeg;
    private int VagyonID1;
    private int VagyonID2;
    private int TranzakcioTipus;
    private int TranzakcioRendszeresseg;
    private int Penznem;
    private String KategoriaNev;
    private String TranzakcioRendszeressegNev;
    private String SzemelyNev1;
    private String VagyonNev1;
    private String TipusNev1;
    private String SzemelyNev2;
    private String VagyonNev2;
    private String TipusNev2;
    private String PenzTipus;
    private int Summa;
    private int Ratio;
    public int honapok;
    public int evek;
    private int AktFel;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok"> 
    public Tranzakcio() {
    }

    public Tranzakcio(int TRID, String Nev, int Kategoria, LocalDate Datum, int Osszeg, int Penznem, int VagyonID1, int VagyonID2, int TranzakcioTipus, int TranzakcioRendszeresseg, String KategoriaNev, String TranzakcioRendszeressegNev, String SzemelyNev1, String VagyonNev1, String TipusNev1, String SzemelyNev2, String VagyonNev2, String TipusNev2, String PenzTipus) {
        this.TRID = TRID;
        this.Nev = Nev;
        this.Kategoria = Kategoria;
        this.Datum = Datum;
        this.Osszeg = Osszeg;
        this.VagyonID1 = VagyonID1;
        this.VagyonID2 = VagyonID2;
        this.TranzakcioTipus = TranzakcioTipus;
        this.TranzakcioRendszeresseg = TranzakcioRendszeresseg;
        this.Penznem = Penznem;
        this.KategoriaNev = KategoriaNev;
        this.TranzakcioRendszeressegNev = TranzakcioRendszeressegNev;
        this.SzemelyNev1 = SzemelyNev1;
        this.VagyonNev1 = VagyonNev1;
        this.TipusNev1 = TipusNev1;
        this.SzemelyNev2 = SzemelyNev2;
        this.VagyonNev2 = VagyonNev2;
        this.TipusNev2 = TipusNev2;
        this.PenzTipus = PenzTipus;
    }

    public Tranzakcio(LocalDate Datum, int Osszeg, int TranzakcioRendszeresseg, int Ratio) {
        this.Datum = Datum;
        this.Osszeg = Osszeg;
        this.TranzakcioRendszeresseg = TranzakcioRendszeresseg;
        this.Ratio = Ratio;
    }

    public Tranzakcio(int TRID, String Nev, int Kategoria, LocalDate Datum, int Osszeg, int VagyonID1, int VagyonID2, int TranzakcioTipus, int TranzakcioRendszeresseg, int Penznem) {
        this.TRID = TRID;
        this.Nev = Nev;
        this.Kategoria = Kategoria;
        this.Datum = Datum;
        this.Osszeg = Osszeg;
        this.VagyonID1 = VagyonID1;
        this.VagyonID2 = VagyonID2;
        this.TranzakcioTipus = TranzakcioTipus;
        this.TranzakcioRendszeresseg = TranzakcioRendszeresseg;
        this.Penznem = Penznem;
    }

    public Tranzakcio(String Nev, int Osszeg, String PenzTipus, LocalDate Datum, String SzemelyNev1, String VagyonNev1) {
        this.Nev = Nev;
        this.Datum = Datum;
        this.Osszeg = Osszeg;
        this.PenzTipus = PenzTipus;
        this.SzemelyNev1 = SzemelyNev1;
        this.VagyonNev1 = VagyonNev1;
    }

    public Tranzakcio(int Summa) {
        this.Summa = Summa;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">

    public int getPenznem() {
        return Penznem;
    }

    public void setPenznem(int Penznem) {
        this.Penznem = Penznem;
    }

    public int getTRID() {
        return TRID;
    }

    public void setTRID(int TRID) {
        this.TRID = TRID;
    }

    public String getNev() {
        return Nev;
    }

    public void setNev(String Nev) {
        this.Nev = Nev;
    }

    public int getKategoria() {
        return Kategoria;
    }

    public void setKategoria(int Kategoria) {
        this.Kategoria = Kategoria;
    }

    public LocalDate getDatum() {
        return Datum;
    }

    public void setDatum(LocalDate Datum) {
        this.Datum = Datum;
    }

    public int getOsszeg() {
        return Osszeg;
    }

    public void setOsszeg(int Osszeg) {
        this.Osszeg = Osszeg;
    }

    public int getVagyonID1() {
        return VagyonID1;
    }

    public void setVagyonID1(int VagyonID1) {
        this.VagyonID1 = VagyonID1;
    }

    public int getVagyonID2() {
        return VagyonID2;
    }

    public void setVagyonID2(int VagyonID2) {
        this.VagyonID2 = VagyonID2;
    }

    public int getTranzakcioTipus() {
        return TranzakcioTipus;
    }

    public void setTranzakcioTipus(int TranzakcioTipus) {
        this.TranzakcioTipus = TranzakcioTipus;
    }

    public int getTranzakcioRendszeresseg() {
        return TranzakcioRendszeresseg;
    }

    public void setTranzakcioRendszeresseg(int TranzakcioRendszeresseg) {
        this.TranzakcioRendszeresseg = TranzakcioRendszeresseg;
    }

    public String getKategoriaNev() {
        return KategoriaNev;
    }

    public void setKategoriaNev(String KategoriaNev) {
        this.KategoriaNev = KategoriaNev;
    }

    public String getTranzakcioRendszeressegNev() {
        return TranzakcioRendszeressegNev;
    }

    public void setTranzakcioRendszeressegNev(String TranzakcioRendszeressegNev) {
        this.TranzakcioRendszeressegNev = TranzakcioRendszeressegNev;
    }

    public String getSzemelyNev1() {
        return SzemelyNev1;
    }

    public void setSzemelyNev1(String SzemelyNev1) {
        this.SzemelyNev1 = SzemelyNev1;
    }

    public String getVagyonNev1() {
        return VagyonNev1;
    }

    public void setVagyonNev1(String VagyonNev1) {
        this.VagyonNev1 = VagyonNev1;
    }

    public String getTipusNev1() {
        return TipusNev1;
    }

    public void setTipusNev1(String TipusNev1) {
        this.TipusNev1 = TipusNev1;
    }

    public String getSzemelyNev2() {
        return SzemelyNev2;
    }

    public void setSzemelyNev2(String SzemelyNev2) {
        this.SzemelyNev2 = SzemelyNev2;
    }

    public String getVagyonNev2() {
        return VagyonNev2;
    }

    public void setVagyonNev2(String VagyonNev2) {
        this.VagyonNev2 = VagyonNev2;
    }

    public String getTipusNev2() {
        return TipusNev2;
    }

    public void setTipusNev2(String TipusNev2) {
        this.TipusNev2 = TipusNev2;
    }

    public String getPenzTipus() {
        return PenzTipus;
    }

    public void setPenzTipus(String PenzTipus) {
        this.PenzTipus = PenzTipus;
    }

    public int getSumma() {
        return Summa;
    }

    public void setSumma(int Summa) {
        this.Summa = Summa;
    }

    public int getAktFel() {
        return AktFel;
    }

    public void setAktFel(int AktFel) {
        this.AktFel = AktFel;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Tranzakció érték számítás metódus">
    // Tranzakció érték összeget ad egy adott időpillanathoz
    public int getPresentValue(LocalDate OpenDate, LocalDate PresentDate) {
        if (OpenDate.isAfter(Datum) || OpenDate.isAfter(PresentDate)) {
        } else {
            if (TranzakcioRendszeresseg == 1 && PresentDate.isAfter(Datum)) {
                return Osszeg * Ratio;
            }
            if (TranzakcioRendszeresseg == 2 && PresentDate.isAfter(Datum)) {
                honapok = (Datum.getYear() * 12 + Datum.getMonthValue()) - (OpenDate.getYear() * 12 + OpenDate.now().getMonthValue());
                return Osszeg * Ratio * honapok;
            }
            if (TranzakcioRendszeresseg == 2 && Datum.isAfter(PresentDate)) {
                honapok = (PresentDate.getYear() * 12 + PresentDate.getMonthValue()) - (OpenDate.getYear() * 12 + OpenDate.now().getMonthValue());
                return Osszeg * Ratio * honapok;
            }
            if (TranzakcioRendszeresseg == 3 && PresentDate.isAfter(Datum)) {
                evek = Datum.getYear() - OpenDate.getYear();
                int x = Osszeg * Ratio * evek;
                return Osszeg * Ratio * evek;
            }
            if (TranzakcioRendszeresseg == 3 && Datum.isAfter(PresentDate)) {
                evek = PresentDate.getYear() - OpenDate.getYear();
                int x = Osszeg * Ratio * evek;
                return Osszeg * Ratio * evek;
            }
        }
        return 0;
    }
    //</editor-fold>
}
