


document.addEventListener("load", getAllItems()); //good
document.addEventListener("load", getAllPeople()); //good
document.addEventListener("load", getPeopleByUserId({id: 3})); //good, with static variable
document.addEventListener("load", getItemById({id: 1}));
document.addEventListener("load", getAllUsers()); //good
document.addEventListener("load", getPersonById({id: 8}));


//using the testBtn id
//had to wrap it in "function" like this to prevent from running the first time on page load
// document.getElementById("testBtn").addEventListener("click", 
//     function () {
//         saveItem({"itemName": "TestOrange"}); //good, for static values. Replace "testOrange" with a variable
//     });

// document.getElementById("testBtn").addEventListener("click", 
//     function(){
//         deleteItemById({id: 11}); //good, for static variable
//         console.log("Deleted");
//     });

// document.getElementById("testBtn").addEventListener("click", 
//     function(){

//         getUserById({id: 3}); //good, for static variable

//     });

// document.getElementById("testBtn").addEventListener("click", 
//     function(){
//         saveUser({firstName: "Testing", 
//                     lastName: "Tester", 
//                     email: "test@test.com", 
//                     password: "password"}) //good, for static information
//     });

document.getElementById("testBtn").addEventListener("click", 

    function(){

    updateUser({id: 5, firstName: "Fake Test", lastName: "Lies", email: "updated@lol.net", password: "password"});

    });

document.getElementById("testBtn").addEventListener("click", inputReceived);

async function inputReceived(){
    console.log("Input received!");
}

//all of these should be named the same as the functions in the back end code********************************

//may not be used
async function getAllItems() {

    const response = await fetch("http://localhost:8080/getAllItems");
    const jsonData = await response.json();
    console.log(jsonData);
    return jsonData;
    

}


//saving an item to a list, will need to get some kind of list ID in there potentially
//itemName is the key
async function saveItem(params){

    const response = await fetch('http://localhost:8080/saveItem', {
        method: 'POST',
        body: JSON.stringify(params),
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });

}

//searching a specific item
async function getItemById(params){

    const response = await fetch("http://localhost:8080/getItemById" + '?' + new URLSearchParams(params)); 
    const data = await response.json();
    console.log(data);
    return data;

}

//editing an item, not sure if i'll use this
async function updateItem(){}

//deleting a specific item from the list
//this is "deleteItem" in the database listener
async function deleteItemById(params){

    const response = await fetch('http://localhost:8080/deleteItem/' + '?' + new URLSearchParams(params), {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });


}

//not sure if i'll use this, ***3 hours later*** J/K I NEED IT FOR TESTING LOL
async function getAllUsers(){

    const response = await fetch("http://localhost:8080/getAllUsers/");
    const jsonData = await response.json();
    console.log(jsonData);
    return jsonData;


}

//get a specific user by their ID, may need this for getting their specific friend lists
async function getUserById(params){

    const response = await fetch("http://localhost:8080/getUserById" + '?' + new URLSearchParams(params)); 
    const data = await response.json();
    console.log(data);
    return data;


}

//adding a new user to the database, will do this via the submit button in createAccount.html
//don't know whether or not the user should be logged in after they do this
//params, firstName, lastName, email, password
async function saveUser(params){

    const response = await fetch('http://localhost:8080/saveUser/', {
        method: 'POST',
        body: JSON.stringify(params),
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });


}

//updating a user's info in the database, will do this via the submit button in accountInfo.html
//params are id, firstName, lastName, email, password
async function updateUser(params){

    const response = await fetch('http://localhost:8080/updateUser/', {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'        
        },
        body: JSON.stringify(params)
        
    });
    
}

//deleting a user from the database, will do this via a delete button in the account info
async function deleteUser(params){
    const response = await fetch('http://localhost:8080/deleteUser/' + '?' + new URLSearchParams(params), {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
}

//will do this via the submit button on login.html - success means that "Log In" will disappear in favor of the acct.png
//params will be the e-mail and password from the fields
async function loginUser(params){

    const response = await fetch("http://localhost:8080/loginUser", {


        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(params)
    }); 
    
    const data = response.status;
    console.log(data);
    return data;

}

//don't know if i'll need this, j/k for testing again
async function getAllPeople(){

    const response = await fetch("http://localhost:8080/getAllPeople");
    const jsonData = await response.json();
    console.log(jsonData);

}

//this will be done in friendLists.html, as well as the modal windows for adding new lists
//initial DB testing will use userId "3" until we start passing variables around with logged in account
async function getPeopleByUserId(params){

    const response = await fetch("http://localhost:8080/getAllPeople" + '?' + new URLSearchParams(params)); 
    const data = await response.json();
    console.log(data);
        

}

//this will be done in some page, maybe browseresults.html, where you pull up the items based on the person's ID
async function getPersonById(params){

    const response = await fetch("http://localhost:8080/getPersonById/" + '?' + new URLSearchParams(params)); 
    const data = await response.json();
    console.log(data);

}

//not sure if i'll use this
async function updatePerson(){}

//will do this in the modal window when saving a new list
//needed params...
//firstName, lastName
//userAttached - a key that has a JSON object with the id, first/last, email/password of the logged in user value, not just one value
//itemAttachedId - a JSON object with id and itemName
//relationship
async function savePerson(params){

    const response = await fetch('http://localhost:8080/saveUser/', {
        method: 'POST',
        body: JSON.stringify(params),
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
}

//will do this in friendLists.html, with the delete list button
async function deletePerson(){
    const response = await fetch('http://localhost:8080/deletePerson/' + '?' + new URLSearchParams(params), {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
}




