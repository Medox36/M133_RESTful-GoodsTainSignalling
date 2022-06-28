/**
 * view-controller for locomotiveEdit.html
 * @author Lorenzo Giuntini (Medox36)
 */
document.addEventListener("DOMContentLoaded", () => {
    readSignalBoxes();
    readLoco();

    document.getElementById("locoeditForm").addEventListener("submit", saveLoco);
    document.getElementById("cancel").addEventListener("click", cancelEdit);

    /*if (getCookie("plainUserRole") !== "admin") {
        document.getElementById("save").disable = true;
        document.getElementById("reset").disable = true;
    }*/
});

/**
 * saves the data of a locomotive
 */
function saveLoco(event) {
    event.preventDefault();

    const locoForm = document.getElementById("locoeditForm");
    const formData = new FormData(locoForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/locomotive/";
    const series = getQueryParam("series");
    const op = getQueryParam("opn")
    if (series == null || op == null) {
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
            window.location.href = "./locomotiveList.html";
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a locomotive
 */
function readLoco() {
    const series = getQueryParam("series");
    const opn = getQueryParam("opn");
    fetch("./resource/locomotive/read?series=" + series + "&opn=" + opn)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showLoco(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a locomotive
 * @param data  the locomotive-data
 */
function showLoco(data) {
    document.getElementById("series").value = data.series;
    document.getElementById("oN").value = data.operationNumber;
    document.getElementById("railwayCompany").value = data.railwayCompany;
    document.getElementById("commissioningDate").value = data.commissioningDate;
    document.getElementById("signalBoxTrackSection").value = data.signalBox.trackSection;

    const userRole = getCookie("plainUserRole");
    const locked =  !(userRole === "admin");
    lockForm("locoeditForm", locked);
}

/**
 * reads all signalBoxes as an array
 */
function readSignalBoxes() {

    fetch("./resource/signalbox/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showSignalBoxes(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all trackSections of all signalBoxes as a dropdown
 * @param data
 */
function showSignalBoxes(data) {
    let dropdown = document.getElementById("signalBoxTrackSection");
    data.forEach(signalBox => {
        let option = document.createElement("option");
        option.text = signalBox.trackSection;
        option.value = signalBox.trackSection;
        dropdown.add(option);
    })
}

/**
 * redirects to the locomotiveList
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./locomotiveList.html";
}