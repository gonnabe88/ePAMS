$(document).ready(() => {
    // id : delete 버튼 클릭 시 del() 함수 호출
    $('#delete').on('click', function() {
        del(this);
    });

    // PDF 링크 클릭 시 모달을 열고 PDF.js 뷰어를 iframe으로 로드
    document.querySelectorAll('.open-pdf').forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault(); // 기본 동작인 다운로드를 막음
            const pdfUrl = this.dataset.pdfurl; // 클릭한 링크의 data-pdfUrl 속성 가져옴
            $('#pdf-frame').attr('src', `/extensions/pdfjs/web/viewer.html?file=${pdfUrl}`); // PDF.js viewer에 파일 경로 전달
            $('#pdfModal').modal('show'); // 모달 열기
        });
    });
});
