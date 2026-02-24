SOURCES = $(wildcard com/craftinginterpreters/lox/*.java)
CLASSES = $(SOURCES:.java=.class)
all: $(CLASSES)
%.class: %.java
	javac $(SOURCES)
run: all
	java com.craftinginterpreters.lox.Lox
run-file: all
	java com.craftinginterpreters.lox.Lox $(FILE)
clean:
	rm -f com/craftinginterpreters/lox/*.class
