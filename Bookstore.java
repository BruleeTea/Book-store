import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bookstore {
    public static void main(String args[]) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "3219");) {
            Database db = new Database(connection);
            MainMenu(db);
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
    }
    //
    /* Displays main menu
     * @param db is the session with bookstore database
     */
    public static void MainMenu(Database db) {
        Scanner option = new Scanner(System.in);
        boolean error = true;
        //TODO change to 0
        int cart =0;
        //0 = no one has login, any other number the customer ID
        Integer User = 0;
        System.out.println("=====================================================");
        System.out.println("===                                              ====");
        System.out.println("===          WELCOME TO LOOK INNA BOOK           ====");
        System.out.println("===                                              ====");
        System.out.println("=====================================================");

        //It will keep looping until user selects an option
        while (error) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\tMain Menu");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Choose one of the following: \nLeave Store(0),Account Info(1), Search for Book(2), View Cart(3), Checkout(4),Track Order(5), \nManger Settings(6)");

            try {
                Integer UserInput = option.nextInt();
                switch (UserInput) {
                    case 0://Leave store
                        System.out.println("Thanks for shopping with Look Inna Book, come again soon! :D");
                        error = false;
                        break;
                    case 1://Account Info
                        User = AccountMenu(User, db, option);
                        break;
                        //ToDO fix year search
                    case 2://Search for a Book
                        cart=SearchMenu(db, option,cart);
                        break;
                    case 3://View Cart
                        db.ViewCart(cart);
                        break;
                    case 4://Checkout
                        if(User==0){
                            System.out.println("Please login to checkout.");
                            User= db.logon();
                            System.out.println("Please enter shipping address");
                            option.nextLine();
                            String address =option.nextLine();
                            db.checkout(User,cart,address);
                        }
                        break;
                    case 5://Track Order
                        System.out.println("Please enter in track number:");
                        UserInput = option.nextInt();
                        db.track(UserInput);
                        break;
                    case 6://Manger settings
                        System.out.println("Please enter password:");
                        option.nextLine();
                        String password =option.nextLine();
                        if(password.equalsIgnoreCase("0000"))
                            OwnerMenu(db, option);
                        System.out.println("Wrong password");
                        break;
                    default:
                        System.out.println(UserInput + " is not an option.");
                }
            } catch (Exception e) {
                System.out.println("Please enter an integer.");
                option.next();
            }

        }
    }


    /* Displays search menu and adds selected book into a cart
     * @param db is the session with bookstore database
     * @param option is a scanner use to get input from user
     * @return cart id
     */
    public static int SearchMenu(Database db, Scanner option, int cart) {
        ArrayList<String> books = new ArrayList<>();
        books.clear();
        //TODO change to 0
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\tSearch Menu");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Search by:\n Author(1), Title(2), ISBN(3),Publisher(4),Year(5)");
        try {
            Integer UserInput = option.nextInt();
            option.nextLine();//wait
            System.out.println("Enter in Search Key: ");
            String key = option.nextLine();
            System.out.println("Searching............");
            switch (UserInput) {
                case 1:
                    books = db.FindBook("Author", key);
                    break;
                case 2:
                    books = db.FindBook("Title", key);
                    break;
                case 3:
                    books = db.FindBook("ISBN", key);
                    break;
                case 4:
                    books = db.FindBook("Pub_Name", key);
                    break;
                case 5:
                    books = db.FindBook("Year", key);
                    break;
                default:
                    System.out.println(UserInput + " is not an option.");
            }
            if (!books.isEmpty()) {
                System.out.println("Select a Book or search again(0):");
                UserInput = option.nextInt();
                if( !(0<= UserInput && UserInput <= books.size())){
                    throw new IllegalArgumentException("");
                }
                switch (UserInput) {
                    case 0:
                        SearchMenu(db,option,cart);
                        break;
                    default:
                        db.ViewBook(books.get(UserInput - 1));
                        option.nextLine();
                        System.out.println("Add book in cart?(y/n)");
                        key = option.nextLine();
                        if(key.equalsIgnoreCase("y")){
                            if(cart==0) {
                                cart=db.NewCart();
                            }
                            db.AddToCart(books.get(UserInput - 1),cart);
                            System.out.println(books.get(UserInput - 1)+" added to cart.");

                        }

                }
            }
            } catch(Exception e){
                System.out.println("That was not an option.");
            }


    return cart;
    }


    /* Displays account menu. This will login a user or register a new user.
     * customer personal or order history info can be display or changed here
     * @param type is the user type
     * @param db is the session with bookstore database
     * @param option is a scanner use to get input from user
     * @return account type
     */
    public static int AccountMenu(int type, Database db, Scanner option) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\tAccount Menu");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        boolean error = true;
        if (type == 0) {
            System.out.println("You're not login, would you like to login(1) or make an account(2)");
            while (error) {
                try {
                    Integer UserInput = option.nextInt();
                    switch (UserInput) {
                        case 1://login

                            type = db.logon();
                            error = false;
                            break;
                        case 2://make an account
                            //TODO Add new customer acc
                            error = false;
                            break;

                        default://Random num was read
                            System.out.println(UserInput + " is not an option.");
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a integer.");
                    option.next();
                }
            }
        }
        error = true;
        while (error) {
            try {
                //TODO finish this
                System.out.println("Account Menu");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Choose one of the following:\nReturn to main menu(0),View Orders(1),Change Account Info(2), View Account Info(3)");
                Integer UserInput = option.nextInt();
                switch (UserInput) {
                    case 0 ://Return to main menu
                        return type;
                    case 1://View Orders
                        break;
                    case 2://Change Account Info
                        break;
                    case 3://View Account Info
                        break;
                    default://Random num was read
                        System.out.println(UserInput + " is not an option.");
                }

            } catch (Exception e) {
                System.out.println("Please enter a integer.");
                option.next();
            }
        }


        return type;
    }
    /* Displays owner/manger menu.
     * Viewing sales reports and add new books will be done in here
     * @param db is the session with bookstore database
     * @param option is a scanner use to get input from user
     */
    public static void OwnerMenu(Database db,Scanner option){
        boolean error = true;
        while (error) {
            try {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\tOwner Menu");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Choose one of the following:\nReturn to main menu(0),Add book(1), View sales(2)");
                Integer UserInput = option.nextInt();
                switch (UserInput) {
                    case 0 ://Return to main menu
                        error=false;
                        break;
                    //TODO Owner Menu
                    case 1://Add book
                        System.out.println("Enter ISNB: ");
                        long isnb=option.nextLong();
                        System.out.println("year: ");
                        Integer year=option.nextInt();
                        System.out.println("price: ");
                        double price=option.nextDouble();
                        System.out.println("quantity: ");
                        Integer quantity=option.nextInt();
                        option.nextLine();
                        System.out.println("Book title: ");
                        String title = option.nextLine();
                        System.out.println("Author Name: ");
                        String author = option.nextLine();
                        System.out.println("publisher: ");
                        String publisher = option.nextLine();
                        db.addNewBook(isnb,publisher,author,price,title,quantity,year);
                        break;
                    case 2://View sales
                        System.out.println("\tSales Reports");
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
                        db.view_sales();
                        break;
                    default://Random num was read
                        System.out.println(UserInput + " is not an option.");
                }//9781442426702 2014 20.99 To All the Boys Ive Loved Before Jenny Han Simon & Schuster Books for Young Readers

            } catch (Exception e) {
                System.out.println("Error: "+e);
                option.next();
            }
        }
    }
}
