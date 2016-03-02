#include	<stdio.h>
#include	<stdlib.h>
#include	<unistd.h>
#include	<signal.h>
#include	<sys/wait.h>
#include        <stdbool.h>
#include	"smsh.h"
#include	"varlib.h"

/**
 **	small-shell version 4
 **		first really useful version after prompting shell
 **		this one parses the command line into strings
 **		uses fork, exec, wait, and ignores signals
 **/
int main(int argc, char** argv)
{
	char	*cmdline, *prompt, **arglist;
	int	result, process(char **);
        int     i = 1;
	void	setup();

	prompt = DFL_PROMPT ;
	setup();

        while (i < argc) {
            result = process_script(argv[i]);
            i++;
        }

	while ( (cmdline = next_cmd(prompt, stdin)) != NULL ){
		if ( (arglist = splitline2(cmdline)) != NULL  ){
			result = process(arglist);
			freelist(arglist);
		}
		free(cmdline);
	}
	return 0;
}

int process_script(char* arg) {
    FILE *fp;
    char **arglist;
    char *line;
    int result;

    line = (char *)emalloc(BUFSIZ);

    fp = fopen(arg, "r");

    if (fp == NULL) {
        fprintf(stderr, "Could not open script file\n");
        return -1;
    }

    /***UNKNOWN BUG - READS FILE TWICE WHILE RUNNING NESTED IF SCRIPT***/
    while (fgets(line, BUFSIZ, fp) != NULL) {
        line[strlen(line) - 1] = '\0';

        if (line[0] != '#') {
            check_comments(line);
            if( (arglist = splitline2(line)) != NULL ) {
                result = process(arglist);
                freelist(arglist);
            }
       }
    }
}

int check_comments(char *line) {

    char *cp;
    int trim = -1;
    int i;

    cp = line;

    for (i = 1; cp[i] != '\0'; i++) {
        if (cp[i] == '#' && cp[i - 1] != '\\') {
            trim = i;
            break;
        }
    }

    if (trim != -1)
        line[i] = '\0';

}

/*
 * purpose: initialize shell
 * returns: nothing. calls fatal() if trouble
 */
void setup()
{
	extern char **environ;

	VLenviron2table(environ);
	signal(SIGINT,  SIG_IGN);
	signal(SIGQUIT, SIG_IGN);
}

void fatal(char *s1, char *s2, int n)
{
	fprintf(stderr,"Error: %s,%s\n", s1, s2);
	exit(n);
}
