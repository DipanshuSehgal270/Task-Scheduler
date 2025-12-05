<div align="center">

# **Real-Time Task Scheduler & Collaboration Platform**
A cloud-native, distributed microservices ecosystem designed for advanced task management and real-time team collaboration.

**Architectural Overview • Configuration • API Reference • Installation**

</div>

---

# 📖 About The Project
This platform is a reference implementation of a modern, production-grade microservices architecture. Unlike simple monolithic CRUD systems, this ecosystem incorporates real distributed systems concerns such as:

- **Stateless Authentication:** JWT shared across service boundaries.
- **Service Discovery:** Dynamic registration using **Netflix Eureka**.
- **API Gateway Routing:** All external traffic passes through **Spring Cloud Gateway**.
- **Contextual Authorization:** Collaboration Service performs permission checks for the Task Service.

---

<a name="architectural-overview"></a>
# 🏗️ Architectural Overview
Business logic is isolated into three services (User, Task, Collaboration) supported by infrastructure services (Gateway, Eureka).

```mermaid
graph TD
    Client[Client App / Postman]
    Gateway[API Gateway :8083]
    Eureka[Eureka Server :8761]

    subgraph Business Logic
        US[User Service :8081]
        TS[Task Service :8082]
        CS[Collaboration Service :8084]
    end

    Client -->|HTTPS + JWT| Gateway
    Gateway -->|Routes Request| US
    Gateway -->|Routes Request| TS
    Gateway -->|Routes Request| CS

    US -.->|Registers| Eureka
    TS -.->|Registers| Eureka
    CS -.->|Registers| Eureka

    TS -- Feign → Get UserId --> US
    TS -- Feign → Check Permission --> CS
    CS -- Feign → Get UserId --> US

    style Gateway fill:#f9f,stroke:#333,stroke-width:2px
    style Eureka fill:#ccf,stroke:#333,stroke-width:2px
````

---

# 📦 Service Portfolio

| Service Name              | Docker Container        | Port     | Description                        |
| ------------------------- | ----------------------- | -------- | ---------------------------------- |
| **Service Discovery**     | `eureka-server`         | **8761** | Registry of active services        |
| **API Gateway**           | `api-gateway`           | **8083** | Front-door router for all services |
| **User Service**          | `user-service`          | **8081** | Manages Users, Roles, JWT signing  |
| **Task Service**          | `task-service`          | **8082** | Task creation & management         |
| **Collaboration Service** | `collaboration-service` | **8084** | Manages Lists & Membership         |

---

<a name="configuration"></a>

# ⚙️ Configuration & Environment Variables

Environment variables (from docker-compose) drive inter-service communication.

## **Global Variables**

| Variable                                 | Default (Dev)                       | Description                           |
| ---------------------------------------- | ----------------------------------- | ------------------------------------- |
| **JWT_SECRET**                           | `mysecretkey...`                    | Shared signing key for JWT validation |
| **EUREKA_CLIENT_SERVICEURL_DEFAULTZONE** | `http://eureka-server:8761/eureka/` | Eureka registry location              |

## **Service-Level Config**

* H2 In-Memory DB
* Console: `/h2-console`
* JDBC: `jdbc:h2:mem:<dbname>`
* API Gateway auto-routing enabled: `discovery.locator.enabled=true`

---

<a name="security-architecture"></a>

# 🔐 Security & Data Flow

### **1. Authentication (JWT Based)**

* Login via **User Service**
* Returns HS256 signed JWT containing:

  * `sub`: email
  * `roles`: authorities
  * `exp`: expiration

### **2. Authorization**

Every service includes a `JwtRequestFilter` that parses and validates JWT.

### **3. End-to-End Flow: Creating a Shared Task**

```mermaid
sequenceDiagram
    participant User
    participant Gateway
    participant TaskService
    participant CollabService
    participant UserService

    User->>Gateway: POST /tasks (listId=101)
    Gateway->>TaskService: Forward + JWT

    TaskService->>TaskService: Validate JWT
    TaskService->>UserService: Get UserId
    UserService-->>TaskService: UserId

    TaskService->>CollabService: Check Membership(101, User)
    CollabService-->>TaskService: Allowed

    TaskService->>TaskService: Save Task
    TaskService-->>User: 201 Created
```

---

<a name="api-reference"></a>

# 📖 API Reference

Base URL: **[http://localhost:8083](http://localhost:8083)** (Gateway)

## 🟢 User Service

| Method | Endpoint          | Description     | Auth |
| ------ | ----------------- | --------------- | ---- |
| POST   | `/auth/register`  | Create user     | ❌    |
| POST   | `/auth/login`     | Login & get JWT | ❌    |
| GET    | `/users/by-email` | Internal lookup | ✅    |

**Register Example**

```json
{
  "username": "jdoe",
  "email": "jdoe@example.com",
  "password": "securepassword123"
}
```

---

## 🔵 Collaboration Service

| Method | Endpoint                       | Description         | Auth |
| ------ | ------------------------------ | ------------------- | ---- |
| POST   | `/lists`                       | Create new list     | ✅    |
| GET    | `/lists`                       | Get lists for user  | ✅    |
| GET    | `/lists/leader`                | Lists owned by user | ✅    |
| GET    | `/lists/{id}/check-membership` | Internal            | ✅    |

**Response Example**

```json
[
  {
    "id": 101,
    "listName": "Project Phoenix",
    "ownerId": 1
  }
]
```

---

## 🟠 Task Service

| Method | Endpoint      | Description | Auth |
| ------ | ------------- | ----------- | ---- |
| POST   | `/tasks`      | Create task | ✅    |
| GET    | `/tasks`      | Get tasks   | ✅    |
| PUT    | `/tasks/{id}` | Update      | ✅    |
| DELETE | `/tasks/{id}` | Delete      | ✅    |

**Create Task Example**

```json
{
  "title": "Design Database Schema",
  "description": "Create ER diagrams",
  "priority": "URGENT",
  "taskListId": 101
}
```

---

<a name="data-models--database-design"></a>

# 🗄️ Database Schemas

## **1. User DB**

* users
* roles
* user_roles

## **2. Collaboration DB**

* task_lists
* memberships

## **3. Task DB**

* tasks

---

<a name="installation--setup"></a>

# 🚀 Installation & Setup

### **Prerequisites**

* Docker Desktop
* Java 17+
* Maven optional

### **Run the System**

```bash
git clone https://github.com/your-username/task-scheduler.git
cd task-scheduler

docker-compose up --build
```

### **Verify**

Visit **[http://localhost:8761](http://localhost:8761)** and ensure all services show **UP**.

---

# 🛑 Troubleshooting

### **Connection Refused** → Normal during startup.

### **403 Forbidden** → JWT_SECRET mismatch.

### **404 Not Found (Gateway)** → Wrong route or missing locator config.

---

<a name="project-roadmap"></a>

# 🗺️ Project Roadmap

* [x] Infrastructure Setup
* [x] Authentication
* [x] Collaboration Domain
* [x] Task Integration
* [ ] Invitations
* [ ] Notifications (RabbitMQ)
* [ ] Frontend UI

```markdown
```
