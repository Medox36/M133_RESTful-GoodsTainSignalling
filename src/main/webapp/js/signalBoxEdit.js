/**
 * view-controller for signalBoxEdit.html
 * @author Lorenzo Giuntini (Medox36)
 */
document.addEventListener("DOMContentLoaded", () => {
    readSignalBox();

    document.getElementById("signalBoxEditForm").addEventListener("submit", saveSignalBox);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a signalBox
 */
function saveSignalBox(event) {
    event.preventDefault();

    const locoForm = document.getElementById("signalBoxEditForm");
    const formData = new FormData(locoForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/signalbox/";
    const trackSection = getQueryParam("trackSection");
    if (trackSection == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                console.log(response);
            } else return response;
        })
        .then(function () {
            window.location.href = "./signalBoxList.html";
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a signalBox
 */
function readSignalBox() {
    const trackSection = getQueryParam("trackSection");
    fetch("./resource/signalbox/read?trackSection=" + trackSection)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showSignalBox(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a signalBox
 * @param data  the signalBox-data
 */
function showSignalBox(data) {
    document.getElementById("trackSection").value = data.trackSection;
    document.getElementById("workingSignalmen").value = data.workingSignalmen;

    const userRole = getCookie("plainUserRole");
    const locked =  !(userRole === "admin");
    lockForm("signalBoxEditForm", locked);
}

/**
 * redirects to the locomotiveList
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./signalBoxList.html";
}