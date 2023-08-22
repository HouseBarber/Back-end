INSERT INTO barbearia.roles (id, active, created_by, created_date, last_modified_by, last_modified_date, description, name, role) VALUES (1, true, 'Rachid', '2023-02-10 14:11:07', 'Rachid', '2023-02-10 14:11:10', 'Role de Administrador', 'Admin', 'BARBEARIA_ROLE_ADMIN');
INSERT INTO barbearia.roles (id, active, created_by, created_date, last_modified_by, last_modified_date, description, name, role) VALUES (2, true, 'Rachid', '2023-02-10 14:11:07', 'Rachid', '2023-02-10 14:11:10', 'Role de Barbeiro', 'Barbeiro', 'BARBEARIA_ROLE_BARBEIRO');
INSERT INTO barbearia.roles (id, active, created_by, created_date, last_modified_by, last_modified_date, description, name, role) VALUES (3, true, 'Rachid', '2023-02-10 14:11:07', 'Rachid', '2023-02-10 14:11:10', 'Role de Cliente', 'Cliente', 'BARBEARIA_ROLE_CLIENTE');

INSERT INTO barbearia.user_auth (id, active, created_by, created_date, last_modified_by, last_modified_date, email, name, password, username) VALUES (1, true, 'Rachid', '2023-02-10 14:09:35', 'Rachid', '2023-02-10 14:09:41', 'lucasrachid@hotmail.com', 'Lucas Rachid Martins', '$2a$12$5f.SKK0SWXOogaIwCM0.POoTG7scBke0TPeiPBuI12WHXRE2vUI1u', 'admin');
INSERT INTO barbearia.user_auth (id, active, created_by, created_date, last_modified_by, last_modified_date, email, name, password, username) VALUES (2, true, 'Vinicius', '2023-02-10 14:09:35', 'Vinicius', '2023-02-10 14:09:41', 'viniciusnakahara@gmail.com', 'Vinicius Koiti Nakahara', '', 'dev');

INSERT INTO barbearia.user_roles (users_id, roles_id) VALUES (1, 1);
INSERT INTO barbearia.user_roles (users_id, roles_id) VALUES (2,2);
