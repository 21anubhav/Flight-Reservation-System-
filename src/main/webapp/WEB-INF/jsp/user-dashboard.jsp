<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="User Dashboard - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">
        <i class="fa-solid fa-circle-check"></i> ${successMessage}
    </div>
</c:if>

<!-- User Dashboard Greeting (Wireframe Page 9) -->
<div style="margin-bottom: 2rem;">
    <div style="display: flex; align-items: center; gap: 0.75rem; margin-bottom: 0.25rem;">
        <i class="fa-solid fa-circle-user" style="font-size: 1.75rem; color: #4f46e5;"></i>
        <h1 style="font-size: 1.75rem; font-weight: 700;">My Dashboard</h1>
    </div>
    <p style="color: var(--text-muted); font-size: 0.95rem;">
        Welcome, <strong>${user.email}</strong>
    </p>
</div>

<!-- Two Cards: My Bookings & Search Flights (Wireframe Page 9) -->
<div class="grid-2">
    <!-- My Bookings Card -->
    <div class="card" style="text-align: center; padding: 2.5rem 1.5rem;">
        <div style="width: 60px; height: 60px; border-radius: 50%; background: #e0e7ff; color: #4338ca; display: inline-flex; align-items: center; justify-content: center; font-size: 1.6rem; margin-bottom: 1rem;">
            <i class="fa-solid fa-ticket"></i>
        </div>
        <h3 style="font-size: 1.25rem; font-weight: 700; margin-bottom: 0.5rem;">My Bookings</h3>
        <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1.5rem; line-height: 1.5;">
            View your booked flight tickets, check journey details, print boarding passes, or manage cancellations.
        </p>
        <a href="${pageContext.request.contextPath}/user/my-bookings" class="btn btn-primary btn-block">
            <i class="fa-solid fa-receipt"></i> Go to My Bookings
        </a>
    </div>

    <!-- Search Flights Card -->
    <div class="card" style="text-align: center; padding: 2.5rem 1.5rem;">
        <div style="width: 60px; height: 60px; border-radius: 50%; background: #ecfdf5; color: #059669; display: inline-flex; align-items: center; justify-content: center; font-size: 1.6rem; margin-bottom: 1rem;">
            <i class="fa-solid fa-magnifying-glass"></i>
        </div>
        <h3 style="font-size: 1.25rem; font-weight: 700; margin-bottom: 0.5rem;">Search Flights</h3>
        <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1.5rem; line-height: 1.5;">
            Browse flight schedules across major destinations, check real-time seat availability, and book tickets instantly.
        </p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-success btn-block">
            <i class="fa-solid fa-plane-departure"></i> Search Flights
        </a>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
