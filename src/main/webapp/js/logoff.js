$(document).ready(function () {
    $("#logoff").click(sendLogoff);

});

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