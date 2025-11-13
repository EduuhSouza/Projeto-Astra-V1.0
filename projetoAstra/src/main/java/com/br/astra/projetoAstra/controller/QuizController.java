package com.br.astra.projetoAstra.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*")
public class QuizController {

    @PostMapping("/resultado")
    public Map<String, String> calcularResultado(@RequestBody Map<String, String> respostas) {
        int pomodoro = 0, feynman = 0, spaced = 0, interleaving = 0, activeRecall = 0;

        // Contador baseado nas respostas
        for (String key : respostas.keySet()) {
            String valor = respostas.get(key);
            switch (valor) {
                case "A": pomodoro++; break;
                case "B": feynman++; break;
                case "C": spaced++; break;
                case "D": interleaving++; break;
                case "E": activeRecall++; break;
            }
        }

        String tipo = "";
        String descricao = "";

        int maior = Math.max(pomodoro, Math.max(feynman, Math.max(spaced, Math.max(interleaving, activeRecall))));

        if (maior == pomodoro) {
            tipo = "Técnica Pomodoro️";
            descricao = """
                Você aprende melhor com foco e pausas programadas.
                A Técnica Pomodoro utiliza blocos de 25 minutos de estudo intenso seguidos de breves pausas.
                Essa metodologia treina sua mente para manter concentração profunda e evitar o cansaço cognitivo.
                
                Dica extra: use as pausas para revisar mentalmente o conteúdo ou fazer algo relaxante, mantendo o cérebro ativo, mas não sobrecarregado.
            """;
        } else if (maior == feynman) {
            tipo = "Técnica de Feynman";
            descricao = """
                Você domina quando ensina — literalmente.
                A Técnica de Feynman se baseia em estudar um tema e explicá-lo em voz alta, de forma simples, como se ensinasse a outra pessoa.
                Ao tentar simplificar o conteúdo, você identifica lacunas e consolida o aprendizado.
                
                Dica: crie “mini-aulas” para si mesmo ou grave explicações para rever depois.
            """;
        } else if (maior == spaced) {
            tipo = "Repetição Espaçada";
            descricao = """
                Seu cérebro aprende melhor com revisões distribuídas no tempo.
                A Repetição Espaçada (Spaced Repetition) usa o princípio da curva do esquecimento para revisar o conteúdo no momento exato antes de esquecê-lo.
                
                Dica: utilize aplicativos como Anki ou Mochi para programar revisões automáticas e turbinar a memória de longo prazo.
            """;
        } else if (maior == interleaving) {
            tipo = "Estudo Intercalado";
            descricao = """
                Você aprende melhor variando os temas e desafios.
                O Estudo Intercalado (Interleaving) alterna entre diferentes disciplinas ou tipos de exercício.
                Isso força o cérebro a fazer conexões, aprimora a adaptabilidade e aumenta a retenção.
                
                Dica: estude 2 a 3 temas diferentes no mesmo bloco — seu cérebro ficará mais flexível e criativo.
            """;
        } else {
            tipo = "Active Recall";
            descricao = """
                Seu aprendizado é movido por desafios mentais.
                O Active Recall (Recordação Ativa) consiste em se testar constantemente — sem consultar o material.
                Essa técnica ativa redes neurais de recuperação, fortalecendo a memória e compreensão real.
                
                Dica: feche o material e tente escrever tudo o que lembra. Depois, corrija e revise apenas o que errou.
            """;
        }

        Map<String, String> resultado = new HashMap<>();
        resultado.put("tipo", tipo);
        resultado.put("descricao", descricao);
        return resultado;
    }
}
