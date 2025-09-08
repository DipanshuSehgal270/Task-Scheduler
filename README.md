# Real-Time Task Scheduler & Collaboration Platform

## 📌 Overview
The **Real-Time Task Scheduler & Collaboration Platform** is a **cloud-native microservices-based application** designed to manage everything from **personal to-do lists** to **collaborative team projects**.  

Built using **Spring Boot**, **Spring Cloud**, and **Docker**, the system ensures **scalability, resilience, and ease of deployment**. It follows a **modular architecture** with independently deployable services for flexibility and maintainability.

---

## 🚀 Current Status (MVP - Phase 1 Complete)
The **Minimum Viable Product (MVP)** foundation is fully implemented.  

### ✅ Key Accomplishments
- **Service Discovery (Eureka):** Centralized registry for all services.  
- **User & Auth Service:** Basic user registration & login (unsecured for now).  
- **Task Service:** CRUD operations for tasks (unsecured).  
- **API Gateway:** Unified entry point with path-based routing.  
- **Service Registration:** All services auto-register with **Eureka**.  
- **Containerization:** Entire stack runs with `docker-compose up`.  
- **API Development:** Basic endpoints for authentication and tasks are available via API Gateway.  

---

## 🏗️ System Architecture
The platform follows a **microservices architecture**, where each service is loosely coupled and independently deployable.

**Flow:**
1. Client request → **API Gateway**  
2. API Gateway routes request → correct service  
3. Service lookup → **Eureka Discovery Server**  
4. Request processed → **User/Task Service**  

---

## 🛠️ Technology Stack
| Category          | Technology                             | Purpose                                  |
|-------------------|----------------------------------------|------------------------------------------|
| Backend           | Java 17, Spring Boot                   | Core application framework               |
| Microservices     | Spring Cloud, Netflix Eureka, Gateway  | Service discovery, routing, communication|
| Data Management   | Spring Data JPA, H2 Database           | In-memory DB (MVP)                       |
| Build Tool        | Maven                                  | Dependency management, builds            |
| Containerization  | Docker, Docker Compose                 | Packaging & orchestration                |

---

## 🗺️ Roadmap (Future Scope)
### **Phase 2: API Security**
- Implement **JWT-based authentication**  
- Add **JWT filter** in Task Service for validation  

### **Phase 3: Collaboration Service**
- Create new **Collaboration Service**  
- Support **task lists, roles (Admin/Member), permissions**  
- Add **invitations & permission checks**  

### **Phase 4: Advanced User Features**
- **Productivity Dashboard** with analytics  
- **Time tracking** for tasks  
- **Sub-tasks** for hierarchy  

### **Phase 5: Production Readiness**
- Migrate to **PostgreSQL**  
- Set up **CI/CD pipeline** (GitHub Actions/Jenkins)  
- Add **Distributed Tracing (Zipkin)** & **Monitoring (Prometheus/Grafana)**  

---

## 📂 Project Structure
```
├── api-gateway/          # API Gateway service
├── eureka-server/        # Service discovery (Eureka)
├── user-service/         # Handles user registration & authentication
├── task-service/         # Manages tasks (CRUD operations)
├── docker-compose.yml    # Container orchestration
└── README.md             # Project documentation
```

---

## ⚡ Quick Start
### Prerequisites
- Docker & Docker Compose  
- JDK 17  
- Maven  

### Run the Application
```bash
# Clone repository
git clone https://github.com/your-repo/task-scheduler.git
cd task-scheduler

# Start services
docker-compose up --build
```

### Access Services
- **Eureka Dashboard:** [http://localhost:8761](http://localhost:8761)  
- **API Gateway:** [http://localhost:8080](http://localhost:8080)  

---

## 📌 Version
**Document Version:** 1.0  
**Last Updated:** September 5, 2025  
