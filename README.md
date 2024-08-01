# Chaque collaborateur doit configurer une clé SSH et faire ce qui est dessus :
- Générer une paire de clés SSH
- Accédez aux paramètres de leur compte GitHub sous "SSH and GPG keys" et ajoutez la clé publique.
- Chaque collaborateur doit vérifier qu'ils peuvent se connecter à GitHub via SSH
- Cloner le dépôt avec SSH
- Chaque collaborateur doit configurer leur nom et leur email pour les commits
- Avant de commencer à travailler, les collaborateurs devraient créer une nouvelle branche pour leurs changements
- Ajouter les fichiers modifiés et accepter les fichier modifier
  
``bash
ssh-keygen -t ed25519 -C "votre-email@example.com"
ssh -T git@github.com
git clone git@github.com:NyAary29/Quiz_Mobile.git
git config --global user.name "Votre Nom utilisateur"
git config --global user.email "votre-email@example.com"
git checkout -b feature/nom-de-la-fonctionnalite
git add .
git commit -m "Nom de la fonctionnalite"
git push origin feature/nom-de-la-fonctionnalite
