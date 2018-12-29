import Controller.Operations;
import View.MainWindow;

public class Main
{
    public static void main(String[]args)
    {
        Operations operations=new Operations();
        MainWindow window=new MainWindow(operations);
    }
}