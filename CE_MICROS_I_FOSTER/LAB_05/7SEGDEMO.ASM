; 7segment constants

; constants for DIGIT subroutine
DIG1	EQU	%00000001 ; left digit
DIG2	EQU	%00000010 ; 2nd from left
DIG3	EQU	%00000100 ; 2nd from right
DIG4	EQU	%00001000 ; right digit


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


; Small demo of the 7-segment display
	ORG	$2200
	LDS	#$3600
	JSR	INIT7SEG
Loop2	LDAB	#$31
	LDAA	#DIG1
	JSR	DIGIT	
	LDAB	#$32
	LDAA	#DIG2
	JSR	DIGIT
	LDAB	#$33
	LDAA	#DIG3
	JSR	DIGIT
	LDAB	#$34
	LDAA	#DIG4
	JSR	DIGIT
	BRA	Loop2



