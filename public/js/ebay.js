
let itemArr = [];

document.addEventListener("load", ebayLoad());

class EbayItem {

    constructor(name, itemId, imageUrl, itemUrl){
        this.name = name;
        this.imageUrl = imageUrl;
        this.itemUrl = itemUrl;
        this.itemId = itemId;
    }

}



async function getWord(){

    const word = (await fetch("https://random-word-api.herokuapp.com/word")).json();
    console.log(word);
    return word;

}

async function ebaySearch(btn){

    const searchBox = document.getElementById("browseText");
    param = searchBox.value;

    //I think VSCode put the first (second?) await there on its own and I'm scared to remove it because this works XD
    const response = await (await fetch("http://localhost:3500/ebay/?" + new URLSearchParams("q=" + param + "&limit=8") )).json().then(
        function(result) {

            sessionStorage.setItem("itemArr", "");
            //some variables - itemArr is for 
            //btnClass is to set the class of the save button so that if you're not logged in, it won't appear
            //counter is for assigning to a save button value so that I can pluck the item from the itemArr
            //array and pass it to the Java back end
            itemArr = [];
            let btnClass = '';
            let counter = 0;

            if(sessionStorage.getItem("loggedIn") == "false")
                btnClass = "hideBtn";
                else
                btnClass = "saveBtn";

            console.log(result);

            console.log(result['itemSummaries']); //this works, mercifully

             //for now, this is 4 - set in the ebayController.js
             for (let i = 0; i < result['itemSummaries'].length; i++){

                //get each item returned from ebay
                const item = document.getElementById("item" + (i+1));
                //insert each item into the four allotted slots
            item.innerHTML = `<div><a href="`+ result['itemSummaries'][i].itemWebUrl +`"><img src="` + result['itemSummaries'][i]['image']['imageUrl'] + `" class="homeImage"></a><br>`+ `<a href="`+ result['itemSummaries'][i].itemWebUrl +`" class="ebayItem">` + result['itemSummaries'][i].title + `</a></div>
            <button type="button" class="btn btn-secondary `+ btnClass + `" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="saveItemNameToStorage(this);modalLoad()" value="` + i + `">Save</button>`
                
                const itemData = new EbayItem(result['itemSummaries'][i].title,
                                            result['itemSummaries'][i].itemId,
                                            result['itemSummaries'][i]['image']['imageUrl'],
                                            result['itemSummaries'][i].itemHref);
                
                itemArr.push(itemData);
                counter++;
        
            }

            console.log(itemArr);
            sessionStorage.setItem("itemArr", JSON.stringify(itemArr));

            return result; 
        });
   
}

/*
    What do I need this ebay thing to do


    Load on index, load on home - For now, make it four items from a random query. Easy enough
    1) Get an array of random words
    2) Randomly pick one of those words
    3) Display the items - an array of objects, pulling only what's necessary from ebay?

    On the search page, we need to pass in whatever's in the text box to the ebay query function
    May need to increase the limit of items returned to fit this. Easy enough




*/



async function ebayLoad(){

   
    const word = await getWord();
    console.log(word);
    

    const response = (await fetch("http://localhost:3500/ebay/?" + new URLSearchParams("q=" + word[0] + "&limit=4"))).json().then(
        function(result) {

            itemArr = [];

            let btnClass = '';

            if(sessionStorage.getItem("loggedIn") == "false")
                btnClass = "hideBtn";
                else
                btnClass = "saveBtn";

            console.log(result);

            if(result.total < 4) {ebayLoad();}

            console.log(result['itemSummaries']); //this works, mercifully

            console.log(result['itemSummaries'][0]);

            console.log(result['itemSummaries'][0].itemId);

            console.log(result['itemSummaries'][0]['image']['imageUrl']);

            

            //for now, this is 4 - set in the ebayController.js
            for (let i = 0; i < result['itemSummaries'].length; i++){

                //get each item returned from ebay
                const item = document.getElementById("item" + (i+1));
                //insert each item into the four allotted slots
            item.innerHTML = `<div><a href="`+ result['itemSummaries'][i].itemWebUrl +`"><img src="` + result['itemSummaries'][i]['image']['imageUrl'] + `" class="homeImage"></a><br>`+ `<a href="`+ result['itemSummaries'][i].itemWebUrl +`" class="ebayItem">` + result['itemSummaries'][i].title + `</a></div>
            <button type="button" class="btn btn-secondary `+ btnClass + `" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="saveItemNameToStorage(this);modalLoad()" value="` + i + `">Save</button>`
                
                const itemData = new EbayItem(result['itemSummaries'][i].title,
                                            result['itemSummaries'][i].itemId,
                                            result['itemSummaries'][i]['image']['imageUrl'],
                                            result['itemSummaries'][i].itemHref);
                
                itemArr.push(itemData);
        
            }

            console.log(itemArr);
            sessionStorage.setItem("itemArr", JSON.stringify(itemArr));

        });

        return response;
        console.log(word);
}
