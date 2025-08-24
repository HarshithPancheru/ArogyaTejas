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
| Database   | MySQL                                                                  |
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
- **A running MySQL instance**

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
```

### 2) Frontend Setup (React)
```bash
# Clone the frontend repository
git clone <your-frontend-repo-url>
cd <frontend-folder>

# Install dependencies
npm install

# Create a .env file in the root and add the API URL
echo "VITE_API_BASE_URL=http://localhost:5000/api" > .env

# Start the development server
npm run dev

```

## 🛣️ API Endpoints – Brief Overview

Here’s a high-level look at the core API endpoints grouped by role:

### 🔑 Authentication
| Method | Endpoint                     | Description                        |
|--------|-------------------------------|------------------------------------|
| POST   | `/api/auth/login`            | Authenticates a user & returns JWT |
| POST   | `/api/auth/patient/register` | Registers a new patient account    |
| POST   | `/api/auth/doctor/register`  | (Admin) Registers a new doctor     |

### 👤 Patient
| Method | Endpoint                          | Description                       |
|--------|-----------------------------------|-----------------------------------|
| GET    | `/api/patient/dashboard`          | Fetches patient’s dashboard data  |
| POST   | `/api/patient/appointment`        | Books a new appointment           |
| DELETE | `/api/cancel?appointmentId={id}`  | Cancels a scheduled appointment   |

### 🧑‍⚕️ Doctor
| Method | Endpoint                                 | Description                           |
|--------|------------------------------------------|---------------------------------------|
| GET    | `/api/doctor/dashboard`                  | Fetches doctor’s dashboard data       |
| PUT    | `/api/doctor/appointment/{id}/status`    | Updates appointment status (Accept/Reject/Finish) |

### ⚙️ Admin
| Method | Endpoint                  | Description                           |
|--------|---------------------------|---------------------------------------|
| GET    | `/api/admin/dashboard`    | Fetches admin dashboard data          |
| POST   | `/api/auth/doctor/register` | Creates doctor accounts (Admin only)  |
| CRUD   | `/api/admin/appointment`  | Manage (Create, Update, Delete) appointments |

---
