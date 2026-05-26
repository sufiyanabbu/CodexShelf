package dao;

import model.BorrowRecord;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;

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
}
