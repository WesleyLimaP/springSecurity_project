# springSecurity_project
pequeno projeto de estudo para por em pratica meus conhecimentos em Spring

DETALHES DO PROJETO:

----------------------------------------------
### **Projeto: Sistema de Gestão de Tarefas**

### **Descrição do Projeto**

O objetivo é criar uma API RESTful para gerenciar tarefas. O sistema permitirá que usuários se cadastrem, façam login, criem tarefas, atualizem seu status e excluam tarefas. As operações de gerenciamento de tarefas serão protegidas por autenticação e autorização.

---

### **Funcionalidades**

1. **Autenticação e Registro de Usuários**:
    - Cadastro de usuários com email, senha e nome.
    - Login utilizando email e senha.
    - Senhas devem ser armazenadas criptografadas.
2. **Gerenciamento de Tarefas**:
    - Criar tarefas com título, descrição e status (PENDENTE, EM ANDAMENTO, CONCLUÍDO).
    - Listar todas as tarefas do usuário autenticado.
    - Atualizar o status de uma tarefa.
    - Excluir uma tarefa.
3. **Autorização**:
    - Apenas usuários autenticados podem gerenciar suas próprias tarefas.
    - Um administrador pode visualizar todas as tarefas.


---

### **Estrutura do Banco de Dados**

1. **Usuário (`User`)**
    - `id` (Long)
    - `name` (String)
    - `email` (String, único)
    - `password` (String, criptografado)
    - `role` (String, ADMIN ou USER)
2. **Tarefa (`Task`)**
    - `id` (Long)
    - `title` (String)
    - `description` (String)
    - `status` (String, valores possíveis: PENDENTE, EM ANDAMENTO, CONCLUÍDO)
    - `user_id` (Long, relacionamento com User)

---

### **Endpoints**

1. **Autenticação**
    - `POST /api/auth/register` - Registro de um novo usuário.
    - `POST /api/auth/login` - Login de usuário (retorna token JWT).
2. **Tarefas**
    - `GET /api/tasks` - Lista as tarefas do usuário autenticado.
    - `POST /api/tasks` - Cria uma nova tarefa.
    - `PUT /api/tasks/{id}` - Atualiza o status de uma tarefa.
    - `DELETE /api/tasks/{id}` - Exclui uma tarefa.
3. **Admin**
    - `GET /api/admin/tasks` - Lista todas as tarefas (somente para ADMIN).

---
