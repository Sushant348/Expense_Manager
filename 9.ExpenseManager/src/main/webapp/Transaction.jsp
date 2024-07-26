<%@page import="java.time.LocalDate"%>
<%@page import="Model.ExpenseDAO"%>
<%@page import="Model.ExpenseDAOInterface"%>
<%@page import="Model.ExpenseDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-300">

<% ArrayList<ExpenseDTO> list=(ArrayList<ExpenseDTO>)request.getAttribute("result"); %>

<section id="main" class="w-screen flex justify-center items-center mt-6">
<div class="w-1/2 bg-white border-2 border-black text-center p-5 grid gap-5 shadow-2xl shadow-black">
<h1 style="color: blue; font-size: larger; font-weight: bolder;">TRANSACTIONS</h1>
<%if(list!=null){ %>
<table id="table">
  <tr>
    <th class="border border-black">Date</th>
    <th class="border border-black">Total Income</th>
    <th class="border border-black">Total Expense</th>
    <th class="border border-black">Total Balance</th>
   </tr>
   <%  for(ExpenseDTO data:list){ 
      LocalDate date = LocalDate.parse(data.getDate());%>
   <tr>
  
           <td class="border border-black"><%=date%></td>      
      
      <td class="border border-black"><%=data.getTotalIncome()+data.getIncome() %></td>
      <td class="border border-black"><%=data.getTotalExpense()+data.getExpense() %></td>
      <td class="border border-black"><%=(data.getTotalIncome()-data.getTotalExpense()) %>
      
   </tr>
   <%}
    %> 
</table>
<%} %>
<table >
  <tr>
    <th class="border border-black"><h3>Total Income</h3></th>
    <th class="border border-black"><h3>Total Expense</h3></th>
    <th class="border border-black"><h3>Total Balance</h3></th>
  </tr>
  <tr>
     <td class="border border-black"><h3><%=0%></h3></td>
     <td class="border border-black"><h3><%=0 %></h3></td>
     <td class="border border-black"><h3><%=0%></h3></td>
  </tr>
</table>
<a href="index.jsp"><button class="cursor-pointer p-3  bg-yellow-400 border border-black">HOME</button></a>
</div>
</section>

</body>
</html>