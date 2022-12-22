#!/usr/bin/env bash

git remote set-url origin https://github.com/HCarlos/SIACentro.git

# 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

git config --global user.email "r0@tecnointel.mx"
git config --global user.name "HCarlos"
git config --global color.ui true
git config core.fileMode false
git config --global push.default simple

git checkout master

git status

git rm -r --cached .gitignore
git rm -r --cached .gitattributes
git rm -r --cached *.sh
git rm -r --cached .idea
git rm -r --cached otros

git add .

git commit -m "SIACentro - A006 | Production"

git push -u origin master --force

exit

