;*******************************************
; Jacob S. Howarth
; Microcomputers I - Test File
; Professor Foster
; CE-320, 11/08/08
;*******************************************
	ORG $1000
	DS.B 4
	
	ORG $2000
	LDAA #4
	LDX  #$1000
AGAIN	LSR 0,X
	ROR 1,X
	ROR 2,X
	ROR 3,X
	DBNE A,AGAIN

