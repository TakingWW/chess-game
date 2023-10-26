#!/bin/sh

Off="\e[0m"
Green="\e[1;32m"
Red="\e[1;30m"
Yellow="\e[1;33m"

echo -e "Building jar file under ${Yellow}./target/output${Off} directory"
src=`find . -name *.java`
javac -d target -sourcepath . $src

if [ $? == 0 ]
then
	cd target
	mkdir -p output
	CLASS=`find . -name "*.class"`
	jar cfe output/chess-game.jar Main $CLASS
	cd ..
#     java -cp target/ Main $1
fi

if [ $? == 0 ]
then
	echo -e "Build ${Green}successfull${Off}"
fi
