// 경고 문구를 보여주는 함수
function disablePastDates() {
    const dateWarning = document.getElementById('dateWarning');

    // 경고 문구 표시 및 투명도 초기화

    dateWarning.style.display = 'flex'; // flex로 설정해서 가운데 정렬
    dateWarning.style.opacity = '1'; // 처음에는 완전히 보이게

    setTimeout(() => {
        let opacity=1;
        const fadeOut = setInterval(()=>{
            if(opacity <=0){
                clearInterval(fadeOut);//투명도 0이면 반복종료
                dateWarning.style.display='none';
            }else{
                opacity-=0.05; //투명도 점차 줄이기
                dateWarning.style.opacity=opacity;
            }
        },50);
    }, 2000);//1초 동안 보여준후 사라지기 시작
}