/* splitline.c - commmand reading and parsing functions for smsh
 *    
 *    char *next_cmd(char *prompt, FILE *fp) - get next command
 *    char **splitline(char *str);           - parse a string

 */

#include	<stdio.h>
#include	<stdlib.h>
#include	<string.h>
#include        <stdbool.h>
#include	"smsh.h"

/*
 * purpose: read next command line from fp
 * returns: dynamically allocated string holding command line
 *  errors: NULL at EOF (not really an error)
 *          calls fatal from emalloc()
 *   notes: allocates space in BUFSIZ chunks.
 */
char * next_cmd(char *prompt, FILE *fp)
{
	char	*buf ; 				/* the buffer		*/
	int	bufspace = 0;			/* total size		*/
	int	pos = 0;			/* current position	*/
	int	c;				/* input char		*/

	printf("%s", prompt);				/* prompt user	*/
	while( ( c = getc(fp)) != EOF ) {

		/* need space? */
		if( pos+1 >= bufspace ){		/* 1 for \0	*/
			if ( bufspace == 0 )		/* y: 1st time	*/
				buf = emalloc(BUFSIZ);
			else				/* or expand	*/
				buf = erealloc(buf,bufspace+BUFSIZ);
			bufspace += BUFSIZ;		/* update size	*/
		}

		/* end of command? */
		if ( c == '\n' )
			break;

		/* no, add to buffer */
		buf[pos++] = c;
	}
	if ( c == EOF && pos == 0 )		/* EOF and no input	*/
		return NULL;			/* say so		*/
	buf[pos] = '\0';
	return buf;
}

/**
 **	splitline ( parse a line into an array of strings )
 **/
#define	is_delim(x) ((x)==' '||(x)=='\t')

/*
 * purpose: special function for changing directory that doesn't split the second
 *          argument by spaces.
 * returns: a NULL-terminated array of pointers to copy the tokens
 *          or NULL if no tokens on the line
 *  action: traverse the array, locate strings, make copies
 *    note: strtok() could work, but we may want to add quotes later
 */
char ** cd(char *line)
{
	char	*newstr();
	char	**args ;
	int	spots = 0;			/* spots in table	*/
	int	bufspace = 0;			/* bytes in table	*/
	int	argnum = 0;			/* slots used		*/
	char	*cp = line;			/* pos in string	*/
	char	*start;
	int	len;
        bool    delim_on = true;
        char*   temp;

	if ( line == NULL )			/* handle special case	*/
		return NULL;

	args     = emalloc(BUFSIZ);		/* initialize array	*/
	bufspace = BUFSIZ;
	spots    = BUFSIZ/sizeof(char *);

        //char *tok = strtok(line, " ");

	while( *cp != '\0' )
	{
		while ( is_delim(*cp) )		/* skip leading spaces	*/
			cp++;
		if ( *cp == '\0' )		/* quit at end-o-string	*/
			break;

		/* make sure the array has room (+1 for NULL) */
		if ( argnum+1 >= spots ){
			args = erealloc(args,bufspace+BUFSIZ);
			bufspace += BUFSIZ;
			spots += (BUFSIZ/sizeof(char *));
		}

		/* mark start, then find end of word */
                start = cp;
		len   = 0;
		while (*cp != '\0') {
                    if (is_delim(*cp) && delim_on) {
                        delim_on = false;
                        break;
                    }
                    len++;
                    cp++;
                }

		args[argnum++] = newstr(start, len);
	}
	args[argnum] = NULL;
	return args;
}

/*
 * purpose: Tokenizes the user input given to the shell by spaces
 *          and processes them into an argument list.
 * returns: a NULL-terminated array of pointers to copy the tokens
 *          or NULL if no tokens on the line
 *  action: traverse the array, locate strings, make copies
 */
char **splitline2(char *line) {

    char *newstr();
    char **args ;
    int	argnum = 0;
    char *input;

    char *tok;
    char *delim = " ";

    if (line == NULL)
        return NULL;

    input = emalloc(BUFSIZ);
    strncpy(input, line, strlen(line));
    args = emalloc(BUFSIZ);

    tok = strtok(line, delim);

    if (strcmp(tok, "cd") == 0) {
        args = cd(input);
        return args;
    }
    else {
        while (tok != NULL) {
            tok = check_arg(tok, true);
            args[argnum++] = newstr(tok, (strlen(tok)));
            tok = strtok(NULL, delim);
        }
    }

    args[argnum] = NULL;
    return args;


}

/*
 * purpose: special function that checks for special characters and performs
 *          variable substitution if needed.
 * parameters: token - string to check for special characters in
 *             spacing - controls triming of whitespace or not (used mostly
 *             for cd function).
 * returns: a string representing the value of the substituted variable
 *    note: strtok() could work, but we may want to add quotes later
 */
char *check_arg(char* token, bool spacing) {
    char *cptr;
    char *value, *val_copy;
    char *tail_str, *temp;
    int k, n = 0;
    bool punct = false;


    int i = 0, j = 1;


    while (is_delim(*token) && spacing)                /* trim leading spaces */
        token++;

    cptr = token;

    if (cptr[i] == '$') {
        char *var_name = emalloc(strlen(token) + 1);          /* allocate memory for var_name */
        for(j; cptr[j] != '\0' ; j++) {                       /* copy token without dollar sign until punct or EOL is reached */
            if (ispunct(cptr[j]) && cptr[j] != '_') {
                punct = true;
                break;
            }
            var_name[j - 1] = cptr[j];
        }
        
        var_name[--j] = '\0';                                 /* add null terminator */

        value = VLlookup(var_name);                           /* lookup variable and get it's value and copy it */
        val_copy = emalloc(strlen(value) + 1);
        val_copy[strlen(value)] = '\0';
        strncpy(val_copy, value, strlen(value));
        
        if (strcmp(val_copy, "") != 0) {
            if (punct == true) {                              /* if punctuation existed, copy the rest of the */
                j++;                                          /* string and concatenate it with the substituted */
                tail_str = emalloc((strlen(token) - j) + 1);  /* value of the variable. */
                temp = token;

                for (k = j; temp[k] != '\0'; k++)
                    tail_str[n++] = temp[k];
                
                tail_str[n] = '\0';
                strcat(val_copy, tail_str);
            }
            
            return val_copy;
        }
            

    }

    return token;

}

/*
 * purpose: constructor for strings
 * returns: a string, never NULL
 */
char *newstr(char *s, int length)
{
	char *rv = emalloc(length+1);

	rv[length] = '\0';
	strncpy(rv, s, length);
	return rv;
}

/*
 * purpose: free the list returned by splitline
 * returns: nothing
 *  action: free all strings in list and then free the list
 */
void freelist(char **list)
{
	char	**cp = list;
	while( *cp )
		free(*cp++);
	free(list);
}

/*
 * purpose: easy memory allocation function that takes care of NULL
 *          pointer checking.
 * parameters: size_t n - a structure containing the size of the memory to allocate
 * returns: void pointer
 */
void * emalloc(size_t n)
{
	void *rv ;
	if ( (rv = malloc(n)) == NULL )
		fatal("out of memory","",1);
	return rv;
}

/*
 * purpose: easy memory allocation function that takes care of NULL
 *          pointer checking.
 * parameters: size_t n - a structure containing the size of the memory to reallocate
 *             void* p - pointer that points to the address of the memory to be reallocated
 * returns: void pointer
 */
void * erealloc(void *p, size_t n)
{
	void *rv;
	if ( (rv = realloc(p,n)) == NULL )
		fatal("realloc() failed","",1);
	return rv;
}

