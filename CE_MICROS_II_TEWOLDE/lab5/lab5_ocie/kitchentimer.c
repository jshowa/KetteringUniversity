#include <plib.h>
// configuration bit settings, Fcy=80MHz, Fpb=40MHz
#pragma config POSCMOD=XT, FNOSC=PRIPLL
#pragma config FPLLIDIV=DIV_2, FPLLMUL=MUL_20
#pragma config FPLLODIV=DIV_1, FPBDIV=DIV_2
#pragma config FWDTEN=OFF, CP=OFF, BWP=OFF

#define CPU_FREQUENCY 80000000
#define TIMER_FREQUENCY (CPU_FREQUENCY/2)
#define MILLI_SECOND (TIMER_FREQUENCY/1000)
#define DEBOUNCE_TIME 12 // triggered by intrpt/1ms timer.

//Function calls
void intrpt_setup(void);
void port_setup(void);
void flash(void);
unsigned char rowpress(int column);
unsigned char keyin(void);
void debounce(unsigned int delay);

//Initializing variables, arrays, and flags
unsigned char const keypad_to_7seg[11] = {0xC0, 0xF9, 0xA4, 0xB0, 0x99, 0x92, 0x82, 0xF8, 0x80, 0x90, 0xFF};
unsigned int delayCounter = DEBOUNCE_TIME; 		  // counter for button press handled in interrupt
unsigned char debounce_flag = 1;	  				  // clear flag in interrupt when 12 ms passes
unsigned char setrun_flag = 1;	  				  // 1 is set and 0 is run 
unsigned char keyPressValue = 0xC0;
unsigned char seven_seg_vals[4] = {0, 0, 0, 0};
unsigned int intrpt_ctr = 0; 
unsigned int alarm_flg = 0; 

// keypad button to 7seg character value map
unsigned const char char_map[4][4] = { 1, 2, 3, 'A',
					   			 	   4, 5, 6, 'B',
					   			 	   7, 8, 9, 'C',
					   			 	   0x2A, 0x30, 0x23, 'D',	
					 		   		 };

int main(void)
{
	unsigned int index = 0;
	port_setup();
	intrpt_setup();   

	while(1) { // Run forever
		keyPressValue = keyin(); //keyPressValue is any key pressed on keypad
			
		if (setrun_flag) { // set mode
			alarm_flg = 0; // Make sure alarm is always off in set mode
			
			//Assigns keypressed to lowest bit and shifts all other values left
			if (keyPressValue != '\0' && keyPressValue != 0x23) {
				if (keyPressValue == 0x30)
					keyPressValue = 0;
				seven_seg_vals[3] = seven_seg_vals[2];
				seven_seg_vals[2] = seven_seg_vals[1];
				seven_seg_vals[1] = seven_seg_vals[0];
				seven_seg_vals[0] = keyPressValue;
			}
			//If pound key pressed switch to Run mode
			if (keyPressValue == 0x23)
				setrun_flag = 0;		
		}
		else {//If in run mode and pound key pressed switch to set mode and reset values if alarm is set
			if (keyPressValue == 0x23){
				if(alarm_flg == 1){
				seven_seg_vals[0] = 0;
				seven_seg_vals[1] = 0;
				seven_seg_vals[2] = 0;
				seven_seg_vals[3] = 0;
				}
				setrun_flag = 1;
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
// Toggle LED 
//mPORTAToggleBits(BIT_0);

		
		PORTG <<= 1;
	
		if ((PORTGbits.RG0 == 0) && (PORTGbits.RG1 == 0) && (PORTGbits.RG2 == 0) && (PORTGbits.RG3 == 0))
			PORTG += 1;	
				
		if (PORTGbits.RG0 == 1)
			PORTA = keypad_to_7seg[seven_seg_vals[0]];
		if (PORTGbits.RG1 == 1)
			PORTA = keypad_to_7seg[seven_seg_vals[1]] + 0x80;
		if (PORTGbits.RG2 == 1)
			PORTA = keypad_to_7seg[seven_seg_vals[2]] + 0x80;
		if (PORTGbits.RG3 == 1)
			PORTA = keypad_to_7seg[seven_seg_vals[3]];	
		
	if (setrun_flag == 0) {
		intrpt_ctr++;
		
		if (seven_seg_vals[0] == 0 && seven_seg_vals[1] == 0 && seven_seg_vals[2] == 0 && seven_seg_vals[3] == 0)
			alarm_flg = 1;
					
			
		if (intrpt_ctr == 1000) {
			if(alarm_flg)
				flash();
			else if (seven_seg_vals[0]!= 0 || seven_seg_vals[1]!= 0 || seven_seg_vals[2]!= 0 || seven_seg_vals[3]!= 0) {
				
				if (seven_seg_vals[0] == 0) {
					if (seven_seg_vals[1] != 0) {
						seven_seg_vals[1]--;
						seven_seg_vals[0] = 9;
					}
					else if (seven_seg_vals[1] == 0) {
						if (seven_seg_vals[2] != 0) {
							seven_seg_vals[2]--;
							seven_seg_vals[1] = 5;
							seven_seg_vals[0] = 9;
						}	
						else if (seven_seg_vals[2] == 0) {
							if (seven_seg_vals[3] != 0) {
								seven_seg_vals[3]--;
								seven_seg_vals[2] = 9;
								seven_seg_vals[1] = 5;
								seven_seg_vals[0] = 9;
							}
						}	
					}
				  
				}
				else	
					seven_seg_vals[0]--;
			}
				
			intrpt_ctr = 0;
		}	
	}
	
		
	if (delayCounter != 0) 
		delayCounter--;
	else
		debounce_flag = 0;	
}

void intrpt_setup(void)
{
	mConfigIntCoreTimer(CT_INT_ON | CT_INT_PRIOR_3); // Setup core timer interrupt: priority level 3
	INTEnableSystemMultiVectoredInt();  			 // setup system wide interrupts
	OpenCoreTimer(MILLI_SECOND);					 // Open core timer and set timer interrupt period
}
void port_setup(void) 
{
	 
	TRISACLR = 0xFF; // Make PORTA outputs
	TRISGSET = 0xF000;  // Make upper nibble of PORTG as inputs for keypad
	TRISGCLR = 0x000F;	// Make lower nibble of PORTG for 7seg turn on and off
	TRISDCLR = 0xF000;	// configure keypad column as output
	
	//PORTA = 0x3F;    // Turn off all 7seg LED's on startup 
	PORTG = 0x0001;
}



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
	
	for (column = 0; column <= 2; column++) {			// scan through all the columns
		if ( (ascii_val = rowpress(column)) != '\0')	// call rowpress for each column to scan for the 
			if (ascii_val == 0x2A)
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
	while(debounce_flag);	
}

void flash(void)
{
	int i;
	
	//loop through all the digits
		for(i = 0;i<4;i++)
			//flip them
			if(seven_seg_vals[i] == 0)
				seven_seg_vals[i] = 10;
			else
				seven_seg_vals[i] = 0;
	

}


	
	