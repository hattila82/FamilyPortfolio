/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila Kategoria osztály létrehozása, konstruktor, getter,
 * setter metódusok
 */
public class Kategoria {

    private int KID;
    private int BevKid;
    private String nev;
    private String megj;
    private int AktFel;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">  
    public Kategoria() {
    }

    public Kategoria(int KID, int BevKid, String nev, String megj) {
        this.KID = KID;
        this.BevKid = BevKid;
        this.nev = nev;
        this.megj = megj;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">

    public int getKID() {
        return KID;
    }

    public void setKID(int KID) {
        this.KID = KID;
    }

    public int getBevKid() {
        return BevKid;
    }

    public void setBevKid(int BevKid) {
        this.BevKid = BevKid;
    }

    public String getNev() {
        return nev;
    }

    public int getAktFel() {
        return AktFel;
    }

    public void setAktFel(int AktFel) {
        this.AktFel = AktFel;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getMegj() {
        return megj;
    }

    public void setMegj(String megj) {
        this.megj = megj;
    }
    //</editor-fold>

}
