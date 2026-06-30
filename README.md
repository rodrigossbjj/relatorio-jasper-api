# Relatório Jasper API  
API Spring Boot que gera relatórios em PDF a partir de dados JSON utilizando JasperReports.  

## Descrição  
Esta aplicação permite gerar relatórios em PDF de forma dinâmica.  
Ela recebe dados no formato JSON via requisição **POST**, formata os dados para a estrutura esperada pelo JasperReports, compila e preenche um relatório `.jrxml` em tempo de execução e retorna o PDF gerado como resposta HTTP.  

## 🚀 Funcionalidades  
- Receber dados via **POST** em JSON.  
- Definir o relatório a ser utilizado diretamente na URL.  
- Formatar o JSON para o padrão que o JasperReports exige.  
- Compilar e preencher relatórios `.jrxml` em tempo de execução.  
- Exportar o relatório preenchido em **PDF**.  
- Retornar o PDF como resposta HTTP com headers apropriados.  

## 📡 Endpoint  
`POST /relatorio/pdf/{nomeRel}`  

- **Path Variable (`nomeRel`)** → Nome do arquivo `.jrxml` (sem a extensão) que será utilizado para gerar o relatório.  
- **Request Body** → JSON contendo os dados do relatório.  
- **Response** → PDF do relatório gerado.  

### Exemplo de Requisição  
```bash
curl -X POST http://localhost:8080/relatorio/pdf/rel -H "Content-Type: application/json" -d '
{
  "Nome": "Rodrigo Sousa",
  "Idade": "19",
  "Semestre": 4
}'
```  

No exemplo acima, será utilizado o template `rel.jrxml` localizado em `src/main/resources/relatorios/`.  

## 📂 Estrutura do Projeto  
- `src/main/java/com/example/relatorioapi/RelatorioController.java` → Controlador principal que recebe os dados JSON e gera o PDF.  
- `src/main/resources/relatorios/` → Pasta onde ficam armazenados os arquivos de template `.jrxml`.  
- `formatarJson(String json)` → Método utilitário que formata o JSON recebido.  

## 🛠️ Tecnologias  
- Java 21 (via OpenJDK no Docker)  
- Spring Boot  
- JasperReports  
- Jackson Databind  
- Docker  

## 🐳 Execução com Docker  

### 1. Criar a imagem  
Na raiz do projeto, execute:  
```bash
docker build -t relatorio-jasper-api .
```  

### 2. Rodar o contêiner  
```bash
docker run -d -p 8080:8080 --name relatorio-api relatorio-jasper-api
```  

### 3. Testar a API  
Depois que o contêiner estiver rodando, acesse:  
```bash
curl -X POST http://localhost:8080/relatorio/pdf/rel -H "Content-Type: application/json" -d '
{ 
  "Nome": "Rodrigo Sousa", 
  "Idade": "19", 
  "Semestre": 4
}
' --output relatorio.pdf
```  

Isso salvará o **relatorio.pdf** no seu diretório local.  

## 📌 Observações  
- Certifique-se de que o arquivo `.jrxml` esteja no caminho correto dentro do classpath (`src/main/resources/relatorios/`).  
- O valor de `{nomeRel}` deve corresponder exatamente ao nome do arquivo `.jrxml` (sem a extensão).  
- O `.gitignore` já deve incluir `target/` e arquivos de IDE.  
- Caso rode fora do Docker, é possível executar via Maven:  
  ```bash
  ./mvnw spring-boot:run
  ```  

## 👤 Autor  
**Rodrigo Sales**  
