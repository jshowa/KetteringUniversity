/*
*********************************************************************************************************
*                        Wytec Dragon12 Board Support Package
* File : Switches.c
* By   : Jacob Howarth, Wyatt Paro, and Scott Snyder
*
* Notes: This file provides thread safe software support for driving the 7-Segment 
*        LED display blocks on a Wytec Dragon12.
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*                                        INCLUDES
*********************************************************************************************************
*/

#include <includes.h>
//byte ph_value;
/*
*********************************************************************************************************
*                                              VARIABLES
*********************************************************************************************************
*/

static  OS_EVENT   *SW4Sema;                            /* Binary semaphore used to access SW4 */
static  OS_EVENT   *SW3Sema;                            /* Binary semaphore used to access SW3 */
static  OS_EVENT   *SW2Sema;                            /* Binary semaphore used to access SW2 */

/*
*********************************************************************************************************
*                                        SWITCHES INIT
*
* Description : This function initializes the switches SW2, SW3, SW4, and SW5 I/O pins as 
*               inputs (i.e. sets DDRH to all 0's).
*
* Arguments   : None
*
* Returns     : None
*
* Notes       : THE CORRECT ECT CHANNEL ISR VECTOR MUST BE SET WITHIN VECTORS.C. 
*               This involves plugging the right vector number with the name of
*               the ISR function below. A prototype at the top of vectors.c must
*               also be created.
*********************************************************************************************************
*/
void Switches_Init(void) 
{
    DDRH = DDRH_INPUT_MASK;      /* Set Port H to act on inputs */
    //PTH = PTH_CLEAR_MASK;
    //SwitchesSemaInit();          /* Initialize switch semaphores */
    //PIEH = PTH_INT_FLAG;         /* Initialize Port H local interrupt flag for SW4 (bit 1) */
}

/*
*********************************************************************************************************
*                                   SEVEN SEGMENT INTERRUPT SERVICE ROUTINE
*
* Description : This function is the Port H Switch Interrupt Service Routine. This interrupt
*               facilitates the use of switches SW2-4 on the Wytec Dragon12 board. It checks if
*               the switches SW2, SW3, and SW4 have been pressed and posts to their associated
*               semaphores.
*
* Arguments   : None
*
* Returns     : None
*
* Notes       : THE CORRECT ECT CHANNEL ISR VECTOR MUST BE SET WITHIN VECTORS.C. 
*               This involves plugging the right vector number with the name of
*               the ISR function below. A prototype at the top of vectors.c must
*               also be created.
*********************************************************************************************************
*/
void Switches_ISR_Handler(void) 
{
    //ph_value = PTH;
    PIFH = PTH_INT_FLAG;            /* Clear Port H local interrupt flag */
    
    
    if ((PTH & PTH_MASK_SW4) == 0)  /* Pend to SW4 semaphore if the SW4 bit in Port H is cleared (i.e. switch is pressed) */
    {
        SW4Unlock();
    }
    
    if ((PTH & PTH_MASK_SW3) == 0)  /* Pend to SW3 semaphore if the SW3 bit in Port H is cleared (i.e. switch is pressed) */
    {
        SW3Unlock();                // increment minute
    }
    
    if ((PTH & PTH_MASK_SW2) == 0)  /* Pend to SW2 semaphore if the SW2 bit in Port H is cleared (i.e. switch is pressed) */
    {
        SW2Unlock();                // increment hour
    }
        
}


/*
*********************************************************************************************************
*                                       INITIALIZE RTOS SERVICES
*
* Description : This function creates a semaphore to ensure exclusive access to switches SW2-4 and thus
*               provide thread safe access.
*
* Arguments   : none
*
* Returns     : none
*********************************************************************************************************
*/
void  SwitchesSemaInit (void)
{
#if OS_EVENT_NAME_SIZE > 11
    INT8U  err;
#endif


    SW4Sema  = OSSemCreate(0);                          /* Create display access semaphore                      */
    SW3Sema  = OSSemCreate(0);
    SW2Sema  = OSSemCreate(0);
#if OS_EVENT_NAME_SIZE > 15
    OSEventNameSet(SW4Sema, (INT8U *)"SW4 Lock", &err);
    OSEventNameSet(SW3Sema, (INT8U *)"SW3 Lock", &err);
    OSEventNameSet(SW2Sema, (INT8U *)"SW2 Lock", &err);
#endif
}


/*
*********************************************************************************************************
*                              EXCLUSIVE ACCESS FUNCTIONS TO THE DISPLAY
*
* Description : These functions are used to gain and release exclusive access to the LCD display.
*
* Arguments   : none
*
* Returns     : none
*********************************************************************************************************
*/

void  SW4Lock (void)
{
    INT8U  err;
    
    OSSemPend(SW4Sema, 0, &err);                        /* Obtain exclusive access to SW4 */
}

void  SW3Lock (void)
{
    INT8U  err;

    //while (SW3Sema->OSEventCnt != 0)                    /* debouce code -- doesn't work*/
    /*{
        if (SW3Sema->OSEventCnt == 1)
            break;
        else
            SW3Sema->OSEventCnt--;
    }*/

    OSSemPend(SW3Sema, 0, &err);                        /* Obtain exclusive access to SW3 */
}

void  SW2Lock (void)
{
    INT8U  err;

    OSSemPend(SW2Sema, 0, &err);                        /* Obtain exclusive access to SW2 */
}

void  SW4Unlock (void)
{
    if (SW4Sema->OSEventCnt < 1)
      OSSemPost(SW4Sema);                                 /* Release access to SW4 */    
}

void  SW3Unlock (void)
{
    OSSemPost(SW3Sema);                                 /* Release access to SW3 */
}

void  SW2Unlock (void)
{
    OSSemPost(SW2Sema);                                 /* Release access to SW2 */
}


    