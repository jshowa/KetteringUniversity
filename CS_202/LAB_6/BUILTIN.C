/* builtin.c
 * contains the switch and the functions for builtin commands
 */

#include	<stdio.h>
#include	<string.h>
#include	<ctype.h>
#include        <unistd.h>
#include        <stdbool.h>
#include	"smsh.h"
#include	"varlib.h"

int assign(char *);
int okname(char *);
int ch_exit_arg(char *);

/* function: builtin_command
 * purpose: run a builtin command
 * returns: 1 if args[0] is builtin, 0 if not
 * details: test args[0] against all known builtins.  Call functions
 */
int builtin_command(char **args, int *resultp)
{
	int rv = 0;
        char* var_val;

	if ( strcmp(args[0],"set") == 0 ){	     /* 'set' command? */
		VLlist();
		*resultp = 0;
		rv = 1;
	}
	else if ( strchr(args[0], '=') != NULL ){   /* assignment cmd */
		*resultp = assign(args[0]);
		if ( *resultp != -1 )		    /* x-y=123 not ok */
			rv = 1;
	}
        else if ( strcmp(args[0], "export") == 0 ){         /* export command */
		if ( args[1] != NULL && okname(args[1]) )
			*resultp = VLexport(args[1]);
		else
			*resultp = 1;
		rv = 1;
	}
        else if (strcmp(args[0], "cd") == 0) {              /* cd (change directory) command */
            if (args[1] != NULL)
               *resultp = chdir(args[1]);
            if (*resultp != -1)
                rv = 1;
        }
        else if (strcmp(args[0], "exit") == 0) {            /* exit command */
           if (args[1] == NULL) {
               exit(0);
               rv = 1;
           }
           
           if (args[1] != NULL && ch_exit_arg(args[1])) {
               exit(atoi(args[1]));
               rv = 1;
           }
           else
               rv = 0;
       
        }
        else if (strcmp(args[0], "read") == 0) {        /* read command, i.e. reads a value from stdin and assigns to variable argument */
            if(args[1] != NULL && okname(args[1])) {
                var_val = next_cmd(DFL_PROMPT, stdin);

                if ( VLstore(args[1], var_val) == 0 )
                    rv = 1;
                else
                    rv = 0;
            }
            else 
                rv = 0;
        }
        else if (strcmp(args[0], "smsh") == 0) {            /* smsh builtin script execution */
            int i = 1;
            while (args[i] != '\0') {
                process_script(args[i]);
                i++;
            }
            rv = 1;
        }
	return rv;
}

/* function: assign
 * purpose: execute name=val AND ensure that name is legal
 * returns: -1 for illegal lval, or result of VLstore
 * warning: modifies the string, but retores it to normal
 */
int assign(char *str)
{
	char	*cp;
	int	rv ;

	cp = strchr(str,'=');
	*cp = '\0';
	rv = ( okname(str) ? VLstore(str,cp+1) : -1 );
	*cp = '=';
	return rv;
}

/* function: okname
 * purpose: determines if a string is a legal variable name
 * returns: 0 for no, 1 for yes
 */
int okname(char *str)
{
	char	*cp;

	for(cp = str; *cp; cp++ ){
		if ( (isdigit(*cp) && cp==str) || !(isalnum(*cp) || *cp=='_' ))
			return 0;
	}
	return ( cp != str );	/* no empty strings, either */
}

/* function: ch_exit_arg
 * purpose: determines if argument for exit command is a number
 * returns: 0 for no, 1 for yes
 */
int ch_exit_arg(char *str) {

    char *cp;

    for (cp = str; *cp; cp++)
        if (!(isdigit(*cp)))
            return 0;

    return 1;
}
