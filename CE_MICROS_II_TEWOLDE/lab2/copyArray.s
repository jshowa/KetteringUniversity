		.include "p24Fxxxx.inc"
		.global __reset
		.bss
src:	.space 2			;source address of the array
dest:	.space 2			;destination address of the copy
len:	.space 2			;length of array
		.text
	__reset:
mov #__SP_init, W15			;initialize stack
mov #__SPLIM_init, W0
mov W0, SPLIM				;set stack limit
.equ	src, 0x0800			;set input parameters
.equ	dest, 0x0808
.equ	len, 4

mov #len, W0				;store length of array and see if it is zero
cp0 W0
bra Z, done
mov #src, W1				;copy the source address and destination address into registers
mov #dest, W2
loop: mov [W1++], [W2++]	;take the contents of 1st pos in src array and copy to 1st pos of dest array
	  dec W0, W0			;decrement the length
	  cp0 W0
	  bra NZ, loop
done:						;end of program
	goto done
	.end 

