import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    private static final int PORTA = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(PORTA), 0);
        servidor.createContext("/", Servidor::paginaInicial);
        servidor.createContext("/api/executar", Servidor::executar);
        servidor.start();
        System.out.println("Servidor rodando em http://localhost:" + PORTA);
    }

    // Entrega o arquivo web/index.html
    private static void paginaInicial(HttpExchange troca) throws IOException {
        Path arquivo = Paths.get("web", "index.html");
        if (!Files.exists(arquivo)) {
            arquivo = Paths.get("..", "web", "index.html");
        }
        if (!Files.exists(arquivo)) {
            responder(troca, 404, "text/plain", "Arquivo web/index.html nao encontrado.");
            return;
        }
        responder(troca, 200, "text/html", new String(Files.readAllBytes(arquivo), StandardCharsets.UTF_8));
    }

    // Recebe /api/executar?algoritmo=primo&valor=7 e devolve o resultado em texto
    private static void executar(HttpExchange troca) throws IOException {
        Map<String, String> parametros = lerParametros(troca.getRequestURI().getRawQuery());
        String algoritmo = parametros.getOrDefault("algoritmo", "");
        String valor = parametros.getOrDefault("valor", "").trim();

        if (valor.isEmpty()) {
            responder(troca, 400, "text/plain", "Por favor, digite algum valor!");
            return;
        }

        try {
            String resultado;
            switch (algoritmo) {
                case "primo":
                    int numPrimo = lerInteiro(valor);
                    resultado = numPrimo + " e primo? " + (Primo.verificar(numPrimo) ? "Sim" : "Nao");
                    break;
                case "somatorio":
                    resultado = "Soma: " + Somatorio.calcular(lerArray(valor));
                    break;
                case "fibonacci":
                    int numFibo = lerInteiro(valor);
                    if (numFibo > 46) {
                        throw new IllegalArgumentException("Digite no maximo 46 termos!");
                    }
                    resultado = "Termos: " + juntar(Fibonacci.gerar(numFibo));
                    break;
                case "mdc":
                    int[] arrMdc = lerArray(valor);
                    if (arrMdc.length != 2) {
                        throw new IllegalArgumentException("Digite dois numeros separados por virgula (ex: 48,18)!");
                    }
                    resultado = "MDC: " + Mdc.calcular(arrMdc[0], arrMdc[1]);
                    break;
                case "quicksort":
                    int[] arrQuick = lerArray(valor);
                    Quicksort.ordenar(arrQuick);
                    resultado = "Ordenado: [" + juntar(arrQuick) + "]";
                    break;
                case "contagem":
                    int nContagem = lerInteiro(valor);
                    int[] dadosBase = {5, 8, 12, 18, 20};
                    resultado = "Quantidade entre " + dadosBase[0] + " e " + nContagem + ": "
                            + Contagem.contar(dadosBase, nContagem);
                    break;
                default:
                    throw new IllegalArgumentException("Algoritmo desconhecido!");
            }
            responder(troca, 200, "text/plain", resultado);
        } catch (IllegalArgumentException e) {
            responder(troca, 400, "text/plain", e.getMessage());
        }
    }

    private static int lerInteiro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite apenas numeros inteiros!");
        }
    }

    private static int[] lerArray(String texto) {
        String[] partes = texto.split(",");
        int[] numeros = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            try {
                numeros[i] = Integer.parseInt(partes[i].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Digite apenas numeros inteiros separados por virgula!");
            }
        }
        return numeros;
    }

    private static String juntar(int[] numeros) {
        String texto = Arrays.toString(numeros);
        return texto.substring(1, texto.length() - 1);
    }

    private static Map<String, String> lerParametros(String query) {
        Map<String, String> parametros = new HashMap<>();
        if (query == null) return parametros;
        for (String par : query.split("&")) {
            String[] chaveValor = par.split("=", 2);
            String chave = URLDecoder.decode(chaveValor[0], StandardCharsets.UTF_8);
            String valor = chaveValor.length > 1 ? URLDecoder.decode(chaveValor[1], StandardCharsets.UTF_8) : "";
            parametros.put(chave, valor);
        }
        return parametros;
    }

    private static void responder(HttpExchange troca, int status, String tipo, String corpo) throws IOException {
        byte[] bytes = corpo.getBytes(StandardCharsets.UTF_8);
        troca.getResponseHeaders().set("Content-Type", tipo + "; charset=UTF-8");
        troca.sendResponseHeaders(status, bytes.length);
        try (OutputStream saida = troca.getResponseBody()) {
            saida.write(bytes);
        }
    }
}
