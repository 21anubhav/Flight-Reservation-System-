<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Add Passenger - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="auth-card-container" style="max-width: 550px;">
    <div class="card">
        <div class="card-header" style="display: flex; justify-content: space-between; align-items: baseline;">
            <div>
                <h2 class="card-title">Add Passenger ${currentStep}</h2>
                <div style="font-size: 0.85rem; color: var(--text-muted); margin-top: 0.25rem;">
                    Remaining passengers: ${remainingPassengers}
                </div>
            </div>
            <div style="font-size: 0.85rem; background: var(--primary-light); color: var(--primary-color); padding: 0.25rem 0.65rem; border-radius: 12px; font-weight: 600;">
                Passenger ${currentStep} of ${totalSteps}
            </div>
        </div>

        <div style="background-color: #f8fafc; border: 1px solid #e2e8f0; border-radius: 6px; padding: 0.75rem 1rem; margin-bottom: 1.25rem; font-size: 0.85rem;">
            <strong>Flight:</strong> ${bookingSession.flight.flightNumber} | 
            <strong>Date:</strong> ${bookingSession.journeyDate} | 
            <strong>Route:</strong> ${bookingSession.flight.source} &rarr; ${bookingSession.flight.destination}
        </div>

        <form:form method="post" action="${pageContext.request.contextPath}/book-flight/passenger?step=${currentStep}" modelAttribute="passengerDto">
            <div class="form-group">
                <label class="form-label" for="passengerName">Passenger Name *</label>
                <form:input path="passengerName" id="passengerName" cssClass="form-control" placeholder="Enter full name"/>
                <form:errors path="passengerName" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="age">Age *</label>
                <form:input path="age" id="age" type="number" min="1" max="120" cssClass="form-control" placeholder="Enter age"/>
                <form:errors path="age" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="gender">Gender *</label>
                <form:select path="gender" id="gender" cssClass="form-control">
                    <form:option value="" label="-- Select Gender --"/>
                    <form:option value="Male" label="Male"/>
                    <form:option value="Female" label="Female"/>
                    <form:option value="Other" label="Other"/>
                </form:select>
                <form:errors path="gender" cssClass="field-error"/>
            </div>

            <c:choose>
                <c:when test="${isLastPassenger}">
                    <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1.5rem; padding: 0.75rem;">
                        <i class="fa-solid fa-circle-check"></i> Complete Booking
                    </button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1.5rem; padding: 0.75rem;">
                        <i class="fa-solid fa-user-plus"></i> Add Passenger &amp; Continue
                    </button>
                </c:otherwise>
            </c:choose>
        </form:form>

        <div style="border-top: 1px solid var(--border-color); margin-top: 1.5rem; padding-top: 0.75rem; text-align: center; font-size: 0.85rem; color: var(--text-muted);">
            Passenger ${currentStep} of ${totalSteps}
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
