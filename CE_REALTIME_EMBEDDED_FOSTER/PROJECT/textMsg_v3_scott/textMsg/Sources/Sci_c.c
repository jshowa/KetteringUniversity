/*
*********************************************************************************************************
*                                      SCI Communication
*
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*
*                                      Communication: RS-232
*                                  Port for the Freescale MC9S12
*
* Filename      : probe_rs232c.c
* Version       : V1.00
* Programmer(s) : Eric Shufro
*********************************************************************************************************
*/

#include  <includes.h>
//#include  <probe_rs232.h>
//#include  <probe_com_cfg.h>


/*
*********************************************************************************************************
*                                              CONSTANTS
*********************************************************************************************************
*/


/*
*********************************************************************************************************
*                                        FUNCTION PROTOTYPES
*********************************************************************************************************
*/		

extern OS_EVENT* SpeakerPlaySem;			      
message BUFFER;
int bufIndex = 0;

/*$PAGE*/
/*
*********************************************************************************************************
*                       Initialize COM port for uC/Probe RS-232 Communication Module
*
* Description: Initialize the UART for uC/Probe communication.
*
* Argument(s): None
*
* Returns    : None
*
* Note(s)    : 
*********************************************************************************************************
*/

void Sci1_Init(void){
  
  SCI1CR2 = 0x00;               // disable the receiver, transmitter and interrupts
  SCI1BDH = 0x00;
  SCI1BDL = 156;
  SCI1CR1 = 0x00;               // 8 data bits, no parity, 1 stop bit
  SCI1CR2 = 0x0C;               // Enable the receiver and transmitter
  
  SCI1CR2 |= SCI1SR1_RDRF_MASK; // enable SCI Rx Interrupts
  SCI1CR2 &= ~SCI1SR1_TC_MASK;  // disable SCI Tx Interrupts
}
    					                

/*
*********************************************************************************************************
*                                 Sci_ISR_Handler()
*
* Description: This functions handle Rx interrupts
*
* Argument(s): None
*
* Returns    : None
*
* Note(s)    : 
*********************************************************************************************************
*/

void  Sci1_ISR_Handler (void) 
{
    CPU_INT08U  status = SCI1SR1; 
    
    // If we have received 160 characters, or this is a newline...                                                
    if(SCI1DRL == '\n' || bufIndex == MSG_LENGTH-1){
      
      // Make sure to insert a null character at the end
      BUFFER.text_msg[bufIndex + 1] = 0;
      
      // If the message is valid (not empty)
      if(bufIndex > 1){
        // Let the ringtone run
        OSSemPost(SpeakerPlaySem);
        // And insert the message into the list
        //msg_insert(&BUFFER);
      }
      
      bufIndex = 0;
    }
    // Otherwise...
    else{      
      // Add the character to the buffer
      BUFFER.text_msg[bufIndex++] = SCI1DRL; 
    }
    
    SCI1SR1_RDRF = 0x00;
    return;  
}