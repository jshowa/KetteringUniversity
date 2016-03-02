#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc-3.exe
CCC=g++-3.exe
CXX=g++-3.exe
FC=g77-3.exe

# Macros
PLATFORM=Cygwin-Windows

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/NewConfiguration-2/${PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/sttyl.o

# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	${MAKE}  -f nbproject/Makefile-NewConfiguration-2.mk dist/NewConfiguration-2/${PLATFORM}/customstty.exe

dist/NewConfiguration-2/${PLATFORM}/customstty.exe: ${OBJECTFILES}
	${MKDIR} -p dist/NewConfiguration-2/${PLATFORM}
	${LINK.c} -o dist/NewConfiguration-2/${PLATFORM}/customstty ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/sttyl.o: sttyl.c 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/sttyl.o sttyl.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/NewConfiguration-2
	${RM} dist/NewConfiguration-2/${PLATFORM}/customstty.exe

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
