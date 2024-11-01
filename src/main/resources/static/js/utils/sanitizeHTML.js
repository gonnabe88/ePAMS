const sanitizeHTML = (html) => { 
    // 2024-08-17 CWE-79(Cross-site Scripting (XSS)) 취약점 조치
    return DOMPurify.sanitize(html, {
        SAFE_FOR_TEMPLATES: true,
        ADD_ATTR: ['data-bs-toggle', 'data-bs-target', 'aria-controls', 'aria-expanded', 'id', 'style'],
        ALLOWED_TAGS: ['img', 'br', 'button', 'small', 'p', 'h7', 'h6', 'h5', 'h4', 'h3', 'h2', 'h1', 'div', 'span', 'section', 'i', 'ul', 'li', 'a', 'strong'], // 필요시 추가
        //FORBID_ATTR: ['style'],
        FORBID_TAGS: ['script']
    });
}