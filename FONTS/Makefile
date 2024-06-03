CLASS_INPUT =	./Domain/*.java \
					./Domain/Operation/*.java \
					./Persistence/*.java \
					./Presentation/Views/*.java \
					./Main.java

CLASS_OUTPUT =	../EXE/

JAR_OUTPUT =	../EXE/

JUNIT_JARS = 	../lib/junit-4.13.1.jar:../lib/hamcrest-core-1.3.jar

JUNIT_TESTS =	./JUnit/*.java

main:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Main.java

jarmain:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Main.java
	jar cvmf ./Main.mf $(JAR_OUTPUT)mainjar.jar -C $(CLASS_OUTPUT) .

executaMain:
	java -classpath $(JAR_OUTPUT) Main

executaMainjar:
	java -jar $(JAR_OUTPUT)mainjar.jar

testCtrlDomainKenken:
	javac -cp $(JUNIT_JARS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(JUNIT_TESTS)
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore JUnit.CtrlDomainKenkenTest

testCtrlDomainUser:
	javac -cp $(JUNIT_JARS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(JUNIT_TESTS)
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore JUnit.CtrlDomainUserTest

testKenken:
	javac -cp $(JUNIT_JARS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(JUNIT_TESTS)
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore JUnit.KenkenTest

testUser:
	javac -cp $(JUNIT_JARS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(JUNIT_TESTS)
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore JUnit.UserTest		


clean:
	rm -r ../EXE/*

distclean:
	rm -r ../EXE/*
