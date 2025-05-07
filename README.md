# JSF User Management Application

This project demonstrates a **web application** built with **Jakarta Server Faces (JSF)** and **PrimeFaces** for user management. The app will handle **user authentication**, **user management**, and **data validation** using **Java EE / Jakarta EE**, **JPA / Hibernate**, and **MySQL**. The goal of this demo is to showcase basic user operations such as authentication, user management, and form validations.

## Features

### **1. User Authentication:**
The application includes a login page with the following logic:

- If the user has the **"admin"** role, they are redirected to the **user management** page.
- If the user has the **"user"** role, they are redirected to a page displaying their **detailed information**.
- If the user does not have a valid role, an **error page** is displayed.

### **2. User Management:**
The **user management** page includes:

- A **DataTable** displaying the list of users.
- The ability to **modify** or **delete** a user.
- A button called **"addUser"** that redirects to a page for adding a new user.

### **3. Add User Page:**
The **add user** page allows the user to enter the following information:

- **Name**
- **Surname**
- **Date of Birth**
- **Account Balance** (in **MAD** - Moroccan Dirham)
- **Address** (format: Street Number - Street Name)
- **City** and **Postal Code** (the postal code starts with the first digit)
- **Country**

### **4. Custom Converters:**
The following **custom converters** will be used in the project:

- **Date of Birth:** Displayed in the format **DD/MM/YYYY**.
- **Account Balance:** Displayed with the unit **MAD** (Moroccan Dirham).
- **Address:** Formatted to extract and display the following parts:
    - Street Number
    - Street Name
    - City
    - Postal Code

### **5. Data Validation:**
Validators will be added to ensure that:

- The **date of birth** follows the correct format.
- The **account balance** is a **positive numeric value**.
- The **address fields** are correctly structured.

## Technologies Used:

- **JSF (Jakarta Server Faces)** for web interface management.
- **PrimeFaces** for UI components (DataTable, form controls, etc.).
- **Java EE / Jakarta EE** for the backend user management logic.
- **JPA / Hibernate** for **database interaction** and object-relational mapping (ORM).
- **Maven** for managing project dependencies.
