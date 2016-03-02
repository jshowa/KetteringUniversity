/******************************************************************************
 *
 *                      Freescale MC9S12DP256 ISR Vector Definitions
 *
 * File Name  : vectors.c
 * Version    : 1.0
 * Date       : Jun/22/2004
 * Programmer : Eric Shufro
 ******************************************************************************/

/*
************************************************************************
*	                EXTERNAL ISR FUNCTION PROTOTYPES
************************************************************************
*/

extern void near _Startup(void);                                /* Startup Routine.                                     */
extern void near  OSTickISR(void);                              /* OS Time Tick Routine.                                */
extern void near  OSCtxSw(void);                                /* OS Contect Switch Routine.                           */
extern void near  SevenSegDisp_ISR(void);                       /* Seven Segment Display ISR.                           */
extern void near  ProbeRS232_RxTxISR(void);                     /* Probe SCI ISR.                                       */
extern void near  Switches_PTH_ISR(void);                       /* Switches Handler (SW2,SW3,SW4,SW5) -jsw              */


/*
************************************************************************
*	              DUMMY INTERRUPT SERVICE ROUTINES
*
* Description : When a spurious interrupt occurs, the processor will 
*               jump to the dedicated default handler and stay there
*               so that the source interrupt may be identified and
*               debugged.
*
* Notes       : Do Not Modify
************************************************************************
*/

#pragma CODE_SEG __NEAR_SEG NON_BANKED 
__interrupt void software_trap64 (void) {for(;;);}
__interrupt void software_trap63 (void) {for(;;);}
__interrupt void software_trap62 (void) {for(;;);}
__interrupt void software_trap61 (void) {for(;;);}
__interrupt void software_trap60 (void) {for(;;);}
__interrupt void software_trap59 (void) {for(;;);}
__interrupt void software_trap58 (void) {for(;;);}
__interrupt void software_trap57 (void) {for(;;);}
__interrupt void software_trap56 (void) {for(;;);}
__interrupt void software_trap55 (void) {for(;;);}
__interrupt void software_trap54 (void) {for(;;);}
__interrupt void software_trap53 (void) {for(;;);}
__interrupt void software_trap52 (void) {for(;;);}
__interrupt void software_trap51 (void) {for(;;);}
__interrupt void software_trap50 (void) {for(;;);}
__interrupt void software_trap49 (void) {for(;;);}
__interrupt void software_trap48 (void) {for(;;);}
__interrupt void software_trap47 (void) {for(;;);}
__interrupt void software_trap46 (void) {for(;;);}
__interrupt void software_trap45 (void) {for(;;);}
__interrupt void software_trap44 (void) {for(;;);}
__interrupt void software_trap43 (void) {for(;;);}
__interrupt void software_trap42 (void) {for(;;);}
__interrupt void software_trap41 (void) {for(;;);}
__interrupt void software_trap40 (void) {for(;;);}
__interrupt void software_trap39 (void) {for(;;);}
__interrupt void software_trap38 (void) {for(;;);}
__interrupt void software_trap37 (void) {for(;;);}
__interrupt void software_trap36 (void) {for(;;);}
__interrupt void software_trap35 (void) {for(;;);}
__interrupt void software_trap34 (void) {for(;;);}
__interrupt void software_trap33 (void) {for(;;);}
__interrupt void software_trap32 (void) {for(;;);}
__interrupt void software_trap31 (void) {for(;;);}
__interrupt void software_trap30 (void) {for(;;);}
__interrupt void software_trap29 (void) {for(;;);}
__interrupt void software_trap28 (void) {for(;;);}
__interrupt void software_trap27 (void) {for(;;);}
__interrupt void software_trap26 (void) {for(;;);}
__interrupt void software_trap25 (void) {for(;;);}
__interrupt void software_trap24 (void) {for(;;);}
__interrupt void software_trap23 (void) {for(;;);}
__interrupt void software_trap22 (void) {for(;;);}
__interrupt void software_trap21 (void) {for(;;);}
__interrupt void software_trap20 (void) {for(;;);}
__interrupt void software_trap19 (void) {for(;;);}
__interrupt void software_trap18 (void) {for(;;);}
__interrupt void software_trap17 (void) {for(;;);}
__interrupt void software_trap16 (void) {for(;;);}
__interrupt void software_trap15 (void) {for(;;);}
__interrupt void software_trap14 (void) {for(;;);}
__interrupt void software_trap13 (void) {for(;;);}
__interrupt void software_trap12 (void) {for(;;);}
__interrupt void software_trap11 (void) {for(;;);}
__interrupt void software_trap10 (void) {for(;;);}
__interrupt void software_trap09 (void) {for(;;);}
__interrupt void software_trap08 (void) {for(;;);}
__interrupt void software_trap07 (void) {for(;;);}
__interrupt void software_trap06 (void) {for(;;);}
__interrupt void software_trap05 (void) {for(;;);}
__interrupt void software_trap04 (void) {for(;;);}
__interrupt void software_trap03 (void) {for(;;);}
__interrupt void software_trap02 (void) {for(;;);}
__interrupt void software_trap01 (void) {for(;;);}
#pragma CODE_SEG DEFAULT   


/*
************************************************************************
*	              INTERRUPT VECTORS
************************************************************************
*/

typedef void (*near tIsrFunc)(void);
const tIsrFunc _vect[] @0xFF80 = {     /* Interrupt table                           */
        software_trap63,               /* 63 RESERVED                               */
        software_trap62,               /* 62 RESERVED                               */
        software_trap61,               /* 61 RESERVED                               */
        software_trap60,               /* 60 RESERVED                               */
        software_trap59,               /* 59 RESERVED                               */
        software_trap58,               /* 58 RESERVED                               */
        software_trap57,               /* 57 PWM Emergency Shutdown                 */
        software_trap56,               /* 56 Port P Interrupt                       */
        software_trap55,               /* 55 CAN4 transmit                          */
        software_trap54,               /* 54 CAN4 receive                           */
        software_trap53,               /* 53 CAN4 errors                            */
        software_trap52,               /* 52 CAN4 wake-up                           */ 
        software_trap51,               /* 51 CAN3 transmit                          */
        software_trap50,               /* 50 CAN3 receive                           */
        software_trap49,               /* 49 CAN3 errors                            */
        software_trap48,               /* 48 CAN3 wake-up                           */ 
        software_trap47,               /* 47 CAN2 transmit                          */
        software_trap46,               /* 46 CAN2 receive                           */
        software_trap45,               /* 45 CAN2 errors                            */
        software_trap44,               /* 44 CAN2 wake-up                           */ 
        software_trap43,               /* 43 CAN1 transmit                          */
        software_trap42,               /* 42 CAN1 receive                           */
        software_trap41,               /* 41 CAN1 errors                            */
        software_trap40,               /* 40 CAN1 wake-up                           */ 
        software_trap39,               /* 39 CAN0 transmit                          */
        software_trap38,               /* 38 CAN0 receive                           */
        software_trap37,               /* 37 CAN0 errors                            */
        software_trap36,               /* 36 CAN0 wake-up                           */        
        software_trap35,               /* 35 FLASH                                  */
        software_trap34,               /* 34 EEPROM                                 */
        software_trap33,               /* 33 SPI2                                   */
        software_trap32,               /* 32 SPI1                                   */
        software_trap31,               /* 31 IIC Bus                                */
        software_trap30,               /* 30 BDLC                                   */
        software_trap29,               /* 29 CRG Self Clock Mode                    */
        software_trap28,               /* 28 CRG PLL lock                           */
        software_trap27,               /* 27 Pulse Accumulator B Overflow           */
        software_trap26,               /* 26 Modulus Down Counter underflow         */
        Switches_PTH_ISR,              /*software_trap25,                25 Port H     -jsw */
        software_trap24,               /* 24 Port J                                 */
        software_trap23,               /* 23 ATD1                                   */
        software_trap22,               /* 22 ATD0                                   */
        software_trap21,
        //ProbeRS232_RxTxISR,            /* 21 SC11                                   */
        software_trap20,               /* 20 SCI0                                   */                              
        software_trap19,               /* 19 SPI0                                   */
        software_trap18,               /* 18 Pulse accumulator input edge           */
        software_trap17,               /* 17 Pulse accumulator A overflow           */
        software_trap16,               /* 16 Enhanced Capture Timer Overflow        */
        OSTickISR,                     /* 15 Enhanced Capture Timer channel 7       */        
        software_trap14,               /* 14 Enhanced Capture Timer channel 6       */
        software_trap13,               /* 13 Enhanced Capture Timer channel 5       */
        software_trap12,               /* 12 Enhanced Capture Timer channel 4       */
        software_trap11,               /* 11 Enhanced Capture Timer channel 3       */
        software_trap10,               /* 10 Enhanced Capture Timer channel 2       */
        software_trap09,               /* 09 Enhanced Capture Timer channel 1       */
        SevenSegDisp_ISR,              /* 08 Enhanced Capture Timer channel 0       */
        software_trap07,               /* 07 Real Time Interrupt                    */
        software_trap06,               /* 06 IRQ                                    */
        software_trap05,               /* 05 XIRQ                                   */
        software_trap04,               /* 04 SWI - Breakpoint on HCS12 Serial Mon.  */
        software_trap03,               /* 03 Unimplemented instruction trap         */
        software_trap02,               /* 02 COP failure reset                      */
        software_trap01,               /* 01 Clock monitor fail reset               */
        _Startup                       /* 00 Reset vector                           */
   };																			      
