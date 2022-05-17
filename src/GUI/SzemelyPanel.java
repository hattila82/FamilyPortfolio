/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.AktFelhasznalo;
import Controls.Szemely;
import Controls.Vagyon;
import Model.DB;
import familyportfolio.FamilyPortfolio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author HorvathAttila SzemelyPanel osztály létrehozása, panel felépítése,
 * eseménykezelés
 */
public class SzemelyPanel extends JPanel implements ActionListener {

    JButton Add = new JButton();
    JButton Mod = new JButton();
    JButton Del = new JButton();
    ImageIcon addIcon = new ImageIcon(new ImageIcon("src/Icons/insert.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon modifyIcon = new ImageIcon(new ImageIcon("src/Icons/edit.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon deleteIcon = new ImageIcon(new ImageIcon("src/Icons/delete.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    JTable UserTable = new JTable(0, 3);
    DefaultTableModel ModelTable = (DefaultTableModel) UserTable.getModel();
    DB UserDB = new DB();
    JPanel UserPanel = new JPanel();
    SzemelyMuvelet UserAction = new SzemelyMuvelet();
    Szemely UserInstance = new Szemely();
    ArrayList<Szemely> Szemelyek = null;
    JTableHeader tableHeader = new JTableHeader();
    JPanel northPanel = new JPanel();
    int felh;
    ArrayList<Vagyon> VagyonDb = null;

    //<editor-fold defaultstate="collapsed" desc="Táblázat készítés metódus">
    public void refreshTable() {
        Szemelyek = UserDB.getSzemely();
        Object[] row = new Object[3];
        ModelTable.setRowCount(0);
        for (int i = 0; i < Szemelyek.size(); i++) {
            row[0] = Szemelyek.get(i).getId();
            row[1] = Szemelyek.get(i).getName();
            row[2] = Szemelyek.get(i).getMegjegyzes();
            ModelTable.addRow(row);
        }
        UserTable.getColumnModel().getColumn(0).setMinWidth(0);
        UserTable.getColumnModel().getColumn(0).setMaxWidth(0);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        UserTable.setFocusable(false);
        UserTable.setSelectionBackground(Color.lightGray);
        UserTable.setFont(new Font("Arial", Font.BOLD, 13));
        UserTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        UserTable.getColumnModel().getColumn(1).setHeaderValue("Személy");
        UserTable.getColumnModel().getColumn(1).setMinWidth(5);
        UserTable.getColumnModel().getColumn(1).setPreferredWidth(UserTable.getColumnModel().getColumn(1).getWidth() + 1);
        UserTable.getColumnModel().getColumn(1).sizeWidthToFit();
        UserTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        UserTable.getColumnModel().getColumn(2).setHeaderValue("Megjegyzés");
        UserTable.getColumnModel().getColumn(2).setMinWidth(5);
        UserTable.getColumnModel().getColumn(2).setPreferredWidth(UserTable.getColumnModel().getColumn(2).getWidth() + 1);
        UserTable.getColumnModel().getColumn(2).sizeWidthToFit();
        UserTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        UserTable.getTableHeader().resizeAndRepaint();
        UserTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        UserTable.setShowVerticalLines(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">  
    public SzemelyPanel() {
        this.setLayout((new BorderLayout()));
        Add.setIcon(addIcon);
        Mod.setIcon(modifyIcon);
        Del.setIcon(deleteIcon);
        Add.addActionListener(this);
        Mod.addActionListener(this);
        Del.addActionListener(this);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(UserTable);
        northPanel.add(Add);
        northPanel.add(Mod);
        northPanel.add(Del);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Eseménykezelés"> 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Add) {
            UserAction.setupAdd();
            UserAction.mainFrameAdd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                }
            });
        }
        if (e.getSource() == Mod) {

            if (UserTable.getSelectedRow() >= 0) {
                UserInstance.setId((int) UserTable.getModel().getValueAt(UserTable.getSelectedRow(), 0));
                UserInstance.setMegjegyzes(UserTable.getModel().getValueAt(UserTable.getSelectedRow(), 2).toString());
                UserInstance.setName(UserTable.getModel().getValueAt(UserTable.getSelectedRow(), 1).toString());
                UserAction.setupMod(UserInstance);
                UserAction.mainFrameMod.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTable();
                    }
                });
            }
        }
        if (e.getSource() == Del) {

            if (UserTable.getSelectedRow() >= 0) {
                int szamlalo = -1;
                VagyonDb = UserDB.getVagyonSzemelyekKapcsolat((int) UserTable.getModel().getValueAt(UserTable.getSelectedRow(), 0));
                for (int k = 0; k < VagyonDb.size(); k++) {
                    szamlalo = VagyonDb.get(k).getVID();
                }
                if (szamlalo > 0) {
                    JOptionPane.showMessageDialog(this, "Az adott személyhez vagyontárgy tartozik, így nem törölhető!!");
                } else {
                    UserInstance.setId((int) UserTable.getModel().getValueAt(UserTable.getSelectedRow(), 0));
                    UserInstance.setMegjegyzes(UserTable.getModel().getValueAt(UserTable.getSelectedRow(), 2).toString());
                    UserInstance.setName(UserTable.getModel().getValueAt(UserTable.getSelectedRow(), 1).toString());
                    UserAction.setupDel(UserInstance);
                    if (UserAction.deleteEvent == 1) {
                        refreshTable();
                    }
                }
            }
        }
    }
    //</editor-fold>
}
