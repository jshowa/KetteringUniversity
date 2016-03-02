/* 
 * File:   main.c
 * Author: Jake Howarth and Harold Dost
 * Class: CS-451 - Operating Systems
 * Term: Fall 2010
 * Description: Server Side/Central Process code
 * Created on October 30, 2010, 5:00 PM
 */

#include "includes.h"

/*
 * 
 */
int main(int argc, char** argv) {

    // setup local variables
    MSG msgp, cmbox;                       // msgp = central processor send message
                                               // rcv_msgp = central processor receive message
    int msqid[NUM_OF_CLIENTS + NUM_OF_SRVRS];  // msqid's for each of the proc. (central & ext.)
    int initTemp;                              // initial temperature of the central proc.
    int tempArray[NUM_OF_CLIENTS];
    int i;
    bool unstable = true;
    int length;
    int error_flg = 1;
    const char* path = "proc_temp_sync_log.txt";
    FILE* fptr;

    // check that appropriate args have been entered
    if ((argc - NUM_OF_SRVR_ARGS) != NUM_OF_SRVR_ARGS) {
        printf("Error: Too few arguments");
        exit(EXIT_FAILURE);
    }
    else {
        initTemp = atoi(argv[1]);
    }

    printf("Starting program...\n");

    // initialize central process message struct
    msgp.priority = 1;
    msgp.pid = 0;
    msgp.temp = initTemp;
    msgp.stable = true;
    
    length = sizeof(msgp) - sizeof(long);

    // get message queue id's for the central process
    msqid[0] = msgget(CENTRAL_PROC_MAILBOX_ID, 0600 | IPC_CREAT);
    if (msqid[0] == -1) {
        perror("Failed to create message box: ");
        exit(EXIT_FAILURE);
    }
    else {
	
	printf("mailbox%d has been created by central process %d for central process %d.\n", msqid[0], msgp.pid, 			msgp.pid);
        for (i = 1; i <= NUM_OF_CLIENTS; i++) {
            msqid[i] = msgget((CENTRAL_PROC_MAILBOX_ID + i), 0600 | IPC_CREAT);
            
            if (msqid[i] == -1) {
            	perror("Failed to create message box: ");
            	exit(EXIT_FAILURE);
	    }
	
	    printf("mailbox%d has been created by central process %d for central process %d.\n", msqid[i], msgp.pid, 			msgp.pid);
        }

    }

    // create/append to the file proc_temp_sync_log.txt
    fptr = fopen(path, "a");
    if (fptr == NULL) {
        perror(NULL);
        exit(EXIT_FAILURE);
    }

    printf("Logging temperatures to log file: %s\n", path);

    // When processes have different temperatures
   while(unstable) {
      int tTemp = 0;        // show total temperature
      int stable = 1;       // trap

      // Receive messages from all client processes
      for(i = 0; i < NUM_OF_CLIENTS; i++) {
         error_flg = msgrcv(msqid[0], &cmbox, length, 1, 0);

         if (error_flg == -1) {
            perror("\n");
            exit(EXIT_FAILURE);
         }

	 printf("message received by central process from external process %d.\n", cmbox.pid);

         // if new temps from external processes are different
         // from the old, there is still instability. Set new
         // temps to corresponding proc ID's
         if(tempArray[(cmbox.pid - 1)] != cmbox.temp) {
            stable = 0;
            tempArray[(cmbox.pid - 1)] = cmbox.temp;
         }

         // Find the sum of the client process temps
         tTemp += cmbox.temp;
      }

      // when all processes have a stable temp
      // 1. get out of loop
      // 2. set central processes stable field to stable
      if (stable) {
            error_flg = fprintf(fptr, "Process ID: %d Temperature: %d Stabilized: %d\n",
                    msgp.pid, msgp.temp, true);

            if (error_flg == -1)
            {
                perror("\n");
                exit(EXIT_FAILURE);
            }
         
            unstable = false;
            msgp.stable = false;
      }
      else { // calculate the weighted average 
          error_flg = fprintf(fptr, "Process ID: %d Temperature: %d Stabilized: %d\n",
                    msgp.pid, msgp.temp, false);

            if (error_flg == -1)
            {
                perror("\n");
                exit(EXIT_FAILURE);
            }
          
          msgp.temp = (2 * msgp.temp + tTemp) / 6;
      }

      // Send resulting information to the clients
      for(i = 1; i <= NUM_OF_CLIENTS; i++) {
         error_flg = msgsnd( msqid[i], &msgp, length, 0);

         if (error_flg == -1)
            {
                perror("\n");
                exit(EXIT_FAILURE);
            }

	 printf("message sent from central process to external process %d.\n", i);
      }

   }

   
   //Delete the central mailbox
   error_flg = msgctl(msqid[0], IPC_RMID, 0);

    if (error_flg != 0) {
        perror("\n");
        error_flg = fclose(fptr);

        if (error_flg != 0)
            perror("\n");

        exit(EXIT_FAILURE);
    }
    else {
        error_flg = fclose(fptr);

        if (error_flg != 0) {
            perror("\n");
            exit(EXIT_FAILURE);
        }
    }

   printf("Program Shutdown.\n");

    return (EXIT_SUCCESS);
}

