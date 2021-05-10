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
(6, 'CEDULA', 'Jeison', 'Ortiz', '123456789', 'jeison@unicauca.edu.co', 381, 'calle 5', 8.0),
(125, 'CEDULA', 'Mayra', 'Castillo', '123456787', 'mayra@unicauca.edu.co', 640, 'calle 5', 8.0),
(126, 'CEDULA', 'Cristian', 'Lopez', '124456788', 'cristian@unicauca.edu.com', 381, 'calle 6', 8.0),
(124, 'CEDULA', 'Hector', 'Meneses', '124456786', 'hector@unicauca.edu.co', 381, 'calle 4', 8.0),
(4, 'CEDULA', 'Isabella', 'Perez', '123456789', 'isabella@unicomfacauca.edu.co', 381, 'calle 5', 8.0),
(123, 'CEDULA', 'Ruben', 'Dorado', '123456785', 'ruben@unicauca.edu.co', 396, 'calle 4', 8.0),
(1000, 'CEDULA', 'Pepe 1', 'Perez', '123456789', 'pepe1@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1001, 'CEDULA', 'Pepe 2', 'Perez', '123456789', 'pepe2@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1002, 'CEDULA', 'Pepe 3', 'Perez', '123456789', 'pepe3@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1003, 'CEDULA', 'Pepe 4', 'Perez', '123456789', 'pepe4@unicomfacauca.edu.co', 1, 'calle 5', 8.0),
(1004, 'CEDULA', 'Pepe 5', 'Perez', '123456789', 'pepe5@unicomfacauca.edu.co', 1, 'calle 5', 8.0);

INSERT INTO referencias_familiares (ref_id, numero_documento, nombres, apellidos, telefono, parentesco)
VALUES
(1, 6, 'Juan', 'Ortiz', '123456789', 'TIO'),
(2, 6, 'Luis', 'Ortiz', '123456789', 'HERMANO'),
(3, 123, 'Andrea', 'Cordoba', '123456789', 'HERMANA'),
(4, 123, 'Juan', 'Cordoba', '123456789', 'PRIMO'),
(5, 123, 'Pepito', 'Cordoba', '123456789', 'PRIMO'),
(6, 123, 'Claudia', 'Cordoba', '123456789', 'PRIMO'),
(7, 123, 'Wilson', 'Cordoba', '123456789', 'PRIMO'),
(8, 123, 'Sofia', 'Cordoba', '123456789', 'PRIMO'),
(9, 123, 'Carla', 'Cordoba', '123456789', 'PRIMO'),
(10, 123, 'Sandra', 'Cordoba', '123456789', 'PRIMO'),
(11, 123, 'Luisa', 'Dorado', '123456789', 'PRIMO'),
(12, 123, 'Dana', 'Cordoba', '123456789', 'PRIMO'),
(13, 123, 'Lupita', 'Cordoba', '123456789', 'PRIMO'),
(14, 125, 'Ana', 'Castillo', '123456789', 'PRIMO');


INSERT INTO referencias_personales (ref_id, numero_documento, nombres, apellidos, telefono)
VALUES
(1, 6, 'Juan', 'Ortiz', '123456789'),
(2, 125, 'Ana', 'Ruiz', '123456789'),
(3, 123, 'Nelson', 'Anacona', '123456789');

INSERT INTO estudios (est_id, numero_documento, nombre_titulo, calificacion, tipo, tiempo, inst_id)
VALUES
(1, 123, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', '4 años', 2),
(2, 125, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', '4 años', 1),
(3, 126, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', '4 años', 2),
(4, 4, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', '4 años', 3),
(5, 124, 'AUXILIAR DE COCINA', 5.0, 'TECNICO', '4 años', 2),
(6, 6, 'AUXILIAR DE COCINA', 5.0, 'PROFESIONAL', '4 años', 1),
(7, 123, 'TECNICO EN COCINA CHINA', 5.0, 'TECNICO', '4 años', 2),
(8, 123, 'INGENIERO DE ALIMENTOS', 5.0, 'PROFESIONAL', '4 años', 3),
(9, 4, 'TECNICO EN MANTENIMIENTO DE ESTUFAS', 5.0, 'TECNICO', '4 años', 2);

INSERT INTO experiencias_laborales (exp_id, numero_documento, cargo, tiempo, calificacion, ee_id)
VALUES
(1, 123, 'COCINERO', 4, 4.0, 1),
(2, 123, 'CHEF', 4, 4.0, 2),
(3, 123, 'BARRENDERO', 4, 4.0, 3),
(4, 125, 'COCINERO', 4, 4.0, 4),
(5, 125, 'MESERO', 4, 4.0, 5),
(6, 125, 'BARRENDERO', 4, 4.0, 1),
(7, 126, 'COCINERO', 4, 4.0, 2),
(8, 126, 'LAVA PLATOS', 4, 4.0, 3),
(9, 126, 'CHEF', 4, 4.0, 4),
(10, 4, 'COCINERO', 4, 4.0, 5),
(11, 4, 'COCINERO', 4, 4.0, 1),
(12, 4, 'COCINERO', 4, 4.0, 2);

