#!/bin/sh
java -Djava.security.policy=./policy.all -classpath ./src:./build/classes:./lib/antlr-3.1.1-runtime.jar:./lib/asm-all-3.3.1.jar:./lib/bcel.jar:./lib/db4o-8.0.249.16098-all-java5.jar:./lib/jdo2-api-2.1.jar:./lib/jta-1.1.jar:./lib/vodjdo.jar Main db4o
