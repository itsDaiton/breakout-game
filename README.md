# Breakout Game

JavaFX game based on popular retro arcade game [***Atari Breakout***](https://en.wikipedia.org/wiki/Breakout_(video_game)).

![image](https://github.com/itsDaiton/breakout-game/assets/72783924/d7f8f9a2-2e8e-4ce3-81aa-d016b23be1a9)

## Gameplay
The board of the game consists of 10x12 grid of bricks, 1 paddle and 1 ball. The goal of the game is to destroy every single brick in the grid. This can be achieved by bouncing the ball off of the paddle, walls, or other bricks upon destruction. The player is given a total of 3 lives. Upon losing all of the lives, the game ends. Lives are lost when the ball is not successfully bounced of the paddle and falls down beneath the game board. Player can control the paddle by moving their mouse. After successfully bouncing the ball with the paddle, the ball will then invert its speed on the Y axis and move towards the grid.

The main motivation in this game is score. Player is given an amount of score after destroying a brick. Bricks give different score based on the row they are placed in. In the event of either victory or defeat, the player can then restart the game by pressing corresponding key on their keyboard. If the game is still in play and the player wishes to take a break, they can do so, by pressing the pause key. See Controls section.§

## Controls
- **P** - pause the game
- **ENTER** - restart the game

## Built With
- Java 11
- JavaFX
- Maven

## Acknowledgments
Credits for audio used in the game: `resources/cz/daiton/music.mp3`.


***Empress of Light · Re-Logic***

***Terraria, Vol. 4 (Original Soundtrack)***

***Copyright Re-Logic, Inc. 2020***
