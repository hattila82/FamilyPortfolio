/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

/**
 *
 * @author HorvathAttila
 * AktFelhasznalo osztály létrehozása, konstruktor, getter, setter metódusok
 */
public class AktFelhasznalo {

    private int AUID;
    private int AktFelID;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódusok">     
    public AktFelhasznalo() {
    }

    public AktFelhasznalo(int AUID, int AktFelID) {
        this.AUID = AUID;
        this.AktFelID = AktFelID;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter és Setter metódusok">      
    public int getAUID() {
        return AUID;
    }

    public void setAUID(int AUID) {
        this.AUID = AUID;
    }
    
    public int getAktFelID() {
        return AktFelID;
    }

    public void setAktFelID(int AktFelID) {
        this.AktFelID = AktFelID;
    }
    //</editor-fold>
}
