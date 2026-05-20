package app;
import java.util.InputMismatchException;
import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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

            }
        }
    }
}
