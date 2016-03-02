NON_BANKED:       section
  
;********************************************************************************************************
;                                           I/O PORT ADDRESSES
;********************************************************************************************************

PPAGE:            equ    $0030         ; Addres of PPAGE register (assuming MC9S12 (non XGATE part)

 
    xdef   Speaker_ISR   
    
;********************************************************************************************************
;                                         EXTERNAL DECLARATIONS
;********************************************************************************************************
   
    xref   OSIntExit
    xref   OSIntNesting  
    xref   OSTCBCur     
    xref   Speaker_ISR_Handler 

Speaker_ISR
    ldaa   PPAGE                       ;  Get current value of PPAGE register                                
    psha                               ;  Push PPAGE register onto current task's stack

    inc    OSIntNesting                ;  Notify uC/OS-II about ISR

    ldab   OSIntNesting                ;  if (OSIntNesting == 1) {    
    cmpb   #$01                        ;  
    bne    Speaker_ISR1           ;  

    ldy    OSTCBCur                    ;      OSTCBCur->OSTCBStkPtr = Stack Pointer     
    sts    0,y                         ;  }                                          

Speaker_ISR1:
    call   Speaker_ISR_Handler    ;  Call the Seven Segment Display ISR Handler in SevenSegDisp_BSP.c

    cli                                ;  Enable interrupts to allow interrupt nesting
       
    call   OSIntExit                   ;  Notify uC/OS-II about end of ISR
    
    pula                               ;  Get value of PPAGE register
    staa   PPAGE                       ;  Store into CPU's PPAGE register                                
        
    rti                                ;  Return from interrupt, no higher priority tasks ready.
    
