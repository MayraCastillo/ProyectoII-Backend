INSERT INTO instituciones_educativas (inst_id, nombre) 
VALUES 
(1, 'Universidad del Cauca'),
(2, 'Servicio nacional de aprendizaje SENA'),
(3, 'Fundación universitaria de Popayan');

INSERT INTO empresas_externas (ee_id, nombre, contacto, telefono)
VALUES
(1, 'Pio Pio', '123456789', '123456789'),
(2, 'Kikos', '123456789', '123456789'),
(3, 'La Cosecha', '123456789', '123456789'),
(4, 'Hotel San Martín', '123456789', '123456789'),
(5, 'Juan Valdez', '123456789', '123456789');

INSERT INTO hojas_vida (numero_documento, tipo_documento, nombres, apellidos, telefono, ciudad, departamento, pais, direccion, calificacion)
VALUES
(6, 'CEDULA', 'Jeison', 'Ortiz', '123456789', 'Popayan', 'Cauca', 'Colombia', 'calle 5', 8.0),
(2, 'CEDULA', 'Mayra', 'Castillo', '123456789', 'Pitalito', 'Huila', 'Colombia', 'calle 5', 8.0),
(3, 'CEDULA', 'Cristian', 'Lopez', '123456789', 'Popayan', 'Cauca', 'Colombia', 'calle 5', 8.0),
(5, 'CEDULA', 'Hector', 'Fabio', '123456789', 'Popayan', 'Cauca', 'Colombia', 'calle 5', 8.0),
(4, 'CEDULA', 'Isabella', 'Peña', '123456789', 'Popayan', 'Cauca', 'Colombia', 'calle 5', 8.0),
(1, 'CEDULA', 'Ruben', 'Dorado', '123456789', 'La Sierra', 'Cauca', 'Colombia', 'calle 5', 8.0);

INSERT INTO referencias_familiares (ref_id, numero_documento, nombres, apellidos, telefono, parentesco)
VALUES
(1, 6, 'Juan', 'Ortiz', '123456789', 'TIO'),
(2, 6, 'Luis', 'Ortiz', '123456789', 'HERMANO'),
(3, 1, 'Andrea', 'Cordoba', '123456789', 'HERMANA');

INSERT INTO referencias_personales (ref_id, numero_documento, nombres, apellidos, telefono)
VALUES
(1, 6, 'Juan', 'Ortiz', '123456789'),
(2, 2, 'Ana', 'Ruiz', '123456789'),
(3, 1, 'Nelson', 'Anacona', '123456789');

INSERT INTO estudios (est_id, numero_documento, nombre_titulo, calificacion, tipo, tiempo, inst_id)
VALUES
(1, 1, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', 4, 2),
(2, 2, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', 4, 1),
(3, 3, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', 4, 2),
(4, 4, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', 4, 3),
(5, 5, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', 4, 2),
(6, 6, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', 4, 1),
(7, 1, 'TECNICO EN COCINA CHINA', 5.0, 'TECNICO', 4, 2),
(8, 1, 'INGENIERO DE ALIMENTOS', 5.0, 'PROFESIONAL', 4, 3),
(9, 4, 'TECNICO EN MANTENIMIENTO DE ESTUFAS', 5.0, 'TECNICO', 4, 2);

INSERT INTO experiencias_laborales (exp_id, numero_documento, cargo, tiempo, calificacion, ee_id)
VALUES
(1, 1, 'COCINERO', 4, 4.0, 1),
(2, 1, 'CHEF', 4, 4.0, 2),
(3, 1, 'BARRENDERO', 4, 4.0, 3),
(4, 2, 'COCINERO', 4, 4.0, 4),
(5, 2, 'MESERO', 4, 4.0, 5),
(6, 2, 'BARRENDERO', 4, 4.0, 1),
(7, 3, 'COCINERO', 4, 4.0, 2),
(8, 3, 'LAVA PLATOS', 4, 4.0, 3),
(9, 3, 'CHEF', 4, 4.0, 4),
(10, 4, 'COCINERO', 4, 4.0, 5),
(11, 4, 'COCINERO', 4, 4.0, 1),
(12, 4, 'COCINERO', 4, 4.0, 2);

