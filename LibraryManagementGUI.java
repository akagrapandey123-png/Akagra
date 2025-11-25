import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

// ========================== DB CONNECTION ==========================
class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_PASSWORD"; // CHANGE THIS

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// ========================== BOOK CLASS ==========================
class Book {
    int id;
    String title;
    String author;
    boolean issued;
    int issuedTo;

    public Book(int id, String title, String author, boolean issued, int issuedTo) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = issued;
        this.issuedTo = issuedTo;
    }
}

// ========================== VISITOR CLASS ==========================
class Visitor {
    int visitorId;
    String name;
    String purpose;

    public Visitor(int vid, String name, String purpose) {
        this.visitorId = vid;
        this.name = name;
        this.purpose = purpose;
    }
}

// ========================== VIP MEMBER CLASS ==========================
class VIPMember {
    int memberId;
    String name;
    String phone;

    public VIPMember(int id, String name, String phone) {
        this.memberId = id;
        this.name = name;
        this.phone = phone;
    }
}

// ======================= MAIN GUI CLASS ==========================
public class LibraryManagementGUI extends JFrame {

    JTextArea output;

    public LibraryManagementGUI() {

        setTitle("Library Management System - MySQL Integrated");
        setSize(750, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane pane = new JScrollPane(output);
        pane.setBounds(350, 20, 360, 470);
        add(pane);

        // --- Buttons ---
        addButton("Add Book", 20, 20, e -> addBook());
        addButton("Display Books", 20, 70, e -> displayBooks());
        addButton("Search Book", 20, 120, e -> searchBook());
        addButton("Delete Book", 20, 170, e -> deleteBook());

        addButton("Add Visitor", 20, 230, e -> addVisitor());
        addButton("Display Visitors", 20, 280, e -> displayVisitors());

        addButton("Add VIP Member", 20, 340, e -> addVIP());
        addButton("Display VIP Members", 20, 390, e -> displayVIP());

        addButton("Issue Book (VIP)", 180, 20, e -> issueBook());
        addButton("Return Book (VIP)", 180, 70, e -> returnBook());

        addButton("Exit", 180, 430, e -> System.exit(0));

        setVisible(true);
    }

    // -------------------- BUTTON CREATION --------------------
    void addButton(String txt, int x, int y, ActionListener action) {
        JButton btn = new JButton(txt);
        btn.setBounds(x, y, 150, 40);
        btn.addActionListener(action);
        add(btn);
    }

    int getInt(String msg) {
        try {
            return Integer.parseInt(JOptionPane.showInputDialog(msg));
        } catch (Exception e) {
            output.append("Invalid input\n");
            return -1;
        }
    }

    // ====================== ADD BOOK ======================
    void addBook() {
        int id = getInt("Enter Book ID:");
        String title = JOptionPane.showInputDialog("Enter Title:");
        String author = JOptionPane.showInputDialog("Enter Author:");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO books VALUES (?,?,?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setBoolean(4, false);
            ps.setInt(5, -1);
            ps.executeUpdate();

            output.append("✔ Book Added: " + title + "\n");

        } catch (Exception e) {
            output.append("✘ Error Adding Book\n");
        }
    }

    // ====================== DISPLAY BOOKS ======================
    void displayBooks() {
        output.append("\n------ BOOK LIST ------\n");

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                output.append("ID: " + rs.getInt("id") +
                        ", " + rs.getString("title") +
                        " | Issued: " + rs.getBoolean("issued") + "\n");
            }

        } catch (Exception e) {
            output.append("✘ Error Fetching Books\n");
        }
    }

    // ====================== SEARCH BOOK ======================
    void searchBook() {
        int id = getInt("Enter Book ID:");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM books WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                output.append("✔ Found: " + rs.getString("title") + "\n");
            else
                output.append("✘ Book Not Found\n");

        } catch (Exception e) {
            output.append("✘ Error Searching Book\n");
        }
    }

    // ====================== DELETE BOOK ======================
    void deleteBook() {
        int id = getInt("Enter Book ID:");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM books WHERE id=? AND issued=FALSE");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                output.append("✔ Book Deleted\n");
            else
                output.append("✘ Cannot delete issued / non-existing book\n");

        } catch (Exception e) {
            output.append("✘ Error Deleting Book\n");
        }
    }

    // ====================== ADD VISITOR ======================
    void addVisitor() {
        int vid = getInt("Enter Visitor ID:");
        String name = JOptionPane.showInputDialog("Enter Name:");
        String purpose = JOptionPane.showInputDialog("Enter Purpose:");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO visitors VALUES (?,?,?)");
            ps.setInt(1, vid);
            ps.setString(2, name);
            ps.setString(3, purpose);
            ps.executeUpdate();

            output.append("✔ Visitor Added\n");

        } catch (Exception e) {
            output.append("✘ Error Adding Visitor\n");
        }
    }

    // ====================== DISPLAY VISITORS ======================
    void displayVisitors() {
        output.append("\n------ VISITOR LIST ------\n");

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM visitors");

            while (rs.next()) {
                output.append("ID: " + rs.getInt("visitorId") +
                        ", " + rs.getString("name") + "\n");
            }

        } catch (Exception e) {
            output.append("✘ Error Fetching Visitors\n");
        }
    }

    // ====================== ADD VIP MEMBER ======================
    void addVIP() {
        int id = getInt("Enter VIP ID:");
        String name = JOptionPane.showInputDialog("Enter Name:");
        String phone = JOptionPane.showInputDialog("Enter Phone:");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO vipmembers VALUES (?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.executeUpdate();

            output.append("✔ VIP Member Added\n");

        } catch (Exception e) {
            output.append("✘ Error Adding VIP\n");
        }
    }

    // ====================== DISPLAY VIP MEMBERS ======================
    void displayVIP() {
        output.append("\n------ VIP MEMBERS ------\n");

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM vipmembers");

            while (rs.next()) {
                output.append("ID: " + rs.getInt("memberId") +
                        ", " + rs.getString("name") + "\n");
            }

        } catch (Exception e) {
            output.append("✘ Error Fetching VIPs\n");
        }
    }

    // ====================== ISSUE BOOK ======================
    void issueBook() {
        int bid = getInt("Enter Book ID:");
        int mid = getInt("Enter VIP Member ID:");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE books SET issued=TRUE, issuedTo=? WHERE id=? AND issued=FALSE");
            ps.setInt(1, mid);
            ps.setInt(2, bid);

            int rows = ps.executeUpdate();

            if (rows > 0)
                output.append("✔ Book Issued Successfully\n");
            else
                output.append("✘ Book is already issued or does not exist\n");

        } catch (Exception e) {
            output.append("✘ ERROR: Issue failed\n");
        }
    }

    // ====================== RETURN BOOK ======================
    void returnBook() {
        int bid = getInt("Enter Book ID:");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE books SET issued=FALSE, issuedTo=-1 WHERE id=? AND issued=TRUE");
            ps.setInt(1, bid);

            int rows = ps.executeUpdate();
            if (rows > 0)
                output.append("✔ Book Returned Successfully\n");
            else
                output.append("✘ Book was not issued or doesn't exist\n");

        } catch (Exception e) {
            output.append("✘ Error Returning Book\n");
        }
    }

    // ====================== MAIN ======================
    public static void main(String[] args) {
        new LibraryManagementGUI();
    }
}
