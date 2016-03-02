## -*- Makefile -*-
##
## User: jshowa
## Time: May 28, 2009 7:20:59 PM
## Makefile created by Sun Studio.
##
## This file is generated automatically.
##

OBJ = smsh4.o builtin.o controlflow.o execute2.o process2.o splitline.o stack.o varlib.o
PROG = SmallShell
HEADER = smsh.h stack.h varlib.h

all: ${PROG}

## Target: pong
${PROG}: ${OBJ}
		gcc ${OBJ} -o ${PROG} 
smsh4.o: smsh4.c smsh.h varlib.h
		gcc -c smsh4.c
builtin.o: builtin.c smsh.h varlib.h
		gcc -c builtin.c
execute2.o: execute2.c smsh.h varlib.h
		gcc -c execute2.c 
process2.o: process2.c smsh.h
		gcc -c process2.c
splitline.o: splitline.c smsh.h
		gcc -c splitline.c
controlflow.o: controlflow.c smsh.h stack.h
		gcc -c controlflow.c
stack.o: stack.c stack.h
		gcc -c stack.c
varlib.o: varlib.c varlib.h
		gcc -c varlib.c


#### Clean target deletes all generated files ####
clean:
	rm ${OBJ}
# Enable dependency checking
#.KEEP_STATE:
#.KEEP_STATE_FILE:.make.state.GNU-x86-Linux

