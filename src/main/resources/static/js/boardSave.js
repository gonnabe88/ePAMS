const save = (boardType) => {
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    let seqId = /*[[${board.seqId}]]*/ "seqId";
    let formData = new FormData();
    let files = myDropzone.getQueuedFiles();
    console.log(files.length);
    for (const file of files) {
        formData.append('boardFile', file);
        console.log(file);
    }

    formData.append("boardTitle", document.getElementById("title").value);
    formData.append("boardContents", tinymce.get("open").getContent());
    formData.append("category", document.querySelector('input[name="flexRadioDefault"]:checked').value);
    formData.append("boardType", boardType); // 공지사항(01) FAQ(04)

    Swal.fire({
        title: "등록",
        html: "등록 하시겠습니까?",
        icon: "info",
        showCancelButton: true,
        confirmButtonText: "등록",
        confirmButtonColor: "#3085d6",
        cancelButtonText: "취소",
        cancelButtonColor: "#d33",
        showLoaderOnConfirm: true,
        preConfirm: async () => {},
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "post",
                url: "/board/save",
                processData: false,
                contentType: false,
                data: formData,
                // CSRF Token
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function(data) {
                    Swal.fire({
                        title: "등록 완료",
                        text: "게시글이 등록되었습니다.",
                        icon: "success"
                    }).then(() => {
                        // URL 검증 로직 추가 (2024-06-22 CWE-601 Open Redirect)
                        const allowedPattern = /^\/board\/\d+$/; // "/board/숫자" 형식의 URL을 허용하는 정규 표현식
                        const redirectUrl = data;

                        try {
                            const url = new URL(redirectUrl, window.location.origin);
                            if (allowedPattern.test(url.pathname)) {
                                // Sanitize the URL by encoding potential unsafe characters
                                const sanitizedPath = encodeURI(url.pathname);
                                window.location.href = sanitizedPath;
                            } else {
                                console.error('Redirect URL is not allowed:', redirectUrl);
                                Swal.fire({
                                    title: "오류",
                                    text: "잘못된 리디렉션 URL입니다.",
                                    icon: "error"
                                });
                            }
                        } catch (e) {
                            console.error('Invalid URL:', redirectUrl);
                            Swal.fire({
                                title: "오류",
                                text: "잘못된 URL입니다.",
                                icon: "error"
                            });
                        }
                    });
                },

                error: function(xhr, status, error) {
                    Swal.fire({
                        title: "등록 실패",
                        text: "게시글 등록에 실패했습니다.",
                        icon: "error"
                    });
                }
            });
        }
    });
}