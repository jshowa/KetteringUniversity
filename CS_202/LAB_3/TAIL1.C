/* 
 * File:   tail.c
 * Author: jshowa
 *
 * Created on May 4, 2009, 7:48 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/types.h>

#define BUFFERSIZE 4096 // max buffer size

void error(char *, char *); // prints errors to stdout
void last_n_chars(char *, char *); // returns last n chars in a file
void last_n_lines(char *, char *); // returns last n lines in a file

/* Function: main
 * Description: Where program begins execution. Checks for specified options
 *              to the command.
 * @param argc - number of command line arguments
 * @param argv[] - command arguments string array
 * @return - integer, 0 success, 1 failure
 */
int main(int argc, char* argv[]) {

    if (argc <= 1) { // if args less than or equal to zero, throw error
        fprintf(stderr, "usage: %s [filename] options: [-cn] [n]\n", *argv);
        exit(1);
    }
    if (argc == 4) {
        if (strncmp(argv[2], "-c", 2) == 0) // character option
            last_n_chars(argv[1], argv[3]);
        if (strncmp(argv[2], "-n", 2) == 0) // line number option
            last_n_lines(argv[1], argv[3]);
    }
    if (argc == 2) // no options
        last_n_lines(argv[1], "10");

    return (EXIT_SUCCESS);
}

/* Function: last_n_chars
 * Description: Prints the last N chars in a text file.
 * @param filename - file to print characters from to stdout
 * @param n_bytes - number of characters to print at EOF i.e. n_bytes = N
 * @return - void
 */
void last_n_chars(char* filename, char* n_bytes) {

    /**************VARIABLE TABLE last_n_chars****************
     * int fd - file descriptor for opened file connection
     * int offset - number of bytes, specified by n_bytes
     *              to offset file pointer in lseek
     * int n_chars - number of bytes returned by read
     * char[] buf - buffer to hold last n characters from file
     *********************************************************
     */
    int fd, offset = -1 * atoi(n_bytes) * sizeof(char), n_chars;
    char buf[BUFFERSIZE];
    
    //Step 1: Open the file and get a file descriptor
    if ((fd = open(filename, O_RDONLY)) == -1)
        error("Cannot open ", filename);
    
    //Step 2: Advance the file pointer to the end of the line and read
    //        beginning at that offset
    if (lseek(fd, offset, SEEK_END) != 1)
        while ((n_chars = read(fd, &buf, BUFFERSIZE)) > 0)
            fprintf(stdout, "%s", buf);
    
    //Step 3: Check if read was successful
    if (n_chars == -1)
        error("Read error from", filename);
    
    //Step 4: close the file
    if (close(fd) == -1)
        error("Error closing file", filename);
               
}

/* Function: last_n_lines
 * Description: Prints the last N lines in a text file.
 * @param filename - file to print lines from to stdout
 * @param line_Number - number of lines to print from EOF i.e. lineNumber = N
 * @return - void
 */
void last_n_lines(char *filename, char* lineNumber) {

    /**************VARIABLE TABLE last_n_chars****************
     * int lineNum - number of lines to print from EOF, this
     *               is the integer value of the string lineNumber
     * int i - for loop indice
     * int marker - counter to keep track of the number of lines
     *              read from the file.
     * char *inputBuff - buffer to store file lines in
     * char *inputBuffPtr - temp pointer that always points
     *                      to the first address of inputBuff
     * FILE *file - FILE structure pointer
     *********************************************************
     */
    int lineNum, i = 0, marker = 0;
    FILE *file;
    char *inputBuff, *inputBuffPtr;

    //Step 1: Convert the line number from string to int and create
    //an array of char* with size specified by the lineNumber arg.
    lineNum = atoi(lineNumber);

    if (lineNumber == 0 || lineNumber < 0) {
        error("Please enter a non-zero, non-negative, integer", filename);
    }

    //Step 2: Create a file pointer and attempt to open the file
    file = fopen(filename, "r");

    if (file == NULL) {
        error("File could not be read", filename);
    }

    //Step 3: allocate a pointer for a buffer of size line number * BUFFERSIZE
    // assuming that the maximum number of characters in each line of text
    // is 4096.
    inputBuff = malloc(lineNum * BUFFERSIZE);
    inputBuffPtr = inputBuff; // create a temp pointer to point to the first
                              // position of the buffer

    while (fgets(inputBuff, BUFFERSIZE, file)) {
        inputBuff += BUFFERSIZE; // read in a line and advance the buffer pointer
        marker++; // keep track of how many lines read

        // if the difference between the first position of the
        // buffer and the advancing buffer pointer is equal to
        // the line number * 4096, reset the advancing pointer
        // back to the first position of the buffer.
        if (((int)inputBuff - (int)inputBuffPtr) == lineNum * BUFFERSIZE) {
            inputBuff = inputBuffPtr;
        }
        
    }

    // if there are lesser lines in the file then requested, set
    // the line number equal to the marker.
    if (marker < lineNum)
        lineNum = marker;

    // print the lines in the buffer to stdout
    for (i; i < lineNum; i++) {

        if (((int)inputBuff - (int)inputBuffPtr) == lineNum * BUFFERSIZE) {
            inputBuff = inputBuffPtr;
            printf("%s", inputBuff);
        }
        else
            printf("%s", inputBuff);

        inputBuff += BUFFERSIZE;
    }

    printf("\n");
    fclose(file); // close file

    free(inputBuffPtr); // free malloced pointer
    
    
}

/* Function: error
 * Description: Prints error messages to stdout
 * @param s1 - string representing error message
 * @param s2 - string to print error message
 * @return - void
 */
void error(char* s1, char* s2) {
    fprintf(stderr, "Error: %s", s1);
    perror(s2);
    exit(1);
}



