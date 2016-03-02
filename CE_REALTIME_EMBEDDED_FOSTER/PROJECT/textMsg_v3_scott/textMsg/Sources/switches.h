/*
*********************************************************************************************************
*                        Wytec Dragon12 Board Support Package
*
* File : switches.h
* By   : Jacob Howarth and Wyatt Paro
*
* Notes: This file contains function prototypes for initializing and use of the four, switches 
*        controlled by port H, on the Wytec Dragon12 EVB.
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*                                        DECLARATIONS
*********************************************************************************************************
*/

#define DDRH_INPUT_MASK 0x00
#define DDRH_OUTPUT_MASK    ~(DDRH_INPUT_MASK)

#define PTH_CLEAR_MASK  0x00
#define PTH_SET_MASK    0xFF

#define PTH_MASK_SW5 0x01
#define PTH_MASK_SW4 0x02
#define PTH_MASK_SW3 0x04
#define PTH_MASK_SW2 0x08

#define PTH_MASK_SET (PTH_MASK_SW5 | PTH_MASK_SW4 | PTH_MASK_SW3 | PTH_MASK_SW2)

#define PTH_INT_FLAG 0x0E

/*
*********************************************************************************************************
*                                        PROTOTYPES
*********************************************************************************************************
*/

void Switches_Init(void);
void Switches_ISR_Handler(void);
void SwitchesSemaInit(void);
void SW4Lock(void);
void SW4Unlock(void);
void SW3Lock(void);
void SW3Unlock(void);
void SW2Lock(void);
void SW2Unlock(void);