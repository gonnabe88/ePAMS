document.addEventListener("DOMContentLoaded", function() {
    const secondaryDownload = document.getElementById('secondaryDownload');
    if (secondaryDownload) {
        secondaryDownload.className = 'no-display';
    }
    const downloadButton = document.getElementById('download');
    if (downloadButton) {
        downloadButton.className = 'no-display';
    }
});