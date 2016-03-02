; Define ports
PORTB	EQU	$0001
DDRB	EQU	$0003

PORTH	EQU	$0260
DDRH	EQU	$0262

PORTP	EQU	$0258
DDRP	EQU	$025a


; No arguments, sets up Dragon12+ 7-segment display
		ORG	$2800
INIT7SEG	MOVB	#$0F,DDRP
		MOVB	#$FF,DDRB
		RTS


;  Writes an ASCII character to one of the Dragon12+ 7-segment LEDs
;  Input:  Register A - mask for which digits will be illuminated.
		;DIG1	EQU	%00000001 ; left digit
		;DIG2	EQU	%00000010 ; 2nd from left
		;DIG3	EQU	%00000100 ; 2nd from right
		;DIG4	EQU	%00001000 ; right digit
;          Register B - ASCII value from $30 to $39, i.e. numbers
;  Output: affects 7-segment display
;
	ORG	$2880
DIGIT	PSHA
	JSR	toLED
	EORA	#%00001111
	PSHA
	LDAA	PORTP
	ANDA	#%11110000
	ORAA	0,SP
	STAA	PORTP
	STAB	PORTB
	LEAS	1,SP
	PULA
	RTS

;  Used by DIGIT subroutine
;  Converts an ASCII character to a 7-segment LED pattern
;  Input: Register B - ASCII value from $30 to $39, i.e. numbers
;  Output: Register B - 7-segment display pattern
;

	ORG	$2900
toLED	PSHX
	PSHC
	LDX	#BCDLED
	SUBB	#$30
	ABX
	LDAB	0,X
	PULC
	PULX
	RTS
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
