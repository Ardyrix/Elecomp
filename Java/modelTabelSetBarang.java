/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.util.ArrayList;
//import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author tam
 */
public class modelTabelSetBarang extends AbstractTableModel {

    private ArrayList<ListSetBarang> list;

    public modelTabelSetBarang(ArrayList<ListSetBarang> list) {
        this.list = list;
    }

//    public modelTabelSetBarang(ArrayList<ListBarang> list) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getSelect();
            case 1:
                return list.get(rowIndex).getNomor();
            case 2:
                return list.get(rowIndex).getKode_barang();
            case 3:
                return list.get(rowIndex).getNama_barang();
            case 4:
                return list.get(rowIndex).getHarga_jual_1_barang();
            case 5:
                return list.get(rowIndex).getHarga_jual_2_barang();
            case 6:
                return list.get(rowIndex).getHarga_jual_3_barang();

            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Select";
            case 1:
                return "No";
            case 2:
                return "Kode Barang ";
            case 3:
                return "Nama Barang";
            case 4:
                return "harga_jual_1_barang";
            case 5:
                return "harga_jual_2_barang";
            case 6:
                return "harga_jual_3_barang";
            default:
                return null;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Class getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Boolean.class;
        } else {
            return super.getColumnClass(columnIndex);
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        if (aValue != null && aValue instanceof Boolean && columnIndex == 0) {
            boolean select = (Boolean) aValue;
            list.get(rowIndex).setSelect(select);
        }
    }

    public void add(ListSetBarang ListSetBarang) {
        list.add(ListSetBarang);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void edit() {
        ArrayList<ListSetBarang> newSelect = new ArrayList<ListSetBarang>();
        for (ListSetBarang ListSetBarang : list) {
            if (!ListSetBarang.getSelect()) {
                newSelect.add(ListSetBarang);
            }
        }
        list = newSelect;
        fireTableDataChanged();
    }
}


