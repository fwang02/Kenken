CLASS_INPUT =	./Domain/*.java \
					./Domain/Operation/*.java

CLASS_OUTPUT =	../EXE/

JAR_OUTPUT =	../EXE/

JUNIT_JARS = 	../lib/junit-4.13.1.jar:../lib/hamcrest-core-1.3.jar

JUNIT_TESTS =	./JUnit/*.java


jarKenken:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Presentation/DriverKenken.java
	jar cvmf ./Presentation/Kenken.mf $(JAR_OUTPUT)DriverKenken.jar -C $(CLASS_OUTPUT) .
	
jarUser:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Presentation/DriverUser.java
	jar cvmf ./Presentation/User.mf $(JAR_OUTPUT)DriverUser.jar -C $(CLASS_OUTPUT) .

jarDomini:
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Presentation/Driver.java
	jar cvmf ./Presentation/Driver.mf $(JAR_OUTPUT)Driver.jar -C $(CLASS_OUTPUT) .

jars: 
	javac -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Presentation/DriverKenken.java ./Presentation/DriverUser.java ./Presentation/Driver.java
	jar cvmf ./Presentation/Kenken.mf $(JAR_OUTPUT)DriverKenken.jar -C $(CLASS_OUTPUT) .
	jar cvmf ./Presentation/User.mf $(JAR_OUTPUT)DriverUser.jar -C $(CLASS_OUTPUT) .
	jar cvmf ./Presentation/Driver.mf $(JAR_OUTPUT)Driver.jar -C $(CLASS_OUTPUT) .

executaDriver:
	java -jar $(JAR_OUTPUT)Driver.jar

executaDriverUser:
	java -jar $(JAR_OUTPUT)DriverUser.jar

executaDriverKenken:
	java -jar $(JAR_OUTPUT)DriverKenken.jar


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
