package Model;

import javax.swing.table.AbstractTableModel;

public class DateTableModel extends AbstractTableModel
{
    private int rowCount;
    private final int columnCount=1;
    private Timetable timetable;

    public DateTableModel(int rowCount)
    {
        this.rowCount=rowCount;
    }

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
        if(timetable==null)
            return " ";
        else
            return timetable.getTime(rowIndex);
    }

    public void setTimetable(Timetable timetable)
    {
        this.timetable=timetable;
        rowCount=24;
    }
}
