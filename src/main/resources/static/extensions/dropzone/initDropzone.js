
// Disable AutoDiscover
Dropzone.autoDiscover = false;


let dropzonePreviewNode = document.querySelector('#dropzone-preview-list'); // <li class="mt-2" id="dropzone-preview-list"> 부분을 가져와서
dropzonePreviewNode.id = ''; // 중복 회피를 위해 id 없애고
let previewTemplate = dropzonePreviewNode.parentNode.innerHTML; // 엘리먼트 전체를 저장 (템플릿)
dropzonePreviewNode.parentNode.removeChild(dropzonePreviewNode); // 그리고 삭제


// Set Dropzone Options
Dropzone.options.myAwesomeDropzone = {
    autoDiscover: false,
    autoProcessQueue: false,
    uploadMultiple: true,
    parallelUploads: 20,
    maxFiles: 20,
    //addRemoveLinks: true,
    acceptedFiles: "application/pdf", // 이미지 : image/*, PDF : application/pdf
    maxFilesize: 10,
    dictDefaultMessage: "파일을 끌어다 놓거나,<br>클릭해서 직접 업로드하세요.<br>(PDF / 최대 10개)",

    previewTemplate: previewTemplate, // 만일 기본 테마를 사용하지않고 커스텀 업로드 테마를 사용하고 싶다면
    previewsContainer: '#dropzone-preview', // 드롭존 파일 나타나는 영역을 .dropzone이 아닌 다른 엘리먼트에서 하고싶을때

    clickable: "#dropzone",
}

// Initialize Dropzone
let myDropzone = new Dropzone("#my-awesome-dropzone", {url: "/store-item"});