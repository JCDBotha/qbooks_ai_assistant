console.log("🟣 Background worker running");

chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {

    if (message.type === "PREDICT_TRANSACTIONS") {

        console.log("📨 Received transactions:", message.data);

        fetch("http://127.0.0.1:8080/api/transactions/predict", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(message.data)
        })
        .then(res => res.json())
        .then(data => {
            console.log("🤖 AI RESPONSE:", data);

            sendResponse({ success: true, data: data });
        })
        .catch(err => {
            console.error("❌ Backend error:", err);

            sendResponse({ success: false, error: err.toString() });
        });

        return true; // IMPORTANT (async response)
    }
});