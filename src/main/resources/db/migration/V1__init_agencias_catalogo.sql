-- ============================================================================
-- MIGRACIÓN V1: Microservicio 3 - Agencias y Catálogo (Huila Travel Expedition)
-- Base de Datos: db_hte_agencias_catalogo
-- Requisitos SRS: RF1, RF3, RF4, RF6, RF7, RF17
-- ============================================================================

-- 1. Tabla de Agencias Turísticas del Huila
CREATE TABLE agencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    nit VARCHAR(25) NOT NULL UNIQUE,
    rnt VARCHAR(30) NOT NULL UNIQUE,
    ciudad VARCHAR(80) NOT NULL,
    direccion VARCHAR(180) NOT NULL,
    telefono VARCHAR(30) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    sitio_web VARCHAR(200),
    redes_sociales VARCHAR(255),
    descripcion TEXT,
    anio_fundacion INT,
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE_APROBACION',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultima_modificacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_agencias_ciudad ON agencias(ciudad);
CREATE INDEX idx_agencias_estado ON agencias(estado);

-- 2. Tabla de Categorías de Turismo (RF6)
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NOT NULL,
    color_hex VARCHAR(15) DEFAULT '#B84B26',
    activa BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Tabla del Catálogo de Planes Turísticos (RF4, RF7, RF17)
CREATE TABLE planes_turisticos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    agencia_id BIGINT NOT NULL,
    municipio_destino VARCHAR(100) NOT NULL,
    categoria_id BIGINT NOT NULL,
    precio DECIMAL(12, 2) NOT NULL,
    duracion_dias INT NOT NULL,
    cupo_maximo INT NOT NULL DEFAULT 15,
    calificacion_promedio DOUBLE NOT NULL DEFAULT 5.0,
    destacado BOOLEAN NOT NULL DEFAULT FALSE,
    hotel VARCHAR(150),
    transporte VARCHAR(120),
    guia VARCHAR(120),
    descripcion TEXT NOT NULL,
    itinerario TEXT NOT NULL,
    incluye TEXT,
    no_incluye TEXT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultima_modificacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_plan_agencia FOREIGN KEY (agencia_id) REFERENCES agencias(id),
    CONSTRAINT fk_plan_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_planes_municipio ON planes_turisticos(municipio_destino);
CREATE INDEX idx_planes_precio ON planes_turisticos(precio);
CREATE INDEX idx_planes_agencia ON planes_turisticos(agencia_id);

-- ============================================================================
-- DATOS SEMILLA INICIALES (Basados en el Prototipo de Huila Travel Expedition)
-- ============================================================================

INSERT INTO agencias (nombre, nit, rnt, ciudad, direccion, telefono, correo, sitio_web, redes_sociales, descripcion, anio_fundacion, estado) VALUES
('Andes Expediciones Huila', '900.123.456-1', 'RNT-41001', 'Neiva', 'Cra 5 # 12-30, Centro', '(608) 871 2201', 'contacto@andesexpediciones.co', 'https://andesexpediciones.co', '@andesexpedicioneshuila', 'Especialistas en astroturismo y expediciones 4x4 por el desierto de la Tatacoa.', 2019, 'APROBADA'),
('Río Abajo Tours', '900.234.567-2', 'RNT-41002', 'Neiva', 'Cra 8 # 20-14, Centro', '(608) 871 4410', 'reservas@rioabajotours.co', 'https://rioabajotours.co', '@rioabajotours', 'Deportes de aventura y recorridos ecológicos sobre el río Magdalena.', 2017, 'APROBADA'),
('Café y Montaña', '900.111.222-1', 'RNT-41009', 'Pitalito', 'Cra 4 # 6-10, Centro', '(608) 836 1140', 'info@cafeymontana.co', 'https://cafeymontana.co', '@cafeymontanahuila', 'Turismo agroecológico por fincas cafeteras del sur del Huila.', 2018, 'APROBADA'),
('Raíces del Macizo', '900.444.555-4', 'RNT-41012', 'Garzón', 'Cra 3 # 7-18, Centro', '(608) 833 2210', 'info@raicesdelmacizo.co', 'https://raicesdelmacizo.co', '@raicesdelmacizo', 'Guianza arqueológica certificada por el Parque Arqueológico San Agustín.', 2016, 'APROBADA');

INSERT INTO categorias (nombre, descripcion, color_hex, activa) VALUES
('Astroturismo', 'Observación astronómica y experiencias nocturnas bajo el cielo del Desierto de la Tatacoa.', '#4A4762', TRUE),
('Cultural', 'Recorridos arqueológicos, patrimonio histórico, tradición cafetera y folclor huilense.', '#2E6650', TRUE),
('Aventura', 'Deportes extremos, rafting en el Río Magdalena, senderismo y circuitos 4x4.', '#B84B26', TRUE),
('Ecológico', 'Turismo de naturaleza, aguas termales, avistamiento de aves y parques nacionales.', '#286E85', TRUE);

INSERT INTO planes_turisticos (nombre, agencia_id, municipio_destino, categoria_id, precio, duracion_dias, cupo_maximo, calificacion_promedio, destacado, hotel, transporte, guia, descripcion, itinerario, incluye, no_incluye, activo) VALUES
('Noche de estrellas en la Tatacoa', 1, 'Villavieja - Tatacoa', 1, 180000.00, 1, 15, 4.8, TRUE, 'Hospedaje Cielo Abierto', 'Van 4x4', 'Carlos Perdomo',
 'Una noche completa en el desierto de la Tatacoa, uno de los mejores lugares del planeta para observar estrellas. Recorremos el valle gris al atardecer y terminamos con observación astronómica guiada en el valle rojo.',
 '4:00 pm — Salida desde Neiva hacia Villavieja\n5:30 pm — Caminata fotográfica por el valle gris\n7:00 pm — Cena típica huilense\n8:00 pm — Observación astronómica con telescopio\n10:30 pm — Regreso a Neiva',
 'Transporte ida y vuelta desde Neiva, Guía certificado en astroturismo, Uso de telescopio, Cena típica',
 'Hospedaje, Bebidas alcohólicas, Fotografías profesionales', TRUE),

('Ruta arqueológica San Agustín', 4, 'San Agustín', 2, 320000.00, 2, 20, 4.9, TRUE, 'Finca La Estatua', 'Bus turístico', 'Marta Ome',
 'Dos días recorriendo el Parque Arqueológico de San Agustín, Patrimonio de la Humanidad, con sus estatuas monolíticas, y el cañón del Alto de los Ídolos, incluyendo hospedaje en finca cafetera.',
 'Día 1 — Parque Arqueológico y museo, tarde libre en el pueblo\nDía 2 — Alto de los Ídolos, Salto de Bordones y regreso',
 'Hospedaje 1 noche en finca, Transporte en bus turístico, Guía arqueológico certificado, Entradas a los parques',
 'Alimentación (excepto desayuno), Entradas a atractivos opcionales', TRUE),

('Rafting Río Magdalena', 2, 'Neiva', 3, 150000.00, 1, 12, 4.6, TRUE, 'No aplica', 'Camioneta', 'Julián Rojas',
 'Descenso en balsa por un tramo de rápidos clase II-III del río Magdalena a su paso por Neiva, ideal para quienes buscan adrenalina en un solo día, con todo el equipo de seguridad incluido.',
 '8:00 am — Punto de encuentro y charla de seguridad\n9:00 am — Descenso en balsa (2.5 horas)\n12:00 pm — Almuerzo típico junto al río\n1:30 pm — Regreso',
 'Equipo de seguridad completo, Guía de rafting certificado, Almuerzo, Transporte al punto de partida',
 'Ropa de cambio, Seguro médico adicional', TRUE),

('Ruta del café en Pitalito', 3, 'Pitalito', 2, 210000.00, 1, 15, 4.7, TRUE, 'Finca Cafetera El Roble', 'Van', 'Diana Losada',
 'Recorrido por una finca cafetera tradicional en Pitalito: desde la recolección del grano hasta la taza, con degustación de café de origen y almuerzo campesino.',
 '8:00 am — Salida desde Pitalito\n9:00 am — Recorrido por los cultivos y proceso de beneficio\n11:30 am — Taller de catación\n1:00 pm — Almuerzo campesino\n3:00 pm — Regreso',
 'Transporte a la finca, Guía cafetero, Taller de catación, Almuerzo típico',
 'Compra de café (opcional, no incluida en el precio)', TRUE);
