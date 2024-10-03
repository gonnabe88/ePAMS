let swiper; // 전역변수로 선언

document.addEventListener("DOMContentLoaded", function() {
    swiper = new Swiper('.mySwiper', {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true, // 페이지 번호 클릭 가능 시 해당 아이템 이동
            renderBullet: function(index, className) { // 숫자 형태의 페이지 번호
                return `<span class="`+className+`">`+(index+1)+`</span>`;
            },
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    });
});