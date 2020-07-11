JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Token.java \
	Tables.java \
	Scanner.java \
	Node.java \
	Parser.java \
	Filter.java \
	VariableList.java \
	SemanticsCheck.java \
	CodeGenerator.java \
	Main.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
