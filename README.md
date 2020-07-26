# Cellular-Automaton-Viewer

See python branch for CAViewer v1.

Installation
============
CAViewer v2 is written in Java.<br>
You will need the Java Runtime Environment / Java Development Kit to run the *.jar file.<br>
If you use Windows, you can use the *.exe file that doesn't need Java.<br>

Compiling from source
=====================
CAViewer v2 uses JavaFX. You will need to download JavaFX from https://gluonhq.com/products/javafx/. <br>
Then, compile to a *.jar using FakeMain.java.

For more detailed steps, look for a tutorial on how to compile JavaFX programs, 
such as this [one](https://medium.com/@vinayprabhu19/creating-executable-javafx-application-part-2-c98cfa65801e). <br>

What is this?
=============
CAViewer is a cellular automaton simulation program written in Java.

The GUI
=======
The GUI is made with JavaFX.

The Menu Bar
-----------
**File Menu**: <br>
*New Rule* - Opens the rule dialog to make a new rule <br>
*Close* - Closes the application <br> 

**Edit Menu**: <br>
*Delete* - Doesn't do anything yet <br>

**Search**: <br>
*Generate Apgtable* - Generates an apgtable to be used with [apgsearch](https://gitlab.com/apgoucher/apgmera) <br>
*Run Rule Search* - Starts a search program that randomly enumerates rules to find spaceships & oscillators <br>

**Help**:<br>
*About* - Doesn't do anything yet <br>

The Tools Bar
-------------
*Run Simulation Button* - Runs the simulation <br>
*Drawing Button* - Go into drawing mode <br>
*Panning Button* - Go into panning mode <br>
*Selection Button* - Go into selection mode <br>
<br>
*Random Soup Button* - Generates a random soup <br>
*Flip Button* - Flips the pattern horizontally / vertically <br>
*Rotate Button* - Rotates the pattern clockwise / counter-clockwise (doesn't work) <br>
*Recording Button* - Records all patterns that move in the selection area and saves it as a *.gif <br>
*Identify Button* - Identifies the pattern as a spaceship / oscillator <br>

The Status Bar
--------------
The generation of the pattern is shown.

Keyboard Shortcuts
------------------
<kbd>Space</kbd> - Step 1 Generation <br>
<kbd>Enter</kbd> - Toggle Simulation <br>
<kbd>Delete</kbd> - Deletes cells <br>
<kbd>Ctrl</kbd> + <kbd>C</kbd> - Copy Pattern to RLE (without header) <br>
<kbd>Ctrl</kbd> + <kbd>V</kbd> - Paste Pattern (requires that an area is already selected) <br>

GUI TODO List
-------------
- [x] Add tooltips
- [ ] Add button to view search results and save to a file
- [ ] Add menu buttons for delete, paste, copy and cut cells
- [ ] Add menu button for new pattern
- [ ] Add menu button to open rules & patterns
- [ ] Add dialog to ask "Would you like to save changes to untitled"
- [ ] Add more information to the status bar
- [ ] Add a way to view output from search programs
- [ ] Add help information to the about button
- [ ] Custom key binds

Editing Features
================
- [x] Drawing Cells
- [x] Selecting Cells
- [x] Delete Cells
- [x] Copy Cells to RLE (without header)
- [x] Flip Horizontally / Vertically
- [ ] Rotate Clockwise / Counter-Clockwise
- [ ] Nudging up / down / left / right
- [ ] Pasting Cells from RLE
- [ ] Invert Cells
- [ ] Undo & Redo
- [ ] Select All
- [ ] Reset Pattern to Generation 0
- [ ] More Random Soup Symmetries
- [ ] Simulate in selection
- [ ] Simulate outside selection

Supported / Planned Rulespaces
==============================
- [x] 2 State Higher Range Outer Totalistic (HROT)
- [ ] Integer HROT
- [ ] HROT BSFKL
- [ ] HROT Generations
- [ ] HROT Extended Generations
- [ ] HROT Regenerating Generations
- [ ] 3-state HROT
- [ ] Deficient HROT
- [ ] Primodia
- [ ] 2 State Isotropic Non-Totalistic (INT)
- [ ] INT BSFKL
- [ ] INT Generations
- [ ] INT Extended Generations
- [ ] INT Regenerating Generations
- [ ] 3-state INT
- [ ] Deficient INT
- [ ] Naive Rules
- [ ] Alternating Rules
- [ ] Second Order Rules
- [ ] Multiple Neighbourhoods Cellular Automaton (MNCA)
- [ ] Cyclic CA
- [ ] Langton's Ant / Turnmites
- [ ] Block CA
- [ ] Margolous
- [ ] 1D CA
- [ ] Golly Ruletrees
- [ ] Golly Ruletables
- [ ] Square Cell Ruletables

Supported / Planned INT Neighbourhoods
======================================
- [ ] Range 1 Moore Isotropic Non-Totalistic
- [ ] Range 1 Hexagonal Isotropic Non-Totalistic
- [ ] Range 2 Von Neumann Isotropic Non-Totalistic
- [ ] Range 2 Checkerboard Isotropic Non-Totalistic
- [ ] Range 2 Far Corners Isotropic Non-Totalistic
- [ ] Range 2 Far Edges Isotropic Non-Totalistic
- [ ] Range 2 Knight Life Isotropic Non-Totalistic
- [ ] Range 2 Cross Isotropic Non-Totalistic
- [ ] Range 3 Cross Isotropic Non-Totalistic

Modifying the Application
=========================
Follow the instructions above and download Java & JavaFX.
If you want to modify the GUI of the main windows, you will also require 
[SceneBuilder](https://gluonhq.com/products/scene-builder/).

Modifying the GUI
-----------------
CAViewer uses the MVC or Model, View, Controller framework. <br>
Model contains the classes the run the simulation as well as search programs. <br>
Controller contains the event handling classes and the dialogs. <br>
View contains the resources needed to render the application such as the *.fxml files and icons. <br>

main.fxml contains the GUI of the main window. Open it in Scene Builder to modify it. <br>
Events from main.fxml are handled by MainController.java. See the comments in MainController.java for more details<br>

Adding custom rule families
---------------------------
All custom rules families will inherit from the abstract RuleFamily class in RuleFamily.java.

Documentation
=============
- [x] GUI
- [ ] Custom rule families
- [ ] Custom symmetries
- [ ] Custom neighbourhoods
- [ ] Custom search programs

Long-term TODO List
===================
- [ ] Custom search programs
- [ ] Add Unit Tests
- [ ] Bounded Grids
- [ ] Agar Search Program
- [ ] Catalyst Search Program
- [ ] Triangular Rules
- [ ] Hexagonal Rendering
- [ ] Implement a faster algorithm (QuickLife, HashLife...)
- [ ] Accept some LifeViewer commands like STEP & RANDOMISE
- [ ] Scripting in Python?

Known Bugs
==========
- [ ] RLEs are pasted rotated (fix is trivial, I just haven't done it)
- [ ] Cells are randomly not evaluated
- [ ] HROT B0 isn't working as intended
- [ ] ConcurrentModificationException is thrown (solution is to restart application if it happens until its fixed)
