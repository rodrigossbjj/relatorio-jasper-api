package com.example.relatorioapi;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {
    // Recebe Json no corpo da requisição via POST
    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> gerarRelatorioPdfComJsonArquivo(@RequestBody String json) throws JRException, IOException {
        // Formata o Json para o formato correto que o Jasper espera receber
        String jsonFormatado = formatarJson(json);

        InputStream jsonStream = new ByteArrayInputStream(jsonFormatado.getBytes(StandardCharsets.UTF_8));

        JsonDataSource dataSource = new JsonDataSource(jsonStream, "dados");

        // Parâmetros do relatório
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ReportTitle", "Relatório de Vendas");

        // Carregar o arquivo .jrxml do classpath
        InputStream jrxmlStream = getClass().getResourceAsStream("/relatorios/rel.jrxml");
        if (jrxmlStream == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Compilar o relatório em tempo de execução
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        // Preencher o relatório
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Exportar para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        // Preparar retorno HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("relatorio.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    public static String formatarJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (json == null || json.isEmpty()) {
                return "{\"dados\":[]}";
            }

            JsonNode node = mapper.readTree(json);
            ObjectNode root = mapper.createObjectNode();
            ArrayNode dadosArray = mapper.createArrayNode();

            if (node.isArray()) {
                node.forEach(dadosArray::add);
            } else if (node.isObject()) {
                dadosArray.add(node);
            } else {
                return "{\"dados\":[]}";
            }

            root.set("dados", dadosArray);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"dados\":[]}";
        }
    }
}