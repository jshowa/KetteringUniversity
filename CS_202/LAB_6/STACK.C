#include "stack.h"
#include <stdlib.h>

StackPtr StackConstructor() {
    StackPtr temp;
    temp = (StackPtr)malloc(sizeof(Stack));

    if (temp != NULL) {
        initStack1(temp);
    }

    return temp;
}

void initStack1(StackPtr stack) {
    int i;
    for (i = 0; i < MAX_STACK_SIZE; i++)
        stack->states[i] = -1;

    stack->size = -1;
}


void push(StackPtr stack, int state) {
    if ( stack != NULL && stack->size < MAX_STACK_SIZE ) {
        stack->states[stack->size] = state;
        stack->size++;
    }
}

int pop(StackPtr stack) {
    int rv = -1;

    if (stack != NULL && stack->size > 0) {
         stack->size--;
         rv = stack->states[stack->size];
         stack->states[stack->size] = 0;
    }

    return rv;
}


void StackDeconstructor(StackPtr stack) {
    free(stack);
}
