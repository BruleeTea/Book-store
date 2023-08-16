# Conceptual Design

![ER Diagram](https://github.com/BruleeTea/Book-store/assets/142434143/c8a52b2f-1af3-438c-9e1e-be3d712a970d)

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
![Database Schema Diagram](https://github.com/BruleeTea/Book-store/assets/142434143/1f69d620-97cf-462a-9006-d48893c566ee)





