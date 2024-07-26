package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Controller.ExpenseManager;

public class ExpenseDAO implements ExpenseDAOInterface{
	
	private static String insertDataQuery="insert into expense values(?,?,?,?,?,?,?,?,?,?,?)";
	private static String transactionQuery="SELECT payment_Date,category,income,expense FROM expense ORDER BY payment_Date,payment_time";
	private static String fetchIncomeExpense="SELECT SUM(income),SUM(expense)  FROM expense WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?";
	private static String fetchDataForYear="SELECT payment_Date,SUM(income),SUM(expense) FROM expense GROUP BY YEAR(payment_Date) ORDER BY YEAR(payment_Date)";
	private static String fetchDataForMonth="SELECT payment_Date,SUM(income),SUM(expense) FROM expense WHERE YEAR(payment_Date)=? GROUP BY MONTH(payment_Date) ORDER BY MONTH(payment_Date)";
	 private static String displayBalanceForDateRange = "SELECT sum(income),sum(expense) FROM expense WHERE payment_date BETWEEN ? AND ?";
	
	private static Connection con=ConnectionHelper.getConnection();
    
	private static int totalIncome=0,totalExpense=0,totalBalance=0;
	
	@Override
	public ExpenseDTO fetchTotalIncomeExpence() {
		try {
			CallableStatement cstmt=con.prepareCall("{call fetchbalance(?,?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.registerOutParameter(2, Types.INTEGER);
            cstmt.execute();
            ExpenseDTO e=new ExpenseDTO();
            e.setTotalIncome(cstmt.getInt(1));
            e.setTotalExpense(cstmt.getInt(2));
            e.setBalance((cstmt.getInt(1)-cstmt.getInt(2)));
            return e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int insertData(ExpenseDTO e) {
		
		ExpenseDTO z=fetchTotalIncomeExpence();
		
		PreparedStatement pstmt;
		try { 
			pstmt = con.prepareStatement(insertDataQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2,e.getIncome());
			pstmt.setInt(3, e.getExpense());
			pstmt.setString(4, e.getCategory());
			pstmt.setString(5, e.getPaymentMethod());
			pstmt.setString(6, e.getNote());
			pstmt.setString(7,  e.getDate());
			pstmt.setString(8, e.getTime());
			pstmt.setInt(9, z.getTotalIncome()+e.getIncome());
			pstmt.setInt(10, z.getTotalExpense()+e.getExpense());
			if(e.getExpense()==0) {
			   pstmt.setInt(11, (z.getTotalIncome()-z.getTotalExpense())+e.getIncome());
			}else {
				pstmt.setInt(11, (z.getTotalIncome()-z.getTotalExpense())-e.getExpense());
			}
	        int count=pstmt.executeUpdate();
	        return count;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<ExpenseDTO> transaction() {
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(transactionQuery);
			ResultSet rs=pstmt.executeQuery();
			ArrayList<ExpenseDTO> list=new ArrayList<ExpenseDTO>();
			ExpenseDTO e;
			while(rs.next()) {
				e=new ExpenseDTO();
				e.setDate(rs.getString(1));
				e.setCategory(rs.getString(2));
				e.setIncome(rs.getInt(3));
				e.setExpense(rs.getInt(4));
				list.add(e);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ExpenseDTO displayDataForSpecificRange(ExpenseDTO dto) {
		 
		 
		try {
			PreparedStatement pstmt = con.prepareStatement(displayBalanceForDateRange);
			pstmt.setString(1, dto.getStartPoint());
			pstmt.setString(2, dto.getEndPoint());
			System.out.println(dto.getStartPoint()+"========");
			System.out.println(dto.getEndPoint()+"===========");
			ResultSet rs = pstmt.executeQuery();
			
			ExpenseDTO temp;
			if(rs.next()) {
				temp = new ExpenseDTO();
				temp.setTotalIncome(rs.getInt(1));
				temp.setTotalExpense(rs.getInt(2));
				temp.setBalance(rs.getInt(1)-rs.getInt(2));
				return temp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	 @Override
	public ExpenseDTO fetchIncomeExpense() {
	        LocalDate currentdate = LocalDate.now();
	        int year=currentdate.getYear();
	        int month=currentdate.getMonthValue();
		try {
			PreparedStatement pstmt=con.prepareStatement(fetchIncomeExpense);
			pstmt.setInt(1, month);
			pstmt.setInt(2, year);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				ExpenseDTO e=new ExpenseDTO();
				e.setTotalIncome(rs.getInt(1));
				e.setTotalExpense(rs.getInt(2));
				e.setBalance((rs.getInt(1)-rs.getInt(2)));
				return e;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	 

	 


}
