$(document).ready(function () {
    $("#loginForm").submit(sendLogin);
    $("#2factorForm").submit(send2factor);
    $("#cancel").click(sendLogoff);

    document.getElementById("2factorForm").style.display = "none";
    document.getElementById("cancel").style.display = "none";
});

function sendLogin(form) {
    form.preventDefault();

    $
        .ajax({
        url: "./resource/user/login",
        dataType: "text",
        type: "POST",
        data: $("#loginForm").serialize()
        })
        .done(function (data) {
            document.getElementById("2factorForm").style.display = "block";
            document.getElementById("loginForm").style.display = "none";

            $("#wordMessage").text("Geben Sie Ihr " + data + " Wort ein");
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status = 404) {
                $("#message").text("Benutzername/Passwort unbekannt");
            } else {
                $("#message").text("Es ist ein Fehler aufgetreten");
            }
        })
}

function send2factor(form) {
    form.preventDefault();

    $
        .ajax({
            url: "./resource/user/2factor",
            dataType: "text",
            type: "POST",
            data: $("form").serialize()
        })
        .done(function () {
            window.location.href = "./locoList.html";
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status = 400) {
                $("#message").text("Falsches Wort");
            } else {
                $("#message").text("Es ist ein Fehler aufgetreten");
                document.getElementById("cancel").style.display = "block";

            }
        })
}

function sendLogoff() {
    $
        .ajax({
            url: "./resource/user/logoff",
            dataType: "text",
            type: "DELETE"
        })
        .data(function () {
            window.location.href = "./index.html";
        })
        .fail(function (xhr, status, errorThrown) {
            alert("Something went wrong");
        })
}