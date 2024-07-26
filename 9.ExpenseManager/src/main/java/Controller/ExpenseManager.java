package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import Model.ConnectionHelper;
import Model.ExpenseDAO;
import Model.ExpenseDAOInterface;
import Model.ExpenseDTO;
import Model.ServiceClass;

@WebServlet(urlPatterns = {"/ExpenseManager","/AddIncome","/AddExpense"})
public class ExpenseManager extends HttpServlet{
	
	private static ExpenseDAOInterface expenseManager=new ExpenseDAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path=req.getServletPath();
		switch (path) {
		case "/ExpenseManager": 
			String button=req.getParameter("btn");
			if(button.equals("ADD INCOME")) {
				RequestDispatcher rd=req.getRequestDispatcher("AddIncome.html");
				rd.forward(req, resp);
			}else if(button.equals("ADD EXPENSE")){
				RequestDispatcher rd=req.getRequestDispatcher("AddExpense.html");
				rd.forward(req, resp);
			}else if(button.equals("TRANSACTION")){
				try {
					transaction(req, resp);
				} catch (ClassNotFoundException | SQLException | ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				displayFrontPageData(req, resp);
			}
		break;
		case "/AddIncome":
			try {
				addIncome(req, resp);
				displayFrontPageData(req, resp);
			} catch (ParseException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
		case "/AddExpense":
			try {
				addExpense(req, resp);
				displayFrontPageData(req, resp);
			} catch (ClassNotFoundException | ParseException | SQLException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
	}
  }
	
	private void displayFrontPageData(HttpServletRequest req, HttpServletResponse resp) {
		ServiceClass s1 = new ServiceClass();
		String startPoint = s1.startPoint();
		String endPoint  = s1.endPoint();
		String temp1 = req.getParameter("startDate");
		String temp2 = req.getParameter("endDate");
		
		if(temp1!="" && temp2!="") {
		   
			startPoint = temp1;
			endPoint = temp2;
			
		}
		ExpenseDTO dto = new ExpenseDTO();
		dto.setStartPoint(startPoint);
		dto.setEndPoint(endPoint);
	   ExpenseDTO data= expenseManager.displayDataForSpecificRange(dto);
	
	   req.setAttribute("DATA", data);
	 
	  try {
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	   } catch (ServletException e) {
		// TODO Auto-generated catch block
	  	e.printStackTrace();
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
	}

	private void  transaction(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, ServletException, IOException {
		ArrayList<ExpenseDTO> result=expenseManager.transaction();
		RequestDispatcher rd=req.getRequestDispatcher("Transaction.jsp");
		req.setAttribute("result", result);
		rd.forward(req, resp);
		
	}

	private void addExpense(HttpServletRequest req, HttpServletResponse resp) throws ParseException, ClassNotFoundException, SQLException, ServletException, IOException {
		int expense=Integer.parseInt(req.getParameter("expense"));
		String category=req.getParameter("category");
		String paymentMethod=req.getParameter("paymentMethod");
		String note=req.getParameter("ADD NOTE");
		String date=req.getParameter("date");
		String time=req.getParameter("time");
		
		
		
        ExpenseDTO e=new ExpenseDTO();
		e.setExpense(expense);
		e.setCategory(category);
		e.setPaymentMethod(paymentMethod);
		e.setNote(note);
		e.setDate(date);
		e.setTime(time);
		
		int count=expenseManager.insertData(e);
		
	}

	private void addIncome(HttpServletRequest req, HttpServletResponse resp) throws ParseException, ClassNotFoundException, SQLException, IOException, ServletException {
		int income=Integer.parseInt(req.getParameter("income"));
		String category=req.getParameter("category");
		String paymentMethod=req.getParameter("paymentMethod");
		String note=req.getParameter("ADD NOTE");
		String date=req.getParameter("date");
		String time=req.getParameter("time");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date realdate = formatter.parse(date);
		java.sql.Date sqlDate = new java.sql.Date(realdate.getTime());
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		if(time.equals("")) {
			time="00:00";
		}
        Date realtime = timeFormat.parse(time);
        java.sql.Time sqlTime = new java.sql.Time(realtime.getTime());
		
		ExpenseDTO e=new ExpenseDTO();
		e.setIncome(income);
		e.setCategory(category);
		e.setPaymentMethod(paymentMethod);
		e.setNote(note);
		e.setDate(date);
		e.setTime(time);
		
		int count=expenseManager.insertData(e);
		
        
	}
}
