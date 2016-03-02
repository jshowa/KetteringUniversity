alphan	equ	4
alphad	equ	9

	org	1000h
Xin	dc.b	0,0,$40,$40,$40,$40,$40,$40,$40,$40,$40,$18,$40,$40,$40,$40
	org	1020h
Xave	ds.b	16
XinP	ds.w	1
XaveP	ds.w	1
count	ds.b	1

	org	2200h
	movb	#16,count
	movw	#Xin,XinP
	movw	#Xave,XaveP
loop	ldx	XinP
	clra
	ldab	0,X
	inx
	stx	XinP
	ldx	#alphad
	idiv
	tfr	X,A
	ldab	#alphan
	mul
	tfr	D,Y

	ldx	XaveP
	clrb
	ldaa	count
	cmpa	#16
	beq	skip
	ldab	-1,X
skip	clra
	ldx	#alphad
	idiv
	tfr	X,A
	ldab	#(alphad-alphan)
	mul
	tfr	Y,A
	aba
	ldx	XaveP
	staa	0,X
	inx
	stx	XaveP
	dec	count
	bne	loop
	swi
