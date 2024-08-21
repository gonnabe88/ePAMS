$(document).ready(function() {

    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');
    const boardId = $('#update').attr('data-seqId');

    // 기존 첨부파일 목록 로드
    reloadFileList(boardId);

    // id : update 버튼 클릭 시 update 함수 호출 리스너 등록
    $('#update').on('click', function() {
        update(boardId);
    });

    window.del = function(boardId, fileId, url, fileName) {
        console.log("delete id : " + fileId);
        console.log("delete url : " + url);
        console.log("delete file : " + fileName);

        Swal.fire({
            title: "삭제하시겠습니까?",
            html: `<a href="${url}">${fileName}</a>`,
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "삭제",
            confirmButtonColor: "#d33",
            cancelButtonText: "취소",
            cancelButtonColor: "#3085d6",
            showLoaderOnConfirm: true,
            preConfirm: async () => {},
            allowOutsideClick: () => !Swal.isLoading()
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: "DELETE",
                    url: `/board/${boardId}/deleteFile/${fileId}`,
                    processData: false,
                    contentType: false,
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    complete: function(data) {
                        Swal.fire({
                            title: "삭제 완료",
                            text: data.responseText,
                            icon: "success"
                        }).then((result) => {
                            reloadFileList(boardId);
                        })
                    },
                    error: function(xhr, status, error) {
                        console.error('AJAX error:', status, error);
                    }
                });
            }
        });
    }

    function reloadFileList(boardId) {
        $.ajax({
            url: `/board/${boardId}/fileList`,
            type: 'GET',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                const fileListElement = $('#file-list');
                fileListElement.empty();

                data.forEach(function(file) {
                    const listItem = $(`
                                <li>
                                    <div class="d-flex align-items-center p-2 truncate-multiline">
                                        <div class="col-9 d-flex align-items-center gap-2">
                                            <span class="badge text-bg-primary" > 첨부 </span>
                                            <h6 class="mb-0">
                                                <a></a>
                                            </h6>
                                        </div>
                                        <div class="col-3 d-flex justify-content-end">
                                            <div class="shrink-0 ms-3">
                                                <button class="btn btn-danger btn-sm" type="button">삭제</button>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            `);
                    // 안전하게 파일 이름 삽입
                    listItem.find('a')
                        .attr('href', `/board/${boardId}/download/${file.seqId}`)
                        .text(file.originalFileName);

                    // 삭제 버튼에 안전하게 데이터 속성 설정
                    const deleteButton = listItem.find('button');
                    deleteButton.data('boardId', boardId)
                        .data('fileId', file.seqId)
                        .data('url', `/board/${boardId}/download/${file.seqId}`)
                        .data('fileName', file.originalFileName);

                    // 삭제 버튼 클릭 이벤트 핸들러 설정
                    deleteButton.on('click', function() {
                        const boardId = $(this).data('boardId');
                        const fileId = $(this).data('fileId');
                        const url = $(this).data('url');
                        const fileName = $(this).data('fileName');
                        del(boardId, fileId, url, fileName);
                    });
                    fileListElement.append(listItem);
                });
            },
            error: function(xhr, status, error) {
                console.error('AJAX error:', status, error);
            }
        });
    }

});

const update = (boardId) => {
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    let seqId = boardId;
    let formData = new FormData();
    let files = myDropzone.getQueuedFiles();
    console.log(files.length);
    for (const file of files) {
        formData.append('boardFile', file);
        console.log(file);
    }

    formData.append("seqId", seqId)
    formData.append("boardTitle", document.getElementById("title").value)
    formData.append("boardContents", tinymce.get("open").getContent())
    formData.append("category", document.querySelector('input[name="flexRadioDefault"]:checked').value)

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
                url: "/board/update",
                processData: false,
                contentType: false,
                data: formData,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                complete: function(data) {
                    Swal.fire({
                        title: "등록 완료",
                        text: "게시글이 등록되었습니다.",
                        icon: "success"
                    }).then((result) => {
                        window.location.href = '/board/' + seqId;
                    })
                }
            });
        }
    })
}