/****************************************************************************/
/* Author: SangJin Hyun                                                     */
/* Assignment: ICP - 4 : Part2 - User Profiling                             */
/* File: js to instigate produce profile given a request using XMLHTTP      */
/****************************************************************************/

function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)


    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://api.github.com/users/' + user);

    xhr.onload = function () {
        if(xhr.readyState == 4 && xhr.status == 200)
        {
            var text = JSON.parse(xhr.responseText);

            showUser(text); //call showUser function

        }
        else {  //doesn't meet condition of if (both statements therein)
            noSuchUser(username); //call noSuchUser function
        }
    };
    xhr.send()

}

function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
// setting the html to elemnts in page using jQuery
    $("#displaytext").text(user.login);
    var link = "<a target='_blank' </a>";
    $(".information").html("<label>Profile</u></label>" +
        "<br/><br/><label style='color: darkslateblue'>Name : </label>"+ user.login);
}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    alert( username + " " + "is not a profile -- please try again."); //concatenation of needed elements while passing username from call else
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});