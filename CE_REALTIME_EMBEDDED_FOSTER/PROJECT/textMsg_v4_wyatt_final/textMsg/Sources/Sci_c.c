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
extern OS_EVENT* SCISemWrt;
extern OS_EVENT* MessageReceived;			      
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
    int i;
    CPU_INT08U  status = SCI1SR1; 
    
    // If we have received 160 characters, or this is a newline...                                                
    if(SCI1DRL == '\r' || bufIndex == MSG_LENGTH-1){
      
      // Make sure to insert a null character at the end
      BUFFER.text_msg[bufIndex + 1] = 0;
      
      // If the message is valid (not empty)
      if(bufIndex > 1){

          if(isEmptyFree() != 0)
          {
            remove_free_list();
            // Let the ringtone run
            OSSemPost(SpeakerPlaySem);
            OSSemPost(SCISemWrt);            
          }
          else
            for(i = 0; i <= bufIndex; i++)
                BUFFER.text_msg[i] = 0;  
      }   
            
      bufIndex = 0;
    }
    // Otherwise...
    else if(SCI1DRL != '\n'){      
      // Add the character to the buffer
      BUFFER.text_msg[bufIndex++] = SCI1DRL; 
    }
    
    SCI1SR1_RDRF = 0x00;
    return;  
}