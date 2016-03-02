;************************************************************
; Names: 	Prof. Foster
; Last Modified:09-1-2008
; Purpose:	Implement a timer that displays elapsed time
;		as a 4-digit number of seconds
;
; Inputs:	Switch SW5 - PTH pin 0
;
; Outputs:	7-segment diplay - PortB all pins
;		digit enable	   PTP pins 3-0
;	
; Dependencies:	7segment.s19 is used as a driver for 7-segs
;
;************************************************************

;*** Constants section
INIT7SEG	EQU	$2800
DIGIT		EQU	$2880

; constants for DIGIT subroutine
DIG1		EQU	%00000001 ; left digit
DIG2		EQU	%00000010 ; 2nd from left
DIG3		EQU	%00000100 ; 2nd from right
DIG4		EQU	%00001000 ; right digit

PORTB		EQU	$0001
DDRB		EQU	$0003

PTH		EQU	$0260
DDRH		EQU	$0262
PIEH		EQU	$0266
PIFH		EQU	$0267

PTP		EQU	$0258
DDRP		EQU	$025A

CRGFLG		EQU	$0037
RTIF		EQU	%10000000
CRGINT		EQU	$0038
RTIE		EQU	%10000000
RTICTL		EQU	$003B

SW5		EQU	%00000001

ONESEC		EQU	122*8

		; define the interrupt vectors
		ORG	$3E70
		DC.W	RTIISR

		ORG	$3E4C
		DC.W	PTHISR

		ORG	$1000
		; create storage for the patterns for
		; each digit
DIGIT1		DS.B	1
DIGIT2		DS.B	1
DIGIT3		DS.B	1
DIGIT4		DS.B	1
RTICOUNT	DS.B	1	; tracks number of interrupts

		ORG 	$2000
		LDS	#$3600
		JSR	INIT7SEG
		; configure/enable SW5 interrupt (portH)
		BCLR	PTH,SW5
		BSET	PIEH,SW5
		; configure RTI device 		
		; NOTE: RTI interrupt is enabled/disabled
		; by PTHISR
		MOVB	#$40,RTICTL	
		
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


;*******************************************************************
		; increment a digit as ASCII and wrap from 9 to 0
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

;*******************************************************************
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

;*******************************************************************
PTHISR		BRCLR	PIFH,SW5,PTHEND
		BSET	PIFH,SW5
		BRSET	CRGINT,RTIE,DISABLE
		LDD	DIGIT1
		CPD	#$3030
		BNE	CLEARTIME
		LDD	DIGIT3
		CPD	#$3030
		BNE	CLEARTIME
		;start timer
		MOVW	#ONESEC,RTICOUNT
		BSET	CRGINT,RTIE	
		BRA	PTHEND
		
		;clear timer
CLEARTIME	MOVB	#$30,DIGIT1
		MOVB	#$30,DIGIT2
		MOVB	#$30,DIGIT3
		MOVB	#$30,DIGIT4
		BRA	PTHEND

		;stop timer
DISABLE		BCLR	CRGINT,RTIE

PTHEND		RTI