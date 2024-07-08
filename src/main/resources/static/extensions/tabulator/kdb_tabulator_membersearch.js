
function userSearchEditor(cell, onRendered, success, cancel){
    // Create and style input
    var cellValue = cell.getValue(),
        input = document.createElement("input");

    input.setAttribute("type", "text");
    input.style.padding = "4px";
    input.style.width = "100%";
    input.style.boxSizing = "border-box";
    input.value = cellValue;

    onRendered(function(){
        input.focus();
        input.style.height = "100%";
    });

    // Search button
    var button = document.createElement("button");
    button.className = "btn btn-primary btn-sm";
    button.innerText = "검색";
    button.onclick = function(){
        // 모달을 올바르게 표시
        console.log(button.onclick);
        $('#userSearchModal').modal('show');
        // Populate search results and handle selection
        $('#userSearchResults').empty();
        $('#searchUserInput').on('input', function(){
            // Mock search results
            var users = ["K140024", "K150024", "user3"];
            var searchResults = users.filter(user => user.includes($('#searchUserInput').val()));
            $('#userSearchResults').empty();
            searchResults.forEach(function(user){
                var li = $('<li class="list-group-item list-group-item-action">').text(user);
                li.on('click', function(){
                    input.value = user;
                    $('#userSearchModal').modal('hide');
                    success(user);
                });
                $('#userSearchResults').append(li);
            });
        });
    };

    var container = document.createElement("div");
    container.className = "d-flex";
    container.appendChild(input);
    container.appendChild(button);

    return container;
}