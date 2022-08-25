# Demonstartion API

This is a demonstartion of the creation of a simple API.

The intention is to concentrate on the coding needed to produce a working REST API and
to make that fairly complete, as such will not reference a database, instead it will
manipulate a small amount of in memory data.

At application start the data set will be empty.

The relevant data set is a simple database of products.

Each product has a name, description, price and stock level.


We will be able to:

* Create a new product.
* Modify it's stock level.
* Obtain a list of all entry names.
* Obtain the details of any given entry.


## The API

The following URIs are available:

* __GET /product/{name}__ will return product details given the product name replaces {name}.
* __GET /products__ will return a list of all product names in alphabetical order.
* __POST /product__ will create a new product.
* __PATCH /product/{name}/amendStock__ will allow the stock level to be changed of a product who's name replaces {name}.

## Implementation

The Implementation will use Java, Spring MVC,  Lombok and Swagger.





