function selectLicense(detailElement, licenseId) {
    if (detailElement.open && detailElement.children.length === 1) {
        let pre = document.createElement("pre");
        pre.innerHTML = document.getElementById(licenseId).textContent;
        detailElement.appendChild(pre);
    }
}