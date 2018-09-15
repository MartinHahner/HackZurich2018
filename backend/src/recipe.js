const ingredientsList = require('../assets/ingredients.json');

function findRecipe(ingredients) {
  co2Ing = ingredientsList.filter(e => ingredients.indexOf(e.name));
  ingredientsList.filter(e => ingredients.indexOf(e.name));

  return recipesList[0];
}

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
  recipesList
};