/*
 * File:   stack.h
 * Author: jshowa
 *
 * Description: This is a stack header that defines a stack structure and
 * it's maximum height requirement and the function prototypes for a stack.
 *
 * Created on June 13, 2009, 8:04 PM
 */

#ifndef _STACK_H
#define	_STACK_H

#ifdef	__cplusplus
extern "C" {
#endif

#define MAX_STACK_SIZE  100                 /* Maximum stack height allowed */

// ***STACK DEFINITION*** //
typedef struct stack {
    int states[MAX_STACK_SIZE];             /* Stack elements */
    int size;                               /* current stack size */
}Stack;

typedef struct stack *StackPtr;

// ***STACK FUNCTION PROTOTYPES*** //
StackPtr StackConstructor(void);                /* Create new stack object */
void initStack1(StackPtr);                      /* initializes stack components */
void push(StackPtr, int);                       /* adds/pushes an element onto the stack */
int pop(StackPtr);                              /* removes/pops an element off the stack */
void StackDeconstructor(StackPtr);              /* Free stack object */

#ifdef	__cplusplus
}
#endif

#endif	/* _STACK_H */
