# HireConnect

HireConnect é uma plataforma projetada para conectar empresas e freelancers de forma rápida, eficiente e segura. O sistema permite que empresas publiquem vagas, gerenciem seus departamentos e contratem freelancers qualificados, enquanto os freelancers têm a oportunidade de se candidatar a vagas que atendam às suas especializações, tudo em um ambiente intuitivo e integrado.

---

## 📌 Funcionalidades

- Cadastro e autenticação de usuários (EMPRESA, FREELANCER e ADMIN);
- Gerenciamento de empresas e departamentos;
- Publicação de vagas e candidatura de freelancers;
- Aceitação e rejeição de candidaturas;
- Criação automática de contratos entre freelancers e empresas;
- Controle de permissões baseado em papéis.

---

## 🚀 Como rodar o projeto localmente

### 1️⃣ Clonar o repositório

```sh
git clone https://github.com/seu-usuario/hireconnect.git
cd hireconnect
```

### 2️⃣ Configurar o banco de dados

O projeto utiliza **PostgreSQL** como banco de dados. Certifique-se de que ele esteja instalado e rodando na sua máquina.

Crie um banco de dados:

```sql
CREATE DATABASE hireconnect;
```

### 3️⃣ Configurar variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto e adicione as seguintes configurações:

```env
DB_URL=jdbc:postgresql://localhost:5432/hireconnect
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta
```

### 4️⃣ Instalar as dependências

```sh
./mvnw clean install
```

### 5️⃣ Rodar a aplicação

```sh
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 📖 Documentação da API

A documentação da API foi escrita em **Obsidian** e pode ser clonada a partir do repositório:

```sh
https: https://github.com/HericlesSouza/Hire-Connect-Doc.git
ssh: git@github.com:HericlesSouza/Hire-Connect-Doc.git
```

Os arquivos estão em **Markdown**, permitindo a leitura tanto no Obsidian quanto em outros editores compatíveis com Markdown.

🔗 [Baixe o Obsidian aqui](https://obsidian.md/)

---

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.0** (Spring Security, Spring Data JPA, Spring Validation)
- **PostgreSQL**
- **Flyway para versionamento do banco de dados**
- **JWT para autenticação**
- **Lombok para redução de boilerplate code**
- **ModelMapper para conversão de objetos**

### 🏗️ Arquitetura do Projeto

O projeto segue princípios modernos de desenvolvimento:

- **SOLID**: Garantindo código mais modular e reutilizável.
- **Clean Architecture**: Separação clara entre camadas (Core, Data, Infra e Presentation).
- **Clean Code**: Código limpo e legível para facilitar a manutenção e evolução do sistema.

---

## 🔄 Importação de Rotas no Insomnia ou Postman

Para facilitar os testes da API, você pode importar todas as rotas diretamente no **Insomnia** ou no **Postman**.

📥 **Baixar export do Insomnia:** [Insomnia_Export.json](./InsomniaJSON.json)

---

## 📩 Contato

Caso tenha dúvidas ou sugestões, entre em contato:
- **Email:** [hericlessouza01@gmail.com](mailto:hericlessouza01@gmail.com)
- **LinkedIn:** [Hericles Souza](https://www.linkedin.com/in/hericlessouza/)

🚀 **HireConnect - Conectando Empresas e Freelancers!**