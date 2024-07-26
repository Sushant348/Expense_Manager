<%@page import="Model.ExpenseDTO"%>
<%@page import="Model.ExpenseDAO"%>
<%@page import="Model.ExpenseDAOInterface"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>

<section class="w-screen h-screen flex justify-center items-center bg-gray-300">
<div class="w-96 border-2 bg-white border-black text-center p-3 grid gap-2 text-center shadow-2xl shadow-black">
  <form  action="ExpenseManager" method="post">
     <input type="submit" name="btn" value="ADD INCOME" placeholder="ADD INCOME" class="p-5 cursor-pointer bg-green-500 border border-black"><br><br>
     <input type="submit" name="btn"  value="ADD EXPENSE" placeholder="ADD EXPENSE" class="p-5 cursor-pointer bg-red-500 border border-black"><br><br>
     <input type="submit" name="btn"  value="TRANSACTION" placeholder="TRANSACTION" class="p-5 cursor-pointer bg-blue-500 border border-black"><br><br>
     <input type="date" name="startDate" >
      <input type="date" name="endDate" >  
      <input type="submit" name="btn" value="find" class="p-2 cursor-pointer bg-red-500 border border-black"> 
     </form>
      <div>
         <br> <br>
<table rules="all" style="border: 1px solid white;">
  
 <tr>
 <th style="padding-right: 10px;">TOTAL INCOME</th>
  <th style="padding-left: 10px; padding-right: 10px;">TOTAL EXPENSE</th>
   <th style="padding-left: 10px;">BALANCE</th>
 
 </tr>
 
 <tr>
 <%ExpenseDTO dto = (ExpenseDTO) request.getAttribute("DATA"); %>
 <% if(dto!=null){ %>
 <td><%= dto.getTotalIncome() %></td>
 <td><%= dto.getTotalExpense() %></td>
 <td><%= dto.getBalance() %></td>
 <%}else {%>
     <td>0.0</td>
      <td>0.0</td>
       <td>0.0</td>
       <%} %>
 
 </tr>

</table>
     </div>
  </div>
</section>


</body>
</html>