package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class usuario extends HttpServlet 
{

	static private int codigo_usuario = 0;
	private String nome_usuario;
	private String login_usuario;
	private String senha_usuario;
	private String email_usuario;
	private Date data_cadastro;
	private String funcao;
	private int usuario_ok = 0;


	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";

	public usuario()
	{
	codigo_usuario = 0;
	nome_usuario = "Sem_nome";
	login_usuario = "Sem_usuario";
	senha_usuario = "Sem_senha";
	email_usuario = "Sem_email";
//	data_cadastro = 00/00/0000;
	funcao = "";

	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
	{
	
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();	

		nome_usuario = req.getParameter("nome_usuario");
		login_usuario = req.getParameter("login_usuario");
		senha_usuario = req.getParameter("senha_usuario");
		email_usuario = req.getParameter("email_usuario");
		funcao = req.getParameter("funcao");
	
		if (funcao.equals("login"))
		{

  			try 
			{
    			Connection con = null;

			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);
	
			
			String sql = "SELECT login_usuario FROM usuario";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next())
			{	
				if (rs.getString("login_usuario").equals(login_usuario)) usuario_ok = 1;
			}

			if (usuario_ok == 1)
			{
				usuario_ok = 0;			
				sql = "SELECT senha_usuario FROM usuario WHERE login_usuario='" + login_usuario+"'";
				rs = st.executeQuery( sql );

				while( rs.next() ) 
				{
					if ( rs.getString("senha_usuario").equals(senha_usuario) ) out.println("OK jovem!");
					else res.sendRedirect( "/outroidioma/erro_identificacao.jsp" );
				}

			}
			
			else if (usuario_ok == 0) res.sendRedirect( "/outroidioma/erro_usuario.jsp" );
			rs.close();			
			st.close();
			con.close();	
			}
    			catch(ClassNotFoundException e) 
			{ 
      				out.println("Couldn't load database driver: " + e.getMessage());
    			}
    			catch(SQLException e) 
			{ 
      				out.println("SQLException caught: " + e.getMessage());
    			}
		}
		
		else if (funcao.equals("cadastro"))
		{

			if ((nome_usuario == "") || (login_usuario == "") || (senha_usuario == "") || (email_usuario == ""))
			{
				res.sendRedirect( "/outroidioma/erro_cadastro.jsp" );
			}
			else
			{

  			try 
			{
    				codigo_usuario = codigo_usuario + 1;
				Connection con = null;
				Class.forName("org.postgresql.Driver"); 
				con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
				String sql = "INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, email_usuario, data_cadastro) VALUES ('"+nome_usuario+"', '"+login_usuario+"', '"+senha_usuario+"', '"+email_usuario+"', '2010-12-12')";
				Statement st = con.createStatement();
				st.executeUpdate( sql );
				con.close();
				st.close();
			
				res.sendRedirect( "/outroidioma/cadastro_ok.jsp" );
			}	

    			catch(ClassNotFoundException e) 
			{ 
      				out.println("Couldn't load database driver: " + e.getMessage());
    			}
    			catch(SQLException e) 
			{ 
      				
				if (e.getErrorCode() == 0) res.sendRedirect( "/outroidioma/erro_usuario_existente.jsp" );
				else out.println("SQLException caught: " + e.getMessage());

    			}

			}
		}

	}
}

