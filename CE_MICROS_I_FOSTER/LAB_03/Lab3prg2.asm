;************************************************************
; Names: 	Prof. Foster
; Last Modified:08-04-2008
; Purpose:	Add a list of unsigned, one-byte values as a 
;		two-byte sum, and indicate unsigned overflow
;
; Inputs:	Address of the array, locations 1030h-1031h
; 		Length of the array, one-byte, location 1032h
;
; Outputs:	Unsigned overflow, 00h for false, FFh for true
;		location 1050h
;		Two-byte sum, location 1051h-1052h
;
;
;************************************************************

;*** Constants section
true	equ	$ff		; define arbirary value for true
false	equ	$00		; define arbirary value for false


;*** Variables and I/O section
; declare inputs
	org	1030h
array	dc.w	3000h		; input:starting address of table
length	dc.w	0400h		; input:length of table


; declare outputs
	org	1050h
ovflow	ds.b	1		; output: unsigned overflow
sum	ds.w	1			; output: two-byte sum

; declare internal variables
	org 3000h
	fill 255,$80	; 30ff
	fill 255,$80	; 31fe
	fill 255,$80	; 32fd
	fill 255,$80	; 33fc
	fill 4,$80	; 33ff	

;*** Program section
	org	2000h
	movw 	#0,sum		; Initialize sum to 0
	movb	#false,ovflow	; initialze overflow to "false"
	ldx	 array		; Point to the first element of the array
	ldd length		; make a working copy of the length
	tfr	D,Y
loop	beq	done		; go to end if there are no more elements
	clra			; load next element and extend to 2 bytes
	ldab	0,X		; and element to the running sum
	addd	sum	
	std	sum
	bcc	sum_ok		; indicate overflow if this operation
	movb	#true,ovflow	; generated an invalid answer
sum_ok  inx			; point to next element
	dey			; decrement number of elements remaining
	bra	loop
done	swi