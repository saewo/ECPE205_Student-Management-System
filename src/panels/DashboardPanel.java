package panels;

import model.DataStore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {
    private final JLabel countLabel;
    private final JLabel funFactLabel;

    public DashboardPanel() {
        setLayout(new BorderLayout());

        ImageIcon logoIcon = new ImageIcon("C:/ECPE205_Student-Management-System/src/app/logo.png"); //Change lng path sir
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(160, 160, 160));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel title = new JLabel("ARES ACADEMY");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        Image scaledImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(logoIcon);

        titlePanel.add(logoLabel);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        ImageIcon bgIcon = new ImageIcon("C:/ECPE205_Student-Management-System/src/app/libary.jpg"); //Change lng path sir
        JLabel bgLabel = new JLabel(bgIcon);
        bgLabel.setLayout(new BoxLayout(bgLabel, BoxLayout.Y_AXIS));
        bgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        countLabel = new JLabel("Currently enrolled: " + DataStore.getInstance().getCount() +" students!");
        countLabel.setForeground(Color.WHITE);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgLabel.add(Box.createVerticalStrut(20));
        bgLabel.add(countLabel);

        List<model.Student> students = DataStore.getInstance().getAllStudents();
        double averageAge = 0;
        if (!students.isEmpty()) {
            int totalAge = 0;
            for (model.Student s : students) totalAge += s.getAge();
            averageAge = (double) totalAge / students.size();
        }
        funFactLabel = new JLabel("Average age of students: " + String.format("%.0f", averageAge) + " years!");
        funFactLabel.setForeground(Color.WHITE);
        funFactLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        funFactLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgLabel.add(Box.createVerticalStrut(10));
        bgLabel.add(funFactLabel);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshBtn.addActionListener(e -> refreshData());
        bgLabel.add(Box.createVerticalStrut(20));
        bgLabel.add(refreshBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(bgLabel);
        centerPanel.setBackground(Color.BLACK);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void refreshData() {
        countLabel.setText("Currently enrolled: " + DataStore.getInstance().getCount() +" students!");
        List<model.Student> students = DataStore.getInstance().getAllStudents();
        double averageAge = 0;
        if (!students.isEmpty()) {
            int totalAge = 0;
            for (model.Student s : students) totalAge += s.getAge();
            averageAge = (double) totalAge / students.size();
        }
        funFactLabel.setText("Average age of students: " + String.format("%.0f", averageAge) + " years!");
    }
}