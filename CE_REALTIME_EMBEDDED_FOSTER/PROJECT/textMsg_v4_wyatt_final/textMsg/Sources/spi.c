//#include "spi.h"
#include <includes.h>

static void sci0put(char);
static char sci0get(void);

void spi_RAM_config(char options) {
    PTS_PTS7 = 0;						// CS enabled  
    sci0put(SPI_WRSR_INST);
    sci0put(options);
    PTS_PTS7 = 1;					   // CS disabled
    return;
}

void spi_RAM_write(int address, char data) {
    char options = SPI_BYTE_MODE;   // SPI status register config
    ADDRESS temp;                   // address at which to write the byte 
    temp.addr = address;            
    
    spi_RAM_config(options);        // write the configuration to the status register
    
    PTS_PTS7 = 0;				    // CS enabled  
    sci0put(SPI_WRITE_INST);        // Write the byte in the address space given
    sci0put(temp.bytes[0]);
    sci0put(temp.bytes[1]);
    sci0put(data);
    PTS_PTS7 = 1;			       // CS disabled
    
    return;
}

char spi_RAM_read(int address) {
    char options = SPI_BYTE_MODE;
    char byte_read;
    
    ADDRESS temp;
    temp.addr = address;
    
    spi_RAM_config(options);
    
    PTS_PTS7 = 0;//&= (~PTS_PTS7_MASK);
    sci0put(SPI_READ_INST);
    sci0put(temp.bytes[0]);
    sci0put(temp.bytes[1]);
    byte_read = sci0get();
    PTS_PTS7 = 1;//= PTS_PTS7_MASK;
    
    return byte_read;  
}

int address2;
void spi_RAM_write_string(int address, char* string) {
    ADDRESS temp;
    address2 = address;
    temp.addr = address2;
    
    spi_RAM_config(SPI_SEQUENTIAL_MODE);    // config SPI register
    
    PTS_PTS7 = 0;					        // CS enabled (pull port S, pin 7 low)
    sci0put(SPI_WRITE_INST);                // send write instruction to SPI
    sci0put(temp.bytes[0]);                 // send address to write to
    sci0put(temp.bytes[1]);
    
    while (*string != '\0')                 // write the string until null char is reached
        sci0put(*string++);               // move the pointer after each char is sent to the SPI
    sci0put('\0');                          /* Since the loop stops when the null char is reached,
                                               it is never written. So, write the NULL char at
                                               the end of the loop */
    PTS_PTS7 = 1;			             // CS disabled (pull port S, pin 7 high)
    
    return;
}

void spi_RAM_read_string(int address, char* string) {
    int counter = -1;
    ADDRESS temp;
    temp.addr = address;
    
    spi_RAM_config(SPI_SEQUENTIAL_MODE);
    
    PTS_PTS7 = 0;
    sci0put(SPI_READ_INST);
    sci0put(temp.bytes[0]);
    sci0put(temp.bytes[1]);
    
    
    // USED FOR SEQUENTIAL MODE (DOESN'T WORK)
    do 
    {
       counter++; 
       string[counter] = sci0get();
    }
    while (string[counter] != '\0' && counter <= 158);    
    
    
    PTS_PTS7 = 1;
    
    if (counter == 159)
        string[counter] = '\0';
    
    return;
}

/* The following functions use the S12 SPI to send or receive one byte               */
/* The code is based on examples from Huang in The HCS12/9S12: An introduction       */
/* to Software and Hardware Interfacing, but has been modified to use the existing   */
/* header files                                                                      */
/* IMPORTANT NOTE!!!                                                                 */
/* The code below does NOT affect the CS (chip select) pin PS7. This must be done by */
/* your subroutines                                                                  */

static void sci0put(char data) {
  char temp;
  while(SPI0SR_SPTEF==0){};   /* verify SPI0 is ready */
  SPI0DR = data;              /* send data */
  /* do not trace next line... set breakpoint at temp=SPI0DR and run */
  while(SPI0SR_SPIF==0){};    /* wait for transfer */
  temp = SPI0DR;              /* read data reg to clear flag */
}
  
static char sci0get(void) {
  while(SPI0SR_SPTEF==0){};   /* verify SPI0 is ready */
  SPI0DR = 0x00;              /* dummy data to trigger transfer */
  /* do not trace next line.. set breakpoint at return SPI0DR and run */
  while(SPI0SR_SPIF==0){};    /* wait for transfer */
  return SPI0DR;
 
}


/* configures the SPI device for the Motorola 23K256 RAM */
void spi0_init(void) {
  SPI0BR  = 0x22;  /* 1 MHz baud rate with 24 MHz bus*/  
  SPI0CR1 = 0x50; /* enable SPI, no intr, clk idle low */
  SPI0CR2 = 0x00; /* disable bidir, don't stop SPI in wait */
  WOMS = 0;       /* port S pullup */  
  DDRS = 0x80; /* set as output for chip select */
  PTS_PTS7 = 1;   /* cycle chip: disable spi RAM */
  PTS_PTS7 = 0;   /* enable spi RAM */
  PTS_PTS7 = 1;   /* disable spi RAM */
  return; 
}