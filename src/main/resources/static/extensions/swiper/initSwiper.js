let swiper; // 전역변수로 선언

document.addEventListener("DOMContentLoaded", function() {
    swiper = new Swiper('.mySwiper', {
        loop: true,
        spaceBetween: 30,
        effect: "fade",
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
            dynamicBullets: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        autoplay: {
            delay: 5000,
        },
    });
});