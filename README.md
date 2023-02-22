# Diplom - Website for tracking personal expenses
# Aleksandra Aleksandrova 12 v class

The main idea of the project is to create a website for tracking personal expenses. It implements user registration, because each user has their own profile with no option to look into others' profile. Each user needs a valid email address to register. User's email is used to confirm the registration, also to send notification emails.
The website has a simple interface. Based on the added expenses, it generates two pie charts: one for the current month and one for the whole year. The charts are interactive and you can hover over the slices to see the exact amount of money spent on each category. The website also has a table with all the expenses on the home page. The user can filter their expenses based on category and period of time.

Important notes on running the project:
- The database is empty when you run the project for the first time. You need to add your own categories in the database. No other data needs to be added.
- The project uses Gmail to send emails. You need 2-step verification enabled and app password generated to use as a password.


Tech stack:
- Java 17
- Spring Boot 3.0.0
- Spring Security 6.0.0
- Spring Data JPA 3.0.0
- Spring ORM 6.0.0
- Tomcat 10.1.1
- Hibernate 6.0.0
- Hibernate Validator 8.0.0
- H2 database 2.1.214
- Thymeleaf 3.1.0
- Mapstruct 1.5.2.Final
- Maven 3.8.6
