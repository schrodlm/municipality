# Municipality XML Parser

Application built with Spring using Gradle as a builder and PostgreSQL as the database. It will download zipped XML files from the link provided, it expects specific format of XML files, it parses
these XML files and saves specific data to the connected database (PostGreSQL) and deletes these downloaded files.


## Getting Started

### Prerequisites
- Java 8 or higher
- Gradle 6 or higher
- PostgreSQL 12 or higher

### Installation

## Running the app
1. Clone the repository: git clone https://github.com/schrodlm/municipality.git
2. Navigate to the server directory: `cd municipality`
3. In your terminal run command `./gradlew build` or use IDE (like IntelliJ which will build it for you)
4. Create a PostGreSQL database running on port 5432
    - easiest way to create one is to use Docker, you can create a database using command `docker run --name municipality_db -p 5433:5432 -e POSTGRES_PASSWORD=password -d postgres`


nd purchase tracks

## Built With
- [Spring](https://spring.io/) - The web framework used
- [Gradle](https://gradle.org/) - Dependency Management
- [PostgreSQL](https://www.postgresql.org/) - Database
- [Docker](https://www.docker.com/) - OS-level virtualization to deliver software

## Authors
- [Matěj Schrödl](https://github.com/schrodlm)

## Acknowledgments
- Hat tip to anyone whose code was used
