
// 비동기로 대기하는 함수
const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

// 진행률 업데이트 함수 (1%당 0.1초 대기)
const updateProgressBar = async (startPercentage, targetPercentage) => {
    const progressBar = document.getElementById('progressBar');
    const progressText = progressBar.querySelector('span');
    let currentPercentage = startPercentage;

    while (currentPercentage < targetPercentage) {
        currentPercentage++;
        progressBar.style.width = currentPercentage + '%';
        progressText.innerHTML = currentPercentage + '%'; // 텍스트 중앙에 표시
        await delay(50);  // 0.05초 대기
    }
};

// 진행률 관리 함수 (충돌 방지)
const controlProgressBar = async (startPercentage, targetPercentage) => {
    const lock = controlProgressBar.lock || Promise.resolve(); // 이전 작업이 끝날 때까지 기다림
    controlProgressBar.lock = lock.then(() => updateProgressBar(startPercentage, targetPercentage)); // 새로운 작업 추가
    await controlProgressBar.lock; // 잠금 풀릴 때까지 대기
};
