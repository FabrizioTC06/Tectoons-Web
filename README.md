# 🎨 Tectoons - Art Management and E-Commerce Web Platform

> **Comprehensive system developed with Spring Boot for the first animation club in Trujillo.**

**Tectoons** is a web platform designed to centralize the broadcasting, management, and commercialization of digital artworks. The project solves the lack of technological infrastructure for artistic collectives by providing professional tools for portfolio exhibition and product sales.

---

## 🚀 System Features

The system implements **Cumulative Role-Based Access Control (RBAC)** and robust security, dividing functionality into key modules:

### 👤 Public and User Module
* **Dynamic Portfolio:** Artwork display with category filtering and smart search.
* **Virtual Store:** Product catalog with a temporary shopping cart and receipt generation.
* **Interaction:** "Likes" system, artist following, and profile management.
* **Artist Application:** Workflow for standard users to apply to become verified creators.

### 🎨 Artist Dashboard (CMS)
* **Artwork Management (CRUD):** Upload and manage illustrations for the portfolio.
* **Product Management (CRUD):** Publish items for sale in the store.
* **Blog/Posts:** Create informative content and updates.
* **Artistic Profile:** Customize biography and social media links.

### 🛡️ Administrative Dashboard
* **Role Management:** Approve or reject artist applications.
* **Supervision:** Full control over users, content, and platform workflow.
* **View Modes:** Ability to toggle between User, Artist, and Admin views.

---

## 🛠️ Tech Stack

The project follows an **MVC (Model-View-Controller)** architecture and uses modern technologies from the Java ecosystem:

| Layer | Technologies |
| :--- | :--- |
| **Backend** | **Java 17**, Spring Boot 3, Spring Web, Spring Validator |
| **Security** | **Spring Security** (Auth & RBAC), BCrypt (Hashing) |
| **Data** | **MySQL**, Spring Data JPA, Hibernate (ORM) |
| **Frontend** | **Thymeleaf**, Bootstrap 5, HTML5/CSS3, JavaScript |
| **Tools** | Maven, Git/GitHub, Visual Studio Code, Postman |

---

## 📂 Project Architecture

The system is structured under modular and scalable design principles:

* **Controller:** Handles HTTP routing, sessions, and validations.
* **Service:** Business logic and cumulative role rules.
* **Repository:** Data abstraction using JPA.
* **Security:** Configuration of filters, CSRF, and URL-based access control.

---

## 🔧 Installation and Deployment

Follow these steps to run the project locally:

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/FabrizioTC06/Tectoons-Web.git](https://github.com/FabrizioTC06/Tectoons-Web.git)
    ```

2.  **Database:**
    * Create a MySQL database named `tectoons_db`.
    * *Note: Hibernate will generate the tables automatically (`ddl-auto=update`).*

3.  **Configuration:**
    * Update your credentials in `src/main/resources/application.properties`:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/tectoons_db
        spring.datasource.username=YOUR_USERNAME
        spring.datasource.password=YOUR_PASSWORD
        ```

4.  **Execution:**
    * From the terminal: `./mvnw spring-boot:run`
    * Or from your IDE by running `TectoonswebApplication.java`.

5.  **Access:**
    * Browser: `http://localhost:9090`

---
*Developed by **Fabrizio Teodor Celis** - 2025*
*Academic Project - Computer and Systems Engineering, UTP*