/**
 * view-controller for logoff buttons wherever needed
 */
$(document).ready(function () {
    $("#logoff").click(sendLogoff);

});

/**
 * logs off the currently logged in user
 */
function sendLogoff() {
    $
        .ajax({
            url: "./resource/user/logoff",
            dataType: "text",
            type: "DELETE"
        })
        .done(function () {
            window.location.href = "./index.html";
        })
        .fail(function (xhr, status, errorThrown) {
            alert("Something went wrong");
        })
}
