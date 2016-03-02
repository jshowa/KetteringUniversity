/* 
 * File:   includes.h
 * Author: Jacob Howarth and Harold Dost
 * Class: CS-451 - Operating Systems
 * Term: Fall 2010
 *
 * Created on October 30, 2010, 5:06 PM
 */

#ifndef _INCLUDES_H
#define	_INCLUDES_H

// linux source includes
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/msg.h>
#include <unistd.h>
#include <errno.h>

// standard C library includes
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

// server/central process mailbox ID
#define CENTRAL_PROC_MAILBOX_ID 1400

// number of server/central processes
#define NUM_OF_SRVRS    1

// number of client/external processes
#define NUM_OF_CLIENTS  4

// number of server/central process args
#define NUM_OF_SRVR_ARGS    1

// number of client/external process args
#define NUM_OF_CLNT_ARGS    2

// message structure
typedef struct {
    long priority;      // message priority
    int temp;           // processor temperature
    int pid;            // process ID
    bool stable;        // temperature stability flag
} MSG;



#ifdef	__cplusplus
extern "C" {
#endif




#ifdef	__cplusplus
}
#endif

#endif	/* _INCLUDES_H */

