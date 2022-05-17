/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila TranzakcioTípus osztály létrehozása, konstruktor,
 * getter, setter metódusok
 */
public class TranzakcioTipus {

    private int TTID;
    private String nev;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">     
    public TranzakcioTipus(int TTID, String nev) {
        this.TTID = TTID;
        this.nev = nev;
    }

    public TranzakcioTipus() {
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">   

    public int getTTID() {
        return TTID;
    }

    public void setTTID(int TTID) {
        this.TTID = TTID;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

//</editor-fold>
}
