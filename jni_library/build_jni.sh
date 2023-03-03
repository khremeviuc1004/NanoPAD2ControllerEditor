#!/usr/bin/bash


g++ -c -fPIC -fpermissive -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux ./jni_library/midisystem.cpp -o ./target/midisystem.o
g++ -shared -fPIC -fpermissive -o ./target/libnativemidisystem.so ./target/midisystem.o -lc -lasound
