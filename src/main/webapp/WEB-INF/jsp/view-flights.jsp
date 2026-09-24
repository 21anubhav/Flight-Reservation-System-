<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="View Flights - Flight Reservation System" scope="request"/>
<jsp:include page="common/header.jsp"/>

<div class="card">
    <div class="card-header">
        <div>
            <h2 class="card-title">
                <i class="fa-solid fa-plane-up" style="color: #6366f1;"></i> Flight Journey Status
            </h2>
            <div style="font-size: 0.9rem; color: var(--text-muted); margin-top: 0.25rem;">
                Current Date: <strong>${currentDate}</strong>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary btn-sm">
            <i class="fa-solid fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>

    <!-- Two Tabs: Upcoming Journeys vs Completed Journey (Wireframe Page 8) -->
    <div class="tabs-nav">
        <a href="${pageContext.request.contextPath}/admin/view-flights?tab=upcoming" 
           class="tab-link ${activeTab == 'upcoming' ? 'active' : ''}">
            <i class="fa-solid fa-calendar-days"></i> Upcoming Journeys
            <span class="badge ${activeTab == 'upcoming' ? 'badge-primary' : 'badge-info'}" style="margin-left: 0.25rem;">
                ${upcomingJourneys.size()}
            </span>
        </a>
        <a href="${pageContext.request.contextPath}/admin/view-flights?tab=completed" 
           class="tab-link ${activeTab == 'completed' ? 'active' : ''}">
            <i class="fa-solid fa-calendar-check"></i> Completed Journey
            <span class="badge badge-info" style="margin-left: 0.25rem;">
                ${completedJourneys.size()}
            </span>
        </a>
    </div>

    <div style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 1rem; font-style: italic;">
        * Note: Showing only flights for which bookings are currently confirmed.
    </div>

    <c:choose>
        <c:when test="${not empty journeys}">
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Flight Number</th>
                            <th>Journey Date</th>
                            <th>Total Passengers</th>
                            <th>Total Bookings</th>
                            <th>Total Revenue</th>
                            <th>Days Until Journey</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${journeys}">
                            <tr>
                                <td>
                                    <span class="badge badge-flight">${item.flightNumber}</span>
                                </td>
                                <td>
                                    <strong>${item.journeyDate}</strong>
                                </td>
                                <td>
                                    <span class="badge badge-info" style="font-size: 0.85rem;">
                                        <i class="fa-solid fa-users"></i> ${item.totalPassengers}
                                    </span>
                                </td>
                                <td>
                                    <strong>${item.totalBookings}</strong>
                                </td>
                                <td>
                                    <strong style="color: #059669; font-size: 1rem;">&#8377; ${item.totalRevenue}</strong>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.completed}">
                                            <span class="badge badge-success">Completed</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-info">${item.daysUntilJourney}</span>
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
            <div style="text-align: center; padding: 2.5rem; color: var(--text-muted);">
                <i class="fa-regular fa-calendar-xmark" style="font-size: 2.5rem; color: #9ca3af; margin-bottom: 0.75rem;"></i>
                <p style="font-size: 1.1rem; font-weight: 600;">No ${activeTab} journeys with bookings found.</p>
                <p style="font-size: 0.9rem;">Bookings will show up here once passengers reserve tickets for flights.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="common/footer.jsp"/>
