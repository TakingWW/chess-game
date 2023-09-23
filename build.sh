#!/bin/sh

javac -d target -sourcepath . src/main/java/com/joao/Main.java src/main/java/com/joao/objects/*.java
if [ $? == 0 ]
then
   java -cp target/ com.joao.Main
fi
