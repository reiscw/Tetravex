# Tetravex
An implementation of the Tetravex game in Java

On most systems (Linux/MacOS) giving executable permissions to buildTetravex and executing it will create a JAR file. On Windows, buildTetravex can be modified to a .BAT file for the same purpose.

This game produces a 3x3, 4x4, or 5x5 Tetravex puzzle. It should be noted that if there are multiple correct solutions, the game may not report success, because it checks to see if the user's solved puzzle has all tiles in the same configuration as the original solved puzzle. The checkSolution() method of the Tetravex puzzle class can be rewritten to resolve this, and probably will be in a future version.
