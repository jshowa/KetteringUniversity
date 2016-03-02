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

struct block
{
    int blk_num;
    char emp_flg;
    char nxt_blk;
    char prev_blk;
};

typedef struct block BLOCK;

/*
*********************************************************************************************************
*                                        DIRECTIVES
*********************************************************************************************************
*/

#define MAX_MSG_NUM     4
#define MAX_MSG_SIZ     160
//#define SPI_START_ADDRESS         0

#define FREE                      0
#define USED                      1

/*
*********************************************************************************************************
*                                        PROTOTYPES
*********************************************************************************************************
*/        

void init_mem_blks(void);
int get_prev_blk_index(int curr_index);
int get_nxt_blk_index(int curr_index);
int calc_spi_base_addr(int curr_index);
int calc_spi_limit(int spi_base_addr);

/***************SPI WRITE READ FUNC*********************/
void clr_blk(BLOCK *blk);

/*********************************************************/
void insert_use_list(void);//NODE *curr_node);
void remove_use_list(int base);
void insert_free_list(void);//NODE *new_node);
int remove_free_list(void);

