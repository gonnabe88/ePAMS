//A2HS
(function () {
    if ("serviceWorker" in navigator) {
        navigator.serviceWorker.register("/js/utils/swA2HS.js");
    }
})();

let installPrompt = null;
const installButton = document.querySelector("#install");

window.addEventListener("beforeinstallprompt", (event) => {
    //event.preventDefault();
    if (event.cancelable) event.preventDefault();
    installPrompt = event;
    //installButton.removeAttribute("hidden");
    installButton.classList.remove("hidden");
    installButton.classList.add("visible");
});

installButton.addEventListener("click", async () => {
    if (!installPrompt) {
        return;
    }
    console.log("installButton fired");
    const result = await installPrompt.prompt();
    installPrompt = null;
    //installButton.setAttribute("hidden", "");
    installButton.classList.remove("visible");
    installButton.classList.add("hidden");
});