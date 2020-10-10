package filters;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class DisableCache implements Filter {
	private static final String[] CACHEABLE_PATHS = new String[] {"/css", "/scripts", "/js", "/images", "/fonts", "/scss"};

	private static boolean isCacheAllowed(String path) {
		path.toLowerCase();
		
		for (String p : CACHEABLE_PATHS)
			if (path.startsWith(p))
				return true;
		
		return false;
	}

	private static void disableCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		if (!isCacheAllowed(request.getServletPath()))
			disableCache(response);
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		chain.doFilter(req, res);
	}

}
