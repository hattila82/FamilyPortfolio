/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controls.Szemely;
import Controls.Vagyon;
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
import java.time.LocalDate;
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
 * @author HorvathAttila VagyonPanel osztály létrehozása, panel felépítése,
 * eseménykezelés
 */
public class VagyonPanel extends JPanel implements ActionListener {

    JButton Add = new JButton();
    JButton Mod = new JButton();
    JButton Del = new JButton();
    ImageIcon addIcon = new ImageIcon(new ImageIcon("src/Icons/insert.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon modifyIcon = new ImageIcon(new ImageIcon("src/Icons/edit.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon deleteIcon = new ImageIcon(new ImageIcon("src/Icons/delete.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    JTable GoodsTable = new JTable(0, 8);
    DefaultTableModel ModelTable = (DefaultTableModel) GoodsTable.getModel();
    DB VagyonDB = new DB();
    Vagyon VagyonInstance = new Vagyon();
    ArrayList<Vagyon> AktivEszkozok = null;
    String VagyonParam;
    JPanel northPanel = new JPanel();

    //<editor-fold defaultstate="collapsed" desc="Táblázat készítés metódus">
    public void refreshTable() {
        AktivEszkozok = VagyonDB.getVagyonParam(VagyonParam);
        Object[] row = new Object[8];
        ModelTable.setRowCount(0);
        for (int i = 0; i < AktivEszkozok.size(); i++) {
            row[0] = AktivEszkozok.get(i).getVID();
            row[1] = AktivEszkozok.get(i).getNev();
            row[2] = AktivEszkozok.get(i).getTipusNev();
            row[3] = AktivEszkozok.get(i).getNyitoEgyenleg();
            row[4] = AktivEszkozok.get(i).getPenznemNev();
            row[5] = AktivEszkozok.get(i).getNyitoDatum();
            row[6] = AktivEszkozok.get(i).getSzemelyNev();
            row[7] = AktivEszkozok.get(i).getMegj();
            ModelTable.addRow(row);
        }
        GoodsTable.getColumnModel().getColumn(0).setMinWidth(0);
        GoodsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        GoodsTable.setFocusable(false);
        GoodsTable.setSelectionBackground(Color.lightGray);
        GoodsTable.setFont(new Font("Arial", Font.BOLD, 12));
        GoodsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        GoodsTable.getColumnModel().getColumn(1).setHeaderValue("Vagyoni eszköz");
        GoodsTable.getColumnModel().getColumn(1).setMinWidth(5);
        GoodsTable.getColumnModel().getColumn(1).setPreferredWidth(GoodsTable.getColumnModel().getColumn(1).getWidth() + 1);
        GoodsTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(1).sizeWidthToFit();
        GoodsTable.getColumnModel().getColumn(2).setHeaderValue("Típus");
        GoodsTable.getColumnModel().getColumn(2).setMinWidth(5);
        GoodsTable.getColumnModel().getColumn(2).setPreferredWidth(GoodsTable.getColumnModel().getColumn(2).getWidth() + 1);
        GoodsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(2).sizeWidthToFit();
        GoodsTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
        GoodsTable.getColumnModel().getColumn(3).setHeaderValue("Nyitó Egyenleg");
        GoodsTable.getColumnModel().getColumn(3).setMinWidth(5);
        GoodsTable.getColumnModel().getColumn(3).setPreferredWidth(GoodsTable.getColumnModel().getColumn(3).getWidth() + 1);
        GoodsTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(3).sizeWidthToFit();
        GoodsTable.getColumnModel().getColumn(4).setHeaderValue("Pénznem");
        GoodsTable.getColumnModel().getColumn(4).setMinWidth(5);
        GoodsTable.getColumnModel().getColumn(4).setPreferredWidth(GoodsTable.getColumnModel().getColumn(3).getWidth() + 1);
        GoodsTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(4).sizeWidthToFit();
        GoodsTable.getColumnModel().getColumn(5).setHeaderValue("Nyitó Dátum");
        GoodsTable.getColumnModel().getColumn(5).setMinWidth(5);
        GoodsTable.getColumnModel().getColumn(5).setPreferredWidth(GoodsTable.getColumnModel().getColumn(3).getWidth() + 1);
        GoodsTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(5).sizeWidthToFit();
        GoodsTable.getColumnModel().getColumn(6).setHeaderValue("Személy");
        GoodsTable.getColumnModel().getColumn(6).setMinWidth(5);
        GoodsTable.getColumnModel().getColumn(6).setPreferredWidth(GoodsTable.getColumnModel().getColumn(3).getWidth() + 1);
        GoodsTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(6).sizeWidthToFit();
        GoodsTable.getColumnModel().getColumn(7).setHeaderValue("Megjegyzés");
        GoodsTable.getColumnModel().getColumn(7).setMinWidth(5);
        GoodsTable.getColumnModel().getColumn(7).setPreferredWidth(GoodsTable.getColumnModel().getColumn(3).getWidth() + 1);
        GoodsTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        GoodsTable.getColumnModel().getColumn(7).sizeWidthToFit();
        GoodsTable.getTableHeader().resizeAndRepaint();
        GoodsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        GoodsTable.setShowVerticalLines(false);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktor metódus">  
    public VagyonPanel(String VagyonParam) {
        this.VagyonParam = VagyonParam;
        this.setLayout((new BorderLayout()));
        Add.setIcon(addIcon);
        Mod.setIcon(modifyIcon);
        Del.setIcon(deleteIcon);
        Add.addActionListener(this);
        Mod.addActionListener(this);
        Del.addActionListener(this);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(GoodsTable);
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
            VagyonMuvelet GoodsAction = new VagyonMuvelet(parseInt(VagyonParam));
            GoodsAction.setupAdd();
            GoodsAction.mainFrameAdd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                }
            });
        }

        if (e.getSource() == Mod) {

            if (GoodsTable.getSelectedRow() >= 0) {
                VagyonMuvelet GoodsAction = new VagyonMuvelet(parseInt(VagyonParam));
                VagyonInstance.setVID((int) GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 0));
                VagyonInstance.setNev(GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 1).toString());
                VagyonInstance.setTipusNev(GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 2).toString());
                VagyonInstance.setNyitoEgyenleg((int) GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 3));
                VagyonInstance.setPenznemNev(GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 4).toString());
                VagyonInstance.setNyitoDatum((LocalDate) GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 5));
                VagyonInstance.setSzemelyNev(GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 6).toString());
                VagyonInstance.setMegj(GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 7).toString());
                GoodsAction.setupMod(VagyonInstance);
                GoodsAction.mainFrameMod.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTable();
                    }
                });
            }
        }
        if (e.getSource() == Del) {
            VagyonMuvelet GoodsAction = new VagyonMuvelet();
            if (GoodsTable.getSelectedRow() >= 0) {
                VagyonInstance.setVID((int) GoodsTable.getModel().getValueAt(GoodsTable.getSelectedRow(), 0));
                GoodsAction.setupDel(VagyonInstance);
                if (GoodsAction.deleteEvent == 1) {
                    refreshTable();
                }
            }
        }
    }
//</editor-fold>
}
