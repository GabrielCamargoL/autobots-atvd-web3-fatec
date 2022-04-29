<h1 align="center">
 🏍️ Atividade - DW III 🚗
</h1>
  
# :bookmark_tabs: Sobre o projeto
Aplicação sobre uma loja de peças de automoveis 🚗 🏍️. <BR>
Consiste em um microsserviço API RESTful com Java e Spring Boot, realizando criações, listagens, atualizações, deleções de clientes e seus respectivos dados relacionados (atualmente). 

### Explicação da estrutura das pastas

| Pasta                                                     | Definição                                                                                      |
| --------------------------------------------------------- | ---------------------------------------------------------------------------------------------- |
| :open_file_folder: src/main/ .../                          | Arquivos com o código fonte do projeto                          |
| :open_file_folder: src/main/ .../ config  (posteriormente) | Configuração de cors, csrf, etc                                 |
| :open_file_folder: src/main/ .../ controles                | Arquivos com os métodos de requisição das rotas                 |
| :open_file_folder: src/main/ .../ entidades                | Arquivos para definição das classes de cada entidade            |
| :open_file_folder: src/main/ .../ modelos              | Arquivos para metodos mais especificos, ex: atualizar, Hateoas, etc |
| :open_file_folder: src/main/ .../ repositorios             | Arquivo para utilização do JPA nas entidades do projeto         |
| :page_facing_up: src/main/ .../ AutoanagerApplication.java | Arquivo principal do projeto                                    |
| :open_file_folder: src/main/ resources/ | Arquivos para configurações globais do projeto (ex: credenciais em banco de dados) |
| :page_facing_up: pom.xml                                   | Arquivo usado gerenciar as dependencias do projeto com o Maven  |
| :page_facing_up: GabrielCamargo_Autobots_insomnia.json     | Arquivo para importar requests usadas no projeto para o imsonia |

<BR>

Inicie o projeto Spring e se achar conveniente, importar o arquivo "GabrielCamargo_Autobots_insomnia.json" (localizado na raiz do projeto) no insomnia para testar os endpoints organizados. <BR>

>![image](https://user-images.githubusercontent.com/55204419/166070033-e2cb39ae-4e8b-47e9-a690-f2c10b31474c.png)<BR>
>clique na setinha do Dashboard e em seguida em "Import/Export"<BR>

>![image](https://user-images.githubusercontent.com/55204419/166070093-10f59356-55ab-4734-a39c-7015d329badc.png)<BR>
>Selecione From File<BR>



>![image](https://user-images.githubusercontent.com/55204419/166070229-81469961-53a6-4ca3-b69c-f34d4ebf64bf.png)<BR>
>Selecione o arquivo .json na raiz do projeto<BR>