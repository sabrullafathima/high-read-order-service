# High-Read Order Service

A high-read order service designed to handle millions of orders efficiently.  
Focuses on read-heavy operations, optimized APIs, pagination, and consistency guarantees 
to ensure reliable performance at scale.

## Features
- Fetch a single order by ID
- List orders by customer ID
- Filtered order lists
- Pagination to handle large datasets
- Strong consistency for critical operations

## Expected Scale
- 100k–1M orders initially, designed to scale beyond 1M
- Read-heavy traffic (75-85% reads, 15-30% writes)

## Hot Endpoints
- `GET /orders/{id}` — Fetch order by ID
- `GET /orders?customerId={id}` — List orders for a customer
- `GET /orders?status={status}&date={date}` — Filtered order list

## Consistency Guarantees
- **Strong consistency:** Fetching, creating, updating orders
- **Eventual consistency:** Listing orders (if slight staleness is acceptable)

## Getting Started
1. Clone the repo:
   ```bash
   git clone https://github.com/sabrullafathima/high-read-order-service.git
2. Navigate to the project folder:
   `cd high-read-order-service`
3. Open in your IDE and start development.

## Technologies
Java 2.1
Spring Boot 
REST APIs
MySQL
Pagination and indexing for high-read optimization

## Notes
This repo is a `Phase 0` / design-focused project to demonstrate handling read-heavy APIs at scale. 
Future phases include implementing the APIs, database indexing, caching, and performance testing.