#include	<stdio.h>
#include        <stdbool.h>
#include	"smsh.h"

/* process2.c   - version 2 - supports builtins
 * command processing layer
 * 
 * The process(char **arglist) function is called by the main loop
 * It sits in front of the execute() function.  This layer handles
 * three main classes of processing:
 * 	a) built-in functions (e.g. exit(), set, =, read, .. )
 * 	b) control structures (e.g. if, else, etc..)
 *      c) I/O redirection (e.g. pipes, redirect, etc..)
 */

int is_control_command(char *);         /* checks if the command in the arg list is a if, else, then, or fi command */
int do_control_command(char **);        /* processes control commands by setting states */
int ok_to_execute();                    /* checks the state of the flow control and executes accordingly */
int builtin_command(char **, int *);    /* processes builtin commands such as set, exit, etc. */

/*
 * purpose: process user command
 * returns: result of processing command
 * details: if a built-in then call appropriate function, if not execute()
 *  errors: arise from subroutines, handled there
 */
int process(char **args)
{
	int		rv = 0;

	if ( args[0] == NULL )
		rv = 0;
	else if ( is_control_command(args[0]) )
		rv = do_control_command(args);
	else if ( ok_to_execute() ) {
                if ( check_pipe(args) )                     /* check if arg list contains pipes */
                        rv = execute_pipe(args);            /* if so execute special pipe execution */
                else if (check_redirection(args))           /* same for redirection... */
                        rv = execute_redirect(args);
                else if ( !builtin_command(args,&rv) )
			rv = execute(args);
                
        }
	return rv;
}

