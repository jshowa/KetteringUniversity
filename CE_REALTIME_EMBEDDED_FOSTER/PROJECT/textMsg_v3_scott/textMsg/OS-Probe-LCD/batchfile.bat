REM specify here all the commands you want to execute
REM Below is a sample command line how to launch the maker
REM and to process a make file
REM **** IMPORTANT: adapt INSTALLPATH below! *****

REM Adapt it to your installation path settings
SET INSTALLPATH=C:\Freescale\CodeWarrior

REM Now let's launch the maker utility with a make file. In order to see the maker utility
REM messages in the DOS shell as well, we are using the piper in front of it.
%INSTALLPATH%\prog\piper.exe  %INSTALLPATH%\prog\maker.exe -Prod="%INSTALLPATH%\(Codewarrior_Examples)\BatchFileRunner\project.ini" sample.mak

REM Wait so we can see the messages
REM uncomment the command blow, to see something
REM pause
