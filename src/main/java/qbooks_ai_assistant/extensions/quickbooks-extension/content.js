(function () {

    console.log("🟢 QBO DOM Extractor V6 running");

    let lastRun = 0;
    let lastHash = "";
    let timeout = null;
    let isSending = false;

    function parseTransactionRow(row) {

    const description = row
        .querySelector("td.description")
        ?.innerText
        ?.replace(/\n/g, " ")
        ?.trim() || "";

    const amounts =
        row.innerText.match(/-?[\d,]+\.\d{2}/g);

    const amount =
        amounts
            ? parseFloat(
                amounts[amounts.length - 1]
                    .replace(/,/g, "")
              )
            : 0;

    const action =
        row.querySelector("td:last-child")
            ?.textContent
            ?.trim() || "";

    return {
        date: "",
        description: description,
        amount: amount,
        bankAccount: "",
        reference: "",
        action: action
    };
}

    function hashData(data) {
        return JSON.stringify(data);
    }

    function extract() {

        console.log("🧪 extract() triggered");

        const companyElement =
            document.querySelector(".client-switcher-title");

        const companyName =
            companyElement?.textContent?.trim() || "UNKNOWN_COMPANY";

        console.log("🏢 Company:", companyName);

        const results = [];

        const rows = document.querySelectorAll("tr[role='row']");

        console.log("Rows found:", rows.length);

        rows.forEach(row => {

    const parsed = parseTransactionRow(row);

    if (!parsed.description) return;

    if (parsed.amount === 0) return;

    // Ignore transactions already processed by QuickBooks
    if (parsed.action !== "Add") return;

    results.push(parsed);

});

        const hash = hashData(results);

        if (hash === lastHash) return;

        lastHash = hash;

        if (results.length > 0) {

            console.log("🔥 CLEAN FOR REVIEW TRANSACTIONS");

            console.table(results);

            if (isSending) return;

            isSending = true;

            console.log("🚀 Sending to Spring Boot...");

            chrome.runtime.sendMessage(
                {
                    type: "PREDICT_TRANSACTIONS",
                    companyName: companyName,
                    data: results
                },
                (response) => {

                    isSending = false;

                    if (chrome.runtime.lastError) {

                        console.error(chrome.runtime.lastError);

                        return;
                    }

                    console.log("🤖 AI RESPONSE");

                    console.log(response);

                    if (response?.data) {

                        console.table(response.data);

                    }

                }
            );

        } else {

            console.log("⚠️ No transactions found.");

        }

    }

    function scheduleExtract() {

        const now = Date.now();

        if (now - lastRun < 3000) return;

        clearTimeout(timeout);

        timeout = setTimeout(() => {

            lastRun = Date.now();

            extract();

        }, 1200);

    }

    setTimeout(scheduleExtract, 5000);

    const observer = new MutationObserver(() => {

        scheduleExtract();

    });

    observer.observe(document.body, {

        childList: true,
        subtree: true

    });

})();