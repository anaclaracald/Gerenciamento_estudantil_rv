# Gerenciamento Estudantil

## Descrição
Este projeto tem o objetivo de desenvolver um sistema de **Gerenciamento Estudantil** em **Java**, utilizando a biblioteca **Swing** para a elaboração da interface gráfica. O sistema permite o controle e gerenciamento de dados acadêmicos aplicando conceitos a Programação Orientada a Objetos e armazenamento de dados.

## Tecnologias Utilizadas

Este projeto utiliza as seguintes tecnologias:

- Linguagem **Java**
- Software **MySQL**
- Biblioteca **Swing**

## Funcionalidades

### Tela Inicial
- Exibição da janela principal com as opções:
  - Gerenciar Estudantes
  - Gerenciar Professores
  - Gerenciar Cursos
  - Sair do programa

### Gerenciamento de Estudantes
- **Gerenciar Estudantes:** Cadastro de estudantes com os campos:
  - Nome
  - Idade
  - Matrícula
- **Listar Estudante:** Pesquisa por nome ou matrícula com opções para:
  - **Editar:** Atualizar nome e idade do estudante
  - **Excluir:** Remover o estudante
- Armazenamento dos dados em MySQL

### Gerenciamento de Professores
- **Gerenciar Professores:** Cadastro de professores com os campos:
  - Nome
  - Idade
  - Especialidade
  - ID
- **Listar Professor:** Pesquisa por nome ou ID com opções para:
  - **Editar:** Atualizar nome, especialidade e ID do professor
  - **Excluir:** Remover o professor
- Armazenamento dos dados em MySQL

### Gerenciamento de Cursos 
- **Gerenciar Cursos:** Cadastro de cursos com os campos:
  - Nome do Curso
  - Carga Horária
  - ID do Professor
- **Consultar Curso:** Pesquisa pelo nome do curso ou ID do professor com opções para:
  - **Editar:** Atualizar nome e carga horária do curso
  - **Excluir:** Remover o curso
- **Vinculação:**
  - **Matricular Estudantes** em cursos
  - **Associar Professores** a cursos
- Armazenamento dos dados em MySQL

### Geração de Relatórios
- **Relatório de Estudantes:** Lista de estudantes com as informações armazenadas
- **Relatório de Professores:** Lista de professores com com as informações armazenadas

## Estrutura das Classes

### Pessoa
- **Atributos:**
  - Nome
  - Idade
- **Métodos:**
  - Getters e Setters

### Estudante
- **Atributos:** 
  - Nome
  - Idade
  - Matrícula
- **Métodos:**
  - Getters e Setters

### Professor
- **Atributos:**
  - Nome
  - Idade
  - Especialidade
  - ID
- **Métodos:**
  - Getters e Setters

### Curso
- **Atributos:**
  - ID
  - nomeCurso
  - cargaHoraria
  - Professor
- **Métodos:**
  - Getters e Setters

### JanelaPrincipal 
- Interface Gráfica
- Navegação intuitiva entre os menus utilizando **Swing**

## Licença 

Este projeto está licenciado sob a Licença MIT. Consulte o arquivo LICENSE para mais detalhes.
 
## Contribuidores 

Agradecemos a contribuição de todas nesse projeto.

Autoras:
- Alice Ferreira
- Ana Clara Caldeira
- Bárbara Dantas
