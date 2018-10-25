import Controller.Operations;
import Model.GraphModel.Edge;
import Model.GraphModel.Graph;
import Model.Stop;
import Model.Transport;
import View.MainWindow;

import java.util.LinkedList;
import java.util.List;

public class Main
{

    public static void main(String[]args)
    {
        Operations operations=new Operations();
        MainWindow window=new MainWindow(operations);
    }
}