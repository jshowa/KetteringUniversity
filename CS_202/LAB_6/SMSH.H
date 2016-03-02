#define	YES	1
#define	NO	0
#define	DFL_PROMPT	"> "

char	*next_cmd();
char	**cd(char *);
void	freelist(char **);
void	*emalloc(size_t);
void	*erealloc(void *, size_t);
int	execute(char **);
void	fatal(char *, char *, int );
int     process_script(char *);
int     check_comments(char *);
char    **splitline2(char *);
char    *check_arg(char *, bool);
int     check_redirection(char **);
int     execute_pipe(char **);
int     check_pipe(char **);
void    arglist_copy(char **, char**, int, int);
int     arglist_len(char**);
int     execute_redirect(char**);

int	process();
