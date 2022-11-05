## Technologies Used
* Java
* Spring
* MySQL

## Environment Variables Required
* SPRING_DATASOURCE_URL (database URL in JDBC format)
* SPRING_DATASOURCE_USERNAME (username of the database connection)
* SPRING_DATASOURCE_PASSWORD (password of the database connection)

## Installation

````
# Clone the repo
git clone https://github.com/therealgibby/movie-api.git

# Navigate to the repo & install with Maven
cd movie-api
mvn install
````

## API Calls

````
# POST & PUT Request Body
{
        "title": "Movie Title",
        "director": "Director Name",
        "actors": "Actors, ..."
}
````
#### /api/movies
* `GET`: Get a movie
* `POST`: Create a new movie

#### /api/movies/{id}
* `PUT`: Update a movie
* `DELETE`: Delete a movie
