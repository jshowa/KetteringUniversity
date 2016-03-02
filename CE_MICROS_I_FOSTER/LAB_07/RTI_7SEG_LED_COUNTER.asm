;*********************************************************************
; Jacob S. Howarth
; Microcomputers I, Fall '08
; Prof. Foster
; 
; Description: This program uses an RTI to create a counter
;	       that interupts a custom number of times per second 
;	       displaying the counters value, in ASCII, on
;	       the 7-segment displays as well as in binary
;	       using the LED's.
;
; Inputs: 1) Switches SW5, SW4, SW3, SW2 - PTH pin [0:3].
; 
; Outputs: 1) 7-segment display - PortB all pins [0:7]
;	      All 7-segement displays enabled - PTP pin [0:3].	   
;	   2) 8-LED's - PTJ pin 1 cleared to output PortB values.
;
;
; Dependant files: 7segment.s19 file used as 7-segment display
;		   driver.
;*********************************************************************

; Define I/O address constants

; PortB constants
PortB	EQU	$0001
DDRB	EQU	$0003

; PTH constants
PTH	EQU	$0260
DDRH	EQU	$0262

; PTP constants
PTP	EQU	$0258
DDRP	EQU	$025A

; PTJ constants
PTJ	EQU	$0268
DDRJ	EQU	$026A

; PTH edge-triggered, interupt enable (PIEH) and interupt flag (PIFH) constants
PIEH	EQU	$0266
PIFH	EQU	$0267

; RTI (real time interupt) interupt enable (RTIE), interupt flag (RTIF), clock reset and
; generation enable (CRGINT) and flag (CRGFLG), and real time controller (RTICTL) constants
CRGFLG	EQU	$0037
RTIF	EQU	%10000000
CRGINT	EQU	$0038
RTIE	EQU	%10000000
RTICTL	EQU	$003B

; 7segment.s19 initialization and digit display subroutine addresses.
INIT7SEG	EQU	$2800
DIGIT		EQU	$2880

; 7segment.s19 subroutine parameters that specify which 7-segment displays to
; turn on and off in DIGIT subroutine.
DIG1	EQU	%00000001 ; left digit
DIG2	EQU	%00000010 ; 2nd from left
DIG3	EQU	%00000100 ; 2nd from right
DIG4	EQU	%00001000 ; right digit

; Switches SW5, SW4, SW3, and SW2 constants.
SW5	EQU	%00000001 ; SW5 = PTH pin 0
SW4	EQU	%00000010 ; SW4 = PTH pin 1
SW3	EQU	%00000100 ; SW3 = PTH pin 2
SW2	EQU	%00001000 ; SW2 = PTH pin 

; Number of times to interupt per second.
ONESEC	EQU	279

		ORG	$1000
		; create storage for the patterns for
		; each digit
DIGIT1		DS.B	1
DIGIT2		DS.B	1
DIGIT3		DS.B	1
DIGIT4		DS.B	1
RTICOUNT	DS.B	1	; tracks number of interrupts		

		; define the interrupt vectors
		ORG	$3E70
		DC.W	RTIISR

		ORG	$3E4C
		DC.W	PTHISR
		
		

;**************** MAIN PROGRAM START ***************
		ORG	$2000
		LDS #$3600
		JSR INIT7SEG	; Configure the 7-segment display
		
		; configure/enable SW5, SW4, SW3, SW2 interrupt (portH)
		MOVB	#$00,PTH
		MOVB	#(SW2 + SW3 + SW4 + SW5), PIEH
		
		; configure/enable the 8-LED's
		MOVB	#$02,DDRJ
		BCLR	PTJ,%00000010

		; configure RTI device 		
		MOVW	#ONESEC,RTICOUNT
		MOVB	#$36,RTICTL
		BSET	CRGINT,RTIE	

		; initialize the display to 0's
		MOVB	#$30,DIGIT1
		MOVB	#$30,DIGIT2
		MOVB	#$30,DIGIT3
		MOVB	#$30,DIGIT4
		; unmask interrupts...
		CLI		
		; and wait for things to happen

Loop		LDAB	DIGIT1
		LDAA	#DIG1
		JSR	DIGIT
		JSR 	pause

		LDAB	DIGIT2
		LDAA	#DIG2
		JSR	DIGIT
		JSR 	pause

		LDAB	DIGIT3
		LDAA	#DIG3
		JSR	DIGIT
		JSR 	pause

		LDAB	DIGIT4
		LDAA	#DIG4
		JSR	DIGIT
		JSR 	pause
		BRA 	Loop

;****************************************************************
;Subroutine: PTHISR
;Description: Checks each button to identify which one is pressed
;	      It then adjusts the coefficient and exponent of the
;	      real time interupt controller (RTICTL) according
;	      to the assoc. button pressed.
;
;Parameters: Register's A, B, X, Y, CCR. and PC are pushed on
;	     the stack during interupt calls.
;
;Returns: Register's A, B, X, Y, CCR, and RTI return address to
;	  be stored back into the PC are pulled off the stack.
;****************************************************************
PTHISR		BRCLR	PIFH,SW5,PTHSW4
		BSET	PIFH,SW5 ; Clear the interupt flag bit by sending a 1 to the interupt device.
				 ; This indicates the device has been serviced.
		BRSET	RTICTL,%01110000,PTHSW4
		LDAA 	RTICTL
		ADDA 	#$10
		STAA	RTICTL

PTHSW4		BRCLR	PIFH,SW4,PTHSW3
		BSET 	PIFH,SW4
		BRCLR	RTICTL,%11100000,PTHSW3
		LDAA 	RTICTL
		SUBA 	#$10
		STAA	RTICTL

PTHSW3		BRCLR	PIFH,SW3,PTHSW2
		BSET	PIFH,SW3
		BRSET	RTICTL,%00001111,PTHSW2
		LDAA 	RTICTL
		ADDA 	#$01
		STAA	RTICTL

PTHSW2		BRCLR	PIFH,SW2,PTHISREND
		BSET	PIFH,SW2
		BRCLR	RTICTL,%00001111,PTHISREND
		LDAA 	RTICTL
		SUBA 	#$01
		STAA	RTICTL

PTHISREND	RTI

;****************************************************************
;Subroutine: RTIISR
;Description: Checks each button to identify which on is pressed
;
;
;
;Parameters: Register's A, B, X, Y, CCR. and PC are pushed on
;	     the stack during interupt calls.
;
;Returns: Register's A, B, X, Y, CCR, and RTI return address to
;	  be stored back into the PC are pulled off the stack.
;****************************************************************
RTIISR		BRCLR	CRGFLG,RTIF,RTIEND
		LDAA	#RTIF
		STAA	CRGFLG
		
CHKINC		; count interrupt for incrementing the display
		LDD	RTICOUNT
		SUBD	#1
		STD	RTICOUNT
		BNE	RTIEND
		MOVW	#ONESEC,RTICOUNT
		; increment display as a 4-digit value
		LDAB	DIGIT4
		JSR	INCASCII
		STAB	DIGIT4
		CMPB	#$30
		BNE	RTIEND

		LDAB	DIGIT3
		JSR	INCASCII
		STAB	DIGIT3
		CMPB	#$30
		BNE	RTIEND

		LDAB	DIGIT2
		JSR	INCASCII
		STAB	DIGIT2
		CMPB	#$30
		BNE	RTIEND

		LDAB	DIGIT1
		JSR	INCASCII
		STAB	DIGIT1
RTIEND		RTI

;****************************************************************
;Subroutine: INCASCII
;Description: Increments an ASCII digit and wraps the value 
;	      from 9 to 0.
;
;Parameters: Register's B
;
;Returns: Nothing
;****************************************************************
INCASCII	INCB
		CMPB	#$3A
		BNE	ENDINC
		LDAB	#$30
ENDINC		RTS

;*******************************************************************
		; pause for ~0.1 ms
pause		ldx	#600
loopp		dex
		bne	loopp
		rts	