# Java Desktop Text Editor 

A modular, multi-tabbed desktop text editor built entirely from scratch using Java Swing and AWT. 

Built using Java (JDK 21), Java Swing & AWT

## Features

* **Multi-Tabbed Workspace:** Open and edit multiple files simultaneously within a single window utilizing `JTabbedPane`.
* **Dynamic Dark Mode:** A custom-built, toggleable dark theme that iterates through all open tabs and updates the application's color palette at runtime to reduce eye strain.
* **Modular Architecture:** File reading and writing operations are completely decoupled from the UI into a dedicated `FileManager` class, ensuring clean separation of concerns.
* **Responsive Layout:** Engineered using `BorderLayout` to ensure the text areas and control panels scale flawlessly when maximizing the window or adjusting on different monitor sizes.
* **Font Customization:** Real-time font family selection and font-size adjustments using event listeners.

## How to Run

1. Clone the repository to your local machine:
   ```bash
   git clone [https://github.com/yourusername/texteditor-with-java.git](https://github.com/yourusername/texteditor-with-java.git)
   ```

2. Navigate to the project directory:
    ```bash
    cd texteditor-with-java
    ```

3. Compile the Java files:
    ```bash
    javac src/*.java
    ```

4. Run the application:

    ```bash
    java -cp src Main
    ```
