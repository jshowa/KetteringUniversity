/*
  Demonstation of the CodeWarrior environment
  
  This program implements the stopwatch example from Lecture 22

*/
#include <hidef.h>      /* common defines and macros */
#pragma LINK_INFO DERIVATIVE "mc9s12dg256b"

#include "main_asm.h"   /* interface to the assembly module */
     
// define all control registers needed
#define PORTB (*(volatile unsigned char*) 0x0001)
#define DDRB  (*(volatile unsigned char*) 0x0003)

#define PTH   (*(volatile unsigned char*) 0x0260)
#define DDRH  (*(volatile unsigned char*) 0x0262)
#define PIEH  (*(volatile unsigned char*) 0x0266)
#define PIFH  (*(volatile unsigned char*) 0x0267)

#define PTP   (*(volatile unsigned char*) 0x0258)
#define DDRP  (*(volatile unsigned char*) 0x025A)

#define CRGFLG (*(volatile unsigned char*) 0x0037)
#define CRGINT (*(volatile unsigned char*) 0x0038)
#define RTICTL (*(volatile unsigned char*) 0x003B)
#define RTII 0x80
#define RTIF 0x80

#define SW5 0x01      // define bit pattern for pushbutton 5
#define ONESEC 122*8  // number of interrups per second

//  function prototypes
void init7seg(void);

// must be volatile since the RTI interrupt will modify
// in addition to the main loop
volatile char digitx[4];

int i;                // counter for looping through digits
volatile unsigned int RTI_count;  // count interrupts to measure one sec.

//#pragma CODE_SEG MY_CODE
void main(void) {
 asm LDS #$3600
 init7seg();

 digitx[0] = 0x30; // initialize left digit...
 digitx[1] = 0x30;            
 digitx[2] = 0x30;
 digitx[3] = 0x30; // ...through right digit
 
 RTICTL = 0x40;    // set to 1x2^13
 
 DDRH &= ~SW5;     // SW5 to input
 PIEH |= SW5;      // and enable its interrupt
 
 enableInts(); 
 for(;;){ //forever...
   digit(dig[i],digitx[i]); // display the next digit
   if (++i == 4)  // preincrement i, and wrap back to 0
     i = 0;       // when 4 is reached
  
 }
}
/****************************************************/      
void init7seg(void){
  DDRB = 0xFF;
  DDRP = 0xFF;
}

/****************************************************/
// when enabled, increment the four-digit 7-segment display
// once per second
void interrupt RTI_ISR(void){
  CRGFLG = RTIF;          // clear flag 
  if (--RTI_count == 0){  // decrement and then compare to 0
    RTI_count = ONESEC;   // reset for the next second
    if (++digitx[3] == 0x3A){ // increment the rightmost...
      digitx[3] = 0x30; 
      if (++digitx[2] == 0x3A){
        digitx[2] = 0x30; 
        if (++digitx[1] == 0x3A){
          digitx[1] = 0x30; 
          if (++digitx[0] == 0x3A){ // increment the leftmost
            digitx[0] = 0x30; 
          }
        }
      }
    }
  }
}

/****************************************************/
// if the RTI is running, stop it
// if the RTI is not running and display is 0000...
// ... start RTI
// if the RTI is not running and display is not 0000...
// clear the display
//
void interrupt PTH_ISR(void){
  PIFH = SW5;      // clear flag for SW5 
  if(CRGINT & RTII){  // if RTI is enabled
    CRGINT &= ~RTII;  // disable the RTI interrupt      
  } else{
    if ( (digitx[0] == 0x30) && (digitx[1] == 0x30) && (digitx[2] == 0x30) && (digitx[3] == 0x30))    {
      CRGINT |= RTII;     // enable the RTI interrupt
      RTI_count = ONESEC; // reset count for the next second
    } else{
      digitx[0] = 0x30;   // clear display back to 0's
      digitx[1] = 0x30;
      digitx[2] = 0x30;
      digitx[3] = 0x30;   
    }
  }
}