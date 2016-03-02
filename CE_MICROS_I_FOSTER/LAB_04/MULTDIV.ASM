alphan	equ	4
alphad	equ	9

	org	1000h
Xin	dc.b	0,0,$40,$40,$40,$40,$40,$40,$40,$40,$40,$18,$40,$40,$40,$40
Xave	ds.b	16
XinP	ds.w	1
XaveP	ds.w	1
count	ds.b	1

	org	2000h
	movb	#16,count
	movw	#Xin,XinP
	movw	#Xave,XaveP
loop	ldx	XinP
	ldaa	0,X
	inx
	stx	XinP
	ldab	#alphan
	mul
	ldx	#alphad
	idiv
	tfr	X,Y
	ldx	XaveP
	clra
	ldab	count
	cmpb	#16
	beq	skip
	ldaa	-1,X
skip	ldab	#(alphad-alphan)
	mul
	ldx	#alphad
	idiv
	tfr	X,B
	tfr	Y,A
	aba
	ldx	XaveP
	staa	0,X
	inx
	stx	XaveP
	dec	count
	bne	loop
	swi
