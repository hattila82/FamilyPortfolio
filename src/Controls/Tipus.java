/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila Tipus osztály létrehozása, konstruktor, getter, setter
 * metódusok
 */
public class Tipus {

    private int TID;
    private int Forma;
    private String nev;
    private String FormaNev;
    private int AktFel;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">   
    public Tipus(int TID, int FID, String nev) {
        this.TID = TID;
        this.Forma = FID;
        this.nev = nev;
    }

    public Tipus(int TID, int Forma, String nev, String FormaNev) {
        this.TID = TID;
        this.Forma = Forma;
        this.nev = nev;
        this.FormaNev = FormaNev;
    }

    public Tipus() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">   
    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public int getForma() {
        return Forma;
    }

    public void setForma(int FID) {
        this.Forma = FID;
    }

    public int getAktFel() {
        return AktFel;
    }

    public void setAktFel(int AktFel) {
        this.AktFel = AktFel;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getFormaNev() {
        return FormaNev;
    }

    public void setFormaNev(String FormaNev) {
        this.FormaNev = FormaNev;
    }
    //</editor-fold>
}
