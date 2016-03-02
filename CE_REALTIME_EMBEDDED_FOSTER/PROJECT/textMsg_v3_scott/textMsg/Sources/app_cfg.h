/*
*********************************************************************************************************
*                              MC9S12DP256 Application Configuration
*
*                       DO NOT DELETE THIS FILE, IT IS REQUIRED FOR OS_VER > 2.80
*
*                                   CHANGE SETTINGS ACCORDINGLY
*
*
* File : app_cfg.h
* By   : Eric Shufro
*********************************************************************************************************
*/

#ifndef  APP_CFG_H
#define  APP_CFG_H

/*
*********************************************************************************************************
*                                        INCLUDES
*********************************************************************************************************
*/

#include  <lib_def.h>


/*
*********************************************************************************************************
*                                    TASK PRIORITIES!
*********************************************************************************************************
*/

#define  APP_TASK_START_PRIO                1                           /* Set the prio for the startup task                        */

#define  SPI_WRITE_TASK_PRIO                13                           /* Set the priority of the SPI write task                   */
#define  LCD_TEST_TASK_PRIO                 2                           /* Set the prio for the LCD Test Task                       */
#define  SEVEN_SEG_TEST_TASK_PRIO           4                           /* Set the prio for Seven Segment Test Task                 */
#define  KEYPAD_RD_TASK_PRIO                6                           /* Set the prio for the Keypad Read Task                    */
#define  OS_PROBE_TASK_PRIO                 8
#define  PROBE_COMM_RS232_PRIO_RESERVED     9                           /* See probe_com_cfg.h, for Probe Parse Task priority       */
#define  OS_TASK_TMR_PRIO                  10                           /* Set the prio of the tmr task, near lowest                */
#define  SPEAKER_PLY_TASK_PRIO             11                           /* Set the priority of the speaker play task -jsw */
#define  SPI_MUTEX_TEMP_PRIO               12
/*
*********************************************************************************************************
*                                    TASK STACK SIZES!
*********************************************************************************************************
*/

#define  APP_TASK_START_STK_SIZE          256                           /* Set the stack size for the startup task                  */
#define  LCD_TASK_STK_SIZE	              600                           /* Set the stack size for the LCD Test task                 */
#define  SEVEN_SEG_TEST_TASK_STK_SIZE     256                           /* Set the stack size for the 7-Segment Test task           */
#define  KEYPAD_RD_TASK_STK_SIZE          256                           /* Set the stack size for the Keypad Read task              */
#define  OS_PROBE_TASK_STK_SIZE           256
#define  SPEAKER_PLY_TASK_STK_SIZE        256                           /* Set the stack size for the Speaker Play task -jsw */
#define  SPI_WRITE_TASK_STK_SIZE          256                           /* Set the stack size for the SPI write task -jsw */
/*
*********************************************************************************************************
*                                  uC/LIB CONFIGURATION
*********************************************************************************************************
*/

#define  uC_CFG_OPTIMIZE_ASM_EN         DEF_DISABLED
#define  LIB_STR_CFG_FP_EN              DEF_ENABLED


/*
*********************************************************************************************************
*                                          uC/LCD
*********************************************************************************************************
*/

#define  uC_LCD_MODULE                  DEF_ENABLED
#define  DISP_BUS_WIDTH                     4                           /* Data bus width: 4 or 8 bit                               */


/*
*********************************************************************************************************
*                                     7-Segment LEDs
*********************************************************************************************************
*/

#define  SEVEN_SEG_OC                       0                           /* Use ECT Output Compare Channel 0 to generate a periodic  */
                                                                        /* interrupt for switching the enabled 7-Segment display    */
                                                                        /* WARNING: This must never be defined the same as          */
                                                                        /* OS_TICK_OC which is defined in bsp.h                     */


/*
*********************************************************************************************************
*                                 uC/Probe CONFIGURATION
*********************************************************************************************************
*/

#define  uC_PROBE_OS_PLUGIN             DEF_DISABLED
#define  uC_PROBE_COM_MODULE            DEF_DISABLED
#define  OS_PROBE_HOOKS_EN              DEF_DISABLED

#define  OS_PROBE_TASK                         1                        /* Task will be created for uC/Probe OS Plug-In             */
#define  OS_PROBE_TMR_32_BITS                  0                        /* Timer is 32 bits                                         */
#define  OS_PROBE_USE_FP                       1                        /* Override uC/Probe floating point support, use integers   */
#define  OS_PROBE_TASK_ID               OS_PROBE_TASK_PRIO              /* Current version of uC/OS-II does not use ID field        */

                                                                       
/*
*********************************************************************************************************
*                                     ERROR CHECKING
*********************************************************************************************************
*/

#if   ((SEVEN_SEG_OC < 0) || (SEVEN_SEG_OC > 7))
#error "SEVEN_SEG_OC is illegally defined in app_cfg.h. Expected value: 0 to 7"
#endif


#endif


