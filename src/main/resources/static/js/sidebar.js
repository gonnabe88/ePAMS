//Sidebar
const DesktopSize = 1200;
class Mr {
    constructor(k, s={}) {
        this.sidebarEL = k instanceof HTMLElement ? k : document.querySelector(k),
            this.options = s,
            this.init()
    }
    init() {
        // 드래그(스와이프) 이벤트를 위한 변수 초기화
        let startPoint = 0;
        let endPoint = 0;

        // 특정 div 제외
        const excludedDiv = document.querySelector('.no-drag');

        // PC 클릭 이벤트 (드래그)
        app.addEventListener("mousedown", (e) => {
            if (excludedDiv && excludedDiv.contains(e.target)) return;
            startPoint = e.pageX; // 마우스 드래그 시작 위치 저장
        });

        app.addEventListener("mouseup", (e) => {
            if (excludedDiv && excludedDiv.contains(e.target)) return;
            endPoint = e.pageX; // 마우스 드래그 끝 위치 저장
            if ((window.innerWidth < DesktopSize) && (startPoint + 100 < endPoint)) {
                // 마우스가 오른쪽으로 드래그 된 경우
                console.log("prev move");
                this.show();
            } else if ((window.innerWidth < DesktopSize) && (startPoint > endPoint + 100)) {
                // 마우스가 왼쪽으로 드래그 된 경우
                console.log("next move");
                this.hide();
            }
        });

        // 모바일 터치 이벤트 (스와이프)
        app.addEventListener("touchstart", (e) => {
            if (excludedDiv && excludedDiv.contains(e.target)) return;
            startPoint = e.touches[0].pageX; // 터치가 시작되는 위치 저장
        });

        app.addEventListener("touchend", (e) => {
            if (excludedDiv && excludedDiv.contains(e.target)) return;
            endPoint = e.changedTouches[0].pageX; // 터치가 끝나는 위치 저장
            if ((window.innerWidth < DesktopSize) && (startPoint + 100 < endPoint)) {
                // 오른쪽으로 스와이프 된 경우
                console.log("prev move");
                this.show();
            } else if ((window.innerWidth < DesktopSize) && (startPoint > endPoint + 100)) {
                // 왼쪽으로 스와이프 된 경우
                console.log("next move");
                this.hide();
            }
        });

        document.querySelectorAll(".burger-btn").forEach(r=>r.addEventListener("click", this.toggle.bind(this))),
            document.querySelectorAll(".sidebar-hide").forEach(r=>r.addEventListener("click", this.toggle.bind(this))),
            window.addEventListener("resize", this.onResize.bind(this));
        const k = r=>{
            r.classList.contains("submenu-open") ? (r.classList.remove("submenu-open"),
                r.classList.add("submenu-closed")) : (r.classList.remove("submenu-closed"),
                r.classList.add("submenu-open"))
        };
        let s = document.querySelectorAll(".sidebar-item.has-sub");
        for (var f = 0; f < s.length; f++) {
            let r = s[f];
            s[f].querySelector(".sidebar-link").addEventListener("click", a=>{
                if (a.cancelable) a.preventDefault();
                let u = r.querySelector(".submenu");
                k(u)
            }),
                r.querySelectorAll(".submenu-item.has-sub").forEach(a=>{
                    a.addEventListener("click", ()=>{
                        const u = a.querySelector(".submenu");
                        k(u),
                            nn(a.parentElement, !0)
                    })
                })
        }
        if (typeof PerfectScrollbar == "function") {
            const r = document.querySelector(".sidebar-wrapper");
            new PerfectScrollbar(r,{
                wheelPropagation: !0
            })
        }
        setTimeout(()=>{
            const r = document.querySelector(".sidebar-item.active");
            r && this.forceElementVisibility(r)
        }, 300),
        this.options.recalculateHeight && Cr(Xe)
    }
    onResize() {
        this.deleteBackdrop(),
            this.toggleOverflowBody(!0)
    }
    toggle() {
        this.sidebarEL.classList.contains("active") ? this.hide() : this.show()
    }
    show() {
        this.sidebarEL.classList.add("active"),
            this.sidebarEL.classList.remove("inactive"),
            this.createBackdrop(),
            this.toggleOverflowBody()
    }
    hide() {
        this.sidebarEL.classList.remove("active"),
            this.sidebarEL.classList.add("inactive"),
            this.deleteBackdrop(),
            this.toggleOverflowBody()
    }
    createBackdrop() {
        if (window.innerWidth > DesktopSize)
            return;
        this.deleteBackdrop();
        const k = document.createElement("div");
        k.classList.add("sidebar-backdrop"),
            k.addEventListener("click", this.hide.bind(this)),
            document.body.appendChild(k)
    }
    deleteBackdrop() {
        const k = document.querySelector(".sidebar-backdrop");
        k && k.remove()
    }
    toggleOverflowBody(k) {
        if (window.innerWidth > DesktopSize)
            return;
        const s = this.sidebarEL.classList.contains("active")
            , f = document.querySelector("html, body");
        typeof k > "u" ? f.style.overflowY = s ? "hidden" : "auto" : f.style.overflowY = k ? "auto" : "hidden"
    }
    isElementInViewport(k) {
        var s = k.getBoundingClientRect();
        return s.top >= 0 && s.left >= 0 && s.bottom <= (window.innerHeight || document.documentElement.clientHeight) && s.right <= (window.innerWidth || document.documentElement.clientWidth)
    }
    forceElementVisibility(k) {
        this.isElementInViewport(k) || k.scrollIntoView(!1)
    }
}

let Xe = document.getElementById("sidebar");

const Bn = U=>{
    if (!Xe)
        return;
    (window.innerWidth > DesktopSize) && (U.classList.add("inactive"),
        U.classList.add("sidebar-desktop"));
    let k = document.querySelectorAll(".sidebar-item.has-sub .submenu");
    for (var s = 0; s < k.length; s++) {
        let f = k[s];
        const r = f.parentElement;
    }
}, Cr = U=>{
    if (!U)
        return;
    let k = document.querySelectorAll(".sidebar-item.has-sub .submenu");
    for (var s = 0; s < k.length; s++) {
        let f = k[s];
        const r = f.parentElement;
    }
};
document.readyState !== "loading" ? Bn(Xe) : window.addEventListener("DOMContentLoaded", ()=>Bn(Xe)),
    window.Sidebar = Mr;
Xe && new window.Sidebar(Xe);