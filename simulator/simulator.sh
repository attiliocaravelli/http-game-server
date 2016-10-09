#!/bin/sh
now=$(date +%s%N)
echo "Start simulation: $now"
for j in $(seq 1 $1);
do
	now=$(date +%s%N)
        echo "Start login request 11: $now"
	sessionkey11="`wget -qO- http://localhost:8080/11/login`"
	now=$(date +%s%N)
        echo "Finish login request 11: $now $sessionkey11" 
        now=$(date +%s%N)
        echo "Start login request 2: $now"
	sessionkey2="`wget -qO- http://localhost:8080/2/login`"
	now=$(date +%s%N)
        echo "Finish login request 2: $now $sessionkey2"
        now=$(date +%s%N)
        echo "Start login request 2 : $now"
	sessionkey3="`wget -qO- http://localhost:8080/2/login`"
	now=$(date +%s%N)
        echo "Finish login request 2: $now $sessionkey3"
        now=$(date +%s%N)
        echo "Start login request 2: $now"
	sessionkey22="`wget -qO- http://localhost:8080/2/login`"
	now=$(date +%s%N)
        echo "Finish login request second time 2 user: $now $sessionkey22 should be equal $sessionkey2"

	for i in $(seq 1 15);
        do
                curl --data "51" http://localhost:8080/1/score?sessionkey=$sessionkey2
                curl --data "35" http://localhost:8080/2/score?sessionkey=$sessionkey3
                curl --data "$j" http://localhost:8080/2/score?sessionkey=$sessionkey2
		curl --data "26" http://localhost:8080/2/score?sessionkey=$sessionkey11
                echo "score valid" 
		curl --data "26A" http://localhost:8080/7/score?sessionkey=$sessionkey22
		echo "\nscore malformed"
                curl --data "61" http://localhost:8080/2/score?sessionkey=$sessionkey2
	        curl --data "47" http://localhost:8080/2/score?sessionkey=$sessionkey3
        done
	now=$(date +%s%N)
        echo "Start highscore request lev 24: $now"
	highScore24="`wget -qO- http://localhost:8080/24/highscorelist`"
	now=$(date +%s%N)
	echo "Finish highscore request lev 24: $now"
	now=$(date +%s%N)
	echo "Start highscore request lev 2: $now"
	highScore2="`wget -qO- http://localhost:8080/2/highscorelist`"
   	now=$(date +%s%N)
        echo "Finish highscore request lev 2: $now"
        now=$(date +%s%N)
        echo "Start highscore request lev 7: $now"
	highScore7="`wget -qO- http://localhost:8080/7/highscorelist`"
	now=$(date +%s%N)
        echo "Finish highscore request lev 7: $now"
	now=$(date +%s%N)
        echo "Start highscore request lev 1: $now"
        highScore1="`wget -qO- http://localhost:8080/1/highscorelist`"
        now=$(date +%s%N)
        echo "Finish highscore request lev 1: $now"
	echo HighScore for Level 24 $highScore24
        echo HighScore for Level 2 $highScore2
	echo HighScore for Level 7 $highScore7
	echo HighScore for Level 1 $highScore1
done
now=$(date +%s%N)
echo "Finish simulation: $now"
