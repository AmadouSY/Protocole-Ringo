JAVAC=javac
SOURCES = $(wildcard *.java)
CLASSES = $(SOURCES:.java=.class)
MAIN = ApplicationTest
CHECK = Check
JVM = java

all: $(CLASSES)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $< -Xlint:unchecked 

run: $(MAIN).class
	$(JVM) $(MAIN)

check: $(CHECK).class
	$(JVM) $(CHECK)
