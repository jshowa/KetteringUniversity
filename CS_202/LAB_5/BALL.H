/* Author: Jacob Howarth
 * Class: CS-202, Spring 2009
 * File:   ball.h
 *
 * Description: Header file that defines the ping pong ball structure
 *              some constants and function prototypes that describe
 *              the settings of the ball.
 *
 * Created on May 26, 2009, 4:09 PM
 */

#ifndef _BALL_H
#define	_BALL_H

#ifdef	__cplusplus
extern "C" {
#endif

/* ball.h			*/

/* some settings for the game	*/

#define	BLANK		' '
#define	DFL_SYMBOL	'O'
#define	TOP_ROW		5               /* ball x and y coordinate boundaries */
#define	BOT_ROW 	20
#define	LEFT_EDGE	10
#define	RIGHT_EDGE	70
#define	TICKS_PER_SEC	50		/* affects speed	*/

#define	X_TTM_LOW       1               /* x speed lower and upper bounds for rand gen */
#define X_TTM_HIGH      10
#define Y_TTM_LOW       1               /* y speed lower and upper bounds for rand gen */
#define	Y_TTM_HIGH      10

#define X_TTM           5               /* default x timer      */
#define Y_TTM           8               /* default y timer      */

/** the ping pong ball **/

struct ppball {
		int	y_pos, x_pos, // x and y position
			y_ttm, x_ttm, // x and y timer
			y_ttg, x_ttg, // x and y timer movement
			y_dir, x_dir; // x and y direction
		char	symbol ; // ball symbol

	} ;

/** ping pong ball functions **/
struct ppball creat_ball(void);
void set_new_speed(struct ppball *);
unsigned long gen_rand_num(int lowerbound, int upperbound);


#ifdef	__cplusplus
}
#endif

#endif	/* _BALL_H */

