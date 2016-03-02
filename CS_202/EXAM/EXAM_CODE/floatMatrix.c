/* 
 * File:   floatMatrix.c
 * Author: jshowa
 *
 * Created on May 8, 2009, 5:05 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*
 * 
 */

#define MAX_BUF_LEN 512

float** matrixConstruct(int row, int column) {

    float **matrix;
    int i;

    matrix = malloc(row * sizeof(float*));

    if (matrix == NULL)
        exit(1);

    for (i = 0; i <= row - 1; i++) {

        matrix[i] = malloc(column * sizeof(float));
        
        if (matrix[i] == NULL) {
            exit(1);
        }
    }
    return matrix;
}



void addNumbers(float** matrix, int row, int column) {
    float value;
    int i, j;

    for (i = 0; i < row; i++)
        for (j = 0; j < column; j++) {
            printf("Enter a floating point value: ");
            scanf("%f", &value);
            matrix[i][j] = value;
        }
}

void printVector(float* vector, int length) {

    int i;

    if (vector != NULL) {
        printf("\nThis is the vector for the matrix:\n");

        for (i = 0; i < length; i++)
            printf("%.2f\n", vector[i]);
    }
        

}

float *createVector(float** matrix, int row, int column) {

    int i;
    float *vector;

    vector = malloc(row * sizeof(float));

    if (row == column && vector != NULL) {
        for (i = 0; i < row; i++)
            vector[i] = matrix[i][i];

        return vector;
    }
    else
        return NULL;
}

void matrixDeconstruct(float** matrix, int rows) {
    int i;
    for (i = 0; i < rows; i++)
        free(matrix[i]);
    free(matrix);
}


int main(int argc, char** argv) {

    float **matrix;
    float *vector;

    char inputLine[MAX_BUF_LEN], *colStr, *rowStr;
    char *newLineChar;
    int row, column;

    printf("Enter a row number: ");
    fgets(inputLine, MAX_BUF_LEN, stdin);

    newLineChar = strstr(inputLine, "\n");

    if (newLineChar == NULL)
        exit(1);
    else {
        *newLineChar = '\0';
        rowStr = inputLine;
    }

    row = atoi(rowStr);

    printf("Enter a column number: ");
    fgets(inputLine, MAX_BUF_LEN, stdin);

    newLineChar = strstr(inputLine, "\n");

    if (newLineChar == NULL)
        exit(1);
    else {
        *newLineChar = '\0';
        colStr = inputLine;
    }

    column = atoi(colStr);
    
    matrix = matrixConstruct(row, column);

    addNumbers(matrix, row, column);
    vector = createVector(matrix, row, column);

    if (vector != NULL)
        printVector(vector, row);

    matrixDeconstruct(matrix, row);

    return (EXIT_SUCCESS);
}

