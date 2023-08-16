# Conceptual Design

![ER Diagram](https://github.com/BruleeTea/Book-store/assets/142434143/7d474dcf-385f-4111-82fa-3eb0d27b7ec0)

# Reduction to Relation Schemas

Publisher (Name , Bank_Account ,Address ,Phone_num , E-mail)

published_by(Name, ISBN)

Author (ID , Name) 

written_by(ID, ISBN)

Book (ISBN, Pub_Name , authorID , price , title, year, available)

Customer (cus_ID, name, login, password, post_code, street, building_num, city , phone_num, e_mail)

Cart (ID , Total)

CartItem (ID ,ISBN, Cart_ID , quantity)

Orders (track, ID, cus_ID ,ordered, date, status)

checkout(ID, Track)

Order_history(Track, cus_ID) 

# Normalization of Relation Schemas
Non- trivial Functional Dependencies 

Book:

	{ISBN → Pub_Name , authorID , price , title, year, available}
 
Publisher:

	{Name →   Bank_Account ,Address ,Phone_num , E-mail}
 
Author:

	{ID →  Name}
Written_by: 

	No functional dependencies
 
Customer:

	{cus_ID → name, login, password, post_code, street, building_num, city , phone_num, e_mail}
 
Cart:

	{ID → Total}
 
 CartItem:
 
 {ID  → ISBN, Cart_ID , quantity}
  

Orders:

	{track → ID, cus_ID ,ordered, date, status}
 
published_by: 

	No functional dependencies
 
checkout: 

	No functional dependencies
 
Order_history: 

	No functional dependencies

Normalization
In book ISBN is the candidate key, with F+ able to divided into 2 rules (rule ⍺ → β ). 
 1. That ⍺ has the candidate key, ISBN, then β would contain the whole schema attributes 
 2. ⍺ doesn’t have the candidate key then β = ⍺.
    
Since the non-trivial functional dependency contains the candidate key only, then the relation is in BCNF. The relations with  no functional dependencies only have trivial functional dependencies which means that they are already in BCNF. 

# Database Schema Diagram 

![Database Schema Diagram](https://github.com/BruleeTea/Book-store/assets/142434143/9df1eff8-bbb8-4974-a23d-788bc3f5c5ef)


# Implementation
This project was coded in Java, which is not complete. The database was implemented in Postgres. The application interface is a command line interface. I have an object called database which does all the queries and the bookstore does all the menu displays and gets user input. 




