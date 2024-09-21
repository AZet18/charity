<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>

<section class="login-page">
    <h2>Załóż konto</h2>

    <section class="login-page">
        <h2>Zaloguj się</h2>
        <form:form method="post" action="/login" modelAttribute="user">
            <div class="form-group">
                <form:input path="email" type="text" id="email" placeholder="email" required="required"/>
            </div>
            <div class="form-group">
                <form:password path="password" id="password" placeholder="password" required="required"/>
                <a href="#" class="btn btn--small btn--without-border reset-password">Przypomnij hasło</a>
            </div>

            <div class="form-group form-group--buttons">
                <a href="#" class="btn btn--without-border">Załóż konto</a>
                <button class="btn" type="submit">Zaloguj się</button>
            </div>
        </form:form>
    </section>
</section>


<jsp:include page="footer.jsp"/>
