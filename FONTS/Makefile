CLASS_INPUT =	./Domain/*.java \
				./Domain/Operation/*.java \
				./Persistence/*.java \
				./Presentation/*.java \
				./Presentation/Views/*.java \
				./Main.java

CLASS_OUTPUT =	../EXE/

JAR_OUTPUT =	../EXE/

main:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Main.java

kenkenprop:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Main.java
	jar cvmf ./Main.mf $(JAR_OUTPUT)KenKenPROP.jar -C $(CLASS_OUTPUT) .

executaMain:
	java -classpath $(JAR_OUTPUT) Main

executaKenkenprop:
	java -jar $(JAR_OUTPUT)KenKenPROP.jar

	
clean:
	rm -r ../EXE/*

distclean:
	rm -r ../EXE/*
