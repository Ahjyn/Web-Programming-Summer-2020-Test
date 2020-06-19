function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)
    var xhttp = new XMLHttpRequest(); //create instant of request
    xhttp.open('GET', "https://api.github.com/users/" + user, true);
    xhttp.send();


    xhttp.onreadystatechange = function() {
        //condition statement operations

            if(xhttp.readyState == 4 && xhttp.status == 200)
            {
                var text = JSON.parse(xhttp.responseText);

                console.log(text); // pass parameter response
                showUser(text);
            }
            else {
                noSuchUser(username);
            }


    };
}

function showUser(user) {
    $("#link").append("<a href='"+user.html_url+"'>Link URL</a>");
    $("#profilePic").html("<img height='100' width='100' src='"+ user.avatar_url+"'/>");
    $("#name").text('Name of user : '+user.login);
    $("#Id").text('ID: '+user.id);


}

function noSuchUser(username) {
    alert("The user " + username + " is not available"); //concatenation of needed elements
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        if (e.which == 13) {
            username = $(this).val(); // recalling this specific instance
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            text = getGithubInfo(username);

        }
    })
});