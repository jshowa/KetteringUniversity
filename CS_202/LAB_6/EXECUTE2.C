#include	<stdio.h>
#include	<stdlib.h>
#include	<unistd.h>
#include	<signal.h>
#include	<sys/wait.h>
#include        <stdbool.h>
#include        "smsh.h"
#include	"varlib.h"
/* execute2.c - includes environmment handling */

#define     pipe_error(x)     { perror(x); }

/*
 * purpose: run a program passing it arguments
 * returns: status returned via wait, or -1 on error
 *  errors: -1 on fork() or wait() errors
 */
int execute(char *argv[])
{
	extern char **environ;
	int	pid ;
	int	child_info = -1;

	if ( argv[0] == NULL )		/* nothing succeeds	*/
		return 0;

	if ( (pid = fork())  == -1 )
		perror("fork");
	else if ( pid == 0 ){
		environ = VLtable2environ();
		signal(SIGINT, SIG_DFL);
		signal(SIGQUIT, SIG_DFL);
		execvp(argv[0], argv);
		perror("cannot execute command");
		exit(1);
	}
	else {
		if ( wait(&child_info) == -1 )
			perror("wait");
	}
	return child_info;
}

int check_redirection(char** argv) {
    int i;
    for (i = 0; argv[i] != NULL; i++)
        if (strcmp(argv[i], ">") == 0)
            return i;
    return 0;
}

int check_pipe(char **argv) {
    int i;
    for (i = 0; argv[i] != NULL; i++)
        if (strcmp(argv[i], "|") == 0)
            return i;
    return 0;
}

int execute_pipe(char **argv) {

    char **arg1 = emalloc(BUFSIZ);                              /* create two arg lists */
    char **arg2 = emalloc(BUFSIZ);
    int pid1, pid2, child_info = -1;

    int pipe_pos = check_pipe(argv);                            /* get the index of the pipe in the arg list */

    arglist_copy(argv, arg1, 0, pipe_pos);                      /* copy the first part of the argv to arg1 */
    arglist_copy(argv, arg2, pipe_pos + 1, arglist_len(argv));  /* copy second part into arg2 */

    if (arg1[0] == NULL || arg2[0] == NULL) {
        printf("Cannot execute command: null argument in pipe expression.\n");
        return -1;
    }

    if ((pid1 = fork()) == -1)
        perror("Cannot create new process.");
    if (pid1 == 0) {
        int thepipe[2];

        if (pipe(thepipe) == -1) {
            perror("Cannot create pipe.");
        }
        else if ((pid2 = fork()) == -1) {
            perror("Cannot create new process");
        }
        else if (pid2 > 0) {
            close(thepipe[1]);

            if (dup2(thepipe[0], 0) == -1) {
                perror("Cannot redirect stdin.");
            }
            else {
                close(thepipe[0]);
                execvp(arg2[0], arg2);
                perror("Command failed.");
                exit(1);
            }
        }
        else {
            close(thepipe[0]);

            if (dup2(thepipe[1], 1) == -1) {
                perror("Cannot redirect stdout.");
            }
            else {
                close(thepipe[1]);
                execvp(arg1[0], arg1);
                perror("Command failed.");
                exit(1);
            }
        }
    }
    else {
        if (wait(&child_info) == -1) {
            perror("wait");
        }
    }

}

int execute_redirect(char **argv) {

    int rv = -1, pid, io_direct_pos, k;
    char **argv_cpy = emalloc(BUFSIZ);

        if ((pid = fork()) == -1)
            perror("Cannot create new process");
        else if (pid == 0) {
            environ = VLtable2environ();
            signal(SIGINT, SIG_DFL);
            signal(SIGQUIT, SIG_DFL);
            if ((io_direct_pos = check_redirection(argv)) > 0) {   /* check for redirection ">" in string */
                close(1);
                int fd = creat(argv[++io_direct_pos], 0644);
                for (k = 0; k < io_direct_pos - 1; k++)
                    argv_cpy[k] = argv[k];

                argv_cpy[k] = NULL;
            }
            execvp(argv_cpy[0], argv_cpy);
            perror("Command failed.");
            exit(1);
        }
        else {
            if (wait(&rv) == -1)
                perror("wait.");
        }

    return rv;

}

void arglist_copy(char **src, char **dest, int start, int end) {
    int i, j;
    int str_len = end - start;
    for (i = 0, j = start; i < str_len; i++, j++) {
        dest[i] = src[j];
    }
    dest[i] = NULL;
}

int arglist_len(char **argv) {
    int i = 0;
    while (argv[i] != NULL)
        i++;
    return i;
}
