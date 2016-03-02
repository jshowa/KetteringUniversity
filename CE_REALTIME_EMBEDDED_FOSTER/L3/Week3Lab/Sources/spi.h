 #ifndef _spi_week_3
  #define _spi_week_3
  
  #include "derivative.h"
  void spi0_init(void);
  void spi_RAM_write(unsigned int address, char data);
  char spi_RAM_read(unsigned int address);
  void spi_RAM_write_string(unsigned int address, char* string);
  void spi_RAM_read_string(unsigned int address, char* string);
  
  /* options for spi_config */   
  #define SPI_BYTE_MODE       0x00  
  #define SPI_SEQUENTIAL_MODE 0x40
  #define SPI_PAGE_MODE       0x80
  #define SPI_ENABLE_HOLD     0x01
  
  /* instructions for writing and reading spi status register */
  #define SPI_WRSR_INST       0x01
  #define SPI_RDSR_INST       0x05
  
  /* instructions for writing and reading spi data */
  #define SPI_READ_INST       0x03
  #define SPI_WRITE_INST      0x02
  
  /* address union */
  typedef union 
  {
    unsigned int addr;
    char bytes[2];
  } ADDRESS;

#endif