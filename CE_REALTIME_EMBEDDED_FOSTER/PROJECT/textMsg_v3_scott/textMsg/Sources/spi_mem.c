/*
*********************************************************************************************************
*                               Wytec Dragon12 Board Support Package 
*
*                                       Freescale MC9S12DP256
*
* File       : spi_mem.c
* By         : Jacob Howarth, Wyatt Paro, Scott Snyder
*
* Description: Describes the memory management scheme for the SPI buffer.
*********************************************************************************************************
*/

/*
*********************************************************************************************************
*                                        INCLUDES
*********************************************************************************************************
*/

#include <includes.h>

/*
*********************************************************************************************************
*                                        VARIABLES
*********************************************************************************************************
*/

BLOCK mem_blks[MAX_MSG_NUM];
int free_head, use_head;
int free_tail, use_tail;

/* -- LEGACY IMPLEMENTATION
NODE *use_lst_head = NULL;                 // in use blocks 
NODE *free_lst_head = NULL;                // free blocks
*/


/* -- TEST METHOD
int main(void)
{
    init_list();
    remove_free_list();
    remove_free_list();
    remove_use_list(0);
    return 0;
}*/

/*
*********************************************************************************************************
*                                   SEVEN SEGMENT INTERRUPT SERVICE ROUTINE
*
* Description : This function is the 7-Segment LED Interrupt Service Routine. This
*               interrupt occurs periodically at the rate configured within 
*               sevenSegDisp_Init(). Each time the interrupt occurs, the active
*               LED block is changed, and the value on the 7-Segment LED data
*               bus is updated.
*
* Arguments   : None
*
* Returns     : None
*
* Notes       : THE CORRECT ECT CHANNEL ISR VECTOR MUST BE SET WITHIN VECTORS.C. 
*               This involves plugging the right vector number with the name of
*               the ISR function below. A prototype at the top of vectors.c must
*               also be created.
*********************************************************************************************************
*/
void init_mem_blks(void) 
{
    int i;
    
    // init heads of free and use list
    free_head = 0;
    use_head = -1;
   
    for (i = 0; i < MAX_MSG_NUM; i++)
    {
       // set block values (all free initially)
       mem_blks[i].blk_num = i;
       mem_blks[i].emp_flg = FREE;
       mem_blks[i].nxt_blk = get_nxt_blk_index(i);
       mem_blks[i].prev_blk = get_prev_blk_index(i);
    
       // zero out all blocks in SPI buffer
       clr_blk(&mem_blks[i]);
    }
    
    
    // init tails of free and use list
    free_tail = i - 1;
    use_tail = use_head;
    
    
    /* -- LEGACY IMPLMENTATION
    NODE new_node[MAX_NUM_OF_MESSAGES];
    NODE *curr_node;
    int i, j;
    int addr_offset = 0;
   
    
    for (i = 0; i < 3; i++)
    {
        
        // create a node
        new_node[i].base = STARTING_SPI_ADDRESS + addr_offset;
        new_node[i].limit = BYTES_PER_MESSAGE;
        new_node[i].nxt_ptr = NULL;
        new_node[i].prev_ptr = NULL;
        
        
        
        // add node to free list
        if (free_lst_head == NULL)
        {
            free_lst_head = &(new_node[i]);
            addr_offset += BYTES_PER_MESSAGE;
        }
        else
        {
            curr_node = free_lst_head;
            
            
            while (curr_node->nxt_ptr != NULL) 
                curr_node = curr_node->nxt_ptr;
            
            curr_node->nxt_ptr = &(new_node[i]);    
            addr_offset += BYTES_PER_MESSAGE;
        }
        
    }
    */
}

int get_prev_blk_index(int curr_index)
{
    // return the prev block index (circular)
    int prev_blk_index = (curr_index - 1) % MAX_MSG_NUM;
    
    if (prev_blk_index == -1)
    {
        prev_blk_index = MAX_MSG_NUM - 1;
    }
    
    return prev_blk_index;
}

int get_nxt_blk_index(int curr_index)
{
    // return the next block index (circular)
    return ((curr_index + 1) % MAX_MSG_NUM);
}

int calc_spi_base_addr(int curr_index)
{
    // convert block index to SPI base address
    return (curr_index * MAX_MSG_SIZ);
}

int calc_spi_limit(int spi_base_addr)
{
    return (spi_base_addr + MAX_MSG_SIZ);
}

int address, limit;
void clr_blk(BLOCK *blk)
{
    // get SPI address for block to clear
    address = calc_spi_base_addr(blk->blk_num);
    limit = calc_spi_limit(address);
    // write 160 \0 to spi buffer
    for (address; address < limit; address++)
        spi_RAM_write(address, 'a');
}

/*
*********************************************************************************************************
*                                   SEVEN SEGMENT INTERRUPT SERVICE ROUTINE
*
* Description : This function is the 7-Segment LED Interrupt Service Routine. This
*               interrupt occurs periodically at the rate configured within 
*               sevenSegDisp_Init(). Each time the interrupt occurs, the active
*               LED block is changed, and the value on the 7-Segment LED data
*               bus is updated.
*
* Arguments   : None
*
* Returns     : None
*
* Notes       : THE CORRECT ECT CHANNEL ISR VECTOR MUST BE SET WITHIN VECTORS.C. 
*               This involves plugging the right vector number with the name of
*               the ISR function below. A prototype at the top of vectors.c must
*               also be created.
*********************************************************************************************************
*/
void insert_free_list(void)//NODE *new_node) 
{
   // Not used due to bugs
}


int remove_free_list() 
{
    /* -- LEGACY IMPLEMENTATION
    int address;
    NODE *free_curr = NULL;
    NODE *use_curr = NULL;
    
    
    if (free_lst_head != NULL) 
    {
        free_curr = free_lst_head;
        free_lst_head = free_curr->nxt_ptr;
        free_curr->nxt_ptr = NULL;
        
        //insert_use_list(free_curr);
        address = free_curr->base;

        if (use_lst_head == NULL)
        {
            use_lst_head = free_curr;
        }
        else
        {
             use_curr = use_lst_head;

             while (use_curr->nxt_ptr != NULL)
                use_curr = use_curr->nxt_ptr;

             use_curr->nxt_ptr = free_curr;
             free_curr->prev_ptr = use_curr;
        }
        
        return address;     // address of the free block returned
    }
    
    return -1;      // error value returned to indicate no free blocks
    */
}

void insert_use_list(void)//NODE *curr_node) 
{
    // Not used due to bugs
}

void remove_use_list(int base_arg)
{

    /* -- LEGACY IMPLEMENTATION
    if(use_lst_head != NULL) 
    {
        NODE* curr = use_lst_head;
        NODE* prev = use_lst_head;
        NODE *curr_node;
        
        while(curr->base != base_arg) 
        {
            prev = curr;
            curr = curr->nxt_ptr;            
        }
        if(prev == curr && (curr->nxt_ptr != NULL))
        {
            use_lst_head = curr->nxt_ptr;
            (curr->nxt_ptr)->prev_ptr = NULL;
            curr->nxt_ptr = NULL;
        }
        else if(prev == curr && (curr->nxt_ptr == NULL))
        {
            use_lst_head = NULL;
        }
        else if(curr->nxt_ptr == NULL)
        {
            prev->nxt_ptr = NULL;
            curr->prev_ptr = NULL;
        }
        else
        {
            prev->nxt_ptr = curr->nxt_ptr;
            (curr->nxt_ptr)->prev_ptr = prev;
            curr->nxt_ptr = NULL;
            curr->prev_ptr = NULL;            
        }
        if (free_lst_head == NULL)
        {
            free_lst_head = curr;
        }
        else
        {
            curr_node = free_lst_head;


            while (curr_node->nxt_ptr != NULL)
                curr_node = curr_node->nxt_ptr;

            curr_node->nxt_ptr = curr;
        }
    }*/    
}

