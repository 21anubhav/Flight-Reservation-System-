<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Add Flight - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="auth-card-container" style="max-width: 600px;">
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">
                <i class="fa-solid fa-plane-circle-check" style="color: #6366f1;"></i> Add Flight
            </h2>
        </div>

        <form:form method="post" action="${pageContext.request.contextPath}/admin/add-flight" modelAttribute="addFlightDto">
            <div class="grid-2">
                <div class="form-group">
                    <label class="form-label" for="flightNumber">Flight Number *</label>
                    <form:input path="flightNumber" id="flightNumber" cssClass="form-control" placeholder="e.g. AI304"/>
                    <form:errors path="flightNumber" cssClass="field-error"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="capacity">Capacity (Seats) *</label>
                    <form:input path="capacity" id="capacity" type="number" min="1" cssClass="form-control" placeholder="Default: 20"/>
                    <div class="form-hint">Default is 20 seats as per policy</div>
                    <form:errors path="capacity" cssClass="field-error"/>
                </div>
            </div>

            <div class="grid-2">
                <div class="form-group">
                    <label class="form-label" for="source">Source (Departure City) *</label>
                    <form:input path="source" id="source" cssClass="form-control" placeholder="e.g. Bangalore"/>
                    <form:errors path="source" cssClass="field-error"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="destination">Destination City *</label>
                    <form:input path="destination" id="destination" cssClass="form-control" placeholder="e.g. Hyderabad"/>
                    <form:errors path="destination" cssClass="field-error"/>
                </div>
            </div>

            <div class="grid-2">
                <div class="form-group">
                    <label class="form-label" for="departureTime">Departure Time *</label>
                    <form:input path="departureTime" id="departureTime" cssClass="form-control" placeholder="e.g. 10:45"/>
                    <form:errors path="departureTime" cssClass="field-error"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="arrivalTime">Arrival Time *</label>
                    <form:input path="arrivalTime" id="arrivalTime" cssClass="form-control" placeholder="e.g. 20:40"/>
                    <form:errors path="arrivalTime" cssClass="field-error"/>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="pricePerSeat">Price per Seat (&#8377;) *</label>
                <form:input path="pricePerSeat" id="pricePerSeat" type="number" step="0.5" cssClass="form-control" placeholder="e.g. 5000.0"/>
                <form:errors path="pricePerSeat" cssClass="field-error"/>
            </div>

            <button type="submit" class="btn btn-primary btn-block" style="margin-top: 1.5rem; padding: 0.75rem;">
                <i class="fa-solid fa-plus-circle"></i> Add Flight
            </button>

            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary btn-block" style="margin-top: 0.75rem;">
                <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
            </a>
        </form:form>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
