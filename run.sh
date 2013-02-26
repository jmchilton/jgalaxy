#!/bin/bash

mvn exec:java -Dexec.mainClass="com.github.jmchilton.jgalaxy.JGalaxy" -Dexec.args="$@" -Dexec.classpathScope=runtime

