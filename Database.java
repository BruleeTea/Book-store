import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    private Scanner myObj;
    Connection connection;
    public  Database (Connection c) {
        myObj = new Scanner(System.in);
        connection=c;


    }
    //Will All of display customer's personal info
    //TODO display UserInfo
    public void UserInfo(){

    }
    //changes UserInfo
    //TODO changes UserInfo
    public void ChangeInfo() {
    }

    public void ViewCart(int cart) {

        if(cart==0){
            System.out.println("Nothing in cart.");
        }
        else {
            double sum=UpdateCart(cart);//Updates cart total
            try{
                Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                System.out.println("Cart: \n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                ResultSet resultSet = statement.executeQuery("select title, price from book natural join cartitem where cart_id= '"+cart+"'");
                while(resultSet.next()){
                    System.out.printf("  $"+resultSet.getString("price")+"\t"+resultSet.getString("title")+ "\n");
                    }
                System.out.println("= $"+sum);
            }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
        }

    }

    /* inserts a new cart
    *  @return cart ID
    */
    public int NewCart() {
        int cartID;
        try{
            PreparedStatement statement=connection.prepareStatement("insert into cart values(default,'0')",Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            cartID=resultSet.getInt(1);


            return cartID;

        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }

        return 0;
    }

    /* Adds a book into a cart
     * @param book is the book title
     * @param cartID is the cartID
     * @return cart ID
     */
    public void AddToCart(String book, Integer cartID) {
        try{
            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select ISBN from book where title = '"+book+"'");
            while(resultSet.next()){
                //insert into CartItem values('ID','ISBN','Cart_ID','quantity');
                PreparedStatement s=connection.prepareStatement("insert into CartItem values(default,'"+resultSet.getString("isbn") + "', "
                                +"'"+cartID+"', '1')");
                s.executeUpdate();
            }

        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);

        }

    }

    /* Updates cart total
     * @return cart total
     */
    public double  UpdateCart(int cart){
        double sum=0;
        try{
            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select cart_id,sum(price) from book natural join cartitem where cart_id='"+ cart+ "' group by cart_id;");
            while(resultSet.next()) {
                sum=resultSet.getInt("sum");
                PreparedStatement s = connection.prepareStatement("update cart set total=' " + sum+"' where id= '" + cart + "'");
                s.executeUpdate();
            }

        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);

        }
        return sum;
    }
    /* Displays Book info, title,author, publisher,ISBN,year & price
     * @param name is the book title
     * @return cart total
     */
    public void ViewBook(String name) {
        name = "'" + name + "'";
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select * , Auth_Name from book, author where title = " + name + " and author=ID");
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Nothing found. :(");
            }
            while (resultSet.next()) {
                System.out.printf("Title: " + resultSet.getString("title") + "\n" +
                        "Author: " + resultSet.getString("Auth_Name") + "\n" +
                        "Price: " + resultSet.getString("price") + "\n" +
                        "ISBN: " + resultSet.getString("ISBN") + "\n" +
                        "Publisher: " + resultSet.getString("Pub_Name") + "\n" +
                        "Year: " + resultSet.getString("Year") + "\n");
            }
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);

        }
    }
    /* Searches for book(s)
     * @param c is the candidate key
     * @param key is the search key
     * @return cart total
     */
    public ArrayList<String> FindBook(String c, String key){
        ArrayList<String> books = new ArrayList<>();
        ResultSet resultSet;
        key = "'" + key + "'";
        int count=1;
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(c.equalsIgnoreCase("Author")){
                resultSet = statement.executeQuery("select title from book,author where author=id and auth_name ~* "+key);
            }
            else {
                resultSet = statement.executeQuery("select * from book where " + c + " ~* " + key);
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("Nothing found. :(");
                }
            }
            while (resultSet.next()) {
                books.add(resultSet.getString("title"));
                System.out.printf("\t"+ count++ +". " + resultSet.getString("title")+ "\n");

            }
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
            System.out.println("Nothing found.");
        }
        return books;
    }
    /* Login in customer or owner
     * @param c is the candidate key
     * @param key is the search key
     * @return cus_ID
     */
    public int logon(){
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //It will keep asking for a login and password until an account is found
            while (true) {
                System.out.print("login:");
                String login = myObj.nextLine();
                System.out.print("password:");
                String pass = myObj.nextLine();
                login = "'" + login + "'";
                pass = "'" + pass + "'";
                ResultSet resultSet = statement.executeQuery("select name,cus_ID from customer where login = " + login + "and password = " + pass);
                //Checks if there is a customer with that login and password
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("Incorrect login or password try again.");
                }
                while (resultSet.next()) {
                    System.out.printf("Welcome Back, " + resultSet.getString("name") + "!\n");
                    return resultSet.getInt("cus_ID");
                }
            }
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }



        return 0;}

    /* Checkouts customers books
     * @param cust_id is customer's id
     */
    public void checkout(int cust_id, int cart_id,String address){
        ViewCart(cart_id);
        address = "'" + address + "'";
        try {

            PreparedStatement statement=connection.prepareStatement("insert into Orders values(default,'"+cart_id+ "','"+ cust_id+"', default," +address+",'PROCESSING')",Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            System.out.printf("Your order number and tracking number is: "+resultSet.getInt("track")+"\n");

        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }

    }

    /* Displays tracking information
     * @param tracknum is tracking number
     */
    public  void track(int tracknum){
        try{
            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select track,ordered,address,status from orders where track='"+tracknum+"'");
            System.out.println("Tracking order.................");
            while(resultSet.next()){
                System.out.printf("Tracking number: "+resultSet.getString("track")+
                        "\nShipped to: " + resultSet.getString("address")+
                        "\nOrdered on: "+resultSet.getString("ordered") +
                        "\nStatus: "+resultSet.getString("status")+"\n");
            }

        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);

        }
    }

    /* Displays book sales information by title and author
     */
    public  void view_sales(){

        try{

            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            Statement s=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //Books with the most sold copies
            ResultSet resultSet = statement.executeQuery("select * from book_sales");
            //Most sold author
            ResultSet rs = s.executeQuery("select Auth_Name, count(Auth_Name)* copies_sold as books_sold from book_sales group by Auth_Name,copies_sold having count(Auth_Name)>0 order by books_sold desc;");
            System.out.println("\tBook sales by title");
            System.out.printf("%-30s %-30s %-30s %-30s %n","Title","Author","Publisher","Copies sold");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            while(resultSet.next()){
                System.out.printf("%-30s %-30s %-30s %-30s %n",resultSet.getString("title")
                        ,resultSet.getString("auth_name")
                        ,resultSet.getString("pub_name")
                        ,resultSet.getString("copies_sold")
                );
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\tBook sales by author");
            System.out.printf("%-30s %-10s %n","Author","Books ","sold");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            while(rs.next()){
                System.out.printf("%-30s %-10s %n",rs.getString("auth_name"),rs.getString("books_sold"));
            }

        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
    }
    public  int getauthor_ID(){
        int auth_ID=0;
        try{

            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select nextval('author_id_seq')");
            while (resultSet.next()) {
                auth_ID = resultSet.getInt("nextval");
            }
        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);

        }

     return auth_ID;
    }
    public void addNewBook(long isbn, String pub_name, String auth_name, double price, String title ,int available,int year ){
        try {
            int auth_ID=getauthor_ID()+1;
            auth_name = "'" + auth_name + "'";
            pub_name = "'" + pub_name + "'";
            title = "'" + title + "'";

                PreparedStatement addBook=connection.prepareStatement("insert into addBook values('"+isbn+"',"+title+",'"+available
                        +"',' "+price+"','"+year+"',"+ auth_name+",'"+auth_ID+"',"+pub_name+")");
                addBook.executeUpdate();
                System.out.println("New book added.");

        }catch (Exception sqle) {
                System.out.println("Exception: " + sqle);

            }
        }

    }

