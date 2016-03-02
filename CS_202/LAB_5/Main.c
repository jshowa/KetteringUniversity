/*  Author: Jacob Howarth
 *  Class: CS-202, Spring 2009
 *
 *  File: Main.c
 *  Description: This program is a 2d, one-sided, console version of the
 *               classic game pong. This file contains the user input
 *               functionality, the ball movement, and the parameters
 *               needed to start and stop the game.
 *
 *  Created May 25, 2009.
 */

#include	<ncurses/curses.h>
#include	<signal.h>
#include        <sys/time.h>
#include	"ball.h"
#include        "game_board.h"
#include        "paddle.h"

/***CONSTANT MACROS***/
#define LOSE          -1 // returned when ball falls out of bounds
#define BOUNCE         1 // returned when ball hits board wall or paddle
#define NO_CONTACT     0 // returned if the ball is not touching anything

/***GLOBAL VARIABLES***/
struct ppball the_ball; // ping pong ball
static int balls_left = 3; // number of balls available before game over

/***FUNCTION PROTOTYPES***/
void ball_move(int);
void set_up();
void wrap_up();
int set_ticker(int);

/* Function: main
 * Description: Where program begins execution. Main initializes
 *              the objects and seeds the random num generator
 *              for the creat_ball() function in ball_func.c. It
 *              also runs the main program loop for the function
 *              which processes user input.
 * Parameters:
 *  void
 * Returns:
 *  @return int - exit status of program, 0 for success, -1 for failure
 */

int main()
{
	int	c; // char used to store user keyboard input during game

	set_up(); // initialize objects
        srandom(getpid()); // IMPORTANT: SEED RAND NUM GEN ONLY ONCE TO PREVENT DUPLICATION!
	while (balls_left > 0 && (c = getchar()) != 'Q' ) {
            if (c == 'j')
                paddle_down();
            if (c == 'k')
                paddle_up();
	}

        if (balls_left <= 0) { // when no more balls are left, print game over on screen
            mvaddstr(CENTER_BRD_Y, (CENTER_BRD_X - 5), "GAME OVER");
            refresh();
        }

        if (c != 'Q') { // if c is not equal to Q, meaning the game has been played thru
            while ((c = getchar()) != 'Q')
                refresh(); // redraw screen until Q is pressed
        }

	wrap_up(); // quit process
}

/* Function: set_up
 * Description: Initializes the objects (ball, board, paddle) for the
 *              game and draws it on the console screen. It also
 *              initializes the signals and the timers for the
 *              signals.
 * Parameters:
 *  void
 * Returns:
 *  void
 */
void set_up() {
	
        the_ball = creat_ball(); // initialize ball with random position
                                // and speed (ball_func.c)

	initscr();
	noecho(); // turn off echo
	crmode(); // turn on canonical input

        draw_board(); // draw game board (game_board_func.c)
        paddle_init();  // draw paddle (paddle_func.c)

	signal( SIGINT , SIG_IGN ); // ignore interupt (Crtl-C) signal
	mvaddch( the_ball.y_pos, the_ball.x_pos, the_ball.symbol  );
        move(LINES-1, COLS-1);
	refresh();

	signal( SIGALRM, ball_move ); // create alarm signal with ball_move handler
	set_ticker( 1000 / TICKS_PER_SEC );	/* send millisecs per tick */
}

/* Function: wrap_up
 * Description: Clears game window and stops alarm by setting timer to 0.
 *              This function is only called when the user presses 'Q'
 *              on the keyboard.
 * Parameters:
 *  void
 * Returns:
 *  void
 */
void wrap_up() {

	set_ticker( 0 ); // stop timer

	endwin();		/* put window back to normal	*/
}

/* Function: ball_move
 * Description: Handler for alarm signal. Causes the ball to move
 *              every time the timer decrements down to 0. Checks
 *              the balls time to move and time to go values and
 *              moves the ball one position at a time during each
 *              call.
 * Parameters:
 *  @param signum - integer representing the signal number that corresponds
 *                  to the type of signal that called the ball_move function
 * Returns:
 *  void
 */
void ball_move(int signum) {

        /***************************VARIABLE TABLE*************************
         * int y_cur - temporary variable to store balls current y position
         * int x_cur - temporary variable to store balls current x position
         * int moved - boolean value used to check and see if the ball
         *             moved and whether it needs updating
         ******************************************************************
         */
	int	y_cur, x_cur, moved;

	signal( SIGALRM , SIG_IGN );		/* dont get caught now 	*/
	y_cur = the_ball.y_pos ;		/* old spot		*/
	x_cur = the_ball.x_pos ;
	moved = 0 ;

	if ( the_ball.y_ttm > 0 && the_ball.y_ttg-- == 1 ){
		the_ball.y_pos += the_ball.y_dir ;	/* move	*/
		the_ball.y_ttg = the_ball.y_ttm  ;	/* reset*/
		moved = 1;
	}

	if ( the_ball.x_ttm > 0 && the_ball.x_ttg-- == 1 ){
		the_ball.x_pos += the_ball.x_dir ;	/* move	*/
		the_ball.x_ttg = the_ball.x_ttm  ;	/* reset*/
		moved = 1;
	}

	if ( moved ){
		mvaddch( y_cur, x_cur, BLANK ); // erase ball at prev position and redraw to new position
		mvaddch( the_ball.y_pos, the_ball.x_pos, the_ball.symbol );
		if ((bounce_or_lose(&the_ball)) == LOSE) { // check bounce_or_lose
                    balls_left--; // if lose, ball is out of play decrement remaining balls
                }
		move(LINES-1,COLS-1);
		refresh();
	}
	signal( SIGALRM, ball_move);		/* for unreliable systems */

}

/* Function: bounce_or_lose
 * Description: Checks the position of the ball relative to the position
 *              of the game board borders and the paddle and changes the
 *              balls direction appropriately. If the ball comes in contact
 *              with the paddle, the speed of the x and y time to move values
 *              are changed by a random amount to introduce uncertainty.
 * Parameters:
 *  @param ppball *bp - a ping pong ball struct pointer
 * Returns:
 *  @return int - can be one of three constants:
 *                  BOUNCE: the ball has bounced off a game board border or the paddle
 *                  NO_CONTACT: the ball is located in the board
 *                  LOSE: the ball is out of play
 */
int bounce_or_lose(struct ppball *bp) {
	int	return_val = NO_CONTACT ;

	if ( bp->y_pos == TOP_ROW ) { // if ball hits top border, change direction to downward
		bp->y_dir = 1;
		return_val = BOUNCE;
	} else if ( bp->y_pos == BOT_ROW ) { // if ball hits bottom border, change direction to upward
		bp->y_dir = -1;
	       	return_val = BOUNCE;
	}
	if ( bp->x_pos == LEFT_EDGE ) { // if ball hits left border, change direction to right
		bp->x_dir = 1;
	       	return_val = BOUNCE;
	} 

        // if ball hits the paddles center...
        if ( (paddle_contact(bp->x_pos, bp->y_pos)) == HIT_PAD_CENT) {
                bp->x_dir = -1; // change the direction to left
                bp->x_ttm = gen_rand_num(X_TTM_LOW, X_TTM_HIGH); // change the x and y components
                bp->y_ttm = gen_rand_num(Y_TTM_LOW, Y_TTM_HIGH); // by a random speed
                return_val = BOUNCE;
	} // if ball is intercepted at bottom of paddle...
        else if ((paddle_contact(bp->x_pos, bp->y_pos)) == HIT_PAD_BOT) {
            bp->y_dir = 1; // change direction to down and left
            bp->x_dir = -1;
            bp->x_ttm = gen_rand_num(X_TTM_LOW, X_TTM_HIGH); // change speed components randomly
            bp->y_ttm = gen_rand_num(Y_TTM_LOW, Y_TTM_HIGH);
            return_val = BOUNCE;
        } // if ball is intercepted at top of paddle...
        else if ((paddle_contact(bp->x_pos, bp->y_pos)) == HIT_PAD_TOP) {
            bp->y_dir = -1; // change direction to up and left
            bp->x_dir = -1;
            bp->x_ttm = gen_rand_num(X_TTM_LOW, X_TTM_HIGH); // change speed components randomly
            bp->y_ttm = gen_rand_num(Y_TTM_LOW, Y_TTM_HIGH);
            return_val = BOUNCE;
        }

        // if the ball is not in contact with the paddle, and falls to far off the right edge
        if (bp->x_pos > RIGHT_EDGE) {
                mvaddch(bp->y_pos, bp->x_pos, BLANK); // erase ball
                if (balls_left <= 3 && balls_left > 1) // create new ball and reserve as long
                    the_ball = creat_ball();            // as there are balls left
	       	return_val = LOSE;
        }

	return return_val;
}

/* Function: set_ticker
 *
 * Description: Sets the balls x and y interval timers. The timer values
 *              cannot be greater than or equal to 1000 milliseconds or
 *              the timers will not work and the ball won't move.
 *
 * Parameters:
 *  @param int n_msecs - milliseconds per tick to set the timer too
 * Returns:
 *  @return int - success of setting the timer, 0 if success, -1 if failure
 */
int set_ticker( int n_msecs ) {
        /************************VARIABLE TABLE***********************
         * struct itimerval new_timeset - A interval timer structure
         * long n_secs - number of seconds per tick
         * long n_usecs - number of microseconds per tick
         *************************************************************
         */
        struct itimerval new_timeset;
        long    n_sec, n_usecs;

        n_sec = n_msecs / 1000 ; // set the interval timer seconds
        n_usecs = ( n_msecs % 1000 ) * 1000L ; // and microseconds

        new_timeset.it_interval.tv_sec  = n_sec;        /* set reload  */
        new_timeset.it_interval.tv_usec = n_usecs;      /* new ticker value */
        new_timeset.it_value.tv_sec     = n_sec  ;      /* store this   */
        new_timeset.it_value.tv_usec    = n_usecs ;     /* and this     */

	return setitimer(ITIMER_REAL, &new_timeset, NULL); // set processes real timer
}
