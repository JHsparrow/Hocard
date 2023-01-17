package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}
	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getServletPath();
		String site = null;
		
		switch(command) {
		case "/index" 			: site = viewIndex(request); break;
		case "/addcard" 		: site = "addCard.jsp"; break;
		case "/insert" 			: site = insertCard(request, response); break;
		case "/cardlist" 		: site = viewAllCardlist(request); break;
		case "/viewcard" 		: site = viewCard(request); break;
		case "/modifycard" 		: site = viewformodify(request); break;
		case "/modify" 			: site = modifyCard(request, response); break;
		case "/delete" 			: site = deleteCard(request, response); break;
		case "/cardlog" 		: site = viewCardLog(request); break;
		case "/pickpage" 		: site = pickCardView(request); break;
		case "/pickcard" 		: site = pickCard(request); break;
		case "/mycard" 			: site = MyCardlist(request); break;
		case "/viewmycard" 		: site = viewMyCard(request); break;
		case "/delmycard" 		: site = delMyCard(request, response); break;
		case "/picklog" 		: site = viewCardLog(request); break;
		case "/login" 			: site = loginPage(request, response); break;
		case "/logout" 			: site = logOutPage(request, response); break;
		case "/join" 			: site = "join.jsp"; break;
		case "/joinResult" 		: site = joinPage(request, response); break;
		case "/viewUserinfo" 	: site = viewUserinfo(request); break;
		case "/modifyUserinfo" 	: site = viewModifyUserinfo(request); break;
		case "/modinfoResult" 	: site = modifyUserinfo(request, response); break;
		}
		
		if(site.startsWith("redirect:/")) {
			String rview = site.substring("redirect:/".length());
			response.sendRedirect(rview);
		} else {
			getServletContext().getRequestDispatcher("/" + site).forward(request, response);
		}
	}
	
	public String insertCard(HttpServletRequest request, HttpServletResponse response) {
		AddCard ac = new AddCard();
		int result = 0;
		response.setContentType("text/html; charset=UTF-8");
		try {	
			PrintWriter out = response.getWriter();
			BeanUtils.populate(ac, request.getParameterMap());
			
			result = dao.insertCard(ac);
			dao.insertCardLog(0,"C");
			if (result == 1) {
				out.println("<script>alert('카드 등록이 완료되었습니다.'); location.href='addCard.jsp'; </script>");
			}
			else out.println("<script>alert('카드 등록이 실패하였습니다.'); location.href='addCard.jsp';</script>"); 
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("추가 과정에서 문제 발생!!");
			request.setAttribute("error", "카드 등록 실패");
		}
		return "1";
	}
	
	public String viewAllCardlist(HttpServletRequest request) {
		String fl_rarity = request.getParameter("fl_rarity");
		String fl_cardno = request.getParameter("fl_cardno");
		String srt_rarity = request.getParameter("srt_rarity");
		List<CardList> list;
		try {
			list = dao.getCardList(fl_rarity, fl_cardno, srt_rarity);
			request.setAttribute("cardList", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드목록 생성 중 오류발생");
			request.setAttribute("error", "카드 목록이 정상적으로 처리되지 않았습니다!!");
		}
		
		if(fl_rarity != null|| fl_cardno != null || srt_rarity != null) return "cardList.jsp?t_rarity="+srt_rarity+"&fl_rarity="+fl_rarity+"&fl_cardno="+fl_cardno;
		else return "cardList.jsp";
	}
	
	public String viewCard(HttpServletRequest request) {
		int card_no = Integer.parseInt(request.getParameter("card_no"));
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userId");
		String excard;
        try {
        	CardView cl = dao.ViewforModi(card_no);	
        	excard = dao.CheckPickCard(userid,card_no);
			request.setAttribute("card", cl);
			request.setAttribute("existCard", excard);
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
	
	public String modifyCard(HttpServletRequest request, HttpServletResponse response) {
		ModifyCard mc = new ModifyCard();
		response.setContentType("text/html; charset=UTF-8");
		int result = 0;
		int card_no = Integer.parseInt(request.getParameter("card_no"));
		try {	
			PrintWriter out = response.getWriter();
			BeanUtils.populate(mc, request.getParameterMap());
			result = dao.ModifyCard(mc);
			dao.insertCardLog(card_no,"U");
			if (result == 1) {
				out.println("<script>alert('카드 수정이 완료되었습니다.'); location.href='viewcard?card_no="+card_no+"';</script>");
			}
			else {
				out.println("<script>alert('카드 수정이 실패하였습니다.'); location.href='viewcard?card_no="+card_no+"';</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("수정 과정에서 문제 발생!!");
			request.setAttribute("error", "카드 수정 실패");
		}
		return "1";
	}
	public String deleteCard(HttpServletRequest request, HttpServletResponse response) {
		int card_no = Integer.parseInt(request.getParameter("card_no"));
		response.setContentType("text/html; charset=UTF-8");
		
		int result = 0;
		AddCard ac = new AddCard();
        try {
        	PrintWriter out = response.getWriter();
        	dao.insertDelCard(ac, card_no);
        	dao.DeleteMyCard(card_no);
        	dao.insertCardLog(card_no,"D");
        	result = dao.DeleteCard(card_no);
			if (result == 1) {
				out.println("<script>alert('카드 삭제가 완료되었습니다.'); location.href='cardlist';</script>");
			}
			else {
				out.println("<script>alert('카드 삭제가 실패하였습니다.'); location.href='viewcard?card_no="+card_no+"';</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cardlist";
	}

	public String viewCardLog(HttpServletRequest request) {
		List<CardLog> list;
		int page = 1;
		int itemsInAPage = 10;
		int totalCount = 0;
		try {
			list = dao.getCardLog();
			totalCount = dao.CountLoglist();
			int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
			request.setAttribute("page", page);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("totalpage", totalPage);
			request.setAttribute("cardLog", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드목록 생성 중 오류발생");
			request.setAttribute("error", "로그 목록이 정상적으로 처리되지 않았습니다!!");
		}
		return "cardLog.jsp";
	}
	
	public String viewIndex(HttpServletRequest request) {
		try {
			IndexView iv = dao.ViewIndex();
			request.setAttribute("indexview", iv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index.jsp";
		
	}
	
	public String pickCardView(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userId");
		
		List<PickCard> list;
		try {
			list = dao.PickCardTemp(userid);
			request.setAttribute("pickList", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드목록 생성 중 오류발생");
			request.setAttribute("error", "카드 목록이 정상적으로 처리되지 않았습니다!!");
		}
		return "pickCard.jsp";
	}
	
	public String pickCard(HttpServletRequest request) {
		String pickno = request.getParameter("pick_no");
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userId");
		try {
			if (pickno.equals("1")) {
				dao.deleteTemp();
				dao.PickCard(pickno,userid);
			}
			else {
				dao.deleteTemp();
				dao.PickCard(pickno,userid);
				dao.PickCard(pickno,userid);
				dao.PickCard(pickno,userid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pickpage";
	}
	
	
	public String MyCardlist(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userId");
		
		String fl_rarity = request.getParameter("fl_rarity");
		String fl_pickno = request.getParameter("fl_pickno");
		String srt_rarity = request.getParameter("srt_rarity");
		List<CardList> list;
		try {
			list = dao.MyCardList(fl_rarity, fl_pickno, srt_rarity, userid);
			request.setAttribute("cardList", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드목록 생성 중 오류발생");
			request.setAttribute("error", "카드 목록이 정상적으로 처리되지 않았습니다!!");
		}
		if(fl_rarity != null|| fl_pickno != null || srt_rarity != null) return "mycardlist.jsp?t_rarity="+srt_rarity+"&fl_rarity="+fl_rarity+"&fl_pickno="+fl_pickno;
		else return "mycardlist.jsp";
	}
	
	public String viewMyCard(HttpServletRequest request) {
		int pick_no = Integer.parseInt(request.getParameter("pick_no"));
        try {
        	CardView cv = dao.ViewMyCard(pick_no);			
			request.setAttribute("card", cv);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("카드 정보 출력 실패!");
			request.setAttribute("error", "카드정보를 정상적으로 가져오지 못했습니다!!");
		}
		return "mycardview.jsp";
	}
	public String delMyCard(HttpServletRequest request, HttpServletResponse response) {
		int pick_no = Integer.parseInt(request.getParameter("pick_no"));
		response.setContentType("text/html; charset=UTF-8");
		
		int result = 0;
        try {
        	PrintWriter out = response.getWriter();
        	result = dao.DelMyCard(pick_no);
			if (result == 1) {
				out.println("<script>alert('카드 삭제가 완료되었습니다.'); location.href='mycard';</script>");
			}
			else {
				out.println("<script>alert('카드 삭제가 실패하였습니다.'); location.href='viewmycard?card_no="+pick_no+"';</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mycard";
	}
	public String loginPage(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		Login login = new Login();
		int result = 0;
        try {
        	BeanUtils.populate(login, request.getParameterMap());
        	PrintWriter out = response.getWriter();
        	result = dao.LoginResult(login);
        	HttpSession session = request.getSession();
			if (result == 1) {
		        session.setAttribute("userId", login.getUserid());
		        session.setAttribute("loginFlag", "Y");
				out.println("<script>alert('로그인 성공.'); location.href='index.jsp';</script>");
			}
			else {
				session.setAttribute("loginFlag", "N");
				out.println("<script>alert('로그인 실패.'); location.href='index.jsp';</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index.jsp";
	}
	
	public String logOutPage(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
        try {
			HttpSession session = request.getSession();
			session.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index.jsp";
	}
	public String joinPage(HttpServletRequest request, HttpServletResponse response) {
		Join join = new Join();
		int result = 0;
		response.setContentType("text/html; charset=UTF-8");
		try {	
			PrintWriter out = response.getWriter();
			BeanUtils.populate(join, request.getParameterMap());
			
			result = dao.joinResult(join);
			if (result == 1) {
				out.println("<script>alert('회원가입이 완료되었습니다.'); location.href='index.jsp'; </script>");
			}
			else if(result == 3) {
				out.println("<script>alert('중복 된 완료되었습니다.'); location.href='index.jsp'; </script>");
			}
			else out.println("<script>alert('회원가입이 실패하였습니다.'); location.href='index.jsp';</script>"); 
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("회원가입 과정에서 문제 발생!!");
			request.setAttribute("error", "회원가입 실패");
		}
		return "1";
	}
	
	public String viewUserinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userId");
        try {
        	UserInfo ui = dao.ViewUserInfo(userid);			
			request.setAttribute("userinfo", ui);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("회원 정보 출력 실패!");
			request.setAttribute("error", "회원정보를 정상적으로 가져오지 못했습니다!!");
		}
		return "userInfoView.jsp";
	}
	
	public String viewModifyUserinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userId");
        try {
        	UserInfo ui = dao.ViewUserInfo(userid);			
			request.setAttribute("userinfo", ui);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("회원 정보 출력 실패!");
			request.setAttribute("error", "회원정보를 정상적으로 가져오지 못했습니다!!");
		}
		return "userInfoModify.jsp";
	}
	
	public String modifyUserinfo(HttpServletRequest request, HttpServletResponse response) {
		Join join = new Join();
		response.setContentType("text/html; charset=UTF-8");
		int result = 0;
		try {	
			PrintWriter out = response.getWriter();
			BeanUtils.populate(join, request.getParameterMap());
			result = dao.ModifyUserInfo(join);
			if (result == 1) {
				out.println("<script>alert('회원정보 수정이 완료되었습니다.'); location.href='viewUserinfo';</script>");
			}
			else {
				out.println("<script>alert('회원정보 수정이 실패하였습니다.'); location.href='modifyUserinfo';</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("수정 과정에서 문제 발생!!");
			request.setAttribute("error", "회원정보 수정 실패");
		}
		return "1";
	}
}
