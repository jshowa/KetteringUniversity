;**************************************************************
;* This stationery serves as the framework for a              *
;* user application. For a more comprehensive program that    *
;* demonstrates the more advanced functionality of this       *
;* processor, please see the demonstration applications       *
;* located in the examples subdirectory of the                *
;* Freescale CodeWarrior for the HC12 Program directory       *
;**************************************************************
; Include derivative-specific definitions
            INCLUDE 'derivative.inc'
           

; export symbols
            XDEF Entry, _Startup, main
            ; we use export 'Entry' as symbol. This allows us to
            ; reference 'Entry' either in the linker .prm file
            ; or from C/C++ later on

            XREF __SEG_END_SSTACK      ; symbol defined by the linker for the end of the stack
            XREF priority



; variable/data section
MY_EXTENDED_RAM: SECTION

priorities  ds.b 8  ; answers
count       ds.b 1  ; working number of tables


MY_CONSTANTS: SECTION
tableaddr   dc.w table1,table2,table3,table4,table5,table6,table7,table8
table1      dc.b %00000001,%00000010,%00000100,%00001000,%00010000,%00100000,%01000000,%10000000 ; answer 0
table2      dc.b %00000000,%00000010,%00000100,%00001000,%00010000,%00100000,%01000000,%10000000 ; answer 9
table3      dc.b %00000000,%00000000,%00000100,%00001000,%00010000,%00100000,%01000000,%10000000 ; answer 18
table4      dc.b %00000000,%00000000,%00000000,%00001000,%00010000,%00100000,%01000000,%10000000 ; answer 27
table5      dc.b %00000000,%00000000,%00000000,%00000000,%00010000,%00100000,%01000000,%10000000 ; answer 36
table6      dc.b %00000000,%00000000,%00000000,%00000000,%00000000,%00100000,%01000000,%10000000 ; answer 45
table7      dc.b %00000000,%00000000,%00000000,%00000000,%00000000,%00000000,%01000000,%10000000 ; answer 54
table8      dc.b %00000000,%00000000,%00000000,%00000000,%00000000,%00000000,%00000000,%10000000 ; answer 63


; code section
MyCode:     SECTION
main:
_Startup:
Entry:
            LDS  #__SEG_END_SSTACK     ; initialize the stack pointer

            LDX   #tableaddr   ; point x at array of table addresses
            LDY   #priorities
            MOVB  #8,count     ; load b with number of tables         
Loop:
            LDD   2,X+         ; pass address of input to sub ... point to next input
            PSHX               ; save regs in use by main program
            PSHY
            JSR   priority
            PULY               ; restore regs in use by main program
            PULX
            STAB  1,Y+         ; store answer to array.. point to next slot in answer array
            DEC   count        ; reduce number of inputs left to process
            BNE   Loop         ; repeat until no more tables

EndlessLoop:
            BRA   EndlessLoop          
