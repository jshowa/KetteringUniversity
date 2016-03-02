/*
*********************************************************************************************************
*                                               uC/OS-II
*                                         The Real-Time Kernel
*
*                         (c) Copyright 1998-2003, Jean J. Labrosse, Weston, FL
*                                          All Rights Reserved
*
*                                          Sample code
*                                          MC9S12DP256B 
*                                       Wytec Dragon12 EVB
*
* File : app.c
* By   : Eric Shufro
* Modified for Text Messaging System By: Jacob Howarth, Wyatt Paro, Scott Snyder
*********************************************************************************************************
*/

#include    <includes.h>


/*
*********************************************************************************************************
*                                                DEFINES
*********************************************************************************************************
*/


/*
*********************************************************************************************************
*                                                CONSTANTS
*********************************************************************************************************
*/

extern message BUFFER;
extern NODE *end_node;
extern NODE *LCD_curr_msg;
extern NODE *use_lst_head;
int curr_info_flag;
int row1;
int row2;
int txt_msg_rows;
   
/*
*********************************************************************************************************
*                                                VARIABLES
*********************************************************************************************************
*/

    OS_STK        AppStartTaskStk[APP_TASK_START_STK_SIZE];
    OS_STK        LCD_TestTaskStk[LCD_TASK_STK_SIZE];
    OS_STK        SevenSegTestTaskStk[SEVEN_SEG_TEST_TASK_STK_SIZE];
    OS_STK        KeypadRdTaskStk[KEYPAD_RD_TASK_STK_SIZE];
    OS_STK        SpeakerPlyTaskStk[SPEAKER_PLY_TASK_STK_SIZE];          // speaker play task stack size -jsw   
    OS_STK        SPIWriteTaskStk[SPI_WRITE_TASK_STK_SIZE];              // task for writing to the SPI buffer -jsw	    
        
    OS_FLAG_GRP  *keypadEnFlagGrp;  
    
    
    CPU_INT16U  hours;
    CPU_INT16U  minutes;
    CPU_INT16U  centisecondCounter;
    
    message LCD_disp_buffer;    
    
/*
*********************************************************************************************************
*                                            SEMAPHORES
*********************************************************************************************************
*/

OS_EVENT*	SpeakerPlaySem;
OS_EVENT*   SCISemWrt;
OS_EVENT*   SPIMutex;
OS_EVENT*   MessageReceived;
    
/*
*********************************************************************************************************
*                                            FUNCTION PROTOTYPES
*********************************************************************************************************
*/

static  void  AppStartTask(void *p_arg);
static  void  AppTaskCreate(void);
static  void  LCD_TestTask(void *p_arg);
static  void  SevenSegTestTask(void *p_arg);
static  void  KeypadRdTask(void *p_arg);
static  void  SpeakerPlyTask(void *p_arg);                              // speaker play task function prototype -jsw
static  void  SPIWriteTask(void *p_arg);                                // SPI write task function prototype -jsw        
static  void  SPIReadTask(void *p_arg);

#if (uC_PROBE_OS_PLUGIN > 0) || (uC_PROBE_COM_MODULE > 0)
extern  void  AppProbeInit(void);
#endif

/*
****************************************
*       INITIALIZATION PROTOTYPES      *
****************************************
*/
static void SemaInit(void);
static void MutexInit(void);
static void HardwareInit(void);

/*
****************************************
*       NON-TASK FUNCTION PROTOTYPES   *
****************************************
*/
//static void DispMsgFrmBlk(void);
static void grabInfo(void);

/*
*********************************************************************************************************
*                                                main()
*
* Description : This is the standard entry point for C code.  It is assumed that your code will call
*               main() once you have performed all necessary 68HC12 and C initialization.
* Arguments   : none
*********************************************************************************************************
*/

void  main (void)
{
    INT8U   err;
    curr_info_flag = 0;


    OSInit();                               /* Initialize "uC/OS-II, The Real-Time Kernel"              */
                                                              
    HardwareInit();

    init_list();
  
    OSTaskCreateExt(AppStartTask,
                    (void *)0,
                    (OS_STK *)&AppStartTaskStk[APP_TASK_START_STK_SIZE - 1],
                    APP_TASK_START_PRIO,
                    APP_TASK_START_PRIO,
                    (OS_STK *)&AppStartTaskStk[0],
                    APP_TASK_START_STK_SIZE,
                    (void *)0,
                    OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR);

    OSTaskNameSet(APP_TASK_START_PRIO, "Start Task", &err);
 
    OSStart();                                                          /* Start multitasking (i.e. give control to uC/OS-II)       */
}




/*$PAGE*/
/*
*********************************************************************************************************
*                                          STARTUP TASK
*
* Description : This is an example of a startup task.  As mentioned in the book's text, you MUST
*               initialize the ticker only once multitasking has started.
*
* Arguments   : p_arg   is the argument passed to 'AppStartTask()' by 'OSTaskCreate()'.
*
* Notes       : 1) The first line of code is used to prevent a compiler warning because 'p_arg' is not
*                  used.  The compiler should not generate any code for this statement.
*               2) Interrupts are enabled once the task start because the I-bit of the CCR register was
*                  set to 0 by 'OSTaskCreate()'.
*				3) After this created from main(), it runs and initializes additional application
*                  modules and tasks. Rather than deleting the task, it is simply suspended
*                  periodically. This tasks body could be used for additional work if desired.
*********************************************************************************************************
*/

static  void  AppStartTask (void *p_arg)
{		
   (void)p_arg;
   		  
    BSP_Init();                                                         /* Initialize the ticker, and other BSP related functions   */

#if OS_TASK_STAT_EN > 0
    OSStatInit();                                                       /* Start stats task                                         */
#endif

#if (uC_PROBE_OS_PLUGIN > 0) || (uC_PROBE_COM_MODULE > 0)
    AppProbeInit();                                                     /* Initialize uC/Probe modules                              */
#endif
    
    AppTaskCreate();                                                    /* Create additional tasks using this user defined function */
    
    while (TRUE) {                                                      /* Task body, always written as an infinite loop            */
        OSTimeDlyHMSM(0, 0, 5, 0);                                      /* Delay the task                                           */
    }
}


/*$PAGE*/
/*
*********************************************************************************************************
*                                     CREATE APPLICATION TASKS
*
* Description : This function demonstrates how to create a new application task. 
* 
* Notes:        1) Each task should be a unique function prototypes as 
*                  static  void  mytaskname (void *p_arg). 
*               2) Additionally, each task should contain an infinite loop and call at least one
*                  OS resource on each pass of the loop. An OS resource may be a call to OSTimeDly(),
*                  OSTimeDlyHMSM(), or one of the message box, semaphore or other OS handled resource.
*               3) Each task must have its own stack. Be sure that the stack is declared large
*                  enough or the entire system may crash or experience erradic results if your stack
*                  grows and overwrites other variables in memory.
*               
* Arguments   : none
* Notes       : none
*********************************************************************************************************
*/

static  void  AppTaskCreate (void)
{
    INT8U  err;

    
    OSTaskCreateExt(LCD_TestTask,
                    (void *)0,
                    (OS_STK *)&LCD_TestTaskStk[LCD_TASK_STK_SIZE-1],
                    LCD_TEST_TASK_PRIO,
                    LCD_TEST_TASK_PRIO,
                    (OS_STK *)&LCD_TestTaskStk[0],
                    LCD_TASK_STK_SIZE,
                    (void *)0,
                    OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR);
    OSTaskNameSet(LCD_TEST_TASK_PRIO, "LCD Test Task", &err);    

    OSTaskCreateExt(SevenSegTestTask,
                    (void *)0,
                    (OS_STK *)&SevenSegTestTaskStk[SEVEN_SEG_TEST_TASK_STK_SIZE-1],
                    SEVEN_SEG_TEST_TASK_PRIO,
                    SEVEN_SEG_TEST_TASK_PRIO,
                    (OS_STK *)&SevenSegTestTaskStk[0],
                    SEVEN_SEG_TEST_TASK_STK_SIZE,
                    (void *)0,
                    OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR);
    OSTaskNameSet(SEVEN_SEG_TEST_TASK_PRIO, "SevenSegTest Task", &err);          

    OSTaskCreateExt(KeypadRdTask,
                    (void *)0,
                    (OS_STK *)&KeypadRdTaskStk[KEYPAD_RD_TASK_STK_SIZE-1],
                    KEYPAD_RD_TASK_PRIO,
                    KEYPAD_RD_TASK_PRIO,
                    (OS_STK *)&KeypadRdTaskStk[0],
                    KEYPAD_RD_TASK_STK_SIZE,
                    (void *)0,
                    OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR);
    OSTaskNameSet(KEYPAD_RD_TASK_PRIO, "KeypadRd Task", &err);          

    
                
     OSTaskCreateExt(SpeakerPlyTask,
     				 (void *)0,
     				 (OS_STK *)&SpeakerPlyTaskStk[SPEAKER_PLY_TASK_STK_SIZE - 1],
     				 SPEAKER_PLY_TASK_PRIO,
     				 SPEAKER_PLY_TASK_PRIO,
     				 (OS_STK *)&SpeakerPlyTaskStk[0],
     				 SPEAKER_PLY_TASK_STK_SIZE,
     				 (void *)0,
                     OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR);
     OSTaskNameSet(SPEAKER_PLY_TASK_PRIO, "Speaker Play Task", &err);
     
     OSTaskCreateExt(SPIWriteTask,
     				 (void *)0,
     				 (OS_STK *)&SPIWriteTaskStk[SPI_WRITE_TASK_STK_SIZE - 1],
     				 SPI_WRITE_TASK_PRIO,
     				 SPI_WRITE_TASK_PRIO,
     				 (OS_STK *)&SPIWriteTaskStk[0],
     				 SPI_WRITE_TASK_STK_SIZE,
     				 (void *)0,
                     OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR);
     OSTaskNameSet(SPI_WRITE_TASK_PRIO, "SPI Write Task", &err);

}


/*$PAGE*/
/*
*********************************************************************************************************
*                                             SevenSegWriteTask
*
* Description: This task displays messages on the Dragon12 (16x2) LCD screen and is
*              responsible for initializing the LCD hardware. Care MUST be taken to
*              ensure that the LCD hardware is initialized before other tasks
*              attempt to write to it. If necessary the DispInit() function
*              may be called from the start task or bsp_init().
*********************************************************************************************************
*/

static  void  LCD_TestTask (void *p_arg)
{
       CPU_INT08S  i;
       CPU_INT08U  err;
                                                                        /* Keypad Enabled Message                                   */
const  CPU_INT08U  KeypadEnStr[18]    = {"Keypad Enabled"};                     

                                                                        /* Keypad Disabled Message                                  */                       
const  CPU_INT08U  KeypadDisStr[18]   = {"Keypad Disabled"};                    

                                                                        /* Power On Welcome Message. three seperate msgs / rows     */
const  CPU_INT08U  WelcomeStr[6][18]  = {"Welcome to the", "Dragon 12 EVB.  ",                                                                          
                                         "This demo runs", "Micrium uC/OS-II",  
                                         "on a 48 MHz   ", "MC9S12DG256B CPU"}; 
    
                                                                        /* Define a message to scroll on the LCDs top line          */
const  CPU_INT08U  aboutStr[]         = {"Did you know that uC/OS-II can "
                                         "provide multi-tasking and "
                                         "real-time services to your "
                                         "embedded applications? In fact, "
                                         "uC/OS-II provides services "
                                         "such as task delays, "
                                         "semaphores, message mailboxes, "  
                                         "timers, event flags, memory "		
                                         "management, mutexes, queues, "
                                         "and much more! "
                                         };

const  CPU_INT08U  no_msg_dsp[]       = { "No new messages." };
const  CPU_INT08U  first_msg_dsp[2][18]    = { "New message", "available!" };
                                         
   CPU_INT08U  *aboutStrPtr;                                            /* Declare a pointer to aboutStr, used for scroll effect    */
    
                                   
   INT8U* msg;                               

   (void)p_arg;
                   
    DispInit(2, 16);                                                    /* Initialize uC/LCD for a 2 row by 16 column display       */    
    
    
    msg[0] = 42;

             
    while (DEF_TRUE) {                                                  /* All tasks bodies include an infinite loop                */           
        
        if(isEmptyUse() == 0)
        {
            DispClrLine(0);
            DispClrLine(1);
            continue;
        }
        if(use_lst_head->nxt_ptr == NULL)
            LCD_curr_msg = use_lst_head;
        
        if(curr_info_flag == 0)
            grabInfo();
        else
        {
          DispStr(0, 0, LCD_disp_buffer.line[row1]);
          DispStr(1, 0, LCD_disp_buffer.line[row2]);  
        }       
    }
}

static void grabInfo() 
{
    int i;
    for(i = 0; i < 160; i++)
        LCD_disp_buffer.text_msg[i] = 0;  
    
	// read string from base address in spi and store in buffer
    spi_RAM_read_string(LCD_curr_msg->base, LCD_disp_buffer.text_msg);
    
    // get the messages number of rows
    txt_msg_rows = (strlen(LCD_disp_buffer.text_msg) / 16) + 1;
    
    // reset rows to beginning two lines in message
    row1 = 0;
    row2 = 1;    
    curr_info_flag = 1;
    
    DispClrLine(0);
    DispClrLine(1);   
}



/*$PAGE*/
/*
*********************************************************************************************************
*                                             Keypad Read Task
*
* Description: This task periodically reads the Wytec Dragon12 EVB keyapd and
*              displays the value on the bottom row of the LCD screen.
*********************************************************************************************************
*/

static  void  KeypadRdTask (void *p_arg)
{
    CPU_INT08U  key;
    CPU_INT08U  out_str[17];
    CPU_INT08U  key_map[] = {'1', '2', '3', 'A',
                             '4', '5', '6', 'B', 
                             '7', '8', '9', 'C',
                             '*', '0', '#', 'D'
                             };
    CPU_INT08U  err;
        

   (void)p_arg;
       
    KeypadInitPort();                                                   /* Initialize the keypad hardware                           */
                
    while (DEF_TRUE) {                                                  /* All tasks bodies include an infinite loop                */   
      
          key = KeypadReadPort();                                         /* Scan the keypad. Returns 0-15 or 0xFF if nothing pushed  */
                    
        if (key_map[key] == 'C') {									/* trigger the sound thread to play -scott   */
            
            if(LCD_curr_msg != NULL)
            {
                if(LCD_curr_msg->nxt_ptr != NULL)
                {
                    LCD_curr_msg = LCD_curr_msg->nxt_ptr;
                    curr_info_flag = 0;
                }
                else if(LCD_curr_msg->prev_ptr != NULL)
                {
                   LCD_curr_msg = use_lst_head;
                   curr_info_flag = 0; 
                }                   
                    
            }	
        }
        
        if(key_map[key] == '8')
        {
            if(LCD_curr_msg != NULL)
            {
                if(LCD_curr_msg->prev_ptr != NULL)
                {
                    LCD_curr_msg = LCD_curr_msg->prev_ptr;
                    curr_info_flag = 0;
                }
                else if(LCD_curr_msg->nxt_ptr != NULL)
                {
                    while(LCD_curr_msg->nxt_ptr != NULL)
                        LCD_curr_msg = LCD_curr_msg->nxt_ptr;
                    curr_info_flag = 0;
                }
            }
        }
        
        if(key_map[key] == '#')
        {
            if(txt_msg_rows > 2)
            {
                if(row2 < txt_msg_rows) 
                {
                    if(row2 != 9)
                    {
                        row1++;
                        row2++;
                        DispClrLine(0);
                        DispClrLine(1);    
                    }                   
                }                    
            }
        }
        
        if(key_map[key] == '6')
        {
            if(txt_msg_rows > 2)
            {
                if(row1 > 0)
                {
                    row1--;
                    row2--;
                    DispClrLine(0);
                    DispClrLine(1);
                }
            }
        }
        
        if(key_map[key] == '9')
        {
            int base_arg;
            if(LCD_curr_msg != NULL)
            {
                base_arg = LCD_curr_msg->base;
                
                if(LCD_curr_msg->nxt_ptr != NULL)
                {
                    LCD_curr_msg = LCD_curr_msg->nxt_ptr;
                    remove_use_list(base_arg);
                    curr_info_flag = 0;  
                }
                else if(LCD_curr_msg->prev_ptr != NULL)
                {
                    LCD_curr_msg = use_lst_head;
                    remove_use_list(base_arg);
                    curr_info_flag = 0;    
                }
                else
                {
                    LCD_curr_msg = NULL;
                    remove_use_list(base_arg);
                    curr_info_flag = 0;
                }                
            }
        }
            
            
       // }
        
        // Happens when SW5 is pressed.
        if ((PTH | 0xFE) == 0xFE) {
        	OSSemPost(SpeakerPlaySem); //SpeakerPlayQueue = 1;
        }
        
        // Happens when SW4 is pressed.
        if ((PTH | 0xFD) == 0xFD) {
            
        }
        
        // Happens when SW3 is pressed.
        if ((PTH | 0xFB) == 0xFB) {
        	minutes++;
        }
        
        // Happens when SW2 is pressed.
        if ((PTH | 0xF7) == 0xF7) {
        	hours++;
        }
        
//        DispStr(1, 0, out_str);                                         /* Display the keypad message on row 1 of the LCD screen    */        
        OSTimeDlyHMSM(0, 0, 0,100);                                     /* Read the keypad every 100ms                              */
    }
}


/*$PAGE*/
/*
*********************************************************************************************************
*                                             SevenSegWriteTask
*
* Description: This task displays messages on the Dragon12 (16x2) LCD screen
*********************************************************************************************************
*/   
static  void  SevenSegTestTask (void *p_arg)
{
   
   (void)p_arg;
   
    SevenSegDisp_Init();	                                            /* Initialize the 7-Seg I/O and periodic refresh interrupt  */
    
    hours = 12;
    minutes = 0;
    centisecondCounter = 0;
    
    
    while (DEF_TRUE) {                                                  /* All tasks bodies include an infinite loop                */   
        SevenSegWrite((hours * 100 + minutes));                                             /* Output the value to the screen */                                                                         
        
        centisecondCounter++;
        centisecondCounter %= 6000;
        
        
        // When the centisecondCounter value is 0,
        // this means that we have wrapped around to another minute.
        // Thus, updating the hours and minutes accordingly.
        if (centisecondCounter == 0)
        {
            minutes++;
        }
        
        if (minutes > 59) {	
	        hours++;	
	        minutes = 0;
        }
        
        if (hours > 12) {
            hours = 1;	
        }
         
        OSTimeDlyHMSM(0, 0, 0, 10);                                     /* Delay the task for 10ms and repeat the process           */
    }
}




/*$PAGE*/
/*
*********************************************************************************************************
*                                             Speaker Play Task
*
* Description: This task plays a tune on the Wytec Dragon12 EVB speaker.
*********************************************************************************************************
*/
INT8U perr;
static  void  SpeakerPlyTask(void *p_arg) 
{
                                                                        /* Initialize the speaker */
    INT8U err;
        
    while(DEF_TRUE)                                                     /* Play B-flat scale  */
    {
    	
    	//if (SpeakerPlayQueue) {
    		OSSemPend(SpeakerPlaySem, 0, &err);
	       /* use this commented code for dragon force	
            Speaker_Note(NOTE_E6);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_F6);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_E6);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_D6);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C6);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_B5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A5); 
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_GS5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_B5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_F5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_E5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_D5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_D5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_E5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_F5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_E5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_D5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_G4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_G4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_F4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_E4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_D4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_G3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_C4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B3);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_D4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_E4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_F4);
	        OSTimeDlyHMSM(0, 0, 0, 100); 
	        Speaker_Note(NOTE_GS4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_C5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_D5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_E5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_F5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
            Speaker_Note(NOTE_G5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_C5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_D5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_E5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_F5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_G5);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        */
	        //Crazy Train    
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_OFF);//added to ensure both beats are heard
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_CS5);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	        Speaker_Note(NOTE_D5);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_CS5);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_GS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_GS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_E4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_OFF);//added to ensure both beats are heard
	        OSTimeDlyHMSM(0, 0, 0, 100);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_CS5);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	        Speaker_Note(NOTE_D5);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_CS5);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_FS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_GS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	        Speaker_Note(NOTE_B4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_A4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_GS4);
	        OSTimeDlyHMSM(0, 0, 0, 200);
	        Speaker_Note(NOTE_E4);
	        OSTimeDlyHMSM(0, 0, 0, 200);//set
	                                         
	        Speaker_Note(NOTE_OFF);
    	
    }
    
}

/*$PAGE*/
/*
*********************************************************************************************************
*                                             SPI Write Task
*
* Description: This task writes a string to the 23K256 SRAM SPI buffer. This is
*              an add on chip separate from the Wytec Dragon12 EVB components.
*********************************************************************************************************
*/

char *string2;
void  SPIWriteTask(void *p_arg) 
{

    INT8U err1, err2;
    int address = 0;
    char *string1 = "zippy";
    int i, j;
    
   
    
    spi_RAM_config(SPI_SEQUENTIAL_MODE);
    
    while (DEF_TRUE)
    {
    	
    
        OSSemPend(SCISemWrt, 0, &err1);
        OSMutexPend(SPIMutex, 0, &err2);
        
        
        used_get_end();
        j = end_node->base;  
        
        spi_RAM_write_string(end_node->base, BUFFER.text_msg);
        
        for(i = 0; i < 160; i++)
        	BUFFER.text_msg[i] = 0;
        
        OSMutexPost(SPIMutex);    
    }
}
	

/*$PAGE*/
/*
*********************************************************************************************************
*                                             SemaInit Function
*
* Description: This function initializes the semaphores used throughout the application.
*********************************************************************************************************
*/
void SemaInit(void) 
{
    SpeakerPlaySem = OSSemCreate(0);
    SCISemWrt = OSSemCreate(0);
    MessageReceived = OSSemCreate(0);
    
}

void MutexInit(void)
{
    INT8U err;
    SPIMutex = OSMutexCreate(SPI_MUTEX_TEMP_PRIO, &err);
}

void HardwareInit(void) 
{
   Switches_Init();
   Sci1_Init();
   Speaker_Init();
   spi0_init();
   SemaInit();
   MutexInit();
}

