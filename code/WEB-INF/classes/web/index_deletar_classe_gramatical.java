package web;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*; 

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


public class index_deletar_classe_gramatical extends HttpServlet
{
	
	private int codigo_classe_gramatical;
	private String nome_classe_gramatical;
	

	private String endereco_db = "jdbc:postgresql://localhost:5432/outroidioma";
	private String usuario_db = "postgres";
	private String senha_db = "teste";

	public void service( HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException  
	{
		HttpSession sessao = req.getSession( true );
		infousuario User = (infousuario) sessao.getAttribute("infousuario");  
		java.io.PrintWriter out = res.getWriter();

    		try 
		{
			Connection con = null;
			Class.forName("org.postgresql.Driver"); 
			con = DriverManager.getConnection(endereco_db, usuario_db, senha_db);	
			Statement st = con.createStatement();

      			ResultSet rs = st.executeQuery("SELECT codigo_classe_gramatical, nome_classe_gramatical, codigo_idioma FROM classegramatical WHERE codigo_idioma="+User.codigo_idioma+"");

			out.println("<html><head>");
			out.println("<meta content='text/html; charset=ISO-8859-1' http-equiv='content-type'>");
			out.println("<title>Classe gramatical</title></head>");
			out.println("<body>");
			out.println("<br><font size=2><b>Selecionar a classe gramatical que deseja deletar: </b></font><br><br>");
			out.println("<form method='post' action='deleta_classe_gramatical' name='codigo_classe_gramatical'>");

    			while(rs.next()) 
			{

			out.println("<input name='codigo_classe_gramatical' value='"+rs.getString("codigo_classe_gramatical")+"' type='radio'> <font size=2>"+rs.getString("nome_classe_gramatical")+"</font><br> ");


			} 
			out.println("<br><button value='index_para_deletar_classe_gramatical' name='Deletar'>Deletar</button>");
			out.println("</form>");

			out.println("</body></html>");
			
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

