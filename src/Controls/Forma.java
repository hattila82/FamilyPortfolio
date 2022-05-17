/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila Forma osztály létrehozása, getterek és setterek
 */
public class Forma {

    private int FID;
    private String nev;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">  
    public Forma(int FID, String nev) {
        this.FID = FID;
        this.nev = nev;
    }

    public Forma() {
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok"> 

    public int getFID() {
        return FID;
    }

    public String getNev() {
        return nev;
    }

    public void setFID(int FID) {
        this.FID = FID;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }
//</editor-fold>
}
