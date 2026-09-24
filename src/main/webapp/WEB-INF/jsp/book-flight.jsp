<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Book Flight - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="auth-card-container" style="max-width: 550px;">
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">
                <i class="fa-solid fa-plane" style="color: #6366f1;"></i> Book Flight
            </h2>
        </div>

        <!-- Highlighted Flight Banner (Wireframe Page 6) -->
        <div style="background: #e0f2fe; border: 1px solid #bae6fd; border-radius: 6px; padding: 1rem; margin-bottom: 1.5rem;">
            <div style="font-size: 1.15rem; font-weight: 700; color: #0369a1; margin-bottom: 0.25rem;">
                Flight Number: ${flight.flightNumber}
            </div>
            <div style="font-size: 0.9rem; color: #0c4a6e;">
                <span><strong>${flight.source}</strong> &rarr; <strong>${flight.destination}</strong></span> |
                <span>Dep: <strong>${flight.departureTime}</strong></span> | 
                <span>Fare: <strong>&#8377; ${flight.pricePerSeat}</strong>/seat</span>
            </div>
        </div>

        <c:if test="${not empty seatError}">
            <div class="alert alert-danger">
                <i class="fa-solid fa-circle-exclamation"></i> ${seatError}
            </div>
        </c:if>

        <form:form method="post" action="${pageContext.request.contextPath}/book-flight" modelAttribute="bookingFormDto">
            <form:hidden path="flightId"/>

            <div class="form-group">
                <label class="form-label" for="journeyDate">Journey Date *</label>
                <form:input path="journeyDate" id="journeyDate" type="date" cssClass="form-control"/>
                <div class="form-hint">Format: YYYY-MM-DD (must be present or future date)</div>
                <form:errors path="journeyDate" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="seats">Number of Passengers *</label>
                <form:input path="seats" id="seats" type="number" min="1" max="10" cssClass="form-control"/>
                <div class="form-hint">Maximum 10 passengers allowed</div>
                <form:errors path="seats" cssClass="field-error"/>
            </div>

            <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1.5rem; padding: 0.75rem;">
                <i class="fa-solid fa-calendar-check"></i> Book Now
            </button>

            <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-secondary btn-block" style="margin-top: 0.75rem;">
                <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
            </a>
        </form:form>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
