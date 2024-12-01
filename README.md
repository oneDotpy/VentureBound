# **VentureBound**

## Authors and Contributors
- Ahnaf Keenan Ardhito
- Alfizza Khalfani Kenaz
- Patuan Michael Christian Purba
- Songhui Lei

## **Overview**
VentureBound is an innovative platform designed to help individuals and groups plan their next vacation with ease and efficiency. By leveraging cutting-edge technologies, VentureBound simplifies the process of choosing destinations, gathering preferences, and facilitating group collaboration.

### **Key Features**:
- **Personalized Recommendations**: Collect user preferences and provide tailored travel destination suggestions powered by the GPT-3.5 Turbo API.
- **Interactive Exploration**: Seamlessly integrates with Google Maps API to display suggested destinations with detailed information and interactive exploration options.
- **Group Collaboration**: Enables users to create group chats, making it easier to build consensus on travel plans and discuss preferences in real-time.
- **User-Friendly Prompts**: Offers guided questions to collect preferences and consolidate group opinions effectively.

### **Why VentureBound?**
- **Purpose**: VentureBound addresses the challenges of collaborative vacation planning by combining AI-driven insights with group communication tools.
- **Key Use Cases**:
    - **Individual Planning**: Users can explore personalized travel suggestions based on their interests and requirements.
    - **Group Decision-Making**: Facilitates smooth collaboration among group members, allowing them to share ideas, vote on preferences, and finalize plans together.
    - **AI Assistance**: The integrated VacationBot simplifies the decision-making process by asking targeted questions and suggesting destinations based on the group’s collective inputs.

VentureBound is the ultimate tool for transforming vacation planning into an effortless, enjoyable, and highly organized experience.

## **Table of Contents**
1. [Features](#features)
2. [Installation](#installation)
3. [Usage Guide](#usage-guide)
4. [Technologies Used](#technologies-used)
5. [License](#license)
6. [Feedback](#feedback)
7. [Contributing](#contributing)

## **Features**
### **1. User Account Management**
- **Description**: Allows users to create accounts, log in, and log out.
    - **Sign-Up**: Users can register by providing basic account details.
    - **Log-In**: Authenticated access to the platform using their credentials.
    - **Log-Out**: Seamlessly switch accounts or log out from the platform.
- **Purpose**: Enables secure user identification and personalized access.

---

### **2. Group Management**
#### **a. Create Group**
- **Description**: Users can create a new group chat for collaborative planning.
    - Example: Andi creates a group for planning a holiday with friends.
- **Purpose**: Serves as the foundational structure for group discussions.

#### **b. Join Group**
- **Description**: Allows users to join an existing group.
    - Example: Bob and Charlie join Andi's group chat for holiday planning.
- **Purpose**: Encourages collaboration by adding more members to the discussion.

#### **c. Leave Group**
- **Description**: Users can leave a group when needed.
    - Example: Charlie, unable to join the holiday, leaves the group.
- **Purpose**: Maintains flexibility for group members while preserving the group for remaining participants.

---

### **3. Messaging System**
- **Description**: Enables users to send and receive messages within group chats.
    - **Send Messages**: Messages are transmitted to a remote database.
    - **Receive Messages**: Group members can access messages sent by others in real-time.
    - Example: Bob and his friends discuss their vacation plans through chat.
- **Purpose**: Facilitates remote collaboration and planning.

---

### **4. VacationBot**
- **Description**: An AI-powered bot that assists users in planning vacations.
    - Activated with the command `/start` in a group chat.
    - Prompts members with questions about their preferences, such as destinations and activities.
    - Consolidates responses and provides personalized vacation recommendations.
    - Includes detailed suggestions with Google Maps links for convenience.
    - Example: Alex activates the bot in the group chat, and the bot suggests travel destinations based on group inputs.
- **Purpose**: Enhances planning efficiency by providing intelligent, tailored recommendations.

## **Installation**

### **1. Requirements**
- **Operating System**: Compatible with Windows, macOS, and Linux.
- **Software**:
    - [Java Development Kit (JDK) 17 or higher](https://www.oracle.com/java/technologies/javase-downloads.html)
    - [Maven](https://maven.apache.org/install.html) for project dependency management
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/) as the preferred IDE for development and execution

---

### **2. Installation Steps**
1. **Clone the Repository**
    - Open a terminal and run:
      ```bash
      git clone <repository_url>
      ```
    - Navigate to the project folder:
      ```bash
      cd <repository_folder>
      ```

2. **Open the Project in IntelliJ IDEA**
    - Launch IntelliJ IDEA and select **File > Open**.
    - Navigate to the project folder and open it.
    - IntelliJ will automatically detect the `pom.xml` file and import the Maven project.

3. **Set the JDK**
    - Go to **File > Project Structure > Project**.
    - Set the Project SDK to JDK 17 or higher.
    - Apply and save changes.

4. **Build the Project**
    - Open the Maven tool window in IntelliJ (**View > Tool Windows > Maven**).
    - Click on **Reload All Maven Projects** to ensure all dependencies are installed.

5. **Run the Application**
    - Locate `app/Main.java` in the project structure.
    - Right-click on `Main.java` and select **Run** to start the application.

---

### **3. Common Issues and Fixes**
- **Maven Dependencies Not Resolving**:
    - Open the Maven tool window and click **Reload All Maven Projects**.
    - Ensure you have a stable internet connection to download dependencies.

- **JDK Not Detected in IntelliJ**:
    - Verify that JDK 17 or higher is installed and added to IntelliJ (**File > Project Structure > SDKs**).

- **Build Errors**:
    - Ensure all required software versions match the specifications above.
    - Clear the Maven cache and rebuild the project:
      ```bash
      mvn clean install
      ```

---

By following these steps, you will have your app up and running in no time!


## **Usage Guide**
### **1. Running the Application**
To run the application, follow these steps:

1. **Default Run Configuration**:
    - Use the **Main** run configuration provided in IntelliJ.
    - To execute, click the green play button or select **Run > Run 'Main'**.

2. **If the Run Configuration Fails**:
    - Reload Maven:
        - Open the Maven tool window (**View > Tool Windows > Maven**).
        - Click **Reload All Maven Projects** to ensure all dependencies are resolved.
    - Retry running the **Main** run configuration.

3. **Manual Execution**:
    - Navigate to `src/main/java/app/Main.java` in your project structure.
    - Right-click on `Main.java` and select **Run 'Main'** to directly execute the application without using the run configuration.

---

### **2. Expected Behavior**
- Upon successful execution, the application initializes and displays the interface for creating and managing groups, messaging, and interacting with the VacationBot.

---

### **3. Troubleshooting**
- **Application Doesn't Launch**:
    - Verify that the correct JDK is set (**File > Project Structure > Project SDK**).
    - Reload Maven dependencies and ensure the `pom.xml` file is correctly configured.

- **Compilation Issues**:
    - Run the following command in the terminal to clean and rebuild the project:
      ```bash
      mvn clean install
      ```
    - Retry running the application after rebuilding.

- **Run Configuration Missing**:
    - Manually navigate to `src/main/java/app/Main.java` and run the file as described above.

---

## **Technologies Used**
- **GPT-3.5 Turbo API**: Used to power the VacationBot for generating intelligent, context-aware vacation recommendations.
- **Google Firestore**: A cloud-hosted NoSQL database utilized for storing messages and group data, ensuring real-time updates for the messaging system.
- **Java**: The primary programming language for implementing core functionality and backend logic.
- **Maven**: Used for project management and dependency handling in the application.

## **License**
This project is licensed under the Creative Commons Legal Code. See [LICENSE](./LICENSE) for details.

## **Feedback**
We value your feedback! Please share your thoughts or report issues by sending an email to:

**Email**: [ahnaf.ardhito@mail.utoronto.ca](mailto:ahnaf.ardhito@mail.utoronto.ca)

### Guidelines:
- Be specific about your feedback or the issue encountered.
- Include relevant details, such as steps to reproduce a bug or suggestions for improvement.

### Review Process:
- Feedback is reviewed on a monthly basis, and responses will be provided as necessary.

Thank you for helping us improve VentureBound!
## **Contributing**
1. Contributions are closed.

## **Writing and Visual Notes**
- This document is up-to-date as of 12-1-2024.

---

Thank you for exploring VentureBound! Let us know how we can improve your experience.
