package maven.crud;

public class Musica {
	private int codigo;
	private String nome;
	private String artista;
	private String album;
	private int duracao;
	
	public Musica() {
		this.codigo = -1;
		this.nome = "";
		this.artista = "";
		this.album = "";
		this.duracao = 0;
	}

	public Musica(int codigo, String nome, String artista, String album, int duracao) {
		this.codigo = codigo;
		this.nome = nome;
		this.artista = artista;
		this.album = album;
		this.duracao = duracao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	@Override
	public String toString() {
		return "Musica [codigo=" + codigo + ", nome=" + nome + ", artista=" + artista + ", album=" + album +", duracao=" + duracao + "ms]";
	}
	
}
