package service;

import dao.BookDAO;
import dao.BorrowDAO;
import dao.MemberDAO;
import model.Book;
import model.BorrowRecord;
import model.Member;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BorrowService {

    // needs all three DAOs because issueBook and returnBook touch multiple tables
    private BorrowDAO borrowDAO = new BorrowDAO();
    private BookDAO bookDAO = new BookDAO();
    private MemberDAO memberDAO = new MemberDAO();

    public void issueBook(int bookId, int memberId) {

        // step 1 — does book exist?
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Error: book not found.");
            return;
        }

        // step 2 — is a copy available?
        if (book.getAvailableCopies() <= 0) {
            System.out.println("Sorry, no copies available right now.");
            return;
        }

        // step 3 — does member exist?
        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            System.out.println("Error: member not found.");
            return;
        }

        // step 4 — is member active?
        if (!member.isActive()) {
            System.out.println("Error: member account is inactive.");
            return;
        }

        // step 5 — does member already have this book?
        BorrowRecord existing = borrowDAO.getActiveBorrow(bookId, memberId);
        if (existing != null) {
            System.out.println("Error: member already has this book issued.");
            return;
        }

        // step 6 — all checks passed, create the borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);
        record.setMemberId(memberId);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14)); // 14 day return window
        record.setStatus("BORROWED");
        borrowDAO.addBorrowRecord(record);

        // step 7 — reduce available copies by 1
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookDAO.updateBook(book);

        System.out.println("Book issued successfully.");
        System.out.println("Due date: " + record.getDueDate());
    }

    public void returnBook(int bookId, int memberId) {

        // step 1 — find the active borrow record for this book + member
        BorrowRecord record = borrowDAO.getActiveBorrow(bookId, memberId);
        if (record == null) {
            System.out.println("Error: no active borrow found for this combination.");
            return;
        }

        // step 2 — calculate fine if returned late
        // ChronoUnit.DAYS.between gives negative number if returned early
        LocalDate today = LocalDate.now();
        long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), today);
        if (daysLate > 0) {
            double fine = daysLate * 2.0; // Rs 2 per day
            System.out.println("Returned " + daysLate + " days late. Fine: Rs " + fine);
        } else {
            System.out.println("Returned on time. No fine.");
        }

        // step 3 — mark record as returned with today's date
        borrowDAO.returnBook(record.getRecordId(), today);

        // step 4 — increase available copies back by 1
        Book book = bookDAO.getBookById(bookId);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookDAO.updateBook(book);

        System.out.println("Return successful.");
    }

    public List<BorrowRecord> getAllActiveBorrows() {
        return borrowDAO.getAllActiveBorrows();
    }
}