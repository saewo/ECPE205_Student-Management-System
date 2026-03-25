package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component; // Required for the return type
import java.awt.Color;     // Required for setting colors

import model.DataStore;
import model.Student;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component; // Required for the return type
import java.awt.Color;     // Required for setting colors
/**
 * Panel for viewing all students in a table.
 * 
 * ASSIGNED TO: Student 4 (View / List Feature Owner)
 * 
 * TODO for Student 4:
 * - Add more columns to match all Student fields
 * - Add a refresh button to reload data
 * - Add sorting functionality (click column headers)
 * - Improve table styling (row colors, column widths)
 * - Show "No records found" when the list is empty
 * - Optionally add pagination if the list is long
 */
public class ViewStudentsPanel extends JPanel {
  private DefaultTableModel tableModel;
  private JTable table;
  private TableRowSorter<TableModel> sorter;


  public ViewStudentsPanel() {
    setLayout(new BorderLayout());

    // Title
    JLabel title = new JLabel("All Students", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Table
    String[] columns = { "Student ID", "Name", "Age" , "Email", "Course", "Year Level", "Contact Number"};
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false; // Read-only table
      }




    };





      table = new JTable(tableModel ){
          // Inside your JTable subclass
          @Override
          public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
              Component c = super.prepareRenderer(renderer, row, column);

              // Apply color based on row index or content, skipping selected rows
              if (!isRowSelected(row)) {
                  // Example: Color rows based on data in a specific column
                  String status = (String) getModel().getValueAt(row, 1);
                  c.setBackground(row%2==0 ? Color.white : Color.yellow);
              }
              return c;
          }

      };


    table.setRowHeight(25);
    table.getTableHeader().setReorderingAllowed(false);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    add(scrollPane, BorderLayout.CENTER);

    // Refresh button
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
    JButton refreshBtn = new JButton("Refresh List");
    refreshBtn.addActionListener(e -> loadData());
    buttonPanel.add(refreshBtn);
    add(buttonPanel, BorderLayout.SOUTH);

    // Load initial data
    loadData();
  }



  private void loadData() {
    tableModel.setRowCount(0); // Clear table
    List<Student> students = DataStore.getInstance().getAllStudents();
    for (Student s : students) {
      tableModel.addRow(s.toTableRow());
    }
  }
}
