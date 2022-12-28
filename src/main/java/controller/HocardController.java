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
		case "/addcard" : site = "addCard.jsp"; break;
		case "/insert" : site = insertCard(request); break;
		case "/mycard" : site = viewAllCardlist(request); break;
		case "/viewcard" : site = viewCard(request); break;
		case "/modifycard" : site = viewformodify(request); break;
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
	
	public String viewAllCardlist(HttpServletRequest request) {
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
	
	public String viewCard(HttpServletRequest request) {
		int card_no = Integer.parseInt(request.getParameter("card_no"));
        try {
        	CardView cl = dao.ViewforModi(card_no);			
			request.setAttribute("card", cl);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드 정보 출력 실패!");
			request.setAttribute("error", "카드정보를 정상적으로 가져오지 못했습니다!!");
		}
		return "cardview.jsp";
	}
	
	public String viewformodify(HttpServletRequest request) {
		int card_no = Integer.parseInt(request.getParameter("card_no"));
        try {
        	CardList cl = dao.ViewCard(card_no);			
			request.setAttribute("card", cl);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드 정보 출력 실패!");
			request.setAttribute("error", "카드정보를 정상적으로 가져오지 못했습니다!!");
		}
		return "cardmodify.jsp";
	}
	
	public String modifyCard(HttpServletRequest request) {
		ModifyCard mc = new ModifyCard();
		try {	
			BeanUtils.populate(mc, request.getParameterMap());
			dao.ModifyCard(mc);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("수정 과정에서 문제 발생!!");
			request.setAttribute("error", "카드 수정 실패");
			return "addCard.jsp";
		}
		return "redirect:/addcard";
	}

}
