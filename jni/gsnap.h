#ifndef _GSNAP_H_
#define _GSNAP_H

#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <linux/fb.h>
#include <linux/kd.h>


#include "include/jpeglib.h"
//#include "include/png.h"

struct _FBInfo;
typedef struct _FBInfo FBInfo;
typedef int (*UnpackPixel)(FBInfo* fb, unsigned char* pixel, 
	unsigned char* r, unsigned char* g, unsigned char* b);

struct _FBInfo
{
	int fd;
	UnpackPixel unpack;
	unsigned char *bits;
	struct fb_fix_screeninfo fi;
	struct fb_var_screeninfo vi;
};

#define fb_width(fb)  ((fb)->vi.xres)
#define fb_height(fb) ((fb)->vi.yres)
#define fb_bpp(fb)    ((fb)->vi.bits_per_pixel>>3)
#define fb_size(fb)   ((fb)->vi.xres * (fb)->vi.yres * fb_bpp(fb))

int fb_unpack_rgb565(FBInfo*, unsigned char*,
	unsigned char*, unsigned char* , unsigned char*);
	
int fb_unpack_rgb24(FBInfo* fb, unsigned char* pixel,
	unsigned char* r, unsigned char* g, unsigned char* b);
	
int fb_unpack_argb32(FBInfo* fb, unsigned char* pixel,
unsigned char* r, unsigned char* g, unsigned char* b);

int fb_unpack_none(FBInfo* fb, unsigned char* pixel,
	unsigned char* r, unsigned char* g, unsigned char* b);
	
void set_pixel_unpacker(FBInfo* fb);

int fb_open(FBInfo* fb, const char* fbfilename);

void fb_close(FBInfo* fb);

int snap2jpg(const char * filename, int quality, FBInfo* fb);
#endif





