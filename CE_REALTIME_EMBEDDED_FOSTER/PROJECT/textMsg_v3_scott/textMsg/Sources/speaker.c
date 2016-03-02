#include <includes.h>


/* use pwm 5.. PP%... for Dragon12plus  */
void Speaker_Init(void) {
  PWMCLK = 0;     /* clock A as source */
  PWMPOL = 0x20;  /* PWM5 high at start of pulse  */
  PWMPRCLK = 0x05;/* prescaler to 32  */
  PWMCTL = 0x4c;  /* concatenate PWM 4,5  */
  PWMCAE = 0;      /* left aligned   */
  PWMPER45 = 1000;
  PWMDTY45 = 0;
  PWME_PWME5 = 1;    
  
  
  
  
  
}


/* continuously plays the tone specified   */

void Speaker_Note(INT16U note){
  PWMPER45 = note;
  PWMDTY45 = note >> 1;
  
  
  
}