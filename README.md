# 🚀 Lancer Votre Projet

Pour configurer correctement votre projet, vous devrez effectuer des modifications dans le fichier `application.conf`. Veuillez suivre les instructions ci-dessous pour une mise en route sans tracas.

## 📁 Où se trouve le fichier `application.conf`?

Ce fichier est situé dans le répertoire `\src\main\resources`. Ouvrez ce répertoire et cherchez `application.conf`.

## 🛠️ Comment modifier `application.conf`?

Vous devrez mettre à jour les chemins des fichiers d'entrée et de sortie, spécifiquement les entrées `input-file`, `output-json-file` et `output-csv-file`.

Ces modifications permettront à votre projet de localiser correctement les fichiers nécessaires et de générer les fichiers de sortie au bon endroit.

## 🔎 Exemple de modification

Voici à quoi doit ressembler le fichier :

```conf
application {
  name = "FunProg"
  name = ${?APP_NAME} # Pour surcharger la valeur par un variable d'environnement

  input-file = "/chemin/vers/votre/fichier/d'entrée"
  input-file = ${?INPUT_FILE}

  output-json-file = "/chemin/vers/votre/fichier/de/sortie.json"
  output-json-file = ${?OUTPUT_JSON_FILE}

  output-csv-file = "/chemin/vers/votre/fichier/de/sortie.csv"
  output-csv-file = ${?OUTPUT_CSV_FILE}
}
```

