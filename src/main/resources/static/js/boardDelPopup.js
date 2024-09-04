const del = (element) => {
    const boardId = element.getAttribute('data-boardId');

    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');

    Swal.fire({
        title: "삭제",
        html: "정말 삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "삭제",
        confirmButtonColor: "#3085d6",
        cancelButtonText: "취소",
        cancelButtonColor: "#d33",
        showLoaderOnConfirm: true,
        preConfirm: async () => {},
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "get",
                url: "/board/delete/"+boardId,
                dataType: "json",
                data: {},
                //CSRF Token
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                complete: function(data) {
                    Swal.fire({
                        title: "삭제 완료",
                        text: "게시글이 삭제되었습니다.",
                        icon: "success"
                    }).then((result) => {
                        window.location.href = '/board/list'
                    })
                }
            });
        }
    })
}