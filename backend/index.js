var express = require('express');
var app = express();
var fs = require("fs");
var mysql = require('mysql');


// db stuff
var con = mysql.createConnection({
  host: "localhost",
  user: "sandro",
  password: "sandrosandro"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
});

app.get('/listMeals', function (req, res) {
   fs.readFile( __dirname + "/" + "meals.json", 'utf8', function (err, data) {
       console.log( data );
       res.end( data );
   });
})

app.get('/listRecipes', function (req, res){

});

app.get('/ingredient/list', function (req, res){

});

app.get('/ingredient/:id', function (req, res){

});

/**
 * post a list of ingredients id
 * ["beef", "peas"]
 */
app.post('/recipe/search', function (req, res){

});



var server = app.listen(8081, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("Example app listening at http://%s:%s", host, port)

})
