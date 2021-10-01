# Commit Viewer

Simple Git public repositories commit viewer.

## Installation

wip

### Test Coverage
-   Open /{project_folder}/target/site/jacoco/index.html

## Usage

### Endpoint
#### http://{app_url}/api/commits?url={git_repository_url}&?page={page}&size={size}
Provides commits of a given git repository url (mandatory), pagination is optional.

Pagination has a default value of page = 1, size = 5, if no values are provided.

(note: tomcat app_url should be 'http://localhost:8080/commit-viewer-0.0.1-SNAPSHOT')

(note: spring boot (Eclipse/IntelliJ) app_url should be 'http://localhost:8080')

#### Flow
-   User request, through the Web api;
-   Returns commits representation from the the Database for given Git repository Url, if present;
-   No Git Repository is present in Database, Rest GitHub api is called, commits retrieved are stored in DB, and a representation is returned;
-   No Git Repository is present in Database, Rest GitHub api is not able to respond for any reason, commits are retrieved from the Command Line Interface and stored in the DB, a representation is returned.

## Architecture

![Kiku](images/app_architecture.png)

Application is divided in three main parts, Web, Application, Core, Infrastructure.

### Web 
Rest API, opens the application to outside interaction.

Controllers and Web Configuration classes are all present in this layer.

Error Handling for a better user friendly experience.

### Application
Handles process flows, creates or updates Domain entities (Core).

Provides API for Web layer usage, and contracts (ports) that the Infrastructure layers must obey.

### Core
Domain objects, core business logic should be handled here, how an object can be built, witch fields are mandatory, etc.

Provides contract for persistence layer interaction (Repository).

### Infrastructure
Provides the Application/Core layers with the implementations (adapters/repositories) required to function;