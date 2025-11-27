# 🎓 IFOBIA v1.0
### **I**nformations **o**f **B** Class **I**nformatics **A**ccess

<div align="center">

![IFOBIA Logo](https://github.com/aliaaa909/IFOBIA-v1.0/blob/2536f648d785913fe9661f78a290d893dc41ecb1/logo/LOGO%20UFOBUA.png)

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![NetBeans](https://img.shields.io/badge/NetBeans-IDE-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)](https://netbeans.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)](LICENSE)

**A comprehensive academic information management system designed specifically for Class B Informatics students**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Team](#-team) • [Documentation](#-documentation)

</div>

---

## 📖 About IFOBIA

**IFOBIA** (Informations of B Class Informatics Access) is a Java-based desktop application that streamlines academic information management for Class B Informatics students. Built with a focus on efficiency and user experience, IFOBIA provides a centralized platform for managing schedules, assignments, materials, announcements, and attendance records.

### 🎯 Problem Statement

Based on surveys conducted with students serving as Course Representatives (PJ), most still rely on WhatsApp Groups as the primary medium for disseminating information from lecturers to students. Despite being commonly used, many respondents reported difficulties in organizing and documenting various course information, especially when handling multiple types of information such as assignments, announcements, and attendance simultaneously.

### 💡 Solution

IFOBIA addresses these challenges by providing:
- Centralized information hub for all academic activities
- Automated notification system for deadlines and announcements
- Organized material repository with file upload capabilities
- Efficient attendance tracking and reporting
- Role-based access control for students and course representatives

---

## ✨ Features

### 👥 For All Users (Students & PJ)

| Feature | Description |
|---------|-------------|
| 📅 **Dashboard** | View weekly schedules, upcoming assignments, and latest announcements at a glance |
| 📚 **Learning Materials** | Access and share course materials with file upload support (PDF, PPT, DOCX, images) |
| 📝 **Assignment Tracking** | View assignment details and mark completion status (personal tracking) |
| 📢 **Announcements** | Stay updated with important class announcements |

### 🎓 Exclusive for Course Representatives (PJ)

| Feature | Description |
|---------|-------------|
| 📆 **Schedule Management** | Add, edit, and delete class schedules |
| 📋 **Assignment Management** | Create, update, and remove course assignments |
| 📣 **Announcement Control** | Post and manage class announcements |
| ✅ **Attendance System** | Record student attendance and generate comprehensive reports |
| 📊 **Export Reports** | Export attendance records to PDF/Excel format |

---

## 🛠️ Tech Stack

<div align="center">

| Category | Technology |
|----------|------------|
| **Programming Language** | Java 17+ |
| **GUI Framework** | JavaFX / Swing |
| **Database** | MySQL 8.0+ |
| **IDE** | Apache NetBeans |
| **Architecture** | MVC (Model-View-Controller) |

</div>

---

## 🚀 Installation

### Prerequisites

Before you begin, ensure you have the following installed:
- ☕ [Java JDK 8+](https://www.oracle.com/java/technologies/downloads/)
- 🗄️ [XAMPP (MySQL)](https://www.apachefriends.org/download.html) - For database server
- 🔧 [Apache NetBeans IDE](https://netbeans.apache.org/download/index.html) - Recommended for development

---

### Step 1: Clone the Repository
```bash
git clone https://github.com/aliaaa909/IFOBIA-v1.0.git
cd IFOBIA-v1.0
```

Or download as ZIP from GitHub:
- Go to https://github.com/aliaaa909/IFOBIA-v1.0
- Click **Code** → **Download ZIP**
- Extract the ZIP file

---

### Step 2: Start XAMPP

1. Open **XAMPP Control Panel**
2. Start **Apache** (optional, for phpMyAdmin)
3. Start **MySQL** ✅ (required)
4. Click **Admin** button next to MySQL to open phpMyAdmin

---

### Step 3: Database Setup

#### Option A: Using phpMyAdmin (Recommended)

1. Open phpMyAdmin in browser: `http://localhost/phpmyadmin`

2. Click **New** in left sidebar to create database

3. Database name: `ifobia_db`
   - Collation: `utf8mb4_general_ci`
   - Click **Create**

4. Click **Import** tab

5. Choose file: Browse to `ifobia_db (2).sql` in the project folder

6. Click **Go** at bottom of page

7. Wait for success message: "Import has been successfully finished"

#### Option B: Using MySQL Command Line
```bash
# Create database
mysql -u root -p -e "CREATE DATABASE ifobia_db;"

# Import SQL file
mysql -u root -p ifobia_db < "ifobia_db (2).sql"
```

---

### Step 4: Configure Database Connection

1. Open project in NetBeans

2. Navigate to: `com.ifobia.database_util` → `database_utility.java`

3. Update database credentials (if needed):
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/ifobia_db";
private static final String DB_USER = "root";           // XAMPP default username
private static final String DB_PASSWORD = "";           // XAMPP default password (empty)
```

**Note:** XAMPP default MySQL credentials:
- Username: `root`
- Password: *(empty/blank)*

---

### Step 5: Add MySQL Connector Library

1. In NetBeans, right-click **Libraries** folder

2. Select **Add JAR/Folder**

3. Browse to `mysql-connector-java.jar` in project folder

4. Click **Open**

5. Verify library appears under **Libraries** folder

---

### Step 6: Build and Run

#### Using NetBeans:

1. Open the project in NetBeans:
   - **File** → **Open Project**
   - Browse to `IFOBIA-v1.0` folder
   - Click **Open Project**

2. Clean and Build:
   - Right-click project name
   - Select **Clean and Build**
   - Wait for "BUILD SUCCESSFUL" message

3. Run the application:
   - Click **Run** → **Run Project** (or press `F6`)
   - Or right-click project → **Run**

4. Login screen should appear! 🎉

---

## 📱 Usage

### Default Login Credentials

#### For Students:
```
Username: check the sql file
Password: check the sql file
```

#### For Course Representatives (PJ):
```
Username: check the sql file
Password: check the sql file
```

### Quick Start Guide

1. **Login** with your credentials
2. **Dashboard** appears automatically showing your schedule, tasks, and announcements
3. Navigate through **Aktivitas** menu to access:
   - 📚 Learning Materials
   - 📝 Assignments
   - 📢 Announcements
4. **PJ users** can access **Kelola Kelas** for administrative tasks

---

## 📸 Screenshots

### Login Screen
![Login](https://via.placeholder.com/800x500/2C3E50/FFFFFF?text=Login+Screen)

### Dashboard
![Dashboard](https://via.placeholder.com/800x500/34495E/FFFFFF?text=Dashboard)

### Assignment Management
![Assignments](https://via.placeholder.com/800x500/16A085/FFFFFF?text=Assignment+Tracking)

### Material Repository
![Materials](https://via.placeholder.com/800x500/2980B9/FFFFFF?text=Learning+Materials)

---

## 👥 Team

<div align="center">

### 🏆 Kelompok CKM CITY

| Name | NPM | Role | GitHub |
|------|-----|------|--------|
| **Alia Hamzah** | 2410631170006 | 💻 Java Developer | [@aliaaa909](https://github.com/aliaaa909) |
| **Andhika Eka Pratama** | 2410631170129 | 🎨 GUI Designer | [@andhikaeka](#) |
| **Andhika Subagja** | 2410631170008 | 🗄️ Data Engineer | [@Andhikasubagja1b](https://github.com/Andhikasubagja1b) |
| **Muhammad Rizky Dermawan** | 2410631170038 | 🚀 Project Lead | [@dermawannnn99](https://github.com/dermawannnn99) |

</div>

### 🎯 Team Roles & Responsibilities

#### 💻 **Alia Hamzah** - Java Developer
- Backend development and business logic implementation
- Database integration using JDBC
- DAO (Data Access Object) pattern implementation

#### 🎨 **Andhika Eka Pratama** - GUI Designer
- User interface design and wireframing
- JavaFX/Swing component development
- UX optimization and user flow design
- Responsive layout implementation

#### 🗄️ **Andhika Subagja** - Data Engineer
- Database schema design and optimization
- SQL query optimization and indexing
- Data migration and backup strategies
- Database performance tuning

#### 🚀 **Muhammad Rizky Dermawan** - Project Lead
- Project coordination and timeline management
- System architecture design
- Code review and quality assurance
- Documentation and deployment

---

## 📂 Project Structure

```
IFOBIA/
├── Source Packages/
│   ├── com.ifobia.dao_entity/          # 🗄️ Data Access Layer
│   │   ├── absensiDAO.java
│   │   ├── jadwalDAO.java
│   │   ├── materiDAO.java
│   │   ├── pengumumanDAO.java
│   │   ├── tugasDAO.java
│   │   └── userDAO.java
│   │
│   ├── com.ifobia.entity/              # 📦 Model Layer
│   │   ├── absensi.java
│   │   ├── jadwal.java
│   │   ├── mataKuliah.java
│   │   ├── materi.java
│   │   ├── pengumuman.java
│   │   ├── tugas.java
│   │   └── user.java
│   │
│   ├── com.ifobia.form/                # 🖼️ Presentation Layer - Forms
│   │   ├── FormDashboard.java
│   │   ├── FormKelolaAbsensi.java
│   │   ├── FormKelolaJadwal.java
│   │   ├── FormKelolaPengumuman.java
│   │   ├── FormKelolaTugas.java
│   │   ├── FormLogin.java
│   │   ├── FormMateri.java
│   │   ├── FormPengumuman.java
│   │   ├── FormTugas.java
│   │   ├── FormUploadFileMateri.java
│   │   └── FormUploadLinkMateri.java
│   │
│   ├── com.ifobia.icon/                # 🎨 Icon Resources
│   │
│   ├── com.ifobia.menu/                # 📋 Menu Components
│   │   ├── LightDarkMode.java
│   │   ├── Menu.java
│   │   ├── MenuAction.java
│   │   ├── MenuAnimation.java
│   │   ├── MenuEvent.java
│   │   ├── MenuItem.java
│   │   ├── MenuItemLayout.java
│   │   └── PopupSubmenu.java
│   │
│   ├── com.ifobia.theme/               # 🎨 Theme Configuration
│   │   ├── FlatDarkLaf.properties
│   │   ├── FlatLaf.properties
│   │   └── FlatLightLaf.properties
│   │
│   ├── com.ifobia.database_util/       # 🔧 Database Utility
│   │   └── database_utility.java
│   │
│   └── com.ifobia.main/                # 🚀 Main Application
│       ├── FormMenuUtama.java
│       └── Main.java
│
├── Test Packages/
├── Test Libraries/
├── Libraries/
│   ├── FlatLaf.jar
│   └── mysql-connector-java.jar
│
└── README.md
```

---

## 🗄️ Database Schema

### Tables Overview

| Table | Description |
|-------|-------------|
| `ifobia_db_user` | User accounts and authentication |
| `ifobia_db_pj_kelas` | Course representative assignments |
| `ifobia_db_mata_kuliah` | Course information |
| `ifobia_db_jadwal` | Class schedules |
| `ifobia_db_tugas` | Assignment details |
| `ifobia_db_status_tugas` | Student assignment completion status |
| `ifobia_db_materi` | Learning materials repository |
| `ifobia_db_pengumuman` | Class announcements |
| `ifobia_db_absensi` | Attendance records |

For detailed schema, check teh SQL file.

---

## 🎨 Design & Architecture

### System Architecture

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│         (JavaFX/Swing GUI)              │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Business Logic Layer           │
│         (Controllers & Services)        │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Data Access Layer               │
│              (DAO Pattern)              │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│           Database Layer                │
│            (MySQL 8.0+)                 │
└─────────────────────────────────────────┘
```

### Design Patterns Used
- 🎯 **MVC (Model-View-Controller)** - Separation of concerns
- 🏭 **DAO (Data Access Object)** - Database abstraction
- 🔒 **Singleton** - Database connection management
- 🎨 **Factory** - Object creation patterns

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

1. 🍴 **Fork** the repository
2. 🌿 Create a new **branch** (`git checkout -b feature/AmazingFeature`)
3. 💾 **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. 📤 **Push** to the branch (`git push origin feature/AmazingFeature`)
5. 🔄 Open a **Pull Request**

Please read our [Contributing Guidelines](CONTRIBUTING.md) for more details.

---

## 🙏 Acknowledgments

- 👨‍🏫 **Yuyun Umaidah, M.Kom** - Supporting Lecturer
- 🏫 **Universitas Singaperbangsa Karawang** - Fakultas Ilmu Komputer
- 💡 Inspired by the need for better academic information management
- 🎓 Class B Informatics 2024 - For valuable feedback and testing

---

<div align="center">

### ⭐ If you find this project useful, please consider giving it a star!

**Made with ❤️ by Kelompok CKM CITY**

📅 **Project Timeline:** November 2024 - January 2025

---

© 2025 IFOBIA Team. All rights reserved.

</div>
