/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Kategoria;
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
import static java.lang.Integer.parseInt;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author HorvathAttila KategoriaPanel osztály létrehozása, panel felépítése,
 * eseménykezelés
 */
public class KategoriaPanel extends JPanel implements ActionListener {

    JButton Add = new JButton();
    JButton Mod = new JButton();
    JButton Del = new JButton();
    ImageIcon addIcon = new ImageIcon(new ImageIcon("src/Icons/insert.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon modifyIcon = new ImageIcon(new ImageIcon("src/Icons/edit.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon deleteIcon = new ImageIcon(new ImageIcon("src/Icons/delete.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    JTable CategoryTable = new JTable(0, 3);
    DefaultTableModel ModelTable = (DefaultTableModel) CategoryTable.getModel();
    DB KategoriaDB = new DB();
    Kategoria CategoryInstance = new Kategoria();
    ArrayList<Kategoria> Kategoriak = null;
    String KategoriaParam;
    JPanel northPanel = new JPanel();

    //<editor-fold defaultstate="collapsed" desc="Táblázat készítés metódus"> 
    public void refreshTable() {
        Kategoriak = KategoriaDB.getKategoriaParam(KategoriaParam);
        Object[] row = new Object[3];
        ModelTable.setRowCount(0);
        for (int i = 0; i < Kategoriak.size(); i++) {
            row[0] = Kategoriak.get(i).getKID();
            row[1] = Kategoriak.get(i).getNev();
            row[2] = Kategoriak.get(i).getMegj();
            ModelTable.addRow(row);
        }
        CategoryTable.getColumnModel().getColumn(0).setMinWidth(0);
        CategoryTable.getColumnModel().getColumn(0).setMaxWidth(0);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        CategoryTable.setFocusable(false);
        CategoryTable.setSelectionBackground(Color.lightGray);
        CategoryTable.setFont(new Font("Arial", Font.BOLD, 13));
        CategoryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        CategoryTable.getColumnModel().getColumn(1).setHeaderValue("Kategória");
        CategoryTable.getColumnModel().getColumn(1).setMinWidth(5);
        CategoryTable.getColumnModel().getColumn(1).setPreferredWidth(CategoryTable.getColumnModel().getColumn(1).getWidth() + 1);
        CategoryTable.getColumnModel().getColumn(1).sizeWidthToFit();
        CategoryTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        CategoryTable.getColumnModel().getColumn(2).setHeaderValue("Megjegyzés");
        CategoryTable.getColumnModel().getColumn(2).setMinWidth(5);
        CategoryTable.getColumnModel().getColumn(2).setPreferredWidth(CategoryTable.getColumnModel().getColumn(2).getWidth() + 1);
        CategoryTable.getColumnModel().getColumn(2).sizeWidthToFit();
        CategoryTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        CategoryTable.getTableHeader().resizeAndRepaint();
        CategoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        CategoryTable.setShowVerticalLines(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">   
    public KategoriaPanel(String KategoriaParam) {
        this.KategoriaParam = KategoriaParam;
        this.setLayout((new BorderLayout()));
        Add.setIcon(addIcon);
        Mod.setIcon(modifyIcon);
        Del.setIcon(deleteIcon);
        Add.addActionListener(this);
        Mod.addActionListener(this);
        Del.addActionListener(this);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(CategoryTable);
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
            KategoriaMuvelet CategoryAction = new KategoriaMuvelet(parseInt(KategoriaParam));
            CategoryAction.setupAdd();
            CategoryAction.mainFrameAdd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                }
            });
        }

        if (e.getSource() == Mod) {
            if (CategoryTable.getSelectedRow() >= 0) {
                KategoriaMuvelet CategoryAction = new KategoriaMuvelet(parseInt(KategoriaParam));
                CategoryInstance.setKID((int) CategoryTable.getModel().getValueAt(CategoryTable.getSelectedRow(), 0));
                CategoryInstance.setNev(CategoryTable.getModel().getValueAt(CategoryTable.getSelectedRow(), 1).toString());
                CategoryInstance.setMegj(CategoryTable.getModel().getValueAt(CategoryTable.getSelectedRow(), 2).toString());
                CategoryInstance.setBevKid(parseInt(KategoriaParam));
                CategoryAction.setupMod(CategoryInstance);
                CategoryAction.mainFrameMod.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTable();
                    }
                });
            }
        }
        if (e.getSource() == Del) {
            if (CategoryTable.getSelectedRow() >= 0) {
                KategoriaMuvelet CategoryAction = new KategoriaMuvelet();
                CategoryInstance.setKID((int) CategoryTable.getModel().getValueAt(CategoryTable.getSelectedRow(), 0));
                CategoryAction.setupDel(CategoryInstance);
                if (CategoryAction.deleteEvent == 1) {
                    refreshTable();
                }
            }
        }
    }
    //</editor-fold>
}
