<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="My Tickets - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="card no-print" style="margin-bottom: 1.5rem;">
    <div class="card-header">
        <div>
            <h2 class="card-title">
                <i class="fa-solid fa-ticket-simple" style="color: #6366f1;"></i> My Tickets
            </h2>
            <div style="font-size: 0.9rem; color: var(--text-muted); margin-top: 0.25rem;">
                View and download your flight tickets for Booking <strong>#${booking.bookingId}</strong>
            </div>
        </div>
        <div style="display: flex; gap: 0.75rem;">
            <button onclick="window.print()" class="btn btn-primary btn-sm">
                <i class="fa-solid fa-print"></i> Print Tickets
            </button>
            <a href="${pageContext.request.contextPath}/user/my-bookings" class="btn btn-secondary btn-sm">
                <i class="fa-solid fa-arrow-left"></i> Back to My Bookings
            </a>
        </div>
    </div>
</div>

<div class="grid-2">
    <c:forEach var="ticket" items="${tickets}" varStatus="status">
        <!-- Ticket Boarding Pass Card (Wireframe Page 9) -->
        <div class="ticket-card">
            <!-- Ticket Header -->
            <div class="ticket-header">
                <div style="display: flex; align-items: center; gap: 0.5rem; font-weight: 700; font-size: 1.1rem;">
                    <i class="fa-solid fa-plane-departure" style="color: #86bc25;"></i>
                    <span>Flight Ticket</span>
                </div>
                <span class="badge" style="background-color: #3b82f6; color: #ffffff; font-size: 0.9rem; padding: 0.35rem 0.75rem;">
                    ${ticket.flightNumber != null ? ticket.flightNumber : booking.flightNumber}
                </span>
            </div>

            <!-- Route Banner -->
            <div style="background-color: #f1f5f9; padding: 0.75rem 1.5rem; border-bottom: 1px solid var(--border-color); display: flex; justify-content: space-between; font-size: 0.9rem;">
                <div>
                    <strong>${booking.source}</strong> &rarr; <strong>${booking.destination}</strong>
                </div>
                <div style="color: var(--text-muted);">
                    Dep: <strong>${booking.departureTime}</strong> | Arr: <strong>${booking.arrivalTime}</strong>
                </div>
            </div>

            <!-- Ticket Body -->
            <div class="ticket-body">
                <!-- Passenger Information -->
                <div>
                    <h4 style="font-size: 0.75rem; text-transform: uppercase; color: var(--text-muted); margin-bottom: 0.5rem; border-bottom: 1px solid #f1f5f9; padding-bottom: 0.25rem;">
                        <i class="fa-solid fa-user"></i> Passenger Information
                    </h4>
                    <div style="margin-bottom: 0.5rem;">
                        <small style="color: var(--text-muted); display: block;">Name</small>
                        <strong style="font-size: 1.05rem;">${ticket.passengerName}</strong>
                    </div>
                    <div style="display: flex; gap: 1.5rem;">
                        <div>
                            <small style="color: var(--text-muted); display: block;">Gender</small>
                            <strong>${ticket.gender}</strong>
                        </div>
                        <div>
                            <small style="color: var(--text-muted); display: block;">Age</small>
                            <strong>${ticket.age} yrs</strong>
                        </div>
                    </div>
                </div>

                <!-- Travel Information -->
                <div>
                    <h4 style="font-size: 0.75rem; text-transform: uppercase; color: var(--text-muted); margin-bottom: 0.5rem; border-bottom: 1px solid #f1f5f9; padding-bottom: 0.25rem;">
                        <i class="fa-solid fa-calendar"></i> Travel Information
                    </h4>
                    <div style="margin-bottom: 0.5rem;">
                        <small style="color: var(--text-muted); display: block;">Journey Date</small>
                        <strong style="font-size: 1.05rem;">${ticket.journeyDate}</strong>
                    </div>
                    <div>
                        <small style="color: var(--text-muted); display: block;">Seat Number</small>
                        <span class="badge" style="background-color: #e0e7ff; color: #4338ca; font-size: 1rem; padding: 0.25rem 0.75rem; font-weight: 800;">
                            ${ticket.seatNo}
                        </span>
                    </div>
                </div>
            </div>

            <!-- Ticket Footer -->
            <div class="ticket-footer">
                <div>
                    <small style="color: var(--text-muted); display: block;">Booking Details</small>
                    <span style="font-size: 0.95rem; font-weight: 700; color: #4f46e5;">
                        Booking #${ticket.bookingId}
                    </span>
                    <span style="font-size: 0.8rem; color: var(--text-muted); margin-left: 0.5rem;">
                        (PNR: ${ticket.pnrNo})
                    </span>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${ticket.status == 'CONFIRMED'}">
                            <span class="badge badge-success" style="font-size: 0.85rem; padding: 0.35rem 0.75rem;">
                                <i class="fa-solid fa-circle-check"></i> BOOKED
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger" style="font-size: 0.85rem; padding: 0.35rem 0.75rem;">
                                <i class="fa-solid fa-ban"></i> CANCELLED
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="common/footer.jsp"/>
