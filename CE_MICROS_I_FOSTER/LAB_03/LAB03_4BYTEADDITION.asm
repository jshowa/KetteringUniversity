;*************************************************************
;Jacob S. Howarth
;Microcomputers I - Lab 3
;CE 320, Fall 2008
;Prof. Foster
;
;Description: This program will take an array of 4 byte values
;	      and sum them up. The program will also indicate 
;	      unsigned and signed overflow for the sum.
;*************************************************************

;Define constants
TRUE	EQU	$FF ;true equals a byte of 1's
FALSE	EQU	$00 ;false = a byte of 0's

;Define space for the sum, array start address, overflow and length.
		ORG $1000
ARRAY		DS.W	1 ;this specifies space for the array start address
		ORG $1004
LENGTH		DS.B    1 ;this provides address for the length.
		ORG $1010
SUM		DS.W	2 ;this provides space for the sum.
		ORG $1014
UNSGN_OVRFLOW	DS.B	1 ;this provides space for the unsigned overflow
SGN_OVRFLOW	DS.B	1 ;this provides space for the signed overflow

		ORG $2000
		MOVB #FALSE,UNSGN_OVRFLOW ;clear contents in address for unsigned overflow
		MOVB #FALSE,SGN_OVRFLOW	;clear contents in address for signed overflow
		CLR SUM		;--
		CLR SUM + 1	;  |
		CLR SUM + 2	;  |--> clear the contents of sum to $00
		CLR SUM + 3	;--
		LDX  ARRAY	
		LDAA LENGTH
LOOP		BEQ  CHECK_OVRFLOW
		LDAB 3,X	;use index addressing to grab contents of memory from 
		ADDB SUM + 3	;X + 3 and add to SUM + 3
		STAB SUM + 3	;store result in SUM + 3
		LDAB 2,X	;use index addressing to grab contents of memory from
		ADCB SUM + 2	;X + 2 and add to SUM + 2
		STAB SUM + 2	;store result in SUM + 2
		LDAB 1,X	;use index addressing to grab contents of memory from
		ADCB SUM + 1	;X + 1 and add to SUM + 1
		STAB SUM + 1	;store result in SUM + 1
		LDAB 0,X	;use index addressing to grab contents of memory from
		ADCB SUM 	;X and add to SUM
		STAB SUM	;store result in SUM
		LEAX 4,X	
		DECA		;decrement X and A by 4
		DECA
		DECA
		DECA
		BRA LOOP
CHECK_OVRFLOW	BCC NO_UNSIGNED_OVRFLOW ;if C bit is clear, skip assigning USGN_OVRFLOW address to TRUE
		MOVB #TRUE,UNSGN_OVRFLOW
NO_UNSIGNED_OVRFLOW  BVC NO_SIGNED_OVRFLOW ;if V bit is clear, skip assigning SGN_OVRFLOW address to TRUE
		MOVB #TRUE,SGN_OVRFLOW
NO_SIGNED_OVRFLOW SWI ;end
