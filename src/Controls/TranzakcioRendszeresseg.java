/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila * Tranzakciorendszeresség osztály létrehozása,
 * konstruktor, getter, setter metódusok
 */
public class TranzakcioRendszeresseg {

    private int TRRID;
    private String nev;
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">

    public TranzakcioRendszeresseg(int TRRID, String nev) {
        this.TRRID = TRRID;
        this.nev = nev;
    }

    public TranzakcioRendszeresseg() {
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">   

    public int getTRRID() {
        return TRRID;
    }

    public void setTRRID(int TRRID) {
        this.TRRID = TRRID;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }
    //</editor-fold>

}
