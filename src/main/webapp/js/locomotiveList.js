/**
 * view-controller for locomotiveList.html
 * @author Lorenzo Giuntini (Medox36)
 */
document.addEventListener("DOMContentLoaded", () => {
    readLocomotives();
});

/**
 * reads all locomotives
 */
function readLocomotives() {
    fetch("./resource/locomotive/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showLocomotiveList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the locomotiveList as a table
 * @param data  the books
 */
function showLocomotiveList(data) {
    const userRole = getCookie("plainUserRole");
    let tBody = document.getElementById("locolist");
    data.forEach(locomotive => {
        let row = tBody.insertRow(-1);
        row.insertCell(-1).innerHTML = locomotive.series;
        row.insertCell(-1).innerHTML = locomotive.operationNumber;
        row.insertCell(-1).innerHTML = locomotive.railwayCompany;
        row.insertCell(-1).innerHTML = locomotive.commissioningDate;
        row.insertCell(-1).innerHTML = locomotive.signalBox.trackSection;

        let button = document.createElement("button");
        button.innerHTML = "Bearbeiten ...";
        button.type = "button";
        button.name = "editLoco";
        if (userRole !== "admin") {
            button.innerHTML = "&#128065;";
        }
        button.setAttribute("data-series", locomotive.series);
        button.setAttribute("data-oN", locomotive.operationNumber);
        button.addEventListener("click", editLoco);
        row.insertCell(-1).appendChild(button);

        button = document.createElement("button");
        button.innerHTML = "Löschen ...";
        button.type = "button";
        button.name = "deleteLoco";
        if (userRole !== "admin") {
            button.disabled = true;
        }
        button.setAttribute("data-series", locomotive.series);
        button.setAttribute("data-oN", locomotive.operationNumber);
        button.addEventListener("click", deleteLoco);
        row.insertCell(-1).appendChild(button);

    });
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editLoco(event) {
    const button = event.target;
    const series = button.getAttribute("data-series");
    const oN = button.getAttribute("data-oN");
    window.location.href = "./locomotiveEdit.html?series=" + series + "&opn=" + oN;
}

/**
 * deletes a locomotive
 * @param event  the click-event
 */
function deleteLoco(event) {
    const button = event.target;
    const series = button.getAttribute("data-series");
    const oN = button.getAttribute("data-oN");

    fetch("./resource/book/delete?series=" + series + "&opn=" + oN,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./locomotiveList.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}