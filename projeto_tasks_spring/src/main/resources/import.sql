-- Inserção de dados na tabela 'tb_user'
INSERT INTO tb_user (name, email, password) VALUES ('Manoel Gomes', 'Manoel@example.com', '$2a$10$qJsoH1ZqKdsACBQ7c5ZLmeNxiKJVyB1x1Xw5D/MWICR96lp5E6G.O');

-- Inserção de dados na tabela 'tb_role'

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

-- Inserção de dados na tabela 'tb_user_role'
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
