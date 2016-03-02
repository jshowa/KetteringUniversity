/* 
 * File:   sttyl.c
 * Author: Jacob S. Howarth
 * Class: CS202, Systems Programming Concepts
 * Assignment No.: 4
 *
 * Description: Basic terminal settings controller to control
 *              the driver settings of the current terminal.
 *              A limited version of the UNIX stty command.
 *
 * Created on May 16, 2009, 9:23 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/termios.h>
#include <strings.h>


/****************STRUCTURES******************
 * flg_info - global structure containing
 *            the flag mask, and the string
 *            command line argument assoc.
 *            with that mask.
 ********************************************
 */
struct flg_info {
    int flg;
    char *flg_name;
};

/***************BAUD RATE FLAGS******************
 * flg_info b_rate_tab[] - A table representing
 *                         the baud rate masks
 *                         associated with the
 *                         input and output speed
 *                         of the terminal driver
 ************************************************
 */
const struct flg_info b_rate_tab[] = {
    {B50, "speed 50 baud;"},
    {B75, "speed 75 baud;"},
    {B110, "speed 110 baud;"},
    {B134, "speed 134 baud;"},
    {B150, "speed 150 baud;"},
    {B200, "speed 200 baud;"},
    {B300, "speed 300 baud;"},
    {B600, "speed 600 baud;"},
    {B1200, "speed 1200 baud;"},
    {B1800, "speed 1800 baud;"},
    {B2400, "speed 2400 baud;"},
    {B4800, "speed 4800 baud;"},
    {B9600, "speed 9600 baud;"},
    {B19200, "speed 19200 baud;"},
    {B38400, "speed 38400 baud;"},
    {B57600, "speed 57600 baud;"},
    {B115200, "speed 115200 baud;"},
    {B230400, "speed 230400 baud;"},
    {B460800, "speed 460800 baud;"},
    {B0, "speed 0 baud;"}
};

/***************TTY DRIVER INPUT FLAGS******************
 * flg_info in_flgs_tab[] - A table representing
 *                         the input flag masks.
 *                         Since ICRNL mask is the
 *                         only mask required to check
 *                         for the lab, it is the
 *                         only one in the table.
 *                         More masks can be checked
 *                         by just adding to the table.
 *****************************************************
 */
const struct flg_info in_flgs_tab[] = {
    {ICRNL, "icrnl"}, // input carriage return to newline conversion mask
    {0, NULL}
};

/***************NEGATED TTY DRIVER INPUT FLAGS*******************
 * flg_info neg_iflgs_tab[] - A table representing
 *                         the negated input flag masks.
 *                         Since ICRNL mask is the
 *                         only mask required to check
 *                         for the lab, it is the
 *                         only one in the table.
 *                         More masks can be checked
 *                         by just adding to the table.
 *************************************************************
 */
const struct flg_info neg_iflgs_tab[] = {
    {~ICRNL, "-icrnl"}, // negation flag of icrnl
    {0, NULL}
};

/***************TTY DRIVER OUTPUT FLAGS******************
 * flg_info out_flgs_tab[] - A table representing
 *                         the output flag masks.
 *                         Since OLCUC, ONLCR, and TAB3 masks
 *                         are the only mask required to check
 *                         for the lab, they are the
 *                         only ones in the table.
 *                         More masks can be checked
 *                         by just adding to the table.
 *****************************************************
 */
const struct flg_info out_flgs_tab[] = {
    {OLCUC, "olcuc"}, // output conversion mask to convert lower to upper case chars
    {ONLCR, "onlcr"}, // output new line to carriage return conversion mask
    {TAB3, "tabs"}, // mask to expand tabs to spaces on output
    {0, NULL}
};

/***************NEGATED TTY DRIVER OUTPUT FLAGS**************
 * flg_info neg_oflgs_tab[] - A table representing
 *                         the negated output flag masks.
 *                         Since OLCUC, ONLCR, TAB3 masks are the
 *                         only masks required to check
 *                         for the lab, they are the
 *                         only ones in the table.
 *                         More masks can be checked
 *                         by just adding to the table.
 *****************************************************
 */
const struct flg_info neg_oflgs_tab[] = {
    {~OLCUC, "-olcuc"}, // negated mask of olcuc
    {~ONLCR, "-onlcr"}, // negated mask of onlcr
    {~TAB3, "-tabs"}, // negated mask of tab3 which is equivalent to tabs
    {0, NULL}
};

/***************TTY DRIVER CONTROL FLAGS******************
 * flg_info con_flgs_tab[] - A table representing
 *                         the control flag masks.
 *                         These flags represent how
 *                         characters are represented
 *                         and how they are transmitted
 *                         to the terminal and other
 *                         devices.
 *****************************************************
 */
const struct flg_info con_flg_tab[] = {
    {CREAD, "cread"}, // enable/disable the receiver Rx signal
    {PARENB, "parenb"}, // enable/disable parity bit detection
    {PARODD, "parodd"}, // select odd/even parity
    {HUPCL, "hupcl"}, // assert/deassert modem control
    {0, NULL}
};

/***************TTY DRIVER LOCAL FLAGS******************
 * flg_info l_flgs_tab[] - A table representing
 *                         the local flag masks.
 *                         Since ECHO, ECHOE, ICANON, and ISIG masks
 *                         are the only mask srequired to check
 *                         for the lab, they are the
 *                         only ones in the table.
 *                         More masks can be checked
 *                         by just adding to the table.
 *****************************************************
 */
const struct flg_info l_flgs_tab[] = {
    {ECHO, "echo"}, // display characters typed
    {ECHOE, "echoe"}, // ERASE char visually erases last char in the current line
    {ICANON, "icanon"}, // Canonical input (ERASE and KILL processing)
    {ISIG, "isig"}, // check characters against control chars INTR, QUIT, SUSP
    {0, NULL}
};

/***************OUTPUT CONTROL FLAGS******************
 * flg_info neg_lflgs_tab[] - A table representing
 *                         the negated local flag masks.
 *                         Since ECHO, ECHOE, ICANON and ISIG masks
 *                         are the only mask required to check
 *                         for the lab, they are the
 *                         only ones in the table.
 *                         More masks can be checked
 *                         by just adding to the table.
 *****************************************************
 */
const struct flg_info neg_lflgs_tab[] = {
    {~ECHO, "-echo"}, // do NOT display characters typed
    {~ECHOE, "-echoe"}, // ERASE char does NOT visually erase last char in current line
    {~ICANON, "-icanon"}, // Non-canonical input
    {~ISIG, "-isig"}, // Do NOT check chars against special control chars INTR, SUSP, QUIT
    {0, NULL}
};

//******FUNCTION PROTOTYPES*******
void print_stty(struct termios *);
void show_baud_con(speed_t, tcflag_t);
void show_cc(cc_t cur_cc[]);
void show_flgs(tcflag_t iflags, tcflag_t oflags, tcflag_t lflags);
void process_args(struct termios *, char *args[], int);
int set_erase_kill(struct termios *, char *, char *);
int set_flgs(struct termios *, char *);

/* Function: main
 *
 * Description: Execution point of stty1, terminal settings controller
 *              command line program.
 *
 * Parameters:
 * @param int argc - command line argument count
 * @param char* argv[] - array of command line argument strings
 *
 * Returns:
 * @return int - 0 for success, -1 for failure
 */
int main(int argc, char *argv[]) {

    //Step 1: Create termios structure and poll attributes of
    //        current tty device driver.
    struct termios tty_info;

    if (tcgetattr(0, &tty_info) == -1) { // check if terminal settings were read without error
        perror("Cannot get terminal settings from stdin");
        exit(1);
    }

    //Step 2: If poll is successful, perform following
    //        actions based on command arguments
    if (argc == 1)
        print_stty(&tty_info); // one arg, print terminal driver settings for current, active terminal
    if (argc > 1)
        process_args(&tty_info, argv, argc); // args, change terminal driver settings
    return (EXIT_SUCCESS);
}

/* Function: print_stty
 *
 * Description: Prints the driver settings of the current, active terminal
 *
 * Parameters:
 * @param struct termios *tty_ptr - A termios pointer containing the current
 *                                  terminal settings
 *
 * Returns:
 * @return void
 */
void print_stty(struct termios *tty_ptr) {
    show_baud_con(cfgetospeed(tty_ptr), tty_ptr->c_cflag); // print terminal out baud rate
    show_cc(tty_ptr->c_cc); // print control chars erase, kill, and intrupt
    show_flgs(tty_ptr->c_iflag, tty_ptr->c_oflag, tty_ptr->c_lflag); // show input, output and local flags
};

/* Function: process_args
 *
 * Description: Processes each command line argument string given to stty1
 *
 * Parameters:
 * @param struct termios *tty_ptr - termios pointer containing the driver settings
 *                                  of the current, active terminal
 * @param char *args[] - an array of argument strings
 * @param int arg_count- integer representing the number of command line arguments
 *
 * Returns:
 * @return void
 */
void process_args(struct termios *tty_ptr, char *args[], int arg_count) {
    //Step 1: Create for loop and process all args
    int i = 1, proc_cc_arg_stat = 0, proc_flgs_arg_stat = 0;

    //Step 2: Process command arguments
    while(i < arg_count && (proc_cc_arg_stat != -1) && (proc_flgs_arg_stat != -1)) {
        if (strcmp(args[i], "erase") == 0) {
            proc_cc_arg_stat = set_erase_kill(tty_ptr, args[i + 1], args[i]);
            if (proc_cc_arg_stat == -1) exit(1);
            i += 2; // increment twice for erase and kill because the argument
                    // following the "erase" and "kill" arg will be the char to
                    // change erase and kill too.
        }
        else if (strcmp(args[i], "kill") == 0) {
            proc_cc_arg_stat = set_erase_kill(tty_ptr, args[i + 1], args[i]);
            if (proc_cc_arg_stat == -1) exit(1);
            i += 2;
        }
        else {
            proc_flgs_arg_stat = set_flgs(tty_ptr, args[i]);
            if (proc_flgs_arg_stat == -1) exit(1);
            i++;
        }
    }

    //print_stty(tty_ptr); // Debugging print statement

    // Check for error in previous functions and update current, active terminal settings
    if (proc_cc_arg_stat != -1 && proc_flgs_arg_stat != -1) {
        if (tcsetattr(0, TCSANOW, tty_ptr) == -1) {
            perror("New terminal settings failed");
            exit(1);
        }
    }
    else
        exit(1);


};

/* Function: set_flgs
 *
 * Description: Turn on/off the current, active terminal input, output, and local
 *              flags given a specific command line argument for command stty1
 *
 * Parameters:
 * @param struct termios *tty_ptr - termios pointer containing the driver settings
 *                                  of the current, active terminal
 * @param char *arg - an argument string
 *
 * Returns:
 * @return int - 0 for sucess, -1 for failure
 */
int set_flgs(struct termios *tty_ptr, char *arg) {
    int i, j, n; // for loop iterators to iterate thru flag tables

    if (arg == NULL) { // if there are no arguments, print invalid argument
        fprintf(stderr, "Invalid argument %s\n", arg);
        return -1;
    }

    // check input flag table against command line argument and if it
    // matches a string in the input flag table or the negated table
    // set or clear the appropriate bit.
    for(i = 0; in_flgs_tab[i].flg || in_flgs_tab[i].flg_name != NULL; i++) {
        if (strcmp(in_flgs_tab[i].flg_name, arg) == 0) {
            tty_ptr->c_iflag |= in_flgs_tab[i].flg;
            return 0;
        }
        if (strcmp(neg_iflgs_tab[i].flg_name, arg) == 0) {
            tty_ptr->c_iflag &= neg_iflgs_tab[i].flg;
            return 0;
        }
    }

     // check output flag table against command line argument and if it
    // matches a string in the output flag table or the negated table
    // set or clear the appropriate bit.
    for(j = 0; out_flgs_tab[j].flg || out_flgs_tab[j].flg_name != NULL; j++) {
        if (strcmp(out_flgs_tab[j].flg_name, arg) == 0) {
            tty_ptr->c_oflag |= out_flgs_tab[j].flg;
            return 0;
        }
        if (strcmp(neg_oflgs_tab[j].flg_name, arg) == 0) {
            tty_ptr->c_oflag &= neg_oflgs_tab[j].flg;
            return 0;
        }
    }

     // check local flag table against command line argument and if it
    // matches a string in the local flag table or the negated table
    // set or clear the appropriate bit.
    for(n = 0; l_flgs_tab[n].flg || l_flgs_tab[n].flg_name != NULL; n++) {
        if (strcmp(l_flgs_tab[n].flg_name, arg) == 0) {
            tty_ptr->c_lflag |= l_flgs_tab[n].flg;
            return 0;
        }
        if (strcmp(neg_lflgs_tab[n].flg_name, arg) == 0) {
            tty_ptr->c_lflag &= neg_lflgs_tab[n].flg;
            return 0;
        }
    }


    fprintf(stderr, "Invalid argument %s\n", arg);
    return -1; // most likely this statement won't be reached, but if so
                // assume error

};

/* Function: set_flgs
 *
 * Description: Set the "erase" and "kill" control chars for the current, active terminal
 *              given a specific command line argument for command stty1
 *
 * Parameters:
 * @param struct termios *tty_ptr- termios pointer containing the driver settings
 *                                 of the current, active terminal
 * @param char *arg - argument for the command string
 * @param char *command - command string, acceptable values are "erase" or "kill"
 *
 * Returns:
 * @return int - 0 for sucess, -1 for failure
 */
int set_erase_kill(struct termios *tty_ptr, char *arg, char *command) {
    if (arg == NULL) { // if no arg, throw error
        fprintf(stderr, "Missing argument to %s\n", command);
        return -1;
    }

    if (strlen(arg) > 2) { // if there is more than one arg for erase or kill, throw error
        fprintf(stderr, "Invalid argument \"%s\" for %s command\n", arg, command);
        return -1;
    }

    // if the command string is erase, and the arg is 1 character, set the
    // erase control character to that character
    if (strlen(arg) == 1 && strcmp(command, "erase") == 0) {
        tty_ptr->c_cc[VERASE] = *arg;
        return 0;
    }

    // if the command string is kill, and the arg is 1 character, set the
    // erase control character to that character
    if (strlen(arg) == 1 && strcmp(command, "kill") == 0) {
        tty_ptr->c_cc[VKILL] = *arg;
        return 0;
    }

    // if the arg is a "^" followed by a char, then set erase or kill
    // to the control character value.
    if (strlen(arg) == 2 && *arg == '^' && strcmp(command, "erase") == 0) {
        tty_ptr->c_cc[VERASE] = CTRL(*++arg);
        return 0;
    }
    else if (strlen(arg) == 2 && *arg == '^' && strcmp(command, "kill") == 0) {
        tty_ptr->c_cc[VKILL] = CTRL(*++arg);
        return 0;
    }
    else
        return -1; // error occurred

    return -1; // most likely this statement will never be reached.

}

/* Function: show_baud_con
 *
 * Description: Prints the current, active terminal baud rate and some control flags
 *
 * Parameters:
 * @param struct speed_t curr_baud - current terminal output baud rate
 * @param struct tcflag_t con_flgs - current terminal control flag status
 *
 * Returns:
 * @return void
 */
void show_baud_con(speed_t curr_baud, tcflag_t con_flgs) {
    int i, j;// for loop iterators to iterate thru baud table

    for (i = 0;  b_rate_tab[i].flg; i++)
        if (curr_baud == b_rate_tab[i].flg)
            printf("%s", b_rate_tab[i].flg_name);

    for (j = 0; con_flg_tab[j].flg; j++) {
        if (con_flgs & con_flg_tab[j].flg)
            printf(" %s ", con_flg_tab[j].flg_name);
        else {
            printf(" -%s ", con_flg_tab[j].flg_name);
        }
    }
    
    printf("\n");
};

/* Function: show_cc
 *
 * Description: Prints the the current active terminals erase, kill, and intr
 *              characters
 *
 * Parameters:
 * @param cc_t curr_cc[] - The control characters array for the current, active terminal
 *
 * Returns:
 * @return void
 */
void show_cc(cc_t curr_cc[]) { 

    // if decimal ascii value is less than 32, then the char for erase, kill,
    // or intr is a control character, so print with ^ simple and uppercase
    if ((unsigned int)curr_cc[VINTR] < 32 || (unsigned int)curr_cc[VINTR] == 127)
        printf("intr = ^%c; ", curr_cc[VINTR] - 1 + 'A');
    else // the char kill, intr, and erase is NOT a control char so print normally
        printf("intr = %c; ", curr_cc[VINTR]);
    if ((unsigned int)curr_cc[VERASE] < 32 || (unsigned int)curr_cc[VERASE] == 127)
        printf("erase = ^%c; ", curr_cc[VERASE] - 1 + 'A');
    else
        printf("erase = %c; ", curr_cc[VERASE]);
    if ((unsigned int)curr_cc[VKILL] < 32 || (unsigned int)curr_cc[VKILL] == 127)
        printf("kill = ^%c; ", curr_cc[VKILL] - 1 + 'A');
    else
        printf("kill = %c; ", curr_cc[VKILL]);

    printf("\n");
}

/* Function: show_flgs
 *
 * Description: Prints the current active terminal input, output, and local flags
 *
 * Parameters:
 * @param struct tcflag_t iflags - current terminal input flags/modes
 * @param struct tcflag_t oflags - current terminal output flags/modes
 * @param struct tcflag_t lflags - current terminal local flags/modes
 *
 * Returns:
 * @return void
 */
void show_flgs(tcflag_t iflags, tcflag_t oflags, tcflag_t lflags) {
    int i, j, n; // for loop iterators to iterate thru flag tables

    // print terminal driver input flags
    for(i = 0; in_flgs_tab[i].flg || in_flgs_tab[i].flg_name != NULL; i++) {
        if (iflags & in_flgs_tab[i].flg)
            printf(" %s ", in_flgs_tab[i].flg_name);
        else {
            printf(" -%s ", in_flgs_tab[i].flg_name);
        } 
    }
    
    // print terminal driver output flags
    for(j = 0; out_flgs_tab[j].flg || out_flgs_tab[j].flg_name != NULL; j++) {
        if (oflags & out_flgs_tab[j].flg)
            printf(" %s ", out_flgs_tab[j].flg_name);
        else {
            printf(" -%s ", out_flgs_tab[j].flg_name);
        } 
    }
    
    // print terminal driver local flags
    for(n = 0; l_flgs_tab[n].flg || l_flgs_tab[n].flg_name != NULL; n++) {
        if (lflags & l_flgs_tab[n].flg)
            printf(" %s ", l_flgs_tab[n].flg_name);
        else {
            printf(" -%s ", l_flgs_tab[n].flg_name);
        } 
    }

    printf("\n");
}



