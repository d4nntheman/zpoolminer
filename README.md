# zpoolminer
ZPoolMiner is a basic auto algo switching program for zpool.ca.
its pretty basic at this point but is working well at that level.

first if test1.txt is not present a benchmarking of all the algos is done.
then json data from zpool.ca/api/status aquired.
then the most profitable algorithim is mined.
every x seconds we check to see if its still the most profitable.
