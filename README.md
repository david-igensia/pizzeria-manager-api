# Pizzeria Manager API

API REST de gestion du catalogue d'une chaîne de pizzerias, réalisée en Spring Boot dans le cadre du module **Architecture distribuée** (B3 CDA, IPI).

L'API gère les ingrédients et les pizzas, puis calcule pour chaque pizza le prix hors taxe, la TVA à 5,5 % et la marge brute. Elle fournit aussi des statistiques sur l'ensemble du catalogue.

## Technologies

- Java 17
- Spring Boot 4.1.1 (Web MVC, Data JPA)
- Hibernate 7
- SQL Server Express
- Maven
- Postman pour les tests

## Architecture

Le projet suit une architecture n-tiers :

```
Client (Postman)
   ↓  requête HTTP + JSON
Controller   → reçoit la requête, vérifie les données, renvoie le code HTTP
   ↓  DTO
Service      → règles métier et calculs (TVA, marge, statistiques)
   ↓  Entity
Repository   → accès à la base (Spring Data JPA)
   ↓
SQL Server   → tables ingredient, pizza, pizza_ingredient
```

Les entités ne sortent jamais de la couche Service : le client reçoit et envoie uniquement des DTO.

## Structure du projet

```
pizzeria/
├── docs/                      diagramme de classes et diagramme de la base
├── postman/                   collection de tests Postman
├── sql/pizzeria.sql           script de création de la base avec le jeu de données
└── src/main/java/com/ipi/pizzeria/
    ├── controller/            IngredientController, PizzaController
    ├── service/               interfaces et implémentations des services
    ├── repository/            IngredientRepository, PizzaRepository
    ├── entity/                IngredientEntity, PizzaEntity
    ├── dto/                   objets échangés avec le client
    └── PizzeriaApplication    point d'entrée de l'application
```

## Installation

### 1. Prérequis

- JDK 17
- SQL Server (Express suffit) avec l'authentification SQL et le protocole TCP/IP activés sur le port 1433
- Un IDE Java (IntelliJ IDEA conseillé)

### 2. Créer la base de données

Ouvrir `sql/pizzeria.sql` dans SQL Server Management Studio et l'exécuter. Le script crée la base `pizzeria`, les 3 tables et le jeu de données (7 ingrédients, 5 pizzas).

Sans le script, il suffit de créer une base vide nommée `pizzeria` : Hibernate crée les tables au démarrage (`ddl-auto=update`).

### 3. Configurer la connexion

Dans `src/main/resources/application.properties`, adapter l'utilisateur et le mot de passe SQL Server :

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=pizzeria;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

### 4. Lancer l'application

Depuis IntelliJ : lancer la classe `PizzeriaApplication`.

En ligne de commande :

```
mvnw spring-boot:run
```

L'API est disponible sur `http://localhost:8080`.

## Endpoints

### Ingrédients

| Méthode | URL | Description | Codes |
|---|---|---|---|
| POST | `/ingredients` | Créer un ingrédient | 201, 400 |
| GET | `/ingredients` | Lister les ingrédients | 200 |
| GET | `/ingredients/{id}` | Voir un ingrédient | 200, 404 |
| DELETE | `/ingredients/{id}` | Supprimer un ingrédient | 200, 404, 409 |

Un ingrédient encore utilisé dans une pizza ne peut pas être supprimé (409).

### Pizzas

| Méthode | URL | Description | Codes |
|---|---|---|---|
| POST | `/pizzas` | Créer une pizza | 201, 400 |
| GET | `/pizzas` | Lister les pizzas | 200 |
| GET | `/pizzas/{id}` | Voir une pizza | 200, 404 |
| DELETE | `/pizzas/{id}` | Supprimer une pizza | 200, 404 |
| GET | `/pizzas/{id}/financier` | Détail financier d'une pizza | 200, 404 |
| GET | `/pizzas/statistiques` | Statistiques du catalogue | 200 |

La modification (PUT) n'était pas demandée dans le sujet.

## Exemples

Créer une pizza :

```json
POST /pizzas
{ "nom": "Reine", "prixVente": 12.00, "ingredientIds": [1, 2, 3] }
```

Détail financier de la Reine (`GET /pizzas/2/financier`) :

```json
{
  "pizzaId": 2,
  "nomPizza": "Reine",
  "prixVenteTTC": 12.0,
  "prixVenteHT": 11.37,
  "tva5_5": 0.63,
  "coutTotalIngredients": 3.2,
  "margeBrute": 8.17
}
```

## Règles de calcul

Les calculs sont faits dans `PizzaService` :

- prix HT = prix TTC ÷ 1,055
- TVA = prix TTC − prix HT
- coût des ingrédients = somme des prix coûtants
- marge brute = prix HT − coût des ingrédients

Les montants sont arrondis à 2 décimales seulement à la fin, pour ne pas cumuler les erreurs d'arrondi.

## Tests

Importer la collection `postman/Pizzeria Manager API.postman_collection.json` dans Postman (**Import** → choisir le fichier).

Elle contient 4 dossiers :

1. **Ingrédients** : création du jeu de données, lecture, suppression
2. **Pizzas** : création du jeu de données, lecture, suppression
3. **Calculs** : financier de la Reine et de la Quattro Formaggi, statistiques
4. **Erreurs** : cas 400, 404 et 409

Pour recharger le jeu de données sur une base vide, lancer d'abord les requêtes « Créer… » du dossier 1, puis celles du dossier 2.

## Auteur

Oscar David Largo Lopez, B3 Concepteur Développeur d'Applications, IPI Blagnac.
