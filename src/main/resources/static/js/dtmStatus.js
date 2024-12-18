// 토글 버튼 클릭 이벤트 처리
document.getElementById('dtmStatusBtn').addEventListener('click', function() {
    var card = document.getElementById('dtmStatusCard');
    var toggleIcon = this.querySelector('.toggle-icon');

    if (card.style.display === 'none' || !card.classList.contains('show')) {
        card.style.display = 'block';
        setTimeout(function() {
            card.classList.add('show');
        }, 10); // 10ms 그대로 유지
        toggleIcon.style.transform = 'rotate(180deg)'; // 아이콘 회전
        toggleIcon.innerText = '▲'; // ▼를 ▲로 변경
    } else {
        card.classList.remove('show');
        toggleIcon.style.transform = 'rotate(0deg)'; // 아이콘 원래 상태로
        toggleIcon.innerText = '▼'; // ▲를 ▼로 변경
        setTimeout(function() {
            card.style.display = 'none';
        }, 300); // 0.3초로 맞춰서 변경
    }
});
