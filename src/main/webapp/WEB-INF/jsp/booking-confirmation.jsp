<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Booking Confirmed - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="confirmation-container">
    <div class="confirmation-hero">
        <div style="font-size: 2.5rem; margin-bottom: 0.5rem;">
            <i class="fa-solid fa-circle-check"></i>
        </div>
        <h2>Booking Confirmed!</h2>
    </div>

    <div class="confirmation-card-content">
        <div style="color: #4b5563; font-size: 1.25rem; font-weight: 600; margin-bottom: 0.5rem;">
            Congratulations! Your flight is booked!
        </div>

        <div style="font-size: 1.4rem; font-weight: 800; color: #4338ca; margin: 1rem 0; padding: 0.75rem; background: #eef2ff; border-radius: 8px; border: 1px dashed #6366f1;">
            <i class="fa-solid fa-receipt"></i> Booking ID: ${booking.bookingId}
        </div>

        <div style="text-align: left; background: #f8fafc; border: 1px solid var(--border-color); border-radius: 8px; padding: 1.25rem; margin: 1.5rem 0;">
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                <span>Flight Number:</span> <strong>${booking.flightNumber}</strong>
            </div>
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                <span>Route:</span> <strong>${booking.source} &rarr; ${booking.destination}</strong>
            </div>
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                <span>Journey Date:</span> <strong>${booking.journeyDate}</strong>
            </div>
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                <span>Passengers / Seats:</span> <strong>${booking.seats}</strong>
            </div>
            <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                <span>Total Fare Paid:</span> <strong style="color: #059669;">&#8377; ${booking.totalPrice}</strong>
            </div>
            <div style="display: flex; justify-content: space-between;">
                <span>Status:</span> <span class="badge badge-success">${booking.status}</span>
            </div>
        </div>

        <!-- 3 Feature Badges (Wireframe Page 7) -->
        <div class="feature-badges-row">
            <div class="feature-badge-item">
                <i class="fa-solid fa-shield-halved icon" style="color: #10b981;"></i>
                <span class="label">Secured</span>
                <span class="sublabel">Your booking is protected</span>
            </div>
            <div class="feature-badge-item">
                <i class="fa-solid fa-envelope icon" style="color: #3b82f6;"></i>
                <span class="label">Email Sent</span>
                <span class="sublabel">Confirmation details sent</span>
            </div>
            <div class="feature-badge-item">
                <i class="fa-solid fa-clock icon" style="color: #8b5cf6;"></i>
                <span class="label">Ready</span>
                <span class="sublabel">Check-in 24hrs before</span>
            </div>
        </div>

        <!-- Next Steps (Wireframe Page 7) -->
        <div style="text-align: left; margin: 1.5rem 0;">
            <h4 style="font-size: 1rem; font-weight: 700; color: var(--text-main); margin-bottom: 0.5rem;">
                Next Steps:
            </h4>
            <ul class="next-steps-list">
                <li><i class="fa-solid fa-check" style="color: #10b981; margin-right: 0.5rem;"></i> Save your booking ID for future reference</li>
                <li><i class="fa-solid fa-check" style="color: #10b981; margin-right: 0.5rem;"></i> Check your email for detailed itinerary</li>
                <li><i class="fa-solid fa-check" style="color: #10b981; margin-right: 0.5rem;"></i> Arrive at airport 2 hours before departure</li>
                <li><i class="fa-solid fa-check" style="color: #10b981; margin-right: 0.5rem;"></i> Complete online check-in 24 hours before flight</li>
            </ul>
        </div>

        <div style="display: flex; gap: 1rem; justify-content: center; margin-top: 2rem;">
            <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-success" style="flex: 1;">
                <i class="fa-solid fa-gauge-high"></i> Go to Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/user/view-tickets/${booking.bookingId}" class="btn btn-outline" style="flex: 1;">
                <i class="fa-solid fa-ticket"></i> View Tickets
            </a>
            <button onclick="window.print()" class="btn btn-outline" style="flex: 1;">
                <i class="fa-solid fa-print"></i> Print Confirmation
            </button>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
