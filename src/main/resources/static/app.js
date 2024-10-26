navigator.serviceWorker.register("dummy-sw.js");

const installButton = document.querySelector("#install");

window.addEventListener("DOMContentLoaded", async event => {
  installButton.addEventListener("click", installApp);
});

let deferredPrompt;

window.addEventListener('beforeinstallprompt', (e) => {
  // Prevents the default mini-infobar or install dialog from appearing on mobile 
  e.preventDefault();
  // Save the event because you’ll need to trigger it later.
  deferredPrompt = e;
  // Show your customized install prompt for your PWA
  installButton.classList.remove("hidden"); // hidden 속성 제거
  installButton.classList.add("visible"); // visible 속성 추가   
});

window.addEventListener('appinstalled', (e) => {
});

async function installApp() {
  if (deferredPrompt) {
    deferredPrompt.prompt();
    // Find out whether the user confirmed the installation or not
    const { outcome } = await deferredPrompt.userChoice;
    // The deferredPrompt can only be used once.
    deferredPrompt = null;
    // We hide the install button
  installButton.classList.remove("visible"); // visible 속성 제거
  installButton.classList.add("hidden"); // hidden 속성 추가
  }
}