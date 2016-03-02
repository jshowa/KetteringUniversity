/* Name: Jacob Howarth and Ocie Lewis
 * Class: CE420 - Microcomputers II
 * Term: Fall 2011
 * Date: 10/27/11
 * Professor Tewolde
 *
 * Description: This program is a basic driver for the keypad
 *				expansion module for the explorer 16 using
 *				the PIC32 microcontroller. The program displays
 *				the ascii value of the key being pressed on the 
 *				keypad on a serial terminal using the UART port.
 * 
 */
 
#include <p32xxxx.h>

// configuration bit settings, Fcy=80MHz, Fpb=40MHz
#pragma config POSCMOD=XT, FNOSC=PRIPLL
#pragma config FPLLIDIV=DIV_2, FPLLMUL=MUL_20, FPLLODIV=DIV_1
#pragma config FPBDIV=DIV_2, FWDTEN=OFF, CP=OFF, BWP=OFF

/** UART CONFIGURATION MACROS **/
#define BRATE 86 //U2BREG setting for ~115,200 baud
#define U_ENABLE 0x8008 //enable UART2, BREGH=1, 1 stop, no parity
#define U_TXRX 0x1400 //enable RX and TX, clear all flags

/** DEBOUNCE TIME IN MS **/
#define DEBOUNCE 96000 		// approx. 12 ms button debounce check

// keypad ascii character value map
unsigned const char char_map[4][4] = { 0x31, 0x32, 0x33, 0x41,
					   			 	   0x34, 0x35, 0x36, 0x42,
					   			 	   0x37, 0x38, 0x39, 0x43,
					   			 	   0x2A, 0x30, 0x23, 0x44,	
					 		   		 };

/* name: rowpress
 * return: unsigned char - ascii code for keypad character pressed
 *						   null char if no button is pressed
 * parameters: int column - keypad column being scanned for key presses
 *
 * Description: Checks for any keypad buttons pressed in the keypad
 *				column specified by the column parameter. 
 *
 *				column = 0 => 1, 4, 7, or *
 *				column = 1 => 2, 5, 8, or 0
 *				column = 2 => 3, 6, 9, or #
 *				column = 3 => A, B, C, or D
 */
unsigned char rowpress(int column)
{
	unsigned int delay = DEBOUNCE;
	
	if (PORTGbits.RG12 == 0) {				// if a button in the first row has been pressed	
		while(delay--);						// debounce delay
		delay = DEBOUNCE;
		while(PORTGbits.RG12 == 0);			// if button is held, wait until released before returning 
			return char_map[0][column];		// the ascii value of the button that was pressed
	}
	if (PORTGbits.RG13 == 0) {				// if a button in the second row has been pressed
		while(delay--);						// debounce delay
		delay = DEBOUNCE;					
		while(PORTGbits.RG13 == 0);			// if button is held, wait until released before returning
			return char_map[1][column];		// the ascii value of the button that was pressed
	}	
	if (PORTGbits.RG14 == 0) {				// if a button in the third row has been pressed
		while(delay--);						// debounce delay
		delay = DEBOUNCE;
		while(PORTGbits.RG14 == 0);			// if button is held, wait until released before returning
			return char_map[2][column];		// the ascii value of the button that was pressed
	}
	if (PORTGbits.RG15 == 0) {				// if a button in the fourth row has been pressed
		while(delay--);						// debounce delay
		delay = DEBOUNCE;
		while(PORTGbits.RG15 == 0);			// if button is held, wait until released before returning
			return char_map[3][column];		// the ascii value of the button that was pressed
	}
	else
		return '\0';						// if no button in any row was pressed, return null char
}

/* name: keyin
 * return: unsigned char - ascii code for keypad character pressed
 *						   null char if no button is pressed
 * parameters: void
 *
 * Description: Scans the keypad for any key that has been pressed
 *				and returns the ascii value of that key or the null
 *				character if no key has been pressed.
 */
unsigned char keyin(void)
{
	
	int ascii_val, column;			// ascii_value stores the ascii code of the key being pressed
									// column is the current keypad column being scanned
	PORTD = 0xE000;					// set PORTD to the first column
	
	for (column = 0; column <= 3; column++) {			// scan through all the columns
		if ( (ascii_val = rowpress(column)) != '\0')	// call rowpress for each column to scan for the 
			return ascii_val;							// row that has the pressed button
		else
			PORTD <<= 1;								// if no button is pressed in that column, go to 
	}													// the next row
	
	return '\0';										// return null if nothing has been pressed
}	

/* name: initKeypad
 * return: void
 * parameters: void
 *
 * Description: Print the keypad entry prompt to the serial
 *				terminal through the UART port.
 */
void printPrompt(void)
{
	char* str = "Enter keys on the keypad...\n";
	_mon_puts(str);
				
}

/* name: initKeypad
 * return: void
 * parameters: void
 *
 * Description: Configure the keypad row and column I/O
 *				directions. The keypad rows are configured as
 *				inputs and the keypad columns are configured
 *				as outputs.
 */
void initKeypad(void) 
{
	TRISGSET = 0xF000;			// configure keypad row as input
	TRISDCLR = 0xF000;			// configure keypad column as output
}

/* name: initUART2
 * return: void
 * parameters: void
 *
 * Description: Initializes the UART2 port used to send
 *				keypad entries to the serial terminal.
 */
void initUART2(void)
{
	U2BRG = BRATE; //intialize the baud rate generator
	U2MODE = U_ENABLE; //intialize the UART module
	U2STA = U_TXRX; //enable TX & RX
}

int main(void) {
	
	initUART2();					// initialize UART	
	initKeypad();					// initiailize keypad I/O
	printPrompt();					// print keypad entry prompt to serial terminal 
	
	while (1) {
		_mon_putc(keyin());			// send ascii value through UART to serial terminal
	}
	
}	
	
	
	