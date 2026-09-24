<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Error - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="auth-card-container" style="max-width: 550px; text-align: center;">
    <div class="card" style="padding: 2.5rem 1.5rem;">
        <div style="width: 64px; height: 64px; border-radius: 50%; background-color: #fee2e2; color: #ef4444; display: inline-flex; align-items: center; justify-content: center; font-size: 1.75rem; margin-bottom: 1rem;">
            <i class="fa-solid fa-triangle-exclamation"></i>
        </div>

        <h2 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 0.5rem; color: #1f2937;">
            ${errorTitle != null ? errorTitle : 'An Error Occurred'}
        </h2>

        <c:if test="${not empty status}">
            <div style="font-size: 0.9rem; font-weight: 600; color: #ef4444; margin-bottom: 0.75rem;">
                Error Code: ${status}
            </div>
        </c:if>

        <p style="color: var(--text-muted); font-size: 0.95rem; line-height: 1.6; margin-bottom: 1.75rem;">
            ${errorMessage != null ? errorMessage : 'Something went wrong while processing your request. Please try again or return to the home page.'}
        </p>

        <div style="display: flex; gap: 0.75rem; justify-content: center;">
            <a href="javascript:history.back()" class="btn btn-outline">
                <i class="fa-solid fa-arrow-left"></i> Go Back
            </a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                <i class="fa-solid fa-house"></i> Home Page
            </a>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
