
// 간편인증 로그인 시 로그인 버튼 스피너 설정
const showSpinnerButton = () => {
    console.log("showSpinnerButton");
    const loginBtn = document.getElementById('login');
    loginBtn.innerHTML = `
            <span class="spinner-border spinner-border-sm" aria-hidden="true"></span>
            <span role="status">Loading...</span>
        `;
    loginBtn.setAttribute('disabled', 'true');
}

// 스피너 제거 및 버튼 복구 함수
const hideSpinnerButton = () => {
    console.log("hideSpinnerButton");
    const loginBtn = document.getElementById('login');
    loginBtn.innerHTML = '로그인'; // 원래 버튼 텍스트로 복구
    loginBtn.removeAttribute('disabled'); // 버튼 활성화
};