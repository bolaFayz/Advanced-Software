-- ============================================================
--  SoftCore Solutions — Company Management System
--  Database Script: Create Tables + Sample Data
--  Database: SQL Server
--  Version: 1.0
-- ============================================================

-- ── Create & Use Database ───────────────────────────────────
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'SoftCoreDB')
    CREATE DATABASE SoftCoreDB;
GO

USE SoftCoreDB;
GO

-- ============================================================
--  DROP TABLES (Safe to run multiple times)
-- ============================================================
IF OBJECT_ID('PROJECT_EMPLOYEES', 'U') IS NOT NULL DROP TABLE PROJECT_EMPLOYEES;
IF OBJECT_ID('TASKS',             'U') IS NOT NULL DROP TABLE TASKS;
IF OBJECT_ID('PROJECTS',          'U') IS NOT NULL DROP TABLE PROJECTS;
IF OBJECT_ID('SALARIES',          'U') IS NOT NULL DROP TABLE SALARIES;
IF OBJECT_ID('EMPLOYEES',         'U') IS NOT NULL DROP TABLE EMPLOYEES;
IF OBJECT_ID('DEPARTMENTS',       'U') IS NOT NULL DROP TABLE DEPARTMENTS;
IF OBJECT_ID('JOB_TITLES',        'U') IS NOT NULL DROP TABLE JOB_TITLES;
IF OBJECT_ID('CLIENTS',           'U') IS NOT NULL DROP TABLE CLIENTS;
IF OBJECT_ID('ADMINS',            'U') IS NOT NULL DROP TABLE ADMINS;
GO

-- ============================================================
--  CREATE TABLES
-- ============================================================

-- ── 1. ADMINS ───────────────────────────────────────────────
CREATE TABLE ADMINS (
    admin_id      INT IDENTITY(1,1) PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    DATETIME     DEFAULT GETDATE()
);
GO

-- ── 2. JOB_TITLES ───────────────────────────────────────────
CREATE TABLE JOB_TITLES (
    job_title_id INT IDENTITY(1,1) PRIMARY KEY,
    title_name   VARCHAR(100) NOT NULL UNIQUE
);
GO

-- ── 3. DEPARTMENTS ──────────────────────────────────────────
--  manager_id FK will be added after EMPLOYEES
CREATE TABLE DEPARTMENTS (
    department_id INT IDENTITY(1,1) PRIMARY KEY,
    dept_name     VARCHAR(100) NOT NULL UNIQUE,
    description   VARCHAR(255),
    manager_id    INT          -- FK added after EMPLOYEES
);
GO

-- ── 4. EMPLOYEES ────────────────────────────────────────────
CREATE TABLE EMPLOYEES (
    employee_id   INT IDENTITY(1,1) PRIMARY KEY,
    full_name     VARCHAR(100) NOT NULL,
    phone         VARCHAR(20),
    email         VARCHAR(100),
    hire_date     DATE,
    job_title     VARCHAR(100),
    salary        DECIMAL(10,2),
    department_id INT,
    CONSTRAINT FK_EMP_DEPT FOREIGN KEY (department_id)
        REFERENCES DEPARTMENTS(department_id)
);
GO

-- Now add manager_id FK to DEPARTMENTS
ALTER TABLE DEPARTMENTS
    ADD CONSTRAINT FK_DEPT_MGR FOREIGN KEY (manager_id)
        REFERENCES EMPLOYEES(employee_id);
GO

-- ── 5. CLIENTS ──────────────────────────────────────────────
CREATE TABLE CLIENTS (
    client_id      INT IDENTITY(1,1) PRIMARY KEY,
    company_name   VARCHAR(150) NOT NULL,
    contact_person VARCHAR(100),
    phone          VARCHAR(20),
    email          VARCHAR(100)
);
GO

-- ── 6. PROJECTS ─────────────────────────────────────────────
CREATE TABLE PROJECTS (
    project_id    INT IDENTITY(1,1) PRIMARY KEY,
    project_name  VARCHAR(150) NOT NULL,
    status        VARCHAR(30)  NOT NULL
                  CONSTRAINT CHK_PROJ_STATUS
                  CHECK (status IN ('Planning','Active','In Review','Done','Cancelled')),
    start_date    DATE,
    end_date      DATE,
    price         DECIMAL(10,2),
    client_id     INT,
    manager_id    INT,
    department_id INT,
    CONSTRAINT FK_PROJ_CLIENT FOREIGN KEY (client_id)
        REFERENCES CLIENTS(client_id),
    CONSTRAINT FK_PROJ_MGR    FOREIGN KEY (manager_id)
        REFERENCES EMPLOYEES(employee_id),
    CONSTRAINT FK_PROJ_DEPT   FOREIGN KEY (department_id)
        REFERENCES DEPARTMENTS(department_id)
);
GO

-- ── 7. TASKS ────────────────────────────────────────────────
CREATE TABLE TASKS (
    task_id     INT IDENTITY(1,1) PRIMARY KEY,
    title       VARCHAR(150) NOT NULL,
    description TEXT,
    status      VARCHAR(30)  NOT NULL
                CONSTRAINT CHK_TASK_STATUS
                CHECK (status IN ('To Do','In Progress','Done')),
    due_date    DATE,
    project_id  INT,
    assigned_to INT,
    CONSTRAINT FK_TASK_PROJ FOREIGN KEY (project_id)
        REFERENCES PROJECTS(project_id),
    CONSTRAINT FK_TASK_EMP  FOREIGN KEY (assigned_to)
        REFERENCES EMPLOYEES(employee_id)
);
GO

-- ── 8. PROJECT_EMPLOYEES (Junction) ─────────────────────────
CREATE TABLE PROJECT_EMPLOYEES (
    project_id  INT NOT NULL,
    employee_id INT NOT NULL,
    CONSTRAINT PK_PROJ_EMP  PRIMARY KEY (project_id, employee_id),
    CONSTRAINT FK_PE_PROJ   FOREIGN KEY (project_id)
        REFERENCES PROJECTS(project_id),
    CONSTRAINT FK_PE_EMP    FOREIGN KEY (employee_id)
        REFERENCES EMPLOYEES(employee_id)
);
GO

-- ============================================================
--  SAMPLE DATA
-- ============================================================

-- ── ADMINS ──────────────────────────────────────────────────
-- password = "admin123" (hashed with SHA2_256)
INSERT INTO ADMINS (username, password_hash) VALUES
('admin',    CONVERT(VARCHAR(255), HASHBYTES('SHA2_256', 'admin123'),   2)),
('superadmin', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256', 'super456'), 2));
GO

-- ── JOB_TITLES ──────────────────────────────────────────────
INSERT INTO JOB_TITLES (title_name) VALUES
('Software Engineer'),
('Senior Software Engineer'),
('Mobile Developer'),
('UI/UX Designer'),
('AI Engineer'),
('Security Analyst'),
('Project Manager'),
('QA Engineer'),
('DevOps Engineer'),
('Team Lead');
GO

-- ── DEPARTMENTS (without manager_id first) ──────────────────
INSERT INTO DEPARTMENTS (dept_name, description) VALUES
('Web Development',     'Develops web applications and websites'),
('Mobile Development',  'Develops iOS and Android mobile applications'),
('AI & Data',           'Artificial intelligence and data analysis'),
('Cybersecurity',       'Information security and penetration testing'),
('UI/UX Design',        'User interface and user experience design');
GO

-- ── EMPLOYEES ───────────────────────────────────────────────
INSERT INTO EMPLOYEES (full_name, phone, email, hire_date, job_title, salary, department_id) VALUES
-- Web Development (dept 1)
('Ahmed Kamal',      '01001234567', 'ahmed.kamal@softcore.com',    '2021-03-15', 'Senior Software Engineer', 25000.00, 1),
('Nour El-Din Samir','01112345678', 'nour.samir@softcore.com',      '2022-06-01', 'Software Engineer',        18000.00, 1),
('Dina Mostafa',     '01223456789', 'dina.mostafa@softcore.com',    '2023-01-10', 'Software Engineer',        17000.00, 1),

-- Mobile Development (dept 2)
('Sara Hassan',      '01334567890', 'sara.hassan@softcore.com',     '2020-09-20', 'Mobile Developer',         22000.00, 2),
('Khaled Fouad',     '01445678901', 'khaled.fouad@softcore.com',    '2022-11-05', 'Mobile Developer',         20000.00, 2),

-- AI & Data (dept 3)
('Rana Ibrahim',     '01556789012', 'rana.ibrahim@softcore.com',    '2021-07-01', 'AI Engineer',              28000.00, 3),
('Omar Tarek',       '01667890123', 'omar.tarek@softcore.com',      '2023-03-15', 'AI Engineer',              24000.00, 3),

-- Cybersecurity (dept 4)
('Mahmoud Adel',     '01778901234', 'mahmoud.adel@softcore.com',    '2020-05-10', 'Security Analyst',         23000.00, 4),
('Yasmin Nabil',     '01889012345', 'yasmin.nabil@softcore.com',    '2022-08-20', 'Security Analyst',         21000.00, 4),

-- UI/UX Design (dept 5)
('Layla Hossam',     '01990123456', 'layla.hossam@softcore.com',    '2021-12-01', 'UI/UX Designer',           19000.00, 5),
('Tarek Wael',       '01001122334', 'tarek.wael@softcore.com',      '2023-05-15', 'UI/UX Designer',           17500.00, 5);
GO

-- Update department managers
UPDATE DEPARTMENTS SET manager_id = 1  WHERE department_id = 1; -- Ahmed Kamal
UPDATE DEPARTMENTS SET manager_id = 4  WHERE department_id = 2; -- Sara Hassan
UPDATE DEPARTMENTS SET manager_id = 6  WHERE department_id = 3; -- Rana Ibrahim
UPDATE DEPARTMENTS SET manager_id = 8  WHERE department_id = 4; -- Mahmoud Adel
UPDATE DEPARTMENTS SET manager_id = 10 WHERE department_id = 5; -- Layla Hossam
GO

-- ── CLIENTS ─────────────────────────────────────────────────
INSERT INTO CLIENTS (company_name, contact_person, phone, email) VALUES
('NileTech Solutions',   'Sherif Abdallah',  '0223456789', 'sherif@niletech.com'),
('BankCo Egypt',         'Hana Mahmoud',     '0234567890', 'hana@bankco.eg'),
('DataCorp MENA',        'Amr Saleh',        '0245678901', 'amr@datacorp.io'),
('RetailZone',           'Mona Farouk',      '0256789012', 'mona@retailzone.com'),
('HealthPlus Clinics',   'Dr. Ramy Khalil',  '0267890123', 'ramy@healthplus.com'),
('EduSmart Academy',     'Samar El-Shamy',   '0278901234', 'samar@edusmart.com');
GO

-- ── PROJECTS ────────────────────────────────────────────────
INSERT INTO PROJECTS (project_name, status, start_date, end_date, price, client_id, manager_id, department_id) VALUES
('NileTech E-Commerce App',      'Active',    '2024-01-15', '2024-12-31', 150000.00, 1, 4, 2),
('BankCo Security Audit',        'In Review', '2024-03-01', '2024-10-31',  80000.00, 2, 8, 4),
('DataCorp AI Dashboard',        'Planning',  '2024-06-01', '2025-03-31', 200000.00, 3, 6, 3),
('RetailZone Corporate Website', 'Done',      '2023-09-01', '2024-04-30',  60000.00, 4, 1, 1),
('HealthPlus Mobile App',        'Active',    '2024-04-01', '2025-01-31', 120000.00, 5, 4, 2),
('EduSmart Web Platform',        'Planning',  '2024-07-01', '2025-06-30',  95000.00, 6, 1, 1);
GO

-- ── TASKS ───────────────────────────────────────────────────
INSERT INTO TASKS (title, description, status, due_date, project_id, assigned_to) VALUES
-- NileTech E-Commerce (proj 1)
('Design UI mockups',       'Design all application screens',           'Done',        '2024-02-28', 1, 10),
('Setup backend API',       'Build the REST API using Java',           'In Progress', '2024-05-15', 1, 2),
('Integrate payment gateway','Integrate the electronic payment gateway',           'To Do',       '2024-07-01', 1, 5),
('QA & Testing',            'Test all application features',             'To Do',       '2024-09-01', 1, 4),

-- BankCo Security Audit (proj 2)
('Penetration testing',     'Comprehensive penetration testing for all systems',            'Done',        '2024-05-30', 2, 8),
('Vulnerability report',    'Prepare the security vulnerabilities report',           'In Progress', '2024-08-15', 2, 9),

-- DataCorp AI Dashboard (proj 3)
('Data pipeline setup',     'Set up data processing pipelines',            'To Do',       '2024-08-01', 3, 6),
('ML model training',       'Train the prediction model',                    'To Do',       '2024-10-01', 3, 7),
('Dashboard frontend',      'Build the interactive display dashboard',            'To Do',       '2024-11-15', 3, 3),

-- RetailZone Website (proj 4)
('Homepage design',         'Design the homepage',                'Done',        '2023-10-15', 4, 10),
('CMS integration',         'Integrate the content management system',               'Done',        '2024-01-20', 4, 3),
('Launch & deployment',     'Deploy the website to the server',               'Done',        '2024-04-25', 4, 2),

-- HealthPlus Mobile App (proj 5)
('App architecture design', 'Design the application architecture',                   'Done',        '2024-05-01', 5, 5),
('Patient registration UI', 'Patient registration screen',                    'In Progress', '2024-07-15', 5, 4),
('Appointment booking',     'Appointment booking system',                    'To Do',       '2024-09-30', 5, 5);
GO

-- ── PROJECT_EMPLOYEES ────────────────────────────────────────
INSERT INTO PROJECT_EMPLOYEES (project_id, employee_id) VALUES
-- NileTech E-Commerce
(1, 4), (1, 5), (1, 2), (1, 10),
-- BankCo Security Audit
(2, 8), (2, 9),
-- DataCorp AI Dashboard
(3, 6), (3, 7), (3, 3),
-- RetailZone Website
(4, 1), (4, 2), (4, 10),
-- HealthPlus Mobile App
(5, 4), (5, 5), (5, 10),
-- EduSmart Platform
(6, 1), (6, 3), (6, 11);
GO

-- ============================================================
--  VERIFY — Sample data preview
-- ============================================================
PRINT '── ADMINS ──';
SELECT * FROM ADMINS;

PRINT '── DEPARTMENTS ──';
SELECT d.dept_name, e.full_name AS manager
FROM DEPARTMENTS d
LEFT JOIN EMPLOYEES e ON d.manager_id = e.employee_id;

PRINT '── EMPLOYEES ──';
SELECT e.full_name, e.job_title, e.salary, d.dept_name
FROM EMPLOYEES e
JOIN DEPARTMENTS d ON e.department_id = d.department_id;

PRINT '── PROJECTS ──';
SELECT p.project_name, p.status, p.price,
       c.company_name AS client,
       e.full_name    AS manager,
       d.dept_name    AS department
FROM PROJECTS p
JOIN CLIENTS     c ON p.client_id     = c.client_id
JOIN EMPLOYEES   e ON p.manager_id    = e.employee_id
JOIN DEPARTMENTS d ON p.department_id = d.department_id;

PRINT '── TASKS ──';
SELECT t.title, t.status, t.due_date,
       p.project_name,
       e.full_name AS assigned_to
FROM TASKS t
JOIN PROJECTS  p ON t.project_id  = p.project_id
JOIN EMPLOYEES e ON t.assigned_to = e.employee_id;

PRINT '✅ SoftCoreDB created and populated successfully!';
GO