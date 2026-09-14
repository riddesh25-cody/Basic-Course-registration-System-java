import javax.swing.*;
import java.awt.*;

// Module 1: User Data Class
class User {
    String username = "";
    String name = "New Student";
    String address = "Not Provided";
    String pfp = "default_avatar.jpg";
    String enrolledCourse = "None";
}

// Module 2: Main GUI Application
public class CourseRegistrationApp extends JFrame {
    
    private User student = new User();
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Profile Labels that need dynamic updating
    private JLabel lblNameDisplay, lblAddressDisplay, lblCourseDisplay, lblPfpDisplay;
    // Edit Profile Fields
    private JTextField txtEditName, txtEditAddress, txtEditPfp;

    public CourseRegistrationApp() {
        setTitle("Course Registration System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Build all "pages" (Panels)
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createProfilePanel(), "Profile");
        mainPanel.add(createEditProfilePanel(), "EditProfile");
        mainPanel.add(createCoursesPanel(), "Courses");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login"); // Start at login screen
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

        panel.add(new JLabel("=== SYSTEM LOGIN ===", SwingConstants.CENTER));
        
        JTextField txtUser = new JTextField();
        txtUser.setBorder(BorderFactory.createTitledBorder("Username (Enter anything)"));
        panel.add(txtUser);
        
        JPasswordField txtPass = new JPasswordField();
        txtPass.setBorder(BorderFactory.createTitledBorder("Password (Enter anything)"));
        panel.add(txtPass);
        
        JButton btnLogin = new JButton("Login");
        panel.add(btnLogin);

        // MODIFIED: Accepts ANY username and password as long as it's not blank
        btnLogin.addActionListener(e -> {
            String enteredUser = txtUser.getText().trim();
            String enteredPass = new String(txtPass.getPassword()).trim();

            if (!enteredUser.isEmpty() && !enteredPass.isEmpty()) {
                student.username = enteredUser;
                student.name = enteredUser; // Set their profile name to their username automatically
                
                txtUser.setText(""); 
                txtPass.setText(""); // clear fields for next time
                cardLayout.show(mainPanel, "Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Please type a username and password to enter!", "Notice", JOptionPane.WARNING_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        panel.add(new JLabel("=== MAIN MENU ===", SwingConstants.CENTER));
        
        JButton btnProfile = new JButton("View / Edit Profile");
        JButton btnCourses = new JButton("Course Enrollment");
        JButton btnLogout = new JButton("Logout");

        panel.add(btnProfile);
        panel.add(btnCourses);
        panel.add(btnLogout);

        btnProfile.addActionListener(e -> {
            updateProfileView();
            cardLayout.show(mainPanel, "Profile");
        });
        btnCourses.addActionListener(e -> cardLayout.show(mainPanel, "Courses"));
        
        btnLogout.addActionListener(e -> {
            // Reset course and profile data on logout so the next person starts fresh
            student = new User(); 
            cardLayout.show(mainPanel, "Login");
        });

        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        panel.add(new JLabel("=== USER PROFILE ===", SwingConstants.CENTER));
        
        lblPfpDisplay = new JLabel("PFP: ");
        lblNameDisplay = new JLabel("Name: ");
        lblAddressDisplay = new JLabel("Address: ");
        lblCourseDisplay = new JLabel("Enrolled Course: ");
        lblCourseDisplay.setForeground(Color.BLUE);

        panel.add(lblPfpDisplay);
        panel.add(lblNameDisplay);
        panel.add(lblAddressDisplay);
        panel.add(lblCourseDisplay);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnEdit = new JButton("Edit Profile");
        JButton btnBack = new JButton("Back to Menu");
        btnPanel.add(btnEdit);
        btnPanel.add(btnBack);
        panel.add(btnPanel);

        btnEdit.addActionListener(e -> {
            txtEditName.setText(student.name);
            txtEditAddress.setText(student.address);
            txtEditPfp.setText(student.pfp);
            cardLayout.show(mainPanel, "EditProfile");
        });
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createEditProfilePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panel.add(new JLabel("=== EDIT PROFILE ===", SwingConstants.CENTER));
        
        txtEditPfp = new JTextField();
        txtEditPfp.setBorder(BorderFactory.createTitledBorder("Profile Picture Filename"));
        panel.add(txtEditPfp);

        txtEditName = new JTextField();
        txtEditName.setBorder(BorderFactory.createTitledBorder("Full Name"));
        panel.add(txtEditName);

        txtEditAddress = new JTextField();
        txtEditAddress.setBorder(BorderFactory.createTitledBorder("Address"));
        panel.add(txtEditAddress);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        panel.add(btnPanel);

        btnSave.addActionListener(e -> {
            student.name = txtEditName.getText();
            student.address = txtEditAddress.getText();
            student.pfp = txtEditPfp.getText();
            updateProfileView();
            JOptionPane.showMessageDialog(this, "Profile Saved!");
            cardLayout.show(mainPanel, "Profile");
        });
        btnCancel.addActionListener(e -> cardLayout.show(mainPanel, "Profile"));

        return panel;
    }

    private JPanel createCoursesPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(new JLabel("=== AVAILABLE COURSES ===", SwingConstants.CENTER));

        panel.add(createCourseRow("1. Java", "Master OOP, backend dev, and JVM.", "Java Course"));
        panel.add(createCourseRow("2. Python", "Learn scripting, automation, & data science.", "Python Course"));
        panel.add(createCourseRow("3. C", "Understand pointers and memory management.", "C Course"));
        panel.add(createCourseRow("4. C++", "High-performance apps and game dev.", "C++ Course"));

        JButton btnBack = new JButton("Back to Menu");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnBack);
        panel.add(bottomPanel);

        return panel;
    }

    private JPanel createCourseRow(String title, String desc, String courseValue) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createEtchedBorder());
        
        JLabel lblInfo = new JLabel("<html><b>" + title + "</b><br/>" + desc + "</html>");
        lblInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JButton btnEnroll = new JButton("Enroll");

        btnEnroll.addActionListener(e -> {
            student.enrolledCourse = courseValue;
            JOptionPane.showMessageDialog(this, "Successfully enrolled in " + courseValue + "!");
            cardLayout.show(mainPanel, "Menu");
        });

        row.add(lblInfo, BorderLayout.CENTER);
        row.add(btnEnroll, BorderLayout.EAST);
        return row;
    }

    private void updateProfileView() {
        lblNameDisplay.setText("Name: " + student.name);
        lblAddressDisplay.setText("Address: " + student.address);
        lblPfpDisplay.setText("PFP File: " + student.pfp);
        lblCourseDisplay.setText("Enrolled Course: " + student.enrolledCourse);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CourseRegistrationApp().setVisible(true);
        });
    }
}