tinymce.init({
    selector: '#open',
    plugins: [
        "advlist", "accordion", "anchor", "autolink", "autoresize", "charmap", "code", "fullscreen",
        "help", "image", "insertdatetime", "link", "lists", "media",
        "nonbreaking", "pagebreak", "preview", "quickbars", "save", "searchreplace", "table", "visualblocks", "wordcount"
    ],
    branding: false,
    promotion: false,
    images_upload_url: 'image',
    images_upload_base_path: '/board/upload/',
    automatic_uploads: true,
    images_reuse_filename: true,
    relative_urls: false,
    remove_script_host: false,
    document_base_url: window.location.origin,
    images_upload_handler : (blobInfo, progress) => new Promise((resolve, reject) => {
        var xhr, formData;
        // CSRF 토큰 설정
        var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        xhr = new XMLHttpRequest();
        xhr.withCredentials = false;
        xhr.open('POST', '/board/upload/image'); // 서버 업로드 엔드포인트
        xhr.setRequestHeader(csrfHeader, csrfToken);

        xhr.upload.onprogress = (e) => {
            progress(e.loaded / e.total * 100);
        };

        xhr.onload = function() {
            var json;
            try {
                if (xhr.status != 200) {
                    console.error('HTTP Error: ' + xhr.status);
                    failure('HTTP Error: ' + xhr.status);
                    return;
                }
                json = JSON.parse(xhr.responseText);
                if (!json || typeof json.location != 'string') {
                    console.error('Invalid JSON: ' + xhr.responseText);
                    failure('Invalid JSON: ' + xhr.responseText);
                    return;
                }
                console.log('Image uploaded successfully:', json.location); // 업로드된 이미지 URL을 로그로 출력
                resolve(json.location);
            } catch (e) {
                console.error('Error parsing JSON: ', e);
                failure('Error parsing JSON: ' + e.message);
            }
        };

        xhr.onerror = () => {
            console.error('Error during the request.');
            reject('Image upload failed due to a XHR Transport error. Code: ' + xhr.status);
        };

        formData = new FormData();
        console.log('blobInfo.filename():', blobInfo.filename()); // 업로드된 이미지 URL을 로그로 출력
        formData.append('file', blobInfo.blob(), blobInfo.filename());
        xhr.send(formData);
    }),
    setup: function(editor) {
        editor.on('init', function() {
            console.log("init");
            editor.getBody().classList.add('tinymce-editor');
        });

        editor.on('NodeChange', function(e) {
            console.log("NodeChange");
            if (e && e.element.nodeName.toLowerCase() == 'img') {
                e.element.classList.add('tinymce-image');
                e.element.style.maxWidth = '100%';
            }
        });
    }
});