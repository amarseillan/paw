package zonaProp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ErrorFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) arg1;
		try{
			arg2.doFilter(request, resp);
		}catch(Exception e){
			resp.sendRedirect("errorPage");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}