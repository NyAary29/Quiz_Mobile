# Chaque collaborateur doit configurer une clé SSH :
# 1  Générer une paire de clés SSH : 
ssh-keygen -t ed25519 -C "votre-email@example.com"
# Etape 2
## Accédez aux paramètres de leur compte GitHub sous "SSH and GPG keys" et ajoutez la clé publique.
# Etape 3
Chaque collaborateur doit vérifier qu'ils peuvent se connecter à GitHub via SSH :
## ssh -T git@github.com
# Etape 4
Cloner le dépôt avec SSH 
## git clone git@github.com:NyAary29/Quiz_Mobile.git

# Etape 5
Chaque collaborateur doit configurer leur nom et leur email pour les commits
## git config --global user.name "Votre Nom"
## git config --global user.email "votre-email@example.com"

# Etape 6
Avant de commencer à travailler, les collaborateurs devraient créer une nouvelle branche pour leurs changements :
## git checkout -b feature/nom-de-la-fonctionnalite

# Etape 7
Ajouter les fichiers modifiés et accepter les fichier modifier 
## git add .
## git commit -m "Nom du changement"

# Etape 8
Pusher 
## git push origin feature/nom-de-la-fonctionnalite
