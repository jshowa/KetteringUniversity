/* Name: Jacob Howarth and Ocie Lewis
 * Class: CE420 - Microcomputers II
 * Date: 11/7/11
 * Term: Fall 2011
 * Professor: Tewolde
 *
 * Description: This program represents a simple kitchen timer using
 *				the Explorer 16 board, keypad expansion board, and
 *				PIC32 processor.
 *
 *				The timer has two modes:
 *					1) SET mode allows you to set the value of the timer.
 * 					2) RUN mode starts the timer.
 *
 *				The two modes are toggled on/off using the # key on the keypad.
 *				
 *				SET mode: The time is input using the keypad
 *				and displayed on the 7 segment displays. The timer
 *				only accepts digits 0-9 on the keypad. Keys A-D and *
 *				are not used and should not be displayed.
 *
 *				RUN mode: When # key is pressed, the timer counts
 *				down every second until it reaches 00:00. When the timer
 *				reaches 00:00 the 7seg display blinks on and off every second
 *				until the user transistions back to set mode and sets a
 *				new time. 
 *
 * 				The timer is implemented using the core timer interrupt.
 */


/***PREPROCESSOR DECLARATIONS***/

#include <plib.h>

// configuration bit settings, Fcy=80MHz, Fpb=40MHz
#pragma config POSCMOD=XT, FNOSC=PRIPLL
#pragma config FPLLIDIV=DIV_2, FPLLMUL=MUL_20
#pragma config FPLLODIV=DIV_1, FPBDIV=DIV_2
#pragma config FWDTEN=OFF, CP=OFF, BWP=OFF

// Configure CPU frequency and core timer frequency (1/2 fclk)
#define CPU_FREQUENCY 80000000
#define TIMER_FREQUENCY (CPU_FREQUENCY/2)
#define MILLI_SECOND (TIMER_FREQUENCY/1000)		// interrupt every 1 ms

#define DEBOUNCE_TIME 12 // triggered by intrpt/1ms timer.

/***FUNCTION PROTOTYPE DECLARATIONS***/

/* name: intrpt_setup
 * return: void
 * parameters: none
 *
 * Description: Configures core timer interrupt and initializes system
 *				interrupt. Starts the core timer to interrupt every 1 ms.
 */
void intrpt_setup(void);

/* name: port_setup
 * return: void
 * parameters: none
 *
 * Description: Configures I/O ports. Specifically, 
 *				PORTA = all outputs
 *				PORTG = most significant nibble (MSN) as inputs for keypad
 *						least significant nibble (LSN) as outputs to control 7seg anodes
 *				PORTD = MSN as inputs for checking columns on keypad
 *				
 *				Turn on the right most 7seg display.
 */
void port_setup(void);

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
unsigned char rowpress(int column);

/* name: keyin
 * return: unsigned char - ascii code for keypad character pressed
 *						   null char if no button is pressed
 * parameters: void
 *
 * Description: Scans the keypad for any key that has been pressed
 *				and returns the ascii value of that key or the null
 *				character if no key has been pressed.
 *
 *				MINOR CHANGE: When button * is pressed, a null char
 *				is returned because * is an invalid value for the timer
 *				and should not be displayed on the 7seg.
 *				Also, the 4th column of the keypad containing letters
 *				A-D are not scanned because letters A-D are invalid values
 *				for the timer.
 *				
 */
unsigned char keyin(void);


/* name: debounce
 * return: void
 * parameters: none
 *
 * Description: This function blocks when a button is pressed until
 *				the core timer interrupt occurs 12 times (12 interrupts = 12 ms)
 *				12 ms is the debounce time for each key press on the keypad.
 */
void debounce(unsigned int delay);

// keypad button character value map A-D and 0x2A are not used
unsigned const char char_map[4][4] = { 1, 2, 3, 'A',
					   			 	   4, 5, 6, 'B',
					   			 	   7, 8, 9, 'C',
					   			 	   0x2A, 0x30, 0x23, 'D',	
					 		   		 };

// keypad button to 7seg display value map
// Each hex value lights the corresponding LEDs on the 7seg to show the key that was pressed
// which is used as an index into this array (i.e. pressing 0 on the keypad chooses 0xC0 which displays
// 0 on the 7seg display).
unsigned char const keypad_to_7seg[10] = {0xC0, 0xF9, 0xA4, 0xB0, 0x99, 0x92, 0x82, 0xF8, 0x80, 0x90};

unsigned int delayCounter = DEBOUNCE_TIME; 		  // counter for button press handled in interrupt
unsigned char debounce_flag = 1;	  			  // clear debounce flag in interrupt when 12 ms passes
unsigned char setrun_flag = 1;	  				  // 1 is SET mode and 0 is RUN mode 
unsigned char keyPressValue = 0xC0;				  // stores the return value from keyin indicating the last key pressed
unsigned char seven_seg_vals[4] = {0, 0, 0, 0};	  // array used to hold the 7seg display values for each 7seg
unsigned int intrpt_ctr = 0; 					  // used to count the number of times the interrupt occurs (used for 
												  // adjusting the core timer to interrupt every second during count down)
unsigned int alarm_flg = 0; 					  // alarm flag used to blink the 7seg displays when the timer reaches 00:00
												  // 0 = no alarm, 1 = alarm

int main(void)
{
	port_setup();
	intrpt_setup();   

	while(1) {

		keyPressValue = keyin();				// wait for a key to be pressed and get its value
			
		if (setrun_flag) {						// SET mode
		
			if (keyPressValue != '\0' && keyPressValue != 0x23) {	// if a key has been pressed and it is not keys A-D and #
				if (keyPressValue == 0x30)
					keyPressValue = 0;
				seven_seg_vals[3] = seven_seg_vals[2];				// shift the newly pressed value into the right
				seven_seg_vals[2] = seven_seg_vals[1];				// shifting over each other digit in the process
				seven_seg_vals[1] = seven_seg_vals[0];
				seven_seg_vals[0] = keyPressValue;
			}
			
			if (keyPressValue == 0x23)								// If # key is pressed, switch to RUN mode
				setrun_flag = 0;		
		}
		else {
			if (keyPressValue == 0x23) {							// If # is pressed again, switch back to SET mode
				setrun_flag = 1;
				alarm_flg = 0;										// reset the alarm flag if the timer reaches 00:00
			}
		}
	
	}
	    
}

void __ISR(_CORE_TIMER_VECTOR, ipl3) 
CoreTimerIntHandler(void)
{
	// Clear core timer interrupt
	mCTClearIntFlag();
	// Update core timer
	UpdateCoreTimer(MILLI_SECOND);


	if (alarm_flg == 0) {	// If the alarm has not been triggered (i.e. the timer is not 00:00
		
		PORTG <<= 1;		// turn on all the 7seg and write their values every 1 ms		
	
		if ((PORTGbits.RG0 == 0) && (PORTGbits.RG1 == 0) && (PORTGbits.RG2 == 0) && (PORTGbits.RG3 == 0))
			PORTG += 1;	
				
		if (PORTGbits.RG0 == 1)
			PORTA = keypad_to_7seg[seven_seg_vals[0]];				// The right most 7seg displays the least significant digit (ones place of seconds)
		if (PORTGbits.RG1 == 1)
			PORTA = keypad_to_7seg[seven_seg_vals[1]] + 0x80;		// The second to right most 7seg displays the next digit (tens place of seconds)
		if (PORTGbits.RG2 == 1)									
			PORTA = keypad_to_7seg[seven_seg_vals[2]] + 0x80;		// The second to left most 7seg displays the next digit (ones place of minuted)
		if (PORTGbits.RG3 == 1)
			PORTA = keypad_to_7seg[seven_seg_vals[3]];				// The right most 7seg displays the most significant digit (tens place of minutes)
	}	
	
	if (setrun_flag == 0) {
		intrpt_ctr++;				// If the timer is in RUN mode, increment the interrupt counter 1000 times (1000 interrupts = 1 s)
		
		if (intrpt_ctr == 1000) {
	
			if (seven_seg_vals[0]!= 0 || seven_seg_vals[1]!= 0 || seven_seg_vals[2]!= 0 || seven_seg_vals[3]!= 0) {		// If the timer is not 00:00
				
				if (seven_seg_vals[0] == 0) {		 
					if (seven_seg_vals[1] != 0) {	// if the ones digit of the seconds (right most 7seg) is 0 but the tens digit isn't	
						seven_seg_vals[1]--;		// decrement the tens digit by 1
						seven_seg_vals[0] = 9;		// display 9 in the right most 7seg
					}
					else if (seven_seg_vals[1] == 0) {	
						if (seven_seg_vals[2] != 0) {	// if the tens digit of the seconds (second to right most 7seg) is 0 but the ones digit of the minutes isn't
							seven_seg_vals[2]--;		// decrement the ones digit of the minutes by 1
							seven_seg_vals[1] = 5;		// set the seconds to 59 which is equivalent to 1 minute.
							seven_seg_vals[0] = 9;
						}	
						else if (seven_seg_vals[2] == 0) {	
							if (seven_seg_vals[3] != 0) {	// if the ones digit of the minutes (second to left most 7seg) is 0 but the tens digit isn't
								seven_seg_vals[3]--;		// decrement the tens digit of the minutes by 1
								seven_seg_vals[2] = 9;		// set the second to left most 7seg to 9 and the seconds to 59
								seven_seg_vals[1] = 5;
								seven_seg_vals[0] = 9;
							}
						}	
					}
				  
				}
				else	
					seven_seg_vals[0]--;					// all other cases, keep all 7seg values the same except decrement the ones digit of the seconds
			}
			else {
				
				
				// if the timer decrements to 00:00, set the alarm flag which turns of the 7seg display refresh and turns all the displays on and off every second
				// blinking the value 00:00 on the 7seg displays.
				alarm_flg = 1;
				if ((alarm_flg == 1) && (PORTGbits.RG0 == 0) && (PORTGbits.RG1 == 0) && (PORTGbits.RG2 == 0) && (PORTGbits.RG3 == 0)) {
					mPORTGSetBits(BIT_0 | BIT_1 | BIT_2 | BIT_3);
				}	
				else 
					mPORTGClearBits(BIT_0 | BIT_1 | BIT_2 | BIT_3);
				
			}
				
			intrpt_ctr = 0;		// reset the interrupt counter for the next second
		}	
	}
	
		
	if (delayCounter != 0) 		// debounce counter
		delayCounter--;
	else
		debounce_flag = 0;	
}

void intrpt_setup(void)
{
	mConfigIntCoreTimer(CT_INT_ON | CT_INT_PRIOR_7); // Setup core timer interrupt: priority level 3
	INTEnableSystemMultiVectoredInt();  			 // setup system wide interrupts
	OpenCoreTimer(MILLI_SECOND);					 // Open core timer and set timer interrupt period
}

void port_setup(void) 
{
	 
	TRISACLR = 0xFF; 	// Make PORTA outputs
	TRISGSET = 0xF000;  // Make upper nibble of PORTG as inputs for keypad
	TRISGCLR = 0x000F;	// Make lower nibble of PORTG for 7seg anode control
	TRISDCLR = 0xF000;	// configure keypad column as output
	
	PORTG = 0x0001;		// turn on the 7seg at the far right of the keypad plugin module
}




unsigned char rowpress(int column)
{
	if (PORTGbits.RG12 == 0) {				// if a button in the first row has been pressed	
		debounce(DEBOUNCE_TIME);
		while(PORTGbits.RG12 == 0);			// if button is held, wait until released before returning 
			return char_map[0][column];		// the ascii value of the button that was pressed
	}
	if (PORTGbits.RG13 == 0) {				// if a button in the second row has been pressed
		debounce(DEBOUNCE_TIME);					
		while(PORTGbits.RG13 == 0);			// if button is held, wait until released before returning
			return char_map[1][column];		// the ascii value of the button that was pressed
	}	
	if (PORTGbits.RG14 == 0) {				// if a button in the third row has been pressed
		debounce(DEBOUNCE_TIME);
		while(PORTGbits.RG14 == 0);			// if button is held, wait until released before returning
			return char_map[2][column];		// the ascii value of the button that was pressed
	}
	if (PORTGbits.RG15 == 0) {				// if a button in the fourth row has been pressed
		debounce(DEBOUNCE_TIME);
		while(PORTGbits.RG15 == 0);			// if button is held, wait until released before returning
			return char_map[3][column];		// the ascii value of the button that was pressed
	}
	else
		return '\0';						// if no button in any row was pressed, return null char
}


unsigned char keyin(void)
{
	
	int ascii_val, column;			// ascii_value stores the ascii code of the key being pressed
									// column is the current keypad column being scanned
	PORTD = 0xE000;					// set PORTD to the first column
	
	for (column = 0; column <= 2; column++) {			// scan through the first 3 columns (A-D are invalid timer values)
		if ( (ascii_val = rowpress(column)) != '\0')	// call rowpress for each column to scan for the 
			if (ascii_val == 0x2A)						// if * button is pressed, return null (invalid timer value)
				return '\0';
			else
				return ascii_val;						// row that has the pressed button
		else
			PORTD <<= 1;								// if no button is pressed in that column, go to 
	}													// the next row
	
	return '\0';										// return null if nothing has been pressed
}	

void debounce(unsigned int delay) 
{
	delayCounter = delay;
	debounce_flag = 1;
	while(debounce_flag);		// block until the core timer interrupts 12 times (12 interrupts = 12 ms) which is
}								// debounce wait time when a key is pressed


	
	