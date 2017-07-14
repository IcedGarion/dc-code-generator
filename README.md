# README #

**Uniupo university, Science & Technologic Innovation dept., Language&Translators Laboratory Project**

This repository is about a simple application translating code written in AC (a basic "programming" language) into code readable (and executable) by UNIX DC calculator. (try typing "dc" in your linux bash!)

The input AC program is stored in "input.txt" file.

AC instructions are like the followings:

f a

i b

b = 10

a = b + 3.2

p a

the corresponding DC instruction generated is:

10 sb lb 3.2 + sa la p

This single-line instruction can then be pasted in dc and executed