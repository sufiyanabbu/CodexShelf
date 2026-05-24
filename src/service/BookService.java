package service;
import model.Book;
import  dao.BookDao;
import java.util.List;

public class BookService {
     private final BookDao bookDao = new BookDao();

    public void addBook(Book book){
        if(book.getTotalCopies() <= 0){
            System.out.println("Error: copies must be atleast 1");
        }
        bookDao.addBook(book);
    }

    public List<Book> getAllBooks(){
        return bookDao.getAllbooks();
    }

    public void updateBook(Book book){
        Book existing = bookDao.getBookById(book.getBookId());
        if(existing == null){
            System.out.println("Eror: book not found");
            return;
        }
        bookDao.updateBook(book);
    }
    public void deleteBook(int Bookid){
        Book existing = bookDao.getBookById(Bookid);
        if(existing == null){
            System.out.println("Error: book not found");
        }
        bookDao.deleteBook(Bookid);
    }

    public Book getbookById(int bookid){
        return bookDao.getBookById(bookid);
    }
}
