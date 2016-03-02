;*************************************************************
;Jacob S. Howarth
;Microcomputers I - Lab 4
;CE 320, Fall 2008
;Prof. Foster
;
;Description: This program will convert a 2-byte hexadecimal
;	      value to a 3-byte BCD value and will also convert
;	      a 3-byte hexadecimal value to ASCII without 
;	      leading zeros.
;*************************************************************


;Define constants
		ORG $1000
HEX_VALUE_ADD	DC.W $3000 ;A constant value containing the address of the 
			   ;hexadecimal number to be converted.
BCD_ADDRESS	DC.W $3002 ;A constant value containing the address of where the
			   ;BCD value will be stored.
ASCII_OFFSET	EQU	$30	;Value used to convert a BCD value to ASCII

;Define program input data
		ORG $3000
		DC.W $0080 ;The hexadecimal value to be converted.
		ORG $3002
		DC.B $00,$00,$00,$00,$00,$00,$00,$00,$00,$00,$00,$00,$00,$00,$00 ;Clear the 
								  ;address line being used.		
	
;Hexadecimal to BCD converter program beginning:

		ORG $2000
		LDD BCD_ADDRESS
		ADDD #5		;Since the maximum number to be converted is 65535, when
				;the division begins, there will be five bytes used to store
				;each digit so the address is incremented by 5 since during the
				;first division, the least significant digit is returned. This is
				;so the number displays correctly from left to right.
		TFR D,Y
		LDX HEX_VALUE_ADD
		LDD 0,X		;Load D with the hex value being converted
LOOP1		BEQ DONE1
		LDX #10		;Load X with $0A since the formula fo IDIV is D/X
		IDIV
		STAB 0,Y	;Store the least significant digit into the first position
		TFR X,D		;Since X stores the quotient after IDIV instruction, D is reset to the new quotient.
		DEY		;Decrementing Y moves it to the left in the memory map.
		CPX #0		;Repeat until the quotient X is zero.
		BRA LOOP1
DONE1		LDY #$3007 	;Load Y with the address of the least significant digit.
		ROL -1,Y	;Rotate left four times to get rid of the leading zero in
		ROL -1,Y	;position pointed to by Y minus 1.
		ROL -1,Y
		ROL -1,Y
		LDAA -1,Y	;Add the value in Y - 1 to the value pointed to by Y and store the 
		ADDA 0,Y	;value in the address contained in Y.
		STAA 0,Y
		DEY		;Decrement the Y pointer
		MOVB -1,Y,0,Y	;Shift each of the succeeding digits to the right.
		MOVB -2,Y,-1,Y
		MOVB -3,Y,-2,Y
		LDAB -1,Y	;Check to the left of the digit succeeding the proceeding digit, if $00 then end program.
LOOP2		BEQ DONE2
		ROR -1,Y	;If the digit succeeding the proceeding digit is not $00, then rotate that digit right.
		ROR -1,Y
		ROR -1,Y
		ROR -1,Y
		ROR -1,Y
		LDAA 0,Y	;Add the rotated digit to the position before the position Y is currently pointing.
		ADDA -1,Y
		STAA 0,Y
		DEY		;Decrement the Y pointer and proceed to shift all succeeding bytes, if
				;any to the right
		MOVB -1,Y,0,Y
		MOVB -2,Y,-1,Y
		MOVB -3,Y,-2,Y
		LDAB -2,Y	;Check the digit succeeding the most significant digit, if $00 end program, if not repeat right rotates.
		BRA LOOP2
DONE2		CLR -1,Y


;Hexadecimal to ASCII program beginning:


		LDD BCD_ADDRESS	;Load the address of where the BCD value will be stored.
		ADDD #10	;Add ten to the BCD address to offset it from the BCD value
				;stored from the above program
		TFR D,Y
		LDX HEX_VALUE_ADD ;Load the hexadecimal value to be converted
		LDD 0,X
		BEQ ASCII_ZERO	  ;If the hex value is zero, skip the loop
LOOP3		BEQ DONE3	  ;The above loop is the same as in the above program
		LDX #10		  ;the only thing different is the adding of the
		IDIV		  ;ASCII offset to each BCD digit.|
		ADDB #ASCII_OFFSET;<------------------------------|
		STAB 0,Y
		TFR X,D
		DEY
		CPX #0
		BRA LOOP3
DONE3		LDX BCD_ADDRESS	  ;When complete append the null string to the end.
		MOVB #$00,$0B,X
		TFR Y,X
SHIFT		LDY #$3008	  ;Shift the bytes until there are no leading zero's in the
		LDAA 0,Y	  ;ASCII string by checking the $3008 address.
		BNE END_OF_PROGRAM
		MOVB 1,Y,0,Y	  ;Perform the shift.
		MOVB 2,Y,1,Y
		MOVB 3,Y,2,Y
		MOVB 4,Y,3,Y
		MOVB 5,Y,4,Y	
SKIP		BRA SHIFT
ASCII_ZERO	ADDD #ASCII_OFFSET ;If the hex value is zero, add the ASCII offset to zero
		STAB 0,Y
		LDX BCD_ADDRESS	  ;Store in position pointed to by Y and append the null string.
		MOVB #$00,$0B,X
END_OF_PROGRAM	SWI ;End program

		
		