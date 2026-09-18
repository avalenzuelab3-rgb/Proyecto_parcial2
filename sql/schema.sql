-- Base de datos para la Variante A: Gestion de empleados
-- Se utiliza utf8mb4 para admitir nombres con tildes y caracteres especiales.

CREATE DATABASE IF NOT EXISTS empleados_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE empleados_db;

-- Decisiones de diseño:
-- BIGINT: permite una cantidad amplia de identificadores.
-- VARCHAR(120): admite nombres completos extensos.
-- VARCHAR(80): permite departamentos escritos como texto libre.
-- DECIMAL(10,2): almacena salarios con centavos sin errores de redondeo.
-- DATE: almacena únicamente la fecha de contratación.
-- BOOLEAN: representa si el empleado está activo o inactivo.

CREATE TABLE IF NOT EXISTS empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    departamento VARCHAR(80) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    fecha_contratacion DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_salario_positivo
        CHECK (salario > 0)
);