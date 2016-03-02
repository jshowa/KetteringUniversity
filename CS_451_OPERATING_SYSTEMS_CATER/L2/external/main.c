/*
 *
 * File:   main.c
 * Author: Jacob Howarth and Harold Dost
 * Class: CS-451 - Operating Systems
 * Term: Fall 2010
 *
 * Description: Client/External Process code.
 * Created on November 2, 2010, 11:29 AM
 */

#include "includes.h"
/*
 * 
 */
int main(int argc, char** argv) {

    // declare variable


    MSG msgp, cmbox;        // msgp = process mailbox, cmbox = central process mailbox
    int initTemp;           // external process temperature
    int argId;              // external process id specified by user
    int msqidC, msqid;      // message queues for this process and central process
    bool unstable = true;   // stability flag
    int error_flg;          // error flag for msgsnd and msgrcv function
    const char* path = "../central/proc_temp_sync_log.txt";    // process temperature synchronization log
    FILE* fptr;
    

    // parse arguments
    if (argc != 3)
    {
        errno = EXIT_FAILURE;
        perror("Must have two arguments: <temperature> <pid>");
        exit(EXIT_FAILURE);
    }
    else
    {
        initTemp = atoi(argv[1]);
        argId = atoi(argv[2]);

        if (argId <= 0 || argId > 4)
        {
            perror("external process must have id's greater than 0, but less than 4");
            exit(EXIT_FAILURE);
        }
    }

    // create a mailbox for the central/server process
    msqidC = msgget(CENTRAL_PROC_MAILBOX_ID, 0600 | IPC_CREAT);

    if (msqidC == -1) {
        perror("Failed to create mailbox: ");
        exit(EXIT_FAILURE);
    }

    printf("mailbox%d created by external process %d for central process %d.\n", msqidC, argId, CENTRAL_PROC_MAILBOX_ID); 	

    // create a mailbox for this process
    msqid = msgget((CENTRAL_PROC_MAILBOX_ID + argId), 0600 | IPC_CREAT);

    if (msqid == -1) {
        perror(NULL);
        exit(EXIT_FAILURE);
    }

    printf("mailbox%d created by external process %d for external process %d.\n", msqid, argId, argId);

    // format message to send
    cmbox.priority = 1;
    cmbox.pid = argId;
    cmbox.temp = initTemp;
    cmbox.stable = true;

    // create/append to the file proc_temp_sync_log.txt
    fptr = fopen(path, "a");
    if (fptr == NULL) {
        perror(NULL);
        exit(EXIT_FAILURE);
    }
    
    int length = sizeof(msgp) - sizeof(long);

    // send process message to central process and evaluate central processes temp
    while (unstable) {
        // send the current temp to the central process
        error_flg = msgsnd(msqidC, &cmbox, length, 0);

        if (error_flg == -1) {
            perror(NULL);
            exit(EXIT_FAILURE);
        }

	printf("message sent from external process %d to central process.\n", argId);

        // wait for data from central process
        error_flg = msgrcv(msqid, &msgp, (sizeof(msgp) - sizeof(long)), cmbox.priority, 0);

        if (error_flg == -1) {
            perror(NULL);
            exit(EXIT_FAILURE);
        }

	printf("message received by external process %d from the central process.\n", argId); 

	// write status to log file
        if (msgp.stable == false) {
            unstable = false;
            error_flg = fprintf(fptr, "Process ID: %d Temperature: %d Stabilized: %d\n",
                    cmbox.pid, cmbox.temp, true);
            if (error_flg == -1)
            {
                perror(NULL);
                exit(EXIT_FAILURE);
            }
        }
        else {
            error_flg = fprintf(fptr, "Process ID: %d Temperature: %d Stabilized: %d\n", 
                    cmbox.pid, cmbox.temp, false);
            if (error_flg == -1)
            {
                perror(NULL);
                exit(EXIT_FAILURE);
            }
            
            cmbox.temp = (cmbox.temp * 3 + 2 * msgp.temp) / 5;
        }

    }

    // delete mailbox of this process
    error_flg = msgctl(msqid, IPC_RMID, 0);

    if (error_flg != 0) {
        perror(NULL);
        error_flg = fclose(fptr);

        if (error_flg != 0)
            perror(NULL);

        exit(EXIT_FAILURE);
    }
    else {
        error_flg = fclose(fptr);

        if (error_flg != 0) {
            perror(NULL);
            exit(EXIT_FAILURE);
        }
    }

    return (EXIT_SUCCESS);
}

