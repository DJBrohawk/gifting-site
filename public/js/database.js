


// document.addEventListener("load", getAllItems()); //good
// document.addEventListener("load", getAllPeople()); //good
// document.addEventListener("load", getPeopleByUserId({userId: 9})); //good, with static variable
// document.addEventListener("load", getItemById({id: 1}));//good, with static variable
// document.addEventListener("load", getAllUsers()); //good
// document.addEventListener("load", getPersonById({id: 2}));//good, with static variable

//I didn't want to add this, but I can only deal with pending promises for so long
let randEbayWord = '';


async function inputReceived(){
    console.log("Input received!");
}

//uses function saveUser with params
//adding a new user to the database, will do this via the submit button in createAccount.html
//don't know whether or not the user should be logged in after they do this
//params, firstName, lastName, email, password
async function createAccount(){


    //I need the data from each field filled out on createAccount.html
    const pw1 = document.getElementById("createPassword").value;
    const pw2 = document.getElementById("createPasswordConfirm").value;

    //if passwords match
    try{

    if(!document.getElementById("accountFlexCheck").checked) {
        throw new Error(`You must read and acknowledge you have read the Terms and Conditions and Privacy Policy`);
        
    }


    if(pw1 === pw2){
       
        const fname = document.getElementById("createFname").value;
        const lname = document.getElementById("createLname").value;
        const userEmail = document.getElementById("createEmail").value;
        const pass = document.getElementById("createPassword").value;
       

        //run the update user function with the above params
        await saveUser({firstName: fname, lastName: lname, email: userEmail, password: pass});

        document.location.href = "http://127.0.0.1:5500/html/logIn.html";


    } else {
        throw new Error(`Passwords do not match. Re-input passwords and try again.`);
    }
    } catch (err) {
        document.getElementById("createError").innerText = err;
        return;
    }



}

async function login(){

    const user = document.getElementById("username").value;
    const pass = document.getElementById("password").value;

    console.log(user);
    console.log(pass);


    if(await loginUser({email: user, password: pass}) === true){
        sessionStorage.setItem("loggedIn", "true");
        console.log("Successful login!");
        document.location.href = "http://127.0.0.1:5500/html/home.html";
    } 

}

//function to load on page specifically for the accountInfo.html page
//loading account info from Session Storage
//they can edit any of their information here.
//Future iterations of this may prevent the e-mail from being added purely because there's a possibility you could have duplicate registrations and weird data returns
//but since this is a school project that'll never go live, I imagine it's fine to not go that deep into it for now.
//the five fields are, accountFname, accountLname, accountEmail, accountPassword, accountPasswordConfirm
async function accountInfoLoad(){

    document.getElementById("accountFname").value = sessionStorage.getItem("firstName");
    document.getElementById("accountLname").value = sessionStorage.getItem("lastName");
    document.getElementById("accountEmail").value = sessionStorage.getItem("email");
    document.getElementById("accountPassword").value = sessionStorage.getItem("password");
    document.getElementById("accountPasswordConfirm").value = sessionStorage.getItem("password");


}

//saving the info if it's updated from accountinfo.html
//requires matching passwords, like most sites do, though mine's not checking on character change for now.
//params are id, firstName, lastName, email, password
async function accountInfoSave(){

    const pw1 = document.getElementById("accountPassword").value;
    const pw2 = document.getElementById("accountPasswordConfirm").value;

    //if passwords match
    try{
    if(pw1 === pw2){
       
        const fname = document.getElementById("accountFname").value;
        const lname = document.getElementById("accountLname").value;
        const userEmail = document.getElementById("accountEmail").value;
        const pass = document.getElementById("accountPassword").value;
        const userId = sessionStorage.getItem("id");

        //run the update user function with the above params
        updateUser({id: userId, firstName: fname, lastName: lname, email: userEmail, password: pass});

        //save the new values into session storage
        sessionStorage.setItem("firstName", fname);
        sessionStorage.setItem("lastName", lname);
        sessionStorage.setItem("email", userEmail);
        sessionStorage.setItem("password", pass);


    } else {
        throw new Error(`Passwords do not match. Re-input passwords and try again.`);
    }
    } catch (err) {
        document.getElementById("accountError").innerText = err;
        return false;
    }


}

//function that loads all the friends' items on friendLists.html
//this will load all the people using getPeopleById and then getting the items attached to those people, creating a div for each one
async function friendListLoad(){

    let jsonData = [];
    //if you're not logged in, you won't have any friends, so log in!
    if (sessionStorage.getItem("loggedIn") == "false"){
      
        document.location.href = "http://127.0.0.1:5500/html/logIn.html";
        return;
        //this may need changing

    }

    const id = sessionStorage.getItem("id");
    jsonData = await getPeopleByUserId({userId: id});

    //jsonData is an array of JSON objects

    sessionStorage.setItem("jsonStorage", JSON.stringify(jsonData));

    console.log(sessionStorage.getItem("jsonStorage"));

    if(jsonData.length <= 0){

        document.getElementById("friendListRows").innerHTML = `It looks like you don't have any friends! Add some friends now!`;

    } else {

        console.log("Hello");

        //idea for delete logic for future me
        //assign the person ID to the button
        //when delete item button is pressed, check the button ID, use deletePerson() function using that ID, make button type "submit" so that it automatically
        //reloads page

        //ideas for tonight
        //finding a way to return the button ID when clicking it - easiest
        //assigning all buttons a class and then iterating through every button to find an id that matches the one pressed, like nth-child type thing - gross

        jsonData.forEach((ele) => {

            console.log(ele['id']);

            document.getElementById("friendListRows").innerHTML += `
            
            
            <div class="row justify-content-center">
            <span class="col-5 text-center">
              <h2>`+ ele['firstName'] + ' ' + ele['lastName'] + `</h2>
            </span>
            <span class="col-4 text-center" id="itemText">`+ ele['itemAttachedId']['itemName'] +`</span>
            <span class="col-2">
                <button type="submit" class="btn btn-danger" id="` + ele['id'] + `" onclick=deleteFriend(this)>Delete Item</button>
            </span>
          </div>
            <br>
            
            
            `

                //you're going to come back to this tomorrow and be thankful past you wrote this:

                //the thinking is that jsonData, while you're on the page, is still in the system and therefore still accessible within this function.
                //it's possible you may need to put jsonData into sessionStorage, which should be fine even if it requires some casting.
                //that will allow you to (you didn't finish this sentence, sir, and therefore this was unhelpful)

        })

    }





}

async function deleteFriend(btn){

    console.log(btn.id);

    deletePerson({id: btn.id});
    location.reload();

}

//this function is going to be called in the modal window to add a new friend
//it will use the savePerson function using data from the modal window
//and, eventually, the item data from eBay, but for now we don't have that

async function createNewFriend(){


    console.log("hello");
    //get data from modal window

    const fname = document.getElementById("friendFname").value;
    const lname = document.getElementById("friendLname").value;
    const rship = document.getElementById("friendRelationship").value;
    const userId = sessionStorage.getItem("id");
    const iname = sessionStorage.getItem("itemName");
    
    

    //item name will be pulled in by selecting the button and then getting the inner text of its previous sibling
    //***NOTE:**** If the HTML in this changes, this may break!!

    if(fname.trim() == '' || lname.trim() == '' || rship.trim() == '' || iname.trim() == ''){

        document.getElementById("modalError").innerText = "Error: One of the required fields is missing. Please fill out all fields.";
        return;


    }

    console.log(iname);

    await savePerson({
        firstName: fname,
         lastName: lname,
         userAttached: {id: userId},
         itemAttachedId: {itemName: iname},
          relationship: rship
         })


}

async function modalLoad(){
    document.getElementById("friendItemName").value = sessionStorage.getItem("itemName");
}

//this is going to pass in the button clicked, but we're going to select the parent div
//and the inner text within that parent div to get the item name
async function saveItemNameToStorage(btn){

    let itemText = btn.parentElement.firstElementChild;

    let iname = itemText.innerText.trim();

    sessionStorage.setItem("itemName", iname);
}




//********************************************************************************** */
//start of back end code functions
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
//response is 200 if accepted, 401 if declined
//I'm returning a boolean to determine whether or not the login was valid, however we're going to store the account info in session storage to pull for other pages >:)
async function loginUser(params){

    try{
        
    const response = await fetch("http://localhost:8080/loginUser", {


        method: 'POST',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },

        body: JSON.stringify(params)
    }); 

    const data = await response.json();
  
    //if the response is from 200-299, an ok response, store the account data in session storage to be used elsewhere
    //we're saving the password in session storage - it's possible this is terrible practice. We're only doing this for the account info page, in the event
    //this were to ever have to be changed
    if(response.ok){
       
        console.log("ok");

        sessionStorage.setItem("id", data['id']);
        sessionStorage.setItem("firstName", data['firstName']);
        sessionStorage.setItem("lastName", data['lastName']);
        sessionStorage.setItem("email", data['email']);
        sessionStorage.setItem("password", data['password']);

        return true;
    } else {
        throw new Error(`HTTP error, status = ${response.status}`);
    }
    } catch (err) {
        document.getElementById("loginError").innerText = err + ' - Your username and/or password may be incorrect.';
        return false;
    }
    

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

    const response = await fetch("http://localhost:8080/getPeopleByUserId/" + '?' + new URLSearchParams(params)); 
    const data = await response.json();
    console.log(data);
    return data;
        

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

    const response = await fetch('http://localhost:8080/savePerson/', {
        method: 'POST',
        body: JSON.stringify(params),
        headers: {
            'Content-type': 'application/json'
        }
    });
}

//will do this in friendLists.html, with the delete list button
async function deletePerson(params){
    const response = await fetch('http://localhost:8080/deletePerson/' + '?' + new URLSearchParams(params), {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
}




