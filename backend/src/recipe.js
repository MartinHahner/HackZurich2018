const ingredientsList = require('../assets/ingredients.json');

findRecipe(ingredients) {
  ingredientsList.filter(e => ingredients.indexOf(e.name))
}

module.exports;