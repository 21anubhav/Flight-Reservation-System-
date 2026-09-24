<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle != null ? pageTitle : 'Flight Reservation System'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<header class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand">
        <i class="fa-solid fa-plane-departure" style="color: #86bc25;"></i>
        <span>Flight Reservation<span class="brand-dot">.</span></span>
    </a>

    <nav>
        <ul class="navbar-nav">
            <li><a href="${pageContext.request.contextPath}/"><i class="fa-solid fa-house"></i> Home</a></li>

            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <c:choose>
                        <c:when test="${sessionScope.currentUser.admin}">
                            <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa-solid fa-gauge-high"></i> Dashboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/add-flight"><i class="fa-solid fa-plus-circle"></i> Add Flight</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/view-flights"><i class="fa-solid fa-list-check"></i> View Flights</a></li>
                            <li class="nav-user-badge">
                                <i class="fa-solid fa-user-shield"></i>
                                <span>${sessionScope.currentUser.email}</span>
                                <span class="role-tag">Admin</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/user/dashboard"><i class="fa-solid fa-gauge-high"></i> Dashboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/user/my-bookings"><i class="fa-solid fa-ticket"></i> My Bookings</a></li>
                            <li class="nav-user-badge">
                                <i class="fa-solid fa-circle-user"></i>
                                <span>${sessionScope.currentUser.email}</span>
                                <span class="role-tag" style="background:#e5e7eb; color:#1f2937;">User</span>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <li><a href="${pageContext.request.contextPath}/logout" style="color: #f87171;"><i class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/login"><i class="fa-solid fa-right-to-bracket"></i> Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/signup" class="btn btn-primary btn-sm"><i class="fa-solid fa-user-plus"></i> Sign Up</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>

<main class="main-content">
