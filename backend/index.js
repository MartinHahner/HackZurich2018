var express = require('express');
var app = express();
var fs = require("fs");
var mysql = require('mysql');

const recipe = require("./src/recipe")

// db stuff
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: "mydb"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
});

app.get('/listUsers', function (req, res){
  con.query('SELECT iduser, firstname, lastname from user', function (error, results, fields){
    if (error) throw error;
    res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
  });
});

app.get('/listMeals', function (req, res) {
  con.query('SELECT * from meal', function (error, results, fields){
    if (error) throw error;
    res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
  });
})

app.get('/ingredient/list', function (req, res){
  res.status(200).json(recipe.ingredientsList);
});

app.get('/ingredient/:id', function (req, res){
  var ingredient = recipe.ingredientsList.find(e => e.id === req.params.id)
  if(ingredient) {
    return res.status(200).json(ingredient);
  }
  res.status(404);
});

/**
 * post a list of ingredients id
 * ["beef", "peas"]
 */
app.post('/recipe/search', function (req, res){

});

/**
 * return all recipes
 */
app.get('/recipe/list', function (req, res){

});

var server = app.listen(8081, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("Example app listening at http://%s:%s", host, port)

})
