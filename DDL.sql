create table publisher
  (Name varchar(100),
  Bank_Account numeric(10),
  Address varchar(30),
  Phone_num numeric(10),
  E_mail varchar(30),
  primary key (Name)
  );

create table customer
  (cus_ID smallserial,
  Name varchar(50),
  login varchar(60) unique not null,
  password varchar(10),
  post_code varchar(6),
  street varchar(20),
  building_num varchar(10),
  city varchar(20),
  phone_num numeric(10,0),
  e_mail varchar(30),
  primary key (cus_ID)
  );

create table author (
  ID smallserial,
  Auth_Name varchar(100),
  primary key (ID)
);

create table Book (
  ISBN varchar(20),
  Pub_Name varchar(100),
  author smallserial,
  Price numeric(6,2) check (Price > 0),
  Title varchar(100),
  Year numeric(4,0) check (year < 2023),
  available numeric check (available > 0),
  primary key (ISBN),
  foreign key (Pub_Name) references publisher
  on delete cascade,
  foreign key (author) references author
  on delete cascade
);

create table Cart (
  ID smallserial,
  Total numeric(6,2),
  primary key (ID)
);
create table Orders
  (track smallserial,
  ID smallserial,
  cus_ID smallserial,
  ordered  date default current_date, --uses todays date
  address varchar(100) not null,
  status varchar(20) check (status = 'PROCESSING' or status = 'PAID' or status = 'SHIPPED'),
  primary key (track),
  foreign key (ID) references Cart
  on delete cascade,
  foreign key (cus_ID) references customer
  on delete cascade
);

create table CartItem(
  Item_ID smallserial,
  ISBN varchar(20),
  Cart_ID smallserial,
  quantity numeric(100),
  primary key (Item_ID),
  foreign key (ISBN) references Book
  on delete cascade,
  foreign key (Cart_ID) references Cart
  on delete cascade
);
