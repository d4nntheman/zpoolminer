JCC = javac
JFLAGS = 
CLASSPATH = -classpath ".:./lib/*"

all: ZPoolMiner.class AlgoStats.class

ZPoolMiner.class: ZPoolMiner.java AlgoStats.java BenchMark.java Algo.java
	$(JCC) $(JFLAGS) $(CLASSPATH) ZPoolMiner.java AlgoStats.java BenchMark.java Algo.java

clean: 
	$(RM) *.class

