# Distributed Rate Limiter Engine

A high-performance, fault-tolerant distributed rate limiting middleware built using **Java (Spring Boot)** and **Redis**, implementing the **Token Bucket Algorithm** via atomic **Lua scripting**.

## 🚀 System Architecture
- **Interception Layer:** Custom Spring `HandlerInterceptor` capturing incoming HTTP request metadata (IP/Client ID) before it hits core controllers.
- **Data Layer:** Containerized Redis cluster serving as a centralized, high-speed, in-memory state store.
- **Atomicity Guard:** All token calculations (evaluating time elapsed vs. bucket refill rate) are offloaded to an internal Redis Lua script execution to eliminate concurrency race conditions.

## 🛠️ Tech Stack
- **Backend:** Java 17+, Spring Boot (Web, Data Redis)
- **Database/Cache:** Redis (Alpine Engine via Docker Desktop)
- **Testing/Automation:** Postman Collection Runner, cURL, Docker Live Monitor

## 📊 Verification & Empirical Results
Validated under simulated network stress using Postman to fire consecutive, zero-delay bursts:
- **Burst Capacity:** 5 tokens
- **Refill Rate:** 1 token/sec
- **Observed Sequence:** `6 OK -> 6 Blocked (429) -> 1 OK -> 2 Blocked`. This empirically proves that token replenishment maps accurately to elapsed system time metrics without synchronization drifts.