#include "subs.h"

byte priority(byte* table) {
  volatile byte temp = *table; 
  byte bit_pos = 0;
  //byte i = 0;
  byte not_done_flag = 1;
  byte mask = 0x01;
  
  while(not_done_flag)//for (i; i < 8 && not_done_flag; i++)  
  {
    if (temp != 0) 
    {
        while (!(temp & mask)) 
        {
            mask <<= 1;
            bit_pos++;
        }       
        not_done_flag = 0;
    }
    else
    {
        bit_pos += 8;
        temp = *(++table);   
    }
    
            
  }
  
  return bit_pos; 
}