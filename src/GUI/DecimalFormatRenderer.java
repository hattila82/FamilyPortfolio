/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author HorvathAttila
 */
public class DecimalFormatRenderer extends DefaultTableCellRenderer {

    private final DecimalFormat formatter = new DecimalFormat("###,###");

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        cell.setText(formatter.format(value));

        return cell;
    }

    @Override
    public void setValue(Object value) {
        setText(value instanceof Double ? formatter.format(value) : "");
    }

}
