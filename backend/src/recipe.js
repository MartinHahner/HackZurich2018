const ingredientsList = require('../assets/ingredients.json');
const axios = require('axios');

const KITCHEN = 'butter,milk,mozzarella,cheddar,american cheese,havarti cheese,onion,garlic,carrot,bell pepper,cucumber,tomato,chicken breast,vegetable oil,olive oil,yogurt,coconut milk,paprika,garlic powder,oregano,cumin,cayenne,nutmeg,curry powder,bay leaf,taco seasoning,italian herbs,garam masala,chipotle,ketchup,vinegar,teriyaki,hot sauce,sriracha,oyster sauce,canola oil,bouillon,peas,chickpea,lentil,edamame,kidney beans,red beans,black beans,almond,peanut'

const CATNAME = 'Dinner,,'

const SUPERCOOK_URL = 'https://www.supercook.com/dyn/results';


function findRecipe(ingredients) {
  co2Ing = ingredientsList.filter(e => ingredients.indexOf(e.name));
  ingredientsList.filter(e => ingredients.indexOf(e.name));

  return recipesList[0];
}

function findRecipesFromSupercook(ingredients) {
  const config = {
    headers: {
      'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36',
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  };

  const requestBody = {
    catname: CATNAME,
    kitchen: KITCHEN,
    focus: ingredients.join(',')
  }

  axios.post(SUPERCOOK_URL, requestBody, config).then(res => {
    console.log(res.data.results[0])
  }).catch(console.log)
}

findRecipesFromSupercook(["chicken breast", "peas"])

function findRecipes(ingredients, _recipes) {
  const ingredient = ingredients[0];
  console.log("ingredient", ingredient)
  if (ingredient) {
    possibleRecipes = _recipes.filter(e => e.ingredients.indexOf(ingredient) > -1)
    return findRecipes(ingredients.slice(1), possibleRecipes)
  }
  return _recipes;
}


const recipesList = [
  {
    id: "beef-stir-fry",
    name: "Beef stir-fry",
    description: "Write stuff here",
    ingredients: ["beef", "peas"]
  },
  {
    id: "beef-sandwich",
    name: "Beef sandwich",
    description: "Write stuff here",
    ingredients: ["beef", "cheese"]
  },
  {
    id: "chicken-and-peas-tartlets",
    name: "Chicken and peas tartlets",
    description: "Write stuff here",
    ingredients: ["chicken", "peas"]
  },
  {
    id: "garlic-butter-creamed-corn-chicken",
    name: "Garlic Butter Creamed Corn Chicken",
    description: "Write stuff here",
    ingredients: ["chicken", "corn"]
  },
  {
    id: "lamb-korma-with-lentils",
    name: "Lamb Korma with lentils",
    description: "Write stuff here",
    ingredients: ["lamb", "lentils"]
  },
  {
    id: "pork-stew",
    name: "Pork stew",
    description: "Write stuff here",
    ingredients: ["pork", "milk"]
  },
  {
    id: "crispy-chicken",
    name: "Crispy Chicken",
    description: "Write stuff here",
    ingredients: ["chicken", "wheat"]
  },
  {
    id: "spaghetti-carbonara",
    name: "Spaghetti Carbonara",
    description: "Write stuff here",
    ingredients: ["eggs", "pancetta"]
  },
  {
    id: "sausage-rolls-with-mashed-peas",
    name: "Sausage-rolls with mashed peas",
    description: "Write stuff here",
    ingredients: ["pork-sausages", "peas"]
  },
  {
    id: "almond-crusted-tofu",
    name: "Almond-crusted tofu",
    description: "Write stuff here",
    ingredients: ["tofu", "almonds"]
  },
  {
    id: "chili-sin-carne",
    name: "Chili sin carne",
    description: "Write stuff here",
    ingredients: ["rice", "quorn"]
  },
  {
    id: "croque-monsieur",
    name: "Croque Monsieur",
    description: "Croque Monsieur",
    ingredients: ["ham", "gruyere"]
  }
]

module.exports = {
  ingredientsList,
  findRecipes,
  findRecipesFromSupercook,
  recipesList
};