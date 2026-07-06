import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Dashboard dashboard = new Dashboard();
        BookManagement book = new BookManagement();
        MemberManagement member = new MemberManagement();
        IssueBookManagement issue = new IssueBookManagement();

        while (true) {

            dashboard.showDashboard();

            System.out.println("\n==========================================");
            System.out.println("      LIBRARY MANAGEMENT SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Issue Book Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice : ");

            int mainChoice = sc.nextInt();

            switch (mainChoice) {

                // ================= BOOK MANAGEMENT =================

                case 1:

                    while (true) {

                        System.out.println("\n========== BOOK MANAGEMENT ==========");
                        System.out.println("1. Add Book");
                        System.out.println("2. View Books");
                        System.out.println("3. Update Book");
                        System.out.println("4. Delete Book");
                        System.out.println("5. Back");
                        System.out.print("Enter your choice : ");

                        int choice = sc.nextInt();

                        switch (choice) {

                            case 1:
                                book.addBook();
                                break;

                            case 2:
                                book.viewBooks();
                                break;

                            case 3:
                                book.updateBook();
                                break;

                            case 4:
                                book.deleteBook();
                                break;

                            case 5:
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }

                        if (choice == 5)
                            break;
                    }

                    break;

                // ================= MEMBER MANAGEMENT =================

                case 2:

                    while (true) {

                        System.out.println("\n========= MEMBER MANAGEMENT =========");
                        System.out.println("1. Add Member");
                        System.out.println("2. View Members");
                        System.out.println("3. Update Member");
                        System.out.println("4. Delete Member");
                        System.out.println("5. Back");
                        System.out.print("Enter your choice : ");

                        int choice = sc.nextInt();

                        switch (choice) {

                            case 1:
                                member.addMember();
                                break;

                            case 2:
                                member.viewMembers();
                                break;

                            case 3:
                                member.updateMember();
                                break;

                            case 4:
                                member.deleteMember();
                                break;

                            case 5:
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }

                        if (choice == 5)
                            break;
                    }

                    break;

                // ================= ISSUE BOOK MANAGEMENT =================

                case 3:

                    while (true) {

                        System.out.println("\n======= ISSUE BOOK MANAGEMENT =======");
                        System.out.println("1. Issue Book");
                        System.out.println("2. View Issued Books");
                        System.out.println("3. Return Book");
                        System.out.println("4. Delete Issue Record");
                        System.out.println("5. Back");
                        System.out.print("Enter your choice : ");

                        int choice = sc.nextInt();

                        switch (choice) {

                            case 1:
                                issue.issueBook();
                                break;

                            case 2:
                                issue.viewIssuedBooks();
                                break;

                            case 3:
                                issue.returnBook();
                                break;

                            case 4:
                                issue.deleteIssueRecord();
                                break;

                            case 5:
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }

                        if (choice == 5)
                            break;
                    }

                    break;

                // ================= EXIT =================

                case 4:

                    System.out.println("\nThank You for Using Library Management System.");
                    sc.close();
                    System.exit(0);

                default:

                    System.out.println("Invalid Choice!");

            }

        }
    }
}