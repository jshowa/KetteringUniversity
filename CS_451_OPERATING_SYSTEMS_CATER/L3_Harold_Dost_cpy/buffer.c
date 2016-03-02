/*
* Authors: Harold Dost
*          Jake Howarth
* Filename: buffer.c
*
* Description:
*
*
*/
#include "buffer.h"

/* Variable Declaration */
buffer_item buffer[BUFFER_SIZE] = {0};
int         buffer_count        = 0;
int         producerLocation    = 0;
int         consumerLocation    = 0;
int         in = 0;
int         out = 0;

pthread_mutex_t mutex;
pthread_mutex_t Nmutex;
sem_t           empty;
sem_t           full;

void printBuffer(){
 int i;
 printf("BUFFER ");
 for(i = 0; i<BUFFER_SIZE;i++){
 	printf(" %d",buffer[i]);
 }
 printf(" END BUFFER \n");
}
/* Main Function */
int main(int arc, char** argv){
   struct timespec sleepTime;
   int i, numProducers, numConsumers;
   // Get command line arguments argv[1], argv[2], argv [3]
   sleepTime.tv_sec = atoi(argv[1]);
   numProducers     = atoi(argv[2]);
   numConsumers     = atoi(argv[3]);
   

   if(numProducers < MAX_PRODUCERS){
   
   }
   if(numConsumers < MAX_CONSUMERS){
   
   }
   
   pthread_t tidP[MAX_PRODUCERS]; /* Producer thread identifiers */
   pthread_t tidC[MAX_CONSUMERS]; /* Consumer thread identifiers */
   pthread_attr_t attr; /* Default thread attributes */
   pthread_attr_init(&attr);
   
   // Create the mutex lock
   pthread_mutex_init(&mutex,NULL);
   pthread_mutex_init(&Nmutex,NULL);
   // Create the semaphores
   sem_init(&empty,0,5);
   sem_init(&full,0,0);
   
   
   // Create producer thread (s)
   for(i=0;i<numProducers;i++){
      pthread_mutex_lock(&Nmutex);
      pthread_create(&tidP[i],&attr,(void *)producer,(void *)&i);
   }
   
   // Create consumer thread (s)
   for(i=0;i<numConsumers;i++){
      pthread_mutex_lock(&Nmutex);
      pthread_create(&tidC[i],&attr,(void *)consumer,(void *)&i);
   }
   
   // Sleep
   nanosleep(&sleepTime,NULL);
   
   // Exit
   return 0;
}





/* insert_item
* Description: insert the item in the buffer
*  return 0 if successful, otherwise
*  return -1 indicating failure
*/
int insert_item(buffer_item item){
   sem_wait(&empty);
   pthread_mutex_lock(&mutex);
   
   buffer[in] = item;
   in = (in + 1) % BUFFER_SIZE;
   printBuffer();
   pthread_mutex_unlock(&mutex);
   sem_post(&full);
   

   return 0;
}

/* remove_item
*  insert the item in the buffer
*  return 0 if successful, otherwise
*  return -1 indicating failure
*/
int remove_item(buffer_item *item){

   sem_wait(&full);
   pthread_mutex_lock(&mutex);
   
   *item = buffer[out];
   buffer[out] = -1;
   out = (out+1)%BUFFER_SIZE;
   printBuffer();
   pthread_mutex_unlock(&mutex); 
   sem_post(&empty);

   return 0;
}

void *producer(void *param){
   buffer_item item;
   int ID = *((int *)param);
   pthread_mutex_unlock(&Nmutex);
   struct timespec tempTime;
   
   while(true){
      
      /* sleep for random period of time */
      tempTime.tv_nsec = randTime();
      nanosleep(&tempTime,NULL);

      /* generate a random number */
      item = rand() %100;

	  /* insert the item */
      if(insert_item(item)){
         printf("An error Occurred P%d\n",ID);
      }
      else{
         printf("P%d produced %d\n",ID,item);
      }
   }// end while

   pthread_exit(0);
}

void *consumer(void *param){
   buffer_item item;
   int ID = *((int *)param);
   pthread_mutex_unlock(&Nmutex);
   struct timespec tempTime;
   
   while(true){
      
      /* sleep for random period of time */
      tempTime.tv_nsec = randTime();
      nanosleep(&tempTime,NULL);

      if(remove_item(&item)){
         printf("An error Occurred C%d\n",ID);
      }
      else{
         printf("C%d consumed %d\n",ID,item);
      }
   }// end while
   
   pthread_exit(0);
}

int randTime(){
   return (rand() % RAND_MAX) + 1;
}
