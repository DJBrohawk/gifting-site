// Language: JavaScript

//import express from "express";
// import bodyParser from "body-parser"; // dont think I need this

//const express = require('express');
//const bodyParser = require('body-parser');

//const main = express();
//const port = 5000;

// Static Files
//use('/', this.static('public'));
//use('/css', this.static(__dirname + 'public/css'));
//use('/img', this.static(__dirname + 'public/img'));
//use('/js', this.static(__dirname + 'public/js'));

// main.use(express.static(__dirname + 'public/css'));
// main.use('/img', express.static(__dirname + 'public/img'));
// main.use('/js', express.static(__dirname + 'public/js')); 

// Templating Engine
// app.set('views', './src/views')
// app.set('view engine', 'ejs')

//main.use(bodyParser.urlencoded({ extended: true }));

// Routes
// const newsRouter = require('./src/routes/news')

// app.use('/', newsRouter)
// app.use('/article', newsRouter)

// Listen on port 5000
//main.listen(process.env.PORT || port, () => console.log(`Listening on port ${port}`));



function login() {
console.log("SURPRISE BOO");


if (sessionStorage.getItem("boo") === 'false'){

sessionStorage.setItem("boo", "true");

} else if (sessionStorage.getItem("boo") === 'true') {

sessionStorage.setItem("boo", "false");
}


    console.log(boo);

}

document.addEventListener("DOMContentLoaded", () => {


    if (sessionStorage.getItem("boo") === 'false') {

        console.log("helpsda");
        document.getElementById("mainnav").innerHTML = `
        
       

           
        <a href="browse.html">Browse For Items</a>
        <a href="friendLists.html">Manage Friend List</a>
        <a href="contactUs.html">Contact Us</a>
        <a href="about.html">About</a>
        <a href="howToUse.html">How to Use This Site</a>
        <a href="logIn.html" id="login">Log In</a>`;

     
    
    } else if (sessionStorage.getItem("boo") === 'true') {

        console.log("help");
   
        document.getElementById("mainnav").innerHTML = `

        <a href="browse.html">Browse For Items</a>
        <a href="friendLists.html">Manage Friend List</a>
        <a href="contactUs.html">Contact Us</a>
        <a href="about.html">About</a>
        <a href="howToUse.html">How to Use This Site</a>
        <a href="accountInfo.html" class="acct"><img src="acct.png" height="40px" width="40px"></a>`;
     

    }




});

document.getElementById("login").addEventListener("click",login);