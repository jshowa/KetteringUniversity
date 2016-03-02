/* controlflow.c
 *
 * "if" processing is done with two state variables
 *    if_state and if_result
 */
#include	<stdio.h>
#include        <stdbool.h>
#include	"smsh.h"
#include        "stack.h"

enum states   { NEUTRAL, WANT_THEN, THEN_BLOCK, ELSE_BLOCK};
enum results  { SUCCESS, FAIL };

static int if_state  = NEUTRAL;
static int if_result = SUCCESS;
static int last_stat = 0;

static Stack stack_state = {.size = 0};
static Stack result_state = {.size = 0};

int	syn_err(char *);

/*
 * purpose: determine the shell should execute a command
 * returns: 1 for yes, 0 for no
 * details: if in THEN_BLOCK and if_result was SUCCESS then yes
 *          if in THEN_BLOCK and if_result was FAIL    then no
 *          if in WANT_THEN  then syntax error (sh is different)
 *          if in ELSE_BLOCK and if_result was FAIL    then yes
 *          if in ELSE_BLOCK and if_result was SUCCESS then no
 */
int ok_to_execute()
{
	int	rv = 1;		/* default is positive */

	if ( if_state == WANT_THEN ){
		syn_err("then expected");
		rv = 0;
	}
        else if ( (if_state == THEN_BLOCK && if_result == SUCCESS) ||
                  (if_state == ELSE_BLOCK && if_result == FAIL) )
		rv = 1;
        else if ( (if_state == THEN_BLOCK && if_result == FAIL) ||
                  (if_state == ELSE_BLOCK && if_result == SUCCESS))
		rv = 0;
	return rv;
}

/*
 * purpose: boolean to report if the command is a shell control command
 * returns: 0 or 1
 */
int is_control_command(char *s)
{
    return (strcmp(s,"if")==0 || strcmp(s,"then")==0 || strcmp(s,"fi")==0 || strcmp(s, "else") == 0);
}

/* function: do_control_command
 * purpose: Process "if", "then", "fi", "else" - change state or detect error
 * returns: 0 if ok, -1 for syntax error
 *   notes: I would have put returns all over the place, Barry says "no"
 */
int do_control_command(char **args)
{
	char	*cmd = args[0];
	int	rv = -1;

	if( strcmp(cmd,"if") == 0) {
                if (stack_state.size > 100)
                    rv = syn_err("too many nested if's");
		else {
                        push(&stack_state, if_state);
                        push(&result_state, if_result);
                        last_stat = process(args+1);
                        if_result = (last_stat == 0 ? SUCCESS : FAIL );
                        if_state = WANT_THEN;
                        rv = 0;
                        
		}
	}
	else if ( strcmp(cmd,"then")==0 ){
		if ( if_state != WANT_THEN )
			rv = syn_err("then unexpected");
		else {
                        if_state = THEN_BLOCK;
                        rv = 0;
		}
	}
        else if ( strcmp(cmd, "else") == 0) {
            if ( if_state != THEN_BLOCK )
                rv = syn_err("else unexpected");
            else {
                if_state = ELSE_BLOCK;
                rv = 0;
            }

        }
	else if ( strcmp(cmd,"fi")==0 ){
		if ( if_state == WANT_THEN || if_state == NEUTRAL )
			rv = syn_err("fi unexpected");
		else {
			if_state = pop(&stack_state);
                        if_result = pop(&result_state);
			rv = 0;
		}
	}
	else 
		fatal("internal error processing:", cmd, 2);
	return rv;
}

/* function: syn_err
 * purpose: handles syntax errors in control structures
 * details: resets state to NEUTRAL
 * returns: -1 in interactive mode. Should call fatal in scripts
 */
int syn_err(char *msg)
{
	if_state = NEUTRAL;
	fprintf(stderr,"syntax error: %s\n", msg);
	return -1;
}
