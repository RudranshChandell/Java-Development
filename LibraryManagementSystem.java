import java.util.*;

class Book {
    private String bookname;
    private String author;
    private String ISBN;

    public Book(String title, String author, String ISBN) {
        this.bookname = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + bookname + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }
}

class User {
    private String username;
    private String userID;

    public User(String username, String userID) {
        this.username = username;
        this.userID = userID;
    }

    public String getname() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}

class Library {
    private List<Book> books;
    private Map<String, User> users;
    private Map<String, String> borrowedBooks;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new HashMap<>();
        this.borrowedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String ISBN) {
        books.removeIf(book -> book.getISBN().equals(ISBN));
        borrowedBooks.remove(ISBN);
    }

    public void registerUser(User user) {
        users.put(user.getUserID(), user);
    }

    public boolean borrowBook(String ISBN, String userID) {
        if (findBookByISBN(ISBN) == null) {
            System.out.println("Book not found.");
            return false;
        }
        if (borrowedBooks.containsKey(ISBN)) {
            System.out.println("Book already borrowed.");
            return false;
        }
        if (users.containsKey(userID)) {
            borrowedBooks.put(ISBN, userID);
            return true;
        }
        System.out.println("User not registered.");
        return false;
    }

    public boolean returnBook(String ISBN) {
        if (borrowedBooks.containsKey(ISBN)) {
            borrowedBooks.remove(ISBN);
            return true;
        }
        System.out.println("Book was not borrowed.");
        return false;
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books are currently borrowed.");
            return;
        }
        for (Map.Entry<String, String> entry : borrowedBooks.entrySet()) {
            String ISBN = entry.getKey();
            String userID = entry.getValue();
            Book book = findBookByISBN(ISBN);
            User user = users.get(userID);
            if (book != null && user != null) {
                System.out.println(book + " borrowed by " + user);
            }
        }
    }

    Book findBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Display All Books");
            System.out.println("2. Display Borrowed Books");
            System.out.println("3. Add Book");
            System.out.println("4. Remove a Book");
            System.out.println("5. Register a User");
            System.out.println("6. Borrow a Book");
            System.out.println("7. Return a Book");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Books:");
                    library.displayBooks();
                    break;

                case 2:
                    System.out.println("\nBorrowed Books:");
                    library.displayBorrowedBooks();
                    break;

                case 3:
                    System.out.println("\nEnter book title: ");
                    String title = sc.nextLine();
                    System.out.println("Enter book author: ");
                    String author = sc.nextLine();
                    System.out.println("Enter book ISBN: ");
                    String ISBN = sc.nextLine();
                    Book newBook = new Book(title, author, ISBN);
                    library.addBook(newBook);
                    System.out.println("Book added successfully.");
                    break;

                case 4:
                    System.out.println("\nEnter book ISBN to remove: ");
                    ISBN = sc.nextLine();
                    library.removeBook(ISBN);
                    System.out.println("Book removed successfully.");
                    break;

                case 5:
                    System.out.println("\nEnter user name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter user ID: ");
                    String userID = sc.nextLine();
                    User newUser = new User(name, userID);
                    library.registerUser(newUser);
                    System.out.println("User registered successfully.");
                    break;

                case 6:
                    System.out.println("\nEnter book ISBN to borrow: ");
                    ISBN = sc.nextLine();
                    System.out.println("Enter user ID: ");
                    userID = sc.nextLine();
                    if (library.borrowBook(ISBN, userID)) {
                        System.out.println("Book borrowed successfully.");
                    }
                    break;

                case 7:
                    System.out.println("\nEnter book ISBN to return: ");
                    ISBN = sc.nextLine();
                    if (library.returnBook(ISBN)) {
                        System.out.println("Book returned successfully.");
                    }
                    break;

                case 8:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
