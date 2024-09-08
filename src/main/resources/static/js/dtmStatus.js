document.addEventListener('DOMContentLoaded', function() {
    const vacationStatusBtn = document.getElementById('dtmStatusBtn');
    const vacationCard = document.getElementById('dtmStatusCard');
    const toggleIcon = vacationStatusBtn.querySelector('.toggle-icon');

    vacationStatusBtn.addEventListener('click', function() {
        if (vacationCard.style.display === 'none') {
            vacationCard.style.display = 'block';
            toggleIcon.textContent = '▲';
        } else {
            vacationCard.style.display = 'none';
            toggleIcon.textContent = '▼';
        }
    });
});