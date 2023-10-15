#!/bin/sh

src=`find . -name *.java`
javac -d target -sourcepath . $src

if [ $? == 0 ]
then
    java -cp target/ Main $1
fi
