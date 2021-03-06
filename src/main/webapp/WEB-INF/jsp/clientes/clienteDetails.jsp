<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	
<petclinic:layout pageName="cliente">

    <h2>Perfil Usuario</h2>

	<div><c:if test="${message}"> <c:out value="${message}"/> </c:if> </div>
	
    <table class="table table-striped">
        <tr>
            <th>Nombre y Apellidos</th>
            <td><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>Identificaci�n</th>
            <td><b><c:out value="${cliente.dni}"/></b></td>
        </tr>
         <tr>
            <th>Correo Electr�nico</th>
            <td><b><c:out value="${cliente.email}"/></b></td>
        </tr>
         <tr>
            <th>Fecha de Nacimiento</th>
            <td><b><c:out value="${cliente.fechaNacimiento}"/></b></td>
        </tr>
        <tr>
            <th>Direcci�n</th>
            <td><b><c:out value="${cliente.direccion} | Codigo Postal: ${cliente.codigoPostal} [${cliente.ciudad}]"/></b></td>
        </tr>
         <tr>
            <th>Telefono</th>
            <td><b><c:out value="${cliente.telefono}"/></b></td>
        </tr>
        <tr>
            <th>Tarjeta de Cr�dito</th>
            <td><b><c:out value="${cliente.tarjetaCredito} | Saldo: ${cliente.cartera}"/> � </b></td>
        </tr>
        
    </table>

	<!-- Bot�n que permitir� al usuario editar sus datos  -->
    <spring:url value="/clientes/{clienteId}/edit" var="editUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Usuario</a>

	<!-- Bot�n que llevar� al usuario a SU lista de reproductores (No visible por administradores)  -->
	<sec:authorize access="!hasAuthority('admin')">
    <spring:url value="/clientes/{clienteId}/reproductores" var="listaReproductoresUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(listaReproductoresUrl)}" class="btn btn-default">Ver Reproductores</a>
	</sec:authorize>
	
	<sec:authorize access="hasAuthority('admin')">
    <spring:url value="/comentarios/{clienteId}" var="listaComentariosUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(listaComentariosUrl)}" class="btn btn-default">Ver mis comentarios</a>
	</sec:authorize>
	
	<!-- Bot�n que permitir� al usuario eliminar su cuenta  -->
    <spring:url value="/clientes/{clienteId}/delete" var="deleteUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default" onclick="return confirm('�Continuar?') ? true : false;">Eliminar Usuario</a>
	
    <br/>
    <br/>
    <br/>

</petclinic:layout>