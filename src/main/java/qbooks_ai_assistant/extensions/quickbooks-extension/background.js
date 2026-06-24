console.log("🟣 Background worker running");

chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {

     console.log("📩 MESSAGE RECEIVED:", message);
     
    if (message.type === "PREDICT_TRANSACTIONS") {

        console.log("🏢 Company:", message.companyName);
        console.log("📨 Received transactions:", message.data);

       fetch("http://127.0.0.1:8080/api/unknown-transactions", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
    companyName: message.companyName,
    transactions: message.data
    })
})
.then(res => res.text())
.then(data => {

    console.log("✅ BACKEND RESPONSE:", data);

    sendResponse({
        success: true,
        data: data
    });
})
.catch(err => {

    console.error("❌ Backend error:", err);

    sendResponse({
        success: false,
        error: err.toString()
    });
});

        return true; // IMPORTANT (async response)
    }
});