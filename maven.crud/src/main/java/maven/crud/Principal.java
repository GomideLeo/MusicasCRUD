package maven.crud;
import java.util.Scanner;

import maven.dao.MusicDAO;
import maven.model.Musica;

public class Principal {
	public static Scanner sc = new Scanner(System.in);
	
	public static Musica createMusica() {
		Musica musica = null;
		
		System.out.println("Digite o codigo da musica:");
		int codigo = Integer.parseInt(sc.nextLine());
		System.out.println("Digite o nome da musica:");
		String nome = sc.nextLine();
		System.out.println("Digite o artista da musica:");
		String artista = sc.nextLine();
		System.out.println("Digite o album da musica:");
		String album = sc.nextLine();
		System.out.println("Digite a duracao da musica (ms):");
		int duracao = Integer.parseInt(sc.nextLine());
		
		musica = new Musica(codigo, nome, artista, album, duracao);
		
		return musica;
	}
	
	public static Musica updateMusica(Musica musica) {
		
		System.out.println(musica.toString());
		System.out.println("Digite o nome da musica:");
		musica.setNome(sc.nextLine());
		System.out.println("Digite o artista da musica:");
		musica.setArtista(sc.nextLine());
		System.out.println("Digite o album da musica:");
		musica.setAlbum(sc.nextLine());
		System.out.println("Digite a duracao da musica (ms):");
		musica.setDuracao(Integer.parseInt(sc.nextLine()));
		
		return musica;
	}
	
	public static void printMusicas(Musica[] musicas) {
		for (int i = 0; i < musicas.length; i++) {
			System.out.println(musicas[i].toString());
		}
	}
	
	public static void main(String[] args) {
		
		MusicDAO dao = new MusicDAO();
		dao.conectar();
		
		int option = 0;
		int musicaCod = 0;
		Musica musica = null;
		
		do {
			System.out.println(" _________________");
			System.out.println("|                 |");
			System.out.println("|  Menu:          |");
			System.out.println("|  1 - Listar     |");
			System.out.println("|  2 - Inserir    |");
			System.out.println("|  3 - Excluir    |");
			System.out.println("|  4 - Atualizar  |");
			System.out.println("|  5 - Sair       |");
			System.out.println("|_________________|");
			option = Integer.parseInt(sc.nextLine());
			
			switch (option) {
			case 1:
				printMusicas(dao.getMusicas());
				break;
			case 2:
				musica = createMusica();
				dao.inserirMusica(musica);
				break;
			case 3:
				System.out.println("Digite o codigo da musica para excluir (-1 para sair):");
				musicaCod =Integer.parseInt(sc.nextLine());
				if (musicaCod != -1) {
					dao.excluirMusica(musicaCod);
				}
				break;
			case 4:
				System.out.println("Digite o codigo da musica para atualizar (-1 para sair):");
				musicaCod = Integer.parseInt(sc.nextLine());
				if (musicaCod != -1) {
					musica = dao.getMusica(musicaCod);
					musica = updateMusica(musica);
					dao.atualizarMusica(musica);
				}
				break;
			}
			
		} while (option != 5);
		
		dao.close();
	}
}
