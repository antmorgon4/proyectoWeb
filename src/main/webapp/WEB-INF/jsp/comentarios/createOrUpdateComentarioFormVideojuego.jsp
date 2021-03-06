<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="comentarios">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${comentario['new']}">Nuevo </c:if> Comentario
        </h2>
        <form:form modelAttribute="comentario" class="form-horizontal">
            <input type="hidden" name="id" value="${comentario.id}"/>
            <input type="hidden" name="cliente.id" value="${comentario.cliente.id}"/>
            <input type="hidden" name="videojuego.id" value="${comentario.videojuego.id}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Autor</label>
                    <div class="col-sm-10">
                        <c:out value="${comentario.cliente.nombre} ${comentario.cliente.email}"/>
                    </div>
                 
                </div>
                </div>
                 <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Videojuego</label>
                    <div class="col-sm-10">
                        <c:out value="${comentario.videojuego.nombre}"/>
                        
                    </div>
                 
                </div>
                <petclinic:inputField label="Titulo" name="titulo"/>
                <petclinic:inputField label="Texto" name="texto"/>
                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${comentario['new']}">
                            <button class="btn btn-default" type="submit">A�adir Comentario</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar Comentario</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!comentario['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>