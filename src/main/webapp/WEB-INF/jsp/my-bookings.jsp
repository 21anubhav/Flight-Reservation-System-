<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="My Bookings - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">
        <i class="fa-solid fa-circle-check"></i> ${successMessage}
    </div>
</c:if>

<div class="card">
    <div class="card-header">
        <h2 class="card-title">
            <i class="fa-solid fa-ticket" style="color: #6366f1;"></i> My Bookings
        </h2>
        <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-secondary btn-sm">
            <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>

    <c:choose>
        <c:when test="${not empty bookings}">
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Booking ID</th>
                            <th>Flight Number</th>
                            <th>Route</th>
                            <th>Journey Date</th>
                            <th>Seats</th>
                            <th>Total Fare</th>
                            <th>Status</th>
                            <th>Action</th>
                            <th>Cancel</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${bookings}">
                            <tr>
                                <td>
                                    <strong>#${b.bookingId}</strong>
                                </td>
                                <td>
                                    <span class="badge badge-flight">${b.flightNumber}</span>
                                </td>
                                <td>
                                    ${b.source} &rarr; ${b.destination}
                                </td>
                                <td>
                                    <strong>${b.journeyDate}</strong>
                                </td>
                                <td>
                                    ${b.seats}
                                </td>
                                <td>
                                    <strong style="color: #059669;">&#8377; ${b.totalPrice}</strong>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${b.status == 'CONFIRMED'}">
                                            <span class="badge badge-success">Confirmed</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">Cancelled</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <!-- View Ticket Function (Page 9) -->
                                    <a href="${pageContext.request.contextPath}/user/view-tickets/${b.bookingId}" class="btn btn-primary btn-sm">
                                        <i class="fa-solid fa-eye"></i> View Ticket
                                    </a>
                                </td>
                                <td>
                                    <!-- Cancel Ticket Function (Page 9) -->
                                    <c:choose>
                                        <c:when test="${b.status == 'CONFIRMED'}">
                                            <form method="post" action="${pageContext.request.contextPath}/user/cancel-booking/${b.bookingId}" 
                                                  onsubmit="return confirm('Are you sure you want to cancel booking #${b.bookingId}? This will release the seats.');" 
                                                  style="display: inline;">
                                                <button type="submit" class="btn btn-danger btn-sm">
                                                    <i class="fa-solid fa-ban"></i> Cancel
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Cancel button must be disabled upon successful cancellation (Page 9) -->
                                            <button class="btn btn-sm" disabled style="background-color: #e5e7eb; color: #9ca3af; cursor: not-allowed;">
                                                <i class="fa-solid fa-ban"></i> Cancelled
                                            </button>
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
            <div style="text-align: center; padding: 3rem; color: var(--text-muted);">
                <i class="fa-solid fa-plane-slash" style="font-size: 2.5rem; color: #9ca3af; margin-bottom: 0.75rem;"></i>
                <p style="font-size: 1.1rem; font-weight: 600;">You have no bookings yet.</p>
                <p style="font-size: 0.9rem; margin-bottom: 1.5rem;">Ready to travel? Search flights and book your tickets now.</p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                    <i class="fa-solid fa-magnifying-glass"></i> Search Flights
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="common/footer.jsp"/>
