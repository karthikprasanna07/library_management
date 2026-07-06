import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard {

    public void showDashboard() {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps1 =
                    con.prepareStatement("SELECT COUNT(*) FROM books");

            ResultSet rs1 = ps1.executeQuery();

            rs1.next();

            int books = rs1.getInt(1);

            PreparedStatement ps2 =
                    con.prepareStatement("SELECT COUNT(*) FROM members");

            ResultSet rs2 = ps2.executeQuery();

            rs2.next();

            int members = rs2.getInt(1);

            PreparedStatement ps3 =
                    con.prepareStatement("SELECT COUNT(*) FROM issue_books WHERE status='Issued'");

            ResultSet rs3 = ps3.executeQuery();

            rs3.next();

            int issued = rs3.getInt(1);

            System.out.println("\n==========================================");
            System.out.println("      LIBRARY MANAGEMENT SYSTEM");
            System.out.println("==========================================");
            System.out.println("Total Books            : " + books);
            System.out.println("Registered Members     : " + members);
            System.out.println("Books Currently Issued : " + issued);
            System.out.println("==========================================");

            rs1.close();
            rs2.close();
            rs3.close();

            ps1.close();
            ps2.close();
            ps3.close();

            con.close();

        }

        catch(Exception e){

            e.printStackTrace();

        }

    }

}