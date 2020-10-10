package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletUtils {
	public static boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}
	

	public static boolean isEmpty(Long l) {
		return l == null;
	}

	public static boolean isEmpty(String... inputs) {
		for (String input : inputs)
			if (isEmpty(input))
				return true;
		return false;
	}

	public static Float safeFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static Long safeLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String safeGetFromList(List<String> list, int i) {
		if (i >= 0 && i < list.size())
			return list.get(i);
		return "";
	}

	public static String DB_ERROR = "<h4>Database Error!</h4> Something went wrong, please try again.";
	public static String PARAM_ERROR = "<h4>Parameters Error!</h4> Some of the required parameters are empty or invalid, please try again.";
	public static String ACCESS_ERROR = "<h4>Forbidden!</h4> You cant access or edit this ";

	public static void forwardWithAlert(ServletRequest request, ServletResponse response, String url) throws ServletException, IOException {
		request.getRequestDispatcher(url).forward(request, response);
	}

	public static void forwardWithAlert(ServletRequest request, ServletResponse response, String url, String alertMsg) throws ServletException, IOException {
		request.setAttribute("alertMsg", alertMsg);
		request.getRequestDispatcher(url).forward(request, response);
	}

	public static void forwardWithAlert(ServletRequest request, ServletResponse response, String url, String alertMsg, String alertType) throws ServletException, IOException {
		request.setAttribute("alertMsg", alertMsg);
		request.setAttribute("alertType", alertType);
		request.getRequestDispatcher(url).forward(request, response);
	}
}
