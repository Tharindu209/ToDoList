# To-Do List Application
This Task Manager app helps users efficiently create, update, and manage their daily tasks.
Designed in Kotlin, using an MVVM architecture with Room database integration.

## Features
* CRUD Functionality
* Add Task / Edit Task
* Optional Set Due Time
* Check / Uncheck as required
* Swipe-to-delete + Undo Delete
* Local storage using ROOM

## Development Progress
The application was developed in structured increments to ensure a smooth and organized implementation.
1. Part 1: UI Design and Task View Setup
* Designed XML layouts for the main screen and task details.
* Created initial views to display a single task.
* Implemented UI components such as buttons, input fields, and task display.

2. Part 2: ViewModel, RecyclerView & CRUD Operations
* Set up the basic ViewModel for task management.
* Designed and implemented a RecyclerView with CardView for task lists.
* Added CRUD (Create, Read, Update, Delete) functionality for tasks.
* Implemented Swipe-to-Delete and Undo-delete features for task removal.

3. Part 3: Room Database Integration
* Set up Data Access Object (DAO) for managing database operations.
* Integrated Room database for persistent task storage.
* Established data sources to interact with the repository and ViewModel.
