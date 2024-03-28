$("#summernote").summernote({
  tabsize: 2,
  height: 300,
  minHeight : null, // set minimum height of editor
  maxHeight : null, // set maximum height of editor
  focus : true,
  lang: "ko-KR",
  toolbar: [
					    // [groupName, [list of button]]
					    ['fontname', ['fontname']],
					    ['fontsize', ['fontsize']],
					    ['color', ['color']],
					    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
					    ['para', ['ul', 'ol', 'paragraph']],
					    ['height', ['height']]
  					  ],
					fontNames: ['나눔고딕', '맑은 고딕'],
					fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
})
$("#hint").summernote({
  height: 100,
  toolbar: false,
  placeholder: "type with apple, orange, watermelon and lemon",
  hint: {
    words: ["apple", "orange", "watermelon", "lemon"],
    match: /\b(\w{1,})$/,
    search: function (keyword, callback) {
      callback(
        $.grep(this.words, function (item) {
          return item.indexOf(keyword) === 0
        })
      )
    },
  },
})
