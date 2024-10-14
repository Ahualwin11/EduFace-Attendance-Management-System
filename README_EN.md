## EduFace-Attendance-Management-System

This system utilizes OpenCV for facial recognition, enabling quick identification of students in a class for automated attendance. EduFace aims to provide schools with a more efficient, accurate, and secure attendance management solution through an intelligent attendance system, thereby enhancing school management and teaching quality.

This README file introduces the basic information, installation steps, system architecture, and functional modules of the project, helping users quickly get started and understand the project.

---

### Table of Contents

1. [Project Introduction](#project-introduction)  
2. [System Features](#system-features)  
3. [Video Demonstration](#video-demonstration)
4. [File Structure](#file-structure)  
5. [Installation Guide](#installation-guide)  
6. [Usage Instructions](#usage-instructions)  
7. [Technical Architecture](#technical-architecture)  
8. [Database Design](#database-design)  
9. [Project Maintenance](#project-maintenance)  
10. [Clone](#clone)  

---

### Project Introduction

EduFace is a system for intelligent attendance management in schools, primarily utilizing computer vision technology to achieve contactless attendance through facial recognition algorithms. The system can automatically identify student identities, record attendance, and generate attendance reports and statistics, facilitating teachers in attendance management and subsequent teaching arrangements.

#### Project Features

- **Efficient Attendance**: Captures images through a camera, automatically identifies student identities, and generates attendance records.
- **Intelligent Management**: Allows importing and exporting of student information and attendance records, facilitating class and student information management for teachers.
- **Data Analysis**: Provides detailed attendance reports, including class attendance rates and individual student attendance statistics for multi-dimensional data analysis.
- **User-Friendly Interface**: Simple and easy-to-use interface design, allowing teachers to operate with ease.

---

### Video Demonstration

You can watch the project demonstration video via the following link: [Demo Video](https://www.bilibili.com/video/BV1YesXeoERB/?spm_id_from=333.999.0.0)

### System Features

The EduFace Attendance Management System includes the following functional modules:

1. **Check-in Function**: Quickly identifies student identities and records attendance using facial recognition algorithms.
2. **Face Enrollment Function**: Teachers can enroll facial information for students in the class to facilitate subsequent automated attendance by the system.
3. **Attendance Record Management**: Provides query, statistics, and export functions for attendance records, helping teachers quickly grasp class attendance.
4. **Student Information Management**: Supports adding, deleting, modifying student information, and importing/exporting student information.
5. **Data Statistics and Analysis**: Generates various charts such as class attendance charts and individual student attendance statistics to help teachers better analyze class attendance.
6. **Exception Reporting and Handling**: Allows students and teachers to appeal and handle erroneous attendance records in the system.

---

### File Structure

```
EduFace-Attendance-Management-System/
├── 01Project Description/        # Project requirements analysis, system design, flowcharts, and documentation
│   ├── EduFace Project Brochure.pdf# Project brochure
├── 02EduFace code/               # Source code files for the EduFace attendance system
│   ├── src/                     # Java source code
│   ├── lib/                     # Third-party libraries
│   └── resources/               # Configuration and resource files
├── 03Database SQL/               # Database design and SQL script files
│   ├── create_tables.sql        # SQL script for creating tables
│   └── insert_initial_data.sql  # SQL script for inserting initial data
├── 04Installation package instructions/ # Installation package and detailed installation instructions
│   ├── EduFaceInstaller.exe     # System installer
│   └── Installation Instructions.pdf # Installation steps documentation
└── README.md                    # Project overview and usage instructions (current file)
```

---

### Installation Guide

Follow these steps to install the EduFace Attendance Management System:

1. **Environment Requirements**
   - Operating System: Windows 10 or higher
   - JDK Version: Java JDK 8 or higher
   - Database: MySQL 5.7 or higher
   - Development Tools: IntelliJ IDEA / Eclipse
   - Image Processing Library: OpenCV 4.5.1 or higher
2. **Database Configuration**
   - Install MySQL database and create a database.
   - Execute `03Database SQL/create_tables.sql` to create the required tables.
   - Execute `03Database SQL/insert_initial_data.sql` to insert initial data.
3. **Installation Steps**
   - Run `04Installation package instructions/EduFaceInstaller.exe` to install the program.
   - After installation, launch the EduFace Attendance Management System.
4. **Dependency Installation**
   If you need to build the project manually, install the following dependencies:
   - OpenCV Java Binding
   - MySQL Connector/J
   - Java Swing (if a graphical interface is needed)
5. **Running the Program**
   Open the project using a development tool (e.g., IntelliJ IDEA) and set the `src` directory as the source root. Configure the database connection and run the `Main.java` file to start the system.

---

### Usage Instructions

1. **Teacher Registration and Login**
   - Teachers need to register using their employee ID and password when using the system for the first time.
   - After successful registration, teachers can log in using their registered username and password.

2. **Face Enrollment**
   - In the "Student Management" module, teachers select a class and click the "Enroll Student Information" button.
   - Enroll facial information for each student, ensuring adequate lighting and proper shooting angles during enrollment.

3. **Attendance Check-in**
   - Teachers click the "One-click Roll Call" button on the main interface.
   - The system will automatically activate the camera and perform facial recognition. Successfully recognized students will be automatically marked as "Present."

4. **Data Export and Import**
   - The system supports exporting attendance records to Excel for teachers to view and analyze.
   - It also supports importing existing student information tables to reduce manual entry workload.

---

### Technical Architecture

The EduFace Attendance Management System is built on the following technologies:

- **Frontend**: Java Swing (Graphical Interface)
- **Backend**: Java (Business Logic Processing)
- **Database**: MySQL (Data Storage)
- **Image Processing**: OpenCV (Facial Recognition and Processing)

#### System Module Diagram

```text
+----------------------------------+
|   EduFace System Architecture    |
+----------------------------------+
| Graphical Interface (Java Swing) |
+----------------------------------+
|  Business Logic Layer (Java)     |
+----------------------------------+
| Data Access Layer (JDBC + MySQL) |
+----------------------------------+
| Image Processing Layer (OpenCV)  |
+----------------------------------+
```

---

### Database Design

The database design for the EduFace Attendance Management System includes the following main tables:

- `Student` (Student Table): Records basic student information such as student ID, name, gender, class, etc.
- `Teacher` (Teacher Table): Records teacher information such as employee ID, name, gender, password, etc.
- `Course` (Course Table): Records course information such as course name, class time, classroom, etc.
- `AttendanceInfo` (Attendance Information Table): Records attendance data such as student ID, teacher ID, attendance time, attendance status, etc.

For detailed database table structures, see the `03Database SQL/create_tables.sql` file.

---

### Project Maintenance

1. **Bug Reporting and Discussion**
   - If you encounter any issues during use, you can submit them on the project's [Issues](https://github.com/your-repo/issues) page.

2. **Version Updates**
   - All updates will be recorded on the [Releases](https://github.com/your-repo/releases) page, where you can check the latest update logs at any time.

3. **Contribution Guidelines**
   - Contributions and improvement suggestions are welcome. Please read the [Contribution Guidelines](https://github.com/your-repo/contributing) file before submitting a PR.

---

### Clone

  ```sh
git clone https://github.com/Ahualwin11/EduFace-Attendance-Management-System.git
  ```