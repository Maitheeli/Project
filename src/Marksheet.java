import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

class Marksheet extends Frame {

    private Label percen;
    private Label cg;
    private TextField t1, t2, t3, t4, t5;
    private TextField percenTextField;
    private TextField cgTextField;
    private Connection connection;
    private TextField tf;
    private TextField tf2;
    private CheckboxGroup c;
    private Checkbox c1;
    private Choice ch;

    Marksheet() {
        setSize(800, 600);
        setLayout(null);
        setTitle("Student Marksheet");
        setBackground(Color.LIGHT_GRAY);

        Font font = new Font("Times Roman", Font.BOLD, 15);

        Label l = new Label("Name");
        l.setBounds(450, 80, 100, 30);
        l.setFont(font);
        add(l);

        Label l2 = new Label("Seat Number");
        l2.setBounds(450, 150, 100, 30);
        l2.setFont(font);
        add(l2);

        Label g = new Label("Gender");
        g.setBounds(450, 250, 100, 30);
        g.setFont(font);
        add(g);

        c = new CheckboxGroup();
        c1 = new Checkbox("Male", c, false);
        c1.setBounds(550, 250, 70, 20);
        c1.setFont(font);
        Checkbox c2 = new Checkbox("Female", c, true);
        c2.setBounds(650, 250, 70, 20);
        c2.setFont(font);
        add(c1);
        add(c2);

        ch = new Choice();
        ch.setBounds(550, 350, 80, 30);
        ch.setFont(font);
        ch.add("A");
        ch.add("B");
        ch.add("C");
        ch.add("D");
        ch.add("E");
        add(ch);

        Label l3 = new Label("Section");
        l3.setBounds(450, 350, 80, 30);
        l3.setFont(font);
        add(l3);

        tf = new TextField(20);
        tf.setBounds(580, 80, 200, 30);
        tf.setFont(font);
        add(tf);

        tf2 = new TextField(20);
        tf2.setBounds(580, 150, 200, 30);
        tf2.setFont(font);
        add(tf2);

        Button p = new Button("Proceed");
        p.setBounds(550, 450, 100, 40);
        p.setFont(font);
        add(p);

        p.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrame();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        setVisible(true);

        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/mydb";
            String user = "root";
            String password = "Abcd@1234";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void newFrame() {
        Frame frame2 = new Frame();

        frame2.setSize(500, 500);
        frame2.setLayout(null);
        frame2.setVisible(true);

        Label l4 = new Label("Discrete Maths and Probability Theory");
        l4.setBounds(150, 100, 250, 30);
        frame2.add(l4);

        Label l5 = new Label("Database Management System");
        l5.setBounds(150, 140, 250, 30);
        frame2.add(l5);

        Label l6 = new Label("Object-Oriented Programming Language");
        l6.setBounds(150, 180, 250, 30);
        frame2.add(l6);

        Label l7 = new Label("Web Development");
        l7.setBounds(150, 220, 250, 30);
        frame2.add(l7);

        Label l8 = new Label("Python Programming");
        l8.setBounds(150, 260, 250, 30);
        frame2.add(l8);

        t1 = new TextField(20);
        t1.setBounds(430, 100, 100, 30);
        frame2.add(t1);

        t2 = new TextField(20);
        t2.setBounds(430, 140, 100, 30);
        frame2.add(t2);

        t3 = new TextField(20);
        t3.setBounds(430, 180, 100, 30);
        frame2.add(t3);

        t4 = new TextField(20);
        t4.setBounds(430, 220, 100, 30);
        frame2.add(t4);

        t5 = new TextField(20);
        t5.setBounds(430, 260, 100, 30);
        frame2.add(t5);

        Button cal = new Button("Calculate");
        cal.setBounds(300, 350, 100, 30);
        frame2.add(cal);

        percen = new Label("Percentage:");
        percen.setBounds(150, 400, 100, 30);
        frame2.add(percen);

        cg = new Label("CGPA:");
        cg.setBounds(150, 450, 100, 30);
        frame2.add(cg);

        percenTextField = new TextField(20);
        percenTextField.setBounds(430, 400, 100, 30);
        frame2.add(percenTextField);

        cgTextField = new TextField(30);
        cgTextField.setBounds(430, 450, 100, 30);
        frame2.add(cgTextField);

        Button close = new Button("Close");
        close.setBounds(300, 500, 100, 30);
        frame2.add(close);

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame2.dispose();
            }
        });

        cal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });
    }

    public void calculateResults() {
        try {
            double m1 = Double.parseDouble(t1.getText());
            double m2 = Double.parseDouble(t2.getText());
            double m3 = Double.parseDouble(t3.getText());
            double m4 = Double.parseDouble(t4.getText());
            double m5 = Double.parseDouble(t5.getText());

            double percentage = (m1 + m2 + m3 + m4 + m5) / 5;
            double cgpa = percentage / 10;

            String sql = "INSERT INTO student_marks (id, name, seat_number, gender, section, subject1, subject2, subject3, subject4, subject5, percentage, cgpa) " +
                         "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tf.getText()); // Name
                preparedStatement.setString(2, tf2.getText()); // Seat Number
                preparedStatement.setString(3, c1.getState() ? "Male" : "Female"); // Gender
                preparedStatement.setString(4, ch.getSelectedItem()); // Section
                preparedStatement.setDouble(5, m1); // Subject 1
                preparedStatement.setDouble(6, m2); // Subject 2
                preparedStatement.setDouble(7, m3); // Subject 3
                preparedStatement.setDouble(8, m4); // Subject 4
                preparedStatement.setDouble(9, m5); // Subject 5
                preparedStatement.setDouble(10, percentage); // Percentage
                preparedStatement.setDouble(11, cgpa); // CGPA

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    percenTextField.setText(String.format("%.2f", percentage) + "%");
                    cgTextField.setText(String.format("%.2f", cgpa));
                } else {
                    JOptionPane.showMessageDialog(null, "Error inserting data into the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Marksheet();
    }
}
