CREATE TABLE roles (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   role VARCHAR(50) NOT NULL,
   active BOOLEAN NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP NOT NULL,
   last_modified_by VARCHAR(255) NOT NULL,
   last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO barbearia.roles (id, active, created_by, created_date, last_modified_by, last_modified_date, description, name, role) VALUES (1, true, 'Rachid', '2023-02-10 14:11:07', 'Rachid', '2023-02-10 14:11:10', 'Role de Administrador', 'Admin', 'BARBEARIA_ROLE_ADMIN');
INSERT INTO barbearia.roles (id, active, created_by, created_date, last_modified_by, last_modified_date, description, name, role) VALUES (2, true, 'Rachid', '2023-02-10 14:11:07', 'Rachid', '2023-02-10 14:11:10', 'Role de Barbeiro', 'Barbeiro', 'BARBEARIA_ROLE_BARBEIRO');
INSERT INTO barbearia.roles (id, active, created_by, created_date, last_modified_by, last_modified_date, description, name, role) VALUES (3, true, 'Rachid', '2023-02-10 14:11:07', 'Rachid', '2023-02-10 14:11:10', 'Role de Cliente', 'Cliente', 'BARBEARIA_ROLE_CLIENTE');
