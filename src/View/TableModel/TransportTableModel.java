package View.TableModel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import Model.Transport;

public class TransportTableModel extends AbstractTableModel
{
   private final int columnCount=5;
   private int rowCount;
   private List<Transport> listTransports;

   public TransportTableModel(int rowCount)
   {
       this.rowCount=rowCount;
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
       int formula=rowIndex*5+columnIndex;
       if(formula>listTransports.size()-1)
          return " ";
       else
           return listTransports.get(formula).getNumber();
   }

   public void clearListTransport()
   {
       listTransports.clear();
   }

   public void setListTransports(List<Transport> listTransports)
   {
      this.listTransports=listTransports;
   }
}