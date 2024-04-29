(function(ft) {
    typeof define == "function" && define.amd ? define(ft) : ft()
}
)(function() {
    "use strict";
    var ft = typeof globalThis < "u" ? globalThis : typeof window < "u" ? window : typeof global < "u" ? global : typeof self < "u" ? self : {};
    function Fn(U) {
        return U && U.__esModule && Object.prototype.hasOwnProperty.call(U, "default") ? U.default : U
    }
    var Kn = {
        exports: {}
    };
    (function(U, k) {
        (function(f, r) {
            U.exports = r()
        }
        )(typeof self < "u" ? self : ft, function() {
            return function(s) {
                var f = {};
                function r(l) {
                    if (f[l])
                        return f[l].exports;
                    var a = f[l] = {
                        i: l,
                        l: !1,
                        exports: {}
                    };
                    return s[l].call(a.exports, a, a.exports, r),
                    a.l = !0,
                    a.exports
                }
                return r.m = s,
                r.c = f,
                r.d = function(l, a, u) {
                    r.o(l, a) || Object.defineProperty(l, a, {
                        configurable: !1,
                        enumerable: !0,
                        get: u
                    })
                }
                ,
                r.r = function(l) {
                    Object.defineProperty(l, "__esModule", {
                        value: !0
                    })
                }
                ,
                r.n = function(l) {
                    var a = l && l.__esModule ? function() {
                        return l.default
                    }
                    : function() {
                        return l
                    }
                    ;
                    return r.d(a, "a", a),
                    a
                }
                ,
                r.o = function(l, a) {
                    return Object.prototype.hasOwnProperty.call(l, a)
                }
                ,
                r.p = "",
                r(r.s = 0)
            }
        })
    }
    )(Kn);
    
    var jr = Kn.exports;
    const Tr = Fn(jr);
    var Wn = {
        exports: {}
    };
    /*!
  * Bootstrap v5.3.2 (https://getbootstrap.com/)
  * Copyright 2011-2023 The Bootstrap Authors (https://github.com/twbs/bootstrap/graphs/contributors)
  * Licensed under MIT (https://github.com/twbs/bootstrap/blob/main/LICENSE)
  */
    (function(U, k) {
        (function(s, f) {
            U.exports = f()
        }
        )(ft, function() {
            const s = new Map
              , f = {
                set(n, e, t) {
                    s.has(n) || s.set(n, new Map);
                    const i = s.get(n);
                    if (!i.has(e) && i.size !== 0) {
                        console.error(`Bootstrap doesn't allow more than one instance per element. Bound instance: ${Array.from(i.keys())[0]}.`);
                        return
                    }
                    i.set(e, t)
                },
                get(n, e) {
                    return s.has(n) && s.get(n).get(e) || null
                },
                remove(n, e) {
                    if (!s.has(n))
                        return;
                    const t = s.get(n);
                    t.delete(e),
                    t.size === 0 && s.delete(n)
                }
            }
              , r = 1e6
              , l = 1e3
              , a = "transitionend"
              , u = n=>(n && window.CSS && window.CSS.escape && (n = n.replace(/#([^\s"#']+)/g, (e,t)=>`#${CSS.escape(t)}`)),
            n)
              , p = n=>n == null ? `${n}` : Object.prototype.toString.call(n).match(/\s([a-z]+)/i)[1].toLowerCase()
              , h = n=>{
                do
                    n += Math.floor(Math.random() * r);
                while (document.getElementById(n));
                return n
            }
              , g = n=>{
                if (!n)
                    return 0;
                let {transitionDuration: e, transitionDelay: t} = window.getComputedStyle(n);
                const i = Number.parseFloat(e)
                  , o = Number.parseFloat(t);
                return !i && !o ? 0 : (e = e.split(",")[0],
                t = t.split(",")[0],
                (Number.parseFloat(e) + Number.parseFloat(t)) * l)
            }
              , m = n=>{
                n.dispatchEvent(new Event(a))
            }
              , E = n=>!n || typeof n != "object" ? !1 : (typeof n.jquery < "u" && (n = n[0]),
            typeof n.nodeType < "u")
              , A = n=>E(n) ? n.jquery ? n[0] : n : typeof n == "string" && n.length > 0 ? document.querySelector(u(n)) : null
              , _ = n=>{
                if (!E(n) || n.getClientRects().length === 0)
                    return !1;
                const e = getComputedStyle(n).getPropertyValue("visibility") === "visible"
                  , t = n.closest("details:not([open])");
                if (!t)
                    return e;
                if (t !== n) {
                    const i = n.closest("summary");
                    if (i && i.parentNode !== t || i === null)
                        return !1
                }
                return e
            }
              , v = n=>!n || n.nodeType !== Node.ELEMENT_NODE || n.classList.contains("disabled") ? !0 : typeof n.disabled < "u" ? n.disabled : n.hasAttribute("disabled") && n.getAttribute("disabled") !== "false"
              , O = n=>{
                if (!document.documentElement.attachShadow)
                    return null;
                if (typeof n.getRootNode == "function") {
                    const e = n.getRootNode();
                    return e instanceof ShadowRoot ? e : null
                }
                return n instanceof ShadowRoot ? n : n.parentNode ? O(n.parentNode) : null
            }
              , T = ()=>{}
              , j = n=>{
                n.offsetHeight
            }
              , S = ()=>window.jQuery && !document.body.hasAttribute("data-bs-no-jquery") ? window.jQuery : null
              , L = []
              , W = n=>{
                document.readyState === "loading" ? (L.length || document.addEventListener("DOMContentLoaded", ()=>{
                    for (const e of L)
                        e()
                }
                ),
                L.push(n)) : n()
            }
              , D = ()=>document.documentElement.dir === "rtl"
              , $ = n=>{
                W(()=>{
                    const e = S();
                    if (e) {
                        const t = n.NAME
                          , i = e.fn[t];
                        e.fn[t] = n.jQueryInterface,
                        e.fn[t].Constructor = n,
                        e.fn[t].noConflict = ()=>(e.fn[t] = i,
                        n.jQueryInterface)
                    }
                }
                )
            }
              , H = (n,e=[],t=n)=>typeof n == "function" ? n(...e) : t
              , ue = (n,e,t=!0)=>{
                if (!t) {
                    H(n);
                    return
                }
                const i = 5
                  , o = g(e) + i;
                let c = !1;
                const d = ({target: y})=>{
                    y === e && (c = !0,
                    e.removeEventListener(a, d),
                    H(n))
                }
                ;
                e.addEventListener(a, d),
                setTimeout(()=>{
                    c || m(e)
                }
                , o)
            }
              , yt = (n,e,t,i)=>{
                const o = n.length;
                let c = n.indexOf(e);
                return c === -1 ? !t && i ? n[o - 1] : n[0] : (c += t ? 1 : -1,
                i && (c = (c + o) % o),
                n[Math.max(0, Math.min(c, o - 1))])
            }
              , Te = /[^.]*(?=\..*)\.|.*/
              , rn = /\..*/
              , Nt = /::\d+$/
              , Oe = {};
            let mt = 1;
            const Qe = {
                mouseenter: "mouseover",
                mouseleave: "mouseout"
            }
              , le = new Set(["click", "dblclick", "mouseup", "mousedown", "contextmenu", "mousewheel", "DOMMouseScroll", "mouseover", "mouseout", "mousemove", "selectstart", "selectend", "keydown", "keypress", "keyup", "orientationchange", "touchstart", "touchmove", "touchend", "touchcancel", "pointerdown", "pointermove", "pointerup", "pointerleave", "pointercancel", "gesturestart", "gesturechange", "gestureend", "focus", "blur", "change", "reset", "select", "submit", "focusin", "focusout", "load", "unload", "beforeunload", "resize", "move", "DOMContentLoaded", "readystatechange", "error", "abort", "scroll"]);
            function _e(n, e) {
                return e && `${e}::${mt++}` || n.uidEvent || mt++
            }
            function ge(n) {
                const e = _e(n);
                return n.uidEvent = e,
                Oe[e] = Oe[e] || {},
                Oe[e]
            }
            function Lt(n, e) {
                return function t(i) {
                    return on(i, {
                        delegateTarget: n
                    }),
                    t.oneOff && x.off(n, i.type, e),
                    e.apply(n, [i])
                }
            }
            function Ee(n, e, t) {
                return function i(o) {
                    const c = n.querySelectorAll(e);
                    for (let {target: d} = o; d && d !== this; d = d.parentNode)
                        for (const y of c)
                            if (y === d)
                                return on(o, {
                                    delegateTarget: d
                                }),
                                i.oneOff && x.off(n, o.type, e, t),
                                t.apply(d, [o])
                }
            }
            function Se(n, e, t=null) {
                return Object.values(n).find(i=>i.callable === e && i.delegationSelector === t)
            }
            function De(n, e, t) {
                const i = typeof e == "string"
                  , o = i ? t : e || t;
                let c = Yn(n);
                return le.has(c) || (c = n),
                [i, o, c]
            }
            function pe(n, e, t, i, o) {
                if (typeof e != "string" || !n)
                    return;
                let[c,d,y] = De(e, t, i);
                e in Qe && (d = (B=>function(R) {
                    if (!R.relatedTarget || R.relatedTarget !== R.delegateTarget && !R.delegateTarget.contains(R.relatedTarget))
                        return B.call(this, R)
                }
                )(d));
                const b = ge(n)
                  , M = b[y] || (b[y] = {})
                  , w = Se(M, d, c ? t : null);
                if (w) {
                    w.oneOff = w.oneOff && o;
                    return
                }
                const I = _e(d, e.replace(Te, ""))
                  , P = c ? Ee(n, t, d) : Lt(n, d);
                P.delegationSelector = c ? t : null,
                P.callable = d,
                P.oneOff = o,
                P.uidEvent = I,
                M[I] = P,
                n.addEventListener(y, P, c)
            }
            function Ze(n, e, t, i, o) {
                const c = Se(e[t], i, o);
                c && (n.removeEventListener(t, c, !!o),
                delete e[t][c.uidEvent])
            }
            function Nr(n, e, t, i) {
                const o = e[t] || {};
                for (const [c,d] of Object.entries(o))
                    c.includes(i) && Ze(n, e, t, d.callable, d.delegationSelector)
            }
            function Yn(n) {
                return n = n.replace(rn, ""),
                Qe[n] || n
            }
            const x = {
                on(n, e, t, i) {
                    pe(n, e, t, i, !1)
                },
                one(n, e, t, i) {
                    pe(n, e, t, i, !0)
                },
                off(n, e, t, i) {
                    if (typeof e != "string" || !n)
                        return;
                    const [o,c,d] = De(e, t, i)
                      , y = d !== e
                      , b = ge(n)
                      , M = b[d] || {}
                      , w = e.startsWith(".");
                    if (typeof c < "u") {
                        if (!Object.keys(M).length)
                            return;
                        Ze(n, b, d, c, o ? t : null);
                        return
                    }
                    if (w)
                        for (const I of Object.keys(b))
                            Nr(n, b, I, e.slice(1));
                    for (const [I,P] of Object.entries(M)) {
                        const N = I.replace(Nt, "");
                        (!y || e.includes(N)) && Ze(n, b, d, P.callable, P.delegationSelector)
                    }
                },
                trigger(n, e, t) {
                    if (typeof e != "string" || !n)
                        return null;
                    const i = S()
                      , o = Yn(e)
                      , c = e !== o;
                    let d = null
                      , y = !0
                      , b = !0
                      , M = !1;
                    c && i && (d = i.Event(e, t),
                    i(n).trigger(d),
                    y = !d.isPropagationStopped(),
                    b = !d.isImmediatePropagationStopped(),
                    M = d.isDefaultPrevented());
                    const w = on(new Event(e,{
                        bubbles: y,
                        cancelable: !0
                    }), t);
                    return M && w.preventDefault(),
                    b && n.dispatchEvent(w),
                    w.defaultPrevented && d && d.preventDefault(),
                    w
                }
            };
            function on(n, e={}) {
                for (const [t,i] of Object.entries(e))
                    try {
                        n[t] = i
                    } catch {
                        Object.defineProperty(n, t, {
                            configurable: !0,
                            get() {
                                return i
                            }
                        })
                    }
                return n
            }
            function Gn(n) {
                if (n === "true")
                    return !0;
                if (n === "false")
                    return !1;
                if (n === Number(n).toString())
                    return Number(n);
                if (n === "" || n === "null")
                    return null;
                if (typeof n != "string")
                    return n;
                try {
                    return JSON.parse(decodeURIComponent(n))
                } catch {
                    return n
                }
            }
            function sn(n) {
                return n.replace(/[A-Z]/g, e=>`-${e.toLowerCase()}`)
            }
            const be = {
                setDataAttribute(n, e, t) {
                    n.setAttribute(`data-bs-${sn(e)}`, t)
                },
                removeDataAttribute(n, e) {
                    n.removeAttribute(`data-bs-${sn(e)}`)
                },
                getDataAttributes(n) {
                    if (!n)
                        return {};
                    const e = {}
                      , t = Object.keys(n.dataset).filter(i=>i.startsWith("bs") && !i.startsWith("bsConfig"));
                    for (const i of t) {
                        let o = i.replace(/^bs/, "");
                        o = o.charAt(0).toLowerCase() + o.slice(1, o.length),
                        e[o] = Gn(n.dataset[i])
                    }
                    return e
                },
                getDataAttribute(n, e) {
                    return Gn(n.getAttribute(`data-bs-${sn(e)}`))
                }
            };
            class gt {
                static get Default() {
                    return {}
                }
                static get DefaultType() {
                    return {}
                }
                static get NAME() {
                    throw new Error('You have to implement the static method "NAME", for each component!')
                }
                _getConfig(e) {
                    return e = this._mergeConfigObj(e),
                    e = this._configAfterMerge(e),
                    this._typeCheckConfig(e),
                    e
                }
                _configAfterMerge(e) {
                    return e
                }
                _mergeConfigObj(e, t) {
                    const i = E(t) ? be.getDataAttribute(t, "config") : {};
                    return {
                        ...this.constructor.Default,
                        ...typeof i == "object" ? i : {},
                        ...E(t) ? be.getDataAttributes(t) : {},
                        ...typeof e == "object" ? e : {}
                    }
                }
                _typeCheckConfig(e, t=this.constructor.DefaultType) {
                    for (const [i,o] of Object.entries(t)) {
                        const c = e[i]
                          , d = E(c) ? "element" : p(c);
                        if (!new RegExp(o).test(d))
                            throw new TypeError(`${this.constructor.NAME.toUpperCase()}: Option "${i}" provided type "${d}" but expected type "${o}".`)
                    }
                }
            }
            const Lr = "5.3.2";
            class he extends gt {
                constructor(e, t) {
                    super(),
                    e = A(e),
                    e && (this._element = e,
                    this._config = this._getConfig(t),
                    f.set(this._element, this.constructor.DATA_KEY, this))
                }
                dispose() {
                    f.remove(this._element, this.constructor.DATA_KEY),
                    x.off(this._element, this.constructor.EVENT_KEY);
                    for (const e of Object.getOwnPropertyNames(this))
                        this[e] = null
                }
                _queueCallback(e, t, i=!0) {
                    ue(e, t, i)
                }
                _getConfig(e) {
                    return e = this._mergeConfigObj(e, this._element),
                    e = this._configAfterMerge(e),
                    this._typeCheckConfig(e),
                    e
                }
                static getInstance(e) {
                    return f.get(A(e), this.DATA_KEY)
                }
                static getOrCreateInstance(e, t={}) {
                    return this.getInstance(e) || new this(e,typeof t == "object" ? t : null)
                }
                static get VERSION() {
                    return Lr
                }
                static get DATA_KEY() {
                    return `bs.${this.NAME}`
                }
                static get EVENT_KEY() {
                    return `.${this.DATA_KEY}`
                }
                static eventName(e) {
                    return `${e}${this.EVENT_KEY}`
                }
            }
            const an = n=>{
                let e = n.getAttribute("data-bs-target");
                if (!e || e === "#") {
                    let t = n.getAttribute("href");
                    if (!t || !t.includes("#") && !t.startsWith("."))
                        return null;
                    t.includes("#") && !t.startsWith("#") && (t = `#${t.split("#")[1]}`),
                    e = t && t !== "#" ? u(t.trim()) : null
                }
                return e
            }
              , C = {
                find(n, e=document.documentElement) {
                    return [].concat(...Element.prototype.querySelectorAll.call(e, n))
                },
                findOne(n, e=document.documentElement) {
                    return Element.prototype.querySelector.call(e, n)
                },
                children(n, e) {
                    return [].concat(...n.children).filter(t=>t.matches(e))
                },
                parents(n, e) {
                    const t = [];
                    let i = n.parentNode.closest(e);
                    for (; i; )
                        t.push(i),
                        i = i.parentNode.closest(e);
                    return t
                },
                prev(n, e) {
                    let t = n.previousElementSibling;
                    for (; t; ) {
                        if (t.matches(e))
                            return [t];
                        t = t.previousElementSibling
                    }
                    return []
                },
                next(n, e) {
                    let t = n.nextElementSibling;
                    for (; t; ) {
                        if (t.matches(e))
                            return [t];
                        t = t.nextElementSibling
                    }
                    return []
                },
                focusableChildren(n) {
                    const e = ["a", "button", "input", "textarea", "select", "details", "[tabindex]", '[contenteditable="true"]'].map(t=>`${t}:not([tabindex^="-"])`).join(",");
                    return this.find(e, n).filter(t=>!v(t) && _(t))
                },
                getSelectorFromElement(n) {
                    const e = an(n);
                    return e && C.findOne(e) ? e : null
                },
                getElementFromSelector(n) {
                    const e = an(n);
                    return e ? C.findOne(e) : null
                },
                getMultipleElementsFromSelector(n) {
                    const e = an(n);
                    return e ? C.find(e) : []
                }
            }
              , Dt = (n,e="hide")=>{
                const t = `click.dismiss${n.EVENT_KEY}`
                  , i = n.NAME;
                x.on(document, t, `[data-bs-dismiss="${i}"]`, function(o) {
                    if (["A", "AREA"].includes(this.tagName) && o.preventDefault(),
                    v(this))
                        return;
                    const c = C.getElementFromSelector(this) || this.closest(`.${i}`);
                    n.getOrCreateInstance(c)[e]()
                })
            }
              , Dr = "alert"
              , Un = ".bs.alert"
              , Ir = `close${Un}`
              , $r = `closed${Un}`
              , Pr = "fade"
              , Rr = "show";
            class vt extends he {
                static get NAME() {
                    return Dr
                }
                close() {
                    if (x.trigger(this._element, Ir).defaultPrevented)
                        return;
                    this._element.classList.remove(Rr);
                    const t = this._element.classList.contains(Pr);
                    this._queueCallback(()=>this._destroyElement(), this._element, t)
                }
                _destroyElement() {
                    this._element.remove(),
                    x.trigger(this._element, $r),
                    this.dispose()
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = vt.getOrCreateInstance(this);
                        if (typeof e == "string") {
                            if (t[e] === void 0 || e.startsWith("_") || e === "constructor")
                                throw new TypeError(`No method named "${e}"`);
                            t[e](this)
                        }
                    })
                }
            }
            Dt(vt, "close"),
            $(vt);
            const Vr = "button"
              , Hr = ".bs.button"
              , kr = ".data-api"
              , zr = "active"
              , Xn = '[data-bs-toggle="button"]'
              , Fr = `click${Hr}${kr}`;
            class xt extends he {
                static get NAME() {
                    return Vr
                }
                toggle() {
                    this._element.setAttribute("aria-pressed", this._element.classList.toggle(zr))
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = xt.getOrCreateInstance(this);
                        e === "toggle" && t[e]()
                    })
                }
            }
            x.on(document, Fr, Xn, n=>{
                n.preventDefault();
                const e = n.target.closest(Xn);
                xt.getOrCreateInstance(e).toggle()
            }
            ),
            $(xt);
            const Kr = "swipe"
              , Je = ".bs.swipe"
              , Wr = `touchstart${Je}`
              , Br = `touchmove${Je}`
              , Yr = `touchend${Je}`
              , Gr = `pointerdown${Je}`
              , Ur = `pointerup${Je}`
              , Xr = "touch"
              , Qr = "pen"
              , Zr = "pointer-event"
              , Jr = 40
              , qr = {
                endCallback: null,
                leftCallback: null,
                rightCallback: null
            }
              , eo = {
                endCallback: "(function|null)",
                leftCallback: "(function|null)",
                rightCallback: "(function|null)"
            };
            class It extends gt {
                constructor(e, t) {
                    super(),
                    this._element = e,
                    !(!e || !It.isSupported()) && (this._config = this._getConfig(t),
                    this._deltaX = 0,
                    this._supportPointerEvents = !!window.PointerEvent,
                    this._initEvents())
                }
                static get Default() {
                    return qr
                }
                static get DefaultType() {
                    return eo
                }
                static get NAME() {
                    return Kr
                }
                dispose() {
                    x.off(this._element, Je)
                }
                _start(e) {
                    if (!this._supportPointerEvents) {
                        this._deltaX = e.touches[0].clientX;
                        return
                    }
                    this._eventIsPointerPenTouch(e) && (this._deltaX = e.clientX)
                }
                _end(e) {
                    this._eventIsPointerPenTouch(e) && (this._deltaX = e.clientX - this._deltaX),
                    this._handleSwipe(),
                    H(this._config.endCallback)
                }
                _move(e) {
                    this._deltaX = e.touches && e.touches.length > 1 ? 0 : e.touches[0].clientX - this._deltaX
                }
                _handleSwipe() {
                    const e = Math.abs(this._deltaX);
                    if (e <= Jr)
                        return;
                    const t = e / this._deltaX;
                    this._deltaX = 0,
                    t && H(t > 0 ? this._config.rightCallback : this._config.leftCallback)
                }
                _initEvents() {
                    this._supportPointerEvents ? (x.on(this._element, Gr, e=>this._start(e)),
                    x.on(this._element, Ur, e=>this._end(e)),
                    this._element.classList.add(Zr)) : (x.on(this._element, Wr, e=>this._start(e)),
                    x.on(this._element, Br, e=>this._move(e)),
                    x.on(this._element, Yr, e=>this._end(e)))
                }
                _eventIsPointerPenTouch(e) {
                    return this._supportPointerEvents && (e.pointerType === Qr || e.pointerType === Xr)
                }
                static isSupported() {
                    return "ontouchstart"in document.documentElement || navigator.maxTouchPoints > 0
                }
            }
            const to = "carousel"
              , Me = ".bs.carousel"
              , Qn = ".data-api"
              , no = "ArrowLeft"
              , io = "ArrowRight"
              , ro = 500
              , _t = "next"
              , qe = "prev"
              , et = "left"
              , $t = "right"
              , oo = `slide${Me}`
              , ln = `slid${Me}`
              , so = `keydown${Me}`
              , ao = `mouseenter${Me}`
              , lo = `mouseleave${Me}`
              , co = `dragstart${Me}`
              , uo = `load${Me}${Qn}`
              , po = `click${Me}${Qn}`
              , Zn = "carousel"
              , Pt = "active"
              , ho = "slide"
              , fo = "carousel-item-end"
              , yo = "carousel-item-start"
              , mo = "carousel-item-next"
              , go = "carousel-item-prev"
              , Jn = ".active"
              , qn = ".carousel-item"
              , vo = Jn + qn
              , xo = ".carousel-item img"
              , _o = ".carousel-indicators"
              , Eo = "[data-bs-slide], [data-bs-slide-to]"
              , bo = '[data-bs-ride="carousel"]'
              , Ao = {
                [no]: $t,
                [io]: et
            }
              , wo = {
                interval: 5e3,
                keyboard: !0,
                pause: "hover",
                ride: !1,
                touch: !0,
                wrap: !0
            }
              , jo = {
                interval: "(number|boolean)",
                keyboard: "boolean",
                pause: "(string|boolean)",
                ride: "(boolean|string)",
                touch: "boolean",
                wrap: "boolean"
            };
            class tt extends he {
                constructor(e, t) {
                    super(e, t),
                    this._interval = null,
                    this._activeElement = null,
                    this._isSliding = !1,
                    this.touchTimeout = null,
                    this._swipeHelper = null,
                    this._indicatorsElement = C.findOne(_o, this._element),
                    this._addEventListeners(),
                    this._config.ride === Zn && this.cycle()
                }
                static get Default() {
                    return wo
                }
                static get DefaultType() {
                    return jo
                }
                static get NAME() {
                    return to
                }
                next() {
                    this._slide(_t)
                }
                nextWhenVisible() {
                    !document.hidden && _(this._element) && this.next()
                }
                prev() {
                    this._slide(qe)
                }
                pause() {
                    this._isSliding && m(this._element),
                    this._clearInterval()
                }
                cycle() {
                    this._clearInterval(),
                    this._updateInterval(),
                    this._interval = setInterval(()=>this.nextWhenVisible(), this._config.interval)
                }
                _maybeEnableCycle() {
                    if (this._config.ride) {
                        if (this._isSliding) {
                            x.one(this._element, ln, ()=>this.cycle());
                            return
                        }
                        this.cycle()
                    }
                }
                to(e) {
                    const t = this._getItems();
                    if (e > t.length - 1 || e < 0)
                        return;
                    if (this._isSliding) {
                        x.one(this._element, ln, ()=>this.to(e));
                        return
                    }
                    const i = this._getItemIndex(this._getActive());
                    if (i === e)
                        return;
                    const o = e > i ? _t : qe;
                    this._slide(o, t[e])
                }
                dispose() {
                    this._swipeHelper && this._swipeHelper.dispose(),
                    super.dispose()
                }
                _configAfterMerge(e) {
                    return e.defaultInterval = e.interval,
                    e
                }
                _addEventListeners() {
                    this._config.keyboard && x.on(this._element, so, e=>this._keydown(e)),
                    this._config.pause === "hover" && (x.on(this._element, ao, ()=>this.pause()),
                    x.on(this._element, lo, ()=>this._maybeEnableCycle())),
                    this._config.touch && It.isSupported() && this._addTouchEventListeners()
                }
                _addTouchEventListeners() {
                    for (const i of C.find(xo, this._element))
                        x.on(i, co, o=>o.preventDefault());
                    const t = {
                        leftCallback: ()=>this._slide(this._directionToOrder(et)),
                        rightCallback: ()=>this._slide(this._directionToOrder($t)),
                        endCallback: ()=>{
                            this._config.pause === "hover" && (this.pause(),
                            this.touchTimeout && clearTimeout(this.touchTimeout),
                            this.touchTimeout = setTimeout(()=>this._maybeEnableCycle(), ro + this._config.interval))
                        }
                    };
                    this._swipeHelper = new It(this._element,t)
                }
                _keydown(e) {
                    if (/input|textarea/i.test(e.target.tagName))
                        return;
                    const t = Ao[e.key];
                    t && (e.preventDefault(),
                    this._slide(this._directionToOrder(t)))
                }
                _getItemIndex(e) {
                    return this._getItems().indexOf(e)
                }
                _setActiveIndicatorElement(e) {
                    if (!this._indicatorsElement)
                        return;
                    const t = C.findOne(Jn, this._indicatorsElement);
                    t.classList.remove(Pt),
                    t.removeAttribute("aria-current");
                    const i = C.findOne(`[data-bs-slide-to="${e}"]`, this._indicatorsElement);
                    i && (i.classList.add(Pt),
                    i.setAttribute("aria-current", "true"))
                }
                _updateInterval() {
                    const e = this._activeElement || this._getActive();
                    if (!e)
                        return;
                    const t = Number.parseInt(e.getAttribute("data-bs-interval"), 10);
                    this._config.interval = t || this._config.defaultInterval
                }
                _slide(e, t=null) {
                    if (this._isSliding)
                        return;
                    const i = this._getActive()
                      , o = e === _t
                      , c = t || yt(this._getItems(), i, o, this._config.wrap);
                    if (c === i)
                        return;
                    const d = this._getItemIndex(c)
                      , y = N=>x.trigger(this._element, N, {
                        relatedTarget: c,
                        direction: this._orderToDirection(e),
                        from: this._getItemIndex(i),
                        to: d
                    });
                    if (y(oo).defaultPrevented || !i || !c)
                        return;
                    const M = !!this._interval;
                    this.pause(),
                    this._isSliding = !0,
                    this._setActiveIndicatorElement(d),
                    this._activeElement = c;
                    const w = o ? yo : fo
                      , I = o ? mo : go;
                    c.classList.add(I),
                    j(c),
                    i.classList.add(w),
                    c.classList.add(w);
                    const P = ()=>{
                        c.classList.remove(w, I),
                        c.classList.add(Pt),
                        i.classList.remove(Pt, I, w),
                        this._isSliding = !1,
                        y(ln)
                    }
                    ;
                    this._queueCallback(P, i, this._isAnimated()),
                    M && this.cycle()
                }
                _isAnimated() {
                    return this._element.classList.contains(ho)
                }
                _getActive() {
                    return C.findOne(vo, this._element)
                }
                _getItems() {
                    return C.find(qn, this._element)
                }
                _clearInterval() {
                    this._interval && (clearInterval(this._interval),
                    this._interval = null)
                }
                _directionToOrder(e) {
                    return D() ? e === et ? qe : _t : e === et ? _t : qe
                }
                _orderToDirection(e) {
                    return D() ? e === qe ? et : $t : e === qe ? $t : et
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = tt.getOrCreateInstance(this, e);
                        if (typeof e == "number") {
                            t.to(e);
                            return
                        }
                        if (typeof e == "string") {
                            if (t[e] === void 0 || e.startsWith("_") || e === "constructor")
                                throw new TypeError(`No method named "${e}"`);
                            t[e]()
                        }
                    })
                }
            }
            x.on(document, po, Eo, function(n) {
                const e = C.getElementFromSelector(this);
                if (!e || !e.classList.contains(Zn))
                    return;
                n.preventDefault();
                const t = tt.getOrCreateInstance(e)
                  , i = this.getAttribute("data-bs-slide-to");
                if (i) {
                    t.to(i),
                    t._maybeEnableCycle();
                    return
                }
                if (be.getDataAttribute(this, "slide") === "next") {
                    t.next(),
                    t._maybeEnableCycle();
                    return
                }
                t.prev(),
                t._maybeEnableCycle()
            }),
            x.on(window, uo, ()=>{
                const n = C.find(bo);
                for (const e of n)
                    tt.getOrCreateInstance(e)
            }
            ),
            $(tt);
            const To = "collapse"
              , Et = ".bs.collapse"
              , Oo = ".data-api"
              , So = `show${Et}`
              , Mo = `shown${Et}`
              , Co = `hide${Et}`
              , No = `hidden${Et}`
              , Lo = `click${Et}${Oo}`
              , cn = "show"
              , nt = "collapse"
              , Rt = "collapsing"
              , Do = "collapsed"
              , Io = `:scope .${nt} .${nt}`
              , $o = "collapse-horizontal"
              , Po = "width"
              , Ro = "height"
              , Vo = ".collapse.show, .collapse.collapsing"
              , dn = '[data-bs-toggle="collapse"]'
              , Ho = {
                parent: null,
                toggle: !0
            }
              , ko = {
                parent: "(null|element)",
                toggle: "boolean"
            };
            class it extends he {
                constructor(e, t) {
                    super(e, t),
                    this._isTransitioning = !1,
                    this._triggerArray = [];
                    const i = C.find(dn);
                    for (const o of i) {
                        const c = C.getSelectorFromElement(o)
                          , d = C.find(c).filter(y=>y === this._element);
                        c !== null && d.length && this._triggerArray.push(o)
                    }
                    this._initializeChildren(),
                    this._config.parent || this._addAriaAndCollapsedClass(this._triggerArray, this._isShown()),
                    this._config.toggle && this.toggle()
                }
                static get Default() {
                    return Ho
                }
                static get DefaultType() {
                    return ko
                }
                static get NAME() {
                    return To
                }
                toggle() {
                    this._isShown() ? this.hide() : this.show()
                }
                show() {
                    if (this._isTransitioning || this._isShown())
                        return;
                    let e = [];
                    if (this._config.parent && (e = this._getFirstLevelChildren(Vo).filter(y=>y !== this._element).map(y=>it.getOrCreateInstance(y, {
                        toggle: !1
                    }))),
                    e.length && e[0]._isTransitioning || x.trigger(this._element, So).defaultPrevented)
                        return;
                    for (const y of e)
                        y.hide();
                    const i = this._getDimension();
                    this._element.classList.remove(nt),
                    this._element.classList.add(Rt),
                    this._element.style[i] = 0,
                    this._addAriaAndCollapsedClass(this._triggerArray, !0),
                    this._isTransitioning = !0;
                    const o = ()=>{
                        this._isTransitioning = !1,
                        this._element.classList.remove(Rt),
                        this._element.classList.add(nt, cn),
                        this._element.style[i] = "",
                        x.trigger(this._element, Mo)
                    }
                      , d = `scroll${i[0].toUpperCase() + i.slice(1)}`;
                    this._queueCallback(o, this._element, !0),
                    this._element.style[i] = `${this._element[d]}px`
                }
                hide() {
                    if (this._isTransitioning || !this._isShown() || x.trigger(this._element, Co).defaultPrevented)
                        return;
                    const t = this._getDimension();
                    this._element.style[t] = `${this._element.getBoundingClientRect()[t]}px`,
                    j(this._element),
                    this._element.classList.add(Rt),
                    this._element.classList.remove(nt, cn);
                    for (const o of this._triggerArray) {
                        const c = C.getElementFromSelector(o);
                        c && !this._isShown(c) && this._addAriaAndCollapsedClass([o], !1)
                    }
                    this._isTransitioning = !0;
                    const i = ()=>{
                        this._isTransitioning = !1,
                        this._element.classList.remove(Rt),
                        this._element.classList.add(nt),
                        x.trigger(this._element, No)
                    }
                    ;
                    this._element.style[t] = "",
                    this._queueCallback(i, this._element, !0)
                }
                _isShown(e=this._element) {
                    return e.classList.contains(cn)
                }
                _configAfterMerge(e) {
                    return e.toggle = !!e.toggle,
                    e.parent = A(e.parent),
                    e
                }
                _getDimension() {
                    return this._element.classList.contains($o) ? Po : Ro
                }
                _initializeChildren() {
                    if (!this._config.parent)
                        return;
                    const e = this._getFirstLevelChildren(dn);
                    for (const t of e) {
                        const i = C.getElementFromSelector(t);
                        i && this._addAriaAndCollapsedClass([t], this._isShown(i))
                    }
                }
                _getFirstLevelChildren(e) {
                    const t = C.find(Io, this._config.parent);
                    return C.find(e, this._config.parent).filter(i=>!t.includes(i))
                }
                _addAriaAndCollapsedClass(e, t) {
                    if (e.length)
                        for (const i of e)
                            i.classList.toggle(Do, !t),
                            i.setAttribute("aria-expanded", t)
                }
                static jQueryInterface(e) {
                    const t = {};
                    return typeof e == "string" && /show|hide/.test(e) && (t.toggle = !1),
                    this.each(function() {
                        const i = it.getOrCreateInstance(this, t);
                        if (typeof e == "string") {
                            if (typeof i[e] > "u")
                                throw new TypeError(`No method named "${e}"`);
                            i[e]()
                        }
                    })
                }
            }
            x.on(document, Lo, dn, function(n) {
                (n.target.tagName === "A" || n.delegateTarget && n.delegateTarget.tagName === "A") && n.preventDefault();
                for (const e of C.getMultipleElementsFromSelector(this))
                    it.getOrCreateInstance(e, {
                        toggle: !1
                    }).toggle()
            }),
            $(it);
            var te = "top"
              , re = "bottom"
              , oe = "right"
              , ne = "left"
              , Vt = "auto"
              , rt = [te, re, oe, ne]
              , Ie = "start"
              , ot = "end"
              , ei = "clippingParents"
              , un = "viewport"
              , st = "popper"
              , ti = "reference"
              , pn = rt.reduce(function(n, e) {
                return n.concat([e + "-" + Ie, e + "-" + ot])
            }, [])
              , hn = [].concat(rt, [Vt]).reduce(function(n, e) {
                return n.concat([e, e + "-" + Ie, e + "-" + ot])
            }, [])
              , ni = "beforeRead"
              , ii = "read"
              , ri = "afterRead"
              , oi = "beforeMain"
              , si = "main"
              , ai = "afterMain"
              , li = "beforeWrite"
              , ci = "write"
              , di = "afterWrite"
              , ui = [ni, ii, ri, oi, si, ai, li, ci, di];
            function ve(n) {
                return n ? (n.nodeName || "").toLowerCase() : null
            }
            function se(n) {
                if (n == null)
                    return window;
                if (n.toString() !== "[object Window]") {
                    var e = n.ownerDocument;
                    return e && e.defaultView || window
                }
                return n
            }
            function $e(n) {
                var e = se(n).Element;
                return n instanceof e || n instanceof Element
            }
            function ce(n) {
                var e = se(n).HTMLElement;
                return n instanceof e || n instanceof HTMLElement
            }
            function fn(n) {
                if (typeof ShadowRoot > "u")
                    return !1;
                var e = se(n).ShadowRoot;
                return n instanceof e || n instanceof ShadowRoot
            }
            function zo(n) {
                var e = n.state;
                Object.keys(e.elements).forEach(function(t) {
                    var i = e.styles[t] || {}
                      , o = e.attributes[t] || {}
                      , c = e.elements[t];
                    !ce(c) || !ve(c) || (Object.assign(c.style, i),
                    Object.keys(o).forEach(function(d) {
                        var y = o[d];
                        y === !1 ? c.removeAttribute(d) : c.setAttribute(d, y === !0 ? "" : y)
                    }))
                })
            }
            function Fo(n) {
                var e = n.state
                  , t = {
                    popper: {
                        position: e.options.strategy,
                        left: "0",
                        top: "0",
                        margin: "0"
                    },
                    arrow: {
                        position: "absolute"
                    },
                    reference: {}
                };
                return Object.assign(e.elements.popper.style, t.popper),
                e.styles = t,
                e.elements.arrow && Object.assign(e.elements.arrow.style, t.arrow),
                function() {
                    Object.keys(e.elements).forEach(function(i) {
                        var o = e.elements[i]
                          , c = e.attributes[i] || {}
                          , d = Object.keys(e.styles.hasOwnProperty(i) ? e.styles[i] : t[i])
                          , y = d.reduce(function(b, M) {
                            return b[M] = "",
                            b
                        }, {});
                        !ce(o) || !ve(o) || (Object.assign(o.style, y),
                        Object.keys(c).forEach(function(b) {
                            o.removeAttribute(b)
                        }))
                    })
                }
            }
            const yn = {
                name: "applyStyles",
                enabled: !0,
                phase: "write",
                fn: zo,
                effect: Fo,
                requires: ["computeStyles"]
            };
            function xe(n) {
                return n.split("-")[0]
            }
            var Pe = Math.max
              , Ht = Math.min
              , at = Math.round;
            function mn() {
                var n = navigator.userAgentData;
                return n != null && n.brands && Array.isArray(n.brands) ? n.brands.map(function(e) {
                    return e.brand + "/" + e.version
                }).join(" ") : navigator.userAgent
            }
            function pi() {
                return !/^((?!chrome|android).)*safari/i.test(mn())
            }
            function lt(n, e, t) {
                e === void 0 && (e = !1),
                t === void 0 && (t = !1);
                var i = n.getBoundingClientRect()
                  , o = 1
                  , c = 1;
                e && ce(n) && (o = n.offsetWidth > 0 && at(i.width) / n.offsetWidth || 1,
                c = n.offsetHeight > 0 && at(i.height) / n.offsetHeight || 1);
                var d = $e(n) ? se(n) : window
                  , y = d.visualViewport
                  , b = !pi() && t
                  , M = (i.left + (b && y ? y.offsetLeft : 0)) / o
                  , w = (i.top + (b && y ? y.offsetTop : 0)) / c
                  , I = i.width / o
                  , P = i.height / c;
                return {
                    width: I,
                    height: P,
                    top: w,
                    right: M + I,
                    bottom: w + P,
                    left: M,
                    x: M,
                    y: w
                }
            }
            function gn(n) {
                var e = lt(n)
                  , t = n.offsetWidth
                  , i = n.offsetHeight;
                return Math.abs(e.width - t) <= 1 && (t = e.width),
                Math.abs(e.height - i) <= 1 && (i = e.height),
                {
                    x: n.offsetLeft,
                    y: n.offsetTop,
                    width: t,
                    height: i
                }
            }
            function hi(n, e) {
                var t = e.getRootNode && e.getRootNode();
                if (n.contains(e))
                    return !0;
                if (t && fn(t)) {
                    var i = e;
                    do {
                        if (i && n.isSameNode(i))
                            return !0;
                        i = i.parentNode || i.host
                    } while (i)
                }
                return !1
            }
            function Ae(n) {
                return se(n).getComputedStyle(n)
            }
            function Ko(n) {
                return ["table", "td", "th"].indexOf(ve(n)) >= 0
            }
            function Ce(n) {
                return (($e(n) ? n.ownerDocument : n.document) || window.document).documentElement
            }
            function kt(n) {
                return ve(n) === "html" ? n : n.assignedSlot || n.parentNode || (fn(n) ? n.host : null) || Ce(n)
            }
            function fi(n) {
                return !ce(n) || Ae(n).position === "fixed" ? null : n.offsetParent
            }
            function Wo(n) {
                var e = /firefox/i.test(mn())
                  , t = /Trident/i.test(mn());
                if (t && ce(n)) {
                    var i = Ae(n);
                    if (i.position === "fixed")
                        return null
                }
                var o = kt(n);
                for (fn(o) && (o = o.host); ce(o) && ["html", "body"].indexOf(ve(o)) < 0; ) {
                    var c = Ae(o);
                    if (c.transform !== "none" || c.perspective !== "none" || c.contain === "paint" || ["transform", "perspective"].indexOf(c.willChange) !== -1 || e && c.willChange === "filter" || e && c.filter && c.filter !== "none")
                        return o;
                    o = o.parentNode
                }
                return null
            }
            function bt(n) {
                for (var e = se(n), t = fi(n); t && Ko(t) && Ae(t).position === "static"; )
                    t = fi(t);
                return t && (ve(t) === "html" || ve(t) === "body" && Ae(t).position === "static") ? e : t || Wo(n) || e
            }
            function vn(n) {
                return ["top", "bottom"].indexOf(n) >= 0 ? "x" : "y"
            }
            function At(n, e, t) {
                return Pe(n, Ht(e, t))
            }
            function Bo(n, e, t) {
                var i = At(n, e, t);
                return i > t ? t : i
            }
            function yi() {
                return {
                    top: 0,
                    right: 0,
                    bottom: 0,
                    left: 0
                }
            }
            function mi(n) {
                return Object.assign({}, yi(), n)
            }
            function gi(n, e) {
                return e.reduce(function(t, i) {
                    return t[i] = n,
                    t
                }, {})
            }
            var Yo = function(e, t) {
                return e = typeof e == "function" ? e(Object.assign({}, t.rects, {
                    placement: t.placement
                })) : e,
                mi(typeof e != "number" ? e : gi(e, rt))
            };
            function Go(n) {
                var e, t = n.state, i = n.name, o = n.options, c = t.elements.arrow, d = t.modifiersData.popperOffsets, y = xe(t.placement), b = vn(y), M = [ne, oe].indexOf(y) >= 0, w = M ? "height" : "width";
                if (!(!c || !d)) {
                    var I = Yo(o.padding, t)
                      , P = gn(c)
                      , N = b === "y" ? te : ne
                      , B = b === "y" ? re : oe
                      , R = t.rects.reference[w] + t.rects.reference[b] - d[b] - t.rects.popper[w]
                      , z = d[b] - t.rects.reference[b]
                      , Y = bt(c)
                      , X = Y ? b === "y" ? Y.clientHeight || 0 : Y.clientWidth || 0 : 0
                      , Q = R / 2 - z / 2
                      , V = I[N]
                      , F = X - P[w] - I[B]
                      , K = X / 2 - P[w] / 2 + Q
                      , G = At(V, K, F)
                      , q = b;
                    t.modifiersData[i] = (e = {},
                    e[q] = G,
                    e.centerOffset = G - K,
                    e)
                }
            }
            function Uo(n) {
                var e = n.state
                  , t = n.options
                  , i = t.element
                  , o = i === void 0 ? "[data-popper-arrow]" : i;
                o != null && (typeof o == "string" && (o = e.elements.popper.querySelector(o),
                !o) || hi(e.elements.popper, o) && (e.elements.arrow = o))
            }
            const vi = {
                name: "arrow",
                enabled: !0,
                phase: "main",
                fn: Go,
                effect: Uo,
                requires: ["popperOffsets"],
                requiresIfExists: ["preventOverflow"]
            };
            function ct(n) {
                return n.split("-")[1]
            }
            var Xo = {
                top: "auto",
                right: "auto",
                bottom: "auto",
                left: "auto"
            };
            function Qo(n, e) {
                var t = n.x
                  , i = n.y
                  , o = e.devicePixelRatio || 1;
                return {
                    x: at(t * o) / o || 0,
                    y: at(i * o) / o || 0
                }
            }
            function xi(n) {
                var e, t = n.popper, i = n.popperRect, o = n.placement, c = n.variation, d = n.offsets, y = n.position, b = n.gpuAcceleration, M = n.adaptive, w = n.roundOffsets, I = n.isFixed, P = d.x, N = P === void 0 ? 0 : P, B = d.y, R = B === void 0 ? 0 : B, z = typeof w == "function" ? w({
                    x: N,
                    y: R
                }) : {
                    x: N,
                    y: R
                };
                N = z.x,
                R = z.y;
                var Y = d.hasOwnProperty("x")
                  , X = d.hasOwnProperty("y")
                  , Q = ne
                  , V = te
                  , F = window;
                if (M) {
                    var K = bt(t)
                      , G = "clientHeight"
                      , q = "clientWidth";
                    if (K === se(t) && (K = Ce(t),
                    Ae(K).position !== "static" && y === "absolute" && (G = "scrollHeight",
                    q = "scrollWidth")),
                    K = K,
                    o === te || (o === ne || o === oe) && c === ot) {
                        V = re;
                        var J = I && K === F && F.visualViewport ? F.visualViewport.height : K[G];
                        R -= J - i.height,
                        R *= b ? 1 : -1
                    }
                    if (o === ne || (o === te || o === re) && c === ot) {
                        Q = oe;
                        var Z = I && K === F && F.visualViewport ? F.visualViewport.width : K[q];
                        N -= Z - i.width,
                        N *= b ? 1 : -1
                    }
                }
                var ee = Object.assign({
                    position: y
                }, M && Xo)
                  , ye = w === !0 ? Qo({
                    x: N,
                    y: R
                }, se(t)) : {
                    x: N,
                    y: R
                };
                if (N = ye.x,
                R = ye.y,
                b) {
                    var ie;
                    return Object.assign({}, ee, (ie = {},
                    ie[V] = X ? "0" : "",
                    ie[Q] = Y ? "0" : "",
                    ie.transform = (F.devicePixelRatio || 1) <= 1 ? "translate(" + N + "px, " + R + "px)" : "translate3d(" + N + "px, " + R + "px, 0)",
                    ie))
                }
                return Object.assign({}, ee, (e = {},
                e[V] = X ? R + "px" : "",
                e[Q] = Y ? N + "px" : "",
                e.transform = "",
                e))
            }
            function Zo(n) {
                var e = n.state
                  , t = n.options
                  , i = t.gpuAcceleration
                  , o = i === void 0 ? !0 : i
                  , c = t.adaptive
                  , d = c === void 0 ? !0 : c
                  , y = t.roundOffsets
                  , b = y === void 0 ? !0 : y
                  , M = {
                    placement: xe(e.placement),
                    variation: ct(e.placement),
                    popper: e.elements.popper,
                    popperRect: e.rects.popper,
                    gpuAcceleration: o,
                    isFixed: e.options.strategy === "fixed"
                };
                e.modifiersData.popperOffsets != null && (e.styles.popper = Object.assign({}, e.styles.popper, xi(Object.assign({}, M, {
                    offsets: e.modifiersData.popperOffsets,
                    position: e.options.strategy,
                    adaptive: d,
                    roundOffsets: b
                })))),
                e.modifiersData.arrow != null && (e.styles.arrow = Object.assign({}, e.styles.arrow, xi(Object.assign({}, M, {
                    offsets: e.modifiersData.arrow,
                    position: "absolute",
                    adaptive: !1,
                    roundOffsets: b
                })))),
                e.attributes.popper = Object.assign({}, e.attributes.popper, {
                    "data-popper-placement": e.placement
                })
            }
            const xn = {
                name: "computeStyles",
                enabled: !0,
                phase: "beforeWrite",
                fn: Zo,
                data: {}
            };
            var zt = {
                passive: !0
            };
            function Jo(n) {
                var e = n.state
                  , t = n.instance
                  , i = n.options
                  , o = i.scroll
                  , c = o === void 0 ? !0 : o
                  , d = i.resize
                  , y = d === void 0 ? !0 : d
                  , b = se(e.elements.popper)
                  , M = [].concat(e.scrollParents.reference, e.scrollParents.popper);
                return c && M.forEach(function(w) {
                    w.addEventListener("scroll", t.update, zt)
                }),
                y && b.addEventListener("resize", t.update, zt),
                function() {
                    c && M.forEach(function(w) {
                        w.removeEventListener("scroll", t.update, zt)
                    }),
                    y && b.removeEventListener("resize", t.update, zt)
                }
            }
            const _n = {
                name: "eventListeners",
                enabled: !0,
                phase: "write",
                fn: function() {},
                effect: Jo,
                data: {}
            };
            var qo = {
                left: "right",
                right: "left",
                bottom: "top",
                top: "bottom"
            };
            function Ft(n) {
                return n.replace(/left|right|bottom|top/g, function(e) {
                    return qo[e]
                })
            }
            var es = {
                start: "end",
                end: "start"
            };
            function _i(n) {
                return n.replace(/start|end/g, function(e) {
                    return es[e]
                })
            }
            function En(n) {
                var e = se(n)
                  , t = e.pageXOffset
                  , i = e.pageYOffset;
                return {
                    scrollLeft: t,
                    scrollTop: i
                }
            }
            function bn(n) {
                return lt(Ce(n)).left + En(n).scrollLeft
            }
            function ts(n, e) {
                var t = se(n)
                  , i = Ce(n)
                  , o = t.visualViewport
                  , c = i.clientWidth
                  , d = i.clientHeight
                  , y = 0
                  , b = 0;
                if (o) {
                    c = o.width,
                    d = o.height;
                    var M = pi();
                    (M || !M && e === "fixed") && (y = o.offsetLeft,
                    b = o.offsetTop)
                }
                return {
                    width: c,
                    height: d,
                    x: y + bn(n),
                    y: b
                }
            }
            function ns(n) {
                var e, t = Ce(n), i = En(n), o = (e = n.ownerDocument) == null ? void 0 : e.body, c = Pe(t.scrollWidth, t.clientWidth, o ? o.scrollWidth : 0, o ? o.clientWidth : 0), d = Pe(t.scrollHeight, t.clientHeight, o ? o.scrollHeight : 0, o ? o.clientHeight : 0), y = -i.scrollLeft + bn(n), b = -i.scrollTop;
                return Ae(o || t).direction === "rtl" && (y += Pe(t.clientWidth, o ? o.clientWidth : 0) - c),
                {
                    width: c,
                    height: d,
                    x: y,
                    y: b
                }
            }
            function An(n) {
                var e = Ae(n)
                  , t = e.overflow
                  , i = e.overflowX
                  , o = e.overflowY;
                return /auto|scroll|overlay|hidden/.test(t + o + i)
            }
            function Ei(n) {
                return ["html", "body", "#document"].indexOf(ve(n)) >= 0 ? n.ownerDocument.body : ce(n) && An(n) ? n : Ei(kt(n))
            }
            function wt(n, e) {
                var t;
                e === void 0 && (e = []);
                var i = Ei(n)
                  , o = i === ((t = n.ownerDocument) == null ? void 0 : t.body)
                  , c = se(i)
                  , d = o ? [c].concat(c.visualViewport || [], An(i) ? i : []) : i
                  , y = e.concat(d);
                return o ? y : y.concat(wt(kt(d)))
            }
            function wn(n) {
                return Object.assign({}, n, {
                    left: n.x,
                    top: n.y,
                    right: n.x + n.width,
                    bottom: n.y + n.height
                })
            }
            function is(n, e) {
                var t = lt(n, !1, e === "fixed");
                return t.top = t.top + n.clientTop,
                t.left = t.left + n.clientLeft,
                t.bottom = t.top + n.clientHeight,
                t.right = t.left + n.clientWidth,
                t.width = n.clientWidth,
                t.height = n.clientHeight,
                t.x = t.left,
                t.y = t.top,
                t
            }
            function bi(n, e, t) {
                return e === un ? wn(ts(n, t)) : $e(e) ? is(e, t) : wn(ns(Ce(n)))
            }
            function rs(n) {
                var e = wt(kt(n))
                  , t = ["absolute", "fixed"].indexOf(Ae(n).position) >= 0
                  , i = t && ce(n) ? bt(n) : n;
                return $e(i) ? e.filter(function(o) {
                    return $e(o) && hi(o, i) && ve(o) !== "body"
                }) : []
            }
            function os(n, e, t, i) {
                var o = e === "clippingParents" ? rs(n) : [].concat(e)
                  , c = [].concat(o, [t])
                  , d = c[0]
                  , y = c.reduce(function(b, M) {
                    var w = bi(n, M, i);
                    return b.top = Pe(w.top, b.top),
                    b.right = Ht(w.right, b.right),
                    b.bottom = Ht(w.bottom, b.bottom),
                    b.left = Pe(w.left, b.left),
                    b
                }, bi(n, d, i));
                return y.width = y.right - y.left,
                y.height = y.bottom - y.top,
                y.x = y.left,
                y.y = y.top,
                y
            }
            function Ai(n) {
                var e = n.reference, t = n.element, i = n.placement, o = i ? xe(i) : null, c = i ? ct(i) : null, d = e.x + e.width / 2 - t.width / 2, y = e.y + e.height / 2 - t.height / 2, b;
                switch (o) {
                case te:
                    b = {
                        x: d,
                        y: e.y - t.height
                    };
                    break;
                case re:
                    b = {
                        x: d,
                        y: e.y + e.height
                    };
                    break;
                case oe:
                    b = {
                        x: e.x + e.width,
                        y
                    };
                    break;
                case ne:
                    b = {
                        x: e.x - t.width,
                        y
                    };
                    break;
                default:
                    b = {
                        x: e.x,
                        y: e.y
                    }
                }
                var M = o ? vn(o) : null;
                if (M != null) {
                    var w = M === "y" ? "height" : "width";
                    switch (c) {
                    case Ie:
                        b[M] = b[M] - (e[w] / 2 - t[w] / 2);
                        break;
                    case ot:
                        b[M] = b[M] + (e[w] / 2 - t[w] / 2);
                        break
                    }
                }
                return b
            }
            function dt(n, e) {
                e === void 0 && (e = {});
                var t = e
                  , i = t.placement
                  , o = i === void 0 ? n.placement : i
                  , c = t.strategy
                  , d = c === void 0 ? n.strategy : c
                  , y = t.boundary
                  , b = y === void 0 ? ei : y
                  , M = t.rootBoundary
                  , w = M === void 0 ? un : M
                  , I = t.elementContext
                  , P = I === void 0 ? st : I
                  , N = t.altBoundary
                  , B = N === void 0 ? !1 : N
                  , R = t.padding
                  , z = R === void 0 ? 0 : R
                  , Y = mi(typeof z != "number" ? z : gi(z, rt))
                  , X = P === st ? ti : st
                  , Q = n.rects.popper
                  , V = n.elements[B ? X : P]
                  , F = os($e(V) ? V : V.contextElement || Ce(n.elements.popper), b, w, d)
                  , K = lt(n.elements.reference)
                  , G = Ai({
                    reference: K,
                    element: Q,
                    strategy: "absolute",
                    placement: o
                })
                  , q = wn(Object.assign({}, Q, G))
                  , J = P === st ? q : K
                  , Z = {
                    top: F.top - J.top + Y.top,
                    bottom: J.bottom - F.bottom + Y.bottom,
                    left: F.left - J.left + Y.left,
                    right: J.right - F.right + Y.right
                }
                  , ee = n.modifiersData.offset;
                if (P === st && ee) {
                    var ye = ee[o];
                    Object.keys(Z).forEach(function(ie) {
                        var We = [oe, re].indexOf(ie) >= 0 ? 1 : -1
                          , Be = [te, re].indexOf(ie) >= 0 ? "y" : "x";
                        Z[ie] += ye[Be] * We
                    })
                }
                return Z
            }
            function ss(n, e) {
                e === void 0 && (e = {});
                var t = e
                  , i = t.placement
                  , o = t.boundary
                  , c = t.rootBoundary
                  , d = t.padding
                  , y = t.flipVariations
                  , b = t.allowedAutoPlacements
                  , M = b === void 0 ? hn : b
                  , w = ct(i)
                  , I = w ? y ? pn : pn.filter(function(B) {
                    return ct(B) === w
                }) : rt
                  , P = I.filter(function(B) {
                    return M.indexOf(B) >= 0
                });
                P.length === 0 && (P = I);
                var N = P.reduce(function(B, R) {
                    return B[R] = dt(n, {
                        placement: R,
                        boundary: o,
                        rootBoundary: c,
                        padding: d
                    })[xe(R)],
                    B
                }, {});
                return Object.keys(N).sort(function(B, R) {
                    return N[B] - N[R]
                })
            }
            function as(n) {
                if (xe(n) === Vt)
                    return [];
                var e = Ft(n);
                return [_i(n), e, _i(e)]
            }
            function ls(n) {
                var e = n.state
                  , t = n.options
                  , i = n.name;
                if (!e.modifiersData[i]._skip) {
                    for (var o = t.mainAxis, c = o === void 0 ? !0 : o, d = t.altAxis, y = d === void 0 ? !0 : d, b = t.fallbackPlacements, M = t.padding, w = t.boundary, I = t.rootBoundary, P = t.altBoundary, N = t.flipVariations, B = N === void 0 ? !0 : N, R = t.allowedAutoPlacements, z = e.options.placement, Y = xe(z), X = Y === z, Q = b || (X || !B ? [Ft(z)] : as(z)), V = [z].concat(Q).reduce(function(ht, Le) {
                        return ht.concat(xe(Le) === Vt ? ss(e, {
                            placement: Le,
                            boundary: w,
                            rootBoundary: I,
                            padding: M,
                            flipVariations: B,
                            allowedAutoPlacements: R
                        }) : Le)
                    }, []), F = e.rects.reference, K = e.rects.popper, G = new Map, q = !0, J = V[0], Z = 0; Z < V.length; Z++) {
                        var ee = V[Z]
                          , ye = xe(ee)
                          , ie = ct(ee) === Ie
                          , We = [te, re].indexOf(ye) >= 0
                          , Be = We ? "width" : "height"
                          , ae = dt(e, {
                            placement: ee,
                            boundary: w,
                            rootBoundary: I,
                            altBoundary: P,
                            padding: M
                        })
                          , me = We ? ie ? oe : ne : ie ? re : te;
                        F[Be] > K[Be] && (me = Ft(me));
                        var Zt = Ft(me)
                          , Ye = [];
                        if (c && Ye.push(ae[ye] <= 0),
                        y && Ye.push(ae[me] <= 0, ae[Zt] <= 0),
                        Ye.every(function(ht) {
                            return ht
                        })) {
                            J = ee,
                            q = !1;
                            break
                        }
                        G.set(ee, Ye)
                    }
                    if (q)
                        for (var Jt = B ? 3 : 1, Vn = function(Le) {
                            var Mt = V.find(function(en) {
                                var Ge = G.get(en);
                                if (Ge)
                                    return Ge.slice(0, Le).every(function(Hn) {
                                        return Hn
                                    })
                            });
                            if (Mt)
                                return J = Mt,
                                "break"
                        }, St = Jt; St > 0; St--) {
                            var qt = Vn(St);
                            if (qt === "break")
                                break
                        }
                    e.placement !== J && (e.modifiersData[i]._skip = !0,
                    e.placement = J,
                    e.reset = !0)
                }
            }
            const wi = {
                name: "flip",
                enabled: !0,
                phase: "main",
                fn: ls,
                requiresIfExists: ["offset"],
                data: {
                    _skip: !1
                }
            };
            function ji(n, e, t) {
                return t === void 0 && (t = {
                    x: 0,
                    y: 0
                }),
                {
                    top: n.top - e.height - t.y,
                    right: n.right - e.width + t.x,
                    bottom: n.bottom - e.height + t.y,
                    left: n.left - e.width - t.x
                }
            }
            function Ti(n) {
                return [te, oe, re, ne].some(function(e) {
                    return n[e] >= 0
                })
            }
            function cs(n) {
                var e = n.state
                  , t = n.name
                  , i = e.rects.reference
                  , o = e.rects.popper
                  , c = e.modifiersData.preventOverflow
                  , d = dt(e, {
                    elementContext: "reference"
                })
                  , y = dt(e, {
                    altBoundary: !0
                })
                  , b = ji(d, i)
                  , M = ji(y, o, c)
                  , w = Ti(b)
                  , I = Ti(M);
                e.modifiersData[t] = {
                    referenceClippingOffsets: b,
                    popperEscapeOffsets: M,
                    isReferenceHidden: w,
                    hasPopperEscaped: I
                },
                e.attributes.popper = Object.assign({}, e.attributes.popper, {
                    "data-popper-reference-hidden": w,
                    "data-popper-escaped": I
                })
            }
            const Oi = {
                name: "hide",
                enabled: !0,
                phase: "main",
                requiresIfExists: ["preventOverflow"],
                fn: cs
            };
            function ds(n, e, t) {
                var i = xe(n)
                  , o = [ne, te].indexOf(i) >= 0 ? -1 : 1
                  , c = typeof t == "function" ? t(Object.assign({}, e, {
                    placement: n
                })) : t
                  , d = c[0]
                  , y = c[1];
                return d = d || 0,
                y = (y || 0) * o,
                [ne, oe].indexOf(i) >= 0 ? {
                    x: y,
                    y: d
                } : {
                    x: d,
                    y
                }
            }
            function us(n) {
                var e = n.state
                  , t = n.options
                  , i = n.name
                  , o = t.offset
                  , c = o === void 0 ? [0, 0] : o
                  , d = hn.reduce(function(w, I) {
                    return w[I] = ds(I, e.rects, c),
                    w
                }, {})
                  , y = d[e.placement]
                  , b = y.x
                  , M = y.y;
                e.modifiersData.popperOffsets != null && (e.modifiersData.popperOffsets.x += b,
                e.modifiersData.popperOffsets.y += M),
                e.modifiersData[i] = d
            }
            const Si = {
                name: "offset",
                enabled: !0,
                phase: "main",
                requires: ["popperOffsets"],
                fn: us
            };
            function ps(n) {
                var e = n.state
                  , t = n.name;
                e.modifiersData[t] = Ai({
                    reference: e.rects.reference,
                    element: e.rects.popper,
                    strategy: "absolute",
                    placement: e.placement
                })
            }
            const jn = {
                name: "popperOffsets",
                enabled: !0,
                phase: "read",
                fn: ps,
                data: {}
            };
            function hs(n) {
                return n === "x" ? "y" : "x"
            }
            function fs(n) {
                var e = n.state
                  , t = n.options
                  , i = n.name
                  , o = t.mainAxis
                  , c = o === void 0 ? !0 : o
                  , d = t.altAxis
                  , y = d === void 0 ? !1 : d
                  , b = t.boundary
                  , M = t.rootBoundary
                  , w = t.altBoundary
                  , I = t.padding
                  , P = t.tether
                  , N = P === void 0 ? !0 : P
                  , B = t.tetherOffset
                  , R = B === void 0 ? 0 : B
                  , z = dt(e, {
                    boundary: b,
                    rootBoundary: M,
                    padding: I,
                    altBoundary: w
                })
                  , Y = xe(e.placement)
                  , X = ct(e.placement)
                  , Q = !X
                  , V = vn(Y)
                  , F = hs(V)
                  , K = e.modifiersData.popperOffsets
                  , G = e.rects.reference
                  , q = e.rects.popper
                  , J = typeof R == "function" ? R(Object.assign({}, e.rects, {
                    placement: e.placement
                })) : R
                  , Z = typeof J == "number" ? {
                    mainAxis: J,
                    altAxis: J
                } : Object.assign({
                    mainAxis: 0,
                    altAxis: 0
                }, J)
                  , ee = e.modifiersData.offset ? e.modifiersData.offset[e.placement] : null
                  , ye = {
                    x: 0,
                    y: 0
                };
                if (K) {
                    if (c) {
                        var ie, We = V === "y" ? te : ne, Be = V === "y" ? re : oe, ae = V === "y" ? "height" : "width", me = K[V], Zt = me + z[We], Ye = me - z[Be], Jt = N ? -q[ae] / 2 : 0, Vn = X === Ie ? G[ae] : q[ae], St = X === Ie ? -q[ae] : -G[ae], qt = e.elements.arrow, ht = N && qt ? gn(qt) : {
                            width: 0,
                            height: 0
                        }, Le = e.modifiersData["arrow#persistent"] ? e.modifiersData["arrow#persistent"].padding : yi(), Mt = Le[We], en = Le[Be], Ge = At(0, G[ae], ht[ae]), Hn = Q ? G[ae] / 2 - Jt - Ge - Mt - Z.mainAxis : Vn - Ge - Mt - Z.mainAxis, sc = Q ? -G[ae] / 2 + Jt + Ge + en + Z.mainAxis : St + Ge + en + Z.mainAxis, kn = e.elements.arrow && bt(e.elements.arrow), ac = kn ? V === "y" ? kn.clientTop || 0 : kn.clientLeft || 0 : 0, mr = (ie = ee == null ? void 0 : ee[V]) != null ? ie : 0, lc = me + Hn - mr - ac, cc = me + sc - mr, gr = At(N ? Ht(Zt, lc) : Zt, me, N ? Pe(Ye, cc) : Ye);
                        K[V] = gr,
                        ye[V] = gr - me
                    }
                    if (y) {
                        var vr, dc = V === "x" ? te : ne, uc = V === "x" ? re : oe, Ue = K[F], tn = F === "y" ? "height" : "width", xr = Ue + z[dc], _r = Ue - z[uc], zn = [te, ne].indexOf(Y) !== -1, Er = (vr = ee == null ? void 0 : ee[F]) != null ? vr : 0, br = zn ? xr : Ue - G[tn] - q[tn] - Er + Z.altAxis, Ar = zn ? Ue + G[tn] + q[tn] - Er - Z.altAxis : _r, wr = N && zn ? Bo(br, Ue, Ar) : At(N ? br : xr, Ue, N ? Ar : _r);
                        K[F] = wr,
                        ye[F] = wr - Ue
                    }
                    e.modifiersData[i] = ye
                }
            }
            const Mi = {
                name: "preventOverflow",
                enabled: !0,
                phase: "main",
                fn: fs,
                requiresIfExists: ["offset"]
            };
            function ys(n) {
                return {
                    scrollLeft: n.scrollLeft,
                    scrollTop: n.scrollTop
                }
            }
            function ms(n) {
                return n === se(n) || !ce(n) ? En(n) : ys(n)
            }
            function gs(n) {
                var e = n.getBoundingClientRect()
                  , t = at(e.width) / n.offsetWidth || 1
                  , i = at(e.height) / n.offsetHeight || 1;
                return t !== 1 || i !== 1
            }
            function vs(n, e, t) {
                t === void 0 && (t = !1);
                var i = ce(e)
                  , o = ce(e) && gs(e)
                  , c = Ce(e)
                  , d = lt(n, o, t)
                  , y = {
                    scrollLeft: 0,
                    scrollTop: 0
                }
                  , b = {
                    x: 0,
                    y: 0
                };
                return (i || !i && !t) && ((ve(e) !== "body" || An(c)) && (y = ms(e)),
                ce(e) ? (b = lt(e, !0),
                b.x += e.clientLeft,
                b.y += e.clientTop) : c && (b.x = bn(c))),
                {
                    x: d.left + y.scrollLeft - b.x,
                    y: d.top + y.scrollTop - b.y,
                    width: d.width,
                    height: d.height
                }
            }
            function xs(n) {
                var e = new Map
                  , t = new Set
                  , i = [];
                n.forEach(function(c) {
                    e.set(c.name, c)
                });
                function o(c) {
                    t.add(c.name);
                    var d = [].concat(c.requires || [], c.requiresIfExists || []);
                    d.forEach(function(y) {
                        if (!t.has(y)) {
                            var b = e.get(y);
                            b && o(b)
                        }
                    }),
                    i.push(c)
                }
                return n.forEach(function(c) {
                    t.has(c.name) || o(c)
                }),
                i
            }
            function _s(n) {
                var e = xs(n);
                return ui.reduce(function(t, i) {
                    return t.concat(e.filter(function(o) {
                        return o.phase === i
                    }))
                }, [])
            }
            function Es(n) {
                var e;
                return function() {
                    return e || (e = new Promise(function(t) {
                        Promise.resolve().then(function() {
                            e = void 0,
                            t(n())
                        })
                    }
                    )),
                    e
                }
            }
            function bs(n) {
                var e = n.reduce(function(t, i) {
                    var o = t[i.name];
                    return t[i.name] = o ? Object.assign({}, o, i, {
                        options: Object.assign({}, o.options, i.options),
                        data: Object.assign({}, o.data, i.data)
                    }) : i,
                    t
                }, {});
                return Object.keys(e).map(function(t) {
                    return e[t]
                })
            }
            var Ci = {
                placement: "bottom",
                modifiers: [],
                strategy: "absolute"
            };
            function Ni() {
                for (var n = arguments.length, e = new Array(n), t = 0; t < n; t++)
                    e[t] = arguments[t];
                return !e.some(function(i) {
                    return !(i && typeof i.getBoundingClientRect == "function")
                })
            }
            function Kt(n) {
                n === void 0 && (n = {});
                var e = n
                  , t = e.defaultModifiers
                  , i = t === void 0 ? [] : t
                  , o = e.defaultOptions
                  , c = o === void 0 ? Ci : o;
                return function(y, b, M) {
                    M === void 0 && (M = c);
                    var w = {
                        placement: "bottom",
                        orderedModifiers: [],
                        options: Object.assign({}, Ci, c),
                        modifiersData: {},
                        elements: {
                            reference: y,
                            popper: b
                        },
                        attributes: {},
                        styles: {}
                    }
                      , I = []
                      , P = !1
                      , N = {
                        state: w,
                        setOptions: function(Y) {
                            var X = typeof Y == "function" ? Y(w.options) : Y;
                            R(),
                            w.options = Object.assign({}, c, w.options, X),
                            w.scrollParents = {
                                reference: $e(y) ? wt(y) : y.contextElement ? wt(y.contextElement) : [],
                                popper: wt(b)
                            };
                            var Q = _s(bs([].concat(i, w.options.modifiers)));
                            return w.orderedModifiers = Q.filter(function(V) {
                                return V.enabled
                            }),
                            B(),
                            N.update()
                        },
                        forceUpdate: function() {
                            if (!P) {
                                var Y = w.elements
                                  , X = Y.reference
                                  , Q = Y.popper;
                                if (Ni(X, Q)) {
                                    w.rects = {
                                        reference: vs(X, bt(Q), w.options.strategy === "fixed"),
                                        popper: gn(Q)
                                    },
                                    w.reset = !1,
                                    w.placement = w.options.placement,
                                    w.orderedModifiers.forEach(function(Z) {
                                        return w.modifiersData[Z.name] = Object.assign({}, Z.data)
                                    });
                                    for (var V = 0; V < w.orderedModifiers.length; V++) {
                                        if (w.reset === !0) {
                                            w.reset = !1,
                                            V = -1;
                                            continue
                                        }
                                        var F = w.orderedModifiers[V]
                                          , K = F.fn
                                          , G = F.options
                                          , q = G === void 0 ? {} : G
                                          , J = F.name;
                                        typeof K == "function" && (w = K({
                                            state: w,
                                            options: q,
                                            name: J,
                                            instance: N
                                        }) || w)
                                    }
                                }
                            }
                        },
                        update: Es(function() {
                            return new Promise(function(z) {
                                N.forceUpdate(),
                                z(w)
                            }
                            )
                        }),
                        destroy: function() {
                            R(),
                            P = !0
                        }
                    };
                    if (!Ni(y, b))
                        return N;
                    N.setOptions(M).then(function(z) {
                        !P && M.onFirstUpdate && M.onFirstUpdate(z)
                    });
                    function B() {
                        w.orderedModifiers.forEach(function(z) {
                            var Y = z.name
                              , X = z.options
                              , Q = X === void 0 ? {} : X
                              , V = z.effect;
                            if (typeof V == "function") {
                                var F = V({
                                    state: w,
                                    name: Y,
                                    instance: N,
                                    options: Q
                                })
                                  , K = function() {};
                                I.push(F || K)
                            }
                        })
                    }
                    function R() {
                        I.forEach(function(z) {
                            return z()
                        }),
                        I = []
                    }
                    return N
                }
            }
            var As = Kt()
              , ws = [_n, jn, xn, yn]
              , js = Kt({
                defaultModifiers: ws
            })
              , Ts = [_n, jn, xn, yn, Si, wi, Mi, vi, Oi]
              , Tn = Kt({
                defaultModifiers: Ts
            });
            const Li = Object.freeze(Object.defineProperty({
                __proto__: null,
                afterMain: ai,
                afterRead: ri,
                afterWrite: di,
                applyStyles: yn,
                arrow: vi,
                auto: Vt,
                basePlacements: rt,
                beforeMain: oi,
                beforeRead: ni,
                beforeWrite: li,
                bottom: re,
                clippingParents: ei,
                computeStyles: xn,
                createPopper: Tn,
                createPopperBase: As,
                createPopperLite: js,
                detectOverflow: dt,
                end: ot,
                eventListeners: _n,
                flip: wi,
                hide: Oi,
                left: ne,
                main: si,
                modifierPhases: ui,
                offset: Si,
                placements: hn,
                popper: st,
                popperGenerator: Kt,
                popperOffsets: jn,
                preventOverflow: Mi,
                read: ii,
                reference: ti,
                right: oe,
                start: Ie,
                top: te,
                variationPlacements: pn,
                viewport: un,
                write: ci
            }, Symbol.toStringTag, {
                value: "Module"
            }))
              , Di = "dropdown"
              , Re = ".bs.dropdown"
              , On = ".data-api"
              , Os = "Escape"
              , Ii = "Tab"
              , Ss = "ArrowUp"
              , $i = "ArrowDown"
              , Ms = 2
              , Cs = `hide${Re}`
              , Ns = `hidden${Re}`
              , Ls = `show${Re}`
              , Ds = `shown${Re}`
              , Pi = `click${Re}${On}`
              , Ri = `keydown${Re}${On}`
              , Is = `keyup${Re}${On}`
              , ut = "show"
              , $s = "dropup"
              , Ps = "dropend"
              , Rs = "dropstart"
              , Vs = "dropup-center"
              , Hs = "dropdown-center"
              , Ve = '[data-bs-toggle="dropdown"]:not(.disabled):not(:disabled)'
              , ks = `${Ve}.${ut}`
              , Wt = ".dropdown-menu"
              , zs = ".navbar"
              , Fs = ".navbar-nav"
              , Ks = ".dropdown-menu .dropdown-item:not(.disabled):not(:disabled)"
              , Ws = D() ? "top-end" : "top-start"
              , Bs = D() ? "top-start" : "top-end"
              , Ys = D() ? "bottom-end" : "bottom-start"
              , Gs = D() ? "bottom-start" : "bottom-end"
              , Us = D() ? "left-start" : "right-start"
              , Xs = D() ? "right-start" : "left-start"
              , Qs = "top"
              , Zs = "bottom"
              , Js = {
                autoClose: !0,
                boundary: "clippingParents",
                display: "dynamic",
                offset: [0, 2],
                popperConfig: null,
                reference: "toggle"
            }
              , qs = {
                autoClose: "(boolean|string)",
                boundary: "(string|element)",
                display: "string",
                offset: "(array|string|function)",
                popperConfig: "(null|object|function)",
                reference: "(string|element|object)"
            };
            class fe extends he {
                constructor(e, t) {
                    super(e, t),
                    this._popper = null,
                    this._parent = this._element.parentNode,
                    this._menu = C.next(this._element, Wt)[0] || C.prev(this._element, Wt)[0] || C.findOne(Wt, this._parent),
                    this._inNavbar = this._detectNavbar()
                }
                static get Default() {
                    return Js
                }
                static get DefaultType() {
                    return qs
                }
                static get NAME() {
                    return Di
                }
                toggle() {
                    return this._isShown() ? this.hide() : this.show()
                }
                show() {
                    if (v(this._element) || this._isShown())
                        return;
                    const e = {
                        relatedTarget: this._element
                    };
                    if (!x.trigger(this._element, Ls, e).defaultPrevented) {
                        if (this._createPopper(),
                        "ontouchstart"in document.documentElement && !this._parent.closest(Fs))
                            for (const i of [].concat(...document.body.children))
                                x.on(i, "mouseover", T);
                        this._element.focus(),
                        this._element.setAttribute("aria-expanded", !0),
                        this._menu.classList.add(ut),
                        this._element.classList.add(ut),
                        x.trigger(this._element, Ds, e)
                    }
                }
                hide() {
                    if (v(this._element) || !this._isShown())
                        return;
                    const e = {
                        relatedTarget: this._element
                    };
                    this._completeHide(e)
                }
                dispose() {
                    this._popper && this._popper.destroy(),
                    super.dispose()
                }
                update() {
                    this._inNavbar = this._detectNavbar(),
                    this._popper && this._popper.update()
                }
                _completeHide(e) {
                    if (!x.trigger(this._element, Cs, e).defaultPrevented) {
                        if ("ontouchstart"in document.documentElement)
                            for (const i of [].concat(...document.body.children))
                                x.off(i, "mouseover", T);
                        this._popper && this._popper.destroy(),
                        this._menu.classList.remove(ut),
                        this._element.classList.remove(ut),
                        this._element.setAttribute("aria-expanded", "false"),
                        be.removeDataAttribute(this._menu, "popper"),
                        x.trigger(this._element, Ns, e)
                    }
                }
                _getConfig(e) {
                    if (e = super._getConfig(e),
                    typeof e.reference == "object" && !E(e.reference) && typeof e.reference.getBoundingClientRect != "function")
                        throw new TypeError(`${Di.toUpperCase()}: Option "reference" provided type "object" without a required "getBoundingClientRect" method.`);
                    return e
                }
                _createPopper() {
                    if (typeof Li > "u")
                        throw new TypeError("Bootstrap's dropdowns require Popper (https://popper.js.org)");
                    let e = this._element;
                    this._config.reference === "parent" ? e = this._parent : E(this._config.reference) ? e = A(this._config.reference) : typeof this._config.reference == "object" && (e = this._config.reference);
                    const t = this._getPopperConfig();
                    this._popper = Tn(e, this._menu, t)
                }
                _isShown() {
                    return this._menu.classList.contains(ut)
                }
                _getPlacement() {
                    const e = this._parent;
                    if (e.classList.contains(Ps))
                        return Us;
                    if (e.classList.contains(Rs))
                        return Xs;
                    if (e.classList.contains(Vs))
                        return Qs;
                    if (e.classList.contains(Hs))
                        return Zs;
                    const t = getComputedStyle(this._menu).getPropertyValue("--bs-position").trim() === "end";
                    return e.classList.contains($s) ? t ? Bs : Ws : t ? Gs : Ys
                }
                _detectNavbar() {
                    return this._element.closest(zs) !== null
                }
                _getOffset() {
                    const {offset: e} = this._config;
                    return typeof e == "string" ? e.split(",").map(t=>Number.parseInt(t, 10)) : typeof e == "function" ? t=>e(t, this._element) : e
                }
                _getPopperConfig() {
                    const e = {
                        placement: this._getPlacement(),
                        modifiers: [{
                            name: "preventOverflow",
                            options: {
                                boundary: this._config.boundary
                            }
                        }, {
                            name: "offset",
                            options: {
                                offset: this._getOffset()
                            }
                        }]
                    };
                    return (this._inNavbar || this._config.display === "static") && (be.setDataAttribute(this._menu, "popper", "static"),
                    e.modifiers = [{
                        name: "applyStyles",
                        enabled: !1
                    }]),
                    {
                        ...e,
                        ...H(this._config.popperConfig, [e])
                    }
                }
                _selectMenuItem({key: e, target: t}) {
                    const i = C.find(Ks, this._menu).filter(o=>_(o));
                    i.length && yt(i, t, e === $i, !i.includes(t)).focus()
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = fe.getOrCreateInstance(this, e);
                        if (typeof e == "string") {
                            if (typeof t[e] > "u")
                                throw new TypeError(`No method named "${e}"`);
                            t[e]()
                        }
                    })
                }
                static clearMenus(e) {
                    if (e.button === Ms || e.type === "keyup" && e.key !== Ii)
                        return;
                    const t = C.find(ks);
                    for (const i of t) {
                        const o = fe.getInstance(i);
                        if (!o || o._config.autoClose === !1)
                            continue;
                        const c = e.composedPath()
                          , d = c.includes(o._menu);
                        if (c.includes(o._element) || o._config.autoClose === "inside" && !d || o._config.autoClose === "outside" && d || o._menu.contains(e.target) && (e.type === "keyup" && e.key === Ii || /input|select|option|textarea|form/i.test(e.target.tagName)))
                            continue;
                        const y = {
                            relatedTarget: o._element
                        };
                        e.type === "click" && (y.clickEvent = e),
                        o._completeHide(y)
                    }
                }
                static dataApiKeydownHandler(e) {
                    const t = /input|textarea/i.test(e.target.tagName)
                      , i = e.key === Os
                      , o = [Ss, $i].includes(e.key);
                    if (!o && !i || t && !i)
                        return;
                    e.preventDefault();
                    const c = this.matches(Ve) ? this : C.prev(this, Ve)[0] || C.next(this, Ve)[0] || C.findOne(Ve, e.delegateTarget.parentNode)
                      , d = fe.getOrCreateInstance(c);
                    if (o) {
                        e.stopPropagation(),
                        d.show(),
                        d._selectMenuItem(e);
                        return
                    }
                    d._isShown() && (e.stopPropagation(),
                    d.hide(),
                    c.focus())
                }
            }
            x.on(document, Ri, Ve, fe.dataApiKeydownHandler),
            x.on(document, Ri, Wt, fe.dataApiKeydownHandler),
            x.on(document, Pi, fe.clearMenus),
            x.on(document, Is, fe.clearMenus),
            x.on(document, Pi, Ve, function(n) {
                n.preventDefault(),
                fe.getOrCreateInstance(this).toggle()
            }),
            $(fe);
            const Vi = "backdrop"
              , ea = "fade"
              , Hi = "show"
              , ki = `mousedown.bs.${Vi}`
              , ta = {
                className: "modal-backdrop",
                clickCallback: null,
                isAnimated: !1,
                isVisible: !0,
                rootElement: "body"
            }
              , na = {
                className: "string",
                clickCallback: "(function|null)",
                isAnimated: "boolean",
                isVisible: "boolean",
                rootElement: "(element|string)"
            };
            class zi extends gt {
                constructor(e) {
                    super(),
                    this._config = this._getConfig(e),
                    this._isAppended = !1,
                    this._element = null
                }
                static get Default() {
                    return ta
                }
                static get DefaultType() {
                    return na
                }
                static get NAME() {
                    return Vi
                }
                show(e) {
                    if (!this._config.isVisible) {
                        H(e);
                        return
                    }
                    this._append();
                    const t = this._getElement();
                    this._config.isAnimated && j(t),
                    t.classList.add(Hi),
                    this._emulateAnimation(()=>{
                        H(e)
                    }
                    )
                }
                hide(e) {
                    if (!this._config.isVisible) {
                        H(e);
                        return
                    }
                    this._getElement().classList.remove(Hi),
                    this._emulateAnimation(()=>{
                        this.dispose(),
                        H(e)
                    }
                    )
                }
                dispose() {
                    this._isAppended && (x.off(this._element, ki),
                    this._element.remove(),
                    this._isAppended = !1)
                }
                _getElement() {
                    if (!this._element) {
                        const e = document.createElement("div");
                        e.className = this._config.className,
                        this._config.isAnimated && e.classList.add(ea),
                        this._element = e
                    }
                    return this._element
                }
                _configAfterMerge(e) {
                    return e.rootElement = A(e.rootElement),
                    e
                }
                _append() {
                    if (this._isAppended)
                        return;
                    const e = this._getElement();
                    this._config.rootElement.append(e),
                    x.on(e, ki, ()=>{
                        H(this._config.clickCallback)
                    }
                    ),
                    this._isAppended = !0
                }
                _emulateAnimation(e) {
                    ue(e, this._getElement(), this._config.isAnimated)
                }
            }
            const ia = "focustrap"
              , Bt = ".bs.focustrap"
              , ra = `focusin${Bt}`
              , oa = `keydown.tab${Bt}`
              , sa = "Tab"
              , aa = "forward"
              , Fi = "backward"
              , la = {
                autofocus: !0,
                trapElement: null
            }
              , ca = {
                autofocus: "boolean",
                trapElement: "element"
            };
            class Ki extends gt {
                constructor(e) {
                    super(),
                    this._config = this._getConfig(e),
                    this._isActive = !1,
                    this._lastTabNavDirection = null
                }
                static get Default() {
                    return la
                }
                static get DefaultType() {
                    return ca
                }
                static get NAME() {
                    return ia
                }
                activate() {
                    this._isActive || (this._config.autofocus && this._config.trapElement.focus(),
                    x.off(document, Bt),
                    x.on(document, ra, e=>this._handleFocusin(e)),
                    x.on(document, oa, e=>this._handleKeydown(e)),
                    this._isActive = !0)
                }
                deactivate() {
                    this._isActive && (this._isActive = !1,
                    x.off(document, Bt))
                }
                _handleFocusin(e) {
                    const {trapElement: t} = this._config;
                    if (e.target === document || e.target === t || t.contains(e.target))
                        return;
                    const i = C.focusableChildren(t);
                    i.length === 0 ? t.focus() : this._lastTabNavDirection === Fi ? i[i.length - 1].focus() : i[0].focus()
                }
                _handleKeydown(e) {
                    e.key === sa && (this._lastTabNavDirection = e.shiftKey ? Fi : aa)
                }
            }
            const Wi = ".fixed-top, .fixed-bottom, .is-fixed, .sticky-top"
              , Bi = ".sticky-top"
              , Yt = "padding-right"
              , Yi = "margin-right";
            class Sn {
                constructor() {
                    this._element = document.body
                }
                getWidth() {
                    const e = document.documentElement.clientWidth;
                    return Math.abs(window.innerWidth - e)
                }
                hide() {
                    const e = this.getWidth();
                    this._disableOverFlow(),
                    this._setElementAttributes(this._element, Yt, t=>t + e),
                    this._setElementAttributes(Wi, Yt, t=>t + e),
                    this._setElementAttributes(Bi, Yi, t=>t - e)
                }
                reset() {
                    this._resetElementAttributes(this._element, "overflow"),
                    this._resetElementAttributes(this._element, Yt),
                    this._resetElementAttributes(Wi, Yt),
                    this._resetElementAttributes(Bi, Yi)
                }
                isOverflowing() {
                    return this.getWidth() > 0
                }
                _disableOverFlow() {
                    this._saveInitialAttribute(this._element, "overflow"),
                    this._element.style.overflow = "hidden"
                }
                _setElementAttributes(e, t, i) {
                    const o = this.getWidth()
                      , c = d=>{
                        if (d !== this._element && window.innerWidth > d.clientWidth + o)
                            return;
                        this._saveInitialAttribute(d, t);
                        const y = window.getComputedStyle(d).getPropertyValue(t);
                        d.style.setProperty(t, `${i(Number.parseFloat(y))}px`)
                    }
                    ;
                    this._applyManipulationCallback(e, c)
                }
                _saveInitialAttribute(e, t) {
                    const i = e.style.getPropertyValue(t);
                    i && be.setDataAttribute(e, t, i)
                }
                _resetElementAttributes(e, t) {
                    const i = o=>{
                        const c = be.getDataAttribute(o, t);
                        if (c === null) {
                            o.style.removeProperty(t);
                            return
                        }
                        be.removeDataAttribute(o, t),
                        o.style.setProperty(t, c)
                    }
                    ;
                    this._applyManipulationCallback(e, i)
                }
                _applyManipulationCallback(e, t) {
                    if (E(e)) {
                        t(e);
                        return
                    }
                    for (const i of C.find(e, this._element))
                        t(i)
                }
            }
            const da = "modal"
              , de = ".bs.modal"
              , ua = ".data-api"
              , pa = "Escape"
              , ha = `hide${de}`
              , fa = `hidePrevented${de}`
              , Gi = `hidden${de}`
              , Ui = `show${de}`
              , ya = `shown${de}`
              , ma = `resize${de}`
              , ga = `click.dismiss${de}`
              , va = `mousedown.dismiss${de}`
              , xa = `keydown.dismiss${de}`
              , _a = `click${de}${ua}`
              , Xi = "modal-open"
              , Ea = "fade"
              , Qi = "show"
              , Mn = "modal-static"
              , ba = ".modal.show"
              , Aa = ".modal-dialog"
              , wa = ".modal-body"
              , ja = '[data-bs-toggle="modal"]'
              , Ta = {
                backdrop: !0,
                focus: !0,
                keyboard: !0
            }
              , Oa = {
                backdrop: "(boolean|string)",
                focus: "boolean",
                keyboard: "boolean"
            };
            class He extends he {
                constructor(e, t) {
                    super(e, t),
                    this._dialog = C.findOne(Aa, this._element),
                    this._backdrop = this._initializeBackDrop(),
                    this._focustrap = this._initializeFocusTrap(),
                    this._isShown = !1,
                    this._isTransitioning = !1,
                    this._scrollBar = new Sn,
                    this._addEventListeners()
                }
                static get Default() {
                    return Ta
                }
                static get DefaultType() {
                    return Oa
                }
                static get NAME() {
                    return da
                }
                toggle(e) {
                    return this._isShown ? this.hide() : this.show(e)
                }
                show(e) {
                    this._isShown || this._isTransitioning || x.trigger(this._element, Ui, {
                        relatedTarget: e
                    }).defaultPrevented || (this._isShown = !0,
                    this._isTransitioning = !0,
                    this._scrollBar.hide(),
                    document.body.classList.add(Xi),
                    this._adjustDialog(),
                    this._backdrop.show(()=>this._showElement(e)))
                }
                hide() {
                    !this._isShown || this._isTransitioning || x.trigger(this._element, ha).defaultPrevented || (this._isShown = !1,
                    this._isTransitioning = !0,
                    this._focustrap.deactivate(),
                    this._element.classList.remove(Qi),
                    this._queueCallback(()=>this._hideModal(), this._element, this._isAnimated()))
                }
                dispose() {
                    x.off(window, de),
                    x.off(this._dialog, de),
                    this._backdrop.dispose(),
                    this._focustrap.deactivate(),
                    super.dispose()
                }
                handleUpdate() {
                    this._adjustDialog()
                }
                _initializeBackDrop() {
                    return new zi({
                        isVisible: !!this._config.backdrop,
                        isAnimated: this._isAnimated()
                    })
                }
                _initializeFocusTrap() {
                    return new Ki({
                        trapElement: this._element
                    })
                }
                _showElement(e) {
                    document.body.contains(this._element) || document.body.append(this._element),
                    this._element.style.display = "block",
                    this._element.removeAttribute("aria-hidden"),
                    this._element.setAttribute("aria-modal", !0),
                    this._element.setAttribute("role", "dialog"),
                    this._element.scrollTop = 0;
                    const t = C.findOne(wa, this._dialog);
                    t && (t.scrollTop = 0),
                    j(this._element),
                    this._element.classList.add(Qi);
                    const i = ()=>{
                        this._config.focus && this._focustrap.activate(),
                        this._isTransitioning = !1,
                        x.trigger(this._element, ya, {
                            relatedTarget: e
                        })
                    }
                    ;
                    this._queueCallback(i, this._dialog, this._isAnimated())
                }
                _addEventListeners() {
                    x.on(this._element, xa, e=>{
                        if (e.key === pa) {
                            if (this._config.keyboard) {
                                this.hide();
                                return
                            }
                            this._triggerBackdropTransition()
                        }
                    }
                    ),
                    x.on(window, ma, ()=>{
                        this._isShown && !this._isTransitioning && this._adjustDialog()
                    }
                    ),
                    x.on(this._element, va, e=>{
                        x.one(this._element, ga, t=>{
                            if (!(this._element !== e.target || this._element !== t.target)) {
                                if (this._config.backdrop === "static") {
                                    this._triggerBackdropTransition();
                                    return
                                }
                                this._config.backdrop && this.hide()
                            }
                        }
                        )
                    }
                    )
                }
                _hideModal() {
                    this._element.style.display = "none",
                    this._element.setAttribute("aria-hidden", !0),
                    this._element.removeAttribute("aria-modal"),
                    this._element.removeAttribute("role"),
                    this._isTransitioning = !1,
                    this._backdrop.hide(()=>{
                        document.body.classList.remove(Xi),
                        this._resetAdjustments(),
                        this._scrollBar.reset(),
                        x.trigger(this._element, Gi)
                    }
                    )
                }
                _isAnimated() {
                    return this._element.classList.contains(Ea)
                }
                _triggerBackdropTransition() {
                    if (x.trigger(this._element, fa).defaultPrevented)
                        return;
                    const t = this._element.scrollHeight > document.documentElement.clientHeight
                      , i = this._element.style.overflowY;
                    i === "hidden" || this._element.classList.contains(Mn) || (t || (this._element.style.overflowY = "hidden"),
                    this._element.classList.add(Mn),
                    this._queueCallback(()=>{
                        this._element.classList.remove(Mn),
                        this._queueCallback(()=>{
                            this._element.style.overflowY = i
                        }
                        , this._dialog)
                    }
                    , this._dialog),
                    this._element.focus())
                }
                _adjustDialog() {
                    const e = this._element.scrollHeight > document.documentElement.clientHeight
                      , t = this._scrollBar.getWidth()
                      , i = t > 0;
                    if (i && !e) {
                        const o = D() ? "paddingLeft" : "paddingRight";
                        this._element.style[o] = `${t}px`
                    }
                    if (!i && e) {
                        const o = D() ? "paddingRight" : "paddingLeft";
                        this._element.style[o] = `${t}px`
                    }
                }
                _resetAdjustments() {
                    this._element.style.paddingLeft = "",
                    this._element.style.paddingRight = ""
                }
                static jQueryInterface(e, t) {
                    return this.each(function() {
                        const i = He.getOrCreateInstance(this, e);
                        if (typeof e == "string") {
                            if (typeof i[e] > "u")
                                throw new TypeError(`No method named "${e}"`);
                            i[e](t)
                        }
                    })
                }
            }
            x.on(document, _a, ja, function(n) {
                const e = C.getElementFromSelector(this);
                ["A", "AREA"].includes(this.tagName) && n.preventDefault(),
                x.one(e, Ui, o=>{
                    o.defaultPrevented || x.one(e, Gi, ()=>{
                        _(this) && this.focus()
                    }
                    )
                }
                );
                const t = C.findOne(ba);
                t && He.getInstance(t).hide(),
                He.getOrCreateInstance(e).toggle(this)
            }),
            Dt(He),
            $(He);
            const Sa = "offcanvas"
              , we = ".bs.offcanvas"
              , Zi = ".data-api"
              , Ma = `load${we}${Zi}`
              , Ca = "Escape"
              , Ji = "show"
              , qi = "showing"
              , er = "hiding"
              , Na = "offcanvas-backdrop"
              , tr = ".offcanvas.show"
              , La = `show${we}`
              , Da = `shown${we}`
              , Ia = `hide${we}`
              , nr = `hidePrevented${we}`
              , ir = `hidden${we}`
              , $a = `resize${we}`
              , Pa = `click${we}${Zi}`
              , Ra = `keydown.dismiss${we}`
              , Va = '[data-bs-toggle="offcanvas"]'
              , Ha = {
                backdrop: !0,
                keyboard: !0,
                scroll: !1
            }
              , ka = {
                backdrop: "(boolean|string)",
                keyboard: "boolean",
                scroll: "boolean"
            };
            class je extends he {
                constructor(e, t) {
                    super(e, t),
                    this._isShown = !1,
                    this._backdrop = this._initializeBackDrop(),
                    this._focustrap = this._initializeFocusTrap(),
                    this._addEventListeners()
                }
                static get Default() {
                    return Ha
                }
                static get DefaultType() {
                    return ka
                }
                static get NAME() {
                    return Sa
                }
                toggle(e) {
                    return this._isShown ? this.hide() : this.show(e)
                }
                show(e) {
                    if (this._isShown || x.trigger(this._element, La, {
                        relatedTarget: e
                    }).defaultPrevented)
                        return;
                    this._isShown = !0,
                    this._backdrop.show(),
                    this._config.scroll || new Sn().hide(),
                    this._element.setAttribute("aria-modal", !0),
                    this._element.setAttribute("role", "dialog"),
                    this._element.classList.add(qi);
                    const i = ()=>{
                        (!this._config.scroll || this._config.backdrop) && this._focustrap.activate(),
                        this._element.classList.add(Ji),
                        this._element.classList.remove(qi),
                        x.trigger(this._element, Da, {
                            relatedTarget: e
                        })
                    }
                    ;
                    this._queueCallback(i, this._element, !0)
                }
                hide() {
                    if (!this._isShown || x.trigger(this._element, Ia).defaultPrevented)
                        return;
                    this._focustrap.deactivate(),
                    this._element.blur(),
                    this._isShown = !1,
                    this._element.classList.add(er),
                    this._backdrop.hide();
                    const t = ()=>{
                        this._element.classList.remove(Ji, er),
                        this._element.removeAttribute("aria-modal"),
                        this._element.removeAttribute("role"),
                        this._config.scroll || new Sn().reset(),
                        x.trigger(this._element, ir)
                    }
                    ;
                    this._queueCallback(t, this._element, !0)
                }
                dispose() {
                    this._backdrop.dispose(),
                    this._focustrap.deactivate(),
                    super.dispose()
                }
                _initializeBackDrop() {
                    const e = ()=>{
                        if (this._config.backdrop === "static") {
                            x.trigger(this._element, nr);
                            return
                        }
                        this.hide()
                    }
                      , t = !!this._config.backdrop;
                    return new zi({
                        className: Na,
                        isVisible: t,
                        isAnimated: !0,
                        rootElement: this._element.parentNode,
                        clickCallback: t ? e : null
                    })
                }
                _initializeFocusTrap() {
                    return new Ki({
                        trapElement: this._element
                    })
                }
                _addEventListeners() {
                    x.on(this._element, Ra, e=>{
                        if (e.key === Ca) {
                            if (this._config.keyboard) {
                                this.hide();
                                return
                            }
                            x.trigger(this._element, nr)
                        }
                    }
                    )
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = je.getOrCreateInstance(this, e);
                        if (typeof e == "string") {
                            if (t[e] === void 0 || e.startsWith("_") || e === "constructor")
                                throw new TypeError(`No method named "${e}"`);
                            t[e](this)
                        }
                    })
                }
            }
            x.on(document, Pa, Va, function(n) {
                const e = C.getElementFromSelector(this);
                if (["A", "AREA"].includes(this.tagName) && n.preventDefault(),
                v(this))
                    return;
                x.one(e, ir, ()=>{
                    _(this) && this.focus()
                }
                );
                const t = C.findOne(tr);
                t && t !== e && je.getInstance(t).hide(),
                je.getOrCreateInstance(e).toggle(this)
            }),
            x.on(window, Ma, ()=>{
                for (const n of C.find(tr))
                    je.getOrCreateInstance(n).show()
            }
            ),
            x.on(window, $a, ()=>{
                for (const n of C.find("[aria-modal][class*=show][class*=offcanvas-]"))
                    getComputedStyle(n).position !== "fixed" && je.getOrCreateInstance(n).hide()
            }
            ),
            Dt(je),
            $(je);
            const rr = {
                "*": ["class", "dir", "id", "lang", "role", /^aria-[\w-]*$/i],
                a: ["target", "href", "title", "rel"],
                area: [],
                b: [],
                br: [],
                col: [],
                code: [],
                div: [],
                em: [],
                hr: [],
                h1: [],
                h2: [],
                h3: [],
                h4: [],
                h5: [],
                h6: [],
                i: [],
                img: ["src", "srcset", "alt", "title", "width", "height"],
                li: [],
                ol: [],
                p: [],
                pre: [],
                s: [],
                small: [],
                span: [],
                sub: [],
                sup: [],
                strong: [],
                u: [],
                ul: []
            }
              , za = new Set(["background", "cite", "href", "itemtype", "longdesc", "poster", "src", "xlink:href"])
              , Fa = /^(?!javascript:)(?:[a-z0-9+.-]+:|[^&:/?#]*(?:[/?#]|$))/i
              , Ka = (n,e)=>{
                const t = n.nodeName.toLowerCase();
                return e.includes(t) ? za.has(t) ? !!Fa.test(n.nodeValue) : !0 : e.filter(i=>i instanceof RegExp).some(i=>i.test(t))
            }
            ;
            function Wa(n, e, t) {
                if (!n.length)
                    return n;
                if (t && typeof t == "function")
                    return t(n);
                const o = new window.DOMParser().parseFromString(n, "text/html")
                  , c = [].concat(...o.body.querySelectorAll("*"));
                for (const d of c) {
                    const y = d.nodeName.toLowerCase();
                    if (!Object.keys(e).includes(y)) {
                        d.remove();
                        continue
                    }
                    const b = [].concat(...d.attributes)
                      , M = [].concat(e["*"] || [], e[y] || []);
                    for (const w of b)
                        Ka(w, M) || d.removeAttribute(w.nodeName)
                }
                return o.body.innerHTML
            }
            const Ba = "TemplateFactory"
              , Ya = {
                allowList: rr,
                content: {},
                extraClass: "",
                html: !1,
                sanitize: !0,
                sanitizeFn: null,
                template: "<div></div>"
            }
              , Ga = {
                allowList: "object",
                content: "object",
                extraClass: "(string|function)",
                html: "boolean",
                sanitize: "boolean",
                sanitizeFn: "(null|function)",
                template: "string"
            }
              , Ua = {
                entry: "(string|element|function|null)",
                selector: "(string|element)"
            };
            class Xa extends gt {
                constructor(e) {
                    super(),
                    this._config = this._getConfig(e)
                }
                static get Default() {
                    return Ya
                }
                static get DefaultType() {
                    return Ga
                }
                static get NAME() {
                    return Ba
                }
                getContent() {
                    return Object.values(this._config.content).map(e=>this._resolvePossibleFunction(e)).filter(Boolean)
                }
                hasContent() {
                    return this.getContent().length > 0
                }
                changeContent(e) {
                    return this._checkContent(e),
                    this._config.content = {
                        ...this._config.content,
                        ...e
                    },
                    this
                }
                toHtml() {
                    const e = document.createElement("div");
                    e.innerHTML = this._maybeSanitize(this._config.template);
                    for (const [o,c] of Object.entries(this._config.content))
                        this._setContent(e, c, o);
                    const t = e.children[0]
                      , i = this._resolvePossibleFunction(this._config.extraClass);
                    return i && t.classList.add(...i.split(" ")),
                    t
                }
                _typeCheckConfig(e) {
                    super._typeCheckConfig(e),
                    this._checkContent(e.content)
                }
                _checkContent(e) {
                    for (const [t,i] of Object.entries(e))
                        super._typeCheckConfig({
                            selector: t,
                            entry: i
                        }, Ua)
                }
                _setContent(e, t, i) {
                    const o = C.findOne(i, e);
                    if (o) {
                        if (t = this._resolvePossibleFunction(t),
                        !t) {
                            o.remove();
                            return
                        }
                        if (E(t)) {
                            this._putElementInTemplate(A(t), o);
                            return
                        }
                        if (this._config.html) {
                            o.innerHTML = this._maybeSanitize(t);
                            return
                        }
                        o.textContent = t
                    }
                }
                _maybeSanitize(e) {
                    return this._config.sanitize ? Wa(e, this._config.allowList, this._config.sanitizeFn) : e
                }
                _resolvePossibleFunction(e) {
                    return H(e, [this])
                }
                _putElementInTemplate(e, t) {
                    if (this._config.html) {
                        t.innerHTML = "",
                        t.append(e);
                        return
                    }
                    t.textContent = e.textContent
                }
            }
            const Qa = "tooltip"
              , Za = new Set(["sanitize", "allowList", "sanitizeFn"])
              , Cn = "fade"
              , Ja = "modal"
              , Gt = "show"
              , qa = ".tooltip-inner"
              , or = `.${Ja}`
              , sr = "hide.bs.modal"
              , jt = "hover"
              , Nn = "focus"
              , el = "click"
              , tl = "manual"
              , nl = "hide"
              , il = "hidden"
              , rl = "show"
              , ol = "shown"
              , sl = "inserted"
              , al = "click"
              , ll = "focusin"
              , cl = "focusout"
              , dl = "mouseenter"
              , ul = "mouseleave"
              , pl = {
                AUTO: "auto",
                TOP: "top",
                RIGHT: D() ? "left" : "right",
                BOTTOM: "bottom",
                LEFT: D() ? "right" : "left"
            }
              , hl = {
                allowList: rr,
                animation: !0,
                boundary: "clippingParents",
                container: !1,
                customClass: "",
                delay: 0,
                fallbackPlacements: ["top", "right", "bottom", "left"],
                html: !1,
                offset: [0, 6],
                placement: "top",
                popperConfig: null,
                sanitize: !0,
                sanitizeFn: null,
                selector: !1,
                template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
                title: "",
                trigger: "hover focus"
            }
              , fl = {
                allowList: "object",
                animation: "boolean",
                boundary: "(string|element)",
                container: "(string|element|boolean)",
                customClass: "(string|function)",
                delay: "(number|object)",
                fallbackPlacements: "array",
                html: "boolean",
                offset: "(array|string|function)",
                placement: "(string|function)",
                popperConfig: "(null|object|function)",
                sanitize: "boolean",
                sanitizeFn: "(null|function)",
                selector: "(string|boolean)",
                template: "string",
                title: "(string|element|function)",
                trigger: "string"
            };
            class ke extends he {
                constructor(e, t) {
                    if (typeof Li > "u")
                        throw new TypeError("Bootstrap's tooltips require Popper (https://popper.js.org)");
                    super(e, t),
                    this._isEnabled = !0,
                    this._timeout = 0,
                    this._isHovered = null,
                    this._activeTrigger = {},
                    this._popper = null,
                    this._templateFactory = null,
                    this._newContent = null,
                    this.tip = null,
                    this._setListeners(),
                    this._config.selector || this._fixTitle()
                }
                static get Default() {
                    return hl
                }
                static get DefaultType() {
                    return fl
                }
                static get NAME() {
                    return Qa
                }
                enable() {
                    this._isEnabled = !0
                }
                disable() {
                    this._isEnabled = !1
                }
                toggleEnabled() {
                    this._isEnabled = !this._isEnabled
                }
                toggle() {
                    if (this._isEnabled) {
                        if (this._activeTrigger.click = !this._activeTrigger.click,
                        this._isShown()) {
                            this._leave();
                            return
                        }
                        this._enter()
                    }
                }
                dispose() {
                    clearTimeout(this._timeout),
                    x.off(this._element.closest(or), sr, this._hideModalHandler),
                    this._element.getAttribute("data-bs-original-title") && this._element.setAttribute("title", this._element.getAttribute("data-bs-original-title")),
                    this._disposePopper(),
                    super.dispose()
                }
                show() {
                    if (this._element.style.display === "none")
                        throw new Error("Please use show on visible elements");
                    if (!(this._isWithContent() && this._isEnabled))
                        return;
                    const e = x.trigger(this._element, this.constructor.eventName(rl))
                      , i = (O(this._element) || this._element.ownerDocument.documentElement).contains(this._element);
                    if (e.defaultPrevented || !i)
                        return;
                    this._disposePopper();
                    const o = this._getTipElement();
                    this._element.setAttribute("aria-describedby", o.getAttribute("id"));
                    const {container: c} = this._config;
                    if (this._element.ownerDocument.documentElement.contains(this.tip) || (c.append(o),
                    x.trigger(this._element, this.constructor.eventName(sl))),
                    this._popper = this._createPopper(o),
                    o.classList.add(Gt),
                    "ontouchstart"in document.documentElement)
                        for (const y of [].concat(...document.body.children))
                            x.on(y, "mouseover", T);
                    const d = ()=>{
                        x.trigger(this._element, this.constructor.eventName(ol)),
                        this._isHovered === !1 && this._leave(),
                        this._isHovered = !1
                    }
                    ;
                    this._queueCallback(d, this.tip, this._isAnimated())
                }
                hide() {
                    if (!this._isShown() || x.trigger(this._element, this.constructor.eventName(nl)).defaultPrevented)
                        return;
                    if (this._getTipElement().classList.remove(Gt),
                    "ontouchstart"in document.documentElement)
                        for (const o of [].concat(...document.body.children))
                            x.off(o, "mouseover", T);
                    this._activeTrigger[el] = !1,
                    this._activeTrigger[Nn] = !1,
                    this._activeTrigger[jt] = !1,
                    this._isHovered = null;
                    const i = ()=>{
                        this._isWithActiveTrigger() || (this._isHovered || this._disposePopper(),
                        this._element.removeAttribute("aria-describedby"),
                        x.trigger(this._element, this.constructor.eventName(il)))
                    }
                    ;
                    this._queueCallback(i, this.tip, this._isAnimated())
                }
                update() {
                    this._popper && this._popper.update()
                }
                _isWithContent() {
                    return !!this._getTitle()
                }
                _getTipElement() {
                    return this.tip || (this.tip = this._createTipElement(this._newContent || this._getContentForTemplate())),
                    this.tip
                }
                _createTipElement(e) {
                    const t = this._getTemplateFactory(e).toHtml();
                    if (!t)
                        return null;
                    t.classList.remove(Cn, Gt),
                    t.classList.add(`bs-${this.constructor.NAME}-auto`);
                    const i = h(this.constructor.NAME).toString();
                    return t.setAttribute("id", i),
                    this._isAnimated() && t.classList.add(Cn),
                    t
                }
                setContent(e) {
                    this._newContent = e,
                    this._isShown() && (this._disposePopper(),
                    this.show())
                }
                _getTemplateFactory(e) {
                    return this._templateFactory ? this._templateFactory.changeContent(e) : this._templateFactory = new Xa({
                        ...this._config,
                        content: e,
                        extraClass: this._resolvePossibleFunction(this._config.customClass)
                    }),
                    this._templateFactory
                }
                _getContentForTemplate() {
                    return {
                        [qa]: this._getTitle()
                    }
                }
                _getTitle() {
                    return this._resolvePossibleFunction(this._config.title) || this._element.getAttribute("data-bs-original-title")
                }
                _initializeOnDelegatedTarget(e) {
                    return this.constructor.getOrCreateInstance(e.delegateTarget, this._getDelegateConfig())
                }
                _isAnimated() {
                    return this._config.animation || this.tip && this.tip.classList.contains(Cn)
                }
                _isShown() {
                    return this.tip && this.tip.classList.contains(Gt)
                }
                _createPopper(e) {
                    const t = H(this._config.placement, [this, e, this._element])
                      , i = pl[t.toUpperCase()];
                    return Tn(this._element, e, this._getPopperConfig(i))
                }
                _getOffset() {
                    const {offset: e} = this._config;
                    return typeof e == "string" ? e.split(",").map(t=>Number.parseInt(t, 10)) : typeof e == "function" ? t=>e(t, this._element) : e
                }
                _resolvePossibleFunction(e) {
                    return H(e, [this._element])
                }
                _getPopperConfig(e) {
                    const t = {
                        placement: e,
                        modifiers: [{
                            name: "flip",
                            options: {
                                fallbackPlacements: this._config.fallbackPlacements
                            }
                        }, {
                            name: "offset",
                            options: {
                                offset: this._getOffset()
                            }
                        }, {
                            name: "preventOverflow",
                            options: {
                                boundary: this._config.boundary
                            }
                        }, {
                            name: "arrow",
                            options: {
                                element: `.${this.constructor.NAME}-arrow`
                            }
                        }, {
                            name: "preSetPlacement",
                            enabled: !0,
                            phase: "beforeMain",
                            fn: i=>{
                                this._getTipElement().setAttribute("data-popper-placement", i.state.placement)
                            }
                        }]
                    };
                    return {
                        ...t,
                        ...H(this._config.popperConfig, [t])
                    }
                }
                _setListeners() {
                    const e = this._config.trigger.split(" ");
                    for (const t of e)
                        if (t === "click")
                            x.on(this._element, this.constructor.eventName(al), this._config.selector, i=>{
                                this._initializeOnDelegatedTarget(i).toggle()
                            }
                            );
                        else if (t !== tl) {
                            const i = t === jt ? this.constructor.eventName(dl) : this.constructor.eventName(ll)
                              , o = t === jt ? this.constructor.eventName(ul) : this.constructor.eventName(cl);
                            x.on(this._element, i, this._config.selector, c=>{
                                const d = this._initializeOnDelegatedTarget(c);
                                d._activeTrigger[c.type === "focusin" ? Nn : jt] = !0,
                                d._enter()
                            }
                            ),
                            x.on(this._element, o, this._config.selector, c=>{
                                const d = this._initializeOnDelegatedTarget(c);
                                d._activeTrigger[c.type === "focusout" ? Nn : jt] = d._element.contains(c.relatedTarget),
                                d._leave()
                            }
                            )
                        }
                    this._hideModalHandler = ()=>{
                        this._element && this.hide()
                    }
                    ,
                    x.on(this._element.closest(or), sr, this._hideModalHandler)
                }
                _fixTitle() {
                    const e = this._element.getAttribute("title");
                    e && (!this._element.getAttribute("aria-label") && !this._element.textContent.trim() && this._element.setAttribute("aria-label", e),
                    this._element.setAttribute("data-bs-original-title", e),
                    this._element.removeAttribute("title"))
                }
                _enter() {
                    if (this._isShown() || this._isHovered) {
                        this._isHovered = !0;
                        return
                    }
                    this._isHovered = !0,
                    this._setTimeout(()=>{
                        this._isHovered && this.show()
                    }
                    , this._config.delay.show)
                }
                _leave() {
                    this._isWithActiveTrigger() || (this._isHovered = !1,
                    this._setTimeout(()=>{
                        this._isHovered || this.hide()
                    }
                    , this._config.delay.hide))
                }
                _setTimeout(e, t) {
                    clearTimeout(this._timeout),
                    this._timeout = setTimeout(e, t)
                }
                _isWithActiveTrigger() {
                    return Object.values(this._activeTrigger).includes(!0)
                }
                _getConfig(e) {
                    const t = be.getDataAttributes(this._element);
                    for (const i of Object.keys(t))
                        Za.has(i) && delete t[i];
                    return e = {
                        ...t,
                        ...typeof e == "object" && e ? e : {}
                    },
                    e = this._mergeConfigObj(e),
                    e = this._configAfterMerge(e),
                    this._typeCheckConfig(e),
                    e
                }
                _configAfterMerge(e) {
                    return e.container = e.container === !1 ? document.body : A(e.container),
                    typeof e.delay == "number" && (e.delay = {
                        show: e.delay,
                        hide: e.delay
                    }),
                    typeof e.title == "number" && (e.title = e.title.toString()),
                    typeof e.content == "number" && (e.content = e.content.toString()),
                    e
                }
                _getDelegateConfig() {
                    const e = {};
                    for (const [t,i] of Object.entries(this._config))
                        this.constructor.Default[t] !== i && (e[t] = i);
                    return e.selector = !1,
                    e.trigger = "manual",
                    e
                }
                _disposePopper() {
                    this._popper && (this._popper.destroy(),
                    this._popper = null),
                    this.tip && (this.tip.remove(),
                    this.tip = null)
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = ke.getOrCreateInstance(this, e);
                        if (typeof e == "string") {
                            if (typeof t[e] > "u")
                                throw new TypeError(`No method named "${e}"`);
                            t[e]()
                        }
                    })
                }
            }
            $(ke);
            const yl = "popover"
              , ml = ".popover-header"
              , gl = ".popover-body"
              , vl = {
                ...ke.Default,
                content: "",
                offset: [0, 8],
                placement: "right",
                template: '<div class="popover" role="tooltip"><div class="popover-arrow"></div><h3 class="popover-header"></h3><div class="popover-body"></div></div>',
                trigger: "click"
            }
              , xl = {
                ...ke.DefaultType,
                content: "(null|string|element|function)"
            };
            class Ut extends ke {
                static get Default() {
                    return vl
                }
                static get DefaultType() {
                    return xl
                }
                static get NAME() {
                    return yl
                }
                _isWithContent() {
                    return this._getTitle() || this._getContent()
                }
                _getContentForTemplate() {
                    return {
                        [ml]: this._getTitle(),
                        [gl]: this._getContent()
                    }
                }
                _getContent() {
                    return this._resolvePossibleFunction(this._config.content)
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = Ut.getOrCreateInstance(this, e);
                        if (typeof e == "string") {
                            if (typeof t[e] > "u")
                                throw new TypeError(`No method named "${e}"`);
                            t[e]()
                        }
                    })
                }
            }
            $(Ut);
            const _l = "scrollspy"
              , Ln = ".bs.scrollspy"
              , El = ".data-api"
              , bl = `activate${Ln}`
              , ar = `click${Ln}`
              , Al = `load${Ln}${El}`
              , wl = "dropdown-item"
              , pt = "active"
              , jl = '[data-bs-spy="scroll"]'
              , Dn = "[href]"
              , Tl = ".nav, .list-group"
              , lr = ".nav-link"
              , Ol = `${lr}, .nav-item > ${lr}, .list-group-item`
              , Sl = ".dropdown"
              , Ml = ".dropdown-toggle"
              , Cl = {
                offset: null,
                rootMargin: "0px 0px -25%",
                smoothScroll: !1,
                target: null,
                threshold: [.1, .5, 1]
            }
              , Nl = {
                offset: "(number|null)",
                rootMargin: "string",
                smoothScroll: "boolean",
                target: "element",
                threshold: "array"
            };
            class Tt extends he {
                constructor(e, t) {
                    super(e, t),
                    this._targetLinks = new Map,
                    this._observableSections = new Map,
                    this._rootElement = getComputedStyle(this._element).overflowY === "visible" ? null : this._element,
                    this._activeTarget = null,
                    this._observer = null,
                    this._previousScrollData = {
                        visibleEntryTop: 0,
                        parentScrollTop: 0
                    },
                    this.refresh()
                }
                static get Default() {
                    return Cl
                }
                static get DefaultType() {
                    return Nl
                }
                static get NAME() {
                    return _l
                }
                refresh() {
                    this._initializeTargetsAndObservables(),
                    this._maybeEnableSmoothScroll(),
                    this._observer ? this._observer.disconnect() : this._observer = this._getNewObserver();
                    for (const e of this._observableSections.values())
                        this._observer.observe(e)
                }
                dispose() {
                    this._observer.disconnect(),
                    super.dispose()
                }
                _configAfterMerge(e) {
                    return e.target = A(e.target) || document.body,
                    e.rootMargin = e.offset ? `${e.offset}px 0px -30%` : e.rootMargin,
                    typeof e.threshold == "string" && (e.threshold = e.threshold.split(",").map(t=>Number.parseFloat(t))),
                    e
                }
                _maybeEnableSmoothScroll() {
                    this._config.smoothScroll && (x.off(this._config.target, ar),
                    x.on(this._config.target, ar, Dn, e=>{
                        const t = this._observableSections.get(e.target.hash);
                        if (t) {
                            e.preventDefault();
                            const i = this._rootElement || window
                              , o = t.offsetTop - this._element.offsetTop;
                            if (i.scrollTo) {
                                i.scrollTo({
                                    top: o,
                                    behavior: "smooth"
                                });
                                return
                            }
                            i.scrollTop = o
                        }
                    }
                    ))
                }
                _getNewObserver() {
                    const e = {
                        root: this._rootElement,
                        threshold: this._config.threshold,
                        rootMargin: this._config.rootMargin
                    };
                    return new IntersectionObserver(t=>this._observerCallback(t),e)
                }
                _observerCallback(e) {
                    const t = d=>this._targetLinks.get(`#${d.target.id}`)
                      , i = d=>{
                        this._previousScrollData.visibleEntryTop = d.target.offsetTop,
                        this._process(t(d))
                    }
                      , o = (this._rootElement || document.documentElement).scrollTop
                      , c = o >= this._previousScrollData.parentScrollTop;
                    this._previousScrollData.parentScrollTop = o;
                    for (const d of e) {
                        if (!d.isIntersecting) {
                            this._activeTarget = null,
                            this._clearActiveClass(t(d));
                            continue
                        }
                        const y = d.target.offsetTop >= this._previousScrollData.visibleEntryTop;
                        if (c && y) {
                            if (i(d),
                            !o)
                                return;
                            continue
                        }
                        !c && !y && i(d)
                    }
                }
                _initializeTargetsAndObservables() {
                    this._targetLinks = new Map,
                    this._observableSections = new Map;
                    const e = C.find(Dn, this._config.target);
                    for (const t of e) {
                        if (!t.hash || v(t))
                            continue;
                        const i = C.findOne(decodeURI(t.hash), this._element);
                        _(i) && (this._targetLinks.set(decodeURI(t.hash), t),
                        this._observableSections.set(t.hash, i))
                    }
                }
                _process(e) {
                    this._activeTarget !== e && (this._clearActiveClass(this._config.target),
                    this._activeTarget = e,
                    e.classList.add(pt),
                    this._activateParents(e),
                    x.trigger(this._element, bl, {
                        relatedTarget: e
                    }))
                }
                _activateParents(e) {
                    if (e.classList.contains(wl)) {
                        C.findOne(Ml, e.closest(Sl)).classList.add(pt);
                        return
                    }
                    for (const t of C.parents(e, Tl))
                        for (const i of C.prev(t, Ol))
                            i.classList.add(pt)
                }
                _clearActiveClass(e) {
                    e.classList.remove(pt);
                    const t = C.find(`${Dn}.${pt}`, e);
                    for (const i of t)
                        i.classList.remove(pt)
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = Tt.getOrCreateInstance(this, e);
                        if (typeof e == "string") {
                            if (t[e] === void 0 || e.startsWith("_") || e === "constructor")
                                throw new TypeError(`No method named "${e}"`);
                            t[e]()
                        }
                    })
                }
            }
            x.on(window, Al, ()=>{
                for (const n of C.find(jl))
                    Tt.getOrCreateInstance(n)
            }
            ),
            $(Tt);
            const Ll = "tab"
              , ze = ".bs.tab"
              , Dl = `hide${ze}`
              , Il = `hidden${ze}`
              , $l = `show${ze}`
              , Pl = `shown${ze}`
              , Rl = `click${ze}`
              , Vl = `keydown${ze}`
              , Hl = `load${ze}`
              , kl = "ArrowLeft"
              , cr = "ArrowRight"
              , zl = "ArrowUp"
              , dr = "ArrowDown"
              , In = "Home"
              , ur = "End"
              , Fe = "active"
              , pr = "fade"
              , $n = "show"
              , Fl = "dropdown"
              , hr = ".dropdown-toggle"
              , Kl = ".dropdown-menu"
              , Pn = `:not(${hr})`
              , Wl = '.list-group, .nav, [role="tablist"]'
              , Bl = ".nav-item, .list-group-item"
              , Yl = `.nav-link${Pn}, .list-group-item${Pn}, [role="tab"]${Pn}`
              , fr = '[data-bs-toggle="tab"], [data-bs-toggle="pill"], [data-bs-toggle="list"]'
              , Rn = `${Yl}, ${fr}`
              , Gl = `.${Fe}[data-bs-toggle="tab"], .${Fe}[data-bs-toggle="pill"], .${Fe}[data-bs-toggle="list"]`;
            class Ke extends he {
                constructor(e) {
                    super(e),
                    this._parent = this._element.closest(Wl),
                    this._parent && (this._setInitialAttributes(this._parent, this._getChildren()),
                    x.on(this._element, Vl, t=>this._keydown(t)))
                }
                static get NAME() {
                    return Ll
                }
                show() {
                    const e = this._element;
                    if (this._elemIsActive(e))
                        return;
                    const t = this._getActiveElem()
                      , i = t ? x.trigger(t, Dl, {
                        relatedTarget: e
                    }) : null;
                    x.trigger(e, $l, {
                        relatedTarget: t
                    }).defaultPrevented || i && i.defaultPrevented || (this._deactivate(t, e),
                    this._activate(e, t))
                }
                _activate(e, t) {
                    if (!e)
                        return;
                    e.classList.add(Fe),
                    this._activate(C.getElementFromSelector(e));
                    const i = ()=>{
                        if (e.getAttribute("role") !== "tab") {
                            e.classList.add($n);
                            return
                        }
                        e.removeAttribute("tabindex"),
                        e.setAttribute("aria-selected", !0),
                        this._toggleDropDown(e, !0),
                        x.trigger(e, Pl, {
                            relatedTarget: t
                        })
                    }
                    ;
                    this._queueCallback(i, e, e.classList.contains(pr))
                }
                _deactivate(e, t) {
                    if (!e)
                        return;
                    e.classList.remove(Fe),
                    e.blur(),
                    this._deactivate(C.getElementFromSelector(e));
                    const i = ()=>{
                        if (e.getAttribute("role") !== "tab") {
                            e.classList.remove($n);
                            return
                        }
                        e.setAttribute("aria-selected", !1),
                        e.setAttribute("tabindex", "-1"),
                        this._toggleDropDown(e, !1),
                        x.trigger(e, Il, {
                            relatedTarget: t
                        })
                    }
                    ;
                    this._queueCallback(i, e, e.classList.contains(pr))
                }
                _keydown(e) {
                    if (![kl, cr, zl, dr, In, ur].includes(e.key))
                        return;
                    e.stopPropagation(),
                    e.preventDefault();
                    const t = this._getChildren().filter(o=>!v(o));
                    let i;
                    if ([In, ur].includes(e.key))
                        i = t[e.key === In ? 0 : t.length - 1];
                    else {
                        const o = [cr, dr].includes(e.key);
                        i = yt(t, e.target, o, !0)
                    }
                    i && (i.focus({
                        preventScroll: !0
                    }),
                    Ke.getOrCreateInstance(i).show())
                }
                _getChildren() {
                    return C.find(Rn, this._parent)
                }
                _getActiveElem() {
                    return this._getChildren().find(e=>this._elemIsActive(e)) || null
                }
                _setInitialAttributes(e, t) {
                    this._setAttributeIfNotExists(e, "role", "tablist");
                    for (const i of t)
                        this._setInitialAttributesOnChild(i)
                }
                _setInitialAttributesOnChild(e) {
                    e = this._getInnerElement(e);
                    const t = this._elemIsActive(e)
                      , i = this._getOuterElement(e);
                    e.setAttribute("aria-selected", t),
                    i !== e && this._setAttributeIfNotExists(i, "role", "presentation"),
                    t || e.setAttribute("tabindex", "-1"),
                    this._setAttributeIfNotExists(e, "role", "tab"),
                    this._setInitialAttributesOnTargetPanel(e)
                }
                _setInitialAttributesOnTargetPanel(e) {
                    const t = C.getElementFromSelector(e);
                    t && (this._setAttributeIfNotExists(t, "role", "tabpanel"),
                    e.id && this._setAttributeIfNotExists(t, "aria-labelledby", `${e.id}`))
                }
                _toggleDropDown(e, t) {
                    const i = this._getOuterElement(e);
                    if (!i.classList.contains(Fl))
                        return;
                    const o = (c,d)=>{
                        const y = C.findOne(c, i);
                        y && y.classList.toggle(d, t)
                    }
                    ;
                    o(hr, Fe),
                    o(Kl, $n),
                    i.setAttribute("aria-expanded", t)
                }
                _setAttributeIfNotExists(e, t, i) {
                    e.hasAttribute(t) || e.setAttribute(t, i)
                }
                _elemIsActive(e) {
                    return e.classList.contains(Fe)
                }
                _getInnerElement(e) {
                    return e.matches(Rn) ? e : C.findOne(Rn, e)
                }
                _getOuterElement(e) {
                    return e.closest(Bl) || e
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = Ke.getOrCreateInstance(this);
                        if (typeof e == "string") {
                            if (t[e] === void 0 || e.startsWith("_") || e === "constructor")
                                throw new TypeError(`No method named "${e}"`);
                            t[e]()
                        }
                    })
                }
            }
            x.on(document, Rl, fr, function(n) {
                ["A", "AREA"].includes(this.tagName) && n.preventDefault(),
                !v(this) && Ke.getOrCreateInstance(this).show()
            }),
            x.on(window, Hl, ()=>{
                for (const n of C.find(Gl))
                    Ke.getOrCreateInstance(n)
            }
            ),
            $(Ke);
            const Ul = "toast"
              , Ne = ".bs.toast"
              , Xl = `mouseover${Ne}`
              , Ql = `mouseout${Ne}`
              , Zl = `focusin${Ne}`
              , Jl = `focusout${Ne}`
              , ql = `hide${Ne}`
              , ec = `hidden${Ne}`
              , tc = `show${Ne}`
              , nc = `shown${Ne}`
              , ic = "fade"
              , yr = "hide"
              , Xt = "show"
              , Qt = "showing"
              , rc = {
                animation: "boolean",
                autohide: "boolean",
                delay: "number"
            }
              , oc = {
                animation: !0,
                autohide: !0,
                delay: 5e3
            };
            class Ot extends he {
                constructor(e, t) {
                    super(e, t),
                    this._timeout = null,
                    this._hasMouseInteraction = !1,
                    this._hasKeyboardInteraction = !1,
                    this._setListeners()
                }
                static get Default() {
                    return oc
                }
                static get DefaultType() {
                    return rc
                }
                static get NAME() {
                    return Ul
                }
                show() {
                    if (x.trigger(this._element, tc).defaultPrevented)
                        return;
                    this._clearTimeout(),
                    this._config.animation && this._element.classList.add(ic);
                    const t = ()=>{
                        this._element.classList.remove(Qt),
                        x.trigger(this._element, nc),
                        this._maybeScheduleHide()
                    }
                    ;
                    this._element.classList.remove(yr),
                    j(this._element),
                    this._element.classList.add(Xt, Qt),
                    this._queueCallback(t, this._element, this._config.animation)
                }
                hide() {
                    if (!this.isShown() || x.trigger(this._element, ql).defaultPrevented)
                        return;
                    const t = ()=>{
                        this._element.classList.add(yr),
                        this._element.classList.remove(Qt, Xt),
                        x.trigger(this._element, ec)
                    }
                    ;
                    this._element.classList.add(Qt),
                    this._queueCallback(t, this._element, this._config.animation)
                }
                dispose() {
                    this._clearTimeout(),
                    this.isShown() && this._element.classList.remove(Xt),
                    super.dispose()
                }
                isShown() {
                    return this._element.classList.contains(Xt)
                }
                _maybeScheduleHide() {
                    this._config.autohide && (this._hasMouseInteraction || this._hasKeyboardInteraction || (this._timeout = setTimeout(()=>{
                        this.hide()
                    }
                    , this._config.delay)))
                }
                _onInteraction(e, t) {
                    switch (e.type) {
                    case "mouseover":
                    case "mouseout":
                        {
                            this._hasMouseInteraction = t;
                            break
                        }
                    case "focusin":
                    case "focusout":
                        {
                            this._hasKeyboardInteraction = t;
                            break
                        }
                    }
                    if (t) {
                        this._clearTimeout();
                        return
                    }
                    const i = e.relatedTarget;
                    this._element === i || this._element.contains(i) || this._maybeScheduleHide()
                }
                _setListeners() {
                    x.on(this._element, Xl, e=>this._onInteraction(e, !0)),
                    x.on(this._element, Ql, e=>this._onInteraction(e, !1)),
                    x.on(this._element, Zl, e=>this._onInteraction(e, !0)),
                    x.on(this._element, Jl, e=>this._onInteraction(e, !1))
                }
                _clearTimeout() {
                    clearTimeout(this._timeout),
                    this._timeout = null
                }
                static jQueryInterface(e) {
                    return this.each(function() {
                        const t = Ot.getOrCreateInstance(this, e);
                        if (typeof e == "string") {
                            if (typeof t[e] > "u")
                                throw new TypeError(`No method named "${e}"`);
                            t[e](this)
                        }
                    })
                }
            }
            return Dt(Ot),
            $(Ot),
            {
                Alert: vt,
                Button: xt,
                Carousel: tt,
                Collapse: it,
                Dropdown: fe,
                Modal: He,
                Offcanvas: je,
                Popover: Ut,
                ScrollSpy: Tt,
                Tab: Ke,
                Toast: Ot,
                Tooltip: ke
            }
        })
    }
    )(Wn);
    
    var Or = Wn.exports;
    const Sr = Fn(Or)
      , Ct = U=>U.innerWidth > 1200
      , nn = (U,k=!1)=>{
        const s = U.children;
        let f = 0;
        for (let r = 0; r < U.childElementCount; r++) {
            const l = s[r];
            if (f += l.querySelector(".submenu-link").clientHeight,
            k && l.classList.contains("has-sub")) {
                const a = l.querySelector(".submenu");
                if (a.classList.contains("submenu-open")) {
                    const u = ~~[...a.querySelectorAll(".submenu-link")].reduce((p,h)=>p + h.clientHeight, 0);
                    f += u
                }
            }
        }
        return U.style.setProperty("--submenu-height", f + "px"),
        f
    }
    ;
    class Mr {
        constructor(k, s={}) {
            this.sidebarEL = k instanceof HTMLElement ? k : document.querySelector(k),
            this.options = s,
            this.init()
        }
        init() {
			
            document.querySelectorAll(".burger-btn").forEach(r=>r.addEventListener("click", this.toggle.bind(this))),
            document.querySelectorAll(".sidebar-hide").forEach(r=>r.addEventListener("click", this.toggle.bind(this))),
            window.addEventListener("resize", this.onResize.bind(this));
            const k = r=>{
                r.classList.contains("submenu-open") ? (r.classList.remove("submenu-open"),
                r.classList.add("submenu-closed")) : (r.classList.remove("submenu-closed"),
                r.classList.add("submenu-open"))
            }
            ;
            let s = document.querySelectorAll(".sidebar-item.has-sub");
            for (var f = 0; f < s.length; f++) {
                let r = s[f];
                s[f].querySelector(".sidebar-link").addEventListener("click", a=>{
                    a.preventDefault();
                    let u = r.querySelector(".submenu");
                    k(u)
                }
                ),
                r.querySelectorAll(".submenu-item.has-sub").forEach(a=>{
                    a.addEventListener("click", ()=>{
                        const u = a.querySelector(".submenu");
                        k(u),
                        nn(a.parentElement, !0)
                    }
                    )
                }
                )
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
            }
            , 300),
            this.options.recalculateHeight && Cr(Xe)
        }
        onResize() {
            Ct(window) ? (this.sidebarEL.classList.add("inactive"),
            this.sidebarEL.classList.remove("active")) : this.sidebarEL.classList.remove("inactive"),
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
			
            if (Ct(window))
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
            if (Ct(window))
                return;
            const s = this.sidebarEL.classList.contains("active")
              , f = document.querySelector("body");
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
        Ct(window) && (U.classList.add("inactive"),
        U.classList.add("sidebar-desktop"));
        let k = document.querySelectorAll(".sidebar-item.has-sub .submenu");
        for (var s = 0; s < k.length; s++) {
            let f = k[s];
            const r = f.parentElement;
            f.clientHeight,
            r.classList.contains("inactive") ? f.classList.add("submenu-open") : f.classList.add("submenu-closed"),
            setTimeout(()=>{
                nn(f, !0)
            }
            , 50)
        }
    }
      , Cr = U=>{
        if (!U)
            return;
        let k = document.querySelectorAll(".sidebar-item.has-sub .submenu");
        for (var s = 0; s < k.length; s++) {
            let f = k[s];
            const r = f.parentElement;
            f.clientHeight,
            r.classList.contains("inactive") ? f.classList.add("submenu-open") : f.classList.add("submenu-closed"),
            setTimeout(()=>{
                nn(f, !0)
            }
            , 50)
        }
    }
    ;
    document.readyState !== "loading" ? Bn(Xe) : window.addEventListener("DOMContentLoaded", ()=>Bn(Xe)),
    window.Sidebar = Mr,
    Xe && new window.Sidebar(Xe),
    window.bootstrap = Sr,
    Tr.replace()
});