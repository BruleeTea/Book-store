
--displays how many copies a book has been sold
create view book_sales as
  select book.isbn,title,auth_name, pub_name, sum(quantity) as copies_sold from orders
  inner join cartitem on cartitem.cart_id = orders.id
  join book on book.isbn = cartitem.isbn
  join author on book.author = author.id
  group by book.isbn, author.auth_name;


  CREATE OR REPLACE VIEW addBook AS (
    SELECT
      book.isbn,
      book.title,
      book.available,
      book.price,
      book.year,
      author.Auth_Name,
      author.id,
      publisher.name
    FROM book
      JOIN author ON book.author = author.id
      JOIN publisher ON book.pub_name = publisher.name);

create rule add as on insert to addBook do instead(
  insert into author values(nextval('author_id_seq'),new.Auth_Name);
  insert into publisher values(new.name);
  insert into book values(new.isbn,
    (select name from publisher where name =new.name),
    (select id from author where id = new.id),
    new.price,new.title,new.year,new.available);

);
