package servletDemo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;


@WebServlet(name="GenericServletDemo",urlPatterns= {"/generic"},
			initParams= {@WebInitParam(name="admin",value="zyy"),
						 @WebInitParam(name="email",value="destinyhill@aliyun.com")})
public class GenericServletDemo extends GenericServlet{
	
	private static final long serialVersionUID = 62500890L;
	
	@Override
	public void service(ServletRequest request,ServletResponse response) throws ServletException,IOException{
		ServletConfig config = getServletConfig();
		String admin = config.getInitParameter("admin");
		String email = config.getInitParameter("email");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print("<html><head></head><body>Admin:"+admin
					+"<br/>Email:"+email
					+"</body></html>");
	}

}
