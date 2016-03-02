/*
* Authors: Harold Dost
*          Jake Howarth
* Filename: buffer.h
*
* Description:
*
*
*/

/* Include Section */
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <time.h>
#include <stdbool.h>

/* Definition Section */
 typedef int buffer_item;
 #define BUFFER_SIZE 30
 #define MAX_CONSUMERS 10
 #define MAX_PRODUCERS 10  
 
/* Function Declaration */
int insert_item(buffer_item);
int remove_item(buffer_item *);
void *producer(void *);
void *consumer(void *);
