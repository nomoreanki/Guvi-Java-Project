package university.management.system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import university.management.system.TeacherLeaveDetails;

import java.awt.*;
import java.sql.*;
//import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class TeacherLeaveDetails extends JFrame implements ActionListener {

    Choice cEmpId;
    JTable table;
    JButton search, print, cancel;
    
    TeacherLeaveDetails() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Search by Employee Id");
        heading.setBounds(20, 20, 150, 20);
        add(heading);
        
        cEmpId = new Choice();
        cEmpId.setBounds(180, 20, 150, 20);
        add(cEmpId);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from teacher");
            while(rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        table = new JTable();
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from teacherleave");
//            table.setModel(DbUtils.resultSetToTableModel(rs));
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (rs.next()) {
                String[] rowData = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getString(i); // Adjust getString for other data types
                }
                model.addRow(rowData);
            }

            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);
        
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(220, 70, 80, 20);
        cancel.addActionListener(this);
        add(cancel);
        
        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select * from teacherleave where rollno = '"+cEmpId.getSelectedItem()+"'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
//                table.setModel(DbUtils.resultSetToTableModel(rs));
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] columnNames = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = metaData.getColumnName(i);
                }

                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                while (rs.next()) {
                    String[] rowData = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        rowData[i - 1] = rs.getString(i); // Adjust getString for other data types
                    }
                    model.addRow(rowData);
                }

                table.setModel(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherLeaveDetails();
    }
}
