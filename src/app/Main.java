package app;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Book;
import service.BookService;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookService bookService = new BookService();
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        while (true) {
            System.out.println("=======MENU=======");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. View all members");
            System.out.println("5. Exit");
            System.out.print("Enter your choice:");
            int choice;
            try{
                choice = sc.nextInt();
            }
            catch(InputMismatchException e){
                System.out.println("Please enter number between 1 and 5");
                sc.nextInt();
                continue;
                }
            switch(choice){
                case 1:
                    System.out.println("enter book id: ");
                    int id = sc.nextInt();
                    System.out.println("enter book title: ");
                    String title = sc.nextLine();
                    System.out.println("enter book author: ");
                    String author = sc.nextLine();
                    System.out.println("enter isbn number: ");
                    String isbn = sc.nextLine();
                    System.out.println("enter book genre: ");
                    String genre = sc.nextLine();
                    System.out.println("enter total_copies: ");
                    int total_copies = sc.nextInt();
                    System.out.println("enter available_copies: ");
                    int available_copies = sc.nextInt();
                   // books.add(new book(title,author,isbn,genre,total_copies,available_copies))
            }
        }
    }
}
