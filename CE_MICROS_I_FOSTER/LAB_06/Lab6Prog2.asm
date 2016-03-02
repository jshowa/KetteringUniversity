;************************************************************
; Names: 	Prof. Foster
; Last Modified:08-25-2008
; Purpose:	Illuminate one LED in each digit of the 7-segment 
;               display at a time
;
; Inputs:	Switch SW2 - PTH pin 3
;               Switch SW3 - PTH pin 2
;               Switch SW4 - PTH pin 1
;               Switch SW5 - PTH pin 0
;
; Outputs:	7-segment diplay - PortB all pins
;		digit enable	   PTP pins 3-0
;
;************************************************************

;*** Constants section

PORTB		EQU		$0001
DDRB		EQU		$0003
PTH		EQU		$0260
DDRH		EQU		$0262
PTP		EQU		$0258
DDRP		EQU		$025A

; declare the bit pattern to enable each digit separately
DIGIT0		EQU		%00001110
DIGIT1		EQU		%00001101
DIGIT2		EQU		%00001011
DIGIT3		EQU		%00000111

; declare patterns that will examine each puch button separately
SW2		EQU		%00001000
SW3		EQU		%00000100
SW4		EQU		%00000010
SW5		EQU		%00000001


		ORG	$1000
		; create storage for the patterns for
		; each digit
DIG0		DS.B	1
DIG1		DS.B	1
DIG2		DS.B	1
DIG3		DS.B	1
		; space to store the previous button values
LAST		DS.B	1

		ORG 	$2000
		LDS	#$3600
		; init PortB to all outputs
		MOVB	#%11111111, DDRB	
		; init PTP to all outputs
		MOVB	#%11111111, DDRP
		; init PTH to all inputs
		MOVB	#%00000000, DDRH
		; begin with one LED segment illuminated in each digit
		MOVB	#%00000001,DIG0	
		MOVB	#%00000010,DIG1
		MOVB	#%00000001,DIG2
		MOVB	#%00000001,DIG3
		; intialize all switches to unpressed
		MOVB	#$f,LAST

		; update digit 0
d0		MOVB	DIG0,PORTB
		MOVB	#DIGIT0,PTP
		;JSR 	pause
		BRSET	PTH,SW2,setsw2 ; if SW2 is now pressed...
		BRCLR	LAST,SW2,d1    ; ...and wasn't pressed last time...
		BCLR	LAST,SW2       ; ... store value and update digit
		LSL	DIG0
		BNE	D1
		MOVB	#1,DIG0
		BRA	D1
setsw2		BSET	LAST,sw2		
		; update digit 1
d1		MOVB	DIG1,PORTB
		MOVB	#DIGIT1,PTP
		;JSR 	pause
		BRSET	PTH,SW3,setsw3 ; if SW3 is now pressed...
		BRCLR	LAST,SW3,d2    ; ...and wasn't pressed last time...
		BCLR	LAST,SW3       ; ... store value and update digit
		LSL	DIG1
		BNE	D2
		MOVB	#1,DIG1
		BRA	D2
setsw3		BSET	LAST,SW3

		; update digit 2
d2		MOVB	DIG2,PORTB
		MOVB	#DIGIT2,PTP
		;JSR 	pause
		BRSET	PTH,SW4,setsw4  ; if SW4 is now pressed...
		BRCLR	LAST,SW4,d3     ; ...and wasn't pressed last time...
		BCLR	LAST,SW4	; ... store value and update digit
		LSL	DIG2
		BNE	D3
		MOVB	#1,DIG2
		BRA	D3
setsw4		BSET	LAST,SW4

		; update digit 3
d3		MOVB	DIG3,PORTB
		MOVB	#DIGIT3,PTP
		;JSR 	pause
		BRSET	PTH,SW5,setsw5  ; if SW5 is now pressed...
		BRCLR	LAST,SW5,repeat ; ...and wasn't pressed last time...
		BCLR	LAST,SW5	; ... store value and update digit
		LSL	DIG3
		BNE	repeat
		MOVB	#1,DIG3
		BRA	repeat
setsw5		BSET	LAST,SW5
repeat		LBRA	d0

; pause for ~0,1 ms
pause		ldx	#600
loop		dex
		bne	loop
		rts