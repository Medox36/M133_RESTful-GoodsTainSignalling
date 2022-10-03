/**
 * view-controller for login and logoff buttons wherever needed
 * @author Lorenzo Giuntini (Medox36)
 */
document.addEventListener("DOMContentLoaded", () => {

    const userRole = getCookie("plainUserRole");
    if (userRole === "guest" || userRole === "user" || userRole === "admin") {
        document.getElementById("login").style.display = "none";
    } else {
        document.getElementById("logoff").style.display = "none";
    }
});