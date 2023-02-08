# SAE 401

Membres :
- DASSONVILLE Valentin
- PETIT Gwenael

## Endpoints

### Ingredients

| HTTP | URI | Requête | Réponse |
| ---  | --- | ---     | ---     |
| GET  | /ingredients | | Obtenir la collection de tous les ingrédients |
| GET  | /ingredients/{id} | | Obtenir un ingrédient particulier par son id ou 404 s'il n'existe pas |
| GET | /ingredients/{id}/name | | Obtenir uniquement le nom d’un ingrédient spécifique ou 404 s'il n'existe pas |
| POST | /ingredients | Ingredient JSON | Ajouter un nouvel ingrédient ou 409 s'il existe déjà |
| DELETE | /ingredients/{id} | | Supprimer un ingrédient ou 404 s'il n'existe pas |

### Pizzas

| HTTP | URI | Requête | Réponse |
| ---  | --- | ---     | ---     |
| GET  | /pizzas | | Obtenir la collection de toutes les pizzas |
| GET | /pizzas/{id} | | Obtenir une pizza en particulier ou 404 si elle n'existe pas |
| POST | /pizzas | Pizza JSON | Ajout d’une nouvelle pizza avec ses ingrédients ou 409 si elle existe déjà |
| DELETE | /pizzas/{id} | | Suppression d'une pizza ou 404 si elle n'existe pas |
| PATCH | /pizzas/{id} | Pizza JSON (valeurs à changer) | Modif d’un attribut d’une pizza (le prix augmente) ou 404 si elle n'existe pas |
| POST | /pizza/{id}/addIngredient | Ingredient JSON | Ajout d’un ingrédient à une pizza ou 404 si elle n'existe pas ou 409 si l'ingrédient est déjà sur la pizza |
| POST | /pizza/{id}/removeIngredient | Ingredient JSON | Suppression d’un ingrédient à une pizza ou 404 si elle n'existe pas ou que l'ingrédient n'est pas sur la pizza |

### Commandes

| HTTP | URI | Requête | Réponse |
| ---  | --- | ---     | ---     |
| GET  | /commandes | | Liste des commandes en cours |
| GET  | /commandes/{id} | | Le détail d’une commande ou 404 si elle n'existe pas |
| POST | /commandes | Commande JSON | Enregistrement d’une nouvelle commande ou 409 si elle existe déjà |