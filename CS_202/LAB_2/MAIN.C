/* 
 * File:   main.c
 * Author: Jacob S. Howarth
 * Class: CS-202: Systems Programming Concepts
 *
 * Description: This program creates and manipulates a simple book
 *              inventory database. The program allows a user to
 *              load and save an inventory list of books from a text
 *              file, print the inventory list to stdout, add a new item
 *              to the list, and compute the average price of the books
 *              in the list.
 *
 * Created on April 17, 2009, 6:20 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>


/* -- ENUM TABLE --
 * MAX_BUFFER_LENGTH -- constant representing the maximum input char length
 * NUM_OF_DATA_FIELDS -- constant representing the number of fields delimited
 *                       by commas per book in an invenotry list text file
 * MAX_CHAR_VALUE -- constant representing the maximum number of chars for 
 *                   a books title, author, and price
 */
enum {
    MAX_BUFFER_LENGTH = 512,
    NUM_OF_DATA_FIELDS = 3,
    MAX_CHAR_VALUE = 100
};

/* -- BOOK STRUCT --
 * This structure defines a book
 * type which contains the author
 * of the book, the price and
 * the title of the book. These
 * values are represented as strings.
 */
typedef struct book {
    char title[MAX_CHAR_VALUE]; // title of book
    char author[MAX_CHAR_VALUE]; // author
    char price[MAX_CHAR_VALUE]; // book price
}BOOK;

/* -- NODE STRUCT --
 * This structure defines a linked list
 * node type which contains a book
 * element and a reference to the next
 * node in the list.
 */
typedef struct node {
    BOOK book; // book struct to be stored in the node
    struct node* next; // pointer to next node in the list
}NODE;

// head and tail node pointers for the linked list.
// head points to the first node, tail to the last node.
NODE *head = NULL, *tail = NULL;


/* -- FUNCTION PROTOTYPES --
 * void usrInterface(void) -- funciton that prints the user interface menu
 * int sizeOf(void) -- computes the number of items in the linked list
 * void add(char title[], char author[], char price[]) -- add function for linked list
 * void addBook(void) -- function that allows a user to create a new book and add it
 *                       to the linked list
 * void printInventory(char option, FILE* fptr) -- A function that can print the
 *                                              the elements in the linked list to
 *                                              stdout or a file specified by the
 *                                              file pointer based on the char option.
 * void computerAvgPrice(void) -- takes each books price in the list, adds them
 *                                togehter, and divides the sum by the size of the
 *                                list
 * void readFile(void) -- reads a file, as specified by the user and parses
 *                        each field, delimited by commas, into the book
 *                        structure and constructs a linked list of books
 * void saveFile(void) -- saves the list to a file, as specified by the user,
 *                        and calls the printInventory function to print the
 *                        book records to the file with the appropriate comma
 *                        delimiting each field.
 */
void usrInterface(void);
int sizeOf(void);
void add(char title[MAX_CHAR_VALUE], char author[MAX_CHAR_VALUE], char price[MAX_CHAR_VALUE]);
void addBook(void);
void computeAvgPrice(void);
void printInventory(char option, FILE* fptr);
void readFile(void);
void saveFile(void);

/* Function: main
 * Arguments: int argc -- number of arguments, including the file name, to main when
 *                        compiled
 *            char** argv -- A reference to a reference pointing at the first char of the
 *                           the arguments passed to main
 *
 * Description: The function is where the program begins execution and calls
 *              the userInterface function to loop the menu
 *
 * Returns: int -- integer representing the exit code (success being 0, failure
 *                 being -1) returned when the program ends
 */
int main(int argc, char** argv) {

    usrInterface();

    return (EXIT_SUCCESS);
}

/* Function: userInterface
 * Arguments: void
 *
 * Description: This function prints the menu to stdout and prompts the user to
 *              enter commands. The function also makes the selection on what function
 *              will be called depending on the program.
 *
 * Returns: void
 */
void usrInterface(void) {

    char readLine[MAX_BUFFER_LENGTH];

    while (true) { // run the program in an endless loop, until the 'x' command
                    // is given.
        printf("Book Inventory v1.0\n");
        printf("Programmed By: Jacob Howarth\n");
        printf("CS-202 Lab II\n\n");
        printf("*************COMMAND MENU*******************\n");
        printf("Please type a command option below:\n");
        printf("\n");
        printf("r: read an inventory of books from\n"
               "   a text file.\n\n");
        printf("i: insert a new book into the inventory\n\n");
        printf("s: save books to an inventory text file\n\n");
        printf("a: compute the average price of the book\n"
                "  inventory.\n\n");
        printf("p: print out the information from the \n"
                "  inventory.\n\n");
        printf("q: exit the program\n");
        printf("********************************************\n\n");

        printf("Please enter a command: ");

        fgets(readLine, MAX_BUFFER_LENGTH, stdin);

        // error checking loop to check for invalid commands
        while (strlen(readLine) > 2) {
            printf("\n\nPlease enter a valid character from\n"
                "the above menu.\n\n");
            printf("Please enter a command: "); // prompt the user to re-enter the command
            fgets(readLine, MAX_BUFFER_LENGTH, stdin);
        }

        // switch used to select the appropriate function relative to the
        // command
        switch (readLine[0]) {
            case 'r': readFile();
                    break;
            case 'i': addBook();
                    break;
            case 's': saveFile();
                    break;
            case 'a': computeAvgPrice();
                    break;
            case 'p': printInventory('s', NULL);
                    break;
            case 'q': printf("Exiting program...");
                      exit(0);
                    break;
            default: printf("\n\nPlease enter a valid character from\n"
                "the above menu.\n\n");
                    break;
        }
    }
}

/* Function: readFile
 * Arguments: void
 *
 * Description: Prompts the user to enter an existing file to read from and
 *              checks if the file can be opened. The function then proceeds
 *              to parse the text file by seperating and storing each field
 *              in the inventory list by title, author, and price. It constructs
 *              the linked list after parsing the require info.
 *
 * Returns: void
 */
void readFile(void) {
        FILE* fptr;
        char fileLine[MAX_BUFFER_LENGTH];
        char fileName[MAX_BUFFER_LENGTH];
        char inputLine[MAX_BUFFER_LENGTH];
        char title[MAX_CHAR_VALUE], author[MAX_CHAR_VALUE], price[MAX_CHAR_VALUE];

        printf("Please enter a file name with extension: ");
        fgets(inputLine, MAX_BUFFER_LENGTH, stdin);

        if (head != NULL) { // delete the current linked list every time a new
            head = NULL;    // file is read
            tail = NULL;
        }

        // If input was entered, replace the newline char at the end of the
        // file name with the null char, otherwise the file will not open
        // properly.
        if (inputLine != NULL) {
            int i;
            for (i = 0; inputLine[i] != '\n'; i++)
                fileName[i] = inputLine[i];
            fileName[i] = '\0';
            fptr = fopen(fileName, "r");
        }
            
        // If the user types a bogus file name, or one without an extension, or
        // the file does not open properly, then ask the user to input another
        // file name.
        while (fptr == NULL) {
            printf("\nFile could not be opened, please\n"
                    "specify a new file.\n\n");

            printf("Please enter a file name with extension: ");
            fgets(inputLine, MAX_BUFFER_LENGTH, stdin);

            if (inputLine != NULL) {
                int i;
                for (i = 0; inputLine[i] != '\n'; i++)
                    fileName[i] = inputLine[i];
                fileName[i] = '\0';
                fptr = fopen(fileName, "r");
            }
        }

        // Get a line from the file...
        while (fgets(fileLine, MAX_BUFFER_LENGTH, fptr) != NULL) {
            int j, k, n;
            int counter = 0; // field parse counter
            while (counter < NUM_OF_DATA_FIELDS) { // parse three fields
                switch (counter) {
                    case 0: for (j = 0; fileLine[j] != ','; j++) // parse the file line
                                title[j] = fileLine[j];         // until the comma char is reached
                            title[j] = '\0';
                            break;
                    case 1: for (k = 0; fileLine[j] != ','; k++, j++) // parse the author...
                                author[k] = fileLine[j];
                            author[k] = '\0';
                            break;
                    case 2: for (n = 0; fileLine[j] != '\r'; n++, j++) // parse the price...
                                price[n] = fileLine[j];
                            price[n] = '\0';
                            break;
                }
                j++; // place holder in the file line string
                counter++; // increment counter after one field is parsed...
            }
            add(title, author, price);
        }

        printf("\nFile read successfully!\n\n");

        fclose(fptr);
}

/* Function: saveFile
 * Arguments: void
 *
 * Description: Prompts the user to enter an existing file to save to and
 *              checks if the file can be opened. It then calls the
 *              printInventory function which writes the linked list
 *              to the file.
 *
 * Returns: void
 */
void saveFile(void) {
    FILE* fptr; // file pointer that references where the file will be saved
    char fileName[MAX_BUFFER_LENGTH]; // file name buffer
    char inputLine[MAX_BUFFER_LENGTH]; // user input buffer

    printf("Enter a file name, with extension, to save too: ");
    fgets(inputLine, MAX_BUFFER_LENGTH, stdin);

    // If input was entered, replace the newline char at the end of the
    // file name with the null char, otherwise the file will not open
    // properly.
    if (inputLine != NULL) {
        int i;
        for (i = 0; inputLine[i] != '\n'; i++)
            fileName[i] = inputLine[i];
        fileName[i] = '\0'; // add null to end of file name
        fptr = fopen(fileName, "w");
    }

    // If the user types a bogus file name, or one without an extension, or
    // the file does not open properly, then ask the user to input another
    // file name.
    while (fptr == NULL) {
        printf("File does not exist, please\n"
                "specify a new file.\n");

        printf("Enter a file name, with extension, to save too: ");
        fgets(inputLine, MAX_BUFFER_LENGTH, stdin);

        if (inputLine != NULL) {
            int i;
            for (i = 0; inputLine[i] != '\n'; i++)
                fileName[i] = inputLine[i];
            fileName[i] = '\0';
            fptr = fopen(fileName, "w");
        }
    }
    //
    
    printInventory('f', fptr); // call this function to write the linked list
                                // to the file as entered by the user
}

/* Function: add
 * Arguments: char title[] -- A string of max 100 characters, representing the title of a book
 *            char author[] -- A string of max 100 characters, representing the author of a book
 *            char price[] -- A string of max 100 characters, representing the price of a book
 *
 * Description: This method adds an element/book to the end of a linked list
 *
 * Returns: void
 */
void add(char title[MAX_CHAR_VALUE], char author[MAX_CHAR_VALUE], char price[MAX_CHAR_VALUE]) {
    NODE* newNodePtr;

    //Allocate space for a new BOOK structure on the
    //heap and return a pointer to it.
    newNodePtr = malloc(sizeof(NODE));

    // if the new node pointer allocated from malloc is not null, add the required
    // information to the new book structure contained in the new node
    if (newNodePtr != NULL) {
        strncpy(newNodePtr->book.title, title, MAX_CHAR_VALUE);
        strncpy(newNodePtr->book.author, author, MAX_CHAR_VALUE);
        strncpy(newNodePtr->book.price, price, MAX_CHAR_VALUE);
    }

    // If the list is empty, point head and tail to the new node
    if (head == NULL) {
        head = newNodePtr;
        tail = newNodePtr;
    }
    else { // if the list has nodes in it, store the new node at the end
            // of the list and reset tail to point to that node.
        tail->next = newNodePtr;
        tail = newNodePtr;
        tail->next = NULL;
    }

}

/* Function: sizeOf
 * Arguments: void
 *
 * Description: Returns the number of nodes/elements in a linked list
 *
 * Returns: int -- integer representing the number of elements in the list
 */
int sizeOf(void) {
    int counter = 0;
    NODE* current = head;

    while (current != NULL) {
        counter++;
        current = current->next;
    }

    return counter;
}

/* Function: addBook
 * Arguments: void
 *
 * Description: Prompts the user to enter in a title, author, and price
 *              for the new book and inserts the new book into the linked
 *              list.
 *
 * Returns: void
 */
void addBook(void) {

    char title[MAX_CHAR_VALUE];
    char author[MAX_CHAR_VALUE];
    char price[MAX_CHAR_VALUE];
    char inputLine[MAX_BUFFER_LENGTH]; // input line from user, used to buffer
                                       // what the user types, buffers 512 chars

    printf("\nEnter the title of the book: ");
    fgets(inputLine, MAX_BUFFER_LENGTH, stdin);

    // Since hitting enter adds a '\n' char to the string, the '\n' char
    // must be removed and a null char inserted in it's place to be a legitimate
    // string
    int i;
    for (i = 0; inputLine[i] != '\n' && inputLine[i] != '\r'; i++)
        title[i] = inputLine[i];
    title[i] = '\0';

    // This process repeats for the author and the price...
    printf("\nEnter the author of the book: ");
    fgets(inputLine, MAX_BUFFER_LENGTH, stdin);

    for (i = 0; inputLine[i] != '\n' && inputLine[i] != '\r'; i++)
        author[i] = inputLine[i];
    author[i] = '\0';

    printf("\nEnter the price of the book: ");
    fgets(inputLine, MAX_BUFFER_LENGTH, stdin);

    for (i = 0; inputLine[i] != '\n' && inputLine[i] != '\r'; i++)
        price[i] = inputLine[i];
    price[i] = '\0';

    add(title, author, price); // add the book to the linked list

    printf("\nBook successfully inserted!\n\n");

}
/* Function: computeAvgPrice
 * Arguments: void
 *
 * Description: Computes the average price of the books stored in the
 *              linked list.
 *
 * Returns: void
 */
void computeAvgPrice(void) {

    NODE* current = head;
    char* end;
    double averagePrice = 0;

    // If the linked list is empty, there are no prices to average so print
    // the message instead
    if (head == NULL)
        printf("\nCurrent inventory is empty. Please load\n"
                "an inventory file using the 'r' command.\n"
                "That contains a list of book records. Or use\n"
                "the 'i' command, to create a new inventory\n"
                "list.\n\n");
    else {
        // Parse the price to a double value and add it to average price
        while (current != NULL) {
            averagePrice += strtod(current->book.price, &end);
            current = current->next;
        }
    }

    // Print the average price divided by the size of the linked list
    printf("\nAverage book price: %.2f\n\n", averagePrice / sizeOf());
}

/* Function: printInventory
 * Arguments: char option -- A char indicating how to print the list. The value 's'
 *                           prints the list to stdout, the value 'f' prints the
 *                           list to a file.
 *            FILE* fptr -- the file pointer of which to write the linked list of
 *                          books to if the 'f' option is chosen.
 *
 * Description: Prints the linked list of books to stdout if the 's' option is
 *              chosen, or a file if the 'f' option is chosen. The file pointer
 *              represents a reference to the file to print the linked list too.
 *
 * Returns: void
 */
void printInventory(char option, FILE* fptr) {

    NODE* current = head;
    char test[MAX_BUFFER_LENGTH];

    // if head is null, there is nothing to print because the list is
    // empty, so print a message saying so
    if (head == NULL)
            printf("\nCurrent inventory is empty. Please load\n"
                "an inventory file using the 'r' command.\n"
                "That contains a list of book records. Or use\n"
                "the 'i' command, to create a new inventory\n"
                "list.\n\n");
    else {

        // if the 'f' option is chosen, write the book information in the
        // linked list to a file
        if (option == 'f') {
            while (current != NULL) {
                fprintf(fptr, "%s,", current->book.title);
                fprintf(fptr, "%s,", current->book.author);
                fprintf(fptr, "%s\r\n", current->book.price);
                current = current->next;
            }
                printf("\nFile saved successfully!\n\n");
        }

        // if the 's' option is chosen, print the book information in the list
        // to stdout
        if (option == 's') {
            int counter = 1;
            printf("\n");

            while (current != NULL) {
                printf("Book %i -> %s, %s, %s\n", counter, current->book.title,
                        current->book.author, current->book.price);
                current = current->next;
                counter++;
            }

            printf("\n\n");
        }
    }
    fclose(fptr);  // close the file when done
}






