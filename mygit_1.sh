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

git commit -m "SIACentro - A001 | Production"

git push -u origin master --force

exit
