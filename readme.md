<h1 align="center">
 🏍️ Atividade - DW III 🚗
</h1>

#### As atividades estão divididas em branchs neste repositório.
- [Atividade 01 e Atividade 02](https://github.com/GabrielCamargoL/autobots-atvd-web3-fatec/tree/atv_1_2)
- [Atividade 03](https://github.com/GabrielCamargoL/autobots-atvd-web3-fatec/tree/atv_iii)
- [Atividade 04](https://github.com/GabrielCamargoL/autobots-atvd-web3-fatec/tree/atv_iv)
- [Atividade 05](https://github.com/GabrielCamargoL/autobots-atvd-web3-fatec/tree/atv_v)
---
  
# :bookmark_tabs: Sobre o projeto
Aplicação sobre uma loja de peças de automoveis 🚗 🏍️. <BR>

Autobots uma aplicação que serve como base de aprendizado para o mercado de trabalho. Tem toda uma preocupação com o cenário montado, para que o aluno possa se imaginar em uma empresa, exercendo o papel de Desenvolvedor WEB.

Consiste em um microsserviço API RESTful com Java e Spring Boot, realizando CRUD de Usuarios e seus respectivos dados relacionados, empresas, vendas, serviços, mercadorias e veículos.

O conjunto de atividades Autobots também explora a segurança do sistema com implementação de autenticação com Token JWT fornecendo credenciais válidas e autorizações com níveis de perfis de cada usuário.

Alem da Segurança, vimos como executar a comunicação entre serviços, utilizando Rest Template (um dos modos). 

### Explicação da estrutura das pastas

| Pasta                                                     | Definição                                                                                      |
| --------------------------------------------------------- | ---------------------------------------------------------------------------------------------- |
| :open_file_folder: src/main/ .../                          | Arquivos com o código fonte do projeto                          |
| :open_file_folder: src/main/ .../ config                   | Configuração de cors, csrf, geração e validação de Tokens...    |
| :open_file_folder: src/main/ .../ controles                | Arquivos com os métodos de requisição das rotas                 |
| :open_file_folder: src/main/ .../ entidades                | Arquivos para definição das classes de cada entidade            |
| :open_file_folder: src/main/ .../ modelos              | Arquivos para metodos mais especificos, ex: atualizar, Hateoas, etc |
| :open_file_folder: src/main/ .../ repositorios             | Arquivos para utilização do JPA nas entidades do projeto        |
| :open_file_folder: src/main/ .../ servicos                  | Arquivos com conjuntod métodos para limpeza do código          |
| :page_facing_up: src/main/ .../ AutomanagerApplication.java | Arquivo principal do projeto                                   |
| :open_file_folder: src/main/ resources/ | Arquivos para configurações globais do projeto (ex: credenciais em banco de dados) |
| :page_facing_up: pom.xml                                   | Arquivo usado gerenciar as dependencias do projeto com o Maven  |

<BR>