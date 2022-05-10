# Crud de Clientes!

Este projeto engloba o fluxo de trabalho básico para criar um CRUD, não foge das atividades cotidianas de um desenvolvedor back-end: criação da estrutura do projeto, controles, serviços, repositórios e modelos de dados.


*** A API Clientes tem a seguinte configuração: ***

	- Roda na porta: 8080
	- Contexto: /clientes
	- Utilizar jpa/hibernate
	- Utilizar H2 em memória, com o console habilitado

*** Os erros gerados são capturados pelo handler ExceptionsHandler, são eles:  ***

	- 204 para não encontrado
	- 409 – Conflict para dados obrigatórios e inválidos 
	- 500 - Erro inesperado

O modelo de Cliente foi criado com os seguintes possui os seguintes atributos: 

	- Id
	- Nome (tamanho máximo 40 posições, obrigatório)
	- Data de nascimento (válida e obrigatória)
	- CPF (válido e obrigatório)
	- Sexo (obrigatório)

O serviço de provê os seguintes itens:

	- Recuperar todos os clientes, permitindo filtro/ordenação por nome, data de nascimento, CPF e Sexo.
	- Recuperar um cliente por CPF, retornar HttpStatusCode – 204 para não encontrado.
	- Criar um novo Cliente validando os campos nome, data de nascimento, CPF e sexo utilizando BeanValidation.
	- Atualizar um Cliente, validando se o mesmo existe e não permitindo que ele altere o CPF.
	- Remover um Cliente, validando se o mesmo existe.

A *** cobertura de testes ***  nas camadas de serviço e controle estão em *** 91,8% e 95,9% ***  respectivamente


# Testando a API

Para testar essa API, siga os seguintes passos:
 - Baixe o codigo desse repositório na sua máquina.
 -  Abra o prompt de comandos da sua preferencia e navegue para a raiz da pasta deste projeto.
 - Baixe as dependencias e contrua o artefato da API com o comando ```mvn clean install```
- Rode a aplicação a partir do comando ```mvn spring-boot:run```

A partir deste ponto a api estará rodando na port 8080 e você poderá fazer as chamadas abaixo no prompt de comando ou importa-lo para o Postman.

### Criando um cliente
Cria um cliente caso não exista nenhum cliente com o mesmo CPF. Se existir, retorna *** 409 ***
```
curl --location --request POST 'http://localhost:8080/v1/clientes' \
--header 'Content-Type: application/json' \
--data-raw '
    {
        "nome": "Cliente 05",
        "nascimento": "1987-10-01",
        "cpf": "29490848000",
        "sexo": "M"
    }'
```

### Atualizando um cliente
Cria um cliente caso o exista algum cliente com o Id informado. Se não existir, retorna *** 409 ***, o mesmo retorno é enviado caso o usuário tente atualizar o CPF.
```
curl --location --request PUT 'http://localhost:8080/v1/clientes/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Cliente 01",
    "nascimento": "1980-02-12",
    "cpf": "29490848000",
    "sexo": "M"
}'
```

### Listando todos os clientes com filtros, paginação e ordenação
Listagem simples. Você pode filtrar por todos os campos do Cliente, configurar o tamanho da página e a ordenação. Caso não encontre nada, a API retorna *** 204 ***
```
curl --location --request GET 'http://localhost:8080/v1/clientes?nome=Cliente&nascimento=1987-10-01&cpf=29490848000&sexo=M&page=0&size=5'
```

###  Buscando o cliente pelo CPF
Busca simples por CPF. Caso não encontre nada, a API retorna *** 204 ***
```
curl --location --request GET 'http://localhost:8080/v1/clientes/87858371215'
```

###  Deletando um cliente
Deleta um cliente caso exista algum cliente com o Id informado. Se não existir, retorna *** 409 ***.
```
curl --location --request DELETE 'http://localhost:8080/v1/clientes/1'
```
  
## License

MIT

**Free Software, Yeah!**