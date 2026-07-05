import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Scanner;

public class IssueBookManagement {

    Scanner sc = new Scanner(System.in);

    public void issueBook() {

        try {

            System.out.print("Enter Issue ID : ");
            int issueId = sc.nextInt();

            System.out.print("Enter Book ID : ");
            int bookId = sc.nextInt();

            System.out.print("Enter Member ID : ");
            int memberId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Issue Date (YYYY-MM-DD) : ");
            String issueDate = sc.nextLine();

            Connection con = DBConnection.getConnection();

            // Check Book Availability
            String checkBook = "SELECT quantity FROM books WHERE book_id = ?";

            PreparedStatement psBook = con.prepareStatement(checkBook);
            psBook.setInt(1, bookId);

            ResultSet rsBook = psBook.executeQuery();

            if (!rsBook.next()) {
                System.out.println("Book Not Found!");
                return;
            }

            int quantity = rsBook.getInt("quantity");

            if (quantity <= 0) {
                System.out.println("Book Out Of Stock!");
                return;
            }

            // Check Member
            String checkMember = "SELECT * FROM members WHERE member_id = ?";

            PreparedStatement psMember = con.prepareStatement(checkMember);
            psMember.setInt(1, memberId);

            ResultSet rsMember = psMember.executeQuery();

            if (!rsMember.next()) {
                System.out.println("Member Not Found!");
                return;
            }

            // Insert Issue Record
            String issueSql = "INSERT INTO issue_books(issue_id,book_id,member_id,issue_date,return_date,status) VALUES(?,?,?,?,?,?)";

            PreparedStatement psIssue = con.prepareStatement(issueSql);

            psIssue.setInt(1, issueId);
            psIssue.setInt(2, bookId);
            psIssue.setInt(3, memberId);
            psIssue.setString(4, issueDate);
            psIssue.setNull(5, Types.DATE);
            psIssue.setString(6, "Issued");

            psIssue.executeUpdate();

            // Reduce Quantity
            String updateBook = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?";

            PreparedStatement psUpdate = con.prepareStatement(updateBook);

            psUpdate.setInt(1, bookId);

            psUpdate.executeUpdate();

            System.out.println("Book Issued Successfully!");

            rsBook.close();
            rsMember.close();

            psBook.close();
            psMember.close();
            psIssue.close();
            psUpdate.close();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void viewIssuedBooks() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM issue_books";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.printf("%-10s %-10s %-10s %-15s %-15s %-15s%n",
                    "IssueID", "BookID", "MemberID", "IssueDate", "ReturnDate", "Status");

            while (rs.next()) {

                System.out.printf("%-10d %-10d %-10d %-15s %-15s %-15s%n",
                        rs.getInt("issue_id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getString("issue_date"),
                        rs.getString("return_date"),
                        rs.getString("status"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void returnBook() {

        try {

            System.out.print("Enter Issue ID : ");
            int issueId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Return Date (YYYY-MM-DD) : ");
            String returnDate = sc.nextLine();

            Connection con = DBConnection.getConnection();

            // Find Book ID
            String findBook = "SELECT book_id FROM issue_books WHERE issue_id=?";

            PreparedStatement psFind = con.prepareStatement(findBook);

            psFind.setInt(1, issueId);

            ResultSet rs = psFind.executeQuery();

            if (!rs.next()) {

                System.out.println("Issue Record Not Found!");

                return;

            }

            int bookId = rs.getInt("book_id");

            // Update Issue Record
            String updateIssue = "UPDATE issue_books SET return_date=?, status='Returned' WHERE issue_id=?";

            PreparedStatement psIssue = con.prepareStatement(updateIssue);

            psIssue.setString(1, returnDate);
            psIssue.setInt(2, issueId);

            psIssue.executeUpdate();

            // Increase Quantity
            String updateBook = "UPDATE books SET quantity=quantity+1 WHERE book_id=?";

            PreparedStatement psBook = con.prepareStatement(updateBook);

            psBook.setInt(1, bookId);

            psBook.executeUpdate();

            System.out.println("Book Returned Successfully!");

            rs.close();
            psFind.close();
            psIssue.close();
            psBook.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteIssueRecord() {

        try {

            System.out.print("Enter Issue ID : ");

            int issueId = sc.nextInt();

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM issue_books WHERE issue_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, issueId);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Issue Record Deleted!");
            else
                System.out.println("Issue Record Not Found!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}