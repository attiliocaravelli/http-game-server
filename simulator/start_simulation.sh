#!/bin/sh
for j in $(seq 1 5);
do
    sh simulator.sh $j &
done

