#!/bin/sh

TARGET=$1;
COMPILE_MODE=$2;

echo "Compiling file $TARGET in mode $COMPILE_MODE"

echo "Compile One";
pdflatex -output-director build $TARGET.tex >> /dev/null;

if [ "$COMPILE_MODE" = "bib" ]
then
	cd build
		echo "Bibliography";
		bibtex $TARGET.aux >> /dev/null
	cd ../

	echo "Compile Three";
	pdflatex -output-director build $TARGET.tex >> /dev/null

	echo "Compile Four";
	pdflatex -output-director build $TARGET.tex >> /dev/null
fi