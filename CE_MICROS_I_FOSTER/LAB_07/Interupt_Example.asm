DIGIT      EQU  $2880    ; displays ASCII number on 7-seg 
                         ; Reg A, which of 4 digits 
                         ; Reg B, ASCII number 
INIT7SEG   EQU  $2800    ; initializes 7-segment display 
 
; constants for DIGIT subroutine 
DIG1    EQU  %00000001   ; left digit 
DIG2    EQU  %00000010   ; 2nd from left 
DIG3    EQU  %00000100   ; 2nd from right 
DIG4    EQU  %00001000      ; right digit 

; address  definitions for Port H 
PTH     EQU  $0260    ; Port H Data 
DDRH    EQU  $0262    ; Data Direction Reg for H 
PIEH    EQU  $0266    ; Interrupt Enable for H 
PIFH    EQU  $0267    ; Interrupt Flags for H 
 
SW2    EQU  %00001000  ; Pattern for increment button 
SW3    EQU  %00000100  ; Pattern for decrement button 
 
       ORG  $3E4C 
       DC.W  PTHISR
       
       ORG  $1000 
       ; create storage for the patterns for 
       ; each digit 
 
DIGIT1   DS.B  1 
DIGIT2   DS.B  1 
DIGIT3   DS.B  1 
DIGIT4   DS.B  1 
 
       ORG   $2000 
       LDS  #$3600 
       ; init PTH to all inputs 
       MOVB  #%00000000, DDRH 
       ; enable interrupts on pushbutton SW2 and SW3 
       ; disable interrupts on all other pins  
       MOVB  #(SW2+SW3),PIEH 
 
       ; initialize 7-segment values 
       MOVB  #$30,DIGIT1 
 
       JSR  INIT7SEG 
       ; unmask interrupts... 
       CLI     
       ; and wait for things to happen 
 
Loop    LDAA  #DIG1 
        LDAB  DIGIT1 
        JSR  DIGIT 
        BRA   Loop 

         ORG   $2300 
PTHISR   BRCLR  PIFH,SW2,PTHSW3 
         BSET   PIFH,SW2 
         LDAB   DIGIT1 
         JSR    INCASCII 
         STAB   DIGIT1 
 
PTHSW3     BRCLR  PIFH,SW3,PTHISREND 
           BSET   PIFH,SW3 
           LDAB   DIGIT1 
           JSR    DECASCII 
           STAB   DIGIT1 
 
PTHISREND  RTI 
 
 
 
INCASCII  CMPB  #$39 
          BHS  ENDINC 
          INCB 
ENDINC    RTS   
 
 
DECASCII  CMPB  #$30 
          BLS  ENDDEC 
          DECB 
ENDDEC    RTS
 