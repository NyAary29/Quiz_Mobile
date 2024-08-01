# Chaque collaborateur doit configurer une clé SSH et faire ce qui est dessus :
- Générer une paire de clés SSH
  
  ssh-keygen -t ed25519 -C "votre-email@example.com"
  
- Accédez aux paramètres de leur compte GitHub sous "SSH and GPG keys" et ajoutez la clé publique.
 
- Chaque collaborateur doit vérifier qu'ils peuvent se connecter à GitHub via SSH
  
   ssh -T git@github.com
  
- Cloner le dépôt avec SSH

  git clone git@github.com:NyAary29/Quiz_Mobile.git
  
- Chaque collaborateur doit configurer leur nom et leur email pour les commits

  git config --global user.name "Votre Nom utilisateur"
  git config --global user.email "votre-email@example.com"
  
- Avant de commencer à travailler, les collaborateurs devraient créer une nouvelle branche pour leurs changements

  git checkout -b feature/nom-de-la-fonctionnalite
  
- Ajouter les fichiers modifiés et accepter les fichier modifier

  git add .
  git commit -m "Nom de la fonctionnalite"
  git push origin feature/nom-de-la-fonctionnalite





