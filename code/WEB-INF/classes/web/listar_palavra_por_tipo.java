package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


class infousuario implements java.io.Serializable
{
   	public String login_usuario;
   	public int codigo_idioma;
	public int codigo_usuario;
    
    	public infousuario(String login_usuario, int codigo_idioma, int codigo_usuario)
	{
        	this.login_usuario = login_usuario;
        	this.codigo_idioma = codigo_idioma;
		this.codigo_usuario = codigo_usuario;
         }
}

public class listar_palavra_por_tipo extends HttpServlet 
{

	private int codigo_usuario;
	private int codigo_primeiro_idioma;
	private int codigo_idioma = 0;
	private String tipo;
	private String senha_usuario;
	private String email_usuario;
	private String funcao;
	private int usuario_ok = 0;



	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";

	public listar_palavra_por_tipo()
	{
	codigo_usuario = 0;
	funcao = "";

	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
	{
	
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();	

		tipo = req.getParameter("tipo");
		funcao = req.getParameter("funcao");


		if (funcao.equals("listar_classe_gramatical"))
		{

    		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			Statement st = con.createStatement();


      			ResultSet rs = st.executeQuery("SELECT palavra, traducao FROM palavra  WHERE codigo_idioma="+User.codigo_idioma+" AND codigo_classe_gramatical="+tipo+"");

			out.println("<html>");
			out.println("<head><title>Lista de palavras</title></head>");
			out.println("<body>");

			out.println("<font size=2><br> <b>Lista de palavras:</b></font><br>");

 			while(rs.next()) 
			{
			out.println("<br>");
			out.println("<font size=2>"+rs.getString("palavra")+" - <i>"+rs.getString("traducao")+"</i></font>");
			} 
			out.println("<br><br><a href='/outroidioma/listar_palavra' TARGET='conteudo'><font size=2>Clique aqui para listar as palavras de outra categoria.</font></a>");
			out.println("</body>");
			out.println("</html>");

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
			out.println("Login: "+User.login_usuario);
			out.println("Codigo user: "+User.codigo_usuario);
    		}




		}

		else if (funcao.equals("listar_topico_especial"))
		{


   		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			Statement st = con.createStatement();


      			ResultSet rs = st.executeQuery("SELECT palavra, traducao FROM palavra  WHERE codigo_idioma="+User.codigo_idioma+" AND codigo_topico_especial="+tipo+"");

			out.println("<html>");
			out.println("<head><title>Lista de palavras</title></head>");
			out.println("<body>");
			out.println("<font size=2><br> <b>Lista de palavras:</b></font><br>");

 			while(rs.next()) 
			{
			out.println("<br>");
			out.println("<font size=2>"+rs.getString("palavra")+" - <i>"+rs.getString("traducao")+"</i></font>");
			} 
			out.println("<br><br><a href='/outroidioma/listar_palavra' TARGET='conteudo'><font size=2>Clique aqui para listar as palavras de outra categoria.</font></a>");

			out.println("</body>");
			out.println("</html>");

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
			out.println("Login: "+User.login_usuario);
			out.println("Codigo user: "+User.codigo_usuario);
    		}


		}
		

 
	}
}

