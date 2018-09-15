var express = require('express');
var fs = require("fs");
var mysql = require('mysql');
var bodyParser = require('body-parser');

var app = express();
// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }))

const recipe = require("./src/recipe")
// parse application/json
app.use(bodyParser.json())

// db stuff
if (process.env.VCAP_SERVICES) {
  const vcapServices = JSON.parse(process.env.VCAP_SERVICES);
  dbUrl = vcapServices.mariadbent[0].credentials.host;
  dbUser = vcapServices.mariadbent[0].credentials.username;
  dbPwd = vcapServices.mariadbent[0].credentials.password;
  dbDB = vcapServices.mariadbent[0].credentials.database;

} else {
  dbUrl = 'localhost';
  dbUser = 'root';
  dbPwd = 'root';
  dbDB = 'mydb';
}

var con = mysql.createConnection({
  host: dbUrl,
  user: dbUser,
  password: dbPwd,
  database: dbDB,
});

con.connect(function(err) {
  if (err) console.log('no sql found');
  console.log("Connected!");
});


app.get('/', function (req, res){
  res.send('hello!');
});

app.post('/createMeal', function (req, res){
  var meal = {'when': req.body.when,
              'title': req.body.title,
              'description': req.body.description,
              'address': req.body.address,
              'city': req.body.city,
              'zip': req.body.zip,
              'max_people': req.body.max_people,
              'co2_score': req.body.co2_score,
              'ingredients': req.body.ingredients};

  var query = con.query('INSERT INTO meal SET ?', meal, function (error, results){
    res.send(meal);
    console.log(query.sql);

  });

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

  console.log("requesting:",req.params.id, "found:",ingredient)

  if(ingredient) {
    res.status(200).json(ingredient);
  } else {
    res.sendStatus(404);
  }
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

var server = app.listen(process.env.PORT || 8080, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("Example app listening at http://%s:%s", host, port)

})
