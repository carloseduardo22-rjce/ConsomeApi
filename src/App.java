import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 séries
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados

        for (Map<String, String> filme : listaDeFilmes) {
            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            InputStream inputStream = new URL(urlImagem).openStream();

            String nomeArquivo = titulo + ".png";

            var geradora = new GeradorDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);

            System.out.println(titulo);
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int) classificacao;
            for (int n = 1; n <= numeroEstrelinhas; n++) {
                System.out.print("⭐");
                if (numeroEstrelinhas < 6) {
                    for (int j = 1; j <= numeroEstrelinhas; j++) {
                        System.out.println("👎");
                    }
                }
            }
            System.out.println("\n");
        }
    }
}