//
// Created by dmitr on 3/16/2019.
//

#ifndef QUEUE_UTIL_H
#define QUEUE_UTIL_H

#include <windows.h>

extern CRITICAL_SECTION cs_console;
extern HANDLE synchronize_semaphore;
extern volatile LONG thread_counter;
extern DWORD producersNumber;
extern DWORD consumersNumber;

DWORD WINAPI consumer(LPVOID lpParam);
DWORD WINAPI producer(LPVOID lpParam);

#endif //QUEUE_UTIL_H
