/* Author: Jacob Howarth
 * Class: CS-202, Spring 2009
 * File:   paddle_func.c
 *
 * Description: Function implementations of paddle.h function prototypes
 *
 * Created on May 27, 2009
 */
#include "paddle.h"
#include <ncurses/curses.h>

// private paddle structure to be used only in this file
static struct pppaddle paddle;

/* Function: paddle_init
 *
 * Description: Creates a new paddle with initial x,y coordinates for the
 *              top and bottom of the paddle and draws the paddles on the
 *              screen.
 *
 * Parameters:
 *  void
 * Returns:
 *  void
 */
void paddle_init(void) {
    int i;

    paddle.pad_top = Y_TOP_INIT; // initialize paddle to board middle at right border
    paddle.pad_bot = Y_BOT_INIT;

    for (i = paddle.pad_top; i <= paddle.pad_bot; i++) {
        mvaddch(i, X_CORD, PAD_SYMBL); // draw paddle
    }
}

/* Function: paddle_up
 *
 * Description: Moves the paddle upward.
 *
 * Parameters:
 *  void
 * Returns:
 *  void
 */
void paddle_up(void) {
    int curr_pad_top = paddle.pad_top; // store paddles current top and bottom
    int curr_pad_bot = paddle.pad_bot; // coordinates so it can be erased

    // if the paddles top coordinates are below it's boundary
    if (paddle.pad_top > Y_TOP_BOUND) {
        paddle.pad_top--; // move paddle up
        paddle.pad_bot--;
        paddle_redraw(curr_pad_top, curr_pad_bot); // redraw paddle
    }
    refresh();
}

/* Function: paddle_down
 *
 * Description: Move the paddle downward.
 *
 * Parameters:
 *  void
 * Returns:
 *  void
 */
void paddle_down(void) {
    int curr_pad_top = paddle.pad_top;
    int curr_pad_bot = paddle.pad_bot;

    if (paddle.pad_bot < Y_BOT_BOUND) {
        paddle.pad_top++;
        paddle.pad_bot++;
        paddle_redraw(curr_pad_top, curr_pad_bot);
    }
    refresh();
}

/* Function: paddle_contact
 *
 * Description: checks if the ping pong ball has contacted the paddle
 *
 * Parameters:
 *  @param int x - current x coordinate of ping pong ball
 *  @param int y - current y coordinate of ping pong ball
 * Returns:
 *  @return int - the area at which the ball contacted the paddle
 *                  HIT_PAD_CENTER: ball hits middle of paddle
 *                  HIT_PAD_BOT: ball intercepted by bottom of paddle
 *                  HIT_PAD_TOP: ball intercepted by top of paddle
 */
int paddle_contact(int x, int y) {
    // if the ball is between the top and bottom of the paddle and
    // is one less than the paddles column, return as hitting the center
    if ((y <= paddle.pad_bot && y >= paddle.pad_top) && x == X_CORD - 1)
        return HIT_PAD_CENT;

    // if the ball hits the paddles bottom (i.e. ball intercepted by paddles bottom)
    // return as hitting the bottom
    if ((y == paddle.pad_bot + 1) && x == X_CORD)
        return HIT_PAD_BOT;

    // if the ball hits the paddles top (i.e. ball intercepted by paddles top)
    // return as hitting the top
    if ((y == paddle.pad_top - 1) && x == X_CORD)
        return HIT_PAD_TOP;

    return -1; // no paddle contact
}

/* Function: paddle_redraw
 *
 * Description: Redraws paddle at new coordinates given previous coordinates.
 *              This function is private to paddle_func.c
 *
 * Parameters:
 *  @param int curr_top - current position of paddles top
 *  @param int curr_bot - current position of paddles bottom
 * Returns:
 *  void
 */
static void paddle_redraw(int curr_top, int curr_bot) {
    int i, j; // for loop iterator variables

    for (j = curr_top; j <= curr_bot; j++) { // erase the paddle at it's current coordinates
        mvaddch(j, X_CORD, PAD_BLANK);
    }

    for (i = paddle.pad_top; i <= paddle.pad_bot; i++) { // redraw paddle at new coordinates
        mvaddch(i, X_CORD, PAD_SYMBL);
    }
    refresh();
    
}
