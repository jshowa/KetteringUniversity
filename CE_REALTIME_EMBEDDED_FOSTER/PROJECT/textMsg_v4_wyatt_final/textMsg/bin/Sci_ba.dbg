;********************************************************************************************************
;                                               uC/OS-II
;                                         The Real-Time Kernel
;
;                         (c) Copyright 2002, Jean J. Labrosse, Weston, FL
;                                          All Rights Reserved
;
;
;                                       PAGED S12X Specific code
;                                            (CODEWARRIOR)
;
; File         : probe_rs232_ba.s
; By           : Eric Shufro
;
; Notes        : THIS FILE *MUST* BE LINKED INTO NON_BANKED MEMORY!
;********************************************************************************************************

NON_BANKED:       section


;********************************************************************************************************
;                                           I/O PORT ADDRESSES
;********************************************************************************************************

PPAGE:            equ    $0030         ; Addres of PPAGE register (Assuming MC9S12DP256)


;********************************************************************************************************
;                                          PUBLIC DECLARATIONS
;********************************************************************************************************

    xdef   ProbeRS232_RxTxISR1


;********************************************************************************************************
;                                         EXTERNAL DECLARATIONS
;********************************************************************************************************

    xref   OSIntExit
    xref   OSIntNesting
    xref   OSTCBCur

    xref   Sci1_ISR_Handler


;********************************************************************************************************
;                                           uC/Probe Rx ISR
;
; Description : This routine is the uC/Probe Rx interrupt service routine
;
; Arguments   : none
;
; Notes       : 1) All USER interrupts should be modeled EXACTLY like this where the only
;                  line to be modified is the call to your ISR_Handler and perhaps the call to
;                  the label name ProbeRS232_RxISRHandler.
;********************************************************************************************************

ProbeRS232_RxTxISR1:
    ldaa   PPAGE                       ; Get current value of PPAGE register
    psha                               ; Push PPAGE register onto current task's stack

    inc    OSIntNesting                ; Notify uC/OS-II about ISR

    ldab   OSIntNesting                ; if (OSIntNesting == 1) {
    cmpb   #$01
    bne    SCI_ISR

    ldy    OSTCBCur                    ;     OSTCBCur->OSTCBStkPtr = Stack Pointer
    sts    0,y                         ; }


SCI_ISR:
    call   Sci1_ISR_Handler           ; Call Rx ISR handler. (See probe_rs232c.c)

    cli                               ; Optionally enable interrupts to allow interrupt nesting

    call   OSIntExit                  ; Notify uC/OS-II about end of ISR, a context switch may occur from within OSIntExit().

    pula                               ; Get value of PPAGE register
    staa   PPAGE                       ; Store into CPU's PPAGE register

    rti                               ; Return from interrupt to interrupted task.

