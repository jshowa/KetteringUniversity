;************************************************************
; Names: 	Prof. Foster
; Last Modified:08-25-2008
; Purpose:	Illuminate one LED in digit 2 of the 7-segment 
;               display at a time
;
; Inputs:	Switch SW2 - PTH pin 1
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


		ORG 		$2000
		; init PortB to all outputs
		MOVB	#%11111111, DDRB	
		; init PTP to all outputs
		MOVB	#%11111111, DDRP
		; init PTH to all inputs
		MOVB	#%00000000, DDRH
		; begin with one LED segment illuminated
		MOVB	#%00000001, PORTB	
		; Enable digit 2, disable the other three
		MOVB	#%11111011, PTP
		; Wait for the push button SW4 to be depressed...
NOTPUSH		BRSET	PTH,%00000010, NOTPUSH
;		LDX	#$6000
;DEBOUNCE	DEX
;		BNE	DEBOUNCE
		;.., and verify it is still depressed ~1 ms later
;		BRSET	PTH,%00000010, NOTPUSH
		; illuminate the next LED
		LSL	PORTB
		BNE	PUSHED
		MOVB	#%00000001,PORTB
PUSHED		; Wait for the push button SW4 to be released...
;		BRCLR	PTH,%00000010, PUSHED
		;LDX	#$6000
;DEBOUNCE2	DEX
;		BNE	DEBOUNCE2
		;.., and verify it is still released ~1 ms later
;		BRCLR	PTH,%00000010, NOTPUSH
		BRA	NOTPUSH