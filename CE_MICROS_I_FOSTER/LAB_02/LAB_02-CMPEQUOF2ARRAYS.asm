****************************************************************************************
* Jacob S. Howarth
* Microcomputers I - Lab 2
* CE 320, Fall 2008 
* Prof. Foster 
*
* Description - This program compares two arrays, position by position, to see if they
* are identical. It returns a value $FF if identical, $00 if not. Arrays of zero length
* are considered identical.
****************************************************************************************

TRUE		EQU $FF		; Constant value representing true.
FALSE		EQU $00 	; Constant value representing false.


		ORG $1000
ARRAY1		DC.W $3000	; Store the address of the first element in the first array.
				; Address $1000 acts as a pointer to data in the first array.
ARRAY2		DC.W $3004	; Store the address of the first element in the second array.
				; Address $1002 acts as a pointer to the first element in the second array. 
LENGTH		DC.W $0000	; The length of each array is 4 values.

		ORG $3000
		DC.B $12,$22,$33,$44	; Initialize data in first array
		ORG $3004
		DC.B $12,$22,$33,$44	; Initialize data in second array

		ORG $2000
		LDX ARRAY1	; Load X with value 3000
		LDY ARRAY2	; Load Y with value 3004
		LDAA #TRUE	; Assume that both arrays are identical by storing true in $1006 
		STAA $1006
		LDD LENGTH	; Load D with length and store temporarily in address $1011.
		STD $1011
LOOP		BEQ DONE	; Exit program if array length is zero. This is also exiting
				; with the value TRUE for the arrays, signifying that arrays
				; with zero length are identical
				
		LDAA 0,X	; Compare position 1 of the first array to position 2 of the second. 
		CMPA 0,Y	; If they aren't equal (Z = 0), branch to label FALSE.
		BNE NOT_IDENTICAL	

		INX		; Else, increment X and Y to point to the second elements in the array
		INY
		LDD $1011	; Load length into D and decrement the length by 1. This controls the loop.
		SUBD #1
		STD $1011
		BRA LOOP
NOT_IDENTICAL	LDAB #FALSE	; If arrays are not equal, store a value of FALSE, signifying they aren't identical.
		STAB $1006
DONE		SWI		; End program.
		