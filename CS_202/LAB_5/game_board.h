/* Author: Jacob Howarth
 * File:   game_board.h
 *
 * Description: Constants and function protoypes used to define the
 *              game board.
 *
 * Created on May 26, 2009, 4:14 PM
 */

#ifndef _GAME_BOARD_H
#define	_GAME_BOARD_H

#ifdef	__cplusplus
extern "C" {
#endif

#define LEFT_EDGE_SYMBL '|'
#define TOP_BOT_SYMBL   '-'
#define TOP_BRD_ROW     4       /* x and y boundary coordinates for game board edges */
#define BOT_BRD_ROW     21
#define LEFT_BRD_EDGE   9
#define RIGHT_BRD_EDGE  70

#define CENTER_BRD_Y    13      /* x and y coordinates for boards center */
#define CENTER_BRD_X    35

/***FUNCTION PROTOTYPES***/
void draw_board(void);


#ifdef	__cplusplus
}
#endif

#endif	/* _GAME_BOARD_H */

