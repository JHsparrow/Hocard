package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.HocardDAO;
import DTO.*;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class controller
 */
@WebServlet("/")
public class HocardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HocardDAO dao;
	private ServletContext ctx;   
    
	
    @Override
	public void init() throws ServletException {
		super.init();
		dao = new HocardDAO();
		ctx = getServletContext();
	}


	public HocardController() {
        super();        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPro(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPro(request, response);
	}
	
	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getServletPath();
		String site = null;
		
		switch(command) {
		case "/addcard" : site = "addCard.jsp";
		case "/insert" : site = insertCard(request);
		case "/mycard" : site = viewCardlist(request);
		}
		
		if(site.startsWith("redirect:/")) {
			// redirect/ 문자열 이후 경로만 가지고 옴
			String rview = site.substring("redirect:/".length());
			response.sendRedirect(rview);
		} else {
			getServletContext().getRequestDispatcher("/" + site).forward(request, response);
		}
		
	}
	
	public String insertCard(HttpServletRequest request) {
		AddCard ac = new AddCard();
		try {	
			BeanUtils.populate(ac, request.getParameterMap());
			dao.insertCard(ac);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("추가 과정에서 문제 발생!!");
			request.setAttribute("error", "카드 등록 실패");
			return "addCard.jsp";
		}
		return "redirect:/addcard";
	}
	
	public String viewCardlist(HttpServletRequest request) {
		List<CardList> list;
		try {
			list = dao.getCardList();
			request.setAttribute("cardList", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드목록 생성 중 오류발생");
			request.setAttribute("error", "카드 목록이 정상적으로 처리되지 않았습니다!!");
		}
		return "cardList.jsp";
	}

}
