console.log("✅ dashboard.js loaded");

const acceptButtons = document.querySelectorAll(".accept-btn");

console.log("Accept Once buttons found:", acceptButtons.length);

acceptButtons.forEach(button => {

    button.addEventListener("click", function (event) {

        event.preventDefault();

        console.log("Accept Once clicked");

        const row = this.closest("tr");

        console.log("Transaction ID:", row.dataset.id);
        console.log("Description:", row.dataset.description);
        console.log("Suggested Account:", row.dataset.account);

    });

});

 function showSuccessToast(message) {

    document.getElementById("toastMessage").innerText = message;

    const toastElement = document.getElementById("successToast");

    const toast = new bootstrap.Toast(toastElement, {

        delay: 2500

    });

    toast.show();

}

// CHANGE BUTTON
document.querySelectorAll(".remember-btn").forEach(button => {

    button.addEventListener("click", function () {

        const row = this.closest("tr");

       const dto = {

    transactionId: row.dataset.id,

    accountId: null,

    action: "ALWAYS"

};

        fetch("/dashboard/action", {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(dto)

        })
        .then(response => response.text())
.then(result => {

    console.log(result);

    showSuccessToast(result);

    setTimeout(() => {

        location.reload();

    }, 2500);

});

    });

});

// SAVE BUTTON - EventListener for the "Save Once" button
document.getElementById("saveOnceBtn")
.addEventListener("click", function () {

    const dto = {

        transactionId:
            document.getElementById("transactionId").value,

        accountId:
            document.getElementById("accountSelect").value,

        action: "ONCE"

    };

    fetch("/dashboard/action", {

        method: "POST",

        headers: {

            "Content-Type": "application/json"

        },

        body: JSON.stringify(dto)

    })
.then(response => response.text())
.then(result => {

    console.log(result);

    showSuccessToast(result);

    setTimeout(() => {

        location.reload();

    }, 2500);

});

});

//Remember Button - EventListener for the "Remember" button

document.getElementById("rememberBtn")
.addEventListener("click", function () {

    const dto = {

        transactionId:
            document.getElementById("transactionId").value,

        accountId:
            document.getElementById("accountSelect").value,

        action: "ALWAYS"

    };

    fetch("/dashboard/action", {

        method: "POST",

        headers: {

            "Content-Type": "application/json"

        },

        body: JSON.stringify(dto)

    })
    .then(response => response.text())
.then(result => {

    console.log(result);

    showSuccessToast(result);

    setTimeout(() => {
         location.reload();

    }, 2500);

});

});

// ACCEPT BUTTON
document.querySelectorAll(".accept-btn").forEach(button => {

    button.addEventListener("click", function () {

        const row = this.closest("tr");

        const dto = {

            transactionId: row.dataset.id,

            accountId: 0,

            action: "ACCEPT"

        };

        fetch("/dashboard/action", {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(dto)

        })
   .then(response => response.text())
.then(result => {

    console.log(result);

    showSuccessToast(result);

    setTimeout(() => {

        location.reload();

    }, 2500);

});

    });

});

// SELECT ALL
document.getElementById("selectAllCheckbox").addEventListener("change", function () {

    const checked = this.checked;

    document.querySelectorAll(".transaction-checkbox").forEach(cb => {
        cb.checked = checked;
    });

});

document.getElementById("selectAllBtn").addEventListener("click", function () {

    document.getElementById("selectAllCheckbox").checked = true;

    document.querySelectorAll(".transaction-checkbox").forEach(cb => {
        cb.checked = true;
    });

});

// ACCEPT SELECTED
document.getElementById("acceptSelectedBtn").addEventListener("click", function () {

    const selectedTransactions = [];

    document.querySelectorAll(".transaction-checkbox").forEach(cb => {

        if (cb.checked) {

            const row = cb.closest("tr");

            selectedTransactions.push(row.dataset.id);

        }

    });

    if (selectedTransactions.length === 0) {

        alert("Please select at least one transaction.");

        return;

    }

    fetch("/dashboard/accept-selected", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(selectedTransactions)

    })
   .then(response => response.text())
.then(result => {

    console.log(result);

    showSuccessToast(result);

    setTimeout(() => {

        location.reload();

    }, 2500);

});

});

// REMEMBER SELECTED
document.getElementById("rememberSelectedBtn").addEventListener("click", function () {

    const selectedTransactions = [];

    document.querySelectorAll(".transaction-checkbox").forEach(cb => {

        if (cb.checked) {

            selectedTransactions.push(cb.closest("tr").dataset.id);

        }

    });

    if (selectedTransactions.length === 0) {

        alert("Please select at least one transaction.");

        return;

    }

    fetch("/dashboard/remember-selected", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(selectedTransactions)

    })
    .then(response => response.text())
    .then(result => {

        console.log(result);

        showSuccessToast(result);

        setTimeout(() => {

            location.reload();

        }, 2500);

    });

});

// CHANGE DROPDOWN
document.querySelectorAll(".change-account").forEach(select => {

    select.addEventListener("change", function () {

        if (this.value === "") {
            return;
        }

        const row = this.closest("tr");

        document.getElementById("transactionId").value =
            row.dataset.id;

        document.getElementById("transactionDescription").innerText =
            row.dataset.description;

        document.getElementById("accountSelect").value =
            this.value;

        const modal = new bootstrap.Modal(
            document.getElementById("changeAccountModal")
        );

        modal.show();

    });

});


