#include <hidef.h>
#include <string.h>

/* PORTB definitions */
#define PORTB    (*((volatile unsigned char*)(0x0001)))     
#define DDRB     (*((volatile unsigned char*)(0x0003))) 

/* RTI definitions */
#define CRGINT   (*((volatile unsigned char*)(0x0038))) 
#define CRGFLG   (*((volatile unsigned char*)(0x0037))) 
#define RTICTL   (*((volatile unsigned char*)(0x003B))) 

/* SCI0 definitions */
#define SCI0BDL   (*((volatile unsigned char*)(0x00C9))) 
#define SCI0CR1   (*((volatile unsigned char*)(0x00CA))) 
#define SCI0CR2   (*((volatile unsigned char*)(0x00CB))) 
#define SCI0SR1   (*((volatile unsigned char*)(0x00CC))) 
#define SCI0DRL   (*((volatile unsigned char*)(0x00CF))) 

/* global variables definitions */
static int waittime = 5;
static unsigned char redButtonDown = FALSE, blueButtonDown = FALSE;
static long absoluteTime = 0;



static void setNextSegment(void){
  static unsigned char seg = 0;
  static const char segs[]={~0x01,~0x02,~0x04,~0x08,~0x10,~0x20};
  seg++;
  if (seg == 6) {
    seg = 0;
  }
  PORTB = segs[seg];
}

static void displayNumber(Byte nbr){
  static const char segs[]={~0x3f,~0x06,~0x5b,~0x4f,~0x66,~0x6d,~0x7d,~0x07,~0x7f,~0x6f};
  PORTB = segs[nbr];
} 

static void increaseSpeed(void){
	if(waittime > 1) waittime--;
}

static void reduceSpeed(void){
	if(waittime < 9) waittime++;
}

static void WriteToSCI0(const char *text){
  while (*text != '\0'){
    while (!(SCI0SR1 & 0x80));  /* wait for output buffer empty */
    SCI0DRL = *text++;
  }
}

static void executeCommand(const char *cmd){
  if (!strcmp(cmd,"stop")) {
    WriteToSCI0("\nApplication stopped by BGND instruction.\n\n");
    __asm BGND;
  }
  if ((*cmd > '0') && (*cmd <= '9') && (*(cmd+1) == '\0')){
    waittime = 10 - (*cmd - '0');
    WriteToSCI0("\nNew speed set to ");
    WriteToSCI0(cmd);
    WriteToSCI0(".\n");
  } else {
    WriteToSCI0("\nIllegal command.\n\n");
  }
}

#pragma CODE_SEG __NEAR_SEG NON_BANKED
interrupt void SCI0_ISR(void){
  static char command[5]; /* enough to hold "stop" or a single digit number */
  static unsigned char i=0; /* index into command */
  unsigned char rc;
  rc = SCI0SR1; /* dummy read to clear flags */
  rc = SCI0DRL; /* data read */
  if (i >= sizeof(command)-1 || rc == 0x0D) {
    command[i] = '\0';
    i = 0;
    executeCommand(command);
  } else {
    command[i] = rc;
    i++;
  }
  SCI0DRL = rc;
}

#pragma CODE_SEG __NEAR_SEG NON_BANKED
interrupt void RTI_ISR(void) {
  absoluteTime++;
  /* clear RTIF bit */
  CRGFLG = 0x80;  
}

#pragma CODE_SEG DEFAULT
static void RTIInit(void) {
  /* setup of the RTI interrupt frequency */
  /* adjusted to get 1 millisecond (1.024 ms) with 16 MHz oscillator */
  RTICTL = 0x1F; /* set RTI prescaler */ 
  CRGINT = 0x80; /* enable RTI interrupts */ 
}

static void startTimeBase(void){
  absoluteTime = 0;
  RTIInit();
  EnableInterrupts;
}

static void wait(long ms){
  long timeout;
  timeout = absoluteTime + ms;
  while (timeout != absoluteTime) {
    __asm NOP;
    /* __asm WAI; */ /* will be waken up by the RTI exception. Not well supported in BDM mode */
  }
}

static void SCI0Init(void) {
  SCI0BDL = (unsigned char)((16000000UL /* OSC freq */ / 2) / 9600 /* baud rate */ / 16 /*factor*/); 
  SCI0CR2 = 0x2C;
}

void main(void) {
  DDRB = 0xFF;
  startTimeBase();
  SCI0Init();
  WriteToSCI0("\n\n*** Disco Demo ***\n");
  WriteToSCI0("\nEnter number (1-9) to change speed or\n'stop' to halt simulation.\n");
  for (;;){    
  	if (redButtonDown){
      increaseSpeed();
  	  displayNumber(10-waittime);
  	  wait(100);
  	} else if (blueButtonDown){
  	  reduceSpeed();
  	  displayNumber(10-waittime);
  	  wait(100);
    } else {
      setNextSegment();
     	wait(waittime * 100);
    }
  }
}
