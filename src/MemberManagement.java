import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.ResultSet;

public class MemberManagement {

    Scanner sc = new Scanner(System.in);

    public void addMember() {

        try {

            System.out.print("Enter Member ID : ");
            int memberId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name : ");
            String name = sc.nextLine();

            System.out.print("Enter Department : ");
            String department = sc.nextLine();

            System.out.print("Enter Phone : ");
            String phone = sc.nextLine();

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO members(member_id,name,department,phone) VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, memberId);
            ps.setString(2, name);
            ps.setString(3, department);
            ps.setString(4, phone);

            int rows = ps.executeUpdate();

            if(rows>0)
                System.out.println("Member Added Successfully!");
            else
                System.out.println("Failed!");

            ps.close();
            con.close();

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void viewMembers() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM members";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.printf("%-10s %-20s %-20s %-15s%n",
                    "ID","Name","Department","Phone");

            while(rs.next()){

                System.out.printf("%-10d %-20s %-20s %-15s%n",
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("phone"));

            }

            rs.close();
            ps.close();
            con.close();

        } catch(Exception e){
            e.printStackTrace();
        }

    }
    public void updateMember() {

        try {

            System.out.print("Enter Member ID : ");
            int memberId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Phone : ");
            String phone = sc.nextLine();

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE members SET phone=? WHERE member_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, phone);
            ps.setInt(2, memberId);

            int rows = ps.executeUpdate();

            if(rows>0)
                System.out.println("Member Updated!");
            else
                System.out.println("Member Not Found!");

            ps.close();
            con.close();

        } catch(Exception e){
            e.printStackTrace();
        }

    }
    public void deleteMember() {

        try {

            System.out.print("Enter Member ID : ");
            int memberId = sc.nextInt();

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM members WHERE member_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, memberId);

            int rows = ps.executeUpdate();

            if(rows>0)
                System.out.println("Member Deleted!");
            else
                System.out.println("Member Not Found!");

            ps.close();
            con.close();

        } catch(Exception e){
            e.printStackTrace();
        }

    }

}