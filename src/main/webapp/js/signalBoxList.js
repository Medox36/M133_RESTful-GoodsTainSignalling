/**
 * view-controller for locomotiveList.html
 * @author Lorenzo Giuntini (Medox36)
 */
document.addEventListener("DOMContentLoaded", () => {
    readSignalBoxes();
});

/**
 * reads all locomotives
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
            showSignalBoxList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the locomotiveList as a table
 * @param data  the books
 */
function showSignalBoxList(data) {
    const userRole = getCookie("plainUserRole");
    let tBody = document.getElementById("signalList");
    data.forEach(signalBox => {
        let row = tBody.insertRow(-1);
        row.insertCell(-1).innerHTML = signalBox.trackSection;
        row.insertCell(-1).innerHTML = signalBox.workingSignalmen;

        let button = document.createElement("button");
        button.innerHTML = "Bearbeiten ...";
        button.type = "button";
        button.name = "editSignalBox";
        if (userRole !== "admin") {
            button.innerHTML = "&#128065;";
        }
        button.setAttribute("data-trackSection", signalBox.trackSection);
        button.addEventListener("click", editSignalBox);
        row.insertCell(-1).appendChild(button);

        button = document.createElement("button");
        button.innerHTML = "Löschen ...";
        button.type = "button";
        button.name = "deleteSignalBox";
        if (userRole !== "admin") {
            button.disabled = true;
        }
        button.setAttribute("data-trackSection", signalBox.trackSection);
        button.addEventListener("click", deleteSignalBox);
        row.insertCell(-1).appendChild(button);

    });
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editSignalBox(event) {
    const button = event.target;
    const trackSection = button.getAttribute("data-trackSection");
    window.location.href = "./signalBoxEdit.html?trackSection=" + trackSection;
}

/**
 * deletes a locomotive
 * @param event  the click-event
 */
function deleteSignalBox(event) {
    const button = event.target;
    const trackSection = button.getAttribute("data-trackSection");

    fetch("./resource/signalbox/delete?trackSection=" + trackSection ,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./signalBoxList.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}