function searchEditor(cell, onRendered, success, cancel, apiUrl, keyName){
    // Create and style input
    var cellValue = cell.getValue() || "", // Handle undefined case
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
    var searchButton = document.createElement("button");
    searchButton.className = "btn btn-primary btn-sm";
    searchButton.innerText = "검색";

    // Variable to store selected code
    var selectedCode = null;

    searchButton.onclick = function(){
        // Perform search
        performSearch();
    };

    function performSearch() {
        $('#userSearchModal').modal('show');
        // Clear previous results and selection
        $('#userSearchResults').empty();
        selectedCode = null;

        var query = $('#searchUserInput').val();

        // Fetch search results from API
        fetch(apiUrl)
            .then(response => response.json())
            .then(data => {
                var searchResults = data.map(item => item[keyName]);
                $('#userSearchResults').empty();
                searchResults.forEach(function(code){
                    var li = $('<li class="list-group-item list-group-item-action">').text(code);
                    li.on('click', function(){
                        // Highlight selected item
                        $('#userSearchResults li').removeClass('active');
                        li.addClass('active');
                        selectedCode = code;
                    });
                    $('#userSearchResults').append(li);
                });
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }

    // Search input event
    $('#searchUserInput').off('input').on('input', function(){
        performSearch();
    });

    // Apply button event
    $('#applySelection').off('click').on('click', function(){
        if (selectedCode) {
            input.value = selectedCode;
            success(selectedCode);
            $('#userSearchModal').modal('hide');
        }
    });

    var container = document.createElement("div");
    container.className = "d-flex align-items-center"; // Center vertically
    container.appendChild(input);
    container.appendChild(searchButton);

    return container;
}
