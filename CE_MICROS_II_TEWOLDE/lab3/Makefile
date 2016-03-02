# MPLAB IDE generated this makefile for use with GNU make.
# Project: ledCounter.mcp
# Date: Fri Oct 21 14:39:00 2011

AS = pic32-as.exe
CC = pic32-gcc.exe
LD = pic32-ld.exe
AR = pic32-ar.exe
HX = pic32-bin2hex.exe
RM = rm

ledCounter.hex : ledCounter.elf
	$(HX) "ledCounter.elf"

ledCounter.elf : ledCounter.o
	$(CC) -mdebugger -mprocessor=32MX360F512L "ledCounter.o" -o"ledCounter.elf" -Wl,-L"C:\Program Files\Microchip\MPLAB C32\lib",-L"C:\Program Files\Microchip\MPLAB C32\pic32mx\lib",--defsym=__MPLAB_BUILD=1,--defsym=__MPLAB_DEBUG=1,--defsym=__MPLAB_DEBUGGER_REAL_ICE=1,--defsym=__ICD2RAM=1,-Map="ledCounter.map"

ledCounter.o : ledCounter.c
	$(CC) -mprocessor=32MX360F512L -x c -c "ledCounter.c" -o"ledCounter.o" -MMD -MF"ledCounter.d" -D__DEBUG -D__MPLAB_DEBUGGER_REAL_ICE=1 -g

clean : 
	$(RM) "ledCounter.o" "ledCounter.elf" "ledCounter.hex"

