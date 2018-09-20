package View;

import Controller.Operations;
import Model.DateTableModel;
import Model.StopTableModel;
import Model.Timetable;
import Model.TransportTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame
{
    private final int WIDTH=600;
    private final int HEIGHT=600;
    private int codeTransport=1;
    private int codeChangeWay=1;
    private int formulaOfCell=-1;
    private int indexOfStop=-1;
    private String TITLE="TSC";
    private Container container;
    private SpringLayout layout;
    private Operations operations;

    public MainWindow(Operations operations)
    {
        setTitle(TITLE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocation(new Point(400,100));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        container=this.getContentPane();
        layout=new SpringLayout();
        container.setLayout(layout);
        this.operations=operations;

        setVisible(true);

        createMenu();
    }

    private void createMenu()
    {
        JButton choiceTransport=new JButton("Транспорт");
        JButton exit=new JButton("Выход");
        final int widthButton=100;
        final int heightButton=30;
        Dimension dimensionButton=new Dimension(widthButton, heightButton);

        choiceTransport.setPreferredSize(dimensionButton);
        exit.setPreferredSize(dimensionButton);

        container.add(choiceTransport);
        container.add(exit);

        layout.putConstraint(SpringLayout.WEST, choiceTransport, 250, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, choiceTransport, 200, SpringLayout.NORTH, container);

        layout.putConstraint(SpringLayout.NORTH, exit, 60, SpringLayout.SOUTH, choiceTransport);
        layout.putConstraint(SpringLayout.WEST, exit, 250, SpringLayout.WEST, container);

        choiceTransport.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                container.removeAll();
                showTransportTables();
            }
        });
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                operations.exitFromProgram();
            }
        });

        revalidate();
        repaint();
    }

    private void showTransportTables()
    {
        TransportTableModel transportTableModel=new TransportTableModel(20);
        transportTableModel.setListTransports(operations.getListTransports(codeTransport));
        StopTableModel stopTableModel=new StopTableModel(10);
        DateTableModel dateTableModel=new DateTableModel(24);

        JButton buttonBack=new JButton("Назад");
        JButton buttonChangeWay=new JButton();

        JRadioButton rbBus=new JRadioButton("Автобус", true);
        JRadioButton rbTram=new JRadioButton("Трамвай", false);
        JRadioButton rbTrolleybus=new JRadioButton("Троллейбус", false);
        JRadioButton rbMetro=new JRadioButton("Метро", false);

        ButtonGroup buttonGroup=new ButtonGroup();

        JLabel labelTransport=new JLabel("Транспорт");
        JLabel labelRoutes=new JLabel("Номера маршрутов");
        JLabel labelStops=new JLabel("Остановки");
        JLabel labelNameWay=new JLabel(" ");
        JLabel labelTime=new JLabel("Время прибытия");

        TableColumn column=null;

        ListSelectionModel selectionModel;


        JTable jTableRoutes=new JTable(transportTableModel);
        for(int i=0; i<=4; i++)
        {
            column=jTableRoutes.getColumnModel().getColumn(i);
            column.setPreferredWidth(30);
        }
        selectionModel=jTableRoutes.getSelectionModel();
        jTableRoutes.setRowHeight(30);
        jTableRoutes.setColumnSelectionAllowed(false);
        jTableRoutes.setRowSelectionAllowed(false);

        //JTable jTableStops=new JTable(10, 1);
        JTable jTableStops=new JTable(stopTableModel);
        jTableStops.setRowHeight(25);
        jTableStops.setColumnSelectionAllowed(false);
        jTableStops.setRowSelectionAllowed(false);

        JTable jTableTimes=new JTable(dateTableModel);
        jTableTimes.setRowHeight(25);
        ///jTableTimes.setCellSelectionEnabled(false);
        jTableTimes.setColumnSelectionAllowed(false);
        jTableTimes.setRowSelectionAllowed(false);


        JScrollPane jspForStops=new JScrollPane(jTableStops);
        JScrollPane jspForTimes=new JScrollPane(jTableTimes);


        buttonGroup.add(rbBus);
        buttonGroup.add(rbTram);
        buttonGroup.add(rbTrolleybus);
        buttonGroup.add(rbMetro);

        container.add(labelTransport);
        container.add(rbBus);
        container.add(rbTram);
        container.add(rbTrolleybus);
        container.add(rbMetro);
        container.add(buttonBack);
        container.add(labelRoutes);
        container.add(jTableRoutes);
        container.add(labelStops);
        container.add(labelNameWay);
        container.add(buttonChangeWay);
        container.add(jspForStops);
        //container.add(jTableStops);
        container.add(labelTime);
        container.add(jspForTimes);


        layout.putConstraint(SpringLayout.NORTH, labelTransport, 180, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, labelTransport, 5, SpringLayout.WEST, container);

        layout.putConstraint(SpringLayout.NORTH, rbBus, 5, SpringLayout.SOUTH, labelTransport);
        layout.putConstraint(SpringLayout.WEST, rbBus, 5, SpringLayout.WEST, container);

        layout.putConstraint(SpringLayout.NORTH, rbTram, 1, SpringLayout.SOUTH, rbBus);
        layout.putConstraint(SpringLayout.WEST, rbTram, 5, SpringLayout.WEST, container);

        layout.putConstraint(SpringLayout.NORTH, rbTrolleybus, 1, SpringLayout.SOUTH, rbTram);
        layout.putConstraint(SpringLayout.WEST, rbTrolleybus, 5, SpringLayout.WEST, container);

        layout.putConstraint(SpringLayout.NORTH, rbMetro, 1, SpringLayout.SOUTH, rbTrolleybus);
        layout.putConstraint(SpringLayout.WEST, rbMetro, 5, SpringLayout.WEST, container);

        layout.putConstraint(SpringLayout.NORTH, buttonBack, 5, SpringLayout.SOUTH, rbMetro);
        layout.putConstraint(SpringLayout.WEST, buttonBack, 5, SpringLayout.WEST, container);

        layout.putConstraint(SpringLayout.WEST, labelRoutes, 10, SpringLayout.EAST, rbTrolleybus);

        layout.putConstraint(SpringLayout.NORTH, jTableRoutes, 2, SpringLayout.SOUTH, labelRoutes);
        layout.putConstraint(SpringLayout.SOUTH, jTableRoutes, 0, SpringLayout.SOUTH, container);
        layout.putConstraint(SpringLayout.WEST, jTableRoutes, 10, SpringLayout.EAST, rbTrolleybus);

        layout.putConstraint(SpringLayout.WEST, labelStops, 2, SpringLayout.EAST, jTableRoutes);

        layout.putConstraint(SpringLayout.NORTH, labelNameWay, 2, SpringLayout.SOUTH, labelStops);
        layout.putConstraint(SpringLayout.WEST, labelNameWay, 2, SpringLayout.EAST, jTableRoutes);

        layout.putConstraint(SpringLayout.NORTH, buttonChangeWay, 0, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.EAST, buttonChangeWay, 0, SpringLayout.EAST, container);

        layout.putConstraint(SpringLayout.NORTH, jspForStops, 2, SpringLayout.SOUTH, labelNameWay);
        layout.putConstraint(SpringLayout.WEST, jspForStops, 2, SpringLayout.EAST, jTableRoutes);
        layout.putConstraint(SpringLayout.EAST, jspForStops, 0, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.SOUTH, jspForStops, -300, SpringLayout.SOUTH, container);

        layout.putConstraint(SpringLayout.NORTH, labelTime, 2, SpringLayout.SOUTH, jspForStops);
        layout.putConstraint(SpringLayout.WEST, labelTime, 2, SpringLayout.EAST, jTableRoutes);

        layout.putConstraint(SpringLayout.NORTH, jspForTimes, 2, SpringLayout.SOUTH, labelTime);
        layout.putConstraint(SpringLayout.WEST, jspForTimes, 2, SpringLayout.EAST, jTableRoutes);
        layout.putConstraint(SpringLayout.EAST, jspForTimes, 0, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.SOUTH, jspForTimes, 0, SpringLayout.SOUTH, container);


        rbBus.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                codeTransport=1;
                transportTableModel.setListTransports(operations.getListTransports(codeTransport));
                labelNameWay.setText(" ");
                dateTableModel.setTimetable(null);
                repaint();
            }
        });
        rbTram.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                codeTransport=2;
                transportTableModel.setListTransports(operations.getListTransports(codeTransport));
                labelNameWay.setText(" ");
                dateTableModel.setTimetable(null);
                repaint();
            }
        });
        rbTrolleybus.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                codeTransport=3;
                transportTableModel.setListTransports(operations.getListTransports(codeTransport));
                labelNameWay.setText(" ");
                dateTableModel.setTimetable(null);
                repaint();
            }
        });
        rbMetro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                codeTransport=4;
                transportTableModel.setListTransports(operations.getListTransports(codeTransport));
                labelNameWay.setText(" ");
                dateTableModel.setTimetable(null);
                repaint();
            }
        });

        buttonBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                container.removeAll();
                codeTransport=1;
                codeChangeWay=1;
                formulaOfCell=-1;
                indexOfStop=-1;
                createMenu();
            }
        });
        buttonChangeWay.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(formulaOfCell!=-1)
                {
                    codeChangeWay = codeChangeWay == 1 ? 2 : 1;
                    stopTableModel.setListStops(operations.getListStops(codeTransport, codeChangeWay, formulaOfCell));
                    dateTableModel.setTimetable(null);
                    labelNameWay.setText(operations.getNameWay(codeTransport, codeChangeWay, formulaOfCell));
                    repaint();
                }
            }
        });

        jTableRoutes.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int selectedRow=jTableRoutes.rowAtPoint(e.getPoint());
                int selectedColumn=jTableRoutes.columnAtPoint(e.getPoint());
                if(!transportTableModel.getValueAt(selectedRow, selectedColumn).equals(" "))
                {
                    formulaOfCell = selectedRow * 5 + selectedColumn;
                    stopTableModel.setListStops(operations.getListStops(codeTransport, codeChangeWay, formulaOfCell));
                    labelNameWay.setText(operations.getNameWay(codeTransport, codeChangeWay, formulaOfCell));
                }
                repaint();
            }
        });

        jTableStops.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                indexOfStop=jTableStops.rowAtPoint(e.getPoint());
                if(!stopTableModel.getValueAt(indexOfStop, 0).equals(" "))
                    dateTableModel.setTimetable(operations.getTimetable(codeChangeWay, formulaOfCell, indexOfStop));
                repaint();
            }
        });

        revalidate();
        repaint();
    }
}