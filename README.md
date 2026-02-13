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
- `GET /orders/{id}` - Fetch order by ID
- `GET /orders?customerId={id}` - List orders for a customer
- `GET /orders?status={status}&date={date}` - Filtered order list

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

## Project Phases

This project has evolved beyond initial design and now includes measured performance optimizations 
and concurrency validation.

### Phase 1 - Baseline Performance
- Implemented OFFSET-based pagination
- Measured latency degradation at high offsets (up to ~896 ms at 1M rows)
- Established baseline metrics

### Phase 2 - Optimized Read Performance
- Implemented keyset (cursor) pagination
- Reduced latency to ~7-9 ms consistently
- Eliminated OFFSET scalability issues
- Load tested up to 100 sequential requests (~7 ms avg)

### Phase 3 - Concurrency & Transaction Safety
- Added `@Transactional` service-layer boundaries
- Implemented optimistic locking using `@Version`
- Simulated 20-50 concurrent threads
- Validated no lost updates or inconsistent writes

Detailed documentation and test results are available in the `/docs` folder

## Future Enhancements

Planned next steps to further evolve this system toward production-grade readiness:

- Observability: Actuator + Micrometer metrics, latency and DB monitoring
- Connection pool tuning (HikariCP)
- Isolation level benchmarking and failure scenario testing
- Input validation and security hardening
- Caching layer evaluation (Redis)
- Containerization and optional cloud deployment

These enhancements focus on operational readiness, scalability, and production observability.
