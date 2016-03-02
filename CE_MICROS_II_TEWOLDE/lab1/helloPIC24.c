#include <p24fj128ga010.h>
main()
{
 	int delay;
 	TRISA = 0;
 	PORTA = 0x00;
 	while(1) {
	 	PORTA ++;
 		delay = 1000;
 		while(delay--);
 	}
}