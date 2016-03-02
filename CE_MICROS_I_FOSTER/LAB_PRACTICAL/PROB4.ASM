*  This program has been written to test your debugging skills.
*  You don't need to know what the program or the instructions
*  contained within it do.
*
testkey	equ	$1F20
target 	equ	$1100

	org 	$3000
count	ds.b	1	
value	ds.b	1


	org	$2000
	lds	#$3C00
        ldaa    #10
        staa    count
        clrb
        ldaa    #$41 
        aba
        psha
        ldaa    #$27 
        staa    value
        ldx     #testkey
        ldy     #target
loop    tst     count
        beq     done
        tfr	A,B
        ldab    value
        jsr     mcrypt
        stab    0,Y
        staa   	40,Y
        iny
        inc     value
        dec    	count
        bra     loop
done    pulb
        jsr 	 mcrypt
	swi





permute	ldx	#16
perloop	bitb	#%00000001
	beq	noxor
	eorb	#$48
noxor	rorb
	bcc	nocbit
	addb	#$80
nocbit	dbne	x,perloop
	rts


mcrypt	eorb	testkey
	jsr	permute
	eorb	testkey+1
	jsr	permute
	rts