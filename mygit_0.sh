#!/usr/bin/env bash
echo "# SISTEMA INTEGRAL DE ATENCIÓN CIUDADANA Versiòn para Celulares" >> README.md
echo "## WebApp :: SIACentro" >> README.md
echo "### " >> README.md
echo "### Subcoordinación de Modernización de la Coordinación de Modernización e Innovación del Municipio de Centro Tabasco México " >> README.md
echo "#### " >> README.md
echo "#### Por @Ch50Dev" >> README.md
git init
git add README.md
git commit -m "Inicio"
git remote add origin https://github.com/HCarlos/SIACentro.git
git push -u origin master

git commit -m "message" .gitignore

git remote set-url origin https://github.com/HCarlos/SIACentro.git
git config --global user.email "r0@tecnointel.mx"
git config --global user.name "HCarlos"
git config --global color.ui true
git config core.fileMode false
git config --global push.default simple

git checkout master

git status

git add .

git commit -m "Init Commit"

git push -u origin master --force

exit


