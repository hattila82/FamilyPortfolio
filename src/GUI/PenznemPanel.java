/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Penznem;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HorvathAttila PenznemPanel osztály létrehozása, panel felépítése,
 * eseménykezelés
 */
public class PenznemPanel extends JPanel implements ActionListener {

    JButton Add = new JButton();
    JButton Mod = new JButton();
    JButton Del = new JButton();
    ImageIcon addIcon = new ImageIcon(new ImageIcon("src/Icons/insert.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon modifyIcon = new ImageIcon(new ImageIcon("src/Icons/edit.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon deleteIcon = new ImageIcon(new ImageIcon("src/Icons/delete.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    JTable CurrencyTable = new JTable(0, 3);
    DefaultTableModel ModelTable = (DefaultTableModel) CurrencyTable.getModel();
    DB PenznemDB = new DB();
    JPanel northPanel = new JPanel();
    PenznemMuvelet CurrencyAction = new PenznemMuvelet();
    Penznem CurrencyInstance = new Penznem();
    ArrayList<Penznem> Penznemek = null;

    //<editor-fold defaultstate="collapsed" desc="Táblázat készítés metódus">
    public void refreshTable() {
        Penznemek = PenznemDB.getPenznem();
        Object[] row = new Object[3];
        ModelTable.setRowCount(0);
        for (int i = 0; i < Penznemek.size(); i++) {
            row[0] = Penznemek.get(i).getId();
            row[1] = Penznemek.get(i).getName();
            row[2] = Penznemek.get(i).getHUFRatio();
            ModelTable.addRow(row);
        }
        CurrencyTable.getColumnModel().getColumn(0).setMinWidth(0);
        CurrencyTable.getColumnModel().getColumn(0).setMaxWidth(0);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        CurrencyTable.setFocusable(false);
        CurrencyTable.setSelectionBackground(Color.lightGray);
        CurrencyTable.setFont(new Font("Arial", Font.BOLD, 13));
        CurrencyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        CurrencyTable.getColumnModel().getColumn(1).setHeaderValue("Pénznem");
        CurrencyTable.getColumnModel().getColumn(1).setMinWidth(5);
        CurrencyTable.getColumnModel().getColumn(1).setPreferredWidth(CurrencyTable.getColumnModel().getColumn(1).getWidth() + 1);
        CurrencyTable.getColumnModel().getColumn(1).sizeWidthToFit();
        CurrencyTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        CurrencyTable.getColumnModel().getColumn(2).setHeaderValue("HUF Ratio");
        CurrencyTable.getColumnModel().getColumn(2).setMinWidth(5);
        CurrencyTable.getColumnModel().getColumn(2).setPreferredWidth(CurrencyTable.getColumnModel().getColumn(2).getWidth() + 1);
        CurrencyTable.getColumnModel().getColumn(2).sizeWidthToFit();
        CurrencyTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        CurrencyTable.getTableHeader().resizeAndRepaint();
        CurrencyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        CurrencyTable.setShowVerticalLines(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">   
    public PenznemPanel() {
        this.setLayout((new BorderLayout()));
        Add.setIcon(addIcon);
        Mod.setIcon(modifyIcon);
        Del.setIcon(deleteIcon);
        Add.addActionListener(this);
        Mod.addActionListener(this);
        Del.addActionListener(this);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(CurrencyTable);
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
            try {
                CurrencyAction.setupAdd();
            } catch (ParseException ex) {
                Logger.getLogger(PenznemPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            CurrencyAction.mainFrameAdd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                }
            });
        }

        if (e.getSource() == Mod) {

            if (CurrencyTable.getSelectedRow() >= 0) {
                CurrencyInstance.setId((int) CurrencyTable.getModel().getValueAt(CurrencyTable.getSelectedRow(), 0));
                CurrencyInstance.setName(CurrencyTable.getModel().getValueAt(CurrencyTable.getSelectedRow(), 1).toString());
                CurrencyInstance.setHUFRatio((float) CurrencyTable.getModel().getValueAt(CurrencyTable.getSelectedRow(), 2));

                try {
                    CurrencyAction.setupMod(CurrencyInstance);
                } catch (ParseException ex) {
                    Logger.getLogger(PenznemPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                CurrencyAction.mainFrameMod.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTable();
                    }
                });
            }
        }
        if (e.getSource() == Del) {

            if (CurrencyTable.getSelectedRow() >= 0) {
                CurrencyInstance.setId((int) CurrencyTable.getModel().getValueAt(CurrencyTable.getSelectedRow(), 0));
                CurrencyAction.setupDel(CurrencyInstance);
                if (CurrencyAction.deleteEvent == 1) {
                    refreshTable();
                }
            }
        }
    }
    //</editor-fold>
}
