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

con.connect(function (err) {
  if (err) console.log('no sql found');
  console.log("Connected!");
});


app.get('/', function (req, res) {
  res.send('hello!');
});

// TODO: add filter
app.get('/meal', function (req, res) {
  con.query('SELECT * from meal', function (error, results, fields){
    if (error) throw error;
    res.status(200).json(results);
  });
});

app.post('/meal', function (req, res){
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
    res.status(200).json(meal);
  });

});

app.get('/user', function (req, res){
  con.query('SELECT iduser, firstname, lastname from user', function (error, results, fields){
    if (error) throw error;
    res.status(200).json(results);
  });
});


// example: /participate?idmeal=1
app.get('/participate', function (req, res){
  var idmeal = req.query.idmeal;

  con.query('SELECT u.iduser, u.firstname, u.lastname, p.status, m.title, m.idmeal, m.title ' +
            'FROM user u, participate p, meal m WHERE p.idmeal = m.idmeal AND u.iduser = p.iduser AND m.idmeal = ?', [idmeal],
            function (error, results, fields){
              if(error) console.log(error);
              res.status(200).json(results);
            });

});

app.post('/participate', function (req, res){
  var participation = {'idmeal': req.body.idmeal,
                       'iduser': req.body.iduser,
                       'status': 0};

  var query = con.query('INSERT INTO participate SET ?', participation, function (error, results){
    if (error) console.log(error);
    res.status(200).json(participation);
  });
});

// TOOD: update a standby(0) participation to accept (1) it or refuse (2) it
app.put('/participate', function (req, res){
  var query = con.query('UPDATE participate SET status = ? WHERE idmeal = ? AND iduser = ?', [parseInt(req.body.stat),parseInt(req.body.idmeal), parseInt(req.body.iduser)],
    function(error, results){
      if (error) console.log(error);
      res.status(200).send(results.affectedRows + " record(s) updated");
    });
  console.log(query.sql);
});


app.get('/review', function (req, res){

});

app.post('/review', function (req, res){

});


app.get('/ingredient/list', function (req, res) {
  res.status(200).json(recipe.ingredientsList);
});

app.get('/ingredient/:id', function (req, res) {
  var ingredient = recipe.ingredientsList.find(e => e.id === req.params.id)

  console.log("requesting ingredient:", req.params.id, "found:", ingredient)

  if (ingredient) {
    res.status(200).json(ingredient);
  } else {
    res.status(404).json({ status: 404, message: `${req.params.id} not found` });
  }
});

/**
 * post a list of ingredients id
 * ["beef", "peas"]
 */
app.post('/recipe/search', function (req, res) {
  console.log("find recipe containing:", req.body);
  res.status(200).json(recipe.recipes[0]);
});

/**
 * return all recipes
 */
app.get('/recipe/list', function (req, res) {
  res.status(200).json(recipe.recipes);
});

app.get('/recipe/:id', function (req, res) {
  var _recipe = recipe.recipes.find(e => e.id === req.params.id)
  console.log("requesting recipe:", req.params.id, "found:", _recipe)

  if (_recipe) {
    res.status(200).json(_recipe);
  } else {
    res.status(404).json({ status: 404, message: `${req.params.id} not found` });
  }
});

var server = app.listen(process.env.PORT || 8080, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("Example app listening at http://%s:%s", host, port)

})
