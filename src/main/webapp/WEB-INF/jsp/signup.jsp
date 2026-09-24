<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Sign Up - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="auth-card-container">
    <div class="card">
        <div class="card-header" style="text-align: center; display: block; border-bottom: none; margin-bottom: 0.5rem;">
            <div style="width: 48px; height: 48px; border-radius: 50%; background-color: var(--primary-light); color: var(--primary-color); display: inline-flex; align-items: center; justify-content: center; font-size: 1.25rem; margin-bottom: 0.5rem;">
                <i class="fa-solid fa-user-plus"></i>
            </div>
            <h2 class="card-title" style="justify-content: center;">Sign Up</h2>
            <p style="color: var(--text-muted); font-size: 0.85rem; margin-top: 0.25rem;">Create a new account to book flights</p>
        </div>

        <form:form method="post" action="${pageContext.request.contextPath}/signup" modelAttribute="signupDto">
            <div class="form-group">
                <label class="form-label" for="email">Email *</label>
                <form:input path="email" id="email" type="email" cssClass="form-control" placeholder="Enter your email (e.g. name@example.com)"/>
                <form:errors path="email" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="password">Password *</label>
                <form:password path="password" id="password" cssClass="form-control" placeholder="Enter your password (min 6 characters)"/>
                <form:errors path="password" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="role">Role *</label>
                <form:select path="role" id="role" cssClass="form-control">
                    <form:option value="" label="-- Select Role --"/>
                    <form:option value="USER" label="User"/>
                    <form:option value="ADMIN" label="Admin"/>
                </form:select>
                <form:errors path="role" cssClass="field-error"/>
            </div>

            <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1.5rem;">
                <i class="fa-solid fa-user-check"></i> Sign Up
            </button>
        </form:form>

        <div style="text-align: center; margin-top: 1.5rem; font-size: 0.9rem; color: var(--text-muted);">
            Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
