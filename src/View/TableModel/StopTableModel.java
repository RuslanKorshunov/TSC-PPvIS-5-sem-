package View.TableModel;

import Model.Stop;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

public class StopTableModel extends AbstractTableModel
{
    private int rowCount;
    private final int columnCount=1;
    private List<Stop> listStops;

    public StopTableModel(int rowCount)
    {
        this.rowCount=rowCount;
        listStops=new LinkedList<>();
    }

    @Override
    public int getRowCount()
    {
        return rowCount;
    }

    @Override
    public int getColumnCount()
    {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if(listStops.size()==0 || rowIndex>listStops.size()-1)
            return " ";
        else
            return listStops.get(rowIndex).getName();
    }

    public void clearListStops()
    {
        listStops=new LinkedList<>();
        rowCount=10;
    }

    public void setListStops(List<Stop> listStops)
    {
        this.listStops = listStops;
        rowCount=listStops.size();
    }
}