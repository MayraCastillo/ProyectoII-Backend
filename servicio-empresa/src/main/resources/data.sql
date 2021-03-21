INSERT INTO paises (nombre) VALUES ('Colombia');
INSERT INTO departamentos (pais_id, nombre, codigo) VALUES (1, 'Antioquia', 123);
INSERT INTO departamentos (pais_id, nombre, codigo) VALUES (1, 'Amazonas', 124);
INSERT INTO departamentos (pais_id, nombre, codigo) VALUES (1, 'Cauca', 125);

INSERT INTO municipios (dep_id, codigo, nombre) VALUES (1, 123, 'Caceres');
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (1, 124, 'Caucasia');
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (1, 125, 'El bagre');

INSERT INTO municipios (dep_id, codigo, nombre) VALUES (2, 126, 'Leticia');
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (2, 127, 'El encanto');
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (2, 128, 'La chorrera');

INSERT INTO municipios (dep_id, codigo, nombre) VALUES (3, 129, 'popayan');
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (3, 130, 'El bordo');
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (3, 131, 'Santander de quilichao');

INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Empresa de salud', 'EPS');
INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Administradora de fondos de pensiones y de cesantías', 'AFP');
INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Administradora de riesgos laborales', 'ARL');
INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Caja de compesacion familiar', 'CCF');
INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Servicio nacional de aprendizaje', 'SENA');
INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Institulo colombiano de bienestar familiar', 'ICBF');
INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Escuela superior de administracion publica', 'ESAP');

INSERT INTO terceros (mun_id, nombre, direccion, correo, telefono, tipo_tercero_id) VALUES (1, 'tercero', 'Carrera 21A', 'tecero@hotmail.com', '1234', 1);

INSERT INTO bancos (nombre) VALUES ('BBC');

INSERT INTO empleados (numero_documento, mun_id, banco_id, nombres, apellidos, fecha_nacimiento, tipo_documento, telefono, direccion, estado,  correo) VALUES (123, 1, 1, 'Ruben', 'Dorado', NOW(), 'CEDULA', '123456789', 'calle 5', 'ACTIVO', 'ruben@unicauca.edu.co');
INSERT INTO empleados (numero_documento, mun_id, banco_id, nombres, apellidos, fecha_nacimiento, tipo_documento, telefono, direccion, estado, correo) VALUES (124, 1, 1, 'Hector', 'meneses', NOW(), 'CEDULA', '124456789', 'calle 4', 'ACTIVO', 'hector@unicauca.edu.co');

INSERT INTO empresas (nit, nombre, telefono, direccion, tipo, frecuencia_pago) VALUES (123, 'Restaurante PRO', '3124567834', 'Trasv 9 #56 con calle 5', 'tipo', 'MENSUAL');
INSERT INTO empresas (nit, nombre, telefono, direccion, tipo, frecuencia_pago) VALUES (124, 'Seguros SA', '3124567833', 'Trasv 9 #54 con calle 5', 'tipo', 'MENSUAL');

INSERT INTO tarifas_arl (nivel, cotizacion) VALUES ('Riesgo 1', 0.522);
INSERT INTO tarifas_arl (nivel, cotizacion) VALUES ('Riesgo 2', 1.044);
INSERT INTO tarifas_arl (nivel, cotizacion) VALUES ('Riesgo 3', 2.436);
INSERT INTO tarifas_arl (nivel, cotizacion) VALUES ('Riesgo 4', 4.350);
 
INSERT INTO contratos (numero_documento, nit, fecha_inicio_contrato, arl_id, fecha_fin_contrato, 
                        fecha_inicio_prueba, fecha_fin_prueba, tipo, salario_basico, estado, comisiones, auxilio_extra)
VALUES(123,123, NOW(), 1 , NOW(), NOW(), NOW(), 'prestacion de servicios',  20000, 'ACTIVO', 100000, 50000);
