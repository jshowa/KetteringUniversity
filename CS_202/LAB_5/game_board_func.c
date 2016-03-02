/* Author: Jacob Howarth
 * Class: CS-202, Spring 2009
 * File:   game_board_func.c
 *
 * Description: Function implementations of game_board.h function prototypes
 *
 * Created on May 26, 2009
 */


#include "game_board.h"
#include <ncurses/curses.h>

/* Function: draw_board
 *
 * Description: Draws game board on the console screeb.
 *
 * Parameters:
 *  void
 * Returns:
 *  void
 */
void draw_board() {
    int i, j, k; // iteration variables for loops

    // draw top game board border
    for (i = LEFT_BRD_EDGE; i <= RIGHT_BRD_EDGE; i++) {
        move(TOP_BRD_ROW, i);
        addch(TOP_BOT_SYMBL);
    }

    // draw left game board border
    for (j = TOP_BRD_ROW + 1; j <= BOT_BRD_ROW - 1; j++) {
        move(j, LEFT_BRD_EDGE);
        addch(LEFT_EDGE_SYMBL);
    }

    // draw bottom game board border
    for (k = LEFT_BRD_EDGE; k <= RIGHT_BRD_EDGE; k++) {
        move(BOT_BRD_ROW, k);
        addch(TOP_BOT_SYMBL);
    }

}
