(function () {

    console.log("🟢 QBO DOM Extractor V5 running");

    let lastRun = 0;
    let lastHash = "";
    let timeout = null;
    let isSending = false;

    function isLikelyTransaction(text) {

        if (!text) return false;

        const hasAmount = /(\d{1,3}(,\d{3})*|\d+)(\.\d{2})?/.test(text);

        const noisePatterns = [
            "For Review",
            "Add",
            "Filter",
            "Search",
            "Sort",
            "Date",
            "Amount",
            "Category",
            "Bank",
            "Reviewed",
            "Posted"
        ];

        const isNoise = noisePatterns.some(p => text.includes(p));

        return hasAmount && !isNoise;
    }

    function parseTransactionRow(text) {

        const lines = text.split("\n").map(l => l.trim()).filter(Boolean);

        const amountMatch = text.match(/(\d{1,3}(?:,\d{3})*|\d+)(\.\d{2})?/g);
        const amount = amountMatch ? amountMatch[amountMatch.length - 1] : null;

        const description = lines.find(l => !/\d/.test(l)) || lines[0];

        return {
            date: "",
            description: description || "UNKNOWN",
            amount: amount ? parseFloat(amount.replace(/,/g, "")) : 0,
            bankAccount: "",
            reference: ""
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
    companyElement?.innerText?.trim() || "UNKNOWN_COMPANY";

console.log("🏢 Company:", companyName);

        const results = [];

        const rows = document.querySelectorAll(
            "[role='row'], div[class*='row'], div[class*='gridRow'], div[class*='TableRow']"
        );

        rows.forEach(row => {

            const text = row.innerText;

            if (!text || text.length < 10) return;

            if (!isLikelyTransaction(text)) return;

            const parsed = parseTransactionRow(text);

            if (parsed.description && parsed.amount !== null) {
                results.push(parsed);
            }
        });

        const hash = hashData(results);

        if (hash === lastHash) return;
        lastHash = hash;

        if (results.length > 0) {

            console.log("🔥 CLEAN FOR REVIEW TRANSACTIONS:");
            console.table(results);

            if (isSending) return;
            isSending = true;

            console.log("🚀 Sending to background script...");

            chrome.runtime.sendMessage(
        {
           type: "PREDICT_TRANSACTIONS",
            companyName: companyName,
             data: results
            },
                (response) => {

                    isSending = false; // ✅ IMPORTANT FIX

                    if (chrome.runtime.lastError) {
                        console.error("❌ Extension error:", chrome.runtime.lastError);
                        return;
                    }

                    console.log("🤖 AI RESPONSE FROM BACKGROUND:", response);

                    if (response?.data) {
                        console.table(response.data);
                    }
                }
            );

        } else {
            console.log("⚠️ No transactions detected yet");
        }
    }

    function scheduleExtract() {

        const now = Date.now();

        // max 1 run per 3 seconds
        if (now - lastRun < 3000) return;

        clearTimeout(timeout);

        timeout = setTimeout(() => {
            lastRun = Date.now();
            extract();
        }, 1200);
    }

    // STARTUP
    setTimeout(scheduleExtract, 5000);

    const observer = new MutationObserver(() => {
        scheduleExtract();
    });

    observer.observe(document.body, {
        childList: true,
        subtree: true
    });

})();