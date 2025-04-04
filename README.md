# ROOM

Android Room is a ORM(object relational mapping) library created for SQLite. SQLite is the database management system we use to create databases in Android projects.
Room acts as a layer on top of SQLite.

To use Room library/framework, you need 3 code components. 

i. Entity classes

ii. Dao interface

iii. Database class

**i. Room Entity Classes.**
One entity class is required for each table. We need to create entity classes for each database table and annotate it with **@Entity**. Holding the data is the only purpose of these classes. So, we will create them as Kotlin data classes.
Also the name of the database table is the same name as the class name and if we need a different name, we could give that name as the value of the **tableName** property.

Name of the columns will have same names as the data class’s variable names but if we want different column names we can provide them using **@ColumnInfo**

**Primary Key**
Use the @PrimaryKey on the variable selected as the primary key of the table. If we want the primary key to auto generate, set autoGenerate = true

![image](https://github.com/user-attachments/assets/35b466a6-5f5c-4158-b1d9-d99e70931682)

**ii. Room DAO interface**
We need to create an interface and annotate it with @Dao. DAO stands for “Data Access Object”. This interface is the place where we define functions to deal with the database.

![image](https://github.com/user-attachments/assets/e6fe1918-b4d2-4821-8d77-49b2c397a889)

Function names
Function names are not important. We can give any name as we like but annotating the function with correct annotation is very important.

For instance, we have annotated above “insertBook” function(method) with @Insert. Therefore, room will know that the purpose of that function is to insert data. Room library does not 
understand the function names but room recognizes annotations.

Suspend keyword
“Kotlin coroutines” is the current best practice to manage threads in android development. So, we are going to use them to execute database operations in a background thread. Therefore, we 
need to define these functions as suspending functions but it is not required for query functions because Room library uses its own dispatcher to run queries on a background thread.

SQL Statement
For basic Insert, Update and Delete functions we don’t need to write SQL statements but we need to write a SQL statement for Query functions and for customized Update and Delete functions.

Room Insert functions
All insert functions should annotate with @Insert. There is no need to write a SQL query for insert functions. Return type is optional. Most of the time, we write room insert functions 
without a return type but Room allows us to get the newly inserted row id as a “Long” value.

For example, if the insert function has a single parameter, return type would be the new row id of type Long . On the other hand, if the parameter is an array or a collection, return type 
would be an array or a collection of Long values.

Update and Delete functions
Update functions should annotate with @Update and delete functions should annotate with @Delete. These functions doesn’t require a SQL statement and return types are optional but we can add
an “int” return type. These functions return an “int" value indicating the number of rows that were updated or deleted successfully

3) Room Database Class
We need to create a Room Database class. This class should be an abstract class. Most importantly, this should extend the “RoomDatabase” class. We need to annotate this class with @Database.

![image](https://github.com/user-attachments/assets/29ef0e25-7a3a-407a-ba4b-fc3c18b5deb5)

Then provide the list of entity classes with it and also provide the version number. Database’s version number is very important when we are going to migrate the database. Finally, we need 
to define abstract functions to get Dao interfaces inside the class.

