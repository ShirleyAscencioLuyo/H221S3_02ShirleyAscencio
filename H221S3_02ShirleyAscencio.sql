
---Configuramos el idioma de la base de datos a español
SET LANGUAGE Español
GO

--Ver idioma de SQL Server
SELECT @@language AS 'Idioma'
GO

--Formato fecha
SET DATEFORMAT dmy

SELECT sysdatetime() as 'Fecha y  hora'
GO

USE master;
DROP DATABASE IF EXISTS H221S3_02ShirleyAscencio;

CREATE DATABASE H221S3_02ShirleyAscencio;
USE H221S3_02ShirleyAscencio;


CREATE TABLE student(
    student_id int IDENTITY(1,1),
	names varchar(60) NOT NULL,
	lastname varchar(60) NOT NULL,
	email varchar(120) NOT NULL UNIQUE,
	career varchar(50),
	semester varchar(50),
    document_type char(3) NOT NULL,
    document_number char(9) NOT NULL UNIQUE,
    active char(1) DEFAULT ('A'),
    CONSTRAINT student_pk PRIMARY KEY (student_id)
)
GO

					--RESTRICCIONES

-- agregamos Restriccion de solo letras en nobre y apellido
ALTER TABLE student
	ADD CONSTRAINT names_student
	CHECK (names NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%')
GO
ALTER TABLE student
	ADD CONSTRAINT lastname_student
	CHECK (lastname NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%')
GO

ALTER TABLE student
	ADD CONSTRAINT email_student
    CHECK (email LIKE '%@gmail.com' OR email LIKE '%@hotmail.com' OR email LIKE '%@outlook.com' OR email LIKE '%@yahoo.com')
GO


-- Agregando restriccion de tipo de documento
ALTER TABLE student
         ADD CONSTRAINT document_number_check
        CHECK ((document_type = 'DNI' AND LEN(document_number) = 8) OR
        (document_type = 'CNE' AND LEN(document_number) = 9));

ALTER TABLE student
	ADD CONSTRAINT document_number_student
	CHECK (document_number like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][^A-Z]')
GO

ALTER TABLE student
	ADD CONSTRAINT states_student
	CHECK(active ='A' OR active ='I')
GO


--Insertamos registro en la tabla estudiante
INSERT INTO student
		(names, lastname,email,career,semester,document_type, document_number)
VALUES
		('Jose Megun', 'Cama la madrid','jose.cama@gmail.com','Analisis de sistemas', 'Primero','CNE', '739314781'),
		('Gabriel Esteban', 'Gutierrez Quispe','gabriel.gutierrez@gmail.com','Analisis de sistemas', 'Primero','DNI', '56743973'),
		('Mario luis', 'Flores Huaman','mario.flores@gmail.com','Analisis de sistemas', 'Primero','CNE', '675484752'),
		('Jose Armando', 'Flores Junes','jose.armando@gmail.com','Analisis de sistemas', 'quinto','DNI', '12345678'),
		('Maria Vicente', 'Luyo Luyo','maria.vicente@gmail.com','Analisis de sistemas', 'tercero','DNI', '87654321')
	GO

-- Listamos la tabla estudiante.
SELECT * FROM student
GO

--Listado de tabla student
CREATE VIEW lista_student AS
SELECT names AS NOMBRES,
       lastname AS APELLIDOS,
       email AS CORREO,
       career AS CARRERA,
       semester AS SEMESTRE,
       document_type AS 'DNI O CNE',
       document_number AS 'N° DOCUMENTO',
       CASE
           WHEN active = 'A' THEN 'Activo'
           WHEN active = 'I' THEN 'Inactivo'
       END AS ESTADO
FROM student;
GO

--Listamos por vista de tabla Estudiantes
SELECT * FROM lista_student;
GO

--Creamos tabla administrative
CREATE TABLE administrative (
    id int IDENTITY(1,1) PRIMARY KEY,
    document_type char(3) NOT NULL,
    document_number char(9) UNIQUE,
    names varchar(60) NOT NULL CHECK (names NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%'),
    lastnames varchar(60) NOT NULL CHECK (lastnames NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%'),
    type_charge varchar(25) NOT NULL,
    active char(1) DEFAULT ('A') CHECK (active IN ('A', 'I')),
    CONSTRAINT document_number_administrative1 CHECK ((document_type = 'DNI' AND LEN(document_number) = 8) OR (document_type = 'CNE' AND LEN(document_number) = 9)),
    CONSTRAINT document_number_administrative CHECK (document_number LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][^A-Z]')
);


-- Insertamos datos administrative

INSERT INTO administrative
		(document_type, document_number, names, lastnames, type_charge)
VALUES
		('DNI', '46583952', 'Luis', 'Correa Candela', 'Tesorero'),
		('CNE', '758390567', 'Miguel Rody', 'Mendieta Espinoza', 'Tesorero'),
		('DNI', '12345788', 'Alfreda', 'cabello matos', 'Tesorera'),
		('CNE', '174936437', 'jose', 'lucio Rosas', 'Tesorero'),
		('DNI', '87493458', 'shirley', 'ascencio luyo', 'Tesorera'),
		('CNE', '534673827', 'jennyfer', 'luyo flores', 'Tesorera')
	GO

-- Listamos la tabla

SELECT * FROM administrative
GO

--Listado de tabla administrative
CREATE VIEW lista_administrative AS
SELECT document_type AS 'Tipo de Documento',
       document_number AS 'N° Documento',
       names AS Nombres,
       lastnames AS Apellidos,
       type_charge AS 'Tipo de Cargo'
FROM administrative;
GO


--Listamos por vis ta de tabla admiistrative
SELECT * FROM lista_administrative;
GO


-- Creando tabla Payments (pagos)
CREATE TABLE Payments (
  id INT PRIMARY KEY,
  student_id INT,
  Amount DECIMAL(10, 2) CHECK (Amount >= 0),
  Dates DATE CHECK (Dates <= GETDATE()),
  Descriptions VARCHAR(100),
  PaymentMethod VARCHAR(50),
  ReferenceNumber VARCHAR(50),
  PaymentStatus VARCHAR(20) CHECK (PaymentStatus IN ('Pendiente', 'Pagado', 'Cancelado')),
  PaymentType VARCHAR(50) CHECK (PaymentType IN ('Matrícula', 'Mensualidad', 'Inscripción', 'Otros')),
  PaymentReceipt VARCHAR(100),
  FOREIGN KEY (student_id) REFERENCES student(student_id),
  CONSTRAINT id_Unique UNIQUE (id)
);


-- Insertando registros a tabla pagos
INSERT INTO Payments (id, student_id, Amount, Dates, Descriptions, PaymentMethod, ReferenceNumber, PaymentStatus, PaymentType, PaymentReceipt)
VALUES
 (1, 1, 500.00, '2023-06-15', 'Matrícula', 'Tarjeta de Crédito', 'ABC123', 'Pagado', 'Matrícula', NULL),
 (2, 2, 750.00, '2023-07-01', 'Pago de Mensualidad', 'Transferencia Bancaria', 'XYZ789', 'Pendiente', 'Mensualidad', NULL),
 (3, 3, 250.00, '2023-06-30', 'Cuota de Inscripción', 'Efectivo', 'DEF456', 'Pagado', 'Inscripción', NULL);


-- Listamos la tabla

SELECT * FROM Payments
GO

--Creamos vista para tabla Payments (pagos)
CREATE VIEW VistaPagos AS
SELECT
  id AS ID_Pago,
  student_id AS ID_Estudiante,
  Amount AS Monto,
  Dates AS Fecha,
  Descriptions AS Descripcion,
  PaymentMethod AS MetodoPago,
  ReferenceNumber AS NumeroReferencia,
  PaymentStatus AS EstadoPago,
  PaymentType AS TipoPago,
  PaymentReceipt AS ComprobantePago
FROM Payments;
GO
--Listamos por vista de tabla Payments (pagos)
SELECT * FROM VistaPagos;
GO


--Creando tablas tarifa
CREATE TABLE Tarifas (
  id INT PRIMARY KEY,
  MatriculationFee DECIMAL(10, 2) CHECK (MatriculationFee >= 0),
  MonthlyFee DECIMAL(10, 2) CHECK (MonthlyFee >= 0),
  ProgramDuration INT CHECK (ProgramDuration > 0),
  AcademicYear INT CHECK (AcademicYear > 0),
  EffectiveStartDate DATE,
  EffectiveEndDate DATE,
  CONSTRAINT EffectiveDate_Valid CHECK (EffectiveStartDate <= EffectiveEndDate)
);


--Ingresando registrpos a tabla tarifa
INSERT INTO Tarifas (id, MatriculationFee, MonthlyFee, ProgramDuration, AcademicYear, EffectiveStartDate, EffectiveEndDate)
VALUES
  (1, 500.00, 100.00, 4, 2023, '2023-09-01', '2027-08-31'),
  (2, 800.00, 150.00, 3, 2023, '2023-09-01', '2026-08-31'),
  (3, 600.00, 120.00, 5, 2023, '2023-09-01', '2028-08-31');
GO

--Creamos vista de tabla tarifa
CREATE VIEW VistaTarifas AS
SELECT id AS TarifaID,
       MatriculationFee AS Matricula,
       MonthlyFee AS Mensualidad,
       ProgramDuration AS DuracionPrograma,
       AcademicYear AS AnioAcademico,
       EffectiveStartDate AS FechaInicio,
       EffectiveEndDate AS FechaFin
FROM Tarifas;
GO

--Listamos vista de tabla tarifa
SELECT * FROM VistaTarifas;
GO




