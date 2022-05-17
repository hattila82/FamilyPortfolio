/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Szemely;
import Controls.Tipus;
import Model.DB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HorvathAttila TipusPanel osztály létrehozása, panel felépítése,
 * eseménykezelés
 */
public class TipusPanel extends JPanel implements ActionListener {

    JButton Add = new JButton();
    JButton Mod = new JButton();
    JButton Del = new JButton();
    ImageIcon addIcon = new ImageIcon(new ImageIcon("src/Icons/insert.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon modifyIcon = new ImageIcon(new ImageIcon("src/Icons/edit.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon deleteIcon = new ImageIcon(new ImageIcon("src/Icons/delete.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    JTable TypeTable = new JTable(0, 4);
    DefaultTableModel ModelTable = (DefaultTableModel) TypeTable.getModel();
    DB TypeDB = new DB();
    JPanel northPanel = new JPanel();
    SzemelyMuvelet UserAction = new SzemelyMuvelet();
    Szemely UserInstance = new Szemely();
    Tipus TipusInstance = new Tipus();
    ArrayList<Szemely> Szemelyek = null;
    ArrayList<Tipus> Tipusok = null;

    //<editor-fold defaultstate="collapsed" desc="Táblázat készítés metódus">
    public void refreshTable() {
        Tipusok = TypeDB.getTipusForma();
        Object[] row = new Object[4];
        ModelTable.setRowCount(0);
        for (int i = 0; i < Tipusok.size(); i++) {
            row[0] = Tipusok.get(i).getTID();
            row[1] = Tipusok.get(i).getNev();
            row[2] = Tipusok.get(i).getForma();
            row[3] = Tipusok.get(i).getFormaNev();
            ModelTable.addRow(row);
        }
        TypeTable.getColumnModel().getColumn(0).setMinWidth(0);
        TypeTable.getColumnModel().getColumn(0).setMaxWidth(0);
        TypeTable.getColumnModel().getColumn(2).setMinWidth(0);
        TypeTable.getColumnModel().getColumn(2).setMaxWidth(0);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TypeTable.setFocusable(false);
        TypeTable.setSelectionBackground(Color.lightGray);
        TypeTable.setFont(new Font("Arial", Font.BOLD, 13));
        TypeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        TypeTable.getColumnModel().getColumn(1).setHeaderValue("Típus");
        TypeTable.getColumnModel().getColumn(1).setMinWidth(5);
        TypeTable.getColumnModel().getColumn(1).setPreferredWidth(TypeTable.getColumnModel().getColumn(1).getWidth() + 1);
        TypeTable.getColumnModel().getColumn(1).sizeWidthToFit();
        TypeTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        TypeTable.getColumnModel().getColumn(3).setHeaderValue("Megjegyzés");
        TypeTable.getColumnModel().getColumn(3).setMinWidth(5);
        TypeTable.getColumnModel().getColumn(3).setPreferredWidth(TypeTable.getColumnModel().getColumn(2).getWidth() + 1);
        TypeTable.getColumnModel().getColumn(3).sizeWidthToFit();
        TypeTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        TypeTable.getTableHeader().resizeAndRepaint();
        TypeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TypeTable.setShowVerticalLines(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">  
    public TipusPanel() {
        this.setLayout((new BorderLayout()));
        Add.setIcon(addIcon);
        Mod.setIcon(modifyIcon);
        Del.setIcon(deleteIcon);
        Add.addActionListener(this);
        Mod.addActionListener(this);
        Del.addActionListener(this);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(TypeTable);
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
            TipusMuvelet TypeAction = new TipusMuvelet();
            TypeAction.setupAdd();
            TypeAction.mainFrameAdd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                }
            });
        }
        if (e.getSource() == Mod) {

            if (TypeTable.getSelectedRow() >= 0) {
                TipusMuvelet TypeAction = new TipusMuvelet();

                TipusInstance.setTID((int) TypeTable.getModel().getValueAt(TypeTable.getSelectedRow(), 0));
                TipusInstance.setNev(TypeTable.getModel().getValueAt(TypeTable.getSelectedRow(), 1).toString());
                TipusInstance.setForma((int) TypeTable.getModel().getValueAt(TypeTable.getSelectedRow(), 2));
                TipusInstance.setFormaNev(TypeTable.getModel().getValueAt(TypeTable.getSelectedRow(), 3).toString());
                TypeAction.setupMod(TipusInstance);
                TypeAction.mainFrameMod.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTable();
                    }
                });
            }
        }
        if (e.getSource() == Del) {
            if (TypeTable.getSelectedRow() >= 0) {
                TipusMuvelet TypeAction = new TipusMuvelet();
                TipusInstance.setTID((int) TypeTable.getModel().getValueAt(TypeTable.getSelectedRow(), 0));
                TypeAction.setupDel(TipusInstance);
                if (TypeAction.deleteEvent == 1) {
                    refreshTable();
                }
            }
        }
    }
    //</editor-fold>
}
