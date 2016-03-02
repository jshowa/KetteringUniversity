/*****************************************************************************
 * Microchip Graphics Library Demo Application
 * This program shows how to use the Graphics Primitives Layer.
 *****************************************************************************
 * FileName:        MainDemo.c
 * Dependencies:    MainDemo.h
 * Processor:       PIC24F, PIC24H, dsPIC, PIC32
 * Compiler:       	MPLAB C30, MPLAB C32
 * Linker:          MPLAB LINK30, MPLAB LINK32
 * Company:         Microchip Technology Incorporated
 *
 * Software License Agreement
 *
 * Copyright © 2008 Microchip Technology Inc.  All rights reserved.
 * Microchip licenses to you the right to use, modify, copy and distribute
 * Software only when embedded on a Microchip microcontroller or digital
 * signal controller, which is integrated into your product or third party
 * product (pursuant to the sublicense terms in the accompanying license
 * agreement).  
 *
 * You should refer to the license agreement accompanying this Software
 * for additional information regarding your rights and obligations.
 *
 * SOFTWARE AND DOCUMENTATION ARE PROVIDED “AS IS” WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION, ANY WARRANTY
 * OF MERCHANTABILITY, TITLE, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR
 * PURPOSE. IN NO EVENT SHALL MICROCHIP OR ITS LICENSORS BE LIABLE OR
 * OBLIGATED UNDER CONTRACT, NEGLIGENCE, STRICT LIABILITY, CONTRIBUTION,
 * BREACH OF WARRANTY, OR OTHER LEGAL EQUITABLE THEORY ANY DIRECT OR INDIRECT
 * DAMAGES OR EXPENSES INCLUDING BUT NOT LIMITED TO ANY INCIDENTAL, SPECIAL,
 * INDIRECT, PUNITIVE OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 * COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY, SERVICES, OR ANY
 * CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF),
 * OR OTHER SIMILAR COSTS.
 *
 * Author               Date        Comment
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Anton Alkhimenok		30/05/07	...
 *****************************************************************************/
 
 /* Name: Jacob Howarth and Ocie Lewis
  * Class: CE420 - Microcomputers II
  *	Term: Fall 2011
  * Date: 11/14/11
  * Professor: Tewolde
  *
  * Description: This program demos the primitive layer of the
  *				 Microchip graphics library API by displaying
  *				 a bitmap image of the Kettering Logo with a 
  * 			 message "Welcome to the ECE" followed by
  *				 three shapes (circle, rectangle, and triangle)
  *				 that start at the center of the LCD and expanding
  *				 in size until it reaches the maximum size of the 
  *				 screen. The names of each shape are also printed
  *				 in the center.
  *
  *				 The labs main purpose is to explore the primitive
  *				 layer drawing and color functions, text manipulations, 
  *				 and bitmap image support.
  */
#include "MainDemo.h"

/*****CONFIGURATION BITS*****/
#if defined(__dsPIC33F__) || defined(__PIC24H__)
_FOSCSEL(FNOSC_PRI);
_FOSC(FCKSM_CSECMD &OSCIOFNC_OFF &POSCMD_XT);
_FWDT(FWDTEN_OFF);
#elif defined(__PIC32MX__)
    #pragma config FPLLODIV = DIV_1, FPLLMUL = MUL_20, FPLLIDIV = DIV_2, FWDTEN = OFF, FCKSM = CSECME, FPBDIV = DIV_1
    #pragma config OSCIOFNC = ON, POSCMOD = XT, FSOSCEN = ON, FNOSC = PRIPLL
    #pragma config CP = OFF, BWP = OFF, PWP = OFF
#else
    #if defined(__PIC24FJ256GB110__)
_CONFIG1(JTAGEN_OFF & GCP_OFF & GWRP_OFF & COE_OFF & FWDTEN_OFF & ICS_PGx2)
_CONFIG2(0xF7FF & IESO_OFF & FCKSM_CSDCMD & OSCIOFNC_OFF & POSCMOD_HS & FNOSC_PRIPLL & PLLDIV_DIV2 & IOL1WAY_OFF)
    #endif
    #if defined(__PIC24FJ256GA110__)
_CONFIG1(JTAGEN_OFF & GCP_OFF & GWRP_OFF & COE_OFF & FWDTEN_OFF & ICS_PGx2)
_CONFIG2(IESO_OFF & FCKSM_CSDCMD & OSCIOFNC_OFF & POSCMOD_HS & FNOSC_PRIPLL & IOL1WAY_OFF)
    #endif
    #if defined(__PIC24FJ128GA010__)
_CONFIG2(FNOSC_PRIPLL & POSCMOD_XT) // Primary XT OSC with PLL
_CONFIG1(JTAGEN_OFF & FWDTEN_OFF)   // JTAG off, watchdog timer off
    #endif
	#if defined (__PIC24FJ256DA210__)
_CONFIG1( WDTPS_PS32768 & FWPSA_PR128 & ALTVREF_ALTVREDIS & WINDIS_OFF & FWDTEN_OFF & ICS_PGx2 & GWRP_OFF & GCP_OFF & JTAGEN_OFF) 
_CONFIG2( POSCMOD_HS & IOL1WAY_OFF & OSCIOFNC_OFF & OSCIOFNC_OFF & FCKSM_CSDCMD & FNOSC_PRIPLL & PLL96MHZ_ON & PLLDIV_DIV2 & IESO_OFF)
_CONFIG3( WPFP_WPFP255 & SOSCSEL_SOSC & WUTSEL_LEG & ALTPMP_ALTPMPEN & WPDIS_WPDIS & WPCFG_WPCFGDIS & WPEND_WPENDMEM) 
	#endif	  
	#if defined (__PIC24FJ256GB210__)
_CONFIG1( WDTPS_PS32768 & FWPSA_PR128 & ALTVREF_ALTVREDIS & WINDIS_OFF & FWDTEN_OFF & ICS_PGx2 & GWRP_OFF & GCP_OFF & JTAGEN_OFF) 
_CONFIG2( POSCMOD_HS & IOL1WAY_OFF & OSCIOFNC_OFF & OSCIOFNC_OFF & FCKSM_CSDCMD & FNOSC_PRIPLL & PLL96MHZ_ON & PLLDIV_DIV2 & IESO_OFF)
_CONFIG3( WPFP_WPFP255 & SOSCSEL_SOSC & WUTSEL_LEG & WPDIS_WPDIS & WPCFG_WPCFGDIS & WPEND_WPENDMEM) 
	#endif
#endif
/**************************/

/********************FONTS**************************/
extern const FONT_FLASH     Font25;
extern const FONT_FLASH     Font35;
/***************************************************/

/*******SIMPLE PREPROCESSOR MACRO FUNCTIONS*********/
#define WAIT_UNTIL_FINISH(x)    while(!x)
#define MIN(x,y)                ((x > y)? y: x)
#define DEMODELAY				1000
/***************************************************/


    /* */
    int main(void)
{
    SHORT       width, height;	// variables to store the height and width of the 16-bit bitmap image
    SHORT       counter;		// loop control variable for each shape that controls how many shapes are printed and their scale
    SHORT  		triangle[] = {160,120,160,120,160,120,160,120};		// triangle polygon points (start triangle in center of LCD)
    
    #if defined(__dsPIC33F__) || defined(__PIC24H__)

    // Configure Oscillator to operate the device at 40Mhz
    // Fosc= Fin*M/(N1*N2), Fcy=Fosc/2
    // Fosc= 8M*40(2*2)=80Mhz for 8M input clock
    PLLFBD = 38;                    // M=40
    CLKDIVbits.PLLPOST = 0;         // N1=2
    CLKDIVbits.PLLPRE = 0;          // N2=2
    OSCTUN = 0;                     // Tune FRC oscillator, if FRC is used

    // Disable Watch Dog Timer
    RCONbits.SWDTEN = 0;

    // Clock switching to incorporate PLL
    __builtin_write_OSCCONH(0x03);  // Initiate Clock Switch to Primary

    // Oscillator with PLL (NOSC=0b011)
    __builtin_write_OSCCONL(0x01);  // Start clock switching
    while(OSCCONbits.COSC != 0b011);

    // Wait for Clock switch to occur	
    // Wait for PLL to lock
    while(OSCCONbits.LOCK != 1)
    { };
    #elif defined(__PIC32MX__)
    INTEnableSystemMultiVectoredInt();
    SYSTEMConfigPerformance(GetSystemClock());
    #ifdef MULTI_MEDIA_BOARD_DM00123
    CPLDInitialize();
    CPLDSetGraphicsConfiguration(GRAPHICS_HW_CONFIG);
    CPLDSetSPIFlashConfiguration(SPI_FLASH_CHANNEL);
    #endif
    #endif

    #if defined (PIC24FJ256DA210_DEV_BOARD)
    
    // _ANSG8 = 0; /* S1 */
    // _ANSE9 = 0; /* S2 */
    // _ANSB5 = 0; /* S3 */
        
    #else

    /////////////////////////////////////////////////////////////////////////////
    // ADC Explorer 16 Development Board Errata (work around 2)
    // RB15 should be output
    /////////////////////////////////////////////////////////////////////////////
    LATBbits.LATB15 = 0;
    TRISBbits.TRISB15 = 0;

    #endif

    /////////////////////////////////////////////////////////////////////////////
    #if defined(__dsPIC33FJ128GP804__) || defined(__PIC24HJ128GP504__)
    AD1PCFGL = 0xffff;
    #endif
    InitGraph();

    while(1)
    {
	    /* Display the Kettering Logo bit image with the Welcome to the ECE in the center 
	    /* The image was taken from a JPEG and converted to a 16-bit bitmap in order to meet 
	    /* the display requirement of 16-bits per pixel. This can be done by resaving the
	    /* JPEG as 16-bit bitmap in a image editing program and then converting it using
	    /* the graphics library resource converter. The LCD only supports 4-bit, 8-bit, and
	    /* 16-bits per pixel images 
	    */
	    width = GetImageWidth((void *) &kulogo);
		SetFont((void *) &Font25);
	    WAIT_UNTIL_FINISH(PutImage(45, 20 , (void *) &kulogo,  1)); // image location is relative to the top left corner of the image
        SetColor(WHITE);
        Bar(45, 110, width + 45, 135);	// Draw a bar over the image to place the text over.
        SetColor(BLUE);
        OutTextXY(65, 110, "WELCOME TO THE ECE");
        
        DelayMs(DEMODELAY);	// delay for 1 sec and clear the screen
        SetColor(BLACK);
        ClearDevice();

		/* Display multiple circles, starting at the center and increasing in size at 
        /* each draw until the LCD borders are met. Increasing the counter value will 
        /* expand the shape size more quickly. */
        for(counter = 0; counter < MIN(GetMaxX(), GetMaxY()) >> 1; counter += 14)
        {
	        SetColor(BLACK);	// refresh the screen
	        ClearDevice();	
	        SetColor(BRIGHTRED);
	        SetFont((void *) &Font25);		// draw the name of the shape in the center
	        OutTextXY(135, 110, "Circle");
            WAIT_UNTIL_FINISH(Circle(GetMaxX() >> 1, GetMaxY() >> 1, counter + 5));	// draw the shape
            DelayMs(50);	// display for 50 ms
        }

        DelayMs(DEMODELAY);	// delay for 1 sec and clear the screen
        SetColor(BLACK);
        ClearDevice();

		/* Display multiple rectangles, starting at the center and increasing in size at 
        /* each draw until the LCD borders are met. Increasing the counter value will 
        /* expand the shape size more quickly. */
        for(counter = 0; counter < MIN(GetMaxX() / 2, GetMaxY() / 2) >> 1; counter += 5)
        {	
	        SetColor(BLACK);	// refresh the screen
	        ClearDevice();	
	        SetColor(BRIGHTBLUE);
	        OutTextXY(120, 110, "Rectangle");	// draw the name of the shape in the center
            WAIT_UNTIL_FINISH
            (
                Rectangle
                    (
                        GetMaxX() / 2 - counter * 3 + 6,	// x value of top left corner of rectangle
                        GetMaxY() / 2 - counter * 2 - 8,	// y value of top left corner of rectangle
                        GetMaxX() / 2 + counter * 3 - 6,	// x value of bottom right corner of rectangle
                        GetMaxY() / 2 + counter * 2 + 8     // y value of bottom right corner of rectangle        
                    )
            );
            DelayMs(20); // Display for 20 ms
            
        }

        DelayMs(DEMODELAY); // delay for 1 sec and clear the screen
        SetColor(BLACK);
        ClearDevice();
        
        /* Display multiple triangles, starting at the center and increasing in size at 
        /* each draw until the LCD borders are met. Increasing the counter value will 
        /* expand the shape size more quickly. */
        int i;
		for(counter = 0; counter < MIN(GetMaxX() / 2, GetMaxY() / 2) >> 1; counter += 10)
		{
			SetColor(BLACK);	// refresh the screen
			ClearDevice();
			SetColor(GREEN);	// draw the name of the shape at the center
			OutTextXY(125, 110, "Triangle");
			
			//{160,120,160,120,160,120,160,120} start in center of screen
			triangle[1] = triangle[1] - counter * 2 - 20; 	 // top point y
			triangle[7] = triangle[1];				 		 // top point y (close triangle)
			triangle[2] = triangle[2] - counter * 2 - 58; 	 // left corner point x
			triangle[3] = triangle[3] + counter * 2 + 16;	 // left corner point y
			triangle[4] = triangle[4] + counter * 2 + 58; 	 // right corner point x
			triangle[5] = triangle[5] + counter * 2 + 16;	 // right corner point y
			
			DrawPoly(4, triangle);	// draw the triangle (4 points to enclose it)
			
			// reset each of the triangle points to the center to prevent over expansion
			for (i = 0; i < 8; i++)
				if (i % 2 == 0)
					triangle[i] = 160;
				else
					triangle[i] = 120;
				
			DelayMs(50);	// display for 50 ms
		}
		
       
		DelayMs(DEMODELAY); // delay for 1 sec and clear the screen
    	SetColor(BLACK);
    	ClearDevice();
        
    }
}
