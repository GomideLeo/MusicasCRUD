package maven.app;

import static spark.Spark.*;

import maven.service.MusicaService;

public class Aplicacao {

    private static MusicaService musicaService = new MusicaService();

    public static void main(String[] args) {
        port(6789);

        post("/musica", (request, response) -> musicaService.add(request, response));
        
        get("/musica/:codigo", (request, response) -> musicaService.get(request, response));
        
        get("/musica/update/:codigo", (request, response) -> musicaService.update(request, response));

        get("/musica/delete/:codigo", (request, response) -> musicaService.remove(request, response));

        get("/musica", (request, response) -> musicaService.getAll(request, response));
               
    }
}