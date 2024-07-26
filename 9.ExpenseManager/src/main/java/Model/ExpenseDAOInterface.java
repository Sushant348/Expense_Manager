package Model;

import java.util.ArrayList;

import Controller.ExpenseManager;

public interface ExpenseDAOInterface {
	
	public int insertData(ExpenseDTO e);
	public ArrayList<ExpenseDTO> transaction();
	public ExpenseDTO fetchIncomeExpense();
	public ExpenseDTO displayDataForSpecificRange(ExpenseDTO dto);
	public ExpenseDTO fetchTotalIncomeExpence();
}
