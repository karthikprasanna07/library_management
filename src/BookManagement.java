import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.ResultSet;

public class BookManagement {

    Scanner sc = new Scanner(System.in);

    public void addBook() {

        try {

            System.out.print("Enter Book ID : ");
            int bookId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Title : ");
            String title = sc.nextLine();

            System.out.print("Enter Author : ");
            String author = sc.nextLine();

            System.out.print("Enter Category : ");
            String category = sc.nextLine();

            System.out.print("Enter Publisher : ");
            String publisher = sc.nextLine();

            System.out.print("Enter Price : ");
            double price = sc.nextDouble();

            System.out.print("Enter Quantity : ");
            int quantity = sc.nextInt();

            // Get Database Connection
            Connection con = DBConnection.getConnection();

            // SQL Query
            String sql = "INSERT INTO books(book_id, title, author, category, publisher, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Create PreparedStatement
            PreparedStatement ps = con.prepareStatement(sql);

            // Set Values
            ps.setInt(1, bookId);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setString(4, category);
            ps.setString(5, publisher);
            ps.setDouble(6, price);
            ps.setInt(7, quantity);

            // Execute Query
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book Added Successfully!");
            } else {
                System.out.println("Failed to Add Book!");
            }

            // Close Resources
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM books";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.printf("%-10s %-25s %-20s %-15s %-20s %-10s %-10s%n",
                    "ID","Title","Author","Category","Publisher","Price","Qty");

            System.out.println("---------------------------------------------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf("%-10d %-25s %-20s %-15s %-20s %-10.2f %-10d%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("publisher"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void updateBook() {

        try {

            System.out.print("Enter Book ID : ");
            int bookId = sc.nextInt();

            System.out.print("Enter New Price : ");
            double price = sc.nextDouble();

            System.out.print("Enter New Quantity : ");
            int quantity = sc.nextInt();

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE books SET price=?, quantity=? WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, price);
            ps.setInt(2, quantity);
            ps.setInt(3, bookId);

            int rows = ps.executeUpdate();

            if(rows>0)
                System.out.println("Book Updated Successfully!");
            else
                System.out.println("Book Not Found!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void deleteBook() {

        try {

            System.out.print("Enter Book ID : ");
            int bookId = sc.nextInt();

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM books WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, bookId);

            int rows = ps.executeUpdate();

            if(rows>0)
                System.out.println("Book Deleted Successfully!");
            else
                System.out.println("Book Not Found!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}