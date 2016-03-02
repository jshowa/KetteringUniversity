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
*                                        
*********************************************************************************************************
*/

NODE *use_lst_head = NULL;                 // in use blocks 
NODE *free_lst_head = NULL;                // free blocks
NODE *end_node = NULL;
NODE *LCD_curr_msg = NULL;
NODE new_node[20];


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
void init_list() 
{
    //INT8U err;
    NODE *curr_node;
    int i;
    int addr_offset = 0;   
    
    for (i = 0; i < 20; i++)
    {
        
        // create a node
        new_node[i].base = STARTING_SPI_ADDRESS + addr_offset;       
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
    use_lst_head->nxt_ptr = NULL;
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


void remove_free_list() 
{

    NODE *free_curr = NULL;
    NODE *use_curr = NULL;
    
    if (free_lst_head != NULL) 
    {
        free_curr = free_lst_head;
        free_lst_head = free_curr->nxt_ptr;
        free_curr->nxt_ptr = NULL;

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
        
        
    }
}


void remove_use_list(int base_arg)
{

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
    }    
}

int isEmptyFree (void)
{
    if(free_lst_head == NULL)
        return 0;
    else
        return 1;
}

int isEmptyUse (void)
{
    if(use_lst_head == NULL)
        return 0;
    else
        return 1;
}



void used_get_end() 
{
    end_node = use_lst_head;
    
    while(end_node->nxt_ptr != NULL)
        end_node = end_node->nxt_ptr;   
    
}

void get_head_of_used()
{
    LCD_curr_msg = use_lst_head;
}
