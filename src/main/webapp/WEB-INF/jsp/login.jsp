<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Login - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="auth-card-container">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            <i class="fa-solid fa-circle-check"></i> ${successMessage}
        </div>
    </c:if>

    <c:if test="${not empty logoutMessage}">
        <div class="alert alert-info">
            <i class="fa-solid fa-info-circle"></i> ${logoutMessage}
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
        </div>
    </c:if>

    <div class="card">
        <div class="card-header" style="text-align: center; display: block; border-bottom: none; margin-bottom: 0.5rem;">
            <div style="width: 48px; height: 48px; border-radius: 50%; background-color: var(--primary-light); color: var(--primary-color); display: inline-flex; align-items: center; justify-content: center; font-size: 1.25rem; margin-bottom: 0.5rem;">
                <i class="fa-solid fa-right-to-bracket"></i>
            </div>
            <h2 class="card-title" style="justify-content: center;">Login</h2>
            <p style="color: var(--text-muted); font-size: 0.85rem; margin-top: 0.25rem;">Enter your credentials to access your account</p>
        </div>

        <form:form method="post" action="${pageContext.request.contextPath}/login" modelAttribute="loginDto">
            <form:hidden path="redirect"/>

            <div class="form-group">
                <label class="form-label" for="email">Email *</label>
                <form:input path="email" id="email" type="email" cssClass="form-control" placeholder="Enter your email (e.g. user@deloitte.com)"/>
                <form:errors path="email" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="password">Password *</label>
                <form:password path="password" id="password" cssClass="form-control" placeholder="Enter your password"/>
                <form:errors path="password" cssClass="field-error"/>
            </div>

            <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1.5rem;">
                <i class="fa-solid fa-arrow-right-to-bracket"></i> Login
            </button>
        </form:form>

        <div style="margin-top: 1.25rem; padding: 0.75rem; background-color: #f8fafc; border-radius: 6px; font-size: 0.8rem; color: var(--text-muted);">
            <strong>Demo Accounts:</strong><br>
            Admin: <code>admin@deloitte.com</code> / <code>admin123</code><br>
            User: <code>user@deloitte.com</code> / <code>user123</code>
        </div>

        <div style="text-align: center; margin-top: 1.25rem; font-size: 0.9rem; color: var(--text-muted);">
            Don't have an account? <a href="${pageContext.request.contextPath}/signup">Sign up here</a>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
