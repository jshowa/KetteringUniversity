/* File:   LinkedList.c
 * Author: Jacob S. Howarth
 * Class: CS-202: Systems Programming Concepts
 *
 * Description: This program allows a user to create
 * a linked list of integers.
 *
 * Created on April 8, 2009, 7:09 PM
 */

// HEADER FILE DECLARATIONS
#include <stdio.h>
#include <stdlib.h>

/* GLOBAL VARIABLES
 * struct intNode: A structure representing a linked list node
 *                 that contains the following items:
 *
 *                 int value = the integer to be stored in the node.
 *                 struct intNode* next = A pointer/reference to the
 *                 next node in the list.
 *
 * struct* head = A reference to the first node of the list.
 *                This node will only contain the reference
 *                address to the first node in the list.
 * struct* tail = A reference to the last node in the list.
 *                This node will only contain a reference to the
 *                last node of the list.
 */
struct intNode {
    int value;
    struct intNode* next;
}*head = NULL, *tail = NULL;

/* FUNCTION PROTOTYPE DECLARATIONS
 * void add(int element); // adds a new node to the end of the list
 * void printList(int* sizeOf); // prints each node in the list.
 */
void add(int element);
void printList(int* sizeOf);

int main(int argc, char** argv) {
    int sizeOf, value;

    // Get the size of the linked list from the user.
    printf("Enter size of list: ");
    scanf("%i", &sizeOf);
    
    // Ask the user to enter each integer for the linked list.
    int i;
    for (i = 0; i < sizeOf; i++) {
    printf("Enter an integer to store: ");
        scanf("%i", &value);
        add(value); // take the entered integers and create new nodes for each
                    // Do this until the list size, specified by the user is
                    // reached.
    }

    printList(&sizeOf); // print the list

    return (EXIT_SUCCESS);
}

/* Function: add
 * Parameters: int element - integer to be added to the list
 *
 * Description: Creates a new node with value of the parameter and
 *              adds it to the end of the list. If the list is empty
 *              or null, the new node is referenced by the head and
 *              tail pointers.
 *
 * Returns: void
 */
void add(int element) {

    // Allocate a space in the heap for a new node and return
    // a pointer to that node. Dereference the pointer to store
    // the parameter and set it's next pointer to null.
    struct intNode* newNode = malloc(sizeof(struct intNode));
    newNode->value = element;
    newNode->next = NULL;

    // If the list is empty and the reference of the node returned
    // by malloc is not null, set the head and tail pointers to
    // reference the new node.
    if (head == NULL && newNode != NULL) {
        head = newNode;
        tail = newNode;
    }
    else {
        // otherwise, set the next pointer of the end node to point
        // to the new node and since the tail references the end node
        // assign the reference of the end node to the tail pointer.
        tail->next = newNode;
        tail = newNode;
    }

}

/* Function: printList
 * Parameters: int* sizeOf - integer pointer that contains the reference
 *                           address of sizeOf to avoid deep copying.
 *
 * Description: Prints the list in the following format:
 *
 *              1) The first node is printed with an indicator of "Head"
 *                 plus the first nodes value.
 *              2) The following nodes up to size of the list - 1 will
 *      `          be printed with the indicator "Node" plus it's value.
 *              3) The last node is printed with an indicator of "Tail"
 *                 plus its value.
 *
 * Returns: void
 */
void printList(int* sizeOf) {
    // Create a new pointers to point to the beginning of
    // the list and allow list iteration.
    struct intNode* current = head;

    printf("\nHere is the list:\n");

    int i;
    for (i = 0; current != NULL; i++) {
        if (i == 0) // first node is printed with "Head" indicator plus its values.
            fprintf("Head  ->%3i\n", current->value);
        else if (*sizeOf - 1 == i) // last node is printed with "Tail" indicator
                                    // plus its value.
            fprintf("Tail  ->%3i\n", current->value);
        else
            // subsequent nodes are printed with "Node"
            // indicator plus their values.
            fprintf("Node%2i->%3i\n", i, current->value);

        current = current->next; // advance to the next node in the list.
    }
}

