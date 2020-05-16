@echo off

:loop
echo Hello world >> hellofile.txt
ping -n 3 127.0.0.1>NUL
goto loop