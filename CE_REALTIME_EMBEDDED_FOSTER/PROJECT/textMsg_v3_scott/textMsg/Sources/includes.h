#ifndef INCLUDES_H
#define INCLUDES_H

/*
*********************************************************************************************************
*                                           Master Include File
*
* File : includes.h
* By   : Eric Shufro
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*                                           FILES TO INCLUDE
*********************************************************************************************************
*/

                                                                /* ---------------- STD INCLUDE FILES ----------------- */
#include  <string.h>                                            
#include  <stddef.h>

                                                                /* ---------------- CPU INCLUDE FILES ----------------- */
                                                                
#include  <hidef.h>                                             /* Processor / environment specifics.                   */
#include  <mc9s12dg256.h>

                                                                /* -------------- MICRIUM INCLUDE FILES --------------- */
                                                                
#include  <cpu_def.h>	                                        /* uC/CPU, processor specifics.                         */
#include  <cpu.h>

#include  <lib_def.h>                                           /* uC/LIB.                                              */
#include  <lib_str.h>
#include  <lib_mem.h>

#include  <ucos_ii.h>				                            /* uC/OS-II.         		                            */			  	   


#include  <bsp.h>                                               /* Board support.                                       */
#include  <keypad.h>
#include  <sevenSegment.h>
#include  <nvm.h>
#include  <speaker.h>                                           // -jsw
#include  <spi.h>                                               // -jsw
#include  <spi_mem.h>                                           // -jsw
#include  <switches.h>
#include  <messages.h>                                          // -jsw

#if (uC_LCD_MODULE > 0)
#include  <lcd.h>                                               /* uC/LCD.                                              */
#endif

                                                                
#if (uC_PROBE_OS_PLUGIN > 0)                                    /* uC/Probe.                                            */
#include  <os_probe.h>
#endif

#if (uC_PROBE_COM_MODULE > 0)
#include  <probe_com.h>

#if (PROBE_COM_METHOD_RS232 > 0)
#include  <probe_rs232.h>
#endif
#endif

                                                                /* ------------ APPLICATION INCLUDE FILES ------------- */

                                                                
#endif                                                          /* End of file.                                         */


