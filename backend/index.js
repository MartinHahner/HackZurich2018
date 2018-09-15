var express = require('express');
var app = express();
var fs = require("fs");
var mysql = require('mysql');


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

app.get('/listRecipes', function (req, res){

})

var server = app.listen(8081, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("Example app listening at http://%s:%s", host, port)

})
