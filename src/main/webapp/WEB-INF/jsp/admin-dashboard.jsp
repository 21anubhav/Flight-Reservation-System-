<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Admin Dashboard - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">
        <i class="fa-solid fa-circle-check"></i> ${successMessage}
    </div>
</c:if>

<!-- Admin Dashboard Greeting (Wireframe Page 8) -->
<div style="margin-bottom: 2rem;">
    <div style="display: flex; align-items: center; gap: 0.75rem; margin-bottom: 0.25rem;">
        <i class="fa-solid fa-user-shield" style="font-size: 1.75rem; color: #4f46e5;"></i>
        <h1 style="font-size: 1.75rem; font-weight: 700;">Admin Dashboard</h1>
    </div>
    <p style="color: var(--text-muted); font-size: 0.95rem;">
        Logged in as: <strong>${adminUser.email}</strong>
    </p>
</div>

<!-- Flight Management Section (Wireframe Page 8) -->
<div class="card">
    <div class="card-header">
        <h3 class="card-title">
            <i class="fa-solid fa-sliders" style="color: #6366f1;"></i> Flight Management
        </h3>
    </div>

    <div class="grid-2">
        <!-- Add New Flight Card -->
        <div style="border: 1px solid var(--border-color); border-radius: 8px; padding: 2rem 1.5rem; text-align: center; background: #fafafa;">
            <div style="width: 56px; height: 56px; border-radius: 50%; background: #e0e7ff; color: #4338ca; display: inline-flex; align-items: center; justify-content: center; font-size: 1.5rem; margin-bottom: 1rem;">
                <i class="fa-solid fa-plus"></i>
            </div>
            <h4 style="font-size: 1.2rem; font-weight: 700; margin-bottom: 0.5rem;">Add New Flight</h4>
            <p style="color: var(--text-muted); font-size: 0.875rem; margin-bottom: 1.5rem; line-height: 1.5;">
                Create new flight route and schedule for reservations. Set departure/arrival times, default capacity, and fare.
            </p>
            <a href="${pageContext.request.contextPath}/admin/add-flight" class="btn btn-primary btn-block">
                <i class="fa-solid fa-plus-circle"></i> Add Flight
            </a>
        </div>

        <!-- View Flights Card -->
        <div style="border: 1px solid var(--border-color); border-radius: 8px; padding: 2rem 1.5rem; text-align: center; background: #fafafa;">
            <div style="width: 56px; height: 56px; border-radius: 50%; background: #ecfdf5; color: #059669; display: inline-flex; align-items: center; justify-content: center; font-size: 1.5rem; margin-bottom: 1rem;">
                <i class="fa-solid fa-list-check"></i>
            </div>
            <h4 style="font-size: 1.2rem; font-weight: 700; margin-bottom: 0.5rem;">View Flights</h4>
            <p style="color: var(--text-muted); font-size: 0.875rem; margin-bottom: 1.5rem; line-height: 1.5;">
                View and manage all existing flights schedules and journeys. Track upcoming vs. completed trips with passenger counts &amp; revenue.
            </p>
            <a href="${pageContext.request.contextPath}/admin/view-flights" class="btn btn-success btn-block">
                <i class="fa-solid fa-eye"></i> View Flights
            </a>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
