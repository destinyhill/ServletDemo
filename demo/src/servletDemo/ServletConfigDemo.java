package servletDemo;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="ServletConfigDemo",urlPatterns= {"/servletConfigDemo"},initParams=
			{@WebInitParam(name="admin",value="zyy"),@WebInitParam(name="email",value="destinyhill@aliyun.com")})
public class ServletConfigDemo implements Servlet {
	private transient ServletConfig servletConfig;

	@Override
	public ServletConfig getServletConfig() {
		return servletConfig;
	}
	
	@Override
	public void init(ServletConfig servletConfig)throws ServletException{
		this.servletConfig = servletConfig;
	}
	
	@Override
	public void service(ServletRequest request,ServletResponse response) throws ServletException,IOException{
		ServletConfig servletConfig = getServletConfig();
		String admin = servletConfig.getInitParameter("admin");
		String email = servletConfig.getInitParameter("email");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print("<html><head></head><body>"
				+"Admin:"+admin
				+"<br/>email:"+email
				+"</body></html>");		
	}
	
	@Override
	public String getServletInfo() {
		return "ServletConfig demo";
	}
	
	@Override
	public void destroy() {		
	}
}
