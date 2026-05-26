package dao;

import model.BorrowRecord;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
public class BorrowDao {
    public void addBorrowRecord(BorrowRecord record) {
        String Query = "INSERT INTO borrow_records (book_id, member_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setInt(1, record.getBookId());
            ps.setInt(2, record.getMemberId());
            ps.setDate(3, Date.valueOf(record.getBorrowDate()));
            ps.setDate(4, Date.valueOf(record.getDueDate()));
            ps.setString(5, "borrowed");
            ps.executeUpdate();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void returnBook(int recordId, LocalDate returnDate){
        String Query = "UPDATE borrow_records SET return_date = ?, status = 'RETURNED' WHERE record_id = ?";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setDate(1, Date.valueOf(returnDate));
            ps.setInt(2, recordId);
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    // finds one active borrow for a specific book + member combination
    // used during return to check if borrow exists
    public BorrowRecord getActiveBorrow(int bookId, int memberId) {
        String query = "SELECT * FROM borrow_records WHERE book_id=? AND member_id=? AND status='BORROWED'";
        BorrowRecord record = null;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, bookId);
            ps.setInt(2, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                record = new BorrowRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setMemberId(rs.getInt("member_id"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());
                record.setStatus(rs.getString("status"));
            }
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return record;
    }

    // shows all books currently borrowed — not yet returned
    public List<BorrowRecord> getAllActiveBorrows() {
        List<BorrowRecord> list = new ArrayList<>();
        String query = "SELECT * FROM borrow_records WHERE status='BORROWED'";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowRecord record = new BorrowRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setMemberId(rs.getInt("member_id"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());
                record.setStatus(rs.getString("status"));
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
