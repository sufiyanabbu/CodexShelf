package dao;

import util.DBConnection;
import model.Book;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class BookDao {

    public void addBook(Book book) {
        String Query = "INSERT INTO books (title, author, isbn, genre, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getGenre());
            ps.setInt(5, book.getTotalCopies());
            ps.setInt(6, book.getAvailableCopies());
            ps.executeUpdate();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    public List<Book> getallbooks() {
        String Query = "select * from books";
        List<Book> books = new ArrayList<>();
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ResultSet rs = ps.executeQuery();
            Book book = new Book ();
            while(rs.next()) {
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("auhtor"));
                book.setIsbn(rs.getString("isbn"));
                book.setGenre(rs.getString("genre"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("total_copies"));
                books.add(book);
            }
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }
    public void updateBook (Book book) {
        String Query = "UPDATE books SET title=?, author=?, genre=?, total_copies=? WHERE book_id=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.executeUpdate();
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getAvailableCopies());
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
