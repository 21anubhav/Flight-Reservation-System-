<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Home - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">
        <i class="fa-solid fa-circle-check"></i> ${successMessage}
    </div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
        <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
    </div>
</c:if>

<div class="grid-2">
    <!-- Search Flights Form Card (Wireframe Page 5) -->
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">
                <i class="fa-solid fa-magnifying-glass" style="color: #6366f1;"></i> Search Flights
            </h2>
        </div>
        <form:form method="post" action="${pageContext.request.contextPath}/search" modelAttribute="searchFlightDto">
            <div class="form-group">
                <label class="form-label" for="source">From</label>
                <form:input path="source" id="source" cssClass="form-control" placeholder="Enter departure city (e.g. Bangalore)"/>
                <form:errors path="source" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="destination">To</label>
                <form:input path="destination" id="destination" cssClass="form-control" placeholder="Enter destination city (e.g. Hyderabad)"/>
                <form:errors path="destination" cssClass="field-error"/>
            </div>

            <button type="submit" class="btn btn-primary btn-block">
                <i class="fa-solid fa-magnifying-glass"></i> Search Flights
            </button>
        </form:form>
    </div>

    <!-- User Actions Card (Wireframe Page 5) -->
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">
                <i class="fa-solid fa-users-gear" style="color: #6366f1;"></i> User Actions
            </h2>
        </div>
        <c:choose>
            <c:when test="${empty sessionScope.currentUser}">
                <p style="color: var(--text-muted); margin-bottom: 1.5rem; line-height: 1.6;">
                    Please login to book flights and access your account, tickets, and bookings history.
                </p>
                <div style="display: flex; flex-direction: column; gap: 0.75rem;">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-block">
                        <i class="fa-solid fa-right-to-bracket"></i> Login
                    </a>
                    <a href="${pageContext.request.contextPath}/signup" class="btn btn-outline btn-block">
                        <i class="fa-solid fa-user-plus"></i> Sign Up
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <p style="color: var(--text-muted); margin-bottom: 1rem; line-height: 1.6;">
                    Welcome back, <strong>${sessionScope.currentUser.email}</strong>!
                </p>
                <div style="display: flex; flex-direction: column; gap: 0.75rem;">
                    <c:choose>
                        <c:when test="${sessionScope.currentUser.admin}">
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-primary btn-block">
                                <i class="fa-solid fa-gauge-high"></i> Go to Admin Dashboard
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/view-flights" class="btn btn-outline btn-block">
                                <i class="fa-solid fa-list-check"></i> View Flight Journeys
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-primary btn-block">
                                <i class="fa-solid fa-gauge-high"></i> Go to User Dashboard
                            </a>
                            <a href="${pageContext.request.contextPath}/user/my-bookings" class="btn btn-outline btn-block">
                                <i class="fa-solid fa-ticket"></i> View My Bookings
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Available Flights Table (Wireframe Page 5 Bottom) -->
<c:if test="${searchPerformed}">
    <div class="card" style="margin-top: 1rem;">
        <div class="card-header">
            <h3 class="card-title">
                <i class="fa-solid fa-plane-departure" style="color: #6366f1;"></i> Available Flights
                <c:if test="${not empty searchedSource and not empty searchedDestination}">
                    <span style="font-size: 0.9rem; font-weight: normal; color: var(--text-muted); margin-left: 0.5rem;">
                        (${searchedSource} &rarr; ${searchedDestination})
                    </span>
                </c:if>
            </h3>
        </div>

        <c:choose>
            <c:when test="${not empty availableFlights}">
                <div class="table-container">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Flight</th>
                                <th>Route</th>
                                <th>Schedule</th>
                                <th>Price</th>
                                <th style="text-align: right;">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="flight" items="${availableFlights}">
                                <tr>
                                    <td>
                                        <span class="badge badge-flight">${flight.flightNumber}</span>
                                    </td>
                                    <td>
                                        <strong>${flight.source}</strong> &rarr; <strong>${flight.destination}</strong>
                                    </td>
                                    <td>
                                        <div><small style="color: var(--text-muted);">Dep:</small> <strong>${flight.departureTime}</strong></div>
                                        <div><small style="color: var(--text-muted);">Arr:</small> <strong>${flight.arrivalTime}</strong></div>
                                    </td>
                                    <td>
                                        <span style="font-size: 1.05rem; font-weight: 700; color: #059669;">
                                            &#8377; ${flight.pricePerSeat}
                                        </span>
                                    </td>
                                    <td style="text-align: right;">
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.currentUser}">
                                                <a href="${pageContext.request.contextPath}/book-flight?flightId=${flight.flightId}" class="btn btn-primary btn-sm">
                                                    <i class="fa-solid fa-ticket"></i> Book
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/login?redirect=/book-flight?flightId=${flight.flightId}" class="btn btn-primary btn-sm">
                                                    <i class="fa-solid fa-right-to-bracket"></i> Book
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 2rem; color: var(--text-muted);">
                    <i class="fa-solid fa-plane-slash" style="font-size: 2.5rem; color: #9ca3af; margin-bottom: 0.75rem;"></i>
                    <p style="font-size: 1.1rem; font-weight: 600;">No flights found for this route.</p>
                    <p style="font-size: 0.9rem;">Try searching for Bangalore to Hyderabad or check another departure city.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>

<jsp:include page="common/footer.jsp"/>
