# Phase 0 - Problem Definition

## What is slow?
Listing orders (fetching all or filtered orders) becomes slow as the number of orders grows to millions. 
Fetching large datasets in a single request can cause timeouts, high response times, and poor user experience. 
Even with proper indexing, returning too many records in one request is expensive.

## Why does it matter at scale?
As the system grows, read-heavy operations dominate traffic. 
Without pagination or optimization, users experience delays, timeouts, and degraded performance. 
Ensuring fast reads under high scale is critical for usability and trust.

## Business risks if it fails
Slow responses and timeouts can frustrate users, reduce trust in the system, and lead to abandonment of the application. 
Critical operations like order retrieval or creation must remain reliable.

## Expected scale
100k–1M orders initially, with the ability to scale beyond this number.

## Read vs Write %
- Reads dominate usage since customers frequently fetch or search orders.
- Estimated ratio: 70-85% reads, 15–30% writes.
- Writes occur less frequently, mainly for creating or updating orders.

## Hot endpoints
- Fetch order by ID
- List orders by customer ID
- Filtered order lists

These endpoints receive the most traffic and require optimization for high-read performance.

## Consistency Guarantees
- **Strong consistency:** Fetching a single order by ID, creating or updating orders - must never fail.
- **Eventual consistency:** Listing orders can tolerate slight staleness depending on business requirements.
