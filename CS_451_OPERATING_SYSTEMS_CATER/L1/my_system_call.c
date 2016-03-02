#include <sys/syscall.h>
#include <unistd.h>
#include <stdio.h>

#define __NR_helloworld		333

int helloworld() {
	return (int) syscall(__NR_helloworld);
}

int main(void) {
	printf("The return code from the helloworld system call is %d\n", helloworld());
}
