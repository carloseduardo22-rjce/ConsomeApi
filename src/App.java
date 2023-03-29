import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 séries
        // String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        // ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-06-12&end_date=2022-06-14";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

        var http = new clienteHttp();
        String json = http.buscaDados(url);

        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradorDeFigurinhas();

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = "saida/" + conteudo.getTitulo().replace(":", "-") + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.getTitulo());
            System.out.println();

        }
    }
}