#ifndef _MAIN_ASM_H
#define _MAIN_ASM_H

#ifdef __cplusplus
    extern "C" { /* our assembly functions have C calling convention */
#endif

//void digit(char, char);
void enableInts(void);
extern char dig[4];      // extern equivalent to XREF in assembly

  /* interface to my assembly main function */

#ifdef __cplusplus
    }
#endif

#endif /* _MAIN_ASM_H */
