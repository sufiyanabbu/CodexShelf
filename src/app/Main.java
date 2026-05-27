package app;

import model.Book;
import model.Member;
import model.BorrowRecord;
import service.BookService;
import service.MemberService;
import service.BorrowService;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static BookService bookService = new BookService();
    static MemberService memberService = new MemberService();
    static BorrowService borrowService = new BorrowService();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Book Menu");
            System.out.println("2. Member Menu");
            System.out.println("3. Borrow Menu");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> bookMenu();
                case 2 -> memberMenu();
                case 3 -> borrowMenu();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ─── BOOK MENU ───────────────────────────────────────────

    static void bookMenu() {
        System.out.println("\n--- Book Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Delete Book");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> addBook();
            case 2 -> viewAllBooks();
            case 3 -> deleteBook();
            default -> System.out.println("Invalid choice.");
        }
    }

    static void addBook() {
        sc.nextLine(); // clear buffer
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();
        System.out.print("Genre: ");
        String genre = sc.nextLine();
        System.out.print("Total Copies: ");
        int copies = sc.nextInt();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setTotalCopies(copies);

        bookService.addBook(book);
    }

    static void viewAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("\nID  | Title                | Author           | Available");
        System.out.println("------------------------------------------------------------");
        for (Book b : books) {
            System.out.printf("%-4d| %-21s| %-17s| %d%n",
                    b.getBookId(), b.getTitle(), b.getAuthor(), b.getAvailableCopies());
        }
    }

    static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        bookService.deleteBook(id);
    }

    // ─── MEMBER MENU ─────────────────────────────────────────

    static void memberMenu() {
        System.out.println("\n--- Member Menu ---");
        System.out.println("1. Register Member");
        System.out.println("2. View All Members");
        System.out.println("3. Deactivate Member");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> registerMember();
            case 2 -> viewAllMembers();
            case 3 -> deactivateMember();
            default -> System.out.println("Invalid choice.");
        }
    }

    static void registerMember() {
        sc.nextLine(); // clear buffer
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();

        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);

        memberService.registerMember(member);
    }

    static void viewAllMembers() {
        List<Member> members = memberService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        System.out.println("\nID  | Name                 | Email                | Active");
        System.out.println("------------------------------------------------------------");
        for (Member m : members) {
            System.out.printf("%-4d| %-21s| %-21s| %s%n",
                    m.getMemberId(), m.getName(), m.getEmail(),
                    m.isActive() ? "Yes" : "No");
        }
    }

    static void deactivateMember() {
        System.out.print("Enter Member ID to deactivate: ");
        int id = sc.nextInt();
        memberService.deactivateMember(id);
    }

    // ─── BORROW MENU ─────────────────────────────────────────

    static void borrowMenu() {
        System.out.println("\n--- Borrow Menu ---");
        System.out.println("1. Issue Book");
        System.out.println("2. Return Book");
        System.out.println("3. View All Active Borrows");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> issueBook();
            case 2 -> returnBook();
            case 3 -> viewActiveBorrows();
            default -> System.out.println("Invalid choice.");
        }
    }

    static void issueBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        borrowService.issueBook(bookId, memberId);
    }

    static void returnBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        borrowService.returnBook(bookId, memberId);
    }

    static void viewActiveBorrows() {
        List<BorrowRecord> records = borrowService.getAllActiveBorrows();
        if (records.isEmpty()) {
            System.out.println("No active borrows.");
            return;
        }
        System.out.println("\nID  | Book ID | Member ID | Borrow Date | Due Date");
        System.out.println("------------------------------------------------------");
        for (BorrowRecord r : records) {
            System.out.printf("%-4d| %-8d| %-10d| %-12s| %s%n",
                    r.getRecordId(), r.getBookId(), r.getMemberId(),
                    r.getBorrowDate(), r.getDueDate());
        }
    }
}