# ArogyaTejas – Appointment Booking System 🩺

ArogyaTejas is a modern, full-stack web application designed to streamline booking and managing medical appointments. Built with a Spring Boot backend and a sleek React + TypeScript frontend, the platform serves three roles—**Admins**, **Doctors**, and **Patients**—each with a dedicated, intuitive dashboard.

---

## ✨ Features

### 👤 Patient Portal
- **Secure Registration & Login** – Easy and safe authentication.
- **Step-by-Step Booking** – Choose specialty → doctor → date.
- **Appointment Dashboard** – Clean table of upcoming/past visits.
- **Cancel Appointments** – One-click cancellation.

### 🧑‍⚕️ Doctor’s Dashboard
- **Secure Login** – Role-based access.
- **Appointment Management** – View assigned appointments by date.
- **Status Updates** – Accept / Reject / Mark as Finished.
- **Patient Information** – Quick view of patient name & contact.

### ⚙️ Admin’s Control Panel [Not yet]
- **Secure Login** – Full administrative access.
- **Comprehensive Dashboard** – Manage all appointments platform-wide.
- **Doctor Management** – Create doctors with specialization & availability.
- **Full CRUD on Appointments** – Update status or delete when needed.
- **Search & Filter** – Find by patient or doctor name.

---

## 🛠️ Tech Stack

| Category   | Technology                                                                 |
|------------|-----------------------------------------------------------------------------|
| Frontend   | React, TypeScript, (Vite or CRA), Axios                                    |
| Backend    | Spring Boot, Spring Web, Spring Security, Spring Data JPA                  |
| Database   | PostgreSQL                                                                  |
| API Client | REST (JSON over HTTP), Axios (frontend), Spring REST Controllers (backend) |

> Replace or extend the above with your project’s exact libs as needed.

---

## 🚀 Getting Started

Follow these steps to run the project locally for development and testing.

### Prerequisites
- **Java 17+**
- **Maven 3.6+**
- **Node.js 18+**
- **npm 9+**
- **A running PostgreSQL instance**

---

### 1) Backend Setup (Spring Boot)

```bash
# Clone the backend repository
git clone <your-backend-repo-url>
cd <backend-folder>

# Update application.properties with your database credentials
# File: src/main/resources/application.properties
# Example:
# spring.datasource.url=jdbc:postgresql://localhost:5432/arogyatejas
# spring.datasource.username=postgres
# spring.datasource.password=your_password
# spring.jpa.hibernate.ddl-auto=update
# server.port=5000

# Build and run the project
mvn clean install
mvn spring-boot:run
