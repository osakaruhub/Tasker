# Tasker
Tasker is a small application for managing events.
The task (no pun intended), is to have a no dependancy, no bullshit task manager, whether you're using a GUI or creep behind a terminal.
This application come with the Server itself, and a graphical interface (and tui), and therefore need a local server Already

Roadmap:
    - [] list events
    - [] add events
    - [] create a timetable
    - [?] add a GUI
    - [] add tests for faster debugging

# How to run
The prebuilt binaries include both the Server side and the Client side using according jar classes in /bin or the release page.
TO build the package yourself is as easy as to clone the repo and run `` gradlew build ``. no dependencies needed. 

# contribution
contribution is always helpful, whether you're just catching bugs or adding to the code.

# who we are
We are a small voluntary group of students that try to make software more free. Our projects are all FOSS-compliant, and never are locked behind a paywall.
-- Oskar Stüwe, Felix Walter, Lukas Walter

For the group:
Our Build software is Gradle, and our project lies in 'app'. Therein lies a build config file and the source code for our project. Please also put diagrams (doesnt just have to be PlantUML, pictures are also ok) for our project to visualise the implementation.
what we are still missing:
Connection between Handler and GUI
Database
Login (seems far-fetched)
extra UI (Terminal, commandline)
