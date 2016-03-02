/*
*********************************************************************************************************
*                               Wytec Dragon12 Board Support Package 
*
*                                       Freescale MC9S12DP256
*
* File       : spi_mem.h
* By         : Jacob Howarth, Wyatt Paro, Scott Snyder
*
* Description: Describes the memory management scheme for the SPI buffer. Contains the structure
*              for the node, the free and use lists, the MicroC-OS/II memory partition and the function
*              prototypes to add and delete from the free and in use lists.
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*                                        STRUCTURES
*********************************************************************************************************
*/

struct node 
{
    int base;
    struct node *nxt_ptr;
    struct node *prev_ptr;
};

typedef struct node NODE;

/*
*********************************************************************************************************
*                                        DIRECTIVES
*********************************************************************************************************
*/

#define MAX_NUM_OF_MESSAGES     200
#define BYTES_PER_MESSAGE       160
#define STARTING_SPI_ADDRESS      0

/*
*********************************************************************************************************
*                                        PROTOTYPES
*********************************************************************************************************
*/        

void init_list(void);
void used_get_end(void);
int isEmptyFree (void);
int isEmptyUse (void);
void remove_use_list(int base);
void remove_free_list(void);
void get_head_of_used(void);
void list(void);    // dummy prototype