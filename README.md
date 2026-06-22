# Distributed Rate Limiter Engine

A high-performance, fault-tolerant distributed rate limiting middleware built using **Java (Spring Boot)** and **Redis**, implementing the **Token Bucket Algorithm** via atomic **Lua scripting**.

## System Architecture
- **Interception Layer:** Custom Spring `HandlerInterceptor` capturing incoming HTTP request metadata (IP/Client ID) before it hits core controllers.
- **Data Layer:** Containerized Redis cluster serving as a centralized, high-speed, in-memory state store.
- **Atomicity Guard:** All token calculations (evaluating time elapsed vs. bucket refill rate) are offloaded to an internal Redis Lua script execution to eliminate concurrency race conditions.

## Tech Stack
- **Backend:** Java 21, Spring Boot (Web, Data Redis)
- **Database/Cache:** Redis (Alpine Engine via Docker Desktop)
- **Testing/Automation:** Postman Collection Runner, Docker Live Monitor

## Verification & Empirical Results
Validated under simulated network stress using Postman to fire consecutive, zero-delay bursts:
- **Burst Capacity:** 5 tokens
- **Refill Rate:** 1 token/sec
- **Observed Sequence:** `6 OK -> 6 Blocked (429) -> 1 OK -> 2 Blocked`. This empirically proves that token replenishment maps accurately to elapsed system time metrics without synchronization drifts.

## Snapshots
- <img width="1480" height="842" alt="image" src="https://github.com/user-attachments/assets/8fca1bcb-d5ab-4e10-8a60-b2822cd278e3" />
- <img width="1481" height="837" alt="image" src="https://github.com/user-attachments/assets/ae4ac1b7-72fe-482b-a99f-494fd5440e09" />
- <img width="1480" height="832" alt="image" src="https://github.com/user-attachments/assets/70390d8d-c3bb-4353-bf71-b4de58179227" />
- <img width="1475" height="845" alt="image" src="https://github.com/user-attachments/assets/45a5a52f-7ad5-4125-b7e1-38fd6ea27b8e" />




