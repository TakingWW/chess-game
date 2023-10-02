#!/bin/sh

# etags $(find -type f -name "*.java") # to use when you need to achange the TAGS file

src=`find . -name *.java`
javac -d target -sourcepath . $src

if [ $? == 0 ]
then
    java -cp target/ Main $1
fi
