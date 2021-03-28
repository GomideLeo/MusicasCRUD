package maven.crud;
import java.sql.*;

public class DAO {
	private Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexao efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexao NAO efetuada com o postgres -- Driver nao encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexao NAO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirMusica(Musica musica) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO musica (codigo, nome, artista, album, duracao) "
					       + "VALUES ("+musica.getCodigo()+ ", '" + musica.getNome() + "', '"  
					       + musica.getArtista() + "', '" + musica.getAlbum() + "', '"
					       + musica.getDuracao() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarMusica(Musica musica) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE musica SET nome = '" + musica.getNome() + "', artista = '"  
				       + musica.getArtista() + "', album = '" + musica.getAlbum() + "'"
					   + ", duracao = '" + musica.getDuracao() + "'" +
					   " WHERE codigo = "+ musica.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirMusica(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM musica WHERE codigo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Musica[] getMusicas() {
		Musica[] musicas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM musica");		
	         if(rs.next()){
	             rs.last();
	             musicas = new Musica[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	            	 musicas[i] = new Musica(rs.getInt("codigo"), rs.getString("nome"), 
	                		                  rs.getString("artista"), rs.getString("album"),
	                		                  rs.getInt("duracao"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return musicas;
	}
	
	public Musica getMusica(int codigo) {
		Musica musica = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM musica WHERE codigo ="+ codigo);
	         if(rs.next()) {
	        	 musica = new Musica(rs.getInt("codigo"), rs.getString("nome"), 
               			rs.getString("artista"), rs.getString("album"),
               			rs.getInt("duracao"));
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return musica;
	}
}