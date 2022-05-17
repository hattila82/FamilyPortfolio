/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila Felhasznalo osztály létrehozása, konstruktor, getter,
 * setter metódusok
 */
public class Felhasznalo {

    private int id;
    private String nev;
    private String jelszo;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">     
    public Felhasznalo(int id, String nev, String jelszo) {
        this.id = id;
        this.nev = nev;
        this.jelszo = jelszo;
    }

    public Felhasznalo() {
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }
    //</editor-fold>
}
