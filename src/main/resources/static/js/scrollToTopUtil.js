// Scroll to top 버튼
// Get the button
const mybutton = document.getElementById("scrollToTopBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

const scrollFunction = () => {
    const scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    const scrollHeight = document.documentElement.scrollHeight;
    const clientHeight = window.visualViewport ? window.visualViewport.height : window.innerHeight;
    const scrollPercent = (scrollTop / (scrollHeight - clientHeight)) * 100;

    if (scrollTop > 20) {
        mybutton.style.display = "flex";
        // Calculate the conic gradient percentage
        const gradientValue = `conic-gradient(#80ABFE ${scrollPercent}%, #DECFFF ${scrollPercent}%)`;
        mybutton.style.background = gradientValue;
    } else {
        mybutton.style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
mybutton.onclick = function() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}
