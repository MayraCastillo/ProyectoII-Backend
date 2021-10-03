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

INSERT INTO hojas_vida (numero_documento, tipo_documento, nombres, apellidos, telefono, correo, municipio_id, direccion, calificacion)
VALUES
(1082537976, 'CEDULA', 'Jeison', 'Ortiz', '123456789', 'jeison@unicauca.edu.co', 381, 'calle 5', 8.0),
(1083921948, 'CEDULA', 'Mayra', 'Castillo', '123456787', 'mayra@unicauca.edu.co', 640, 'calle 5', 8.0),
(1083123564, 'CEDULA', 'Cristian', 'Lopez', '124456788', 'cristian@unicauca.edu.com', 381, 'calle 6', 8.0),
(1082483783, 'CEDULA', 'Hector', 'Meneses', '124456786', 'hector@unicauca.edu.co', 381, 'calle 4', 8.0),
(1082759823, 'CEDULA', 'Isabella', 'Perez', '123456789', 'isabella@unicomfacauca.edu.co', 381, 'calle 5', 8.0),
(1083347592, 'CEDULA', 'Ruben', 'Dorado', '123456785', 'ruben@unicauca.edu.co', 396, 'calle 4', 8.0),
(1082323456, 'CEDULA', 'Yurany', 'Guevara', '123456789', 'yurany@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1082323457, 'CEDULA', 'Nicole', 'Urbano', '123456789', 'nicole@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1082458532, 'CEDULA', 'Andrea', 'Angeles', '123456789', 'andrea@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1083224352, 'CEDULA', 'Diana', 'Perdomo', '123456789', 'diana@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1082458533, 'CEDULA', 'Felipe', 'Muñoz', '123456789', 'felipe@unicomfacauca.edu.co', 1, 'calle 5', 8.0);

INSERT INTO referencias_familiares (ref_id, numero_documento, nombres, apellidos, telefono, parentesco)
VALUES
(1, 1082537976, 'Juan', 'Ortiz', '123456789', 'TIO'),
(2, 1082537976, 'Luis', 'Ortiz', '123456789', 'HERMANO'),
(3, 1083347592, 'Andrea', 'Cordoba', '123456789', 'HERMANA'),
(4, 1083347592, 'Juan', 'Cordoba', '123456789', 'PRIMO'),
(5, 1083347592, 'Pepito', 'Cordoba', '123456789', 'PRIMO'),
(6, 1083347592, 'Claudia', 'Cordoba', '123456789', 'PRIMO'),
(7, 1083347592, 'Wilson', 'Cordoba', '123456789', 'PRIMO'),
(8, 1083347592, 'Sofia', 'Cordoba', '123456789', 'PRIMO'),
(9, 1083347592, 'Carla', 'Cordoba', '123456789', 'PRIMO'),
(10, 1083347592, 'Sandra', 'Cordoba', '123456789', 'PRIMO'),
(11, 1083347592, 'Luisa', 'Dorado', '123456789', 'PRIMO'),
(12, 1083347592, 'Dana', 'Cordoba', '123456789', 'PRIMO'),
(13, 1083347592, 'Lupita', 'Cordoba', '123456789', 'PRIMO'),
(14, 1083921948, 'Ana', 'Castillo', '123456789', 'PRIMO');


INSERT INTO referencias_personales (ref_id, numero_documento, nombres, apellidos, telefono)
VALUES
(1, 1082537976, 'Juan', 'Ortiz', '123456789'),
(2, 1083921948, 'Ana', 'Ruiz', '123456789'),
(3, 1083347592, 'Nelson', 'Anacona', '123456789');

INSERT INTO estudios (est_id, numero_documento, nombre_titulo, calificacion, tipo, tiempo, inst_id)
VALUES
(1, 1083347592, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', '4 años', 2),
(2, 1083921948, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', '4 años', 1),
(3, 1083123564, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', '4 años', 2),
(4, 1082759823, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', '4 años', 3),
(5, 1082483783, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', '4 años', 2),
(6, 1082537976, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', '4 años', 1),
(7, 1083347592, 'TECNICO EN COCINA CHINA', 5.0, 'TECNICO', '4 años', 2),
(8, 1083347592, 'INGENIERO DE ALIMENTOS', 5.0, 'PROFESIONAL', '4 años', 3),
(9, 1082759823, 'TECNICO EN MANTENIMIENTO DE ESTUFAS', 5.0, 'TECNICO', '4 años', 2);

INSERT INTO experiencias_laborales (exp_id, numero_documento, cargo, tiempo, calificacion, ee_id)
VALUES
(1, 1083347592, 'COCINERO', 4, 4.0, 1),
(2, 1083347592, 'CHEF', 4, 4.0, 2),
(3, 1083347592, 'BARRENDERO', 4, 4.0, 3),
(4, 1083921948, 'COCINERO', 4, 4.0, 4),
(5, 1083921948, 'MESERO', 4, 4.0, 5),
(6, 1083921948, 'BARRENDERO', 4, 4.0, 1),
(7, 1083123564, 'COCINERO', 4, 4.0, 2),
(8, 1083123564, 'LAVA PLATOS', 4, 4.0, 3),
(9, 1083123564, 'CHEF', 4, 4.0, 4),
(10, 1082759823, 'COCINERO', 4, 4.0, 5),
(11, 1082759823, 'COCINERO', 4, 4.0, 1),
(12, 1082759823, 'COCINERO', 4, 4.0, 2);

