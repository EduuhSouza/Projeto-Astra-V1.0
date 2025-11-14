package com.br.astra.projetoAstra.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FluxogramaController {

    @Value("${openrouter.api.key}")
    private String openRouterApiKey;

    @PostMapping("/gerar-fluxograma")
    public ResponseEntity<String> gerarFluxograma(@RequestBody Map<String, String> body) {
        try {
            String tema = body.get("tema");
            String prompt = "Gere um fluxograma em MermaidJS para o seguinte tema: " + tema;

            String apiUrl = "https://openrouter.ai/api/v1/chat/completions";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openRouterApiKey);

            // Montagem do corpo da requisição para OpenRouter
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "openai/gpt-4o-mini");
            requestBody.put("messages", new Object[]{
                    Map.of("role", "user", "content", prompt)
            });

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Chamada da API OpenRouter
            Map response = restTemplate.postForObject(apiUrl, request, Map.class);

            // Pegar o conteúdo do texto gerado no response (depende da estrutura da API)
            // Geralmente está em choices[0].message.content
            String conteudo = "";

            if (response != null && response.containsKey("choices")) {
                Object[] choices = ((java.util.List<Object>) response.get("choices")).toArray();
                if (choices.length > 0) {
                    Map choice0 = (Map) choices[0];
                    Map message = (Map) choice0.get("message");
                    conteudo = (String) message.get("content");
                }
            }

            return ResponseEntity.ok(conteudo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao gerar fluxograma");
        }
    }
}
