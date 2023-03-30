<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>CSCI441 Group 2 Gifting Site</title>
  <meta name="description" content="CSCI441 Group 2 Final Project">
  <meta name="author" content="CSCI441 Group 2">

  <meta property="og:title" content="CSCI441 Group 2 Final Project">
  <meta property="og:type" content="website">
  <meta property="og:description" content="A site for helping pick out gifts for your friends.">

  <link rel="icon" href="images/favicon.ico">
  <link rel="icon" href="images/favicon.svg" type="image/svg+xml">
  <link rel="account icon" href="images/acct.png">

  <link href="public/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="public/css/styles.css">
</head>


<body>

 

  <!-- Nav menu here-->
<div class="container-fluid navbar" height="80px">
<a href="index.php" class="logo"><img src="images/logo.png" height="80px" width="324px"></a>

<nav id="mainnav">

  <a href="html/browse.html">Browse For Items</a>
  <a href="html/friendLists.html">Manage Friend List</a>
  <a href="html/contactUs.html">Contact Us</a>
  <a href="html/about.html">About</a>
  <a href="html/howToUse.html">How to Use This Site</a>
  <a href="html/logIn.html" id="login">Log In</a>
  
  </nav>

</div>

<div class="container-fluid" max-height="100%">

  <h1>testing</h1>




</div>



<div class=".container"></div>
  <!--
  
  image of happy couple here

rotating images of items -  size dependent on window size-->

<!--

We do we want and what do we know

4 items, will be an array, with buttons under them for adding to the list.

The array will be objects, probably some kind of JSON object, from amazon, with data

Inside each column will be the data, with a button below it that will open either a modal or a popover to add to a list.

The lists will be stored in the database, per user...somehow.

Probably by storing the account ID in the session state, and then querying the database based on that?


-->


<div class="container-sm text-center">

  <div class="row">

    <div class="col" id="item1">

    </div>

    <div class="col" id="item2">


    </div>

    <div class="col" id="item3">


    </div>

    <div class="col" id="item4">


    </div>

  </div>




</div>


  <!--footer here-->
  <div class="container-fluid footer">

    
    <p>All rights reserved. Copyright 2023</p>  
      <p>This site is for demonstration purposes only.</p>
      
      <span class="botnav">
      <a href="html/privacyPolicy.html">Privacy Policy</a> |
      <a href="html/termsOfUse.html">Terms of Use</a> |
      <a href="html/careers.html">Careers</a>
      <span>





  </div>

  <script>

  sessionStorage.setItem("boo", "true");

  </script>
</body>
</html>