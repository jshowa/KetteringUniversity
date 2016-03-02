;**********************************************************
; Jacob S. Howarth
; Microcomputers I, Fall 2008
; Professor Foster
;
; Purpose: This program converts a 2-byte hexadecimal
; value in the range from 0 to 9999 to a 4-byte ASCII 
; value and displays this value on the 7-segment displays.
;**********************************************************

;Define the address of the two byte hexadecimal value and the place to store the ASCII value.
		ORG 	$1000
HEX_VALUE	DC.W	$0100
ASCII_STRING	DS.W	2

;Define DIGIT constants to be displayed for the digit subroutine
DIG1	EQU	%00000001 ; left digit
DIG2	EQU	%00000010 ; 2nd from left
DIG3	EQU	%00000100 ; 2nd from right
DIG4	EQU	%00001000 ; right digit

;Define ASCII offset and base 10 divisor
ASCII_OFFSET	EQU	$30

; initializes the 7 segment display
; inputs - none
; outputs - none
; no affect on any registers
INIT7SEG EQU	$2800

; writes an ASCII character to the 7-segment display
; inputs -  Register A: one of the 4 defined constants, by value
;           to select which digit to write
;	    Register B: ASCII value to write, by value
;           Currently, must be $30 to $39
; outputs - changes 7-segment
; Does not preserce registers
DIGIT	EQU	$2880

;Main program start
	ORG $2000
	LDS #$3600 
	JSR INIT7SEG ;1) Initialize the DDR registers for PTP and PORTB to outputs.
	LDX #ASCII_STRING
	PSHX
	LDX #HEX_VALUE ;2) Load register X with hex value address and push onto stack
	PSHX 
	JSR ASCII_CONVRT ;3) Call the hex to ASCII conversion subroutine
	PULX
	PULY
Loop2	LDAB	0,Y
	LDAA	#DIG1
	JSR	DIGIT	
	LDAB	1,Y
	LDAA	#DIG2
	JSR	DIGIT
	LDAB	2,Y
	LDAA	#DIG3
	JSR	DIGIT
	LDAB	3,Y
	LDAA	#DIG4
	JSR	DIGIT
	BRA	Loop2	


;Subroutine that converts a 2-byte hexadecimal value to 4-byte ASCII
; * Arguments - Reference address of hex value passed on the stack
; * Return Value - Reference address of ASCII string returned on stack
; * Registers are preserved.
	       ORG $2C00
ASCII_CONVRT   LDX 2,SP ;Load X with the contents of the SP offset by 2
	       LDD 0,X	;Load D with the hex value
	       LDY 4,SP ;Load Y with the LSB of ASCII String 
	       LDX #10
	       IDIV	;Load X with 10 and divide D by X and store quotient in X and remainder in D
	       ADDB #ASCII_OFFSET ;Add $30 to register B
	       STAB 3,Y		;Store value of register B in position Y and decrement Y and reload
	       XGDX 		;D with the new quotient
	       LDX #10
	       IDIV	;Load X with 10 and divide D by X and store quotient in X and remainder in D
	       ADDB #ASCII_OFFSET ;Add $30 to register B
	       STAB 2,Y		;Store value of register B in position Y and decrement Y and reload
	       XGDX 		;D with the new quotient
	       LDX #10
	       IDIV	;Load X with 10 and divide D by X and store quotient in X and remainder in D
	       ADDB #ASCII_OFFSET ;Add $30 to register B
	       STAB 1,Y		;Store value of register B in position Y and decrement Y and reload
	       XGDX 		;D with the new quotient
               LDX #10
	       IDIV	;Load X with 10 and divide D by X and store quotient in X and remainder in D
	       ADDB #ASCII_OFFSET ;Add $30 to register B
	       STAB 0,Y		;Store value of register B in position Y and decrement Y and reload
	       XGDX 		;D with the new quotient
END_CONVERSION RTS
