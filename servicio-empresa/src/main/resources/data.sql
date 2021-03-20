INSERT INTO paises (nombre) VALUES ('Colombia');
INSERT INTO departamentos (pais_id, nombre, codigo) VALUES (1, 'CAUCA', 123);
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (1, 124, 'popayan');
INSERT INTO municipios (dep_id, codigo, nombre) VALUES (1, 124, 'cali');
INSERT INTO tipo_terceros (nombre, abrevicion) VALUES ('Caja de compesacion familiar', 'CCF');
INSERT INTO terceros (mun_id, nombre, direccion, correo, telefono, tipo_tercero_id) VALUES (1, 'tercero', 'Carrera 21A', 'tecero@hotmail.com', '1234', 1);
INSERT INTO bancos (nombre) VALUES ('BBC');
INSERT INTO empleados (numero_documento, mun_id, banco_id, nombres, apellidos, fecha_nacimiento, tipo_documento, telefono, direccion, estado, cuenta_bancaria, tipo_cuenta_bancaria, correo) VALUES (123, 1, 1, 'Ruben', 'Dorado', NOW(), 'CEDULA', '123456789', 'calle 5', 'ACTIVO', '12345', 'AHORROS', 'ruben@unicauca.edu.co');
INSERT INTO empleados (numero_documento, mun_id, banco_id, nombres, apellidos, fecha_nacimiento, tipo_documento, telefono, direccion, estado, cuenta_bancaria, tipo_cuenta_bancaria, correo) VALUES (124, 1, 1, 'Hector', 'meneses', NOW(), 'CEDULA', '124456789', 'calle 4', 'ACTIVO', '1245', 'AHORROS', 'hector@unicauca.edu.co');
INSERT INTO empresas (nit, nombre, telefono, direccion, tipo, frecuencia_pago)
VALUES (123, 'Restaurante PRO', '3124567834', 'Trasv 9 #56 con calle 5', 'tipo', 'MENSUAL');
INSERT INTO empresas (nit, nombre, telefono, direccion, tipo, frecuencia_pago)
VALUES (124, 'Seguros SA', '3124567833', 'Trasv 9 #54 con calle 5', 'tipo', 'MENSUAL');
INSERT INTO tarifas_arl (nivel, cotizacion) VALUES ('Riesgo 1', 0.522); 
INSERT INTO contratos (numero_documento, nit, fecha_inicio_contrato, arl_id, fecha_fin_contrato, 
                        fecha_inicio_prueba, fecha_fin_prueba, tipo, salario_basico, estado)
VALUES(123,123, NOW(), 1 , NOW(), NOW(), NOW(), 'prestacion de servicios',  20000, 'ACTIVO');
