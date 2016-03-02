//#ifndef MESSAGES_H
//#define MESSAGES_H

/*
*********************************************************************************************************
*                                           messages.h
*
* This is a Linked List For our messages
* File : includes.h
*********************************************************************************************************
*/

// includes 
//#include "spi.h"

//defines
//#define EMPTY 0x00
//#define FULL  0xFF 
//#define MAX_MSGS 120
//#define MSG_SIZE 164
#define MSG_LENGTH 160
//#define EMPTY_STRING ""
//#define EMPTY_LINE "                "       

//types
struct msg 
{
  /*unsigned char isEmpty;
  unsigned char index;
  unsigned char previous; //address of prev message
  unsigned char next; //address of next message
  union{
    unsigned char line[10][16];
    unsigned char string[160];
  } characters; */
  
  char text_msg[160];
};

typedef struct msg message;

   



/*prototypes
void msg_init(void);
void msg_insert(message *msg);
void msg_delete(unsigned char index);
void msg_write(unsigned char index, message *msg);
void msg_clear(unsigned char index);
void msg_get(unsigned char index, message *msg);
void msg_getNext(unsigned char index, message *msg);
void msg_getPrev(unsigned char index, message *msg);
unsigned char msg_getIsEmpty(unsigned char index);
unsigned char msg_getIndex(unsigned char index);
unsigned char msg_getNextIndex(unsigned char index);
unsigned char msg_getPrevIndex(unsigned char index);
char *msg_getCharacters(unsigned char index);
void msg_setIsEmpty(unsigned char index, unsigned char isEmpty);
void msg_setIndex(unsigned char index, unsigned char newIndex);
void msg_setNext(unsigned char index, unsigned char nextIndex);
void msg_setPrev(unsigned char index, unsigned char prevIndex);
void msg_setCharacters(char index, char characters[160]);
unsigned char find_hole();
int convert(unsigned char index);
*/

//void Sci1_init(void);


//#endif 