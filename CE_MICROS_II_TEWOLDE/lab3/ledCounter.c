/* Name: Jacob Howarth
 * Class: CE420 - Microcomputers II
 * Term: Fall 2011
 * Prof. Tewolde
 *
 * Description: This program implements a binary decrementer that decrements 
 *				its value every second displaying it on the LED's RA0-7. 
 *				The timer value is set by holding down S3 and pressing S4
 *				which increments the timer value by one on each press.
 *				When S3 is released, the timer decrements by one roughly
 *				every second.
 */

// configuration bit settings, Fcy=80MHz, Fpb=40MHz
#pragma config POSCMOD=XT, FNOSC=PRIPLL
#pragma config FPLLIDIV=DIV_2, FPLLMUL=MUL_20, FPLLODIV=DIV_1
#pragma config FPBDIV=DIV_2, FWDTEN=OFF, CP=OFF, BWP=OFF

#include <p32xxxx.h>
#include <plib.h>

#define CPU_CLK 80000000

int main() {
	
	/* Configure CPU for optimum performance (8 MHz clock) */
	SYSTEMConfigPerformance(CPU_CLK);
	
	/* Configure TRIS registers */
	TRISDSET = 0x2040;					// make TRISD RD6 (S3) and RD13 (S4) inputs
	TRISACLR = 0xFF;					// make TRISA RA0-7 to outputs
	
	/* Configure PORT registers */
	PORTA = 0x00;					// clear lower byte of PORTA (turns on led's)
	
	/* debounce counter */
	unsigned int delay;				// debounce delay counter 
	
	/* counter value */
	char ledCount = 0x00;			// timer value
	
	while(1) {
		
		/* SET MODE */
		if (PORTDbits.RD6 == 0) {	// scan for S3 depress
			delay = 400000;			// delay approx. 25 ms for debounce
			while (delay--);
			
			delay = 400000;
			
			if (PORTDbits.RD13 == 0) {	// while S3 is held down and S4
				while (delay--);		
				delay = 400000; 
				PORTA = ++ledCount;		// show timer value on led's in binary
			}
		}
		/* RUN MODE */
		else if (PORTDbits.RD6 == 1) {	// when S3 is depressed 
			if (ledCount != 0) {		// and the timer value isn't zero
				delay = 8000000;		
				while (delay--);
				PORTA = --ledCount;		// start counting down roughly every second
			}	
		}
		
	}
}			
