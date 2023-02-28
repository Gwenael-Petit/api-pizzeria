DROP TABLE IF EXISTS commandes_pizzas, pizzas_ingredients, commandes, pizzas, ingredients, users;

CREATE TABLE ingredients(
    id int PRIMARY KEY,
    name text,
    price numeric
);

CREATE TABLE pizzas(
    id int PRIMARY KEY,
    name text,
    basicPrice numeric,
    dough text
);

CREATE TABLE pizzas_ingredients(
    pizzaId int,
    ingredientId int,
    PRIMARY KEY(pizzaId, ingredientId),
    FOREIGN KEY(pizzaId) REFERENCES pizzas(id) ON DELETE CASCADE,
    FOREIGN KEY(ingredientId) REFERENCES ingredients(id) ON DELETE CASCADE
);

CREATE TABLE users(
    id int PRIMARY KEY,
    login text,
    password text
);

CREATE TABLE commandes(
    id int PRIMARY KEY,
    userId int,
    dateCommande date,
    FOREIGN KEY(userId) REFERENCES users(id)
);

CREATE TABLE commandes_pizzas(
    commandeId int,
    pizzaId int,
    PRIMARY KEY(commandeId, pizzaId),
    FOREIGN KEY(commandeId) REFERENCES commandes(id) ON DELETE CASCADE,
    FOREIGN KEY(pizzaId) REFERENCES pizzas(id) ON DELETE CASCADE
);

INSERT INTO ingredients VALUES(1, 'pomme de terre', 0.4);
INSERT INTO ingredients VALUES(2, 'poivrons', 0.8);
INSERT INTO ingredients VALUES(3, 'chorizo', 1.0);
INSERT INTO ingredients VALUES(4, 'lardons', 1.0);
INSERT INTO ingredients VALUES(5, 'aubergines', 0.8);
INSERT INTO ingredients VALUES(6, 'champignons', 1.2);
INSERT INTO ingredients VALUES(7, 'oeufs', 0.8);
INSERT INTO ingredients VALUES(8, 'ananas', 0.05);
INSERT INTO ingredients VALUES(9, 'fromage', 2.0);
INSERT INTO ingredients VALUES(10, 'tomates', 0.8);
INSERT INTO ingredients VALUES(11, 'olives', 1.2);

INSERT INTO pizzas VALUES(1, 'savoyarde', 10, 'napolitaine');
INSERT INTO pizzas_ingredients VALUES(1, 1);
INSERT INTO pizzas_ingredients VALUES(1, 4);
INSERT INTO pizzas_ingredients VALUES(1, 9);

INSERT INTO users VALUES(1, 'valentin', 'valentinvalentinvalentinvalentinvalentinvalentin');
INSERT INTO users VALUES(2, 'gwenael', 'gwenaelgwenaelgwenaelgwenaelgwenaelgwenaelgwenaelgwenael');