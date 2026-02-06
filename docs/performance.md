# Phase 1 - Baseline Performance Metrics

## 1. Problem Definition

Fetching orders in a read-heavy system can become slow as data grows. 
Returning large datasets in a single request causes:

* High latency
* Possible timeouts
* Poor user experience

**Goal:** Build a simple baseline system to measure raw CRUD performance before any optimization.

---

## 2. Dataset

* Total orders seeded: **1,000,000 rows**
* Each order contains:

    * `id` (primary key)
    * `customerName` (string)
    * `amount` (BigDecimal)
* Spring Boot + JPA + MySQL backend
* SQL logging enabled (`show-sql`, `hibernate.SQL=DEBUG`)

---

## 3. Endpoints Implemented

| Endpoint       | Method | Description                                   |
| -------------- | ------ | --------------------------------------------- |
| `/orders`      | POST   | Create a new order                            |
| `/orders/{id}` | GET    | Get order by ID                               |
| `/orders`      | GET    | List orders with pagination (`page` & `size`) |

---

## 4. Baseline Latency Measurements

* Pagination: `page = 0, 1000, 10000, 50000, 100000`
* Page size: 10 rows
* Latency measured by **4 hits per page**, average calculated

| Page    | Offset    | Avg Latency (ms) |
| ------- | --------- | ---------------- |
| 0       | 0         | 105.5            |
| 1000    | 10,000    | 106              |
| 10,000  | 100,000   | 155.75           |
| 50,000  | 500,000   | 436.75           |
| 100,000 | 1,000,000 | 896.5            |

**Observations:**

* Small pages (0–10k) perform well (~105ms)
* Latency starts increasing significantly after 100k offset
* Page 100,000 (~1M rows) shows **OFFSET pagination problem** → almost 900ms

> This baseline highlights why **pagination optimization, indexing, and keyset/cursor pagination** are needed.

---

## 5. SQL Queries Observed

**Example for paginated query:**

```sql
select
    o1_0.id,
    o1_0.amount,
    o1_0.customer_name
from
    orders o1_0
order by
    o1_0.id
limit ?, ?;
```

* `?` = offset, page size
* Observed `count(*)` query executed automatically by Spring Data JPA

---

## 6. Conclusion

* Baseline established with **1M rows**
* OFFSET pagination shows **poor scalability at high offsets**
* Phase 2 will optimize:

    * **Indexes** (single/composite)
    * **Keyset/cursor pagination**
    * **Query optimization / N+1 fixes**
   