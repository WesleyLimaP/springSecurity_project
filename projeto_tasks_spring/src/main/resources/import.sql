-- Inserção de dados na tabela 'tb_user'
INSERT INTO tb_user (name, email, password) VALUES ('Manoel Gomes', 'Manoel@example.com', 'Manoel123');
INSERT INTO tb_user (name, email, password) VALUES ('Maria Silva', 'maria@example.com', 'maria123');
INSERT INTO tb_user (name, email, password) VALUES ('João Souza', 'joao@example.com', 'joao123');
INSERT INTO tb_user (name, email, password) VALUES ('Pedro Oliveira', 'pedro@example.com', 'pedro123');
INSERT INTO tb_user (name, email, password) VALUES ('Lúcia Costa', 'lucia@example.com', 'lucia123');

-- Inserção de dados na tabela 'tb_task'
INSERT INTO tb_task (title, description, status, user_id) VALUES ('Finalizar Projeto X', 'Finalizar o desenvolvimento do Projeto X com integração de API', 'CONCLUIDO', (SELECT id FROM tb_user WHERE email = 'maria@example.com'));
INSERT INTO tb_task (title, description, status, user_id) VALUES ('Revisar Código', 'Revisar o código-fonte para assegurar a qualidade', 'EM_ANDAMENTO', (SELECT id FROM tb_user WHERE email = 'joao@example.com'));
INSERT INTO tb_task (title, description, status, user_id) VALUES ('Testar novas funcionalidades', 'Testar as novas funcionalidades adicionadas ao sistema', 'PENDENTE', (SELECT id FROM tb_user WHERE email = 'pedro@example.com'));
INSERT INTO tb_task (title, description, status, user_id) VALUES ('Documentar Projeto', 'Documentar o projeto para o próximo lançamento', 'CONCLUIDO', (SELECT id FROM tb_user WHERE email = 'lucia@example.com'));
INSERT INTO tb_task (title, description, status, user_id) VALUES ('Gerenciar Deployment', 'Gerenciar o processo de deployment para produção', 'PENDENTE', (SELECT id FROM tb_user WHERE email = 'Manoel@example.com'));
