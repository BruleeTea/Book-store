--The queries that a used in my application

--List all the items and prices of everything in  a cart c1
select title, price from book natural join cartitem where cart_id= '1';

--Finds the sum of all cart items
select cart_id,sum(price) from book natural join cartitem where cart_id= '1'cart_num group by cart_id;

--Displays a book's information
select * , Auth_Name from book, author where title = " + name + " and author=ID;

--Finds book with author Name
select title from book,author where author=id and auth_name ~* 'James';

--Finds book with title
select * from book where title  ~*'The catcher in the rye';

--Finds book with year
select * from book where year ='2009';

--Find book with ISBN
select * from book where isbn ='9780316101844';

--Loging in a register customer
select name,cus_ID from customer where login = " + login + "and password = " + pass";

--Will find order information with givin track num
select track,ordered,address,status from orders where track='"+tracknum+"';

--Displays the amount of copies a book sold with going from greatest to least
select * from book_sales;

--Displays the author with amount of books sold going from greatest to least
select Auth_Name, count(Auth_Name)* copies_sold as books_sold
from book_sales group by Auth_Name,copies_sold
having count(Auth_Name)>0 order by books_sold desc;
