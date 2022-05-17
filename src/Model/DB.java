/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controls.AktFelhasznalo;
import Controls.Felhasznalo;
import Controls.Penznem;
import Controls.Forma;
import Controls.Szemely;
import Controls.Tipus;
import Controls.TranzakcioTipus;
import Controls.Vagyon;
import Controls.TranzakcioRendszeresseg;
import Controls.Kategoria;
import Controls.Tranzakcio;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author HorvathAttila Lokális Derby adatbázis létrehozása és a tábla
 * műveletek definiálása
 *
 *
 *
 */
public class DB {

    final String URL = "jdbc:derby://localhost:1527/Portfolio;create=true";
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    String[] peldanyFormak = {"Aktív eszközök", "Befektetések", "Hitelek", "Vagyontárgyak"};
    String[] peldanyTranzakciok = {"Aktív Bevétel", "Aktív Kiadás", "Aktív Átvezetés", "Várható Vagyontárgy Értékváltozás", "Várható Vagyontárgy Bevétel", "Várható Vagyontárgy Költség", "Várható Befektetés Hozam", "Várható Befektetés Veszteség", "Várható Hitel Hiteltőke", "Várható Hitel Törlesztés", "Várható Egyéb bevétel", "Várható Egyéb kiadás"};
    String[] peldanyTranzakcioRendszeresseg = {"Egyszeri", "Havi", "Éves"};

    //<editor-fold defaultstate="collapsed" desc="Kapcsolat + táblák">
    //adatbázis kapcsolat felépítése és táblák  létrehozása
    public DB() {

        try {
            conn = DriverManager.getConnection(URL);
            createStatement = conn.createStatement();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            dbmd = conn.getMetaData();
            System.out.println("Elérhetők a metaadatok.");
        } catch (SQLException ex) {
            System.out.println("Hiba3: " + ex);
        }
        // aktualis felhasználó tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "AKTFELHASZNALO", null);

            if (!rs.next()) {
                createStatement.execute("create table AktFelhasznalo (AUID int not null primary key, AktFelID int not null)");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // felhasznalo tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "FELHASZNALO", null);

            if (!rs.next()) {
                createStatement.execute("create table Felhasznalo (UID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "Nev varchar(255) not null, Jelszo varchar(255) not null )");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Forma tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "FORMA", null);

            if (!rs.next()) {
                createStatement.execute("create table Forma (FID int not null primary key, Nev varchar(255) not null)");
                formaFeltolt();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // TranzakcioTipus tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "TRANZAKCIOTIPUS", null);

            if (!rs.next()) {
                createStatement.execute("create table TranzakcioTipus (TTID int not null primary key, Nev varchar(255) not null)");
                tranzakcioTipusFeltolt();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // TranzakcioRendszeresseg tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "TRANZAKCIORENDSZERESSEG", null);

            if (!rs.next()) {
                createStatement.execute("create table TranzakcioRendszeresseg (TRRID int not null primary key, Nev varchar(255) not null)");
                tranzakcioRendszeressegFeltolt();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Típus tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "TIPUS", null);

            if (!rs.next()) {
                createStatement.execute("create table Tipus (TID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Forma int references Forma(FID), Nev varchar(255) not null, AktFel int)");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Pénznem tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "PENZNEM", null);
            if (!rs.next()) {
                createStatement.execute("create table Penznem (PID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "Nev varchar(10) not null, HufRatio float(5), AktFel int)");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Személy tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "SZEMELY", null);
            if (!rs.next()) {
                createStatement.execute("create table Szemely (SzID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "Nev varchar(255) not null, Megj varchar(255), AktFel int)");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Vagyon tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "VAGYON", null);
            if (!rs.next()) {
                createStatement.execute("create table Vagyon (VID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "Szemely int not null references Szemely(SzID), NyitoEgyenleg int, NyitoDatum date, Penznem int not null references Penznem(PID),"
                        + "Tipus int not null references Tipus(TID), Forma int not null references Forma(FID),Nev varchar(255), Megj varchar(255),AktFel int )");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Kategória tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "KATEGORIA", null);
            if (!rs.next()) {
                createStatement.execute("create table Kategoria (KID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "Nev varchar (255) not null, Megj varchar(255), BevKid int, AktFel int)");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Tranzakció tábla létrehozása   
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "TRANZAKCIO", null);
            if (!rs.next()) {
                createStatement.execute("create table Tranzakcio (TRID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "Nev varchar(255), Kategoria int not null references Kategoria(KID), Datum date, Osszeg int, Penznem int not null references Penznem(PID), Vagyon1 int not null references Vagyon(VID),"
                        + "Vagyon2 int references Vagyon(VID), TranzakcioTipus int not null references TranzakcioTipus(TTID),TranzakcioRendszeresseg int not null references TranzakcioRendszeresseg(TRRID), AktFel int )");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adatbázis feltöltése">
    // adatbázis tesztadatokkal feltöltése
    public void feltolt() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Database connected");
            createStatement = conn.createStatement();
            createStatement.execute("insert into Szemely (Nev,Megj) values('Asztalos Alfonz','férj'),('Asztalosné Szakács Szilvia','feleség'),('Asztalos Albert','gyerek')");
            createStatement.execute("insert into Penznem (Nev,HufRatio) values('HUF',1.0),('EUR',356.5)");
            createStatement.execute("insert into Kategoria (Nev,Megj,BevKid) values('Egyéb','Nem meghatározott',1),('Ajándék','',1), ('Kamatjövedelem','',1), ('Bérleti díj','',1), ('Munkabér','',1), ('Használt tárgy eladása','',1),('Családi pótlék','',1), ('Egyéb','Nem meghatározott',2), ('Bankköltség','',2), ('Bérlet','',2), ('Bevásárlás','',2), ('Rezsi','',2), ('Hiteltörlesztés','',2) ");
            createStatement.execute("insert into Tipus (Forma,Nev) values(1,'Bankszámla'),(1,'Készpénz'),(1,'Szép Kártya'),(2,'Értékpapír Számla'),(3,'Lakáshitel'),(3,'Személyi kölcsön'),(4,'Igatlan'),(4,'Személygépkocsi')");
            createStatement.execute("insert into Vagyon (Szemely, NyitoEgyenleg, NyitoDatum, Penznem, Tipus, Forma, Nev, Megj) values"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),300000,'2009-03-02',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Bankszámla'),1,'OTP Bank',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),25000,'2018-09-11',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Készpénz'),1,'Pénztárca',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalosné Szakács Szilvia'),430000,'2012-03-15',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Bankszámla'),1,'MKB Bank',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalosné Szakács Szilvia'),71000,'2016-03-02',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Szép Kártya'),1,'MKB Bank','Étkezésre használható'),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),700000,'2018-05-11',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Értékpapír Számla'),2,'Állampapír 2028/A',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),500000,'2018-06-10',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Értékpapír Számla'),2,'Testla részvény',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),500000,'2015-11-11',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Értékpapír Számla'),2,'OTP részvény',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),8400000,'2019-05-11',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Lakáshitel'),3,'Jelzálog tartozás - lakás','Befektetési célú lakás hitele'),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalosné Szakács Szilvia'),500000,'2013-07-01',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Személyi kölcsön'),3,'Áru vásárlás hitele',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),35000000,'2010-07-01',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Igatlan'),4,'Családi ház',''),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalosné Szakács Szilvia'),25000000,'2010-07-01',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Igatlan'),4,'Lakás','Befektetés célú lakás'),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalosné Szakács Szilvia'),2100000,'2016-08-01',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Személygépkocsi'),4,'Autó1','Opel Astra'),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),2500000,'2017-08-07',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Személygépkocsi'),4,'Autó2','Ford Focus'),"
                    + "((select Szemely.SZID from Szemely where Szemely.NEV='Asztalos Alfonz'),1800000,'2011-02-01',(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Tipus.TID from Tipus where Tipus.NEV='Értékpapír Számla'),1,'OTP Bank','')");
            createStatement.execute("insert into Tranzakcio (Nev,Kategoria, Datum, Osszeg, Penznem, Vagyon1, Vagyon2, TranzakcioTipus, TranzakcioRendszeresseg) values"
                    + "('Beérkezett munkabér',(select Kategoria.KID from Kategoria where Kategoria.NEV='Munkabér'),'2021-10-01',300000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),1,1),"
                    + "('Beérkezett albérleti díj',(select Kategoria.KID from Kategoria where Kategoria.NEV='Bérleti díj'),'2021-10-08',120000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),1,1),"
                    + "('Beérkezett munkabér',(select Kategoria.KID from Kategoria where Kategoria.NEV='Munkabér'),'2021-10-01',250000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),1,1),"
                    + "('Beérkezett munkabér',(select Kategoria.KID from Kategoria where Kategoria.NEV='Munkabér'),'2021-09-01',250000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),1,1),"
                    + "('Családi pótlék',(select Kategoria.KID from Kategoria where Kategoria.NEV='Családi pótlék'),'2021-10-01',20000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),1,1),"
                    + "('Családi pótlék beérk',(select Kategoria.KID from Kategoria where Kategoria.NEV='Családi pótlék'),'2021-09-01',20000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),1,1),"
                    + "('Élemiszer vásárlás',(select Kategoria.KID from Kategoria where Kategoria.NEV='Bevásárlás'),'2021-10-15',11000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),2,1),"
                    + "('Élemiszer vásárlás',(select Kategoria.KID from Kategoria where Kategoria.NEV='Bevásárlás'),'2021-11-15',15000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),2,1),"
                    + "('Csekkbefizetés',(select Kategoria.KID from Kategoria where Kategoria.NEV='Rezsi'),'2021-10-15',30000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),2,1),"
                    + "('Csekkbefizetés',(select Kategoria.KID from Kategoria where Kategoria.NEV='Rezsi'),'2021-11-15',31000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalosné Szakács Szilvia'),2,1),"
                    + "('Kamat átvezetése',(select Kategoria.KID from Kategoria where Kategoria.NEV='Kamatjövedelem'),'2021-10-19',200000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),(select Vagyon.VID from Vagyon where Vagyon.NEV='Állampapír 2028/A'),3,1),"
                    + "('Beérkezett munkabér',(select Kategoria.KID from Kategoria where Kategoria.NEV='Munkabér'),'2021-09-01',300000,(select Penznem.PID from Penznem where Penznem.NEV='HUF'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),(select Vagyon.VID from Vagyon left join Tipus on Vagyon.TIPUS=Tipus.TID left join Szemely on Szemely.SZID=Vagyon.SZEMELY where Tipus.NEV='Bankszámla' and Szemely.NEV='Asztalos Alfonz'),1,1)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adatbázis törlése">
    public void torol() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Database connected");
            createStatement = conn.createStatement();
            createStatement.execute("delete from Tranzakcio where TRID>0");
            createStatement.execute("delete from Vagyon where VID>0");
            createStatement.execute("delete from Tipus where TID>0");
            createStatement.execute("delete from Szemely where SzID>0");
            createStatement.execute("delete from Penznem where PID>0");
            createStatement.execute("delete from Kategoria where KID>0");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AktFelhasznalo táblázat">
    //Aktfelahsználó tábla lekérdezés
    public ArrayList<AktFelhasznalo> getAktFelhasznalo() {
        String sql = "select * from AktFelhasznalo";
        ArrayList<AktFelhasznalo> AktFelhasznalok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            AktFelhasznalok = new ArrayList<>();
            while (rs.next()) {
                AktFelhasznalo aktAktFelhasznalo = new AktFelhasznalo(rs.getInt("AUID"), rs.getInt("AktFelID"));
                AktFelhasznalok.add(aktAktFelhasznalo);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getAktFelhasznalo lekérdezésénél: " + ex);
        }
        return AktFelhasznalok;
    }

    //Aktfelhasznalo tábla új rekord felvitel
    public void addAktFelhasznalo(AktFelhasznalo Valaki) {

        try {
            String sql = "insert into AktFelhasznalo (AUID, AktFelID) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, Valaki.getAktFelID());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új Aktfelhasznalo felvételekor: " + ex);
        }

    }

    //Aktfelhasznalo tábla rekord módosítás
    public void updateAktFelhasznalo(AktFelhasznalo Valaki) {
        try {
            String sql = "update AktFelhasznalo set AktFelUD = ? where AUID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Valaki.getAktFelID());
            preparedStatement.setInt(2, Valaki.getAUID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Aktfelhasznalo módosításánál: " + ex);
        }
    }

    //Aktfelhasznalo tábla rekord törlés
    public void deleteAktFelhasznalo(/*AktFelhasznalo Valaki*/) {
        try {
            String sql = "delete from AktFelhasznalo where AUID = 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba az Aktfelhasznalo  törlésénél: " + ex);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Felhasznalok táblázat">
    //Felhasznalo tábla lekérdezés
    public ArrayList<Felhasznalo> getFelhasznalo() {
        String sql = "select * from Felhasznalo";
        ArrayList<Felhasznalo> Felhasznalok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Felhasznalok = new ArrayList<>();
            while (rs.next()) {
                Felhasznalo aktFelhasznalo = new Felhasznalo(rs.getInt("UID"), rs.getString("nev"), rs.getString("jelszo"));
                Felhasznalok.add(aktFelhasznalo);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getFelhasznalo lekérdezésénél: " + ex);
        }
        return Felhasznalok;
    }

    //Felhasznalo tábla lekérdezés név alapján szűrve
    public ArrayList<Felhasznalo> getFelhasznaloNev(String x) {
        String sql = "select * from Felhasznalo where Felhasznalo.nev='" + x + "'";
        ArrayList<Felhasznalo> Felhasznalok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Felhasznalok = new ArrayList<>();
            while (rs.next()) {
                Felhasznalo aktFelhasznalo = new Felhasznalo(rs.getInt("UID"), rs.getString("nev"), rs.getString("jelszo"));
                Felhasznalok.add(aktFelhasznalo);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getFelhasznaloNev lekérdezésénél: " + ex);
        }
        return Felhasznalok;
    }

    //Felhasznalo tábla új rekord felvitel
    public void addFelhasznalo(Felhasznalo Valaki) {
        try {
            String sql = "insert into Felhasznalo (nev, jelszo) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Valaki.getNev());
            preparedStatement.setString(2, Valaki.getJelszo());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új felhasznalo felvételekor: " + ex);
        }

    }

    //Felhasznalo tábla rekord módosítás
    public void updateFelhasznalo(Felhasznalo Valaki) {
        try {
            String sql = "update Felhasznalo set nev = ?, jelszo = ? where UID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Valaki.getNev());
            preparedStatement.setString(2, Valaki.getJelszo());
            preparedStatement.setInt(3, Valaki.getId());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a felhasználó módosításánál: " + ex);
        }
    }

    //Felhasznalo tábla rekord törlés
    public void deleteFelhasznalo(Felhasznalo Valaki) {
        try {
            String sql = "delete from Felhasznalo where UID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Valaki.getId());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a felhasználó törlésénél: " + ex);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Személyek táblázat">
    //Szemely tábla lekérdezés
    public ArrayList<Szemely> getSzemely() {
        String sql = "select * from szemely inner join AktFelhasznalo AK on Szemely.AKTFEL=AK.AKTFELID";
        ArrayList<Szemely> Szemelyek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Szemelyek = new ArrayList<>();
            while (rs.next()) {
                Szemely aktSzemely = new Szemely(rs.getInt("SzID"), rs.getString("nev"), rs.getString("megj"));
                Szemelyek.add(aktSzemely);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getSzemely lekérdezésénél: " + ex);
        }
        return Szemelyek;
    }

    //Szemely tábla lekérdezés névre szűrve
    public ArrayList<Szemely> getSzemelyNev(String x) {
        String sql = "select * from Szemely inner join AktFelhasznalo AK on Szemely.AKTFEL=AK.AKTFELID where Szemely.nev='" + x + "'";
        ArrayList<Szemely> Szemelyek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Szemelyek = new ArrayList<>();
            while (rs.next()) {
                Szemely aktSzemely = new Szemely(rs.getInt("SzID"), rs.getString("nev"), rs.getString("megj"));
                Szemelyek.add(aktSzemely);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getSzemelyNev lekérdezésénél: " + ex);
        }
        return Szemelyek;
    }

    //Szemely tábla rekord felvitele
    public void addSzemely(Szemely Valaki) {

        try {
            String sql = "insert into SZEMELY (nev, megj, AktFel) values(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Valaki.getName());
            preparedStatement.setString(2, Valaki.getMegjegyzes());
            preparedStatement.setInt(3, Valaki.getAktFel());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új személy felvételekor: " + ex);
        }

    }

    //Szemely tábla rekord módosítása
    public void updateSzemely(Szemely Valaki) {
        try {
            String sql = "update SZEMELY set nev = ?, megj = ? where SzID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Valaki.getName());
            preparedStatement.setString(2, Valaki.getMegjegyzes());
            preparedStatement.setInt(3, Valaki.getId());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a személy módosításánál: " + ex);
        }
    }

    //Szemely tábla rekord törlése
    public void deleteSzemely(Szemely Valaki) {
        try {
            String sql = "delete from SZEMELY where SzID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Valaki.getId());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a személy törlésénél: " + ex);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Pénznem táblázat">
    //Penznem tábla lekérdezés
    public ArrayList<Penznem> getPenznem() {
        String sql = "select * from Penznem inner join AktFelhasznalo AK on Penznem.AKTFEL=AK.AKTFELID";
        ArrayList<Penznem> Penznemek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Penznemek = new ArrayList<>();
            while (rs.next()) {
                Penznem aktPenznem = new Penznem(rs.getInt("PID"), rs.getString("nev"), rs.getFloat("HufRatio"));
                Penznemek.add(aktPenznem);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getPenznem lekérdezésénél: " + ex);
        }
        return Penznemek;
    }

    //Penznem tábla lekérdezés névre szűrve
    public ArrayList<Penznem> getPenznemID(String x) {
        String sql = "select * from Penznem inner join AktFelhasznalo AK on Penznem.AKTFEL=AK.AKTFELID where Penznem.nev='" + x + "'";
        ArrayList<Penznem> Penznemek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Penznemek = new ArrayList<>();
            while (rs.next()) {
                Penznem aktPenznem = new Penznem(rs.getInt("PID"), rs.getString("nev"), rs.getFloat("HufRatio"));
                Penznemek.add(aktPenznem);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getPenznemID lekérdezésénél: " + ex);
        }
        return Penznemek;
    }

    //Penznem tábla lekérdezés ID-ra szűrve
    public ArrayList<Penznem> getPenznemIDkeres(int x) {
        String sql = "select * from Penznem inner join AktFelhasznalo AK on Penznem.AKTFEL=AK.AKTFELID where Penznem.PID=" + x + "";
        ArrayList<Penznem> Penznemek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Penznemek = new ArrayList<>();
            while (rs.next()) {
                Penznem aktPenznem = new Penznem(rs.getInt("PID"), rs.getString("nev"), rs.getFloat("HufRatio"));
                Penznemek.add(aktPenznem);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getPenznemIDkeres lekérdezésénél: " + ex);
        }
        return Penznemek;
    }

    //Penznem tábla rekord felvitele
    public void addPenznem(Penznem Valuta) {

        try {
            String sql = "insert into PENZNEM (nev, HufRatio, AktFel) values(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Valuta.getName());
            preparedStatement.setFloat(2, Valuta.getHUFRatio());
            preparedStatement.setInt(3, Valuta.getAktFel());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új pénznem felvételekor: " + ex);
        }

    }

    //Penznem tábla rekord módosítása
    public void updatePenznem(Penznem Valuta) {
        try {
            String sql = "update Penznem set nev = ?, HufRatio = ? where PID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Valuta.getName());
            preparedStatement.setFloat(2, Valuta.getHUFRatio());
            preparedStatement.setInt(3, Valuta.getId());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a pénznem módosításánál: " + ex);
        }
    }

    //Penznem tábla rekord törlése
    public void deletePenznem(Penznem Valuta) {
        try {
            String sql = "delete from PENZNEM where PID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Valuta.getId());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a pénznem módosításánál: " + ex);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Forma táblázat">
    //Forma tábla lekérdezés
    public ArrayList<Forma> getForma() {
        String sql = "select * from Forma";
        ArrayList<Forma> FormaTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            FormaTipusok = new ArrayList<>();
            while (rs.next()) {
                Forma aktForma = new Forma(rs.getInt("FID"), rs.getString("nev"));
                FormaTipusok.add(aktForma);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getForma lekérdezésésél" + ex);
        }
        return FormaTipusok;
    }

    //Forma tábla lekérdezés névre szűrve
    public ArrayList<Forma> getFormaID(String x) {
        String sql = "select * from Forma where Forma.nev = '" + x + "'";
        ArrayList<Forma> FormaTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            FormaTipusok = new ArrayList<>();
            while (rs.next()) {
                Forma aktForma = new Forma(rs.getInt("FID"), rs.getString("nev"));
                FormaTipusok.add(aktForma);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getFormaID lekérdezésésél" + ex);
        }
        return FormaTipusok;
    }

    //Forma tábla rekord felvitele
    public void addForma(Forma FormaPeldany) {

        try {
            String sql = "insert into Forma(FID, nev) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, FormaPeldany.getFID());
            preparedStatement.setString(2, FormaPeldany.getNev());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új Forma felvételekor: " + ex);
        }
    }

    //Forma tábla rekord módosítása
    public void updateForma(Forma FormaPeldany) {
        try {
            String sql = "update Forma set nev = ? where FID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, FormaPeldany.getNev());
            preparedStatement.setInt(2, FormaPeldany.getFID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Forma módosításánál: " + ex);
        }
    }

    //Forma tábla rekord törlése
    public void deleteForma(Forma FormaPeldany) {
        try {
            String sql = "delete from FORMA where FID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, FormaPeldany.getFID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Forma törlésésnél: " + ex);
        }
    }

    public void formaFeltolt() {
        Forma formaAkt = new Forma();
        for (int i = 1; i < 5; i++) {
            formaAkt.setFID(i);
            formaAkt.setNev(peldanyFormak[i - 1]);
            addForma(formaAkt);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Tipus táblázat">
    //Tipus tábla lekérdezés
    public ArrayList<Tipus> getTipus() {
        String sql = "select * from Tipus inner join AktFelhasznalo AK on Tipus.AKTFEL=AK.AKTFELID";
        ArrayList<Tipus> TipusTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TipusTipusok = new ArrayList<>();
            while (rs.next()) {
                Tipus aktTipus = new Tipus(rs.getInt("TID"), rs.getInt("Forma"), rs.getString("nev"));
                TipusTipusok.add(aktTipus);
                System.out.println("Tipustípusok összegyűjtve.");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTipus lekérdezésésél" + ex);
        }
        return TipusTipusok;
    }

    //Tipus és Forma tábla lekérdezés
    public ArrayList<Tipus> getTipusForma() {
        String sql = "select Tipus.*, Forma.NEV FormaNev from Tipus inner join Forma on Forma.FID=Tipus.FORMA inner join AktFelhasznalo AK on Tipus.AKTFEL=AK.AKTFELID";
        ArrayList<Tipus> TipusTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            TipusTipusok = new ArrayList<>();
            while (rs.next()) {
                Tipus aktTipus = new Tipus(rs.getInt("TID"), rs.getInt("Forma"), rs.getString("nev"), rs.getString("FormaNev"));
                TipusTipusok.add(aktTipus);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTipusForma lekérdezésésél" + ex);
        }
        return TipusTipusok;
    }

    //Tipus tábla lekérdezés forma értékre szűrve
    public ArrayList<Tipus> getTipusParam(String x) {
        String sql = "select * from Tipus inner join AktFelhasznalo AK on Tipus.AKTFEL=AK.AKTFELID where Tipus.Forma=" + x + "";
        ArrayList<Tipus> TipusTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TipusTipusok = new ArrayList<>();
            while (rs.next()) {
                Tipus aktTipus = new Tipus(rs.getInt("TID"), rs.getInt("Forma"), rs.getString("nev"));
                TipusTipusok.add(aktTipus);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTipusParam lekérdezésésél" + ex);
        }
        return TipusTipusok;
    }

    //Tipus tábla lekérdezés névre szűrve
    public ArrayList<Tipus> getTipusID(String x) {
        String sql = "select * from Tipus inner join AktFelhasznalo AK on Tipus.AKTFEL=AK.AKTFELID where Tipus.nev='" + x + "'";
        ArrayList<Tipus> TipusTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TipusTipusok = new ArrayList<>();
            while (rs.next()) {
                Tipus aktTipus = new Tipus(rs.getInt("TID"), rs.getInt("Forma"), rs.getString("nev"));
                TipusTipusok.add(aktTipus);
                System.out.println("Tipustípusok összegyűjtve.");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTipusID lekérdezésésél" + ex);
        }
        return TipusTipusok;
    }

    //Tipus tábla rekord felvitele
    public void addTipus(Tipus TipusPeldany) {
        try {
            String sql = "insert into Tipus(Forma, nev, AktFel) values(?,?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, TipusPeldany.getForma());
            preparedStatement.setString(2, TipusPeldany.getNev());
            preparedStatement.setInt(3, TipusPeldany.getAktFel());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új példány Tipus felvételekor: " + ex);
        }

    }

    //Tipus tábla rekord mődosítása
    public void updateTipus(Tipus TipusPeldany) {
        try {
            String sql = "update Tipus set nev = ?, Forma = ? where TID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, TipusPeldany.getNev());
            preparedStatement.setInt(2, TipusPeldany.getForma());
            preparedStatement.setInt(3, TipusPeldany.getTID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Tipus módosításánál: " + ex);
        }
    }

    //Tipus tábla rekord törlése
    public void deleteTipus(Tipus TipusPeldany) {
        try {
            String sql = "delete from TIPUS where TID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, TipusPeldany.getTID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Tipus módosításánál: " + ex);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Vagyon táblázat">
    //Vagyon tábla lekérdezés
    public ArrayList<Vagyon> getVagyon() {
        String sql = "select * from Vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID";
        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getInt("VID"), rs.getInt("NyitoEgyenleg"), rs.getInt("Szemely"), rs.getInt("Penznem"), rs.getInt("Tipus"), rs.getInt("Forma"), rs.getString("Nev"), rs.getString("megj"), LocalDate.parse(rs.getObject("NyitoDatum").toString()));
                VagyonTipusok.add(aktVagyon);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyon lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla vagyontárgy mennyiség lekérdezés személyre szűrve
    public ArrayList<Vagyon> getVagyonSzemelyekKapcsolat(int y) {
        String sql = "Select count(VID) as Vagyondb from Vagyon where Vagyon.Szemely=" + y + "";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getInt(1));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonSzemelyekKapcsolat lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés forma értékre szűrve
    public ArrayList<Vagyon> getVagyonParam(String x) {
        String sql = "Select Vagyon.VID VID, Vagyon.NEV NEV, Tipus.NEV TIPUSNEV, Vagyon.NYITOEGYENLEG NYITOEGYENLEG, Penznem.NEV PENZNEMNEV, Vagyon.NYITODATUM NYITODATUM, Szemely.NEV SZEMELYNEV, Vagyon.MEGJ MEGJ from Vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join Szemely on Vagyon.SZEMELY=Szemely.SZID inner join Tipus on Vagyon.TIPUS=Tipus.TID inner join Penznem on Vagyon.PENZNEM = Penznem.PID where Vagyon.Forma=" + x + "";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), LocalDate.parse(rs.getObject(6).toString()), rs.getString(7), rs.getString(8));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonParam lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés személy és forma értékre szűrve
    public ArrayList<Vagyon> getVagyonParamNev(String x, Integer y) {
        String sql = "Select Vagyon.VID VID, Vagyon.NEV NEV, Tipus.NEV TIPUSNEV, Vagyon.NYITOEGYENLEG NYITOEGYENLEG, Penznem.NEV PENZNEMNEV, Vagyon.NYITODATUM NYITODATUM, Szemely.NEV SZEMELYNEV, Vagyon.MEGJ MEGJ from Vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join Szemely on Vagyon.SZEMELY=Szemely.SZID inner join Tipus on Vagyon.TIPUS=Tipus.TID inner join Penznem on Vagyon.PENZNEM = Penznem.PID where Vagyon.Szemely=" + y + " and Vagyon.Forma=" + x + "";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), LocalDate.parse(rs.getObject(6).toString()), rs.getString(7), rs.getString(8));
                VagyonTipusok.add(aktVagyon);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonParamNev lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés forma értékre és dátum értékre szűrve
    public ArrayList<Vagyon> getVagyonParam2(String x, LocalDate d) {
        String sql = "Select Vagyon.VID VID, Vagyon.NEV NEV, Tipus.NEV TIPUSNEV, Vagyon.NYITOEGYENLEG NYITOEGYENLEG, Penznem.NEV PENZNEMNEV, Vagyon.NYITODATUM NYITODATUM, Szemely.NEV SZEMELYNEV, Vagyon.MEGJ MEGJ from Vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join Szemely on Vagyon.SZEMELY=Szemely.SZID inner join Tipus on Vagyon.TIPUS=Tipus.TID inner join Penznem on Vagyon.PENZNEM = Penznem.PID where (Vagyon.NYITODATUM <'" + d + "'" + " and Vagyon.Forma=" + x + ")";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), LocalDate.parse(rs.getObject(6).toString()), rs.getString(7), rs.getString(8));
                VagyonTipusok.add(aktVagyon);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonParam2 lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés forma értékre és dátum értékre és névre szűrve
    public ArrayList<Vagyon> getVagyonParam3(String x, LocalDate d, String nev, String vagyon) {
        String sql = "Select Vagyon.VID VID, Vagyon.NEV NEV, Tipus.NEV TIPUSNEV, Vagyon.NYITOEGYENLEG NYITOEGYENLEG, Penznem.NEV PENZNEMNEV, Vagyon.NYITODATUM NYITODATUM, Szemely.NEV SZEMELYNEV, Vagyon.MEGJ MEGJ from Vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join Szemely on Vagyon.SZEMELY=Szemely.SZID inner join Tipus on Vagyon.TIPUS=Tipus.TID inner join Penznem on Vagyon.PENZNEM = Penznem.PID where (Vagyon.NYITODATUM <'" + d + "'" + " and Szemely.Nev like '" + nev + "' and Vagyon.Nev like '" + vagyon + "' and Vagyon.Forma=" + x + ")";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), LocalDate.parse(rs.getObject(6).toString()), rs.getString(7), rs.getString(8));
                VagyonTipusok.add(aktVagyon);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonParam3 lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés személy és vagyon név+típusnév értékre szűrve
    public ArrayList<Vagyon> getVagyonAndTipusParamID(String x, int y) {
        String sql = "select vagyon.VID from vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join tipus on vagyon.TIPUS=tipus.TID where vagyon.SZEMELY=" + y + " and vagyon.NEV ||' ' || tipus.NEV='" + x + "'";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getInt(1));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonAndTipusParamID lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés személy értékre szűrve
    public ArrayList<Vagyon> getVagyonAndTipusParam(int x) {
        String sql = "select vagyon.NEV ||' ' || tipus.NEV from vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join tipus on vagyon.TIPUS=tipus.TID where vagyon.SZEMELY=" + x + "";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getString(1));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonAndTipusParam lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés személy értékre szűrve, aktív eszközök
    public ArrayList<Vagyon> getVagyonAndTipusParam2(int x) {
        String sql = "select vagyon.NEV ||' ' || tipus.NEV, vagyon.VID from vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join tipus on vagyon.TIPUS=tipus.TID where vagyon.SZEMELY=" + x + " and vagyon.FORMA=1";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getString(1), rs.getInt(2));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonAndTipusParam2 lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés, aktív eszközök
    public ArrayList<Vagyon> getVagyonAndTipusParam3() {
        String sql = "select vagyon.NEV ||' ' || tipus.NEV, vagyon.VID from vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join tipus on vagyon.TIPUS=tipus.TID where vagyon.FORMA=1";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getString(1), rs.getInt(2));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonAndTipusParam3 lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés személy értékre, vagyonID-ra szűrve
    public ArrayList<Vagyon> getVagyonAndTipusParamSelected(int x, int y) {
        String sql = "select vagyon.NEV ||' ' || tipus.NEV from vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID inner join tipus on vagyon.TIPUS=tipus.TID where vagyon.SZEMELY=" + x + " and vagyon.Vid=" + y + "";

        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {
                Vagyon aktVagyon = new Vagyon(rs.getString(1));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonAndTipusParamSelected lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés vagyon név értékre szűrve
    public ArrayList<Vagyon> getVagyonIDnevbol(String x) {
        String sql = "select * from Vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID where Vagyon.nev='" + x + "'";
        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {

                Vagyon aktVagyon = new Vagyon(rs.getInt("VID"), rs.getInt("NyitoEgyenleg"), rs.getInt("Szemely"), rs.getInt("Penznem"), rs.getInt("Tipus"), rs.getInt("Forma"), rs.getString("Nev"), rs.getString("megj"), LocalDate.parse(rs.getObject("NyitoDatum").toString()));
                VagyonTipusok.add(aktVagyon);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonIDnevbol lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla lekérdezés vagyonID-ra szűrve
    public ArrayList<Vagyon> getVagyonIDidbol(int x) {
        String sql = "select * from Vagyon inner join AktFelhasznalo AK on Vagyon.AKTFEL=AK.AKTFELID where Vagyon.VID=" + x + "";
        ArrayList<Vagyon> VagyonTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            VagyonTipusok = new ArrayList<>();
            while (rs.next()) {

                Vagyon aktVagyon = new Vagyon(rs.getInt("VID"), rs.getInt("NyitoEgyenleg"), rs.getInt("Szemely"), rs.getInt("Penznem"), rs.getInt("Tipus"), rs.getInt("Forma"), rs.getString("Nev"), rs.getString("megj"), LocalDate.parse(rs.getObject("NyitoDatum").toString()));
                VagyonTipusok.add(aktVagyon);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getVagyonIDidbol lekérdezésésél" + ex);
        }
        return VagyonTipusok;
    }

    //Vagyon tábla rekord felvitele
    public void addVagyon(Vagyon VagyonPeldany) {

        try {
            String sql = "insert into Vagyon(Szemely, NyitoDatum, Penznem, Tipus, Forma, Nev, Megj, NyitoEgyenleg, AktFel) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, VagyonPeldany.getSzemely());
            preparedStatement.setObject(2, java.sql.Date.valueOf(VagyonPeldany.getNyitoDatum()));
            preparedStatement.setInt(3, VagyonPeldany.getPenznem());
            preparedStatement.setInt(4, VagyonPeldany.getTipus());
            preparedStatement.setInt(5, VagyonPeldany.getForma());
            preparedStatement.setString(6, VagyonPeldany.getNev());
            preparedStatement.setString(7, VagyonPeldany.getMegj());
            preparedStatement.setInt(8, VagyonPeldany.getNyitoEgyenleg());
            preparedStatement.setInt(9, VagyonPeldany.getAktFel());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új Vagyon példány felvételekor: " + ex);
        }

    }

    //Vagyon tábla rekord módosítása
    public void updateVagyon(Vagyon VagyonPeldany) {
        try {
            String sql = "update Vagyon set Szemely = ?, NyitoDatum = ?, Penznem = ?, Tipus = ?, Forma = ?, Nev = ?, Megj=?, NyitoEgyenleg=? where VID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, VagyonPeldany.getSzemely());
            preparedStatement.setObject(2, java.sql.Date.valueOf(VagyonPeldany.getNyitoDatum()));
            preparedStatement.setInt(3, VagyonPeldany.getPenznem());
            preparedStatement.setInt(4, VagyonPeldany.getTipus());
            preparedStatement.setInt(5, VagyonPeldany.getForma());
            preparedStatement.setString(6, VagyonPeldany.getNev());
            preparedStatement.setString(7, VagyonPeldany.getMegj());
            preparedStatement.setInt(8, VagyonPeldany.getNyitoEgyenleg());
            preparedStatement.setInt(9, VagyonPeldany.getVID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Vagyon módosításánál: " + ex);
        }
    }

    //Vagyon tábla rekord törlése
    public void deleteVagyon(Vagyon VagyonPeldany) {
        try {
            String sql = "delete from Vagyon where VID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, VagyonPeldany.getVID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Forma módosításánál: " + ex);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="TranzakcióTípusok táblázat">
    //Tranzakciotipus tábla lekérdezés
    public ArrayList<TranzakcioTipus> getTranzakcioTipus() {
        String sql = "select * from TranzakcioTipus";
        ArrayList<TranzakcioTipus> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {
                TranzakcioTipus aktTipus = new TranzakcioTipus(rs.getInt("TTID"), rs.getString("nev"));
                TranzakcioTipusok.add(aktTipus);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioTipus lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakciotipus tábla rekord felvitele
    public void addTranzakcioTipus(TranzakcioTipus TranzakcioTipusPeldany) {

        try {
            String sql = "insert into TranzakcioTipus(TTID, nev) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, TranzakcioTipusPeldany.getTTID());
            preparedStatement.setString(2, TranzakcioTipusPeldany.getNev());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új példány Tranzakciótípus felvételekor: " + ex);
        }

    }

    //Tranzakciotipus tábla rekord módosítása
    public void updateTranzakcioTipus(TranzakcioTipus TranzakcioTipusPeldany) {
        try {
            String sql = "update TranzakcioTipus set Nev = ? where TTID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, TranzakcioTipusPeldany.getNev());
            preparedStatement.setInt(2, TranzakcioTipusPeldany.getTTID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Tranzakciótípus módosításánál: " + ex);
        }
    }

    //Tranzakciotipus tábla rekord törlése
    public void deleteTranzakcioTipus(TranzakcioTipus TranzakcioTipusPeldany) {
        try {
            String sql = "delete from TRANZAKCIOTIPUS where TTID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, TranzakcioTipusPeldany.getTTID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Tranzakciótípus törlésekor " + ex);
        }
    }

    //Tranzakciotipus tábla rekordok feltöltése
    public void tranzakcioTipusFeltolt() {
        TranzakcioTipus tranzakcioTipusAkt = new TranzakcioTipus();
        for (int i = 1; i < 13; i++) {
            tranzakcioTipusAkt.setTTID(i);
            tranzakcioTipusAkt.setNev(peldanyTranzakciok[i - 1]);
            addTranzakcioTipus(tranzakcioTipusAkt);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="TranzakcióRendszeresseg táblázat">
    //Tranzakciorendszeresseg tábla lekérdezés
    public ArrayList<TranzakcioRendszeresseg> getTranzakcioRendszeresseg() {
        String sql = "select * from TranzakcioRendszeresseg";
        ArrayList<TranzakcioRendszeresseg> TranzakcioReszeressegek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioReszeressegek = new ArrayList<>();
            while (rs.next()) {
                TranzakcioRendszeresseg aktTipus = new TranzakcioRendszeresseg(rs.getInt("TRRID"), rs.getString("nev"));
                TranzakcioReszeressegek.add(aktTipus);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioRendszeresseg lekérdezésésél" + ex);
        }
        return TranzakcioReszeressegek;
    }

    //Tranzakciorendszeresseg tábla lekérdezés név alapján szűrve
    public ArrayList<TranzakcioRendszeresseg> getTranzakcioRendszeressegID(String x) {
        String sql = "select * from TranzakcioRendszeresseg where TranzakcioRendszeresseg.nev='" + x + "'";
        ArrayList<TranzakcioRendszeresseg> TranzakcioReszeressegek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioReszeressegek = new ArrayList<>();
            while (rs.next()) {
                TranzakcioRendszeresseg aktTipus = new TranzakcioRendszeresseg(rs.getInt("TRRID"), rs.getString("nev"));
                TranzakcioReszeressegek.add(aktTipus);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioRendszeressegID lekérdezésésél" + ex);
        }
        return TranzakcioReszeressegek;
    }

    //Tranzakciorendszeresseg tábla lekérdezés TRRID-ra szűrve
    public ArrayList<TranzakcioRendszeresseg> getTranzakcioRendszeressegIDidbol(int x) {
        String sql = "select * from TranzakcioRendszeresseg where TranzakcioRendszeresseg.TRRID=" + x + "";
        ArrayList<TranzakcioRendszeresseg> TranzakcioReszeressegek = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioReszeressegek = new ArrayList<>();
            while (rs.next()) {
                TranzakcioRendszeresseg aktTipus = new TranzakcioRendszeresseg(rs.getInt("TRRID"), rs.getString("nev"));
                TranzakcioReszeressegek.add(aktTipus);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioRendszeressegIDidbol lekérdezésésél" + ex);
        }
        return TranzakcioReszeressegek;
    }

    //Tranzakciorendszeresseg tábla rekord felvitele
    public void addTranzakcioRendszeresseg(TranzakcioRendszeresseg TranzakcioRendszeressegPeldany) {

        try {
            String sql = "insert into TranzakcioRendszeresseg(TRRID, nev) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, TranzakcioRendszeressegPeldany.getTRRID());
            preparedStatement.setString(2, TranzakcioRendszeressegPeldany.getNev());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új példány TranzakcióRendszeresseg felvételekor: " + ex);
        }
    }

    //Tranzakciorendszeresseg tábla rekord módosítása
    public void updateTranzakcioRendszeresseg(TranzakcioRendszeresseg TranzakcioRendszeressegPeldany) {
        try {
            String sql = "update TranzakcioRendszeresseg set Nev = ? where TRRID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, TranzakcioRendszeressegPeldany.getNev());
            preparedStatement.setInt(2, TranzakcioRendszeressegPeldany.getTRRID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a TranzakcióRendszeresseg módosításánál: " + ex);
        }
    }

    //Tranzakciorendszeresseg tábla rekord törlése
    public void deleteTranzakcioRendszeresseg(TranzakcioRendszeresseg TranzakcioRendszeressegPeldany) {
        try {
            String sql = "delete from TRANZAKCIORENDSZERESSEG where TRRID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, TranzakcioRendszeressegPeldany.getTRRID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a TranzakcióRendszeresseg törlésekor " + ex);
        }
    }

    //Tranzakciorendszeresseg tábla rekordok feltötlése
    public void tranzakcioRendszeressegFeltolt() {
        TranzakcioRendszeresseg tranzakcioRendszeressegAkt = new TranzakcioRendszeresseg();
        for (int i = 1; i < 4; i++) {
            tranzakcioRendszeressegAkt.setTRRID(i);
            tranzakcioRendszeressegAkt.setNev(peldanyTranzakcioRendszeresseg[i - 1]);
            addTranzakcioRendszeresseg(tranzakcioRendszeressegAkt);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Kategoria táblázat">  
    //Kategoria tábla lekérdezés
    public ArrayList<Kategoria> getKategoria() {
        String sql = "select * from Kategoria inner join AktFelhasznalo AK on Kategoria.AKTFEL=AK.AKTFELID";
        ArrayList<Kategoria> Kategoriak = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Kategoriak = new ArrayList<>();
            while (rs.next()) {
                Kategoria aktKategoria = new Kategoria(rs.getInt("KID"), rs.getInt("BevKid"), rs.getString("Nev"), rs.getString("Megj"));
                Kategoriak.add(aktKategoria);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getKategoria lekérdezésénél: " + ex);
        }
        return Kategoriak;
    }

    //Kategoria tábla lekérdezés bevétel-kiadás kategória alapján szűrve
    public ArrayList<Kategoria> getKategoriaParam(String x) {
        String sql = "select * from Kategoria inner join AktFelhasznalo AK on Kategoria.AKTFEL=AK.AKTFELID where Kategoria.BevKid=" + x + "";
        ArrayList<Kategoria> Kategoriak = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Kategoriak = new ArrayList<>();
            while (rs.next()) {
                Kategoria aktKategoria = new Kategoria(rs.getInt("KID"), rs.getInt("BevKid"), rs.getString("Nev"), rs.getString("Megj"));
                Kategoriak.add(aktKategoria);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getKategoriaParam lekérdezésénél: " + ex);
        }
        return Kategoriak;
    }

    //Kategoria tábla lekérdezés bevétel-kiadás kategória  és kategória név alapján szűrve
    public ArrayList<Kategoria> getKategoriaID(String x, int y) {
        String sql = "select * from Kategoria inner join AktFelhasznalo AK on Kategoria.AKTFEL=AK.AKTFELID where Kategoria.nev='" + x + "' and Kategoria.BevKid=" + y + "";
        ArrayList<Kategoria> Kategoriak = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            Kategoriak = new ArrayList<>();
            while (rs.next()) {
                Kategoria aktKategoria = new Kategoria(rs.getInt("KID"), rs.getInt("BevKid"), rs.getString("Nev"), rs.getString("Megj"));
                Kategoriak.add(aktKategoria);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getKategoriaID lekérdezésénél: " + ex);
        }
        return Kategoriak;
    }

    //Kategoria tábla példány felvitele
    public void addKategoria(Kategoria KategoriaPeldany) {

        try {
            String sql = "insert into Kategoria(BevKid, nev, megj,AktFel) values(?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, KategoriaPeldany.getBevKid());
            preparedStatement.setString(2, KategoriaPeldany.getNev());
            preparedStatement.setString(3, KategoriaPeldany.getMegj());
            preparedStatement.setInt(4, KategoriaPeldany.getAktFel());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új példány Kategória felvételekor: " + ex);
        }

    }

    //Kategoria tábla példány módosítása
    public void updateKategoria(Kategoria KategoriaPeldany) {
        try {
            String sql = "update Kategoria set Nev = ?, BevKid=?, Megj=? where KID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, KategoriaPeldany.getNev());
            preparedStatement.setInt(2, KategoriaPeldany.getBevKid());
            preparedStatement.setString(3, KategoriaPeldany.getMegj());
            preparedStatement.setInt(4, KategoriaPeldany.getKID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Kategoria módosításánál: " + ex);
        }
    }

    //Kategoria tábla példány törlése
    public void deleteKategoria(Kategoria KategoriaPeldany) {
        try {
            String sql = "delete from Kategoria where KID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, KategoriaPeldany.getKID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Kategoria törlésekor " + ex);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Tranzakcio táblázat">
    //Tranzakcio tábla lekérdezés
    public ArrayList<Tranzakcio> geTranzakcio() {
        String sql = "select * from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("TRID"), rs.getString("Nev"), rs.getInt("Kategoria"), LocalDate.parse(rs.getObject("Datum").toString()), rs.getInt("Osszeg"), rs.getInt("Penznem"), rs.getInt("Vagyon1"), rs.getInt("Vagyon2"), rs.getInt("TranzakcioTipus"), rs.getInt("TranzakcioRendszeresseg"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes geTranzakcio lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla lekérdezés tranzakciótípusra szűrve
    public ArrayList<Tranzakcio> geTranzakcioParam(String x) {
        String sql = "select Tranzakcio.TRID, Tranzakcio.NEV, Tranzakcio.KATEGORIA, Tranzakcio.DATUM, Tranzakcio.OSSZEG, Tranzakcio.PENZNEM, Tranzakcio.VAGYON1, Tranzakcio.VAGYON2, Tranzakcio.TRANZAKCIOTIPUS, Tranzakcio.TRANZAKCIORENDSZERESSEG, Kategoria.NEV KategoriaNev, TranzakcioRendszeresseg.NEV TranzakcioRendszeressegNev, Sz1.NEV SzemelyNev1, V1.NEV VagyonNev1, T1.NEV TipusNev1, Sz2.NEV SzemelyNev2, V2.NEV VagyonNev2, T2.NEV TipusNev2, PN.NEV PenzTipus\n"
                + "from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Kategoria on Kategoria.KID=Tranzakcio.KATEGORIA inner join TranzakcioRendszeresseg on TranzakcioRendszeresseg.TRRID=Tranzakcio.TRANZAKCIORENDSZERESSEG inner join Vagyon V1 on V1.VID=Tranzakcio.VAGYON1 \n"
                + "inner join Penznem PN on PN.PID=Tranzakcio.PENZNEM   inner join Szemely Sz1 on V1.SZEMELY=Sz1.SZID inner join Tipus T1 on T1.TID= V1.TIPUS inner join Vagyon V2 on V2.VID=Tranzakcio.VAGYON2 inner join Szemely Sz2 on Sz2.SZID=V2.SZEMELY inner join Tipus T2 on T2.TID=V2.TIPUS where Tranzakcio.Tranzakciotipus=" + x + "";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("TRID"), rs.getString("Nev"), rs.getInt("Kategoria"), LocalDate.parse(rs.getObject("Datum").toString()), rs.getInt("Osszeg"), rs.getInt("Penznem"), rs.getInt("Vagyon1"), rs.getInt("Vagyon2"), rs.getInt("TranzakcioTipus"), rs.getInt("TranzakcioRendszeresseg"), rs.getString("KategoriaNev"), rs.getString("TranzakcioRendszeressegNev"), rs.getString("SzemelyNev1"), rs.getString("VagyonNev1"), rs.getString("TipusNev1"), rs.getString("SzemelyNev2"), rs.getString("VagyonNev2"), rs.getString("TipusNev2"), rs.getString("PenzTipus"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes geTranzakcioParam lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla lekérdezés tranzakciótípusra és dátumra szűrve
    public ArrayList<Tranzakcio> getTranzakcioSum(int tr, int m, int y) {
        String sql = "select sum(Tranzakcio.OSSZEG*Penznem.HUFRATIO) Summa from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where Tranzakcio.Tranzakciotipus=" + tr + " and month(Tranzakcio.DATUM)=" + m + "and year(Tranzakcio.DATUM)=" + y + "";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("Summa"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioSum lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla tranzakció összeg lekérdezés tranzakciótípusra, dátumra, rendszerességre szűrve
    public ArrayList<Tranzakcio> getTranzakcioSumExpectedIn(int tr, int m, int y) {
        String sql = "select sum(Tranzakcio.OSSZEG*Penznem.HUFRATIO) Summa from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where (Tranzakcio.Tranzakciotipus=" + tr + "  and year(Tranzakcio.DATUM)>" + y + " and Tranzakcio.TRANZAKCIORENDSZERESSEG=2) or  (Tranzakcio.Tranzakciotipus=" + tr + " and month(Tranzakcio.DATUM)>=" + m + " and year(Tranzakcio.DATUM)=" + y + " and Tranzakcio.TRANZAKCIORENDSZERESSEG=2) or (Tranzakcio.Tranzakciotipus=" + tr + " and month(Tranzakcio.DATUM)=" + m + " and year(Tranzakcio.DATUM)=" + y + " and Tranzakcio.TRANZAKCIORENDSZERESSEG=1) or (Tranzakcio.Tranzakciotipus=" + tr + " and month(Tranzakcio.DATUM)=" + m + " and year(Tranzakcio.DATUM)>=" + y + " and Tranzakcio.TRANZAKCIORENDSZERESSEG=3)";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("Summa"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioSumExpectedIn lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla tranzakció összeg lekérdezés tranzakciótípusra, dátumra, vagyon1 típusra szűrve
    public ArrayList<Tranzakcio> getTranzakcioBalancedatum(int tr, int v, LocalDate d) {
        String sql = "select sum(Tranzakcio.OSSZEG*Penznem.HUFRATIO) Summa from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where (Tranzakcio.Tranzakciotipus=" + tr + "and Tranzakcio.Datum<'" + d + "' " + " and Tranzakcio.VAGYON1=" + v + ")";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("Summa"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioBalancedatum lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla tranzakció összeg lekérdezés tranzakciótípusra, dátumra, vagyon2 típusra szűrve
    public ArrayList<Tranzakcio> getTranzakcioBalancedatum2(int tr, int v, LocalDate d) {
        String sql = "select sum(Tranzakcio.OSSZEG*Penznem.HUFRATIO) Summa from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where (Tranzakcio.Tranzakciotipus=" + tr + "and Tranzakcio.Datum<'" + d + "' " + " and Tranzakcio.VAGYON2=" + v + ")";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("Summa"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioBalancedatum2 lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla tranzakció összeg lekérdezés tranzakciótípusra, dátumra, vagyon1 típusra szűrve
    public ArrayList<Tranzakcio> getTranzakcioBalance(int tr, int m, int y, int v) {
        String sql = "select sum(Tranzakcio.OSSZEG*Penznem.HUFRATIO) Summa from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where (Tranzakcio.Tranzakciotipus=" + tr + " and month(Tranzakcio.DATUM)<=" + m + " and year(Tranzakcio.DATUM)<=" + y + " and Tranzakcio.VAGYON1=" + v + ")";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("Summa"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioBalance lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla tranzakció összeg lekérdezés tranzakciótípusra, dátumra, vagyon2 típusra szűrve
    public ArrayList<Tranzakcio> getTranzakcioBalanceAtvez(int tr, int m, int y, int v) {
        String sql = "select sum(Tranzakcio.OSSZEG*Penznem.HUFRATIO) Summa from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where (Tranzakcio.Tranzakciotipus=" + tr + " and month(Tranzakcio.DATUM)<=" + m + " and year(Tranzakcio.DATUM)<=" + y + " and Tranzakcio.VAGYON2=" + v + ")";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getInt("Summa"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioBalanceAtvez lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla  összeg lekérdezés tranzakciótípusra, dátumra, vagyon1, vagyon2 típusra szűrve
    public ArrayList<Tranzakcio> getTranzakcioFutureValue(int tr, int m, int y, int v) {
        String sql = "select Datum, OSSZEG, Tranzakciorendszeresseg, Penznem.HUFRATIO Ratio from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where\n"
                + "  (Tranzakcio.Tranzakciotipus=" + tr + " and year(Tranzakcio.DATUM)<" + y + " and Tranzakcio.VAGYON2=" + v + ") or \n"
                + " (Tranzakcio.Tranzakciotipus=" + tr + " and month(Tranzakcio.DATUM)<=" + m + " and year(Tranzakcio.DATUM)=" + y + "  and Tranzakcio.VAGYON2=" + v + ")";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(LocalDate.parse(rs.getObject("Datum").toString()), rs.getInt("Osszeg"), rs.getInt("TranzakcioRendszeresseg"), rs.getInt("Ratio"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioFutureValue lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla  összeg lekérdezés tranzakciótípusra, dátumra, vagyon1 típusra szűrve
    public ArrayList<Tranzakcio> getTranzakcioFutureValue2(int tr, int v) {
        String sql = "select Datum, OSSZEG, Tranzakciorendszeresseg, Penznem.HUFRATIO Ratio from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where\n"
                + "  (Tranzakcio.Tranzakciotipus=" + tr + " and Tranzakcio.VAGYON1=" + v + ")";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(LocalDate.parse(rs.getObject("Datum").toString()), rs.getInt("Osszeg"), rs.getInt("TranzakcioRendszeresseg"), rs.getInt("Ratio"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioFutureValue2 lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla  összeg lekérdezés tranzakciótípusra, vagyon2 típusra szűrve
    public ArrayList<Tranzakcio> getTranzakcioFutureValue3(int tr, int v) {
        String sql = "select Datum, OSSZEG, Tranzakciorendszeresseg, Penznem.HUFRATIO Ratio from Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join Penznem on Penznem.PID=Tranzakcio.PENZNEM where\n"
                + "  (Tranzakcio.Tranzakciotipus=" + tr + " and Tranzakcio.VAGYON2=" + v + ")";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(LocalDate.parse(rs.getObject("Datum").toString()), rs.getInt("Osszeg"), rs.getInt("TranzakcioRendszeresseg"), rs.getInt("Ratio"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioFutureValue3 lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla  összeg lekérdezés tranzakciótípusra, személyre, eszközre, dátumra, vagyon1 értékre szűrve
    public ArrayList<Tranzakcio> getTranzakcioAktEszk(String Eszkoz, LocalDate FromDate, LocalDate ToDate, int TranzakcioTipus, String Szemely) {
        String sql = "SELECT Tranzakcio.NEV, Tranzakcio.OSSZEG, Penznem.NEV PenzTipus, Tranzakcio.DATUM, Szemely.nev SzemelyNev1, vagyon.NEV ||' ' || tipus.NEV VagyonNev1 FROM Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join vagyon on Tranzakcio.VAGYON1 = Vagyon.VID inner join tipus on vagyon.TIPUS=tipus.TID inner join Szemely on Vagyon.SZEMELY=Szemely.SZID inner join Penznem on Tranzakcio.PENZNEM=Penznem.PID where vagyon.NEV ||' ' || tipus.NEV like '" + Eszkoz + "' and Szemely.Nev like '" + Szemely + "' and Tranzakcio.DATUM >= '" + FromDate + "' " + "and Tranzakcio.DATUM <= '" + ToDate + "'" + " and Tranzakcio.TRANZAKCIOTIPUS=" + TranzakcioTipus + "";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getString("Nev"), rs.getInt("Osszeg"), rs.getString("PenzTipus"), LocalDate.parse(rs.getObject("Datum").toString()), rs.getString("SzemelyNev1"), rs.getString("VagyonNev1"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioAktEszk lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla  összeg lekérdezés tranzakciótípusra, személyre, eszközre, dátumra, vagyon2 értékre szűrve
    public ArrayList<Tranzakcio> getTranzakcioAktEszk2(String Eszkoz, LocalDate FromDate, LocalDate ToDate, int TranzakcioTipus, String Szemely) {
        String sql = "SELECT Tranzakcio.NEV, Tranzakcio.OSSZEG, Penznem.NEV PenzTipus, Tranzakcio.DATUM, Szemely.nev SzemelyNev1, vagyon.NEV ||' ' || tipus.NEV VagyonNev1 FROM Tranzakcio inner join AktFelhasznalo AK on Tranzakcio.AKTFEL=AK.AKTFELID inner join vagyon on Tranzakcio.VAGYON2 = Vagyon.VID inner join tipus on vagyon.TIPUS=tipus.TID inner join Szemely on Vagyon.SZEMELY=Szemely.SZID inner join Penznem on Tranzakcio.PENZNEM=Penznem.PID where vagyon.NEV ||' ' || tipus.NEV like '" + Eszkoz + "' and Szemely.Nev like '" + Szemely + "' and Tranzakcio.DATUM >= '" + FromDate + "' " + "and Tranzakcio.DATUM <= '" + ToDate + "'" + " and Tranzakcio.TRANZAKCIOTIPUS=" + TranzakcioTipus + "";
        ArrayList<Tranzakcio> TranzakcioTipusok = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);

            TranzakcioTipusok = new ArrayList<>();
            while (rs.next()) {

                Tranzakcio aktTranzakcio = new Tranzakcio(rs.getString("Nev"), rs.getInt("Osszeg"), rs.getString("PenzTipus"), LocalDate.parse(rs.getObject("Datum").toString()), rs.getString("SzemelyNev1"), rs.getString("VagyonNev1"));
                TranzakcioTipusok.add(aktTranzakcio);

            }
        } catch (SQLException ex) {
            System.out.println("Hiba az összes getTranzakcioAktEszk2 lekérdezésésél" + ex);
        }
        return TranzakcioTipusok;
    }

    //Tranzakcio tábla példány felvitele
    public void addTranzakcio(Tranzakcio TranzakcioPeldany) {

        try {
            String sql = "insert into Tranzakcio(Nev, Kategoria, Datum, Osszeg, Penznem, Vagyon1, Vagyon2, TranzakcioTipus, TranzakcioRendszeresseg, AktFel) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, TranzakcioPeldany.getNev());
            preparedStatement.setInt(2, TranzakcioPeldany.getKategoria());
            preparedStatement.setObject(3, java.sql.Date.valueOf(TranzakcioPeldany.getDatum()));
            preparedStatement.setInt(4, TranzakcioPeldany.getOsszeg());
            preparedStatement.setInt(5, TranzakcioPeldany.getPenznem());
            preparedStatement.setInt(6, TranzakcioPeldany.getVagyonID1());
            preparedStatement.setInt(7, TranzakcioPeldany.getVagyonID2());
            preparedStatement.setInt(8, TranzakcioPeldany.getTranzakcioTipus());
            preparedStatement.setInt(9, TranzakcioPeldany.getTranzakcioRendszeresseg());
            preparedStatement.setInt(10, TranzakcioPeldany.getAktFel());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba az új Tranzakció példány felvételekor: " + ex);
        }

    }

    //Tranzakcio tábla példány módosítása
    public void updateTranzakcio(Tranzakcio TranzakcioPeldany) {
        try {
            String sql = "update Tranzakcio set Nev = ?, Kategoria = ?, Datum = ?, Osszeg = ?, Penznem = ?, Vagyon1 = ?, Vagyon2=?, TranzakcioTipus=?, TranzakcioRendszeresseg=? where TRID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, TranzakcioPeldany.getNev());
            preparedStatement.setInt(2, TranzakcioPeldany.getKategoria());
            preparedStatement.setObject(3, java.sql.Date.valueOf(TranzakcioPeldany.getDatum()));
            preparedStatement.setInt(4, TranzakcioPeldany.getOsszeg());
            preparedStatement.setInt(5, TranzakcioPeldany.getPenznem());
            preparedStatement.setInt(6, TranzakcioPeldany.getVagyonID1());
            preparedStatement.setInt(7, TranzakcioPeldany.getVagyonID2());
            preparedStatement.setInt(8, TranzakcioPeldany.getTranzakcioTipus());
            preparedStatement.setInt(9, TranzakcioPeldany.getTranzakcioRendszeresseg());
            preparedStatement.setInt(10, TranzakcioPeldany.getTRID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Tranzakció módosításánál: " + ex);
        }
    }

    //Tranzakcio tábla példány törlése
    public void deleteTranzakcio(Tranzakcio TranzakcioPeldany) {
        try {
            String sql = "delete from Tranzakcio where TRID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, TranzakcioPeldany.getTRID());
            preparedStatement.execute();
        } catch (Exception ex) {
            System.out.println("Hiba a Tranzakció módosításánál: " + ex);
        }
    }
    //</editor-fold>

}
