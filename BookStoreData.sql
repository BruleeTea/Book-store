
--insert into publisher values('Name','Bank_Account','Address','Phone_num','E-mail');
insert into publisher values ('Scholastic Press', '1001100346','175 Hillmount Road, Markham,ON','6463305288','custserve@scholastic.ca');
insert into publisher values ('Harper Perennial Modern Classics', '1461111341','22 Adelaide St W Toronto,ON','8443275757','HCOrder@harpercollins.com');
insert into publisher values('Scribner','1000000001','153 5th Ave, NYC','1234567890','Public@SimonandSchuster.com');
insert into publisher values('Dutton Books','1001001301','New York, New York, U.S','0987654321','dutttonHelp@dutton.com');
insert into publisher values('Houghton Mifflin','1000111111','Boston, Massachusetts, U.S','5145904234','Houghton@Mifflin.com');
insert into publisher values('Little, Brown','1000074504','Boston, Massachusetts, U.S','2345612956','Little@Brown.com');
--insert into author values('ID','Name','Pub_Name');
insert into author values (default, 'Suzanne Collins');
insert into author values (default, 'Harper Lee');
insert into author values(default,'F. Scott Fitzgerald');
insert into author values(default,'John Green');
insert into author values(default,'J.R.R. Tolkien');
insert into author values(default,'J.D. Salinger');
insert into author values(default,'James Patterson');

--insert into book values('ISBN','Pub_Name','authorID','price','title','year',available);
insert into book values('9780439023481','Scholastic Press', '1','30','The Hunger Games','2008','10');
insert into book values('9780061120084','Harper Perennial Modern Classics', '2','10','To Kill a Mockingbird','1960','10');
insert into book values('9780743273565','Scribner','3','10','The Great Gatsby','1925','10');
insert into book values('9780525478812','Dutton Books','4','25','The Fault in Our Stars','2012','10');
insert into book values('9780618260300','Houghton Mifflin','5','15','The Hobbit or There and Back Again','1937','10');
insert into book values('9780316769174','Little, Brown','6','15','The Catcher in the Rye','1951','10');
insert into book values('9780439023498','Scholastic Press','1','30','Catching Fire','2009','10');
insert into book values('9780439023511','Scholastic Press','1','30','Mockingjay','2010','10');
insert into book values('9780316018784','Little, Brown','7','20','I, Alex Cross','2009','10');
insert into book values('9780316101844','Little, Brown','7','23','Nevermore','2012','10');
insert into book values('9780316036177','Little, Brown','7','11.99','Cross Fire','2010','10');


--insert into customer values('cus_ID','name','login','password','post_code','street','building_num','city','phone_num','e_mail');
insert into customer values('1','Amy Penuela','test','1234','g4c6w5','123 street','69','ottawa','613123456','ILuvDatabases@gmail.com');
insert into customer values(default,'Jin Kim','rj','1234','g4c6w5','777 road','0','ottawa','61323422','JinKim@hotmail.com');


  --insert into cart values('ID','ISBN','Total');
  insert into cart values(default,'30');
  insert into cart values(default,'65');
  insert into cart values(default,'30');
--insert into CartItem values('ID','ISBN','Cart_ID','quantity');
insert into CartItem values('1','9780439023481','1','1');
insert into CartItem values(default,'9780439023498','2','1');
insert into CartItem values(default,'9780316018784','2','1');
insert into CartItem values(default,'9780316769174','2','1');
insert into CartItem values(default,'9780439023481','3','2');
--insert into CartItem values(default,'9780316101844','6','1');


--insert into Orders values('track','ID','cus_ID','ordered','date','address','status');
insert into Orders values(default,'1','1','2020-04-9','69-123 street ottawa,ON Canada','SHIPPED');
insert into Orders values(default,'2','1','2021-04-11','69-123 street ottawa,ON Canada','PAID');
insert into Orders values(default,'3','1','2021-04-12','69-123 street ottawa,ON Canada','PROCESSING');
