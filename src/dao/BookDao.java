package dao;

import util.DBConnection;
import model.Book;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class BookDao {
    DBConnection dbcon = new DBConnection();
    public void addBook(Book book) {
        String query = "INSERT INTO books (title, author, isbn, genre, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getGenre());
            ps.setInt(5, book.getTotalCopies());
            ps.setInt(6, book.getTotalCopies()); // available = total at the time of adding
            ps.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
