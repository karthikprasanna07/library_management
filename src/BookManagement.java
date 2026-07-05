import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

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

    }

    public void updateBook() {

    }

    public void deleteBook() {

    }

}