# Library System App

## Overview

This Library System is a console-based application developed in Java. It enables management of a book collection within a library, offering functionalities for users to loan and return books, and for administrators to view reports on book loans.

## Features

- **User Accounts**: Allows for the creation of new user accounts. The system includes default accounts for both a standard user and an administrator. User account information is persisted across sessions in a JSON file.
- **Book Management**: Users can view available books, loan books, and return books. The status of each book (available or loaned) is persisted in a JSON file, ensuring data persistence across application restarts.
- **Reporting**: Administrators can access reports to see which books are currently loaned out and the frequency of loans for each book.

## Getting Started

### Prerequisites

- Java Development Kit (JDK), version 11 or higher.
- Maven, for dependency management and project building.

### Setup

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Use Maven to build the project.
4. Run the application.

### Default Accounts

- **Admin Account**: Username: `admin`, Password: `password`
- **User Account**: Username: `user`, Password: `password`

## Contact

For any additional questions or comments, feel free to reach out to me directly through the contact information provided on my GitHub page.