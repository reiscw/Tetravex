# Tetravex
An implementation of the Tetravex game in Java

This program was created for two purposes. First, I enjoyed playing Tetravex in Linux (the gnome-tetravex game provided by most distributions) and was disappointed that no comparable applications were available for Windows or macOS.  

Second, I have used this program as a lab in my AP Computer Science A course to teach why inheritance would be used in a real world application (how a TetravexButton is-a JToggleButton, for example). Experienced developers will note many bizarre design choices, primarily with how tiles are "moved" from one side of the board to another. These were done so that a high school student (or college student in Introduction to Object-Oriented Programming) could understand the code for the entire project, with a minimum of "black boxes."  That is why the code is divided into two folders, one implemented using inheritance, and one implemented without inheritance.  Both folders are complete versions of the game, and should be compiled/run separately.

This game produces a 4x4 puzzle, which for teaching purposes, gives enough of a challenge without being as formidable as 5x5 and 6x6 puzzles. It could probably be adapted to run other puzzle sizes, but a redesign of the GUI code will probably be necessary.
