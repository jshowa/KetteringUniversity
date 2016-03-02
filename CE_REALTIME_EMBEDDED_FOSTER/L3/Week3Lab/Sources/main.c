#include <hidef.h>      /* common defines and macros */
#include "derivative.h"      /* derivative-specific definitions */
#include "spi.h"

char data;
volatile char temp;

char* string = "zippy";
char string1[160];

void main(void) {
  
  int i;
  
  char* mystring1;
  char* mystring; 
  
  //volatile char a = '\0';
  //volatile int b = (int)a;
  
  
  
  mystring = string;
  mystring1 = string1;
  
  spi0_init();

  //spi_RAM_write(0x1000, 0x27);
  //spi_RAM_write(0x1002, 0x36);

  //data = spi_RAM_read(0x1000);
  //temp = data;
  //data = spi_RAM_read(0x1002);
  
  for (i = 0; i < 165; i++)
  {
    if (i == 164)
        string[i] = '\0';
    else if (i <= 160)
        string[i] = 'a';
    else if (i == 161)
        string[i] = 'b';
    else if (i == 162)
        string[i] = 'c';
    else if (i == 163)
        string[i] = 'd';
  }
  
  spi_RAM_write_string(0x1000, mystring);
  spi_RAM_read_string(0x1000, mystring1);
  
  for(;;) {
    
   
  } /* loop forever */
  /* please make sure that you never leave main */
}
