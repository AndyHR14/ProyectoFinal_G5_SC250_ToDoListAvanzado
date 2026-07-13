-- =====================================================
-- BASE DE DATOS: ORGANIZADOR DE TAREAS AVANZADO
-- =====================================================

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS organizador_tareas;

-- Crear la base de datos
CREATE DATABASE organizador_tareas
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Seleccionar la base de datos
USE organizador_tareas;

-- =====================================================
-- TABLA: USUARIOS
-- =====================================================

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,

    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: CATEGORIAS
-- =====================================================

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    color VARCHAR(20)
);

-- =====================================================
-- TABLA: TAREAS
-- =====================================================

CREATE TABLE tareas (
    id_tarea INT AUTO_INCREMENT PRIMARY KEY,

    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,

    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_limite DATE,

    prioridad ENUM('ALTA', 'MEDIA', 'BAJA')
        NOT NULL DEFAULT 'MEDIA',

    estado ENUM('PENDIENTE',
                'EN_PROGRESO',
                'COMPLETADA',
                'VENCIDA')
        NOT NULL DEFAULT 'PENDIENTE',

    progreso INT NOT NULL DEFAULT 0,

    id_usuario INT NOT NULL,
    id_categoria INT,

    CONSTRAINT chk_progreso
        CHECK (progreso BETWEEN 0 AND 100),

    CONSTRAINT fk_tarea_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_tarea_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- =====================================================
-- TABLA: ETIQUETAS
-- =====================================================

CREATE TABLE etiquetas (
    id_etiqueta INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- =====================================================
-- TABLA INTERMEDIA: TAREA_ETIQUETA
-- RELACIÓN MUCHOS A MUCHOS
-- =====================================================

CREATE TABLE tarea_etiqueta (
    id_tarea INT NOT NULL,
    id_etiqueta INT NOT NULL,

    PRIMARY KEY (id_tarea, id_etiqueta),

    CONSTRAINT fk_te_tarea
        FOREIGN KEY (id_tarea)
        REFERENCES tareas(id_tarea)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_te_etiqueta
        FOREIGN KEY (id_etiqueta)
        REFERENCES etiquetas(id_etiqueta)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =====================================================
-- TABLA: SUBTAREAS
-- =====================================================

CREATE TABLE subtareas (
    id_subtarea INT AUTO_INCREMENT PRIMARY KEY,

    descripcion VARCHAR(255) NOT NULL,
    completada BOOLEAN NOT NULL DEFAULT FALSE,

    id_tarea INT NOT NULL,

    CONSTRAINT fk_subtarea_tarea
        FOREIGN KEY (id_tarea)
        REFERENCES tareas(id_tarea)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =====================================================
-- TABLA: RECORDATORIOS
-- =====================================================

CREATE TABLE recordatorios (
    id_recordatorio INT AUTO_INCREMENT PRIMARY KEY,

    fecha_hora DATETIME NOT NULL,
    mensaje VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    id_tarea INT NOT NULL,

    CONSTRAINT fk_recordatorio_tarea
        FOREIGN KEY (id_tarea)
        REFERENCES tareas(id_tarea)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =====================================================
-- TABLA: REGISTRO DE TIEMPO
-- =====================================================

CREATE TABLE registro_tiempo (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,

    inicio DATETIME NOT NULL,
    fin DATETIME,

    duracion_minutos INT DEFAULT 0,

    id_tarea INT NOT NULL,

    CONSTRAINT chk_duracion
        CHECK (duracion_minutos >= 0),

    CONSTRAINT fk_registro_tarea
        FOREIGN KEY (id_tarea)
        REFERENCES tareas(id_tarea)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =====================================================
-- ÍNDICES 
-- =====================================================

CREATE INDEX idx_tareas_usuario
ON tareas(id_usuario);

CREATE INDEX idx_tareas_categoria
ON tareas(id_categoria);

CREATE INDEX idx_tareas_estado
ON tareas(estado);

CREATE INDEX idx_tareas_prioridad
ON tareas(prioridad);

CREATE INDEX idx_tareas_fecha_limite
ON tareas(fecha_limite);

CREATE INDEX idx_subtareas_tarea
ON subtareas(id_tarea);

CREATE INDEX idx_recordatorios_tarea
ON recordatorios(id_tarea);

CREATE INDEX idx_registro_tiempo_tarea
ON registro_tiempo(id_tarea);

-- =====================================================
-- DATOS INICIALES OPCIONALES
-- =====================================================

INSERT INTO categorias (nombre, color) VALUES
('Trabajo', '#FF5733'),
('Estudio', '#3498DB'),
('Personal', '#2ECC71'),
('Salud', '#9B59B6');

INSERT INTO etiquetas (nombre) VALUES
('Urgente'),
('Importante'),
('Pendiente'),
('Revisión');

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================