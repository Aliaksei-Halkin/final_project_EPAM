The educational project for the UpskillLab course.
Author: Aliaksei Halkin, contact: pochtax3@gmail.com
Theme: the restaurant.

The project represents the website of restaurant "Flying Dutchman".
The website provides an opportunity for guests to get information about dishes in restaurant and order them.
Guests also can register as customer or login as customer/manager/administrator/cook.
Customers can make an order, which can be approved or rejected by a manager of the restaurant. 
The administrator maintains the website's catalog and user's accounts. Also, an administrator,a manager and a cook are 
people, they can oder dishes too. The app has two languages-RU/EN. The main actions are accompanied by messages(RU/EN). 
Include validation of data.

**_UNAUTHORIZED VISITOR_**
The unauthorized visitor to the website is considered a guest and can change the language of the site, visit to menu 
page, search product catalogs, register, and then log in if you already have an account.Common functionality is
provided for all roles.
_Allowed actions:_
Visit the home page
View page of all dishes
Search a dish by keyword
Put dish to the cart
View the personal cart
Remove the dish from the cart
Follow to the home page
Register
Login
Change website language
Follow the link to my github

**_CUSTOMER_**
The authorized user with minimal privileges - a customer. Customer can do everything, that is allowed to the guest plus
some additional functionality.
_Allowed actions:_
*Functionality of unauthorized visitor without Register
Make an order
View personal orders history
Remove orders with status "NEW"
View profile information

**_MANAGER_**
The manager is an authorized user - employee of restaurant. He is responsible for order management and can view a 
list of not closed orders of users, can approve, delete orders or close order after cooking and handing over to
the client.If the order status is APPROVED, the dish is being prepared and the manager is just waiting for preparation.
Also has all privileges of a customer and a guest.
_Allowed actions:_
*Functionality of Customer
View all unclosed orders
Close the order if status of order NEW or COOKED
Approve the order if status of order NEW 

**_COOK_**
The chef sees  all unclosed orders.If the order is approved, the chef can prepare the dish and then change the
status to COOKED.
_Allowed actions:_
*Functionality of Customer
-View all unclosed orders
-Change the  status of order "Approved" to "Cooked" if the dish cooked

**_ADMINISTRATOR_**
_Allowed actions:_
*Functionality of Customer
Manage products:
-view all products
-delete the product;
-edit the product(change all field of product, except ID of course);
-add new product;
Manage users:
-view all users;
-quick change role of user;
-delete the user(the user has active status = false);
-add new user(it's registration)

