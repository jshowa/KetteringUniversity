#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

#define BUFFER_SIZE 20

void error(char *, char *);

int main(int ac, char* av[]) {
	
	int fd_rd1, fd_wrt, fd_rd2, n_chars;
	char buf[BUFFER_SIZE];
	char testStr[] = "testing 1 2 3...";

	if ((fd_rd1 = open(av[1], O_RDONLY)) == -1)
		error("Cannot open: ", av[1]);
	if ((fd_wrt = open(av[1], O_WRONLY)) == -1)
		error("Cannot open: ", av[1]);
	if ((fd_rd2 = open(av[1], O_RDONLY)) == -1)
		error("Cannot open: ", av[1]);

	while ((n_chars = read(fd_rd1, &buf, BUFFER_SIZE)) == BUFFER_SIZE)
		printf("%s\n", buf);

	if (write(fd_wrt, &testStr, BUFFER_SIZE) < 0)
		error("Error writing file: ", av[1]);
	
	while ((n_chars = read(fd_rd2, &buf, BUFFER_SIZE)) == BUFFER_SIZE)
		printf("%s\n", buf);

	if (close(fd_rd1) == -1 || close(fd_wrt) == -1 || close(fd_rd2) == -1)
		error("Error closing files", av[1]);
}

void error (char *str1, char *str2) {
	fprintf(stderr, "Error: %s", str1);
	perror(str2);
	exit(1);
}
		
