/* Author: Jacob Howarth
 * Class: CS-202, Spring 2009
 * File:   paddle.h
 *
 * Description: Constants and function prototypes used to define the paddle
 *              object.
 *
 * Created on May 27, 2009, 7:14 PM
 */

#ifndef _PADDLE_H
#define	_PADDLE_H

#ifdef	__cplusplus
extern "C" {
#endif

#define X_CORD          70                  /* fixed paddle column location */
#define Y_TOP_INIT      9                   /* y coord for paddle top and bottom */
#define Y_BOT_INIT      14
#define Y_TOP_BOUND     5                   /* paddle movement boundary coordinates */
#define Y_BOT_BOUND     20
#define PAD_SYMBL       '#'
#define PAD_BLANK       ' '
#define HIT_PAD_CENT     0                  /* return constants for area where ball contacts paddle */
#define HIT_PAD_BOT      1                  /* used in paddle_contact function in paddle_func.c */
#define HIT_PAD_TOP      2

/* paddle structure */
struct pppaddle {
    int pad_top, pad_bot; // coordinates of top and bottom of paddle
};

/***FUNCTION PROTOTYPES***/
void paddle_init(void);
void paddle_up(void);
void paddle_down(void);
int paddle_contact(int x, int y);
static void paddle_redraw(int curr_top, int curr_bot);

#ifdef	__cplusplus
}
#endif

#endif	/* _PADDLE_H */

