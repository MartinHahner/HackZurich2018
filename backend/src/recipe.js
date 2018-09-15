const ingredientsList = require('../assets/ingredients.json');

function findRecipe(ingredients) {
  co2Ing = ingredientsList.filter(e => ingredients.indexOf(e.name));
  ingredientsList.filter(e => ingredients.indexOf(e.name));
}

module.exports = {
  ingredientsList,
  findRecipe
};

const recipes = [
  {
    name: "Beef stir-fry",
    description: "Write stuff here",
    ingredients: ["beef", "peas"]
  },
  {
    name: "Beef sandwich",
    description: "Write stuff here",
    ingredients: ["beef", "cheese"]
  },
  {
    name: "Chicken and peas tartlets",
    description: "Write stuff here",
    ingredients: ["chicken", "peas"]
  },
  {
    name: "Garlic Butter Creamed Corn Chicken",
    description: "Write stuff here",
    ingredients: ["chicken", "corn"]
  },
  {
    name: "Lamb Korma with lentils",
    description: "Write stuff here",
    ingredients: ["lamb", "lentils"]
  },
  {
    name: "Pork stew",
    description: "Write stuff here",
    ingredients: ["pork", "milk"]
  },
  {
    name: "Crispy Chicken",
    description: "Write stuff here",
    ingredients: ["chicken", "wheat"]
  },
  {
    name: "Spaghetti Carbonara",
    description: "Write stuff here",
    ingredients: ["eggs", "pancetta"]
  },
  {
    name: "Sausage-rolls with mashed peas",
    description: "Write stuff here",
    ingredients: ["pork-sausages", "peas"]
  },
  {
    name: "Almond-crusted tofu",
    description: "Write stuff here",
    ingredients: ["tofu", "almonds"]
  },
  {
    name: "Chili sin carne",
    description: "Write stuff here",
    ingredients: ["rice", "quorn"]
  },
  {
    name: "Croque Monsieur",
    description: "Croque Monsieur",
    ingredients: ["ham", "gruyere"]
  }
]