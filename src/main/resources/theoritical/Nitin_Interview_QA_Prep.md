# Interview Q&A Prep — Based on Nitin Kumar Gupta's CV

### Technical Architect & Team Leader | Java/Spring, AWS, Kafka, Microservices

---

## SECTION 1: Java & Spring (Core Technical)

**1. You upgraded systems from Java 6/8 to Java 17/21. What are the top 3 features that gave you real production
benefit?**
> Answer: Records and sealed classes reduced boilerplate DTOs; virtual threads (Java 21, Project Loom) helped with
> I/O-bound microservice calls without needing reactive rewrites; pattern matching for switch simplified business rule
> logic. Also cite removal of deprecated APIs (e.g., `sun.misc.*`, old security providers) closing security gaps.

**2. Explain how you reduced a REST endpoint's latency from 3 seconds to 15 milliseconds.**
> Answer: Identify it was likely caused by synchronous blocking calls, N+1 DB queries, or missing caching. Frame your
> answer as: profiled with APM (New Relic), found the bottleneck (e.g., sequential DB calls or unindexed query),
> introduced Redis caching for hot data, converted to async/non-blocking (WebFlux) or batched calls, and re-indexed DB.
> Always end with the measurable outcome.

**3. What's the difference between Spring Boot, Spring WebFlux, and Spring Batch — when do you choose each?**
> Answer: Spring Boot (Web MVC) — standard synchronous REST APIs, sufficient thread-per-request model. WebFlux —
> reactive, non-blocking, used when handling high concurrency with limited threads (e.g., I/O-heavy Kafka consumers).
> Spring Batch — used for the 6M-record batch modernization project, ideal for chunk-based processing, restartability,
> and
> transaction management over large datasets.

**4. How does Spring manage transactions, and how did you handle transaction boundary optimization in your Citi
project?**
> Answer: Spring uses `@Transactional` with proxy-based AOP to manage commit/rollback. In the Citi project, transaction
> scope was reduced by excluding SELECT statements from the transactional boundary (only including writes), shrinking
> lock
> duration and meeting strict SLA windows.

**5. Explain distributed 2-phase commit (2PC) and why you used Atomikos.**
> Answer: 2PC ensures atomicity across heterogeneous systems (DB + JMS queue) — a "prepare" phase where all resources
> vote to commit, then a "commit" phase executed only if all agree. Atomikos was used as the JTA transaction manager to
> coordinate this across Oracle DB and JMS, guaranteeing zero data loss during the TIBCO-to-Java migration.

---

## SECTION 2: Microservices & Architecture

**6. Walk me through how you'd design a microservices architecture for a banking payment system (like your JPMC ACH
project).**
> Answer: API Gateway for routing/auth → dedicated services per bounded context (Payment Initiation, Validation, Ledger,
> Notification) → Kafka for async event propagation between services → Saga pattern for distributed transaction
> consistency (since 2PC doesn't scale well across microservices) → Outbox pattern to guarantee reliable event
> publishing → idempotency keys to handle retries safely.

**7. What is the Saga pattern and how does it differ from 2PC?**
> Answer: Saga breaks a distributed transaction into a sequence of local transactions, each with a compensating action
> if a later step fails (eventual consistency). 2PC is a blocking, synchronous protocol suited for tightly-coupled
> systems
> (like your Atomikos use case) but doesn't scale well in loosely-coupled microservices — which is why Saga became the
> standard for your JPMC/RBC style event-driven systems.

**8. Explain the Outbox pattern and why it matters in event-driven systems.**
> Answer: Ensures atomicity between a DB write and a message publish — instead of writing to DB and then separately
> publishing to Kafka (risking inconsistency if one fails), you write the event to an "outbox" table in the same DB
> transaction, then a separate poller/CDC process publishes it to Kafka reliably.

**9. What is CQRS and where would you apply it in a banking system?**
> Answer: Command Query Responsibility Segregation — separates write models (commands, e.g., "process payment") from
> read models (queries, e.g., "get transaction history"), allowing each to scale and be optimized independently. Useful
> in
> high-read banking dashboards where read replicas or denormalized views serve queries while a normalized write model
> handles transactions.

**10. What's Hexagonal Architecture, and why would you use it over a typical layered architecture?**
> Answer: Hexagonal (Ports & Adapters) isolates core business logic from external concerns (DB, messaging, UI) via
> interfaces (ports) and implementations (adapters). This makes it easier to swap infrastructure (e.g., switching from
> JMS
> to Kafka, as you did generalizing to SQS) without touching business logic — improving testability and reducing vendor
> lock-in.

**11. How do you approach designing for 1,000+ TPS in an event-driven Kafka system?**
> Answer: Partition topics appropriately for parallelism, tune producer batching/linger.ms for throughput, use consumer
> groups scaled to partition count, ensure idempotent producers/consumers to avoid duplicate processing, and monitor
> consumer lag. Backpressure handling and async processing are key to sustaining throughput without overwhelming
> downstream systems.

---

## SECTION 3: Kafka & Streaming

**12. How do you guarantee message delivery in Kafka (you mentioned "guaranteed message delivery" for JPMC)?**
> Answer: Producer side — set `acks=all` and enable idempotence to avoid duplicates on retries. Consumer side — use
> manual offset commits after successful processing (at-least-once), combined with idempotent business logic (e.g.,
> dedup
> keys) to effectively achieve exactly-once semantics.

**13. Explain your bounded stream-processing framework using Kafka + ForkJoinPool. Why not just use Kafka Streams?**
> Answer: For CPU-bound parallel processing of large in-memory partitioned datasets, ForkJoinPool's work-stealing
> algorithm gave more control over thread allocation and memory bounds than Kafka Streams' higher-level abstraction —
> critical when needing to stay within strict memory limits (e.g., your 800MB constraint) while still processing
> multi-million record datasets efficiently.

**14. How do you tune a Kafka broker for high throughput vs low latency?**
> Answer: For throughput — increase `batch.size`, `linger.ms`, use compression (snappy/lz4), increase partition count.
> For latency — reduce `linger.ms`, disable batching delays, tune `fetch.min.bytes` on the consumer side. It's a
> trade-off, and in your Bajaj Finance project you likely optimized for low-latency lead distribution.

**15. What happens if a Kafka consumer crashes mid-processing — how do you avoid data loss or duplication?**
> Answer: With manual offset commit strategy — only commit after successful processing. On crash, the consumer group
> rebalances and un-committed messages are re-delivered to another consumer. To avoid double-processing side effects,
> implement idempotent writes (e.g., upsert by unique key) downstream.

---

## SECTION 4: Cloud & DevOps (AWS, Kubernetes, Terraform)

**16. Walk me through your approach to migrating on-prem banking infra to AWS using Terraform.**
> Answer: Start with a Landing Zone setup (VPC, IAM, security baselines) using reusable Terraform modules, then migrate
> compute (EC2/EKS), data (RDS with DMS for migration), and networking incrementally per environment. Use blue-green or
> phased cutover to minimize downtime, validate with smoke tests, and roll back via Terraform state if issues arise. You
> achieved 99.9% uptime and 5% cost reduction this way.

**17. What's the difference between EKS, ECS, and Lambda — how do you decide which to use?**
> Answer: EKS — full Kubernetes control, best for complex microservices needing custom scheduling/autoscaling (used in
> your JPMC/RBC projects with Karpenter). ECS — simpler, AWS-native container orchestration, less operational overhead.
> Lambda — event-driven, serverless, ideal for short-lived, sporadic workloads (e.g., triggered file processing) without
> needing persistent infra.

**18. What is Karpenter and why did you choose it over the standard Cluster Autoscaler?**
> Answer: Karpenter provisions right-sized nodes just-in-time based on actual pod requirements rather than pre-defined
> node groups, reducing over-provisioning and cost while improving scheduling speed — which is why it helped optimize
> resource utilization in your EKS workloads.

**19. Explain your CI/CD pipeline design with GitHub Actions, Helm, and Blue-Green deployments.**
> Answer: GitHub Actions triggers build → run unit tests + SonarQube/Snyk security scans → package as Helm chart →
> deploy to a "green" environment → run smoke tests → switch traffic (via Ingress/load balancer) from blue to green →
> keep
> blue as instant rollback target. This gives zero-downtime, low-risk releases.

**20. What was involved in your PCF (Pivotal Cloud Foundry) to OCP (OpenShift) migration for RBC?**
> Answer: Re-platforming apps from PCF's buildpack model to OpenShift's container-native model — containerizing apps
> (Dockerfiles), converting PCF manifests to Kubernetes/OpenShift manifests (deployments, routes, services), adjusting
> for
> OpenShift's stricter security context constraints (SCCs), and validating CI/CD pipeline compatibility with the new
> platform.

---

## SECTION 5: Security & DevSecOps

**21. How did you integrate OWASP Dependency-Check and Snyk into your CI/CD pipeline?**
> Answer: Added as pipeline gates post-build — Snyk/OWASP Dependency-Check scans dependencies for known CVEs, and the
> pipeline fails the build if high/critical vulnerabilities are found, enforcing a "shift-left" security posture before
> code reaches production.

**22. What's your approach to secrets management in a microservices environment (you listed HashiCorp Vault)?**
> Answer: Store secrets (DB creds, API keys) in Vault rather than config files or environment variables; services
> authenticate to Vault (via Kubernetes service accounts) and dynamically fetch short-lived credentials, reducing the
> blast radius if a secret is compromised.

**23. Explain OAuth2/JWT flow in the context of securing your microservices.**
> Answer: Client authenticates with an Identity Provider, receives a JWT access token; each microservice validates the
> token's signature and claims (scope/roles) via the API Gateway or a local filter, avoiding a centralized session store
> and enabling stateless, scalable authorization across services.

---

## SECTION 6: Leadership & Behavioral

**24. Tell me about a time you had to make an architectural decision that your team disagreed with. How did you handle
it?**
> Answer: Structure with STAR (Situation, Task, Action, Result) — describe presenting trade-offs transparently to your
> 13-engineer team, gathering their concerns, doing a proof-of-concept or spike to validate the decision empirically
> rather than by authority, and either adjusting the approach or aligning the team once data supported the direction.

**25. How do you mentor engineers while remaining hands-on with code and architecture?**
> Answer: Balance through structured practices — architecture review sessions, pair programming on complex modules, code
> review as a teaching tool (not just gatekeeping), and delegating ownership of subsystems to build engineers'
> confidence
> while staying involved in HLD/LLD reviews and critical design decisions.

**26. Describe a situation where a production issue under peak load nearly caused an outage. How did you resolve it (
relates to your OOM/Kubernetes OOMKill experience)?**
> Answer: Describe RCA process — used New Relic/monitoring to identify memory pressure, applied Java 8 partitioning
> strategy to process records in bounded chunks instead of loading all 6M records at once, tuned JVM heap and Kubernetes
> memory limits/requests accordingly, and added alerting thresholds to catch early warning signs before OOMKill in
> future.

**27. How do you manage stakeholder expectations when working across multiple bank clients (JPMC, RBC, TD)
simultaneously?**
> Answer: Emphasize context-switching discipline — dedicated time blocks per client, clear documentation (HLD/LLD) so
> decisions aren't lost between contexts, standardized status reporting cadence, and setting realistic timelines upfront
> by clarifying dependencies and risks early with each stakeholder group.

---

## SECTION 7: GenAI / RAG (Emerging Tech — likely to be probed given it's on your CV)

**28. Explain the RAG pipeline you built — how does vector search combine with LLM generation?**
> Answer: Documents/data are chunked and embedded into vector representations, stored in a vector DB (e.g., pgvector,
> Pinecone, OpenSearch). At query time, the user's query is embedded and a semantic similarity search retrieves the most
> relevant chunks; these are injected as context into the LLM prompt, which then generates a grounded, context-aware
> response — reducing hallucination vs a standalone LLM call.

**29. Why integrate the RAG pipeline with Spring Boot rather than a Python-native stack (like LangChain)?**
> Answer: Likely driven by existing enterprise Java/Spring investment, easier integration with existing
> microservices/security (OAuth2, Vault) and observability stack, and avoiding introducing a second language/runtime
> into
> the production ecosystem — trading some ecosystem convenience (Python has richer GenAI tooling) for operational
> consistency.

**30. How did you achieve sub-second response latency in your RAG pipeline?**
> Answer: Likely via approximate nearest neighbor (ANN) indexing (e.g., HNSW) for fast vector search instead of exact
> search, caching frequent queries/embeddings, keeping the vector DB co-located with the app for low network latency,
> and
> possibly batching or streaming LLM responses.

---

## Tips for the Interview

- For every "how did you do X" question, use a brief **Situation → Approach → Result** structure with a number (e.g.,
  "3s → 15ms", "3 hrs → 5 min", "99.9% uptime").
- Interviewers will drill into **your own bullet points** verbatim — be ready to go 2–3 levels deeper than what's
  written (e.g., if you say "Kafka with 1,000+ TPS," expect "how did you test that? what was the bottleneck at 800
  TPS?").
- For architecture rounds, always mention **trade-offs** — no answer should sound like there was only one possible
  solution.
