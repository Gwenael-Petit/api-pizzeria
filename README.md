# SAE 401

Membres :
- DASSONVILLE Valentin
- PETIT Gwenael

## Endpoints

### Ingredients

| HTTP | URI | Requête | Réponse |
| ---  | --- | ---     | ---     |
| GET  | /ingredients | | Obtenir la collection de tous les ingrédients |
| GET  | /ingredients/{id} | | - Obtenir un ingrédient particulier par son id <br>- 404 si l'ingrédient n'existe pas |
| GET | /ingredients/{id}/name | | - Obtenir uniquement le nom d’un ingrédient spécifique <br>- 404 si l'ingrédient n'existe pas |
| POST | /ingredients | Ingredient JSON | - Ajouter un nouvel ingrédient <br> - 409 si l'ingrédient existe déjà |
| DELETE | /ingredients/{id} | | - Supprimer un ingrédient <br> - 404 si l'ingrédient n'existe pas |

### Pizzas

| HTTP | URI | Requête | Réponse |
| ---  | --- | ---     | ---     |
| GET  | /pizzas | | Obtenir la collection de toutes les pizzas |
| GET | /pizzas/{id} | | - Obtenir une pizza en particulier <br> - 404 si la pizza n'existe pas |
| POST | /pizzas | Pizza JSON | - Ajout d’une nouvelle pizza avec ses ingrédients <br> - 409 si la pizza existe déjà |
| DELETE | /pizzas/{id} | | - Suppression d'une pizza <br> - 404 si la pizza n'existe pas |
| PATCH | /pizzas/{id} | Pizza JSON (valeurs à changer) | - Modification d’un attribut d’une pizza <br> - 404 si la pizza n'existe pas |
| POST | /pizza/{id}/addIngredient | Ingredient JSON | - Ajout d’un ingrédient à une pizza <br> - 404 si la pizza n'existe pas <br> - 409 si l'ingrédient est déjà sur la pizza |
| POST | /pizza/{id}/removeIngredient | Ingredient JSON | - Suppression d’un ingrédient à une pizza <br> - 404 si la pizza n'existe pas <br> - 404 si l'ingrédient n'est pas sur la pizza |

### Commandes

| HTTP | URI | Requête | Réponse |
| ---  | --- | ---     | ---     |
| GET  | /commandes | | Liste des commandes en cours |
| GET  | /commandes/{id} | | - Obtenir le détail d’une commande <br> - 404 si la commande n'existe pas |
| POST | /commandes | Commande JSON | - Enregistrement d’une nouvelle commande <br> - 409 si la commande existe déjà |