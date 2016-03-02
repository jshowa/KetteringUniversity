/* Author: Jacob Howarth
 * Class: CS-202, Spring 2009
 * File:   ball_func.c
 *
 * Description: Function implementations of ball.h function prototypes
 *
 * Created on May 26, 2009
 */

#include "ball.h"
#include <stdlib.h>


/* Function: creat_ball
 *
 * Description: Creates a new ping pong ball with the initial x and y position, speed,
 *              and direction. The x and y speeds are randomized by a random number
 *              generator.
 *
 * Parameters:
 *  void
 * Returns:
 *  @return struct ppball - new ping pong ball
 */
struct ppball creat_ball(void) {
    struct ppball ball;

    ball.y_pos = gen_rand_num(TOP_ROW, BOT_ROW);
    // subtract 20 so ball isn't served to close to the paddle
    ball.x_pos = gen_rand_num(LEFT_EDGE, RIGHT_EDGE - 20);
    ball.y_ttg = ball.y_ttm = gen_rand_num(Y_TTM_LOW, Y_TTM_HIGH);
    ball.x_ttg = ball.x_ttm = gen_rand_num(X_TTM_LOW, X_TTM_HIGH);
    ball.y_dir = 1;
    ball.x_dir = 1;
    ball.symbol = DFL_SYMBOL;

    return ball;
};

/* Function: gen_rand_num
 *
 * Description: Generates a random number for ping pong ball x and y time
 *              to move (speed) values. The number is generated within a
 *              range between lowerbound and upperbound.
 *
 * Parameters:
 *  @param lowerbound - lower bound of number range for ball x and y ttm
 *  @param upperbound - upper bound of number range for ball x and y ttm
 * Returns:
 *  @return unsigned long - random number
 */

unsigned long gen_rand_num(int lowerbound, int upperbound) {
    unsigned long rand_num = 0;
    do {
        rand_num = (random() % upperbound); // constrain the random
    } while(rand_num < lowerbound);         // num gen between upper and lower bound
    return rand_num;                        // defined in ball.h
}
