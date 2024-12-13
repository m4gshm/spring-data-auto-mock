# spring-data-auto-mock

Data access layer mock utility.

Add `@EnableAutoRepositoryMocks` to your test class and avoid creating unused Spring Data repositories 
in test cases by `@MockBean`, which simply serve to initialize the context.
