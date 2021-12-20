# image-metadata

### Solution

I have used onion architecture.
- At the ``interfaces`` there is a RESTApi to fetch data related to image metadata.
- The ``infrastructure`` (``repositories``) is responsible to talk to in-memory db.
- The ``domain`` is responsible to convert a Base64 to a byte array.
  - At the domain, repositories' ``Interfaces`` are just defined to tell the ``infrastructure`` what to do.
  - At the domain, the exception handler is implemented.
  - At the domain, the models are just anemic objects.
- The ``application`` is responsible to say **what to do**, validating and processing the ``interfaces'`` request, and talking to ``infrastructure``.
  - The ``QueryService`` is responsible to handle the queries.

### Requirement
- Java 11
- Gradle

### Running from docker

- To build it use this command: ``sudo docker build -t image-metadata .``
- Then we run the docker image: ``sudo docker run -p 8083:8083 image-metadata``
- The address of rest api is: ``http://loclahost:8083``
- The address of swagger is: ``http://localhost:8083/swagger``
- There is ``UP42.postman_collection.json`` in the root. You can just import it into the Postman to test the RESTApi.







