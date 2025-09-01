# LuxeLine-v1

LuxeLine is a full-stack e-commerce web application featuring a robust product catalog and secure user authentication. It combines a scalable Java Spring Boot backend with a responsive and modern React-based frontend.

## Features

- **Product Catalog:** Browse products with dynamic filtering and sorting.
- **User Authentication:** Secure registration, login, and session management.
- **Responsive UI:** Modern interface built with React, TypeScript, Tailwind CSS, and shadCN UI components.
- **REST APIs:** Well-structured and secure endpoints for frontend-backend communication.
- **JWT Security:** Protected routes and user sessions powered by JWT token-based authentication.
- **Scalable Architecture:** MVC-inspired layered backend design ensures maintainability and scalability.

## Tech Stack

- **Frontend:**  
  - React  
  - TypeScript  
  - Tailwind CSS  
  - shadCN UI  

- **Backend:**  
  - Java Spring Boot  
  - JWT Authentication  
  - MVC Architecture  
  - REST API  
  - MongoDB  

---

## Getting Started

### Prerequisites

- Node.js & npm (for frontend)
- Java 17+ (for backend)
- MongoDB

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```
4. The app runs at `http://localhost:3000`

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Configure your MongoDB connection in `application.properties`.
3. Build and run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API server starts at `http://localhost:8080`

---

## Project Structure

```
LuxeLine-v1/
├── frontend/      # React, TypeScript, Tailwind CSS, shadCN UI
└── backend/       # Java Spring Boot, JWT, MVC, REST API, MongoDB
```

## Authentication & Security

- User credentials are securely managed via JWT tokens.
- APIs use token validation for protected routes.
- Session management is handled server-side for enhanced security.

## UI Components

- Responsive design with Tailwind CSS and shadCN UI for a modern look and feel.
- All components are modular and reusable.

## API Overview

- RESTful endpoints for products, users, authentication, and orders.
- JWT-required for protected operations.

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License.
