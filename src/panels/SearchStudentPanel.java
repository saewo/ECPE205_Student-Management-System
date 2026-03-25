package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Panel for searching students by ID or name.
 * 
 * ASSIGNED TO: Student 5 (Search Feature Owner)
 * 
 * TODO for Student 5:
 * - Implement search by name (partial match / contains)
 * - Add search filter options (search by ID, by name, by course, etc.)
 * - Display results in a table or formatted list
 * - Handle case-insensitive search
 * - Show "No results found" message when appropriate
 * - Add a "Clear Search" button
 */
public class SearchStudentPanel extends JPanel {
  private JTextField searchField;
  private JTable table;
  private DefaultTableModel tableModel;
  private JLabel resultLabel;


    public SearchStudentPanel() {
    setLayout(new BorderLayout());

    // Title
    JLabel title = new JLabel("Search Student", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Search bar
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    searchPanel.add(new JLabel("Search:"));
    searchField = new JTextField(20);
    searchPanel.add(searchField);

    JButton searchBtn = new JButton("Search");
    searchBtn.addActionListener(e -> performSearch());
    searchPanel.add(searchBtn);

    JButton clearBtn = new JButton("Clear");
    clearBtn.addActionListener(e -> {
      searchField.setText("");
      tableModel.setRowCount(0);
      resultLabel.setText("");
    });
    searchPanel.add(clearBtn);
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(title, BorderLayout.NORTH);
    topPanel.add(searchPanel, BorderLayout.CENTER);


    add(topPanel, BorderLayout.NORTH);


    JPanel resultsWrapper = new JPanel(new BorderLayout());
    resultsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

    JLabel resultsLabel = new JLabel("Results:");
    resultsLabel.setFont(new Font("Arial", Font.BOLD, 14));
    resultsWrapper.add(resultsLabel, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Age", "Email", "Course", "YearLevel", "Contact Number"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table read-only
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        resultsWrapper.add(scrollPane, BorderLayout.CENTER);

        // Result summary text
        resultLabel = new JLabel("");
        resultsWrapper.add(resultLabel, BorderLayout.SOUTH);

        add(resultsWrapper, BorderLayout.CENTER);

  }

  private void performSearch() {
    String query = searchField.getText().trim().toLowerCase();
    tableModel.setRowCount(0);
    resultLabel.setText("");

    if (query.isEmpty()) {
        resultLabel.setText("Please enter a search term;");
        return;
    }

    List<Student> allStudents = DataStore.getInstance().getAllStudents();
    List<Student> results = new ArrayList<>();

    for (Student s : allStudents) {
      if (s.getId().toLowerCase().contains(query)
          || s.getName().toLowerCase().contains(query)|| String.valueOf(s.getAge()).contains(query) || s.getEmail().toLowerCase().contains(query) ||
              s.getCourse().toLowerCase().contains(query) ||String.valueOf(s.getYearLevel()).contains(query) || String.valueOf(s.getContactNumber()).contains(query)) {
        results.add(s);
      }
    }

    if (results.isEmpty()) {
      resultLabel.setText("No students found matching: \"" + searchField.getText().trim() + "\"");
    } else {
        StringBuilder sb = new StringBuilder();


        for (Student s : results) {
            tableModel.addRow(new Object[]{

                    s.getId(),
                    s.getName(),
                    s.getAge(),
                    s.getEmail(),
                    s.getCourse(),
                    s.getYearLevel(),
                    s.getContactNumber()
});
        }
      resultLabel.setText("Found " +results.size() + " result(s)." );
    }
  }
}
