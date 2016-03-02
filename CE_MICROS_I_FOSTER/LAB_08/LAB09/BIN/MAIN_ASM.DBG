; export symbols
            XDEF dig
            XDEF digit, enableInts

; include derivative specific macros
            INCLUDE 'mc9s12dg256.inc'

; variable/data section
MY_EXTENDED_RAM: SECTION

dig:     DC.B  1,2,4,8  ; define constants for digit subroutine

BCDLED	DC.B	%00111111	;0
	      DC.B	%00000110	;1
	      DC.B	%01011011	;2
	      DC.B	%01001111	;3
	      DC.B	%01100110	;4
	      DC.B	%01101101	;5
	      DC.B	%01111101	;6
	      DC.B	%00000111	;7
	      DC.B	%01111111	;8
	      DC.B	%01100111	;9
; code section
MyCode:     SECTION

; ASCII value in B, digit is on the stack
; no output
digit	PSHA
      LDAA  3,SP         ; load second parameter
	    JSR	  toLED        ; convert B to pattern
	    EORA	#%00001111
	    PSHA
	    LDAA	PTP
	    ANDA	#%11110000
	    ORAA	0,SP
	    STAA	PTP
	    STAB	PORTB
	    LEAS	1,SP
	    PULA
	    RTS

; converts ASCII number to 7-segment pattern
; ASCII input in B
; pattern output in B	    
toLED	PSHX
	    PSHC
	    LDX	#BCDLED
	    SUBB	#$30
	    ABX
	    LDAB	0,X
	    PULC
	    PULX
	    RTS
; no input or output, simple enables global interrupts
enableInts
      CLI
      RTS
