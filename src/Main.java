import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("Connected Successfully!");
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection Failed!");
        }
        BookManagement book = new BookManagement();
        book.viewBooks();
    }
}