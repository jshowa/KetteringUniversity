## -*- Makefile -*-
##
## User: jshowa
## Time: May 28, 2009 7:20:59 PM
## Makefile created by Sun Studio.
##
## This file is generated automatically.
##

OBJ = Main.o game_board_func.o ball_func.o paddle_func.o
PROG = pong
HEADER = ball.h game_board.h paddle.h

all: ${PROG}

## Target: pong
${PROG}: ${OBJ}
		gcc -Wl,--enable-auto-import ${OBJ} -o ${PROG} -lncurses
Main.o: Main.c ${HEADER}
		gcc -c -Wl,--enable-auto-import Main.c -lncurses
game_board_func.o: game_board_func.c game_board.h
		gcc -c -Wl,--enable-auto-import game_board_func.c -lncurses
ball_func.o: ball_func.c ball.h
		gcc -c -Wl,--enable-auto-import ball_func.c -lncurses
paddle_func.o: paddle_func.c paddle.h
		gcc -c -Wl,--enable-auto-import paddle_func.c -lncurses

#### Clean target deletes all generated files ####
clean:
	rm ${OBJ}
# Enable dependency checking
#.KEEP_STATE:
#.KEEP_STATE_FILE:.make.state.GNU-x86-Linux

