-- Base de datos para la Variante A: Gestión de empleados.
-- Se utiliza utf8mb4 para admitir tildes y caracteres especiales.

CREATE DATABASE IF NOT EXISTS empleados_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE empleados_db;

-- Tabla principal de empleados.
CREATE TABLE IF NOT EXISTS empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    departamento VARCHAR(80) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    fecha_contratacion DATE NOT NULL,
    tipo_contrato VARCHAR(20) NOT NULL DEFAULT 'Temporal',
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_salario_positivo
        CHECK (salario > 0),

    CONSTRAINT chk_tipo_contrato
        CHECK (
            tipo_contrato IN (
                'Temporal',
                'Permanente',
                'Por hora',
                'Ambos'
            )
        )
);