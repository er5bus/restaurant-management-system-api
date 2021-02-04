# Restaurant management system

A restaurant table is characterized by a number and always has a theoretical maximum capacity and a type. A customer, in reality the one paying the bill, has a name, first name and date of birth and can also have an email address or a phone number (both optional). A ticket concerns a customer, and each ticket has a unique and always entered number, a number of seats and the amount of the bill paid. We did not use the date of the ticket as the primary key, because it is possible to have two tickets generated at exactly the same time. A dish is a starter, a main course or a dessert; and it has a price.

NB: A supplement attribute in the table class allows you to charge a potential supplement depending on the type of table (small terrace, large terrace, etc.).

## Project Requirements:

In order to get the project running you need to install:

* docker

#### Install Docker:

Docker is an open platform for developing, shipping, and running applications. Docker enables you to separate your applications from your infrastructure so you can deliver software quickly.

[Get Docker](https://docs.docker.com/get-docker/).

## Setting the Project Locally:

#### Cloning the project:

Once you have all the needed requirements installed, clone the project:

``` bash
git clone git@github.com:er5bus/restaurant-management-system.git
```

#### Run the Project:

to run the project type:

``` bash
docker-compose up --build -d
```

Check 0.0.0.0:8000/swagger-ui.html on your browser!

That's it.

