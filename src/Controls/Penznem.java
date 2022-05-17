/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila Pénznem osztály létrehozása, konstruktor, setter,
 * getter metódusok
 */
public class Penznem {

    private int id;
    private String name;
    private float HUFRatio;
    private int AktFel;
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">  

    public Penznem(int id, String name, float HUFRatio) {
        this.id = id;
        this.name = name;
        this.HUFRatio = HUFRatio;
    }

    public Penznem() {
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok"> 

    public int getId() {
        return id;
    }

    public int getAktFel() {
        return AktFel;
    }

    public void setAktFel(int AktFel) {
        this.AktFel = AktFel;
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

    public float getHUFRatio() {
        return HUFRatio;
    }

    public void setHUFRatio(float HUFRatio) {
        this.HUFRatio = HUFRatio;
    }
    //</editor-fold>
}
