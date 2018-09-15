const ingredientsList = require('../assets/ingredients.json');

findRecipe(ingredients) {
  co2Ing = ingredientsList.filter(e => ingredients.indexOf(e.name));


}

module.exports = {
  findRecipe
};

const recipes = [
  {
    ingredients: ["Beef, Peas", "Cheese"]
  },
  {
    ingredients: ["Red kidney beans", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  },
  {
    ingredients: ["Chicken", "Cheese"]
  }
]