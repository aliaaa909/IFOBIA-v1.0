# 🎓 IFOBIA v1.0
### **I**nformations **o**f **B** Class **I**nformatics **A**ccess

<div align="center">

![IFOBIA Logo](https://github.com/dermawannnn99/IFOBIA-v1.0/blob/7f1a922a1b286c5040d1bb5a84f9f39ebeb15b4b/logo/LOGO%20UFOBUA.png)

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
- ☕ [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- 🗄️ [MySQL 8.0+](https://dev.mysql.com/downloads/mysql/)
- 🔧 [Apache NetBeans IDE](https://netbeans.apache.org/download/index.html) (Optional, for development)

### Step 1: Clone the Repository

```bash
git clone https://github.com/dermawannnn99/IFOBIA-v1.0.git
cd IFOBIA-v1.0
```

### Step 2: Database Setup

1. Create a new MySQL database:
```sql
CREATE DATABASE ifobia_db;
```

2. Import the database schema:
```bash
mysql -u your_username -p ifobia_db < database/ifobia_schema.sql
```

3. Update database credentials in `src/config/DatabaseConfig.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/ifobia_db";
private static final String DB_USER = "your_username";
private static final String DB_PASSWORD = "your_password";
```

### Step 3: Build and Run

#### Using NetBeans:
1. Open the project in NetBeans
2. Click **Run** → **Run Project** or press `F6`

#### Using Command Line:
```bash
mvn clean install
java -jar target/IFOBIA-v1.0.jar
```

---

## 📱 Usage

### Default Login Credentials

#### For Students:
```
Username: mahasiswa_demo
Password: demo123
```

#### For Course Representatives (PJ):
```
Username: pj_demo
Password: demo123
```

### Quick Start Guide

1. **Login** with your credentials
2. **Dashboard** appears automatically showing your schedule, tasks, and announcements
3. Navigate through **Aktivitas** menu to access:
   - 📚 Learning Materials
   - 📝 Assignments
   - 📢 Announcements
4. **PJ users** can access **Kelola Kelas** for administrative tasks

For detailed usage instructions, see our [User Manual](docs/USER_MANUAL.md).

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

### 🏆 Kelompok Overpower

| Name | NPM | Role | GitHub |
|------|-----|------|--------|
| **Alia Hamzah** | 2410631170006 | 💻 Java Developer | [@aliahamzah](#) |
| **Andhika Eka Pratama** | 2410631170129 | 🎨 GUI Designer | [@andhikaeka](#) |
| **Andhika Subagja** | 2410631170008 | 🗄️ Data Engineer | [@andhikasubagja](#) |
| **Muhammad Rizky Dermawan** | 2410631170038 | 🚀 Project Lead | [@dermawannnn99](https://github.com/dermawannnn99) |

</div>

### 🎯 Team Roles & Responsibilities

#### 💻 **Alia Hamzah** - Java Developer
- Backend development and business logic implementation
- Database integration using JDBC
- DAO (Data Access Object) pattern implementation
- 
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

## 📂 Project Structure (BELUM FIX)

```
IFOBIA-v1.0/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── ifobia/
│   │   │   │       ├── model/          # Data models (User, Tugas, Materi, etc.)
│   │   │   │       ├── view/           # GUI components
│   │   │   │       ├── controller/     # Business logic controllers
│   │   │   │       ├── dao/            # Database access objects
│   │   │   │       └── util/           # Utility classes
│   │   └── resources/
│   │       ├── images/                 # Image assets
│   │       ├── styles/                 # CSS stylesheets
│   │       └── fxml/                   # FXML layouts
├── database/
│   ├── ifobia_schema.sql              # Database schema
│   └── sample_data.sql                # Sample data for testing
├── docs/
│   ├── ERD.png                        # Entity Relationship Diagram
│   ├── USE_CASE.png                   # Use Case Diagram
│   └── USER_MANUAL.md                 # User manual
├── uploads/                           # Uploaded files storage
├── pom.xml                            # Maven configuration
└── README.md                          # This file
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

For detailed schema, see [Database Documentation](docs/DATABASE.md).

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

## 🐛 Bug Reports & Feature Requests

Found a bug or have a feature request? Please open an issue on our [GitHub Issues](https://github.com/dermawannnn99/IFOBIA-v1.0/issues) page.

### Bug Report Template
- **Description**: Clear description of the bug
- **Steps to Reproduce**: How to reproduce the issue
- **Expected Behavior**: What should happen
- **Actual Behavior**: What actually happens
- **Screenshots**: If applicable

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- 👨‍🏫 **Yuyun Umaidah, M.Kom** - Project Supervisor
- 🏫 **Universitas Singaperbangsa Karawang** - Fakultas Ilmu Komputer
- 💡 Inspired by the need for better academic information management
- 🎓 Class B Informatics 2024 - For valuable feedback and testing

---

## 📞 Contact

For questions, suggestions, or support, please contact:

- 📧 Email: ifobia.support@example.com
- 🌐 Website: [ifobia-project.com](#)
- 💬 Discord: [Join our server](#)

---

<div align="center">

### ⭐ If you find this project useful, please consider giving it a star!

**Made with ❤️ by Kelompok CKM CITY**

📅 **Project Timeline:** November 2024 - January 2025

---

© 2025 IFOBIA Team. All rights reserved.

</div>
