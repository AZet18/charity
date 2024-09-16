<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>

<section class="login-page">
    <h2>Załóż konto</h2>

    <form:form method="post" modelAttribute="user">
        <div class="form-group">
            <form:input path="email" type="text" id="email" placeholder="email" required="required"/>
            <form:errors path="email" cssClass="error" />
        </div>
        <div class="form-group">
            <form:input path="password" type="password" id="password" placeholder="password" required="required"/>
            <form:errors path="password" cssClass="error" />
        </div>
        <div class="form-group">
            <form:input path="passwordRepeat" type="password" id="passwordRepeat" placeholder="repeat password" required="required"/>
            <form:errors path="passwordRepeat" cssClass="error" />
        </div>

        <div class="form-group form-group--buttons">
            <a href="<c:url value="/login" />" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
    </form:form>
</section>


<jsp:include page="footer.jsp"/>
