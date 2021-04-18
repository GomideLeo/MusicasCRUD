package maven.service;

import maven.dao.MusicDAO;
import maven.model.Musica;
import spark.Request;
import spark.Response;

public class MusicaService {
    private MusicDAO musicaDAO;

    public MusicaService() {
		try {
			musicaDAO = new MusicDAO();
		    musicaDAO.conectar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    public Object add(Request request, Response response) {
        String nome = request.queryParams("nome");
        String artista = request.queryParams("artista");
        String album = request.queryParams("album");
        int duracao = Integer.parseInt(request.queryParams("duracao"));
        
        int id = musicaDAO.getMaxId() + 1;
        
        Musica musica = new Musica(id, nome, artista, album, duracao);

        musicaDAO.inserirMusica(musica);

        response.status(201); // 201 Created
        return id;
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":codigo"));

        Musica musica = (Musica) musicaDAO.getMusica(id);

        if (musica != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<musica>\n" + "\t<id>" + musica.getCodigo() + "</id>\n" + "\t<nome>" + musica.getNome()
                    + "</nome>\n" + "\t<artista>" + musica.getArtista() + "</artista>\n" + "\t<album>"
                    + musica.getAlbum() + "</album>\n" + "\t<duracao>" + musica.getDuracao()
                    + "</duracao>\n" + "</musica>\n";
        } else {
            response.status(404); // 404 Not found
            return "Musica " + id + " não encontrado.";
        }

    }

    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":codigo"));

        Musica musica = (Musica) musicaDAO.getMusica(id);
        
        if (musica != null) {
            musica.setNome(request.queryParams("nome"));
            musica.setAlbum(request.queryParams("album"));
            musica.setArtista(request.queryParams("artista"));
            musica.setDuracao(Integer.parseInt(request.queryParams("duracao")));

            musicaDAO.atualizarMusica(musica);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Musica não encontrado.";
        }

    }

    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":codigo"));

        Musica musica = (Musica) musicaDAO.getMusica(id);

        if (musica != null) {

            musicaDAO.excluirMusica(musica.getCodigo());

            response.status(200); // success
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Musica não encontrado.";
        }
    }

    public Object getAll(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<musicas type=\"array\">");
        for (Musica musica : musicaDAO.getMusicas()) {
            returnValue.append("<musica>\n" + "\t<id>" + musica.getCodigo() + "</id>\n" + "\t<nome>" + musica.getNome()
                    + "</nome>\n" + "\t<artista>" + musica.getArtista() + "</artista>\n" + "\t<album>"
                    + musica.getAlbum() + "</album>\n" + "\t<duracao>" + musica.getDuracao()
                    + "</duracao>\n" + "</musica>\n");
        }
        returnValue.append("</musicas>");
        response.header("Content-Type", "application/xml");
        response.header("Content-Encoding", "UTF-8");
        return returnValue.toString();
    }
}
