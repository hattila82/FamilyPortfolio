/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila Személy osztály létrehozása, konstruktor, getter,
 * setter metódusok
 */
public class Szemely {

    private int id;
    private String name;
    private String megjegyzes;
    private int AktFel;

//<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">  
    public Szemely(int id, String name, String megjegyzes) {
        this.id = id;
        this.name = name;
        this.megjegyzes = megjegyzes;
    }

    public Szemely(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Szemely() {

    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok metódusok">  
    public int getAktFel() {
        return AktFel;
    }

    public void setAktFel(int AktFel) {
        this.AktFel = AktFel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }
    //</editor-fold>
}
