//A2HS
(function () {
    if ("serviceWorker" in navigator) {
        navigator.serviceWorker.register("/js/a2hs_sw.js");
    }
})();

let installPrompt = null;
const installButton = document.querySelector("#install");

// window.addEventListener("beforeinstallprompt", (event) => {
//     //event.preventDefault();
//     if (event.cancelable) event.preventDefault();
//     installPrompt = event;
//     installButton.removeAttribute("hidden");
// });

// installButton.addEventListener("click", async () => {
//     if (!installPrompt) {
//         return;
//     }
//     console.log("installButton fired");
//     const result = await installPrompt.prompt();
//     installPrompt = null;
//     installButton.setAttribute("hidden", "");
// });