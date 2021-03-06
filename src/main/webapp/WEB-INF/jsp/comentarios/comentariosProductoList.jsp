<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="comentarios">
    <h2>Comentarios</h2>

    <table id="commentsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Titulo</th>
            <th style="width: 200px;">Texto</th>
            <th style="width: 120px">Autor</th>
  
        </tr>
        </thead>
        <tbody>
      		  <c:forEach items="${comentarios}" var="comentario">
            <tr>
                <td>
                    <c:out value="${comentario.titulo}"/>
                </td>
                <td>
                    <c:out value="${comentario.texto}"/>
                </td>
                <td>
                    <c:out value="${comentario.cliente.email}"/>
                </td>
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>