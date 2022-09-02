# asteroids
Java implementation of the classic 1979 Atari game Asteroids, built using JavaFX.

---

### Running Example

<div align="center"><img src="https://media.giphy.com/media/4lOhPhYJxk2gIjdXFk/giphy.gif" alt="JavaFX Asteroids demo" /></div>

---

### Summary

The game begins immediately after running the program, with no menus or difficulties currently implemented. The ship spawns in the middle of the game window with 5 starting asteroids. Additional asteroids spawn throughout the lifetime of the game. The player controls the ship, firing projectiles at the asteroids to destroy them. If the ship comes in contact with an asteroid, the game freezes, effectively ending the game. 1000 points are allocated for each asteroid destroyed (however, the point system is currently arbitrary, with no way to end the game other than crashing into an asteroid. High scores are currently not saved).

---

### Controls

The player uses the arrow keys to control the ship by turning it and allowing it to accelerate. The ship can accelerate faster by keeping the up arrow key depressed. This is not recommended, given the high volume of asteroids in the ship's vicinity. To slow the ship, the ship will need to be turned in the opposite direction of it's velocity, and accelerated in that opposite direction. This does a great job of mimicking a ship thrusting through space. To fire projectiles, the space bar is used. The space bar can be held down to continuously fire projectiles, and is the recommended way to use the ship's weapon system for best results, due to the current implementation. If a projectile makes contact with an asteroid, the asteroid is destroyed, removed from the game map entirely.

---

### Implementation Notes

The program bases the ```Ship```, ```Asteroid```, and ```Projectile``` classes off an abstract subclass, the ```Character``` class. This class is used to determine the shape of the given entity and how it moves in the game window (map), along with retrieving data on its current position on the map.

The JavaFX library was primarily used because it was a library I was already using and learning about for other projects, including university course projects while working towards my bachelors degree. There may be more suitable libraries out there for this kind of program, but JavaFX was what I used because it was what I was working with heavily at the time.

---

### Continuous Improvements

Although I do not have plans to work on this particular project in favor of working on other projects, future improvements could include:
- improving the ```ProjectileTimer```, improving how the projectiles are fired from the ship, along with their rate of fire.
- running the program brings the user to a menu of sorts, so the game does not start immediately.
- Implementing a way to pause the game.
- saving high scores (a text file could suffice for this).
- allowing the user to restart the game after crashing into an asteroid, so the program does not need to be restarted each time the user ends the game.
- implementing different sizes of asteroids. Larger asteroids would break into smaller asteroids as the user breaks down the larger asteroids with the ship's projectiles. Destroying smaller asteroids could net the user more points, as they are harder to destroy because they are smaller targets.
- difficulties.