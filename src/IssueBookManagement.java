import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

            // fine calculation
            LocalDate issue = LocalDate.parse(issueDate);
            LocalDate due = issue.plusDays(10); // 10 days from the issue date

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
            String issueSql =
                    "INSERT INTO issue_books(issue_id,book_id,member_id,issue_date,due_date,return_date,status,fine) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement psIssue = con.prepareStatement(issueSql);

            psIssue.setInt(1, issueId);
            psIssue.setInt(2, bookId);
            psIssue.setInt(3, memberId);
            psIssue.setString(4, issueDate);
            psIssue.setString(5, due.toString());
            psIssue.setNull(6, Types.DATE);
            psIssue.setString(7, "Issued");
            psIssue.setDouble(8, 0);

            psIssue.executeUpdate();

            // Reduce Quantity
            String updateBook = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?";

            PreparedStatement psUpdate = con.prepareStatement(updateBook);

            psUpdate.setInt(1, bookId);

            psUpdate.executeUpdate();

            System.out.println("Book Issued Successfully!");

            System.out.println("Due Date : " + due); // displays due date

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

            System.out.printf("%-8s %-8s %-10s %-12s %-12s %-12s %-10s %-8s%n",
                    "IssueID","BookID","MemberID","IssueDate","DueDate","Return","Status","Fine");

            while (rs.next()) {

                System.out.printf("%-8d %-8d %-10d %-12s %-12s %-12s %-10s %-8.2f%n",
                        rs.getInt("issue_id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getString("issue_date"),
                        rs.getString("due_date"),
                        rs.getString("return_date"),
                        rs.getString("status"),
                        rs.getDouble("fine"));

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
            String findBook =
                    "SELECT book_id,due_date,status FROM issue_books WHERE issue_id=?";

            PreparedStatement psFind = con.prepareStatement(findBook);

            psFind.setInt(1, issueId);

            ResultSet rs = psFind.executeQuery();

            if (!rs.next()) {

                System.out.println("Issue Record Not Found!");

                return;

            }

            int bookId = rs.getInt("book_id");

            String status = rs.getString("status");

            if(status.equalsIgnoreCase("Returned")){

                System.out.println("Book already returned.");

                return;

            }

            LocalDate dueDate = LocalDate.parse(rs.getString("due_date"));

            LocalDate returnedDate = LocalDate.parse(returnDate);

            long lateDays = ChronoUnit.DAYS.between(dueDate, returnedDate);

            double fine = 0;

            if(lateDays > 0){

                fine = lateDays * 5;

            }

            // Update Issue Record
            String updateIssue =
                    "UPDATE issue_books SET return_date=?, status='Returned', fine=? WHERE issue_id=?";

            PreparedStatement psIssue = con.prepareStatement(updateIssue);

            psIssue.setString(1, returnDate);
            psIssue.setDouble(2, fine);
            psIssue.setInt(3, issueId);

            psIssue.executeUpdate();

            // Increase Quantity
            String updateBook = "UPDATE books SET quantity=quantity+1 WHERE book_id=?";

            PreparedStatement psBook = con.prepareStatement(updateBook);

            psBook.setInt(1, bookId);

            psBook.executeUpdate();

            System.out.println("\nBook Returned Successfully!");

            System.out.println("Due Date      : " + dueDate);

            System.out.println("Return Date   : " + returnedDate);

            if(fine==0){

                System.out.println("Returned On Time");

            }
            else{

                System.out.println("Late Days     : " + lateDays);

                System.out.println("Fine Amount   : ₹" + fine);

            }

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