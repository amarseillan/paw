
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-responsive.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body>
		<h1>Sistema de propiedades online</h1>
			<a href="../publication/search">Buscar publicaciones</a>
			<a href="../user/realEstates">Inmobiliarias</a>
		<c:if test="${userId != null}">
			<a href="../user/publications">Mis publicaciones</a>
			<a href="../user/logout">Salir</a>
		</c:if>
		<c:if test="${userId == null}">
			<a href="../user/login">Login</a>
		</c:if>
		<br>
			<div class="container-fluid">
  				<div class="container">
					<%-- header --%>
 				 </div>
  				<div class="container">
 				 
