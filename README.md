# Relatório Jasper API

API Spring Boot que gera relatórios em PDF a partir de dados JSON utilizando JasperReports.

## Descrição

Esta aplicação permite gerar relatórios em PDF de forma dinâmica. Ela recebe dados no formato JSON via requisição POST, formata os dados para a estrutura esperada pelo JasperReports, compila e preenche um relatório `.jrxml` em tempo de execução e retorna o PDF gerado como resposta HTTP.

## Funcionalidades

- Receber dados via POST em JSON.
- Formatar o JSON para o padrão que o JasperReports exige.
- Compilar e preencher relatórios `.jrxml` em tempo de execução.
- Exportar o relatório preenchido em PDF.
- Retornar o PDF como resposta HTTP com headers apropriados.

## Endpoint

**POST** `/relatorio/pdf`

- **Request Body:** JSON contendo os dados do relatório.
- **Response:** PDF do relatório gerado.

### Exemplo de Requisição

```bash
curl -X POST http://localhost:8080/relatorio/pdf \
-H "Content-Type: application/json" \
-d '{
  "Nome": "Rodrigo Sousa",
  "Idade": "19",
  "Semestre": 4
}'
```

## Estrutura do Projeto

- `src/main/java/com/example/relatorioapi/RelatorioController.java`: Controlador principal que recebe os dados JSON e gera o PDF.
- `src/main/resources/relatorios/rel.jrxml`: Arquivo de template do JasperReports.
- `formatarJson(String json)`: Método utilitário que formata o JSON recebido.

## Tecnologias

- Java 17+
- Spring Boot
- JasperReports
- Jackson Databind

## Configuração e Execução

1. Clone o repositório:

```bash
git clone https://github.com/rodrigossbjj/relatorio-jasper-api.git
```

2. Entre na pasta do projeto:

```bash
cd relatorio-jasper-api
```

3. Compile e execute o projeto:

```bash
./mvnw spring-boot:run
```

4. Envie requisições POST para `http://localhost:8080/relatorio/pdf` com JSON no corpo.

## Observações

- Certifique-se de ter o arquivo `.jrxml` no caminho correto dentro do classpath (`src/main/resources/relatorios/`).
- Use o `.gitignore` para evitar subir arquivos desnecessários como `target/` ou arquivos de IDE.
- Para autenticação no GitHub, utilize um Personal Access Token (PAT) ao dar push.

## Autor

Rodrigo Sales
